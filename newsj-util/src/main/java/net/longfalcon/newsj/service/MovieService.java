/*
 * Copyright (c) 2016. Sten Martinez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.CategoryService;
import net.longfalcon.newsj.fs.FileSystemService;
import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.fs.model.FsFile;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.MovieInfo;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.MovieInfoDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.util.ParseUtil;
import net.longfalcon.newsj.util.StreamUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.newsj.ws.google.GoogleSearchResponse;
import net.longfalcon.newsj.ws.google.GoogleSearchResult;
import net.longfalcon.newsj.ws.tmdb.TmdbFindResults;
import net.longfalcon.newsj.ws.tmdb.TmdbMovieResults;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 2:17 PM
 */
@Service
public class MovieService {
    private static final Log _log = LogFactory.getLog(MovieService.class);
    private CategoryDAO categoryDAO;
    private FileSystemService fileSystemService;
    private GoogleSearchService googleSearchService;
    private MovieInfoDAO movieInfoDAO;
    private ReleaseDAO releaseDAO;
    private TmdbService tmdbService;

    @Transactional
    public void addMovieInfo(int imdbId) {
        _log.info("fetching movie info from tmdb - " + imdbId);

        // check themoviedb for movie info
        MovieInfo movieInfo = fetchDataFromTmdb(imdbId);
        if (movieInfo != null) {
            movieInfoDAO.update(movieInfo);
            _log.info("Added Movie " + movieInfo.getTitle());
        } else {
            _log.warn("Unable to locate movie with imdb id " + imdbId);
        }
    }

    public MovieInfo getMovieInfo(int imdbId) {
        return movieInfoDAO.findByImdbId(imdbId);
    }

    // TODO: move to genre table
    public List<String> getGenres() {
        return Arrays.asList(
                "Action",
                "Adventure",
                "Animation",
                "Biography",
                "Comedy",
                "Crime",
                "Documentary",
                "Drama",
                "Family",
                "Fantasy",
                "Film-Noir",
                "Game-Show",
                "History",
                "Horror",
                "Music",
                "Musical",
                "Mystery",
                "News",
                "Reality-TV",
                "Romance",
                "Sci-Fi",
                "Sport",
                "Talk-Show",
                "Thriller",
                "War",
                "Western");
    }

    @Transactional
    public void processMovieReleases() {
        List<Category> movieCats = categoryDAO.findByParentId(CategoryService.CAT_PARENT_MOVIE);
        List<Integer> ids = new ArrayList<>();
        for (Category category : movieCats) {
            ids.add(category.getId());
        }
        List<Release> releases = releaseDAO.findReleasesByNoImdbIdAndCategoryId(ids);

        if (!releases.isEmpty()) {
            _log.info(String.format("Processing %s movie releases", releases.size()));
        }

        for (Release release : releases) {
            String movieName = parseMovieName(release);

            if (ValidatorUtil.isNotNull(movieName)) {
                if (_log.isDebugEnabled()){
                    _log.debug(String.format("Looking up %s [%s]", movieName, release.getSearchName()));
                }

                GoogleSearchResponse googleSearchResponse = googleSearchService.search(movieName + " imdb", "");
                if (googleSearchResponse != null) {
                    int imdbId = getImdbId(googleSearchResponse);
                    if (imdbId > 0) {
                        if(_log.isDebugEnabled()) {
                            _log.debug(String.format("- found %s", imdbId));
                        }

                        release.setImdbId(imdbId);

                        //check for existing movie entry
                        MovieInfo movieInfo = movieInfoDAO.findByImdbId(imdbId);
                        if (movieInfo == null) {
                            movieInfo = fetchDataFromTmdb(imdbId);
                            if (movieInfo != null) {
                                movieInfoDAO.update(movieInfo);
                            }
                        }
                    } else {
                        //no imdb id found, set to all zeros so we dont process again
                        release.setImdbId(imdbId);
                    }
                    releaseDAO.updateRelease(release);
                }
            } else {
                //no valid movie name found, set to all zeros so we dont process again
                release.setImdbId(0);
                releaseDAO.updateRelease(release);
            }
        }
    }

    private MovieInfo fetchDataFromTmdb(int imdbId) {
        MovieInfo movieInfo;
        _log.info("querying TMDB for imdbId " + imdbId);
        TmdbFindResults tmdbFindResults = tmdbService.findResultsByImdbId(imdbId);
        List<TmdbMovieResults> movieResults = tmdbFindResults.getMovieResults();
        _log.info("TMDB returned " + movieResults.size() + " results");
        if (movieResults != null && !movieResults.isEmpty()) {
            TmdbMovieResults tmdbMovieResults = movieResults.get(0);
            movieInfo = new MovieInfo();
            movieInfo.setImdbId(imdbId);
            movieInfo.setTmdbId(tmdbMovieResults.getId());
            movieInfo.setTitle(tmdbMovieResults.getTitle());
            movieInfo.setPlot(StringUtils.abbreviate(tmdbMovieResults.getOverview(), 250));
            movieInfo.setCreateDate(new Date());
            movieInfo.setUpdateDate(new Date());
            Date releaseDate = tmdbMovieResults.getReleaseDate();
            if (releaseDate != null) {
                int year = new DateTime(releaseDate).getYear();
                movieInfo.setYear(String.valueOf(year));
            }
            int rating = tmdbMovieResults.getVoteAverage().intValue();
            movieInfo.setRating(String.valueOf(rating));
            movieInfo.setLanguage(tmdbMovieResults.getOriginalLanguage());
            movieInfo.setTagline("");
            movieInfo.setBackdrop(false);
            movieInfo.setCover(false);
            movieInfo.setActors("");
            movieInfo.setDirector("");
            movieInfo.setGenre("");

            return movieInfo;
        }

        return null;
    }

    private String parseMovieName(Release release) {
        int categoryId = 0;
        Category category = release.getCategory();
        if (category != null) {
            categoryId = category.getId();
        }
        if (categoryId != CategoryService.CAT_MOVIE_FOREIGN) {
            Pattern movieNamePattern = Pattern.compile("^(?<name>.*)[\\.\\-_\\( ](?<year>19\\d{2}|20\\d{2})", Pattern.CASE_INSENSITIVE);
            Matcher movieNameMatcher = movieNamePattern.matcher(release.getSearchName());
            if (movieNameMatcher.find()) {
                String yearString = "";
                try {
                    yearString = movieNameMatcher.group("year");
                } catch (IllegalArgumentException iae) {
                    // do nothing
                }
                if (ValidatorUtil.isNull(yearString)) {
                    movieNamePattern = Pattern.compile("^(?<name>.*)[\\.\\-_ ](?:dvdrip|bdrip|brrip|bluray|hdtv|divx|xvid|proper|repack|real\\.proper|sub\\.?fix|sub\\.?pack|ac3d|unrated|1080i|1080p|720p)", Pattern.CASE_INSENSITIVE);
                    movieNameMatcher = movieNamePattern.matcher(release.getSearchName());
                }

                String nameString = movieNameMatcher.group("name");
                nameString = nameString.replaceAll("\\(.*?\\)|\\.|_", " ");
                if (ValidatorUtil.isNotNull(yearString)) {
                    yearString = String.format(" (%s)", yearString);
                }
                return nameString.trim() + yearString;
            }
        }
        return null;
    }

    private int getImdbId(GoogleSearchResponse googleSearchResponse) {
        List<GoogleSearchResult> results = googleSearchResponse.getResponseData().getResults();
        if (results != null) {
            for (GoogleSearchResult result : results) {
                if (result.getVisibleUrl().equals("www.imdb.com")) {
                    String imdbIdString = ParseUtil.parseImdb(result.getUnescapedUrl());
                    if (ValidatorUtil.isNotNull(imdbIdString)) {
                        return Integer.parseInt(imdbIdString);
                    }
                }
            }
        }
        return 0;
    }

    @Transactional
    public void updateMovieInfo(MovieInfo movieInfo, InputStream coverStream, InputStream backdropStream) throws IOException {
        Directory movieCoverDirectory = fileSystemService.getDirectory("/images/covers/movies");
        boolean coverImageExists = movieCoverDirectory.fileExists(movieInfo.getId() + "-cover.jpg");
        boolean backdropImageExists = movieCoverDirectory.fileExists(movieInfo.getId() + "-backdrop.jpg");
        movieInfo.setCover(coverImageExists);
        movieInfo.setBackdrop(backdropImageExists);

        if (coverStream != null) {
            FsFile fsFile = movieCoverDirectory.getFile(movieInfo.getId() + "-cover.jpg");
            StreamUtil.transferByteArray(coverStream, fsFile.getOutputStream(), 1024);
            movieInfo.setCover(true);
        }

        if (backdropStream != null) {
            FsFile fsFile = movieCoverDirectory.getFile(movieInfo.getId() + "-backdrop.jpg");
            StreamUtil.transferByteArray(backdropStream, fsFile.getOutputStream(), 1024);
            movieInfo.setBackdrop(true);
        }

        movieInfoDAO.update(movieInfo);
    }

    public int getMovieCount(List<Category> searchCategories, int maxAgeDays, List<Integer> userExCatIds,
                             String titleSearch, String genreSearch, String actorsSearch, String directorSearch, String yearSearch) {
        List<Integer> categoryIds = searchCategories.stream().map(Category::getId).collect(Collectors.toList());
        List<Long> imdbIds = releaseDAO.getDistinctImdbIds(categoryIds, maxAgeDays, userExCatIds);
        long movieCount = movieInfoDAO.getMovieCount(imdbIds, titleSearch, genreSearch, actorsSearch, directorSearch, yearSearch);

        return Math.toIntExact(movieCount);
    }

    public List<MovieInfo> getMovies(List<Category> searchCategories, int maxAgeDays, List<Integer> userExCatIds,
                             String titleSearch, String genreSearch, String actorsSearch, String directorSearch, String yearSearch,
                                     int offset, int pageSize, String orderByField, boolean descending) {
        List<Integer> categoryIds = searchCategories.stream().map(Category::getId).collect(Collectors.toList());
        List<Long> imdbIds = releaseDAO.getDistinctImdbIds(categoryIds, maxAgeDays, userExCatIds);
        List<MovieInfo> movieInfoList = movieInfoDAO.getMovies(imdbIds, titleSearch, genreSearch, actorsSearch,
                directorSearch, yearSearch, offset, pageSize, orderByField, descending);

        return movieInfoList;
    }

    public ReleaseDAO getReleaseDAO() {
        return releaseDAO;
    }

    public void setReleaseDAO(ReleaseDAO releaseDAO) {
        this.releaseDAO = releaseDAO;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public void setCategoryDAO(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public GoogleSearchService getGoogleSearchService() {
        return googleSearchService;
    }

    public void setGoogleSearchService(GoogleSearchService googleSearchService) {
        this.googleSearchService = googleSearchService;
    }

    public FileSystemService getFileSystemService() {
        return fileSystemService;
    }

    public void setFileSystemService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    public MovieInfoDAO getMovieInfoDAO() {
        return movieInfoDAO;
    }

    public void setMovieInfoDAO(MovieInfoDAO movieInfoDAO) {
        this.movieInfoDAO = movieInfoDAO;
    }

    public TmdbService getTmdbService() {
        return tmdbService;
    }

    public void setTmdbService(TmdbService tmdbService) {
        this.tmdbService = tmdbService;
    }
}

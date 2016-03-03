/*
 * Copyright (c) 2015. Sten Martinez
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
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.util.ParseUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.newsj.ws.GoogleSearchResponse;
import net.longfalcon.newsj.ws.GoogleSearchResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 2:17 PM
 */
@Service
public class MovieService {
    private static final Log _log = LogFactory.getLog(MovieService.class);

    private ReleaseDAO releaseDAO;
    private CategoryDAO categoryDAO;
    private GoogleSearchService googleSearchService;

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
}

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

package net.longfalcon.newsj;

import net.longfalcon.newsj.fs.FileSystemService;
import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.TvRage;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.persistence.TvRageDAO;
import net.longfalcon.newsj.service.TraktService;
import net.longfalcon.newsj.util.ArrayUtil;
import net.longfalcon.newsj.util.DateUtil;
import net.longfalcon.newsj.util.StreamUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.newsj.ws.trakt.TraktEpisodeResult;
import net.longfalcon.newsj.ws.trakt.TraktIdSet;
import net.longfalcon.newsj.ws.trakt.TraktImage;
import net.longfalcon.newsj.ws.trakt.TraktImages;
import net.longfalcon.newsj.ws.trakt.TraktResult;
import net.longfalcon.newsj.ws.trakt.TraktShowResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NOTE: TVRAGE is no longer a viable API to use. this whole api uses trakt, but tvrage ids. migrate to trakt/tvdb ids
 * in the future.
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 2:54 PM
 */
@Service
public class TVRageService {
    private static final Log _log = LogFactory.getLog(TVRageService.class);

    private ReleaseDAO releaseDAO;
    private CategoryDAO categoryDAO;
    private TvRageDAO tvRageDAO;

    private TraktService traktService;
    private FileSystemService fileSystemService;

    @Transactional(propagation = Propagation.SUPPORTS)
    public void processTvReleases(boolean lookupTvRage) {
        List<Category> movieCats = categoryDAO.findByParentId(CategoryService.CAT_PARENT_TV);
        List<Integer> ids = new ArrayList<>();
        for (Category category : movieCats) {
            ids.add(category.getId());
        }
        List<Release> releases = releaseDAO.findReleasesByRageIdAndCategoryId(-1, ids);

        if (!releases.isEmpty()) {
            _log.info("Processing tv for " + releases.size() + " releases");
            if (lookupTvRage) {
                _log.info("Looking up tv info from the web");
            }
        }

        for (Release release : releases) {
            ShowInfo showInfo = parseNameEpSeason(release.getName());
            if (showInfo != null) {
                // update release with season, ep, and airdate info (if available) from releasetitle
                updateEpInfo(showInfo, release);

                // find the trakt ID
                long traktId = 0;
                String cleanName = showInfo.getCleanName();
                try {
                    _log.info("looking up " + cleanName + " in db");
                    traktId = getByTitle(cleanName);
                } catch (Exception e) {
                    _log.error("unable to find tvinfo for " + cleanName + " - " + e.toString(), e);
                }

                if (traktId < 0 && traktId > -2 && lookupTvRage) {
                    // if it doesnt exist locally and lookups are allowed lets try to get it
                    _log.info("didnt find trakt id for \""+ cleanName +"\" in local db, checking web...");

                    traktId = getTraktMatch(showInfo);

                    if (traktId > 0) {
                        updateRageInfo(traktId, showInfo, release);
                    } else {
                        // no match
                        //add to tvrage with rageID = -2 and cleanName title only
                        _log.info("no trakt data found for " + cleanName + " - adding placeholder");
                        TvRage tvRage = new TvRage();
                        tvRage.setRageId(-2);
                        tvRage.setTraktId((long) -2);
                        tvRage.setReleaseTitle(cleanName);
                        tvRage.setCreateDate(new Date());
                        tvRage.setDescription("");
                        tvRageDAO.update(tvRage);
                        release.setRageId(tvRage.getId());
                    }
                } else if (traktId > 0) {
                    if (lookupTvRage) {
                        // update airdate and ep title
                        updateEpisodeInfo(traktId, showInfo.getSeason(), showInfo.getEpisode(), release);
                    }
                    TvRage tvRage = tvRageDAO.findByTvTraktId(traktId);
                    release.setRageId(tvRage.getId());

                }

            } else {
                // not a tv episode, so set rageid to n/a
                release.setRageId((long) -2);
            }

            releaseDAO.updateRelease(release);
        }
    }

    public void rebuildTvInfo(TvRage tvRage) {
        try {
            TraktResult traktResult = getTraktMatch(tvRage.getReleaseTitle());
            if (traktResult != null) {
                TraktShowResult showResult = traktResult.getShowResult();

                _log.info("found tvinfo for " + tvRage.getReleaseTitle());

                tvRage.setTraktId(showResult.getIds().getTrakt());
                tvRage.setRageId(showResult.getIds().getTvrage());
                tvRage.setTvdbId(showResult.getIds().getTvdb());
                tvRage.setReleaseTitle(showResult.getTitle());
                TraktImages traktImages = showResult.getTraktImages();
                try {
                    if (traktImages != null) {
                        TraktImage posterImage = traktImages.getPoster();
                        if (posterImage != null) {
                            String posterUrl = posterImage.getMedium();
                            if (ValidatorUtil.isNotNull(posterUrl)) {
                                Directory tempDir = fileSystemService.getDirectory("/temp");
                                File tempFile = tempDir.getTempFile("image_" + String.valueOf(System.currentTimeMillis()));
                                FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                                URL url = new URL(posterUrl);
                                URLConnection urlConnection = url.openConnection();
                                urlConnection.setRequestProperty("User-Agent", "Java");
                                StreamUtil.transferByteArray(urlConnection.getInputStream(), fileOutputStream, 1024);
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                InputStream tempFileInputStream = new FileInputStream(tempFile);
                                StreamUtil.transferByteArray(tempFileInputStream, byteArrayOutputStream, 1024);
                                tvRage.setImgData(byteArrayOutputStream.toByteArray());
                            }
                        }
                    }
                } catch (Exception e) {
                    _log.warn("unable to add poster image for " + tvRage.getReleaseTitle(), e);
                }

                tvRage.setDescription(showResult.getOverview());
                tvRageDAO.update(tvRage);
            }
        } catch (Exception e) {
            _log.error(e);
        }
    }

    // restrict to using trakt id since trakt only allows trakt id or imdb to search by episode
    private void updateEpisodeInfo(long traktShowId, String season, String episode, Release release) {
        try {
            int seasonNumber = 0;
            if (season.startsWith("S") || season.startsWith("s")) {
                seasonNumber = Integer.parseInt(season.substring(1));
            } else {
                seasonNumber = Integer.parseInt(season);
            }
            int episodeNumber = 0;
            if (episode.startsWith("E") || episode.startsWith("e")) {
                episodeNumber = Integer.parseInt(episode.substring(1));
            } else {
                episodeNumber = Integer.parseInt(episode);
            }
            TraktEpisodeResult traktEpisodeResult = null;
            try {
                traktEpisodeResult = traktService.getEpisode(traktShowId, seasonNumber, episodeNumber);
            } catch (Exception e) {
                _log.error("error fetching episode data for " + release.getSearchName() + " - " + e.toString());
                _log.debug("",e);
            }
            if (traktEpisodeResult != null) {
                release.setTvAirDate(traktEpisodeResult.getFirstAired());
                release.setTvTitle(traktEpisodeResult.getTitle());
                release.setSeason(String.valueOf(traktEpisodeResult.getSeason()));
                release.setEpisode(String.valueOf(traktEpisodeResult.getNumber()));
            }
        } catch (NumberFormatException e) {
            _log.warn("Parse error: " + e.toString() + " for release " + release.getSearchName(), e);
        }
    }

    private TvRage updateRageInfo(long traktId, ShowInfo showInfo, Release release) {
        // find existing tvRage info
        String season = showInfo.getSeason();
        String episode = showInfo.getEpisode();

        _log.info("looking for existing tvinfo with trakt id " + traktId);
        TvRage tvRage = tvRageDAO.findByTvTraktId(traktId);

        if (tvRage == null) {
            // try looking by name
            tvRage = getTvInfoByTitle(showInfo.getCleanName());
        }
        if (tvRage == null) {
            tvRage = new TvRage();
            tvRage.setCreateDate(new Date());
        }
        //add
        _log.info("no existing tvinfo with trakt Id " + traktId + ", adding from Trakt");
        TraktResult[] traktResults = new TraktResult[0];
        try {
            traktResults = traktService.searchShowByTraktId(traktId);
        } catch (Exception e) {
            _log.error("error fetching show data for " + release.getSearchName() + " - " + e.toString());
            _log.debug("", e);
        }
        if (traktResults.length > 0) {
            TraktResult firstResult = traktResults[0];
            TraktShowResult showResult = firstResult.getShowResult();

            _log.info("found tvinfo for " + showInfo.getCleanName());


            tvRage.setTraktId(showResult.getIds().getTrakt());
            tvRage.setRageId(showResult.getIds().getTvrage());
            tvRage.setTvdbId(showResult.getIds().getTvdb());
            tvRage.setReleaseTitle(showResult.getTitle());
            // TODO: tvRage.setImgData();

            tvRage.setDescription(showResult.getOverview());
            tvRage.setCountry(showInfo.getCountry());
            tvRageDAO.update(tvRage);
            updateEpisodeInfo(showResult.getIds().getTrakt(), season, episode, release);
            release.setRageId(tvRage.getId());
        } else {
            // trakt id that we found before, didnt work this time. this is highly likely to a connectivity or other
            // issue that does not relate the release.
            _log.warn("no results from Trakt found for trakt id " + traktId);
        }

        releaseDAO.updateRelease(release);
        return tvRage;
    }

    private long getRageMatch(ShowInfo showInfo) {
        String cleanName = showInfo.getCleanName();

        try {
            TraktResult[] traktResults = new TraktResult[0];
            try {
                traktResults = traktService.searchTvShowByName(cleanName.toLowerCase());
            } catch (Exception e) {
                _log.error("error fetching show data for " + cleanName + " - " + e.toString());
                _log.debug("", e);
            }
            if (traktResults.length > 0) {
                TraktResult firstResult = traktResults[0];
                if (firstResult.getScore() > 50) {
                    // probably the best match, we wont bother looking elsewhere.
                    if (firstResult.getShowResult() != null) {
                        _log.info("found +50% match: " + firstResult.getShowResult().getTitle());
                        return getRageIdFromTraktResultsSafe(firstResult);
                    } else {
                        return -2;// error
                    }
                } else if (traktResults.length > 1) {
                    String firstResultName = firstResult.getShowResult().getTitle();
                    String secondResultName = traktResults[1].getShowResult().getTitle();
                    double firstSimilarityScore = StringUtils.getJaroWinklerDistance(cleanName, firstResultName);
                    double secondSimilarityScore = StringUtils.getJaroWinklerDistance(cleanName, secondResultName);
                    if (firstSimilarityScore > secondSimilarityScore) {
                        return getRageIdFromTraktResultsSafe(firstResult);
                    } else {
                        return getRageIdFromTraktResultsSafe(traktResults[1]);
                    }
                }
                return getRageIdFromTraktResultsSafe(firstResult);
            }
            _log.warn("no trakt show found for name " + cleanName);
            return -2; // not found
        } catch (Exception e) {
            _log.error(e.toString(), e);
            return -2; // error
        }
    }

    private long getTraktMatch(ShowInfo showInfo) {
        String cleanName = showInfo.getCleanName();
        _log.info("searching trakt for show " + cleanName);
        try {
            TraktResult[] traktResults = new TraktResult[0];
            try {
                traktResults = traktService.searchTvShowByName(cleanName.toLowerCase());
            } catch (Exception e) {
                _log.error("error fetching show data for " + cleanName + " - " + e.toString());
                _log.debug("", e);
            }
            if (traktResults.length > 0) {
                TraktResult firstResult = traktResults[0];
                if (firstResult.getScore() > 50) {
                    // probably the best match, we wont bother looking elsewhere.
                    if (firstResult.getShowResult() != null) {
                        _log.info("found +50% match: " + firstResult.getShowResult().getTitle());
                        return getTraktIdFromTraktResultsSafe(firstResult);
                    } else {
                        return -2;// error
                    }
                } else if (traktResults.length > 1) {
                    String firstResultName = firstResult.getShowResult().getTitle();
                    String secondResultName = traktResults[1].getShowResult().getTitle();
                    double firstSimilarityScore = StringUtils.getJaroWinklerDistance(cleanName, firstResultName);
                    double secondSimilarityScore = StringUtils.getJaroWinklerDistance(cleanName, secondResultName);
                    if (firstSimilarityScore > secondSimilarityScore) {
                        return getTraktIdFromTraktResultsSafe(firstResult);
                    } else {
                        return getTraktIdFromTraktResultsSafe(traktResults[1]);
                    }
                }
                return getTraktIdFromTraktResultsSafe(firstResult);
            }
            return -1; // not found
        } catch (Exception e) {
            _log.error(e.toString(), e);
            return -2; // error
        }
    }

    private TraktResult getTraktMatch(String showname) {
        _log.info("searching trakt for show " + showname);
        try {
            TraktResult[] traktResults = new TraktResult[0];
            try {
                traktResults = traktService.searchTvShowByName(showname.toLowerCase());
            } catch (Exception e) {
                _log.error("error fetching show data for " + showname + " - " + e.toString());
                _log.debug("", e);
            }
            if (traktResults.length > 0) {
                TraktResult firstResult = traktResults[0];
                if (firstResult.getScore() > 50) {
                    // probably the best match, we wont bother looking elsewhere.
                    if (firstResult.getShowResult() != null) {
                        _log.info("found +50% match: " + firstResult.getShowResult().getTitle());
                        return firstResult;
                    } else {
                        return null;// error
                    }
                } else if (traktResults.length > 1) {
                    String firstResultName = firstResult.getShowResult().getTitle();
                    String secondResultName = traktResults[1].getShowResult().getTitle();
                    double firstSimilarityScore = StringUtils.getJaroWinklerDistance(showname, firstResultName);
                    double secondSimilarityScore = StringUtils.getJaroWinklerDistance(showname, secondResultName);
                    if (firstSimilarityScore > secondSimilarityScore) {
                        return firstResult;
                    } else {
                        return traktResults[1];
                    }
                }
                return firstResult;
            }
            return null; // not found
        } catch (Exception e) {
            _log.error(e.toString(), e);
            return null; // error
        }
    }

    private long getRageIdFromTraktResultsSafe(TraktResult traktResult) {
        try {
            if (traktResult == null) {
                return -2;
            }

            TraktShowResult showResult = traktResult.getShowResult();

            if (showResult == null) {
                return -2;
            }

            TraktIdSet traktIdSet = showResult.getIds();

            if (traktIdSet == null) {
                return -2;
            }

            long rageId = traktIdSet.getTvrage();
            _log.info("found rage id: " + rageId);
            return rageId;
        } catch (NullPointerException e) {
            _log.error(e.toString(), e);
            return -2;
        }
    }

    private long getTraktIdFromTraktResultsSafe(TraktResult traktResult) {
        try {
            if (traktResult == null) {
                return -2;
            }

            TraktShowResult showResult = traktResult.getShowResult();

            if (showResult == null) {
                return -2;
            }

            TraktIdSet traktIdSet = showResult.getIds();

            if (traktIdSet == null) {
                return -2;
            }

            long traktId = traktIdSet.getTrakt();
            _log.info("found trakt id: " + traktId);
            return traktId;
        } catch (NullPointerException e) {
            _log.error(e.toString(), e);
            return -2;
        }
    }

    private long getByTitle(String cleanName) {
        long traktId = -1;
        // check if we already have an entry for this show
        TvRage tvRage = tvRageDAO.findByReleaseTitle(cleanName);
        if (tvRage != null) {
            if (tvRage.getTraktId() != null) {
                traktId = tvRage.getTraktId();
            }
            // TODO: update trakt ID for shows we dont have an id for
        } else {
            cleanName = cleanName.replace(" and ", " & ");
            tvRage = tvRageDAO.findByReleaseTitle(cleanName);
            if (tvRage != null) {
                traktId = tvRage.getTraktId();
            }
        }
        return traktId;
    }

    private TvRage getTvInfoByTitle(String cleanName) {
        // check if we already have an entry for this show
        TvRage tvRage = tvRageDAO.findByReleaseTitle(cleanName);
        if (tvRage != null) {
            return tvRage;
        } else {
            cleanName = cleanName.replace(" and ", " & ");
            tvRage = tvRageDAO.findByReleaseTitle(cleanName);
            if (tvRage != null) {
                return tvRage;
            }
        }
        return null;
    }

    private void updateEpInfo(ShowInfo showInfo, Release release) {
        String airDateString = showInfo.getAirDate();
        Date airDate = null;
        if (ValidatorUtil.isNotNull(airDateString)) {
            DateTime airDateTime = DateUtil.parseAirDate(airDateString);
            if (airDateTime != null) {
                airDate = airDateTime.toDate();
            }
        }

        release.setSeriesFull(showInfo.getSeriesFull());
        release.setSeason(showInfo.getSeason());
        release.setEpisode(showInfo.getEpisode());
        release.setTvAirDate(airDate);
        releaseDAO.updateRelease(release);
    }

    private ShowInfo parseNameEpSeason(String name) {

        ShowInfo showInfo = new ShowInfo();
        //S01E01-E02
        //S01E01-02
        Pattern pattern = Pattern.compile("^(.*?)[\\. ]s(\\d{1,2})\\.?e(\\d{1,3})(?:\\-e?|\\-?e)(\\d{1,3})\\.", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason(matcher.group(2));
            showInfo.setEpisode(matcher.group(3) + "," + matcher.group(4));
        }
        //S01E0102 - lame no delimit numbering, regex would collide if there was ever 1000 ep season
        pattern = Pattern.compile("^(.*?)[\\. ]s(\\d{2})\\.?e(\\d{2})(\\d{2})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason(matcher.group(2));
            showInfo.setEpisode(matcher.group(3) + "," + matcher.group(4));
        }
        //S01E01
        //S01.E01
        pattern = Pattern.compile("^(.*?)[\\. ]s(\\d{1,2})\\.?e(\\d{1,3})\\.?", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason(matcher.group(2));
            showInfo.setEpisode(matcher.group(3));
        }
        //S01
        pattern = Pattern.compile("^(.*?)[\\. ]s(\\d{1,2})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason(matcher.group(2));
            showInfo.setEpisode("all");
        }
        //S01D1
        //S1D1
        pattern = Pattern.compile("^(.*?)[\\. ]s(\\d{1,2})d(\\d{1})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason(matcher.group(2));
            showInfo.setEpisode(matcher.group(3));
        }
        //1x01
        pattern = Pattern.compile("^(.*?)[\\. ](\\d{1,2})x(\\d{1,3})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason(matcher.group(2));
            showInfo.setEpisode(matcher.group(3));
        }
        //2009.01.01
        //2009-01-01
        pattern = Pattern.compile("^(.*?)[\\. ](19|20)(\\d{2})[\\.\\-](\\d{2})[\\.\\-](\\d{2})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason(matcher.group(2)+matcher.group(3));
            showInfo.setEpisode(matcher.group(4)+"/"+matcher.group(5));
            showInfo.setAirDate(matcher.group(2)+matcher.group(3)+"-"+matcher.group(4)+"-"+matcher.group(5)); //yy-m-d
        }
        //01.01.2009
        pattern = Pattern.compile("^(.*?)[\\. ](\\d{2}).(\\d{2})\\.(19|20)(\\d{2})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason(matcher.group(4)+matcher.group(5));
            showInfo.setEpisode(matcher.group(2)+"/"+matcher.group(3));
            showInfo.setAirDate(matcher.group(4)+matcher.group(5)+"-"+matcher.group(2)+"-"+matcher.group(3)); //yy-m-d
        }
        //01.01.09
        pattern = Pattern.compile("^(.*?)[\\. ](\\d{2}).(\\d{2})\\.(\\d{2})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            String yearString = matcher.group(4);
            int year = Integer.parseInt(yearString);
            showInfo.setSeason((year <= 99 && year > 15) ? "19"+matcher.group(4) : "20"+matcher.group(4));
            showInfo.setEpisode(matcher.group(2) + "/" + matcher.group(3));
            showInfo.setAirDate(showInfo.getSeason()+"-"+matcher.group(2)+"-"+matcher.group(3)); //yy-m-d
        }
        //2009.E01
        pattern = Pattern.compile("^(.*?)[\\. ]20(\\d{2})\\.e(\\d{1,3})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason("20"+matcher.group(2));
            showInfo.setEpisode(matcher.group(3));
        }
        //2009.Part1
        pattern = Pattern.compile("^(.*?)[\\. ]20(\\d{2})\\.Part(\\d{1,2})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason("20"+matcher.group(2));
            showInfo.setEpisode(matcher.group(3));
        }
        //Part1/Pt1
        pattern = Pattern.compile("^(.*?)[\\. ](?:Part|Pt)\\.?(\\d{1,2})\\.", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason("1");
            showInfo.setEpisode(matcher.group(2));
        }
        //e.g. The.Pacific.Pt.VI.HDTV.XviD-XII / Part.IV
        pattern = Pattern.compile("^(.*?)[\\. ](?:Part|Pt)\\.?([ivx]+)", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason("1");
            String epLow = matcher.group(2).toLowerCase();
            int e = 0;
            switch(epLow) {
                case "i": e = 1; break;
                case "ii": e = 2; break;
                case "iii": e = 3; break;
                case "iv": e = 4; break;
                case "v": e = 5; break;
                case "vi": e = 6; break;
                case "vii": e = 7; break;
                case "viii": e = 8; break;
                case "ix": e = 9; break;
                case "x": e = 10; break;
                case "xi": e = 11; break;
                case "xii": e = 12; break;
                case "xiii": e = 13; break;
                case "xiv": e = 14; break;
                case "xv": e = 15; break;
                case "xvi": e = 16; break;
                case "xvii": e = 17; break;
                case "xviii": e = 18; break;
                case "xix": e = 19; break;
                case "xx": e = 20; break;
            }
            showInfo.setEpisode(String.valueOf(e));
        }
        //e.g. Band.Of.Brothers.EP06.Bastogne.DVDRiP.XviD-DEiTY
        pattern = Pattern.compile("^(.*?)[\\. ]EP?\\.?(\\d{1,3})", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason("1");
            showInfo.setEpisode(matcher.group(2));
        }
        //Season.1
        pattern = Pattern.compile("^(.*?)[\\. ]Seasons?\\.?(\\d{1,2})", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            showInfo.setName(matcher.group(1));
            showInfo.setSeason(matcher.group(2));
            showInfo.setEpisode("all");
        }

        if (ValidatorUtil.isNotNull(showInfo.name)) {
            //country or origin matching
            pattern = Pattern.compile("[\\._ ](US|UK|AU|NZ|CA|NL|Canada|Australia|America)");
            matcher = pattern.matcher(showInfo.getName());
            if (matcher.find()) {
                String group1 = matcher.group(1).toLowerCase();
                if (group1.equals("canada")) {
                    showInfo.setCountry("CA");
                } else if (group1.equals("australia") || group1.equals("aus")) {
                    showInfo.setCountry("AU");
                } else if (group1.equals("america")) {
                    showInfo.setCountry("US");
                } else {
                    showInfo.setCountry(group1.toUpperCase());
                }
            }

            //clean show name
            showInfo.setCleanName(cleanName(showInfo.getName()));

            //check for dates instead of seasons
            if (showInfo.getSeason().length() == 4) {
                showInfo.setSeriesFull(showInfo.getSeason() + "/" + showInfo.getEpisode());

            } else {
                //get year if present (not for releases with dates as seasons)
                pattern = Pattern.compile("[\\._ ](19|20)(\\d{2})", Pattern.CASE_INSENSITIVE);
                matcher = pattern.matcher(name);
                if (matcher.find()) {
                    showInfo.setYear(matcher.group(1)+matcher.group(2));
                }

                if (ValidatorUtil.isNumeric(showInfo.getSeason())) {
                    int season = Integer.parseInt(showInfo.getSeason());
                    showInfo.setSeason(String.format("S%02d", season));
                }

                if (showInfo.getEpisode().contains(",")) {
                    String[] episodesStrings = showInfo.getEpisode().split(",");
                    for (int i = 0; i < episodesStrings.length; i++) {
                        String episodeString = episodesStrings[i];
                        if (ValidatorUtil.isNumeric(episodeString)) {
                            int episode = Integer.parseInt(episodeString);
                            episodesStrings[i] = String.format("E%02d", episode);
                        }
                        showInfo.setEpisode(ArrayUtil.stringify(episodesStrings, ","));
                    }
                } else {
                    String episodeString = showInfo.getEpisode();
                    if (ValidatorUtil.isNumeric(episodeString)) {
                        int episode = Integer.parseInt(episodeString);
                        showInfo.setEpisode(String.format("E%02d", episode));
                    }
                }
                showInfo.setSeriesFull(showInfo.getSeason()+showInfo.getEpisode());
            }

            return showInfo;
        }

        return null;
    }

    private String cleanName(String name) {
        name = name.replace(".", " ");
        name = name.replace("_", " ");
        name = name.replace("&", "and");
        Pattern pattern = Pattern.compile("^(history|discovery) channel", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        name = matcher.replaceAll("");

        name = name.replaceAll("(\\\\|:|!|\\\"|#|\\*|'|,|\\(|\\)|\\?)", "");

        pattern = Pattern.compile("\\s{2,}", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        name = matcher.replaceAll(" ");
        return name.trim();
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

    public TvRageDAO getTvRageDAO() {
        return tvRageDAO;
    }

    public void setTvRageDAO(TvRageDAO tvRageDAO) {
        this.tvRageDAO = tvRageDAO;
    }

    public TraktService getTraktService() {
        return traktService;
    }

    public void setTraktService(TraktService traktService) {
        this.traktService = traktService;
    }

    public FileSystemService getFileSystemService() {
        return fileSystemService;
    }

    public void setFileSystemService(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    private class ShowInfo{
        private String name;
        private String season;
        private String episode;
        private String seriesFull;
        private String airDate;
        private String country;
        private String year;
        private String cleanName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSeason() {
            return season;
        }

        public void setSeason(String season) {
            this.season = season;
        }

        public String getEpisode() {
            return episode;
        }

        public void setEpisode(String episode) {
            this.episode = episode;
        }

        public String getSeriesFull() {
            return seriesFull;
        }

        public void setSeriesFull(String seriesFull) {
            this.seriesFull = seriesFull;
        }

        public String getAirDate() {
            return airDate;
        }

        public void setAirDate(String airDate) {
            this.airDate = airDate;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getCleanName() {
            return cleanName;
        }

        public void setCleanName(String cleanName) {
            this.cleanName = cleanName;
        }
    }
}

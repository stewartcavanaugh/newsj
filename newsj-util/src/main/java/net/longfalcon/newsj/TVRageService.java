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

import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.TvRage;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.persistence.TvRageDAO;
import net.longfalcon.newsj.util.ArrayUtil;
import net.longfalcon.newsj.util.DateUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NOTE: TVRAGE is down, so this is purely to do regex processing until a solution with TVDB is created
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
                _log.info("Looking up tv rage from the web");
            }
        }

        for (Release release : releases) {
            ShowInfo showInfo = parseNameEpSeason(release.getName());
            if (showInfo != null) {
                // update release with season, ep, and airdate info (if available) from releasetitle
                updateEpInfo(showInfo, release);

                // find the rageID
                long rageId = getByTitle(showInfo.getCleanName());

                if (rageId == 0 && lookupTvRage) {
                    // if it doesnt exist locally and lookups are allowed lets try to get it
                    if (_log.isDebugEnabled()) {
                        _log.debug("didnt find rageid for \""+showInfo.getCleanName()+"\" in local db, checking web...");
                    }

                    //rageId = getRageMatch(showInfo);
                    if (rageId > 0) {
                        //updateRageInfo(rageId, showInfo, release);
                    } else {
                        // no match
                        //add to tvrage with rageID = -2 and cleanName title only
                       // add(-2, showInfo.getCleanName(), "", "", "", "");
                    }
                } else if (rageId > 0) {
                    if (lookupTvRage) {
                        //getEpisodeInfo(rageId, showInfo.getSeason(), showInfo.getEpisode());
                        // update airdate and ep title
                    }

                }

                release.setRageId(rageId);

            } else {
                // not a tv episode, so set rageid to n/a
                release.setRageId((long) -2);
            }


            releaseDAO.updateRelease(release);
        }
    }

    private long getByTitle(String cleanName) {
        long rageId = -1;
        // check if we already have an entry for this show
        TvRage tvRage = tvRageDAO.findByReleaseTitle(cleanName);
        if (tvRage != null) {
            rageId = tvRage.getRageId();
        } else {
            cleanName = cleanName.replace(" and ", " & ");
            tvRage = tvRageDAO.findByReleaseTitle(cleanName);
            if (tvRage != null) {
                rageId = tvRage.getRageId();
            }
        }
        return rageId;
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

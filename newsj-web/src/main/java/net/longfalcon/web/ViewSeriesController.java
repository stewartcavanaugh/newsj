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

package net.longfalcon.web;

import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.TvRage;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.TvRageDAO;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 5/5/16
 * Time: 4:33 PM
 */
@Controller
public class ViewSeriesController extends BaseController {
    @Autowired
    TvRageDAO tvRageDAO;

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    Releases releaseService;

    @RequestMapping("series/{seriesId}")
    public String seriesView(@PathVariable(value = "seriesId")long seriesId,
                             @RequestParam(value = "t", required = false)Integer categoryId,
                             Model model) throws NoSuchResourceException {
        // series is tvinfo id, not a rageid
        TvRage tvRage = tvRageDAO.findById(seriesId);
        if (tvRage == null) {
            throw new NoSuchResourceException("TvRage with id " + seriesId + " not found");
        }

        List<Integer> categoryIdlist = new ArrayList<>();
        Category category = null;
        String catName = "";
        if (categoryId != null) {
            category = categoryDAO.findByCategoryId(categoryId);
            if (category == null) {
                throw new NoSuchResourceException("Category with id " + categoryId + " not found");
            }
            catName = category.getTitle();
            categoryIdlist.add(categoryId);
        }

        List<Release> releaseList = releaseService.findByRageIdAndCategoryId(seriesId, categoryIdlist);
        Map<String,Map<String,List<Release>>> sortedMap = new HashMap<>();
        // get all seasons, then get all episodes.
        for ( Release release : releaseList) {
            String season = release.getSeason();
            String episode = release.getEpisode();
            Map<String,List<Release>> seasonMap = sortedMap.get(season);
            if (seasonMap == null) {
                seasonMap = new HashMap<>();
            }
            List<Release> episodeReleaseList = seasonMap.get(episode);
            if (episodeReleaseList == null) {
                episodeReleaseList = new ArrayList<>();
            }
            episodeReleaseList.add(release);
            seasonMap.put(episode, episodeReleaseList);
            sortedMap.put(season,seasonMap);
        }


        model.addAttribute("tvInfo", tvRage);
        model.addAttribute("catName", catName);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("sortedMap", sortedMap);
        model.addAttribute("pageMetaTitle", "View TV Series " + tvRage.getReleaseTitle());
        model.addAttribute("pageMetaKeywords", "view,series,tv,show,description,details");
        model.addAttribute("pageMetaDescription", "View " + tvRage.getReleaseTitle() + " Series");

        return "view-series";
    }
}

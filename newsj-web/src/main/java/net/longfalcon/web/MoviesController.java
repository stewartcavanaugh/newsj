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

import net.longfalcon.newsj.CategoryService;
import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.MovieInfo;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.CategoryDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.persistence.UserExCatDAO;
import net.longfalcon.newsj.service.MovieService;
import net.longfalcon.newsj.util.ArrayUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Sten Martinez
 * Date: 3/29/16
 * Time: 4:50 PM
 */
@Controller
public class MoviesController extends BaseController {

    @Autowired
    CategoryDAO categoryDAO;

    @Autowired
    UserExCatDAO userExCatDAO;

    @Autowired
    MovieService movieService;

    @Autowired
    ReleaseDAO releaseDAO;

    @Autowired
    Releases releaseService;

    private static Set<String> movieFieldNames = new HashSet<>();
    static {
        movieFieldNames.add("title");//'title_asc', 'title_desc',
        movieFieldNames.add("rating");// 'rating_asc', 'rating_desc',
        movieFieldNames.add("year");// 'year_asc', 'year_desc',
    }

    @RequestMapping("/movies")
    public String moviesView(@RequestParam(value = "t", required = false) Integer categoryId,
                             @RequestParam(value = "title", required = false) String titleSearch,
                             @RequestParam(value = "genre", required = false) String genreSearch,
                             @RequestParam(value = "actors", required = false) String actorsSearch,
                             @RequestParam(value = "director", required = false) String directorSearch,
                             @RequestParam(value = "year", required = false) String yearSearch,
                             @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                             @RequestParam(value = "ob", required = false) String orderBy,
                             Model model) {

        String catName = "All";
        if (categoryId == null) {
            categoryId = CategoryService.CAT_PARENT_MOVIE;
        }

        List<Category> movieCategories = categoryDAO.findByParentId(CategoryService.CAT_PARENT_MOVIE);
        List<Category> searchCategories = new ArrayList<>();
        if (CategoryService.CAT_PARENT_MOVIE == categoryId) {
            searchCategories.addAll(movieCategories);
            Category movieCategory = categoryDAO.findByCategoryId(CategoryService.CAT_PARENT_MOVIE);
            catName = movieCategory.getTitle();
        } else if (ValidatorUtil.isNotNull(categoryId)) {
            Category category = categoryDAO.findByCategoryId(categoryId);
            if ((category != null) &&
                    ((category.getParentId() != null) && (category.getParentId() == CategoryService.CAT_PARENT_MOVIE))) {
                searchCategories.add(category);
                catName = category.getTitle();
            }
        }

        User user = userDAO.findByUserId(getUserId());
        if (user != null) {
            model.addAttribute("lastVisit",user.getLastLogin());
        }
        List<Integer> userExCats = userExCatDAO.getUserExCatIds(getUserId());

        int pagerTotalItems = movieService.getMovieCount(searchCategories, -1, userExCats, titleSearch, genreSearch, actorsSearch, directorSearch, yearSearch);

        List<MovieInfo> movieInfoList = null;
        if (ValidatorUtil.isNull(orderBy)) {
            movieInfoList = movieService.getMovies(searchCategories, -1, userExCats, titleSearch, genreSearch,
                    actorsSearch, directorSearch, yearSearch, offset, PAGE_SIZE, "updateDate", true);
        } else {
            String orderByField = getOrderByProperty(orderBy);
            boolean desc = getOrderByOrder(orderBy);
            movieInfoList = movieService.getMovies(searchCategories, -1, userExCats, titleSearch, genreSearch,
                    actorsSearch, directorSearch, yearSearch, offset, PAGE_SIZE, orderByField, desc);
        }

        Map<Long, List<Release>> movieReleaseInfoMap = new HashMap<>();
        for (MovieInfo movieInfo : movieInfoList) {
            List<Release> releases = releaseService.findByImdbId(movieInfo.getImdbId());
            movieReleaseInfoMap.put(movieInfo.getId(), releases);
        }

        long[] ratings = ArrayUtil.range(1,9);

        List<String> genreList = movieService.getGenres();

        long[] years = ArrayUtil.range(1903, new DateTime().getYear()+1);
        ArrayUtils.reverse(years);

        model.addAttribute("catName", catName);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("movieInfoList", movieInfoList);
        model.addAttribute("movieReleaseInfoMap", movieReleaseInfoMap);
        model.addAttribute("titleSearch", titleSearch);
        model.addAttribute("genreSearch", genreSearch);
        model.addAttribute("actorsSearch", actorsSearch);
        model.addAttribute("directorSearch", directorSearch);
        model.addAttribute("yearSearch",yearSearch);
        model.addAttribute("ratingSearch", "");//todo: add rating info once movies are properly fetched.
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("ratings", ratings);
        model.addAttribute("genreList", genreList);
        model.addAttribute("years", years);
        model.addAttribute("movieCats", movieCategories);
        return "movies";
    }

    private String getOrderByProperty(String orderByParam) {
        for (String fieldName : movieFieldNames) {
            if (orderByParam.toLowerCase().startsWith(fieldName.toLowerCase())) {
                return fieldName;
            }
        }
        return "";
    }

    private boolean getOrderByOrder(String orderByParam) {
        return orderByParam.endsWith("_desc");
    }
}

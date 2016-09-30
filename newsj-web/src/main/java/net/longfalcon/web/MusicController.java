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
import net.longfalcon.newsj.model.Genre;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.persistence.GenreDAO;
import net.longfalcon.newsj.persistence.UserExCatDAO;
import net.longfalcon.newsj.util.ArrayUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 5/11/16
 * Time: 10:49 AM
 * TODO: make functional post 0.3
 */
@Controller
public class MusicController extends BaseController {

    @Autowired
    GenreDAO genreDAO;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserExCatDAO userExCatDAO;

    @Autowired
    Releases releases;

    @RequestMapping("/music")
    public String musicView(@RequestParam(value = "artist", required = false) String artistSearch,
                            @RequestParam(value = "title", required = false) String titleSearch,
                            @RequestParam(value = "genreId", required = false) Integer genreIdSearch,
                            @RequestParam(value = "year", required = false) Integer yearSearch,
                            @RequestParam(value = "categoryId", required = false) Integer categoryIdSearch,
                            Model model) {

        if (categoryIdSearch == null || categoryIdSearch == 0) {
            categoryIdSearch = CategoryService.CAT_PARENT_MUSIC;
        }

        List<Integer> userExCats = userExCatDAO.getUserExCatIds(getUserId());

        List<Genre> genreList = genreDAO.getGenres(CategoryService.CAT_PARENT_MUSIC);
        List<Category> categoryList = categoryService.getSubCategories(new HashSet<>(userExCats), CategoryService.CAT_PARENT_MUSIC);
        List<Integer> yearList = ArrayUtil.rangeListInt(1950, new DateTime().getYear()+1);
        List<Release> releaseList = new ArrayList<>();

        // needs pager setup

        model.addAttribute("artist", artistSearch);
        model.addAttribute("title", titleSearch);
        model.addAttribute("genreId", genreIdSearch);
        model.addAttribute("year", yearSearch);
        model.addAttribute("categoryId", categoryIdSearch);
        model.addAttribute("genreList", genreList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("years", yearList);
        return "music";
    }

    @Override
    public String getPageMetaKeywords() {
        return "browse,nzb,albums,description,details";
    }

    @Override
    public String getPageMetaDescription() {
        return "Browse for Albums";
    }

    @Override
    public String getPageMetaTitle() {
        return "Browse Albums";
    }
}

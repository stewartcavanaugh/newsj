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

import net.longfalcon.newsj.model.MovieInfo;
import net.longfalcon.newsj.persistence.MovieInfoDAO;
import net.longfalcon.newsj.service.MovieService;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/15/16
 * Time: 4:58 PM
 */
@Controller
@SessionAttributes({"movieInfo"})
public class AdminMovieController extends BaseController {

    @Autowired
    MovieService movieService;

    @Autowired
    MovieInfoDAO movieInfoDAO;

    @RequestMapping("/admin/movie-list")
    public String listMovieView(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                Model model) {
        title = "Movie List";

        if (offset == null) {
            offset = 0;
        }

        int pagerTotalItems = Math.toIntExact(movieInfoDAO.countMovieInfos());
        List<MovieInfo> movieInfoList = movieInfoDAO.getMovieInfos(offset, PAGE_SIZE);

        model.addAttribute("title", title);
        model.addAttribute("movieInfoList", movieInfoList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        return "admin/movie-list";
    }

    @RequestMapping("/admin/movie-edit")
    public String editMovieView(@RequestParam(value = "id", required = false, defaultValue = "0") Long id,
                                Model model) throws NoSuchResourceException {
        title =  "Movie Edit";
        MovieInfo movieInfo;
        if (id != null && id > 0) {
            movieInfo = movieInfoDAO.findByMovieInfoId(id);
            if (movieInfo == null) {
                throw new NoSuchResourceException();
            }
        } else {
            throw new NoSuchResourceException();
        }
        model.addAttribute("title", title);
        model.addAttribute("movieInfo", movieInfo);
        return "admin/movie-edit";
    }

    @RequestMapping(value = "/admin/movie-edit", method = RequestMethod.POST)
    public View editMoviePost(@ModelAttribute("movieInfo") MovieInfo movieInfo,
                              @RequestParam("coverImage") MultipartFile cover,
                              @RequestParam("backdropImage") MultipartFile backdrop,
                              Model model) throws IOException {

        InputStream coverStream = null;
        InputStream backdropStream = null;

        if (cover != null && !cover.isEmpty()) {
            coverStream = cover.getInputStream();
        }

        if (backdrop != null && !backdrop.isEmpty()) {
            backdropStream = backdrop.getInputStream();
        }

        movieService.updateMovieInfo(movieInfo, coverStream, backdropStream);

        return safeRedirect("/admin/movie-list");
    }

    @RequestMapping("/admin/movie-add")
    public String addMovieView(Model model) {
        title =  "Movie Edit";

        model.addAttribute("title", title);
        return "admin/movie-add";
    }

    @RequestMapping(value = "/admin/movie-add", method = RequestMethod.POST)
    public View addMoviePost(@RequestParam(value = "imdbId") Integer imdbId,
                             Model model) {

        movieService.addMovieInfo(imdbId);

        return safeRedirect("/admin/movie-list");
    }
}

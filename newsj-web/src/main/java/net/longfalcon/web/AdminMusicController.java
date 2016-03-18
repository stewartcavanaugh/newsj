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
import net.longfalcon.newsj.service.MusicService;
import net.longfalcon.newsj.model.Genre;
import net.longfalcon.newsj.model.MusicInfo;
import net.longfalcon.newsj.persistence.GenreDAO;
import net.longfalcon.newsj.persistence.MusicInfoDAO;
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
 * Date: 3/18/16
 * Time: 2:36 PM
 */
@Controller
@SessionAttributes({"musicInfo"})
public class AdminMusicController extends BaseController {

    @Autowired
    GenreDAO genreDAO;

    @Autowired
    MusicInfoDAO musicInfoDAO;

    @Autowired
    MusicService musicService;

    @RequestMapping("/admin/music-list")
    public String listMusicView(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                Model model) {
        title = "Music List";

        if (offset == null ) {
            offset = 0;
        }

        List<MusicInfo> musicInfoList = musicInfoDAO.getMusicInfos(offset, PAGE_SIZE);
        int pagerTotalItems = Math.toIntExact(musicInfoDAO.countMusicInfos());

        model.addAttribute("title", title);
        model.addAttribute("musicInfoList", musicInfoList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        return "/admin/music-list";
    }

    @RequestMapping("/admin/music-edit")
    public String editMusicInfoView(@RequestParam(value = "id", required = false, defaultValue = "0")Long id,
                                    Model model) throws NoSuchResourceException {
        MusicInfo musicInfo;
        if (id != null && id > 0) {
            musicInfo = musicInfoDAO.findByMusicInfoId(id);
            if ( musicInfo == null) {
                throw new NoSuchResourceException();
            }
            title = "Music Edit";
        } else {
            throw new NoSuchResourceException();
        }

        List<Genre> genreList = genreDAO.getGenres(CategoryService.CAT_PARENT_MUSIC);

        model.addAttribute("title", title);
        model.addAttribute("genreList", genreList);
        model.addAttribute("musicInfo", musicInfo);
        return "/admin/music-edit";
    }

    @RequestMapping(value = "/admin/music-edit", method = RequestMethod.POST)
    public View editMusicInfoPost(@ModelAttribute("musicInfo")MusicInfo musicInfo,
                                  @RequestParam("coverImage") MultipartFile cover) throws IOException {

        InputStream coverStream = null;

        if (cover != null && !cover.isEmpty()) {
            coverStream = cover.getInputStream();
        }

        musicService.updateMusicInfo(musicInfo, coverStream);

        return safeRedirect("/admin/music-list");
    }
}

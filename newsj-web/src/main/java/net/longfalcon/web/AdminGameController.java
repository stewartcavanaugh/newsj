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
import net.longfalcon.newsj.model.ConsoleInfo;
import net.longfalcon.newsj.model.Genre;
import net.longfalcon.newsj.persistence.ConsoleInfoDAO;
import net.longfalcon.newsj.persistence.GenreDAO;
import net.longfalcon.newsj.service.GameService;
import net.longfalcon.view.ConsoleInfoVO;
import net.longfalcon.web.exception.FileUploadException;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 3/10/16
 * Time: 2:25 PM
 */
@Controller
@SessionAttributes("consoleInfo")
public class AdminGameController extends BaseController {

    @Autowired
    ConsoleInfoDAO consoleInfoDAO;

    @Autowired
    GenreDAO genreDAO;

    @Autowired
    GameService gameService;

    private static final Log _log = LogFactory.getLog(AdminGameController.class);

    @RequestMapping("/admin/console-list")
    public String listConsoleView(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                  Model model) {
        title = "Console List";

        int pagerTotalItems = Math.toIntExact(consoleInfoDAO.countConsoleInfos());
        List<ConsoleInfo> consoleInfoList = consoleInfoDAO.getConsoleInfos(offset, PAGE_SIZE);

        model.addAttribute("title",title);
        model.addAttribute("consoleInfoList",consoleInfoList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        return "admin/console-list";
    }

    @RequestMapping("/admin/console-edit")
    public String editConsoleView(@RequestParam(value = "id", required = false, defaultValue = "0")Long id, Model model) throws NoSuchResourceException {
        title = "Console Edit";

        ConsoleInfo consoleInfo;
        if (id != null && id > 0) {
            consoleInfo = consoleInfoDAO.findByConsoleInfoId(id);
            if (consoleInfo == null) {
                throw new NoSuchResourceException();
            }
        } else {
            consoleInfo = new ConsoleInfo();
        }

        List<Genre> genres = genreDAO.getGenres(CategoryService.CAT_PARENT_GAME);
        Map<Long,String> genreMap = new HashMap<>();
        for(Genre genre : genres) {
            genreMap.put(genre.getId(), genre.getTitle());
        }

        model.addAttribute("title",title);
        model.addAttribute("genreMap", genreMap);
        model.addAttribute("consoleInfo", new ConsoleInfoVO(consoleInfo));
        return "admin/console-edit";
    }

    @RequestMapping(value = "/admin/console-edit", method = RequestMethod.POST)
    public View editConsolePost(@ModelAttribute("consoleInfo") ConsoleInfoVO consoleInfoVo, Model model) throws NoSuchResourceException, FileUploadException {
        MultipartFile file = consoleInfoVo.getMultipartFile();
        long id;
        InputStream coverStream = null;
        try {
            if (file != null && !file.isEmpty()) {
                coverStream = file.getInputStream();
            }
            ConsoleInfo consoleInfo = populateConsoleInfo(consoleInfoVo);
            gameService.updateConsoleInfo(consoleInfo, coverStream);
            id = consoleInfo.getId();
        } catch (Exception e) {
            _log.error(e,e);
            throw new FileUploadException();
        }

        return safeRedirect("/admin/console-edit?id=" + id);
    }

    private ConsoleInfo populateConsoleInfo(ConsoleInfoVO consoleInfoVo) {
        ConsoleInfo consoleInfo = new ConsoleInfo();
        consoleInfo.setId(consoleInfoVo.getId());
        consoleInfo.setTitle(consoleInfoVo.getTitle());
        consoleInfo.setAsin(consoleInfoVo.getAsin());
        consoleInfo.setUrl(consoleInfoVo.getUrl());
        consoleInfo.setSalesRank(consoleInfoVo.getSalesRank());
        consoleInfo.setPlatform(consoleInfoVo.getPlatform());
        consoleInfo.setPublisher(consoleInfoVo.getPublisher());
        consoleInfo.setGenreId(consoleInfoVo.getGenreId());
        consoleInfo.setEsrb(consoleInfoVo.getEsrb());
        consoleInfo.setReleaseDate(consoleInfoVo.getReleaseDate());
        consoleInfo.setReview(consoleInfoVo.getReview());
        consoleInfo.setCover(consoleInfoVo.isCover());
        consoleInfo.setCreateDate(consoleInfoVo.getCreateDate());
        consoleInfo.setUpdateDate(consoleInfoVo.getUpdateDate());
        return consoleInfo;
    }
}

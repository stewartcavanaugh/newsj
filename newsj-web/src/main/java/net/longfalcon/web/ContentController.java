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

import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.model.Content;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * User: Sten Martinez
 * Date: 11/7/15
 * Time: 12:10 AM
 */
@Controller
public class ContentController extends BaseController {
    private static final Log _log = LogFactory.getLog(ContentController.class);

    @RequestMapping("/")
    public String content(Model model, HttpSession httpSession) {
        Content indexContent = contentService.getIndex();
        model.addAttribute("content", indexContent);
        return "content";
    }

    @RequestMapping("/content/{id:\\d+}/-/**")
    public String viewContentUrl(@PathVariable long id, HttpServletRequest httpServletRequest, Model model) throws NoSuchResourceException {
        Content indexContent = contentService.getContent(id);
        String url = httpServletRequest.getRequestURI();
        String[] urlParts = url.split("/-");
        String urlPart = urlParts[1];
        if (urlPart.equals(indexContent.getUrl())) {
            model.addAttribute("content", indexContent);
        } else {
           throw new NoSuchResourceException();
        }


        return "content";
    }

    @RequestMapping("/content/{id:\\d+}")
    public String viewContent(@PathVariable long id, Model model) {
        Content indexContent = contentService.getContent(id);
        model.addAttribute("content", indexContent);

        return "content";
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String getTitle() {
        return title;
    }

}

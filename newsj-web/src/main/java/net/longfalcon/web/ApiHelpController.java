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

import net.longfalcon.newsj.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: longfalcon
 * Date: 3/12/2016
 * Time: 7:35 PM
 */
@Controller
public class ApiHelpController extends BaseController {

    @RequestMapping("/apihelp")
    public String apiHelpView(Model model) {
        title = "Api";
        setPageMetaTitle("Api Help Topics");
        setPageMetaKeywords("view,nzb,api,details,help,json,rss,atom");
        setPageMetaDescription("View description of the site Nzb Api.");

        User user = userDAO.findByUserId(getUserId());

        model.addAttribute("title", title);
        model.addAttribute("userData", user);
        return "apihelp";
    }
}

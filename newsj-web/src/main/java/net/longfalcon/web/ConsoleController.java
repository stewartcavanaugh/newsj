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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * User: Sten Martinez
 * Date: 6/23/16
 * Time: 11:45 AM
 * TODO post-0.3
 */
@Controller
public class ConsoleController extends BaseController {

    @RequestMapping("/console")
    public String consoleView(Model model) {
        return "console";
    }

    @Override
    public String getPageMetaKeywords() {
        return "browse,nzb,console,games,description,details";
    }

    @Override
    public String getPageMetaDescription() {
        return "Browse for Games";
    }

    @Override
    public String getPageMetaTitle() {
        return "Browse Console";
    }
}

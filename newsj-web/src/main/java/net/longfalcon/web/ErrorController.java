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

import javax.servlet.http.HttpServletRequest;

/**
 * User: Sten Martinez
 * Date: 11/10/15
 * Time: 5:46 PM
 */
@Controller
public class ErrorController {

    @RequestMapping("/error")
    public String handle(HttpServletRequest request, Model model) {

        model.addAttribute("status", request.getAttribute("javax.servlet.error.status_code"));
        model.addAttribute("reason", request.getAttribute("javax.servlet.error.message"));
        model.addAttribute("request_uri", request.getAttribute("javax.servlet.error.request_uri"));

        return "error";
    }

}

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * User: Sten Martinez
 * Date: 11/11/15
 * Time: 4:31 PM
 */
@Controller
public class LogoutController extends BaseController {
    private static final Log _log = LogFactory.getLog(LogoutController.class);

    @RequestMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, HttpSession httpSession, Model model) {
        try {
            httpSession.invalidate();
            Cookie uidCookie = new Cookie("uid", "");
            uidCookie.setMaxAge(-1);
            Cookie idhCookie = new Cookie("idh", "");
            idhCookie.setMaxAge(-1);
            httpServletResponse.addCookie(uidCookie);
            httpServletResponse.addCookie(idhCookie);
        } catch (Exception e) {
            _log.error(e, e);
        }
        model.asMap().clear();
        return "redirect:/";
    }
}

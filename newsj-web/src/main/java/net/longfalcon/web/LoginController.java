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
import net.longfalcon.newsj.service.UserService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.LoginVO;
import net.longfalcon.web.auth.PasswordHash;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * User: Sten Martinez
 * Date: 11/11/15
 * Time: 12:25 PM
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    private static final Log _log = LogFactory.getLog(LoginController.class);

    @RequestMapping(method = RequestMethod.POST)
    public String loginPost(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest httpServletRequest, HttpSession httpSession, HttpServletResponse httpServletResponse, Model model) {
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();
        String host = httpServletRequest.getRemoteHost();
        boolean rememberMe = loginVO.isRememberMe();
        loginVO.setPassword("");
        model.addAttribute("loginForm", loginVO);

        if (ValidatorUtil.isNull(username) || ValidatorUtil.isNull(password)) {
            model.addAttribute("error", "Please enter your username and password.");
            return "login";
        }

        User user = userDAO.findByUsername(username);

        if (user == null) {
            model.addAttribute("error", "Incorrect username or password.");
            return "login";
        }

        if (user.getRole() == UserService.ROLE_DISABLED) {
            model.addAttribute("error", "Your account has been disabled.");
            return "login";
        }

        String correctHash = user.getPassword();
        try {
            if (PasswordHash.validatePassword(password, correctHash)) {
                login(httpSession, httpServletResponse, host, rememberMe, user);
                String redirect = loginVO.getRedirect();
                if (ValidatorUtil.isNotNull(redirect)) {
                    _log.error("I dont know if redirects work yet, redirect: " + redirect);
                    // return "redirect:"+redirect;
                }
                model.asMap().clear();
                return "redirect:/";
            } else {
                model.addAttribute("error", "Incorrect username or password.");
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            _log.error(e, e);
            model.addAttribute("error", "An Unexpected error has occurred.");

        }

        return "login";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String loginView(Model model) {
        LoginVO loginVO = new LoginVO();
        model.addAttribute("loginForm", loginVO);
        model.addAttribute("rememberMe", false);
        return "login";
    }
}

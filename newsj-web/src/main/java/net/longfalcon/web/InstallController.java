/*
 * Copyright (c) 2015. Sten Martinez
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

import net.longfalcon.newsj.service.UserService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.UserRegistrationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashSet;
import java.util.Set;

/**
 * User: Sten Martinez
 * Date: 11/11/15
 * Time: 10:53 PM
 */
@Controller
public class InstallController {

    @Autowired
    UserService userService;

    public static final String INSTALL_STEP_1_TITLE = "Setup Admin User";
    public static final String INSTALL_STEP_2_TITLE = "NZB File Path";

    @RequestMapping(name="/install", method = RequestMethod.GET)
    public String startInstallProcess(Model model) {
        String title = INSTALL_STEP_1_TITLE;
        UserRegistrationVO registerForm = new UserRegistrationVO();

        model.addAttribute("title", title);
        model.addAttribute("registerForm", registerForm);
        return "install";
    }

    @RequestMapping(name="/install", method = RequestMethod.POST)
    public String installStepOne(@ModelAttribute("registerForm") UserRegistrationVO registerForm, Model model) {
        String title = "";
        Set<String> errorSet = new HashSet<>();
        String username = registerForm.getUserName();
        String password = registerForm.getPassword();
        String email = registerForm.getEmail();

        if (ValidatorUtil.isNull(username)) {
            errorSet.add("Invalid username");
        }

        if (ValidatorUtil.isNull(password)) {
            errorSet.add("Invalid password");
        }

        if (ValidatorUtil.isNull(email)) {
            errorSet.add("Invalid email");
        }

        if (errorSet.size() > 0) {
            title = INSTALL_STEP_1_TITLE;
            model.addAttribute("title", title);
            model.addAttribute("registerForm", registerForm);
            model.addAttribute("errorSet", errorSet);
            return "install";
        }

        userService.add(username, password, email, UserService.ROLE_ADMIN, null, UserService.DEFAULT_INVITES, null);

        title = INSTALL_STEP_2_TITLE;
        model.addAttribute("title", title);
        return "install2";
    }

    @RequestMapping(name = "/install2", method = RequestMethod.POST)
    public String installStepTwo(Model model) {

        model.asMap().clear();
        return "redirect:/";
    }
}

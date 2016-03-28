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
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.service.InstallerService;
import net.longfalcon.newsj.service.UserService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.InstallerVO;
import net.longfalcon.view.UserRegistrationVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
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

    @Autowired
    Config config;

    @Autowired
    UserDAO userDAO;

    @Autowired
    InstallerService installerService;

    public static final String INSTALL_STEP_1_TITLE = "Setup Admin User";
    public static final String INSTALL_STEP_2_TITLE = "NZB File Path";

    private static final Log _log = LogFactory.getLog(InstallController.class);

    @RequestMapping(value="/install", method = RequestMethod.GET)
    public String startInstallProcess(Model model) {

        // check if site is already configured
        if (userDAO.countUsers() > 0) {
            if (!config.getDefaultSite().getNzbPath().equals("/your/path/to/nzbs/")) {
                model.asMap().clear();
                return "redirect:/";
            }
            model.asMap().clear();
            return "redirect:/install2";
        }

        String title = INSTALL_STEP_1_TITLE;
        UserRegistrationVO registerForm = new UserRegistrationVO();

        model.addAttribute("title", title);
        model.addAttribute("registerForm", registerForm);
        return "install";
    }

    @RequestMapping(value="/install", method = RequestMethod.POST)
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
        model.addAttribute("nzbPath", config.getNzbFileLocation());
        model.addAttribute("installerForm", new InstallerVO());
        return "install2";
    }

    @RequestMapping(value = "/install2", method = RequestMethod.GET)
    public String showInstallStepTwo(Model model) {
        model.addAttribute("title", INSTALL_STEP_2_TITLE);
        model.addAttribute("nzbPath", config.getNzbFileLocation());
        model.addAttribute("installerForm", new InstallerVO());
        return "install2";
    }

    @RequestMapping(value = "/install2", method = RequestMethod.POST)
    public String installStepTwo(@ModelAttribute("installerForm") InstallerVO installerForm, Model model) {

        String nzbFileLocation = installerForm.getNzbFileLocation();
        Set<String> errorSet = new HashSet<>();

        File file = new File(nzbFileLocation);
        if (file.exists()) {
            if (!file.canRead() || !file.canWrite()) {
                errorSet.add(String.format("Location %s is not readable and/or writeable.", nzbFileLocation));
            }
            if (!file.isDirectory()) {
                errorSet.add(String.format("Location %s is not a directory.", nzbFileLocation));
            }
        } else {
            errorSet.add(String.format("Location %s does not exist.", nzbFileLocation));
        }

        if (errorSet.size() > 0) {
            model.addAttribute("title", INSTALL_STEP_2_TITLE);
            model.addAttribute("nzbPath", nzbFileLocation);
            model.addAttribute("installerForm", installerForm);
            model.addAttribute("errorSet", errorSet);
            return "install2";
        }

        try {
            installerService.initializeNzbStorage(nzbFileLocation);
        } catch (Exception e) {
            _log.error(e, e);
            errorSet.add(e.toString());
            model.addAttribute("title", INSTALL_STEP_2_TITLE);
            model.addAttribute("nzbPath", nzbFileLocation);
            model.addAttribute("installerForm", installerForm);
            model.addAttribute("errorSet", errorSet);
            return "install2";
        }

        model.asMap().clear();
        return "redirect:/";
    }
}

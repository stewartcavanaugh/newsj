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

import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.model.UserInvite;
import net.longfalcon.newsj.service.SiteService;
import net.longfalcon.newsj.service.UserInviteService;
import net.longfalcon.newsj.service.UserService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.UserRegistrationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

/**
 * User: Sten Martinez
 * Date: 11/10/15
 * Time: 5:35 PM
 */
@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {

    @Autowired
    UserInviteService userInviteService;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String registerView(Model model, @RequestParam(value = "invite", required = false) String inviteCode) {
        if (isLoggedIn()) {
            model.asMap().clear();
            return "redirect:/";
        }

        boolean showRegister = true;
        Site site = config.getDefaultSite();

        if (site.getRegisterStatus() == SiteService.REGISTER_STATUS_CLOSED) {
            model.addAttribute("error", "Registrations are currently disabled.");
            showRegister = false;
        } else if ((site.getRegisterStatus() == SiteService.REGISTER_STATUS_INVITE) && ValidatorUtil.isNull(inviteCode)) {
            model.addAttribute("error", "Registrations are currently invite only.");
            showRegister = false;
        }

        model.addAttribute("showregister", showRegister);

        if (showRegister) {
            UserRegistrationVO registerForm = new UserRegistrationVO();

            if (ValidatorUtil.isNotNull(inviteCode)) {
                //
                // see if its a valid invite
                //
                UserInvite userInvite = userInviteService.getInvite(inviteCode);
                if (userInvite == null) {
                    model.addAttribute("error", String.format("Bad or invite code older than %d days.", UserService.DEFAULT_INVITE_EXPIRY_DAYS));
                    model.addAttribute("showregister", false);
                } else {
                    // set invite code
                    model.addAttribute("invitecode", userInvite.getGuid());
                    registerForm.setInviteCode(userInvite.getGuid());
                }
            }
            model.addAttribute("registerForm", registerForm);
        }

        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submitPost( @ModelAttribute("registerForm") UserRegistrationVO userRegistrationVO,
                              HttpServletRequest httpServletRequest,
                              HttpSession httpSession,
                              HttpServletResponse httpServletResponse,
                              Model model) {

        boolean showRegister = true;
        Site site = config.getDefaultSite();

        if (site.getRegisterStatus() == SiteService.REGISTER_STATUS_CLOSED) {
            model.addAttribute("error", "Registrations are currently disabled.");
            showRegister = false;

        } else if ((site.getRegisterStatus() == SiteService.REGISTER_STATUS_INVITE) && ValidatorUtil.isNull(userRegistrationVO.getInviteCode())) {
            model.addAttribute("error", "Registrations are currently invite only.");
            showRegister = false;
        }

        model.addAttribute("showregister", showRegister);

        model.addAttribute("registerForm", userRegistrationVO);
        //
        // check uname/email isnt in use, password valid.
        // if all good create new user account and redirect back to home page
        //
        if (!Objects.equals(userRegistrationVO.getPassword(), userRegistrationVO.getConfirmPassword())) {
            model.addAttribute("error", "Password Mismatch");
        } else {
            long userId = userService.signup(userRegistrationVO);
            if (userId > 0) {
                User user = userDAO.findByUserId(userId);
                login(httpSession, httpServletResponse, httpServletRequest.getRemoteHost(), false, user);
                model.asMap().clear();
                return "redirect:/";
            } else {
                switch ((int) userId) {
                    case UserService.ERR_SIGNUP_BADUNAME:
                        model.addAttribute("error", "Your username must be longer than three characters.");
                        break;
                    case UserService.ERR_SIGNUP_BADPASS:
                        model.addAttribute("error", "Your password must be longer than five characters.");
                        break;
                    case UserService.ERR_SIGNUP_BADEMAIL:
                        model.addAttribute("error", "Your email is not a valid format.");
                        break;
                    case UserService.ERR_SIGNUP_UNAMEINUSE:
                        model.addAttribute("error", "Sorry, the username is already taken.");
                        break;
                    case UserService.ERR_SIGNUP_EMAILINUSE:
                        model.addAttribute("error", "Sorry, the email is already in use.");
                        break;
                    case UserService.ERR_SIGNUP_BADINVITECODE:
                        model.addAttribute("error", "Sorry, the invite code is old or has been used.");
                        break;
                    default:
                        model.addAttribute("error", "Failed to register.");
                        break;
                }
            }
        }

        return "register";
    }
}

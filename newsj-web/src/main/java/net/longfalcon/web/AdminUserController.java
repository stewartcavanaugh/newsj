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
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.service.UserService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.EditUserVO;
import net.longfalcon.view.UserRegistrationVO;
import net.longfalcon.web.auth.PasswordHash;
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
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User: Sten Martinez
 * Date: 3/2/16
 * Time: 9:54 PM
 */
@Controller
@SessionAttributes({"user"})
public class AdminUserController extends BaseController {

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserService userService;

    private static Set<String> userFieldNames;
    private static final Log _log = LogFactory.getLog(AdminUserController.class);
    static {
        userFieldNames = new HashSet<>();
        userFieldNames.add("username");
        userFieldNames.add("email");
        userFieldNames.add("host");
        userFieldNames.add("grabs");
        userFieldNames.add("apiAccess");
        userFieldNames.add("createDate");
        userFieldNames.add("lastLogin");
        userFieldNames.add("role");
    }

    @RequestMapping("/admin/user-list")
    public String userListView(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                               @RequestParam(value = "ob", required = false) String orderBy,
                               Model model) {
        if (offset == null) {
            offset = 0;
        }
        title = "User List";

        List<User> userList = new ArrayList<>();
        int pagerTotalItems = Math.toIntExact(userDAO.countUsers());

        if (ValidatorUtil.isNull(orderBy)) {
            userList = userDAO.getUsers(offset, PAGE_SIZE);
        } else {
            String propertyName = getOrderByProperty(orderBy);
            boolean descending = getOrderByOrder(orderBy);
            if (ValidatorUtil.isNotNull(propertyName)) {
                userList = userDAO.getUsers(offset, PAGE_SIZE, propertyName, descending);
            }
        }

        model.addAttribute("title", title);
        model.addAttribute("userList", userList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        model.addAttribute("orderBy", orderBy);
        return "admin/user-list";
    }

    @RequestMapping("/admin/user-edit")
    public String userEditView(@RequestParam(value = "id", required = false)Long id, Model model) {
        EditUserVO userVO;

        if (ValidatorUtil.isNull(id)) {
            // add user
            title = "Add User";
            userVO = new EditUserVO();
            userVO.setRole(UserService.ROLE_USER);
            userVO.setInvites(UserService.DEFAULT_INVITES);
            userVO.setMovieView(true);
        } else {
            title = "Edit User";
            User user = userDAO.findByUserId(id);
            if (user == null) {
                user = new User();
            }
            userVO = new EditUserVO(user);
        }
        Map<Integer,String> roleMap = new HashMap<>();
        roleMap.put(UserService.ROLE_ADMIN, "Admin");
        roleMap.put(UserService.ROLE_USER, "User");
        roleMap.put(UserService.ROLE_DISABLED, "Disabled");

        model.addAttribute("title", title);
        model.addAttribute("user", userVO);
        model.addAttribute("roleMap", roleMap);
        return "admin/user-edit";
    }

    @RequestMapping(value = "/admin/user-edit", method = RequestMethod.POST)
    public View userEditPost(@ModelAttribute("user")EditUserVO userVO, HttpSession httpSession, Model model) {
        long returnCode = 0;
        User user;
        if (userVO.getUserId() > 0) {
            user = userDAO.findByUserId(userVO.getUserId());
            user.setUsername(userVO.getUsername());
            user.setEmail(userVO.getEmail());
            String newPassword = userVO.getPassword();
            if ( ValidatorUtil.isNotNull(newPassword)) {
                try {
                    user.setPassword(PasswordHash.createHash(newPassword));
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    _log.error(e);
                }
            }
            user.setMovieView(userVO.isMovieView() ? 1 : 0);
            user.setMusicView(userVO.isMusicView() ? 1 : 0);
            user.setConsoleView(userVO.isConsoleView() ? 1 : 0);
            user.setGrabs(userVO.getGrabs());
            user.setInvites(userVO.getInvites());
            user.setRole(userVO.getRole());
            returnCode = userService.update(user); //TODO: there is a bug here with saving after an error
        } else {
            UserRegistrationVO userRegistrationVO = new UserRegistrationVO();
            userRegistrationVO.setUserName(userVO.getUsername());
            userRegistrationVO.setEmail(userVO.getEmail());
            userRegistrationVO.setPassword(userVO.getPassword());
            returnCode = userService.signup(userRegistrationVO);
        }

        if (returnCode < 1) {
            String error = "";
            if (returnCode == UserService.ERR_SIGNUP_BADUNAME) {
                error += "Bad username. Try a better one.<br/>";
            } else if (returnCode == UserService.ERR_SIGNUP_BADPASS) {
                error += "Bad password. Try a longer one.<br/>";

            } else if (returnCode == UserService.ERR_SIGNUP_BADEMAIL) {
                error += "Bad email.<br/>";

            } else if (returnCode == UserService.ERR_SIGNUP_UNAMEINUSE) {
                error += "Username in use.<br/>";

            } else if (returnCode == UserService.ERR_SIGNUP_EMAILINUSE) {
                error += "Email in use.<br/>";

            } else {
                error += "Unknown save error. Please contact site administrator.<br/>";
            }
            httpSession.setAttribute("errors", error);
        } else {
            return safeRedirect("/admin/user-edit?id="+ returnCode);
        }
        return safeRedirect("/admin/user-edit?id="+ userVO.getUserId());
    }

    @RequestMapping(value = "/admin/user-delete", method = RequestMethod.POST)
    public View userDeletePost(@RequestParam(value = "id")Long id, Model model) {
        User user = userDAO.findByUserId(id);
        if (user == null) {
            _log.error("userId="+id+" does not exist.");
        } else {
            userDAO.delete(user);
        }
        Integer offset = (Integer) model.asMap().get("pagerOffset");
        String orderBy = (String) model.asMap().get("orderBy");

        return safeRedirect("/admin/user-list?ob="+orderBy+"&offset="+offset);
    }

    private String getOrderByProperty(String orderByParam) {
        for (String fieldName : userFieldNames) {
            if (orderByParam.toLowerCase().startsWith(fieldName.toLowerCase())) {
                return fieldName;
            }
        }
        return "";
    }

    private boolean getOrderByOrder(String orderByParam) {
        return orderByParam.endsWith("_desc");
    }
}

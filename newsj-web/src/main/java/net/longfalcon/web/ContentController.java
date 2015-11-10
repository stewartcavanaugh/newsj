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

import net.longfalcon.SessionKeys;
import net.longfalcon.newsj.CategoryService;
import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.model.Content;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.service.ContentService;
import net.longfalcon.newsj.service.MenuService;
import net.longfalcon.newsj.service.UserService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.UserData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * User: Sten Martinez
 * Date: 11/7/15
 * Time: 12:10 AM
 */
@Controller
public class ContentController {
    private static final Log _log = LogFactory.getLog(ContentController.class);

    protected String title = "";
    protected String pageMetaTitle = "";
    protected String pageMetaKeywords = "";
    protected String pageMetaDescription = "";

    @Autowired
    Config config;

    @Autowired
    UserDAO userDAO;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MenuService menuService;

    @Autowired
    ContentService contentService;

    @RequestMapping("/")
    public String content(Model model, HttpSession httpSession) {
        Content indexContent = contentService.getIndex();
        model.addAttribute("content", indexContent);
        return "content";
    }

    @ModelAttribute
    public void populateModel(Model model, HttpSession httpSession, NativeWebRequest nativeWebRequest,
                              @RequestParam(value = "t", required = false, defaultValue = "") String categoryId,
                              @RequestParam(value = "id", required = false, defaultValue = "Enter keywords") String searchStr) {
        model.addAttribute("site", config.getDefaultSite());
        model.addAttribute("version", Config.getReleaseVersion());
        model.addAttribute("pageMetaTitle", getPageMetaTitle());
        model.addAttribute("pageMetaKeywords", getPageMetaKeywords());
        model.addAttribute("pageMetaDescription", getPageMetaDescription());
        model.addAttribute("year", config.getYear());

        boolean sabEnabled = false;
        long userId = 0;
        int roleId = UserService.ROLE_GUEST;
        String userIdString = null;
        String rssToken = null;
        try {
            userIdString = String.valueOf(httpSession.getAttribute(SessionKeys.USER_ID));
            rssToken = "";
            if (ValidatorUtil.isNotNull(userIdString) && ValidatorUtil.isNumeric(userIdString)) {
                userId = Long.parseLong(userIdString);
                User user = userDAO.findByUserId(userId);
                if (user != null) {
                    rssToken = user.getRssToken();
                    roleId = user.getRole();
                    model.addAttribute("loggedIn", true);
                    model.addAttribute("userData", _getUserData(user));
                    String sabCookieName = "sabnzbd_" + String.valueOf(userId) + "__apikey";
                    sabEnabled = isCookieSet(sabCookieName, nativeWebRequest);
                } else {
                    model.addAttribute("loggedIn", false);
                }
            }
        } catch (Exception e) {
            _log.error(e);
        }

        model.addAttribute("categories", categoryService.getCategoriesForMenu(userId));
        model.addAttribute("rssToken", rssToken);
        model.addAttribute("userId", userIdString);
        model.addAttribute("headerMenuCat", categoryId);
        model.addAttribute("headerMenuSearch", searchStr);
        model.addAttribute("menuItems", menuService.getMenuItems(roleId, sabEnabled));
        model.addAttribute("usefulcontentlist", contentService.getForMenuByTypeAndRole(ContentService.TYPEUSEFUL, roleId));
        model.addAttribute("articlecontentlist", contentService.getForMenuByTypeAndRole(ContentService.TYPEARTICLE, roleId));
    }

    protected boolean isCookieSet(String cookieName, NativeWebRequest nativeWebRequest) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) nativeWebRequest.getNativeRequest();
            Cookie[] cookies = httpServletRequest.getCookies();
            for (Cookie cookie : cookies) {
                if ( cookie.getName().equals(cookieName) && ValidatorUtil.isNotNull(cookie.getValue())) {
                    return true;
                }
            }
        } catch (Exception e) {
            _log.error(e);
        }
        return false;
    }

    private UserData _getUserData(User user) {
        return new UserData(user);
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

    public String getPageMetaKeywords() {
        return pageMetaKeywords;
    }

    public String getPageMetaDescription() {
        return pageMetaDescription;
    }

    public String getTitle() {
        return title;
    }

    public String getPageMetaTitle() {
        return pageMetaTitle;
    }
}

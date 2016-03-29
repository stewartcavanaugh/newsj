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

import net.longfalcon.SessionKeys;
import net.longfalcon.newsj.CategoryService;
import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.service.ContentService;
import net.longfalcon.newsj.service.MenuService;
import net.longfalcon.newsj.service.UserService;
import net.longfalcon.newsj.util.EncodingUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.UserData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 11/10/15
 * Time: 5:23 PM
 */
public class BaseController {
    private static final Log _log = LogFactory.getLog(BaseController.class);
    protected static int PAGE_SIZE = 50;
    protected static final Map<Integer, String> YES_NO_MAP = new HashMap<>();
    static {
        YES_NO_MAP.put(1, "Yes");
        YES_NO_MAP.put(0, "No");
    }

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
    private long userId;
    private int roleId;
    private boolean isLoggedIn;
    private boolean isAdmin;

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
        isLoggedIn = false;
        isAdmin = false;
        userId = 0;
        roleId = UserService.ROLE_GUEST;
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
                    isLoggedIn = true;
                    if (roleId == UserService.ROLE_ADMIN) {
                        isAdmin = true;
                    }
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
        model.addAttribute("loggedIn", isLoggedIn);
        model.addAttribute("isAdmin", isAdmin);
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

    protected void login(HttpSession httpSession, HttpServletResponse httpServletResponse, String host, boolean rememberMe, User user) {
        long userId = user.getId();
        httpSession.setAttribute(SessionKeys.USER_ID, userId);
        httpSession.setAttribute(SessionKeys.UID, userId);

        if (config.getDefaultSite().getStoreUserIps() != 1) {
            host = "";
        }

        updateSiteAccessed(user, host);

        if (rememberMe) {
            setCookies(user, httpServletResponse);
        }
    }

    protected void setCookies(User user, HttpServletResponse httpServletResponse) {
        long userId = user.getId();
        String idh = EncodingUtil.sha1Hash(user.getUserseed() + String.valueOf(userId));
        Cookie uidCookie = new Cookie("uid", String.valueOf(userId));
        uidCookie.setMaxAge(2592000);
        Cookie idhCookie = new Cookie("idh", idh);
        idhCookie.setMaxAge(2592000);
        httpServletResponse.addCookie(uidCookie);
        httpServletResponse.addCookie(idhCookie);
    }

    protected void updateSiteAccessed(User user, String host) {
        user.setLastLogin(new Date());
        if (ValidatorUtil.isNotNull(host)) {
            user.setHost(host);
        }
        userDAO.update(user);
    }

    protected RedirectView safeRedirect(String url) {
        RedirectView rv = new RedirectView(url, true);
        rv.setExposeModelAttributes(false);
        return rv;
    }

    private UserData _getUserData(User user) {
        return new UserData(user);
    }

    public String getPageMetaKeywords() {
        return pageMetaKeywords;
    }

    public String getPageMetaDescription() {
        return pageMetaDescription;
    }

    public String getPageMetaTitle() {
        if (ValidatorUtil.isNull(this.pageMetaTitle)) {
            return title;
        } else {
            return pageMetaTitle;
        }
    }

    public void setPageMetaTitle(String pageMetaTitle) {
        this.pageMetaTitle = pageMetaTitle;
    }

    public void setPageMetaKeywords(String pageMetaKeywords) {
        this.pageMetaKeywords = pageMetaKeywords;
    }

    public void setPageMetaDescription(String pageMetaDescription) {
        this.pageMetaDescription = pageMetaDescription;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}

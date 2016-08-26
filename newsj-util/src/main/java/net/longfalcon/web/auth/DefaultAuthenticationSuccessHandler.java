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

package net.longfalcon.web.auth;

import net.longfalcon.SessionKeys;
import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 8/25/16
 * Time: 4:07 PM
 */
public class DefaultAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Config config;
    private UserDAO userDAO;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String host = request.getRemoteHost();
        NewsJUserDetails userDetails = (NewsJUserDetails) authentication.getPrincipal();
        User user = userDAO.findByUserId(userDetails.getUserId());
        login(httpSession, host, user);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    protected void login(HttpSession httpSession, String host, User user) {
        long userId = user.getId();
        httpSession.setAttribute(SessionKeys.USER_ID, userId);
        httpSession.setAttribute(SessionKeys.UID, userId);

        if (config.getDefaultSite().getStoreUserIps() != 1) {
            host = "";
        }

        updateSiteAccessed(user, host);

    }

    protected void updateSiteAccessed(User user, String host) {
        user.setLastLogin(new Date());
        if (ValidatorUtil.isNotNull(host)) {
            user.setHost(host);
        }
        userDAO.update(user);
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
}

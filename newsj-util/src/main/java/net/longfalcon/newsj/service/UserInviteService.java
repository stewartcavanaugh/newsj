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

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.model.UserInvite;
import net.longfalcon.newsj.persistence.UserInviteDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Sten Martinez
 * Date: 11/10/15
 * Time: 10:02 PM
 */
@Service
public class UserInviteService {
    private static final Log _log = LogFactory.getLog(UserInviteService.class);

    private UserInviteDAO userInviteDAO;

    @Transactional
    public UserInvite getInvite(String inviteToken) {

        // TODO: move this to a daily job
        DateTime expireDate = new DateTime();
        expireDate.minusDays(UserService.DEFAULT_INVITE_EXPIRY_DAYS);

        userInviteDAO.cleanOldInvites(expireDate.toDate());

        return userInviteDAO.getInviteByGuid(inviteToken);
    }

    public UserInviteDAO getUserInviteDAO() {
        return userInviteDAO;
    }

    public void setUserInviteDAO(UserInviteDAO userInviteDAO) {
        this.userInviteDAO = userInviteDAO;
    }
}

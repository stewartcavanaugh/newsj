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

import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.model.UserInvite;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.persistence.UserInviteDAO;
import net.longfalcon.newsj.util.EncodingUtil;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.UserRegistrationVO;
import net.longfalcon.web.auth.PasswordHash;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 11/9/15
 * Time: 2:38 PM
 */
@Service
public class UserService {
    public static final int ERR_SIGNUP_BADUNAME = -1;
    public static final int ERR_SIGNUP_BADPASS = -2;
    public static final int ERR_SIGNUP_BADEMAIL = -3;
    public static final int ERR_SIGNUP_UNAMEINUSE = -4;
    public static final int ERR_SIGNUP_EMAILINUSE = -5;
    public static final int ERR_SIGNUP_BADINVITECODE = -6;
    public static final int ERR_SIGNUP_GENERAL = -7;
    public static int SUCCESS = 1;

    public static int ROLE_GUEST = 0;
    public static int ROLE_USER = 1;
    public static int ROLE_ADMIN = 2;
    public static int ROLE_DISABLED = 3;

    public static int DEFAULT_INVITES = 1;
    public static int DEFAULT_INVITE_EXPIRY_DAYS = 7;

    public static int SALTLEN = 4;
    public static int SHA1LEN = 40;

    private static final Log _log = LogFactory.getLog(UserService.class);

    private static Pattern usernamePattern = Pattern.compile("^[a-z][a-z0-9]{2,}$", Pattern.CASE_INSENSITIVE);

    private UserDAO userDAO;
    private UserInviteDAO userInviteDAO;

    @Transactional
    public long signup(UserRegistrationVO userRegistrationVO) {
        String userName = userRegistrationVO.getUserName().trim();
        String password = userRegistrationVO.getPassword().trim();
        String email = userRegistrationVO.getEmail().trim();
        String host = "";

        if (!_isValidUserName(userName)) {
            return ERR_SIGNUP_BADUNAME;
        }

        if (!_isValidPassword(password)) {
            return ERR_SIGNUP_BADPASS;
        }

        if (!ValidatorUtil.isValidEmail(email)) {
            return ERR_SIGNUP_BADEMAIL;
        }

        User existingUser = userDAO.findByUsername(userName);
        if ( existingUser != null) {
            return ERR_SIGNUP_UNAMEINUSE;
        }

        existingUser = userDAO.findByEmail(email);
        if (existingUser != null) {
            return ERR_SIGNUP_EMAILINUSE;
        }

        //
        // make sure this is the last check, as if a further validation check failed,
        // the invite would still have been used up
        //
        String inviteCode = userRegistrationVO.getInviteCode();
        long invitedBy = 0L;
        if (ValidatorUtil.isNotNull(inviteCode)) {
            invitedBy = checkAndUseInvite(inviteCode);
            if (invitedBy < 0) {
                return ERR_SIGNUP_BADINVITECODE;
            }
        }

        User user = add(userName, password, email, ROLE_USER, host, DEFAULT_INVITES, invitedBy);

        if (user == null) {
            return ERR_SIGNUP_GENERAL;
        }

        return user.getId();
    }

    @Transactional
    public User add(String userName, String password, String email, int roleId, String host, int inviteCount, Long invitedByUserId) {
        User user = new User();
        user.setUsername(userName);

        user.setEmail(email.toLowerCase());
        user.setRole(roleId);
        user.setHost(host);
        user.setInvites(inviteCount);
        user.setInvitedBy(invitedByUserId);
        user.setCreateDate(new Date());
        String rssToken = UUID.randomUUID().toString();
        user.setRssToken(EncodingUtil.md5Hash(rssToken));
        String userSeed = UUID.nameUUIDFromBytes(userName.getBytes()).toString();
        user.setUserseed(EncodingUtil.md5Hash(userSeed));

        try {
            user.setPassword(PasswordHash.createHash(password));
            userDAO.update(user);
        } catch (Exception e) {
            _log.error(e, e);
            return null;
        }
        return user;
    }

    @Transactional
    public long update(User user) {
        String userName = user.getUsername();
        String password = user.getPassword();
        String email = user.getEmail();

        if (!_isValidUserName(userName)) {
            return ERR_SIGNUP_BADUNAME;
        }

        if (!_isValidPassword(password)) {
            return ERR_SIGNUP_BADPASS;
        }

        if (!ValidatorUtil.isValidEmail(email)) {
            return ERR_SIGNUP_BADEMAIL;
        }

        User existingUser = userDAO.findByUsername(userName);
        if ( existingUser.getId() != user.getId()) {
            return ERR_SIGNUP_UNAMEINUSE;
        }

        existingUser = userDAO.findByEmail(email);
        if (existingUser.getId() != user.getId()) {
            return ERR_SIGNUP_EMAILINUSE;
        }

        try {
            userDAO.update(user);
        } catch (Exception e) {
            _log.error(e.toString());
            _log.debug(null, e);
            return ERR_SIGNUP_GENERAL;
        }

        return user.getId();
    }

    public List<User> getTopGrabbers() {
        return userDAO.findTopGrabbers();
    }

    public String generateUsername(String email) {
        String[] strings = email.split("@");
        String username = strings[0];
        User otherUser = userDAO.findByUsername(username);
        if (username != null && otherUser == null) {
            return username;
        } else {
            String uuid = UUID.randomUUID().toString();
            String md5Hash = EncodingUtil.md5Hash(uuid);
            return "u" + md5Hash.substring(0, 7);
        }
    }

    // TODO: this really belongs somewhere else.
    public String generatePassword() {
        String uuid = UUID.randomUUID().toString();
        String md5Hash = EncodingUtil.md5Hash(uuid);
        if (md5Hash != null) {
            return md5Hash.substring(0, 8);
        } else {
            return "kitty";
        }
    }

    private long checkAndUseInvite(String inviteCode) {
        UserInvite userInvite = userInviteDAO.getInviteByGuid(inviteCode);
        if (userInvite == null) {
            return -1;
        }
        User user = userDAO.findByUserId(userInvite.getUserId());
        int invites = user.getInvites();
        user.setInvites(invites - 1);
        userDAO.update(user);
        userInviteDAO.delete(userInvite);
        return user.getId();
    }

    private boolean _isValidUserName(String userName) {
        Matcher matcher = usernamePattern.matcher(userName);

        return matcher.matches();
    }

    private boolean _isValidPassword(String password) {
        return (password.length() > 5);
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserInviteDAO getUserInviteDAO() {
        return userInviteDAO;
    }

    public void setUserInviteDAO(UserInviteDAO userInviteDAO) {
        this.userInviteDAO = userInviteDAO;
    }
}

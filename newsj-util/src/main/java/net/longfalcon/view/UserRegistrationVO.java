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

package net.longfalcon.view;

import net.longfalcon.newsj.util.ValidatorUtil;

/**
 * User: Sten Martinez
 * Date: 11/10/15
 * Time: 10:42 PM
 */
public class UserRegistrationVO {
    private String userName = "";
    private String password = "";
    private String confirmPassword = "";
    private String email = "";
    private String inviteCode = "";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (ValidatorUtil.isNotNull(userName)) {
            this.userName = userName;
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (ValidatorUtil.isNotNull(password)) {
            this.password = password;
        }
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (ValidatorUtil.isNotNull(confirmPassword)) {
            this.confirmPassword = confirmPassword;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (ValidatorUtil.isNotNull(email)) {
            this.email = email;
        }
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        if (ValidatorUtil.isNotNull(inviteCode)) {
            this.inviteCode = inviteCode;
        }
    }
}

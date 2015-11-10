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

package net.longfalcon.newsj.service;

/**
 * User: Sten Martinez
 * Date: 11/9/15
 * Time: 2:38 PM
 */
public class UserService {
    public static int ERR_SIGNUP_BADUNAME = -1;
    public static int ERR_SIGNUP_BADPASS = -2;
    public static int ERR_SIGNUP_BADEMAIL = -3;
    public static int ERR_SIGNUP_UNAMEINUSE = -4;
    public static int ERR_SIGNUP_EMAILINUSE = -5;
    public static int ERR_SIGNUP_BADINVITECODE = -6;
    public static int SUCCESS = 1;

    public static int ROLE_GUEST = 0;
    public static int ROLE_USER = 1;
    public static int ROLE_ADMIN = 2;
    public static int ROLE_DISABLED = 3;

    public static int DEFAULT_INVITES = 1;
    public static int DEFAULT_INVITE_EXPIRY_DAYS = 7;

    public static int SALTLEN = 4;
    public static int SHA1LEN = 40;
}

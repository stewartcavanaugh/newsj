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
import net.longfalcon.newsj.test.BaseFsTestSupport;
import net.longfalcon.view.UserRegistrationVO;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

/**
 * User: Sten Martinez
 * Date: 3/7/16
 * Time: 10:46 AM
 */
public class UserServiceTest extends BaseFsTestSupport {

    @Autowired
    UserService userService;

    @Autowired
    UserInviteDAO userInviteDAO;

    @Test
    @Rollback
    public void testSignUpUser() {
        UserRegistrationVO userRegistrationVO = new UserRegistrationVO();
        userRegistrationVO.setUserName("testUser1");
        userRegistrationVO.setPassword("password");
        userRegistrationVO.setEmail("testUser1@email.com");

        long responseCode = userService.signup(userRegistrationVO);
        Assert.assertTrue(responseCode > 0);
    }

    @Test
    @Rollback
    public void testInviteUse() {
        UserRegistrationVO userRegistrationVO = new UserRegistrationVO();
        userRegistrationVO.setUserName("testUser1");
        userRegistrationVO.setPassword("password");
        userRegistrationVO.setEmail("testUser1@email.com");
        userRegistrationVO.setInviteCode("abc");

        long responseCode = userService.signup(userRegistrationVO);
        Assert.assertTrue(responseCode > 0);

        UserInvite userInvite = userInviteDAO.getInviteByGuid("abc");
        Assert.assertTrue(userInvite == null);
    }
}

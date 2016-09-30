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

import net.longfalcon.newsj.model.UserCart;
import net.longfalcon.newsj.persistence.UserCartDAO;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/29/16
 * Time: 3:37 PM
 */
@Component
public class CartService {
    private static final Log _log = LogFactory.getLog(CartService.class);

    UserCartDAO userCartDAO;

    @Transactional
    public void addToUserCart(long releaseId, long userId){
        UserCart userCart = null;
        userCart = userCartDAO.findByUserIdAndReleaseId(userId, releaseId);
        if (userCart == null) {
            userCart = new UserCart();
            userCart.setReleaseId(releaseId);
            userCart.setUserId(userId);
            userCart.setCreateDate(new Date());
            userCartDAO.update(userCart);
        }
    }

    public List<UserCart> getUserCartsByUser(long userId) {
        return userCartDAO.findByUserId(userId);
    }

    @Transactional
    public void deleteUserCarts(String[] userCartIdStrings, long userId) {
        for (String userCartIdString : userCartIdStrings) {
            if (ValidatorUtil.isNotNull(userCartIdString) && ValidatorUtil.isNumeric(userCartIdString)) {
                long cartId = Long.parseLong(userCartIdString);
                UserCart userCart = userCartDAO.findByCartIdAndUserId(cartId, userId);
                if (userCart == null) {
                    _log.warn("Cart " + userCartIdString + " cannot be deleted because it did not exist");
                }
                userCartDAO.delete(userCart);
            }
        }
    }

    public UserCartDAO getUserCartDAO() {
        return userCartDAO;
    }

    public void setUserCartDAO(UserCartDAO userCartDAO) {
        this.userCartDAO = userCartDAO;
    }
}

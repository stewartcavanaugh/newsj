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

import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.UserCart;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.service.CartService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 3/29/16
 * Time: 3:15 PM
 */
@Controller
public class CartController extends BaseController {

    @Autowired
    ReleaseDAO releaseDAO;

    @Autowired
    CartService cartService;

    @RequestMapping("/cart")
    public String doCartView(@RequestParam(value = "delete", required = false) String delete,
                             Model model) {

        if (ValidatorUtil.isNotNull(delete)) {
            String[] cartIds = delete.split(",");
            cartService.deleteUserCarts(cartIds, getUserId());
        }

        List<UserCart> userCarts = cartService.getUserCartsByUser(getUserId());
        Map<Long,Release> releaseInfoMap = new HashMap<>();
        for (UserCart userCart : userCarts) {
            Release release = releaseDAO.findByReleaseId(userCart.getReleaseId());
            releaseInfoMap.put(release.getId(), release);
        }

        setPageMetaTitle("My Nzb Cart");
        setPageMetaKeywords("search,add,to,cart,nzb,description,details");
        setPageMetaDescription("Manage Your Nzb Cart");
        model.addAttribute("userCarts", userCarts);
        model.addAttribute("releaseInfoMap", releaseInfoMap);
        return "cart";
    }

    @ResponseBody
    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    public String addCartPost(@RequestParam(value = "add") String add) throws NoSuchResourceException {
        String[] guids = add.split(",");

        List<Release> releases = releaseDAO.findByGuids(guids);

        if (releases == null || releases.isEmpty()) {
            throw new NoSuchResourceException();
        }

        for (Release release : releases) {
            cartService.addToUserCart(release.getId(), getUserId());
        }

        return "";
    }
}

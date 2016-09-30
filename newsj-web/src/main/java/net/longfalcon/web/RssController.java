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

import net.longfalcon.newsj.CategoryService;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.UserDAO;
import net.longfalcon.newsj.persistence.UserExCatDAO;
import net.longfalcon.newsj.service.SearchService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.newsj.ws.rss.Rss;
import net.longfalcon.web.exception.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * User: Sten Martinez
 * Date: 8/23/16
 * Time: 3:22 PM
 */
@Controller
public class RssController extends BaseController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    SearchService searchService;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserExCatDAO userExCatDAO;

    @RequestMapping("/rss")
    public String rssHelpView(Model model) {

        List<Category> parentCategories = categoryService.getParentCategories();
        List<Category> subCategories = categoryService.getChildCategories();

        model.addAttribute("parentCategoryList", parentCategories);
        model.addAttribute("subCategoryList", subCategories);
        model.addAttribute("title", "Rss Feeds");
        model.addAttribute("pageMetaTitle", title);
        model.addAttribute("pageMetaKeywords", "view,nzb,description,details,rss,atom");
        model.addAttribute("pageMetaDescription", "View available Rss Nzb feeds.");
        return "rssdesc";
    }

    @RequestMapping(value = "/rss", produces = "text/xml", params = "t")
    @ResponseBody
    public Rss rssGet(@RequestParam("t") String categoryIdString,
                      @RequestParam(value = "r", required = false) String token,
                      @RequestParam(value = "i", required = false) String userIdString,
                      @RequestParam(value = "del", required = false) String del,
                      @RequestParam(value = "dl", required = false)  String dl,
                      @RequestParam(value = "num", required = false)String num,
                      UriComponentsBuilder uriComponentsBuilder) throws PermissionDeniedException {
        String serverBaseUrl = uriComponentsBuilder.toUriString();

        long userId = Long.parseLong(userIdString);
        User user = userDAO.findByUserId(userId);
        if (user == null) {
            throw new PermissionDeniedException();
        }
        if (!user.getRssToken().equals(token)) {
            throw new PermissionDeniedException();
        }
        int categoryId = 0;
        if (ValidatorUtil.isNotNull(categoryIdString)) {
            categoryId = Integer.parseInt(categoryIdString);
        }

        List<Integer> categoryIds = new ArrayList<>();
        if (categoryId > 0) {
            Category category = categoryService.getCategory(categoryId);
            if (!ValidatorUtil.isNotNull(category.getParentId())) {
                categoryIds = categoryService.getCategoryChildrenIds(categoryId);
            } else {
                categoryIds.add(categoryId);
            }
        }

        List<Integer> userExCatIds = userExCatDAO.getUserExCatIds(userId);

        int limit = 100;
        if (ValidatorUtil.isNotNull(num)) {
            limit = Integer.parseInt(num);
        }
        boolean download = false;
        if (ValidatorUtil.isNotNull(dl)) {
            download = Objects.equals(dl, "1");
        }

        return searchService.getRssFeed(user, serverBaseUrl, userExCatIds, categoryIds, limit, download);
    }
}

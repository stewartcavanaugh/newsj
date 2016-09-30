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
import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import net.longfalcon.newsj.persistence.UserExCatDAO;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: longfalcon
 * Date: 3/12/2016
 * Time: 8:06 PM
 */
@Controller
public class BrowseController extends BaseController {

    @Autowired
    GroupDAO groupDAO;

    @Autowired
    UserExCatDAO userExCatDAO;

    @Autowired
    ReleaseDAO releaseDAO;

    @Autowired
    Releases releases;

    private static Set<String> releaseFieldNames = new HashSet<>();
    static {
        releaseFieldNames.add("searchName");//'name_asc', 'name_desc',
        releaseFieldNames.add("category");// 'cat_asc', 'cat_desc',
        releaseFieldNames.add("postDate");// 'posted_asc', 'posted_desc',
        releaseFieldNames.add("size");// 'size_asc', 'size_desc',
        releaseFieldNames.add("totalpart");// 'files_asc', 'files_desc',
        releaseFieldNames.add("grabs");// 'stats_asc', 'stats_desc'
    }

    @RequestMapping("/browse")
    public String browseView(@RequestParam(value = "t", required = false) Integer categoryId,
                             @RequestParam(value = "g", required = false) String groupName,
                             @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                             @RequestParam(value = "ob", required = false) String orderBy,
                             Model model) throws NoSuchResourceException {
        String categoryName = "All";
        String section = "";
        setPageMetaTitle("Browse Nzbs");
        setPageMetaKeywords("browse,nzb,description,details");
        setPageMetaDescription("Browse for Nzbs");

        if (offset == null) {
            offset = 0;
        }

        List<Integer> categoryIds = new ArrayList<>();
        if (categoryId != null && categoryId > 0) {
            Category category = categoryService.getCategory(categoryId);
            if ( category != null) {
                categoryName = category.getTitle();
                Integer categoryParentId;
                if (category.getParentId() == null) {
                    categoryParentId = 0;
                    categoryIds.addAll(categoryService.getCategoryChildrenIds(categoryId));
                }
                else {
                    categoryParentId = category.getParentId();
                    categoryIds.add(categoryId);
                }
                int id = category.getId();
                if (id == CategoryService.CAT_PARENT_GAME ||
                        categoryParentId == CategoryService.CAT_PARENT_GAME) {
                    section = "console";
                } else if (id == CategoryService.CAT_PARENT_MOVIE ||
                        categoryParentId == CategoryService.CAT_PARENT_MOVIE) {
                    section = "movies";
                } else if (id == CategoryService.CAT_PARENT_MUSIC ||
                        categoryParentId == CategoryService.CAT_PARENT_MUSIC) {
                    section = "music";
                }

            } else {
                throw new NoSuchResourceException();
            }

        }

        long groupId = -1;
        if (ValidatorUtil.isNotNull(groupName)) {
            Group group = groupDAO.getGroupByName(groupName);
            if ( group != null) {
                groupId = group.getId();
            }
        }

        User user = userDAO.findByUserId(getUserId());
        if (user != null) {
            model.addAttribute("lastVisit",user.getLastLogin());
        }
        List<Integer> userExCats = userExCatDAO.getUserExCatIds(getUserId());

        List<Release> releaseList = new ArrayList<>();
        int pagerTotalItems = Math.toIntExact(releases.getBrowseCount(categoryIds, -1, userExCats, groupId));

        if (ValidatorUtil.isNull(orderBy)) {
            releaseList = releases.getBrowseReleases(categoryIds, -1, userExCats, groupId, "postDate", true, offset, PAGE_SIZE);
        } else {
            String propertyName = getOrderByProperty(orderBy);
            boolean descending = getOrderByOrder(orderBy);
            if (ValidatorUtil.isNotNull(propertyName)) {
                releaseList = releases.getBrowseReleases(categoryIds, -1, userExCats, groupId, propertyName, descending, offset, PAGE_SIZE);
            }
        }

        model.addAttribute("categoryId", categoryId);
        model.addAttribute("groupName", groupName);
        model.addAttribute("catName", categoryName);
        model.addAttribute("section", section);
        model.addAttribute("releaseList", releaseList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("now", new Date());
        return "browse";
    }

    @RequestMapping("/browsegroup")
    public String browseGroupsView(Model model) {
        title = "Browse Groups";

        List<Long> groupIds = releaseDAO.findReleaseGroupIds();
        List<Group> groupList = groupDAO.findGroupsByIds(groupIds);

        setPageMetaDescription("Browse groups");
        setPageMetaKeywords("browse,groups,description,details");
        model.addAttribute("title", title);
        model.addAttribute("groupList", groupList);
        return "browse-groups";
    }

    private String getOrderByProperty(String orderByParam) {
        for (String fieldName : releaseFieldNames) {
            if (orderByParam.toLowerCase().startsWith(fieldName.toLowerCase())) {
                return fieldName;
            }
        }
        return "";
    }

    private boolean getOrderByOrder(String orderByParam) {
        return orderByParam.endsWith("_desc");
    }
}

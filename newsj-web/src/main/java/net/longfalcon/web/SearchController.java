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

import net.longfalcon.newsj.model.Binary;
import net.longfalcon.newsj.model.Category;
import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.persistence.UserExCatDAO;
import net.longfalcon.newsj.service.SearchService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User: Sten Martinez
 * Date: 3/24/16
 * Time: 1:36 PM
 */
@Controller
public class SearchController extends BaseController {

    @Autowired
    GroupDAO groupDAO;

    @Autowired
    UserExCatDAO userExCatDAO;

    @Autowired
    SearchService searchService;

    private static final Log _log = LogFactory.getLog(SearchController.class);
    private static Set<String> releaseFieldNames = new HashSet<>();
    static {
        releaseFieldNames.add("searchName");//'name_asc', 'name_desc',
        releaseFieldNames.add("category");// 'cat_asc', 'cat_desc',
        releaseFieldNames.add("postDate");// 'posted_asc', 'posted_desc',
        releaseFieldNames.add("size");// 'size_asc', 'size_desc',
        releaseFieldNames.add("totalpart");// 'files_asc', 'files_desc',
        releaseFieldNames.add("grabs");// 'stats_asc', 'stats_desc'
    }

    @RequestMapping("/search")
    public String searchView(@RequestParam(value = "search", required = false) String search,
                             @RequestParam(value = "t", required = false) String categories,
                             @RequestParam(value = "g", required = false) String groupName,
                             Model model) throws NoSuchResourceException {
        return searchView(search, categories, groupName, 0, "postDate_desc", model);
    }

    @RequestMapping("/search/{searchToken}")
    public String searchView(@PathVariable("searchToken") String search,
                             @RequestParam(value = "t", required = false) String categories,
                             @RequestParam(value = "g", required = false) String groupName,
                             @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                             @RequestParam(value = "ob", required = false) String orderBy,
                             Model model) throws NoSuchResourceException {

        List<Integer> categoryIds = new ArrayList<>();
        if (ValidatorUtil.isNotNull(categories)) {
            String[] categoryIdStrings = categories.split(",");
            for (String categoryIdString : categoryIdStrings) {
                try {
                    int categoryId = Integer.parseInt(categoryIdString);
                    Category category = categoryService.getCategory(categoryId);
                    if ( category != null) {
                        categoryIds.add(categoryId);
                    } else {
                        throw new NoSuchResourceException();
                    }
                } catch (NumberFormatException nfe) {
                    _log.warn(categoryIdString + " is not a number " + nfe.toString());
                }
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
        int pagerTotalItems = 0;
        if (ValidatorUtil.isNotNull(search)) {
            pagerTotalItems = Math.toIntExact(searchService.getSearchCount(search, categoryIds, -1, userExCats, groupId));

            if (ValidatorUtil.isNull(orderBy)) {
                releaseList = searchService.getSearchReleases(search, categoryIds, -1, userExCats, groupId, "postDate", true, offset, PAGE_SIZE);
            } else {
                String propertyName = getOrderByProperty(orderBy);
                boolean descending = getOrderByOrder(orderBy);
                if (ValidatorUtil.isNotNull(propertyName)) {
                    releaseList = searchService.getSearchReleases(search, categoryIds, -1, userExCats, groupId, propertyName, descending, offset, PAGE_SIZE);
                }
            }
        }

        model.addAttribute("search", search);
        model.addAttribute("searchCategories", categories);
        model.addAttribute("groupName", groupName);
        model.addAttribute("releaseList", releaseList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("now", new Date());
        return "search";
    }

    @RequestMapping("/searchraw/{searchToken}")
    public String searchBinariesView(@PathVariable("searchToken") String search,
                                      Model model) {

        List<Integer> userExCats = userExCatDAO.getUserExCatIds(getUserId());
        List<Binary> binaryList = searchService.searchBinaries(search, userExCats);

        model.addAttribute("search", search);
        model.addAttribute("binaryList", binaryList);
        return "searchraw";
    }

    @RequestMapping("/searchraw")
    public String searchBinariesFormView(@RequestParam(value = "search", required = false) String search, Model model) {
        return searchBinariesView(search, model);
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

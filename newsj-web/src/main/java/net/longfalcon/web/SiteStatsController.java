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
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.User;
import net.longfalcon.newsj.service.UserService;
import net.longfalcon.view.RecentReleaseCategoryView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/2/16
 * Time: 10:54 AM
 */
@Controller
public class SiteStatsController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    Releases releases;

    @Autowired
    CategoryService categoryService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @RequestMapping("/admin/site-stats")
    public String siteStatsView(Model model) {

        title = "Site Stats";

        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED));

        List<User> topGrabbers = userService.getTopGrabbers();
        List<Release> topDownloads = releases.getTopDownloads();
        List<Release> topComments = releases.getTopComments();
        List<Object[]> resultSet = releases.getRecentlyAddedReleases();
        List<RecentReleaseCategoryView> recentReleaseCategories = new ArrayList<>(10);

        for (Object[] resultRow : resultSet) {
            Category category = (Category) resultRow[0];
            long count = (long) resultRow[1];
            Category parentCategory = categoryService.getCategory(category.getParentId());
            String categoryName = parentCategory.getTitle() + ">" + category.getTitle();
            RecentReleaseCategoryView recentReleaseCategoryView = new RecentReleaseCategoryView(categoryName, count);
            recentReleaseCategories.add(recentReleaseCategoryView);
        }

        transactionManager.commit(transaction);

        model.addAttribute("title", title);
        model.addAttribute("topGrabberList", topGrabbers);
        model.addAttribute("topCommentsList", topComments);
        model.addAttribute("topDownloadsList", topDownloads);
        model.addAttribute("recentlyAddedCategories", recentReleaseCategories);
        return "admin/site-stats";
    }
}

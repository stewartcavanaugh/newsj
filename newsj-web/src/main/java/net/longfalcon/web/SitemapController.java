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

import net.longfalcon.newsj.Config;
import net.longfalcon.newsj.model.Content;
import net.longfalcon.newsj.persistence.ContentDAO;
import net.longfalcon.newsj.service.ContentService;
import net.longfalcon.view.SitemapItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 9/22/16
 * Time: 10:58 AM
 */
@Controller
public class SitemapController extends BaseController {

    @Autowired
    Config config;

    @Autowired
    ContentDAO contentDAO;

    @RequestMapping("/sitemap")
    public String sitemapView(Model model) {
        List<SitemapItemView> sitemapItems = new ArrayList<>();

        sitemapItems.add(new SitemapItemView("Home","Home Page", "/", 1.0f, "daily"));
        int roleId = getRoleId();
        List<Content> usefulLinks = contentDAO.findByTypeAndRole(ContentService.TYPEUSEFUL, roleId);
        for (Content content : usefulLinks) {
            sitemapItems.add(new SitemapItemView("Useful Links",content.getTitle(), "/content" + content.getId() + content.getUrl(), 0.5f, "monthly"));
        }

        List<Content> articleLinks = contentDAO.findByTypeAndRole(ContentService.TYPEARTICLE, roleId);
        for (Content content : articleLinks) {
            sitemapItems.add(new SitemapItemView("Articles",content.getTitle(), "/content" + content.getId() + content.getUrl(), 0.5f, "monthly"));
        }

        sitemapItems.add(new SitemapItemView("Useful Links","Contact Us", "/contact-us", 0.3f, "yearly"));
        sitemapItems.add(new SitemapItemView("Useful Links","Site Map", "/sitemap", 0.5f, "weekly"));
        sitemapItems.add(new SitemapItemView("Useful Links","Rss Feeds", "/rss", 0.5f, "weekly"));

        sitemapItems.add(new SitemapItemView("Nzb","Search Nzb", "/search", 0.5f, "weekly"));
        sitemapItems.add(new SitemapItemView("Nzb", "Browse Nzb", "/browse", 0.8f, "daily"));

        String title = config.getDefaultSite().getMetaTitle() + " Site Map";
        model.addAttribute("sitemapItems", sitemapItems);
        model.addAttribute("title", title);
        model.addAttribute("pageMetaTitle", title);
        model.addAttribute("pageMetaKeywords", "sitemap,site,map");
        model.addAttribute("pageMetaDescription", title);
        return "sitemap";
    }
}

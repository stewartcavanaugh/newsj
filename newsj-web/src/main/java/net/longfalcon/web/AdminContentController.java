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

import net.longfalcon.newsj.model.Content;
import net.longfalcon.newsj.service.ContentService;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 2/26/16
 * Time: 4:22 PM
 */
@Controller
@SessionAttributes("content")
public class AdminContentController extends BaseController {

    @Autowired
    ContentService contentService;

    private static final Log _log = LogFactory.getLog(AdminContentController.class);

    @RequestMapping("/admin/content-list")
    public String listContentView(Model model) {
        title = "Content List";

        List<Content> contentList = contentService.getAllContent();
        model.addAttribute("title", title);
        model.addAttribute("contentList", contentList);
        return "admin/content-list";
    }

    @RequestMapping(value = "/admin/content-add", method = RequestMethod.GET)
    public String addContentView(@RequestParam(value = "action", required = false)String action,
                                 @RequestParam(value = "id", required = false)Long id,
                                 Model model) {
        Content content = null;
        if (ValidatorUtil.isNotNull(action) && action.equals("add")) {
            title = "Content Add";
            content = new Content();
            content.setShowInMenu(1);
            content.setStatus(1);
            content.setContentType(2);

        } else {
            if (ValidatorUtil.isNotNull(id)) {
                title = "Content Edit";

                content = contentService.getContent(id);
            }
        }

        Map<Integer, String> yesNoMap = new HashMap<>();
        yesNoMap.put(1,"Yes");
        yesNoMap.put(0, "No");

        Map<Integer, String> statusMap = new HashMap<>();
        statusMap.put(1, "Enabled");
        statusMap.put(0, "Disabled");

        Map<Integer, String> contentTypeMap = new HashMap<>();
        contentTypeMap.put(1, "Useful Link");
        contentTypeMap.put(2, "Article");
        contentTypeMap.put(3, "Homepage");

        Map<Integer, String> roleMap = new HashMap<>();
        roleMap.put(0, "Everyone");
        roleMap.put(1, "Logged in Users");
        roleMap.put(2, "Admins");

        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("yesNoMap", yesNoMap);
        model.addAttribute("statusMap", statusMap);
        model.addAttribute("contentTypeMap", contentTypeMap);
        model.addAttribute("roleMap", roleMap);
        return "admin/content-add";
    }

    @RequestMapping(value = "/admin/content-add", method = RequestMethod.POST)
    public View addContentPost(@ModelAttribute("content")Content content, Model model) {
        contentService.update(content);

        return safeRedirect("/admin/content-add?id="+content.getId());
    }

    @RequestMapping(value = "/admin/content-delete")
    public View deleteContent(@RequestParam(value = "id", required = true)long id, Model model) {
        contentService.delete(id);

        return safeRedirect("/admin/content-list");
    }
}

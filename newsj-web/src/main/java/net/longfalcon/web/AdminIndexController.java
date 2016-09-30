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

import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.service.GroupService;
import net.longfalcon.newsj.service.SiteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 12/16/15
 * Time: 2:55 PM
 */
@Controller
@SessionAttributes({"siteObject"})
public class AdminIndexController extends BaseController {

    @Autowired
    SiteService siteService;

    @Autowired
    GroupDAO groupDAO;

    @Autowired
    GroupService groupService;

    private static final Log _log = LogFactory.getLog(AdminIndexController.class);

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Admin Hangout");
        return "admin/admin_index";
    }

    @RequestMapping(value = "/admin/site-edit", method = RequestMethod.GET)
    public String editSiteView(Model model, HttpSession httpSession) {
        Site site = config.getDefaultSite();
        model.addAttribute("title", "Site Edit");
        model.addAttribute("siteObject", site);

        List<String> themeNameList = new ArrayList<>();
        try {
            String realPath = httpSession.getServletContext().getRealPath("/public-resources/themes");
            File themesDir = new File(realPath);
            String[] themeDirList = themesDir.list();
            Collections.addAll(themeNameList, themeDirList);
        } catch (Exception e) {
            _log.error(e, e);
        }

        Map<Integer, String> showPasswordedRelOptionsMap = new HashMap<>();
        showPasswordedRelOptionsMap.put(0,"Dont show passworded or potentially passworded");
        showPasswordedRelOptionsMap.put(1,"Dont show passworded");
        showPasswordedRelOptionsMap.put(2,"Show everything");

        Map<Integer, String> newGroupsScanMethodMap = new HashMap<>();
        newGroupsScanMethodMap.put(1,"Days");
        newGroupsScanMethodMap.put(0, "Posts");

        Map<Integer, String> registerStatusMap = new HashMap<>();
        registerStatusMap.put(SiteService.REGISTER_STATUS_OPEN, "Open");
        registerStatusMap.put(SiteService.REGISTER_STATUS_INVITE, "Invite");
        registerStatusMap.put(SiteService.REGISTER_STATUS_CLOSED, "Closed");

        model.addAttribute("yesNoMap", YES_NO_MAP);
        model.addAttribute("showPasswordedRelOptionsMap", showPasswordedRelOptionsMap);
        model.addAttribute("newGroupsScanMethodMap", newGroupsScanMethodMap);
        model.addAttribute("registerStatusMap", registerStatusMap);
        model.addAttribute("themeNameList", themeNameList);
        return "admin/site-edit";
    }

    @RequestMapping(value = "/admin/site-edit", method = RequestMethod.POST)
    public String editSitePost(@ModelAttribute("siteObject")Site siteObject, Model model, HttpSession httpSession) {
        siteService.updateSite(siteObject);
        return editSiteView(model, httpSession);
    }
}

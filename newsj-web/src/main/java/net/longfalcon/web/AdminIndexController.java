/*
 * Copyright (c) 2015. Sten Martinez
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

import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.persistence.GroupDAO;
import net.longfalcon.newsj.service.GroupService;
import net.longfalcon.newsj.service.SiteService;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.view.DateView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

/**
 * User: Sten Martinez
 * Date: 12/16/15
 * Time: 2:55 PM
 */
@Controller
@RequestMapping("/admin")
@SessionAttributes({"siteObject","group"})
public class AdminIndexController extends BaseController {

    @Autowired
    SiteService siteService;

    @Autowired
    GroupDAO groupDAO;

    @Autowired
    GroupService groupService;

    private static int pageSize = 50;
    private static final Log _log = LogFactory.getLog(AdminIndexController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        model.addAttribute("title", "Admin Hangout");
        return "admin/admin_index";
    }

    @RequestMapping(value = "/site-edit", method = RequestMethod.GET)
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

        Map<Integer, String> yesNoMap = new HashMap<>();
        yesNoMap.put(1,"Yes");
        yesNoMap.put(0, "No");

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

        model.addAttribute("yesNoMap", yesNoMap);
        model.addAttribute("showPasswordedRelOptionsMap", showPasswordedRelOptionsMap);
        model.addAttribute("newGroupsScanMethodMap", newGroupsScanMethodMap);
        model.addAttribute("registerStatusMap", registerStatusMap);
        model.addAttribute("themeNameList", themeNameList);
        return "admin/site-edit";
    }

    @RequestMapping(value = "/site-edit", method = RequestMethod.POST)
    public String editSitePost(@ModelAttribute("siteObject")Site siteObject, Model model, HttpSession httpSession) {
        siteService.updateSite(siteObject);
        return editSiteView(model, httpSession);
    }

    @RequestMapping(value = "/group-list", method = RequestMethod.GET)
    public String groupListView(@RequestParam(value = "page", required = false, defaultValue = "0") Integer pageNum, Model model) {
        List<Group> groupList;

        // pager logic
        Map<Integer,String> pagerMap = new HashMap<>();
        long resultCount = groupDAO.getGroupsCount();

        if (resultCount > pageSize) {
            int firstResult = pageNum * pageSize;

            groupList = groupDAO.getGroups(firstResult, pageSize);
            int totalPages = (int) (resultCount + pageSize -1 ) / pageSize;
            for (int i = 0; i < totalPages; i++) {
                String pageName = "";
                if (i == 0) {
                    pageName = "first";
                }
                if (i == (totalPages -1)) {
                    pageName = "last";
                }
                if (i == pageNum) {
                    pageName += ":current";
                }
                if (i == pageNum - 1) {
                    pageName += ":prev";
                }
                if (i == pageNum + 1) {
                    pageName += ":next";
                }
                if (ValidatorUtil.isNotNull(pageName)) {
                    pagerMap.put(i,pageName);
                }
            }
        } else {
            groupList = groupDAO.getGroups();
        }


        model.addAttribute("title", "Group List");
        model.addAttribute("groupList", groupList);
        model.addAttribute("dateView", new DateView());
        model.addAttribute("groupService", groupService);
        model.addAttribute("pagerMap", pagerMap);
        return "admin/group-list";
    }

    // TODO: restrict to POST only
    // TODO: replace magic string literals with constants
    @RequestMapping("/ajax_group-edit")
    @ResponseBody
    public String ajaxGroupEdit(@RequestParam(value = "action", required = false) String action,
                                @RequestParam(value = "group_id", required = true) Integer groupId,
                                @RequestParam(value = "group_status", required = false, defaultValue = "0") Integer groupStatus) {
        if (ValidatorUtil.isNotNull(action)) {
            if (action.equals("2")) {
                groupService.delete(groupId);
                return String.format("Group %d deleted.", groupId);
            }
            if (action.equals("3")) {
                groupService.reset(groupId);
                return String.format("Group %d reset.", groupId);
            }
            if (action.equals("4")) {
                groupService.purge(groupId);
                return String.format("Group %d purged.", groupId);
            }
        } else {
            if (ValidatorUtil.isNotNull(groupId)) {
                boolean active = groupStatus == 1;
                groupService.updateGroupStatus(groupId, active);
                return String.format("Group %d is now %s", groupId, active ? "activated" : "deactivated");
            }
        }
        return "ajaxGroupEdit called incorrectly.";
    }

    @RequestMapping(value = "/group-edit", method = RequestMethod.GET)
    public String editGroupView(@RequestParam(value = "id")Long groupId, Model model, HttpSession httpSession) {
        Group group = groupService.getGroup(groupId);
        Map<Integer, String> yesNoMap = new HashMap<>();
        yesNoMap.put(1,"Yes");
        yesNoMap.put(0, "No");

        model.addAttribute("title", group.getName());
        model.addAttribute("yesNoMap", yesNoMap);
        model.addAttribute("group", group);
        return "admin/group-edit";
    }

    @RequestMapping(value = "/group-edit", method = RequestMethod.POST)
    public String editGroupPost(@ModelAttribute("group")Group group, Model model, HttpSession httpSession) {
        groupService.update(group);
        return editGroupView(group.getId(), model, httpSession);
    }
}

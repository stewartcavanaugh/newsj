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

import net.longfalcon.newsj.model.Group;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 2/26/16
 * Time: 3:41 PM
 */
@Controller
@SessionAttributes({"group"})
public class AdminGroupController extends BaseController {

    @Autowired
    SiteService siteService;

    @Autowired
    GroupDAO groupDAO;

    @Autowired
    GroupService groupService;

    private static final Log _log = LogFactory.getLog(AdminGroupController.class);

    @RequestMapping(value = "/admin/group-list", method = RequestMethod.GET)
    public String groupListView(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                Model model) {
        List<Group> groupList = groupDAO.getGroups(offset, PAGE_SIZE);

        int pagerTotalItems = Math.toIntExact(groupDAO.getGroupsCount());

        model.addAttribute("title", "Group List");
        model.addAttribute("groupList", groupList);
        model.addAttribute("dateView", new DateView());
        model.addAttribute("groupService", groupService);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);

        return "admin/group-list";
    }

    // TODO: restrict to POST only
    // TODO: replace magic string literals with constants
    @RequestMapping("/admin/ajax_group-edit")
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

    @RequestMapping(value = "/admin/group-edit", method = RequestMethod.GET)
    public String editGroupView(@RequestParam(value = "id")Long groupId, Model model, HttpSession httpSession) {
        Group group = groupService.getGroup(groupId);

        model.addAttribute("title", group.getName());
        model.addAttribute("yesNoMap", YES_NO_MAP);
        model.addAttribute("group", group);
        return "admin/group-edit";
    }

    @RequestMapping(value = "/admin/group-edit", method = RequestMethod.POST)
    public View editGroupPost(@ModelAttribute("group")Group group, Model model, HttpSession httpSession) {
        groupService.update(group);
        return safeRedirect("/admin/group-edit?id="+group.getId());
    }
}

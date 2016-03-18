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

import net.longfalcon.newsj.model.BinaryBlacklistEntry;
import net.longfalcon.newsj.persistence.BinaryBlacklistDAO;
import net.longfalcon.newsj.util.Defaults;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 3/15/16
 * Time: 12:13 PM
 */
@Controller
public class AdminBinaryBlacklistController extends BaseController {

    @Autowired
    BinaryBlacklistDAO binaryBlacklistDAO;

    @RequestMapping("/admin/binaryblacklist-list")
    public String listBinaryBlacklistView(Model model) {
        title = "Binary Black/Whitelist List";

        List<BinaryBlacklistEntry> binaryBlacklistEntries = binaryBlacklistDAO.findAllBinaryBlacklistEntries(false);

        model.addAttribute("title", title);
        model.addAttribute("binaryBlacklistEntries",binaryBlacklistEntries);
        return "admin/binaryblacklist-list";
    }

    @RequestMapping(value = "/admin/binaryblacklist-edit", method = RequestMethod.GET)
    public String editBinaryBlacklistView(@RequestParam(value = "id", required = false) Long id,
                                          Model model) throws NoSuchResourceException {
        BinaryBlacklistEntry blacklistEntry;
        if (id != null && id > 0) {
            blacklistEntry = binaryBlacklistDAO.findByBinaryBlacklistId(id);
            if (blacklistEntry == null) {
                throw new NoSuchResourceException();
            }
            title = "Binary Black/Whitelist Edit";
        } else {
            blacklistEntry = new BinaryBlacklistEntry();
            title = "Binary Black/Whitelist Add";
        }

        Map<Integer,String> opTypeMap = new HashMap<>();
        opTypeMap.put(1, "Black");
        opTypeMap.put(2, "White");

        Map<Integer,String> columnMap = new HashMap<>();
        columnMap.put(Defaults.BLACKLIST_FIELD_SUBJECT, "Subject");
        columnMap.put(Defaults.BLACKLIST_FIELD_FROM, "Poster");
        columnMap.put(Defaults.BLACKLIST_FIELD_MESSAGEID, "MessageId");

        model.addAttribute("title", title);
        model.addAttribute("blacklistEntry", blacklistEntry);
        model.addAttribute("yesNoMap", YES_NO_MAP);
        model.addAttribute("opTypeMap", opTypeMap);
        model.addAttribute("columnMap", columnMap);
        return "admin/binaryblacklist-edit";
    }

    @RequestMapping(value = "/admin/binaryblacklist-edit", method = RequestMethod.POST)
    public View editBinaryBlacklistPost(@ModelAttribute("blacklistEntry") BinaryBlacklistEntry binaryBlacklistEntry,
                                        Model model) {

        binaryBlacklistDAO.update(binaryBlacklistEntry);

        return safeRedirect("/admin/binaryblacklist-edit?id=" + binaryBlacklistEntry.getId());
    }
}

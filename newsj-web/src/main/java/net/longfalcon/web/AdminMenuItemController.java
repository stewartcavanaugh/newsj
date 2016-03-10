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

import net.longfalcon.newsj.model.MenuItem;
import net.longfalcon.newsj.persistence.MenuItemDAO;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.View;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/10/16
 * Time: 10:52 AM
 */
@Controller
@SessionAttributes({"menuItem"})
public class AdminMenuItemController extends BaseController {

    @Autowired
    MenuItemDAO menuItemDAO;

    @RequestMapping("/admin/menu-list")
    public String listMenuItemView(Model model) {
        title = "Menu List";

        List<MenuItem> menuItemList = menuItemDAO.getMenuItems();

        model.addAttribute("title", title);
        model.addAttribute("menuItemList",menuItemList);
        return "admin/menu-list";
    }

    @RequestMapping("/admin/menu-edit")
    public String editMenuItemView(@RequestParam(value = "id", required = false, defaultValue = "0")Long id, Model model) throws NoSuchResourceException {
        title = "Menu Edit";

        MenuItem menuItem = null;
        if (id > 0) {
            menuItem = menuItemDAO.findByMenuItemId(id);
            if (menuItem == null) {
                throw new NoSuchResourceException();
            }
        } else {
            menuItem = new MenuItem();
        }

        model.addAttribute("title", title);
        model.addAttribute("menuItem", menuItem);
        return "admin/menu-edit";
    }

    @RequestMapping(value = "/admin/menu-edit", method = RequestMethod.POST)
    public View editMenuItemView(@ModelAttribute("menuItem") MenuItem menuItem, Model model) {
        menuItemDAO.update(menuItem);

        return safeRedirect("/admin/menu-edit?id=" + menuItem.getId());
    }

    @RequestMapping(value = "/admin/menu-delete", method = RequestMethod.POST)
    public View deleteMenuItemPost(@RequestParam(value = "id")Long id, Model model) throws NoSuchResourceException {
        MenuItem menuItem = menuItemDAO.findByMenuItemId(id);
        if (menuItem == null) {
            throw new NoSuchResourceException();
        }
        menuItemDAO.delete(menuItem);

        return safeRedirect("/admin/menu-list");
    }
}

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

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.model.MenuItem;
import net.longfalcon.newsj.persistence.MenuItemDAO;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 11/9/15
 * Time: 2:39 PM
 */
@Service
public class MenuService {
    private MenuItemDAO menuItemDAO;

    public List<MenuItem> getMenuItems(int roleId, boolean isSabEnabled) {
        List<MenuItem> menuItems = menuItemDAO.getMenuItemsByRole(roleId, (roleId != UserService.ROLE_GUEST));
        if (!isSabEnabled) {
            for (Iterator<MenuItem> iterator = menuItems.iterator(); iterator.hasNext(); ) {
                MenuItem menuItem = iterator.next();
                String menuEval = menuItem.getMenuEval();
                if (menuEval != null && menuEval.contains("sabintegrated")) {
                    iterator.remove();
                }
            }
        }

        return menuItems;
    }

    public MenuItemDAO getMenuItemDAO() {
        return menuItemDAO;
    }

    public void setMenuItemDAO(MenuItemDAO menuItemDAO) {
        this.menuItemDAO = menuItemDAO;
    }
}

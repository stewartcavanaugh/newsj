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

package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.Group;

import java.util.Collection;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 10/5/15
 * Time: 9:48 PM
 */
public interface GroupDAO {
    List<Group> getGroups();

    List<Group> getGroups(int start, int pageSize);

    List<Group> getGroups(int start, int pageSize, String orderByField, boolean descending);

    Long getGroupsCount();

    List<Group> getActiveGroups();

    void update(Group group);

    void delete(Group group);

    Group getGroupByName(String name);

    List<Group> findGroupsByName(String name);

    Group findGroupByGroupId(long groupId);

    List<Group> findGroupsByIds(Collection<Long> ids);

    public List<String> getGroupsForSelect();
}

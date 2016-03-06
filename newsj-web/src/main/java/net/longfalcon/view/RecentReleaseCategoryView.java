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

package net.longfalcon.view;

/**
 * User: Sten Martinez
 * Date: 3/2/16
 * Time: 5:29 PM
 */
public class RecentReleaseCategoryView {
    private String categoryName;
    private long count;

    public RecentReleaseCategoryView(String category, long count) {
        this.categoryName = category;
        this.count = count;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public long getCount() {
        return count;
    }
}

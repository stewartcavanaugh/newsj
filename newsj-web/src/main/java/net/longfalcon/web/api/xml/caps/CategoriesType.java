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

package net.longfalcon.web.api.xml.caps;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class CategoriesType {
    @XmlElement(name = "category")
    protected List<CategoryType> categories;

    public CategoriesType() {
    }

    public CategoriesType(List<CategoryType> categories) {
        this.categories = categories;
    }

    public List<CategoryType> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryType> categories) {
        this.categories = categories;
    }
}

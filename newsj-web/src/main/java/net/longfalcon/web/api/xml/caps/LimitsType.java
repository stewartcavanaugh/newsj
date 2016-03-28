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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LimitsType")
public class LimitsType {
    @XmlAttribute
    protected int max;

    @XmlAttribute(name = "default")
    protected int default_;

    public LimitsType() {
    }

    public LimitsType(int max, int default_) {
        this.max = max;
        this.default_ = default_;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getDefault() {
        return default_;
    }

    public void setDefault(int default_) {
        this.default_ = default_;
    }
}

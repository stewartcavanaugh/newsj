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

/**
 * User: Sten Martinez
 * Date: 7/28/16
 * Time: 4:21 PM
 */
@javax.xml.bind.annotation.XmlSchema(xmlns={@XmlNs(prefix="atom", namespaceURI="http://www.w3.org/2005/Atom"),
        @XmlNs(prefix = "newznab", namespaceURI = "http://www.newznab.com/DTD/2010/feeds/attributes/")})
package net.longfalcon.newsj.ws.rss;

import javax.xml.bind.annotation.XmlNs;
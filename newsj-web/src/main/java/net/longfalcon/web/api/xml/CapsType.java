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

package net.longfalcon.web.api.xml;

import net.longfalcon.web.api.xml.caps.*;

import javax.xml.bind.annotation.*;

/**
 * User: longfalcon
 * Date: 3/1/16
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "caps")
@XmlType(name = "CapsType",propOrder = {"serverType","limitsType","registrationType","searchingType","categoriesType"})
public class CapsType extends ApiResponse {

    @XmlElement(name = "server")
    protected ServerType serverType;

    @XmlElement(name = "limits")
    protected LimitsType limitsType;

    @XmlElement(name = "registration")
    protected RegistrationType registrationType;

    @XmlElement(name = "searching")
    protected SearchingType searchingType;

    @XmlElement(name = "categories")
    protected CategoriesType categoriesType;

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }

    public LimitsType getLimitsType() {
        return limitsType;
    }

    public void setLimitsType(LimitsType limitsType) {
        this.limitsType = limitsType;
    }

    public RegistrationType getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(RegistrationType registrationType) {
        this.registrationType = registrationType;
    }

    public SearchingType getSearchingType() {
        return searchingType;
    }

    public void setSearchingType(SearchingType searchingType) {
        this.searchingType = searchingType;
    }

    public CategoriesType getCategoriesType() {
        return categoriesType;
    }

    public void setCategoriesType(CategoriesType categoriesType) {
        this.categoriesType = categoriesType;
    }
}

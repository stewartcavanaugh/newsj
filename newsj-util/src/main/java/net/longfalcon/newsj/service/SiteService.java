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

import net.longfalcon.newsj.model.Site;
import net.longfalcon.newsj.persistence.SiteDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Sten Martinez
 * Date: 11/10/15
 * Time: 8:22 PM
 */
@Service
public class SiteService {
    public static final int REGISTER_STATUS_OPEN = 0;
    public static final int REGISTER_STATUS_INVITE = 1;
    public static final int REGISTER_STATUS_CLOSED = 2;

    private SiteDAO siteDAO;

    @Transactional
    public void updateSite(Site site) {
        siteDAO.update(site);
    }

    public void setSiteDAO(SiteDAO siteDAO) {
        this.siteDAO = siteDAO;
    }
}

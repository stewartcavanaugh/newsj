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

import net.longfalcon.newsj.model.Content;
import net.longfalcon.newsj.persistence.ContentDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 11/10/15
 * Time: 11:24 AM
 */
@Service
public class ContentService {
    public static int TYPEUSEFUL = 1;
    public static int TYPEARTICLE = 2;
    public static int TYPEINDEX = 3;

    private static final Log _log = LogFactory.getLog(ContentService.class);

    private ContentDAO contentDAO;

    public List<Content> getForMenuByTypeAndRole(int type, int roleId) {
        return contentDAO.findByTypeAndRole(type, roleId, (roleId == UserService.ROLE_ADMIN));
    }

    public Content getIndex() {
        List<Content> indexContents = contentDAO.findByTypeAndRole(TYPEINDEX, UserService.ROLE_GUEST);
        if (!indexContents.isEmpty()) {
            return indexContents.get(0);
        }

        return null;
    }

    public ContentDAO getContentDAO() {
        return contentDAO;
    }

    public void setContentDAO(ContentDAO contentDAO) {
        this.contentDAO = contentDAO;
    }

    public Content getContent(long id) {
        return contentDAO.getById(id);
    }

    public List<Content> getAllContent() {
        return contentDAO.getAllContent();
    }

    @Transactional
    public void update(Content content) {
        contentDAO.update(content);
    }

    @Transactional
    public void delete(Content content) {
        contentDAO.delete(content);
    }

    @Transactional
    public void delete(long contentId) {
        contentDAO.deleteById(contentId);
    }

}

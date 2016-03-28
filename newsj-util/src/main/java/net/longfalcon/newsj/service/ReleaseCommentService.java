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

import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.model.ReleaseComment;
import net.longfalcon.newsj.persistence.ReleaseCommentDAO;
import net.longfalcon.newsj.persistence.ReleaseDAO;

/**
 * User: longfalcon
 * Date: 3/23/2016
 * Time: 12:37 PM
 */
public class ReleaseCommentService {

    private ReleaseCommentDAO releaseCommentDAO;
    private ReleaseDAO releaseDAO;

    public void addReleaseComment(ReleaseComment releaseComment) {
        if (releaseComment.getId() > 0) {
            ReleaseComment old = releaseCommentDAO.findByReleaseCommentId(releaseComment.getId());
            if (old != null) {
                throw new IllegalArgumentException("ReleaseComment must not exist");
            }
        }

        Release release = releaseComment.getRelease();
        int comments = release.getComments();
        release.setComments(comments+1);

        releaseCommentDAO.updateReleaseComment(releaseComment);
        releaseDAO.updateRelease(release);
    }

    public ReleaseCommentDAO getReleaseCommentDAO() {
        return releaseCommentDAO;
    }

    public void setReleaseCommentDAO(ReleaseCommentDAO releaseCommentDAO) {
        this.releaseCommentDAO = releaseCommentDAO;
    }

    public ReleaseDAO getReleaseDAO() {
        return releaseDAO;
    }

    public void setReleaseDAO(ReleaseDAO releaseDAO) {
        this.releaseDAO = releaseDAO;
    }
}

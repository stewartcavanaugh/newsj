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

package net.longfalcon.web;

import net.longfalcon.newsj.model.ForumPost;
import net.longfalcon.newsj.persistence.ForumPostDAO;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

/**
 * User: Sten Martinez
 * Date: 6/30/16
 * Time: 9:38 AM
 */
@Controller
public class AdminForumController extends BaseController {
    private static final Log _log = LogFactory.getLog(AdminForumController.class);

    @Autowired
    ForumPostDAO forumPostDAO;

    //TODO: move to POST only
    @RequestMapping("/admin/forum-delete")
    public View deleteForumPost(@RequestParam(value = "id", required = true)Long forumPostId,
                                @RequestParam(value = "from", required = false)String from,
                                Model model) {
        ForumPost forumPost = forumPostDAO.getForumPost(forumPostId);
        forumPostDAO.deleteForumPost(forumPost);

        if (ValidatorUtil.isNull(from)) {
            from = "/forum";
        }
        return safeRedirect(from);
    }
}

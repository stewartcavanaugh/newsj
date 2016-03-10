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

import net.longfalcon.newsj.model.ReleaseComment;
import net.longfalcon.newsj.persistence.ReleaseCommentDAO;
import net.longfalcon.newsj.util.ValidatorUtil;
import net.longfalcon.web.exception.NoSuchResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 3/9/16
 * Time: 2:39 PM
 */
@Controller
public class AdminCommentsController extends BaseController {

    @Autowired
    ReleaseCommentDAO releaseCommentDAO;

    @RequestMapping("/admin/comments-list")
    public String listCommentsView(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                                   Model model) {
        title = "Comments List";

        int pagerTotalItems = Math.toIntExact(releaseCommentDAO.countReleaseComments());
        List<ReleaseComment> commentList = releaseCommentDAO.getReleaseComments(offset, PAGE_SIZE);

        model.addAttribute("title", title);
        model.addAttribute("commentList", commentList);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        return "admin/comments-list";
    }

    // TODO: move to POST
    @RequestMapping("/admin/comments-delete")
    public View deleteCommentPost(@RequestParam(value = "id") Long id, Model model) throws NoSuchResourceException {
        ReleaseComment release = releaseCommentDAO.findByReleaseCommentId(id);
        if (release == null) {
            throw new NoSuchResourceException();
        }
        releaseCommentDAO.deleteReleaseComment(release);
        Integer offset = (Integer) model.asMap().get("pagerOffset");
        if (!ValidatorUtil.isNotNull(offset)) {
            offset = 0;
        }

        return safeRedirect("/admin/comments-list?offset=" + offset);
    }
}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 6/29/16
 * Time: 4:35 PM
 */
@Controller
public class ForumController extends BaseController {
    public static final String PAGE_TITLE = "Forum";

    @Autowired
    ForumPostDAO forumPostDAO;

    @RequestMapping("/forum")
    public String viewForum(@RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
                            Model model) {

        long pagerTotalItems = forumPostDAO.getForumPostCount();
        List<ForumPost> forumPosts = forumPostDAO.getForumPosts(offset, PAGE_SIZE);

        model.addAttribute("title", PAGE_TITLE);
        model.addAttribute("pagerTotalItems", pagerTotalItems);
        model.addAttribute("pagerOffset", offset);
        model.addAttribute("pagerItemsPerPage", PAGE_SIZE);
        model.addAttribute("forumPosts", forumPosts);
        return "forum";
    }

    @RequestMapping("/forumpost/{id}")
    public String viewForumPost(@PathVariable("id")long forumPostId, Model model) {

        ForumPost parentForumPost = forumPostDAO.getForumPost(forumPostId);
        List<ForumPost> forumPosts = forumPostDAO.getForumPostsByParent(forumPostId);

        model.addAttribute("title", parentForumPost.getSubject());
        model.addAttribute("pageMetaTitle", parentForumPost.getSubject());
        model.addAttribute("pageMetaKeywords", "view,forum,post,thread");
        model.addAttribute("pageMetaDescription", "View forum post");
        model.addAttribute("parentForumPost", parentForumPost);
        model.addAttribute("forumPosts", forumPosts);
        return "view-forum-post";
    }

    @RequestMapping(value = "/forum", method = RequestMethod.POST)
    public View postNewPost(@RequestParam("addSubject")String subject, @RequestParam("addMessage")String message, Model model) {
        long userId = getUserId();

        ForumPost forumPost = new ForumPost();
        forumPost.setForumId(1);
        forumPost.setParentId(0);
        forumPost.setUserId(userId);
        forumPost.setSubject(subject);
        forumPost.setMessage(message);
        forumPost.setLocked(false);
        forumPost.setSticky(false);
        forumPost.setReplies(0);
        forumPost.setCreateDate(new Date());
        forumPost.setUpdateDate(new Date());
        forumPostDAO.updateForumPost(forumPost);

        return safeRedirect("/forum");
    }

    @RequestMapping(value = "/forumpost/{id}/reply", method = RequestMethod.POST)
    public View postReply( @RequestParam("addReply")String message, @PathVariable("id")long parentPostId, Model model) {
        long userId = getUserId();

        ForumPost parentForumPost = forumPostDAO.getForumPost(parentPostId);
        int replies = parentForumPost.getReplies();

        ForumPost forumPost = new ForumPost();
        forumPost.setForumId(1);
        forumPost.setParentId(parentPostId);
        forumPost.setUserId(userId);
        forumPost.setSubject("RE: "+parentForumPost.getSubject());
        forumPost.setMessage(message);
        forumPost.setLocked(false);
        forumPost.setSticky(false);
        forumPost.setReplies(0);
        forumPost.setCreateDate(new Date());
        forumPost.setUpdateDate(new Date());
        forumPostDAO.updateForumPost(forumPost);

        parentForumPost.setReplies(replies+1);
        parentForumPost.setUpdateDate(new Date());
        forumPostDAO.updateForumPost(parentForumPost);

        return safeRedirect("/forumpost/" + String.valueOf(parentPostId));
    }

    @Override
    public String getPageMetaTitle() {
        return PAGE_TITLE;
    }

    @Override
    public String getPageMetaDescription() {
        return PAGE_TITLE;
    }

    @Override
    public String getPageMetaKeywords() {
        return "forum,chat,posts";
    }
}

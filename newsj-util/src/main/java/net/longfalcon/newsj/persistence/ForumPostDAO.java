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

package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.ForumPost;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 6/29/16
 * Time: 4:45 PM
 */
public interface ForumPostDAO {
    List<ForumPost> getForumPostsByParent(long parentId);

    List<ForumPost> getForumPosts(int offset, int pageSize);

    Long getForumPostCount();

    ForumPost getForumPost(long forumPostId);

    void updateForumPost(ForumPost forumPost);

    void deleteForumPost(ForumPost forumPost);
}

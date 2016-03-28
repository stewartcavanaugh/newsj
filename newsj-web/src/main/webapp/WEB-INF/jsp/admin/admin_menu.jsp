<%--
  ~ Copyright (c) 2016. Sten Martinez
  ~
  ~ This program is free software; you can redistribute it and/or modify
  ~ it under the terms of the GNU General Public License as published by
  ~ the Free Software Foundation; either version 2 of the License, or
  ~ (at your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License along
  ~ with this program; if not, write to the Free Software Foundation, Inc.,
  ~ 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
  --%>

<%--
  User: Sten Martinez
  Date: 12/16/15
  Time: 3:01 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Admin Functions</h2>
<ul>
    <li><a title="Home" href="${pageContext.request.contextPath}">Home</a></li>
    <li><a title="Admin Home" href="${pageContext.request.contextPath}/admin">Admin Home</a></li>
    <li><a title="Edit Site" href="${pageContext.request.contextPath}/admin/site-edit">Edit Site</a></li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/content-add?action=add">Add</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/content-list">Edit</a>
        Content Page
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/menu-list">View</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/menu-edit?action=add">Add</a>
        Menu Items
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/category-list">Edit</a>
        Categories
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/group-list">View</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/group-edit">Add</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/group-bulk">BulkAdd</a>
        Groups
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/regex-list">View</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/regex-edit?action=add">Add</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/regex-test">Test</a>
        <%--<a style="padding:0;" href="${pageContext.request.contextPath}/admin/regex-submit">Send</a>--%>
        Regex
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/binaryblacklist-list">View</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/binaryblacklist-edit?action=add">Add</a>
        Blacklist
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/release-list">View Releases</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/rage-list">View</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/rage-edit?action=add">Add</a>
        TVRage List
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/movie-list">View</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/movie-add">Add</a>
        Movie List
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/music-list">View Music List</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/console-list">View Console List</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/nzb-import">Import</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/nzb-export">Export</a>
        Nzb's
    </li>
   <%-- <li>
        <a href="${pageContext.request.contextPath}/admin/db-optimise">Optimise</a>
        Tables
    </li>--%>
    <li>
        <a href="${pageContext.request.contextPath}/admin/comments-list">View Comments</a>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/user-list">View</a>
        <a style="padding:0;" href="${pageContext.request.contextPath}/admin/user-edit?action=add">Add</a>
        Users
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/admin/site-stats">Site Stats</a>
    </li>
</ul>

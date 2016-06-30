<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<%--@elvariable id="isAdmin" type="boolean"--%>
<%--@elvariable id="loggedIn" type="boolean"--%>
<%--@elvariable id="userId" type="java.lang.String"--%>
<%--@elvariable id="site" type="net.longfalcon.newsj.model.Site"--%>
<%--@elvariable id="userData" type="net.longfalcon.view.UserData"--%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="common/common_head.jsp"%>
</head>
<body>
<a name="top"></a>

<%@include file="common/statusbar.jsp"%>

<%@include file="common/logo.jsp"%>
<hr />

<div id="header">
    <div id="menu">

        <c:if test="${loggedIn}">
            <%--Header Menu--%>
            <%@include file="common/header_menu.jsp"%>
        </c:if>

    </div>
</div>

<div id="page">

    <%@include file="common/adpanel.jsp"%>

    <div id="content">
        <%--START PAGE CONTENT--%>
            <h1><a href="${pageContext.request.contextPath}/forum">Forum</a></h1>

            <c:if test="${(not empty forumPosts) || (parentForumPost != null)}">
                <h2>${text:escapeHtml(parentForumPost.subject)}</h2>

                <div style="float:right;margin-bottom:5px;"><a href="#reply">Reply</a></div>

                <table style="width:100%;" class="data highlight" id="forumtable">
                    <tr>
                        <th>By</th>
                        <th>Message</th>
                    </tr>

                    <tr>
                        <td width="15%;">
                            <a title="View profile" href="${pageContext.request.contextPath}/profile/?name=${parentForumPost.username}">${parentForumPost.username}</a>
                            <br/>
                            on <span title="${parentForumPost.createDate}">${date:formatDate(parentForumPost.createDate)}</span> <div class="hint">(${date:timeAgo(parentForumPost.createDate)})</div>

                            <c:if test="${userData.role == 2}">
                                <div>
                                    <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/forum-delete?id=${parentForumPost.id}" title="Delete Post">Delete</a>
                                </div>
                            </c:if>

                        </td>
                        <td>${text:nl2br(text:escapeHtml(parentForumPost.message))}</td>
                    </tr>

                    <c:forEach items="${forumPosts}" var="forumPost" varStatus="rowNum">
                        <tr class="${text:cycle(rowNum, "alt", "")}">
                        <td <c:if test="${rowNum.last}">id="last"</c:if> width="15%;">
                            <a title="View profile" href="${pageContext.request.contextPath}/profile/?name=${forumPost.username}">${forumPost.username}</a>
                            <br/>
                            on <span title="${forumPost.createDate}">${date:formatDate(forumPost.createDate)}</span> <div class="hint">(${date:timeAgo(forumPost.createDate)})</div>

                            <c:if test="${userData.role == 2}">
                                <div>
                                    <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/forum-delete?id=${forumPost.id}&from=${text:urlEncode("/forumpost/")}${parentForumPost.id}" title="Delete Post">Delete</a>
                                </div>
                            </c:if>

                        </td>
                        <td>${text:nl2br(text:escapeHtml(forumPost.message))}</td>
                        </tr>    
                    </c:forEach>

                </table>

                <div style="float:right;margin-top:5px;"><a href="#">Top</a></div>


                <div style="margin-top:10px;">
                    <h3>Add Reply</h3>
                    <a id="reply"></a>
                    <form action="${pageContext.request.contextPath}/forumpost/${parentForumPost.id}/reply" method="post">
                        <label for="addReply">Message</label>:<br/>
                        <textarea maxlength="5000" id="addReply" name="addReply" rows="6" cols="60"></textarea>
                        <br/>
                        <input class="forumreplysubmit" type="submit" value="submit"/>
                    </form>
                </div>    
            </c:if>

        <%--END PAGE CONTENT--%>
    </div>

    <c:if test="${site.menuPosition == 1}">
        <%@include file="common/sidebar.jsp"%>
    </c:if>

    <div style="clear: both;text-align:right;">
        <a class="w3validator" href="http://validator.w3.org/check?uri=referer">
            <img src="${pageContext.request.contextPath}/resources/images/valid-xhtml10.png" alt="Valid XHTML 1.0 Transitional" height="31" width="88" />
        </a>
    </div>

</div>

<div class="footer">
    <p>
        ${site.footer}
        <br /><br /><br />
        <a title="Newznab - A usenet indexing web application with community features." href="http://www.newznab.com/">Newznab</a> is released under GPL. All rights reserved ${year}. <br/> <a title="Chat about newznab" href="http://www.newznab.com/chat.html">Newznab Chat</a> <br/><a href="${pageContext.request.contextPath}/terms-and-conditions">${site.title} Terms and Conditions</a>
    </p>
</div>

<c:if test="${!(empty site.googleAnalyticsAcc)}">
    <%@include file="common/google_analytics.jsp"%>
</c:if>
<c:if test="${loggedIn}">
    <input type="hidden" name="UID" value="${userId}" />
    <input type="hidden" name="RSSTOKEN" value="${rssToken}" />
</c:if>
</body>
</html>
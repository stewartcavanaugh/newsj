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
            <h1>Profile for ${text:escapeHtml(user.username)}</h1>

            <table class="data">
                <tr><th>Username:</th><td>${text:escapeHtml(user.username)}</td></tr>
                <c:if test="${(user.id == userData.id) || userData.role == 2}">
                    <tr><th title="Not public">Email:</th><td>${user.email}</td></tr>    
                </c:if>
                <tr><th>Registered:</th><td title="${user.createDate}">${date:formatDate(user.createDate)}  (${date:timeAgo(user.createDate)} ago)</td></tr>
                <tr><th>Last Login:</th><td title="${user.lastLogin}">${date:formatDate(user.lastLogin)}  (${date:timeAgo(user.lastLogin)} ago)</td></tr>
                <c:if test="${(user.id == userData.id) || userData.role == 2}">
                    <tr>
                        <th title="Not public">Site Api/Rss Key:</th>
                        <td>
                            <a href="${pageContext.request.contextPath}/rss?t=0&dl=1&i=${user.id}&r=${user.rssToken}">
                            ${user.rssToken}
                            </a>
                        </td>
                    </tr>    
                </c:if>
                <tr><th>Grabs:</th><td>${user.grabs}</td></tr>

                <c:if test="${((user.id == userData.id) || userData.role == 2) && siteObject.registerStatus == 1}">
                    <tr>
                        <th title="Not public">Invites:</th>
                        <td>${user.invites}
                            <c:if test="${user.invites > 0}">
                                [<a id="lnkSendInvite" onclick="return false;" href="#">Send Invite</a>]
                                <span title="Your invites will be reduced when the invitation is claimed." class="invitesuccess" id="divInviteSuccess">Invite Sent</span>
                                <span class="invitefailed" id="divInviteError"></span>
                                <div style="display:none;" id="divInvite">
                                    <form id="frmSendInvite" method="GET">
                                        <label for="txtInvite">Email</label>:
                                        <input type="text" id="txtInvite" />
                                        <input type="submit" value="Send"/>
                                    </form>
                                </div>
                            </c:if>

                        </td>
                    </tr>
                </c:if>

                <c:if test="${invitedByUser != null}">
                    <tr>
                        <th>Invited By:</th>
                        <td>
                            <a title="View ${userinvitedby.username}'s profile" href="${pageContext.request.contextPath}/profile?name=${userinvitedby.username}">
                            ${userinvitedby.username}
                            </a>
                        </td>
                    </tr>
                </c:if>

                <tr><th>UI Preferences:</th>
                    <td>
                        <c:choose>
                            <c:when test="${user.movieView == 1}">
                                View movie covers
                            </c:when>
                            <c:otherwise>
                                View standard movie category
                            </c:otherwise>
                        </c:choose>
                        <br/>
                        <c:choose>
                            <c:when test="${user.musicView == 1}">
                                View music covers
                            </c:when>
                            <c:otherwise>
                                View standard music category
                            </c:otherwise>
                        </c:choose>
                        <br/>
                        <c:choose>
                            <c:when test="${user.consoleView == 1}">
                                View console covers
                            </c:when>
                            <c:otherwise>
                                View standard console category
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <c:if test="${(user.id == userData.id) || userData.role == 2}">
                    <tr>
                        <th title="Not public">Excluded Categories:</th>
                        <td>
                            <c:forEach items="${exCatNames}" var="exCatName" varStatus="status">
                                ${exCatName}<c:if test="${!status.last}"><br/></c:if>
                            </c:forEach>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${user.id == userData.id}">
                    <tr>
                        <th></th>
                        <td>
                            <a href="${pageContext.request.contextPath}/profileedit">Edit</a>
                        </td>
                    </tr>
                </c:if>
            </table>

            <c:if test="${(not empty userReleaseComments) && pagerTotalItems > 0}">
                <div style="padding-top:20px;">
                    <a id="comments"></a>
                    <h2>Comments</h2>

                        <tags:pager pagerTotalItems="${pagerTotalItems}"
                                    pagerItemsPerPage="${pagerItemsPerPage}" pagerOffset="${pagerOffset}" pagerQueryBase="/profile?name=${user.username}&offset="/>

                    <table style="margin-top:10px;" class="data Sortable">

                        <tr>
                            <th>date</th>
                            <th>comment</th>
                        </tr>

                        <c:forEach items="${userReleaseComments}" var="comment">
                            <tr>
                                <td width="80" title="${comment.createDate}">${date:formatDate(comment.createDate)}</td>
                                <td>${text:nl2br(text:escapeHtml(comment.text))}</td>
                            </tr>
                        </c:forEach>
                    </table>
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

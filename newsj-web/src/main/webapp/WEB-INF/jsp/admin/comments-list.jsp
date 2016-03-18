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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="admin_common_head.jsp" %>
    <style type="text/css">
        .deprecated {
            text-decoration: line-through;
        }
    </style>
</head>
<body>
<div id="logo" style="cursor: pointer;">
    <h1><a href="${pageContext.request.contextPath}"></a></h1>

    <p><em></em></p>
</div>
<hr/>

<div id="header">
    <div id="menu">
    </div>
    <!-- end #menu -->
</div>

<div id="page">

    <div id="adpanel">

    </div>

    <div id="content">
        <%--START PAGE CONTENT--%>
            <h1>${title}</h1>

            <c:choose>
                <c:when test="${commentList.size() > 0}">
                    <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}"
                                pagerOffset="${pagerOffset}" pagerQueryBase="${pageContext.request.contextPath}/admin/comments-list?offset="/>
                    <table style="margin-top:10px;" class="data Sortable highlight">

                        <tr>
                            <th>user</th>
                            <th>date</th>
                            <th>comment</th>
                            <th>host</th>
                            <th>options</th>
                        </tr>

                        <c:forEach items="${commentList}" var="comment" varStatus="rowNum">
                            <tr class='${(rowNum.count % 2 == 0) ? "" : "alt"}'>
                                <td><a href="${pageContext.request.contextPath}/admin/user-edit?id=${comment.user.id}">${comment.user.username}</a></td>
                                <td title="${comment.createDate}">${date:formatDate(comment.createDate)}</td>
                                <td>${text:nl2br(text:escapeHtml(comment.text))}</td>
                                <td>${comment.host}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/details/${comment.release.guid}#comments">view</a> |
                                    <a href='javascript:$("#form-delete-releasecomment-${comment.id}").submit()'>delete</a>
                                    <form id="form-delete-releasecomment-${comment.id}" action="${pageContext.request.contextPath}/admin/comments-delete?id=${comment.id}" method="post"></form>
                                </td>
                            </tr>
                        </c:forEach>

                    </table>
                </c:when>
                <c:otherwise>
                    <p>No comments available</p>
                </c:otherwise>
            </c:choose>
        <%--END PAGE CONTENT--%>
    </div>
    <!-- end #content -->

    <div id="sidebar">
        <ul>
            <li>
                <%@include file="admin_menu.jsp"%>
            </li>

        </ul>
    </div>
    <!-- end #sidebar -->

    <div style="clear: both;">&nbsp;</div>

</div>
<!-- end #page -->

<div id="searchfooter">
    <center>
    </center>
</div>

<div class="footer">
    <p>
        ${site.footer}
        <br/><br/><br/>Copyright &copy; ${year} ${site.title}. All rights reserved.
    </p>
</div>
<!-- end #footer -->

<c:if test="${!(empty site.googleAnalyticsAcc)}">
    <%@include file="../common/google_analytics.jsp"%>
</c:if>
</body>
</html>
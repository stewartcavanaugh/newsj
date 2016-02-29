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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

        <table class="data Sortable highlight">

            <tr>
                <th width="60">ordinal</th>
                <th width="30">id</th>
                <th>title</th>
                <th>url</th>
                <th>type</th>
                <th>status</th>
                <th>role</th>
                <th>in menu</th>
                <th>body</th>
                <th>options</th>
            </tr>

            <c:forEach items="${contentList}" var="content" varStatus="rowNum">
                <tr class='${(rowNum.count % 2 == 0) ? "" : "alt"}'>
                    <td>${content.ordinal}</td>
                    <td>${content.id}</td>
                    <td><a href="${pageContext.request.contextPath}/admin/content-add?id=${content.id}" title="Edit ${content.title}">${content.title}</a></td>
                    <td><a title="Preview in new window" href="${pageContext.request.contextPath}/content/${content.id}${content.url}" target="null">${content.url}</a></td>
                    <td>
                        <c:choose>
                            <c:when test="${content.contentType == 1}">
                                Useful Link
                            </c:when>
                            <c:when test="${content.contentType == 2}">
                                Article
                            </c:when>
                            <c:when test="${content.contentType == 3}">
                                Homepage
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${content.status == 1}">
                                Enabled
                            </c:when>
                            <c:otherwise>
                                Disabled
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${content.role == 0}">
                                Everyone
                            </c:when>
                            <c:when test="${content.role == 1}">
                                Users
                            </c:when>
                            <c:when test="${content.role == 2}">
                                Admins
                            </c:when>
                        </c:choose>
                    </td>
                    <td style="width:50px;">
                        <c:choose>
                            <c:when test="${content.showInMenu == 1}">
                                Yes
                            </c:when>
                            <c:otherwise>
                                No
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td title="${text:escapeHtml(content.body)}">${text:truncate(text:escapeHtml(content.body), 100)}</td>
                    <td>
                        <c:if test="${content.contentType != 3}">
                            <a class="confirm_action" href="${pageContext.request.contextPath}/admin/content-delete?id=${content.id}">delete</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>

        </table>

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
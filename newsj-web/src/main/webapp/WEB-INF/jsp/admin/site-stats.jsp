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
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
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

            <h2>Top Grabbers</h2>

            <table style="width:100%;margin-top:10px;" class="data highlight">
                <tr>
                    <th>User</th>
                    <th>Grabs</th>
                </tr>

                <c:forEach items="${topGrabberList}" var="result" varStatus="rowNum">
                    <tr class='${(rowNum.count % 2 == 0) ? "" : "alt"}'>
                    <td width="75%"><a href="${pageContext.request.contextPath}/admin/user-edit?id=${result.id}">${result.username}</a></td>
                    <td>${result.grabs}</td>
                    </tr>
                </c:forEach>
            </table>

            <br/><br/>

            <h2>Top Downloads</h2>

            <table style="width:100%;margin-top:10px;" class="data highlight">
                <tr>
                    <th>Release</th>
                    <th>Grabs</th>
                    <th>Days Ago</th>
                </tr>
                <c:forEach items="${topDownloadsList}" var="result" varStatus="rowNum">
                    <tr class='${(rowNum.count % 2 == 0) ? "" : "alt"}'>
                        <td width="75%"><a href="${pageContext.request.contextPath}/details/${result.guid}/${text:escapeHtml(result.searchName)}">${text:escapeHtml(result.searchName).replace("."," ")}</a>
                            <c:if test="${isAdmin}"><a href="${pageContext.request.contextPath}/admin/release-edit?id=${result.id}">[Edit]</a></c:if>
                        </td>
                        <td>${result.grabs}</td>
                        <td>${date:timeAgo(result.addDate)}</td>
                    </tr>
                </c:forEach>

            </table>

            <br/><br/>

            <h2>Releases Added In Last 7 Days</h2>

            <table style="width:100%;margin-top:10px;" class="data highlight">
                <tr>
                    <th>Category</th>
                    <th>Releases</th>
                </tr>

                <c:forEach items="${recentlyAddedCategories}" var="result" varStatus="rowNum">
                    <tr class='${(rowNum.count % 2 == 0) ? "" : "alt"}'>
                        <td width="75%">${result.categoryName}</td>
                        <td>${result.count}</td>
                    </tr>
                </c:forEach>

            </table>

            <br/><br/>

            <h2>Top Comments</h2>

            <table style="width:100%;margin-top:10px;" class="data highlight">
                <tr>
                    <th>Release</th>
                    <th>Comments</th>
                    <th>Days Ago</th>
                </tr>

                <c:forEach items="${topCommentsList}" var="result" varStatus="rowNum">
                    <tr class='${(rowNum.count % 2 == 0) ? "" : "alt"}'>
                        <td width="75%"><a href="${pageContext.request.contextPath}/details/${result.guid}/${text:escapeHtml(result.searchName)}#comments">${text:escapeHtml(result.searchName).replace("."," ")}</a></td>
                        <td>${result.comments}</td>
                        <td>${date:timeAgo(result.addDate)}</td>
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
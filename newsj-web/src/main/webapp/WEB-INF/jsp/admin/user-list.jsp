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

            <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}"
                        pagerOffset="${pagerOffset}" pagerQueryBase="${pageContext.request.contextPath}/admin/user-list?ob=${orderBy}&offset="/>

            <table style="margin-top:10px;" class="data highlight">

                <tr>
                    <th>name<br/>
                        <a title="Sort Descending" href="${pageContext.request.contextPath}/admin/user-list?ob=username_desc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                        </a>
                        <a title="Sort Ascending" href="${pageContext.request.contextPath}/admin/user-list?ob=username_asc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                        </a>
                    </th>
                    <th>email<br/>
                        <a title="Sort Descending" href="${pageContext.request.contextPath}/admin/user-list?ob=email_desc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                        </a>
                        <a title="Sort Ascending" href="${pageContext.request.contextPath}/admin/user-list?ob=email_asc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                        </a>
                    </th>
                    <th>host<br/>
                        <a title="Sort Descending" href="${pageContext.request.contextPath}/admin/user-list?ob=host_desc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                        </a>
                        <a title="Sort Ascending" href="${pageContext.request.contextPath}/admin/user-list?ob=host_asc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                        </a>
                    </th>
                    <th>join date<br/>
                        <a title="Sort Descending" href="${pageContext.request.contextPath}/admin/user-list?ob=createdate_desc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                        </a>
                        <a title="Sort Ascending" href="${pageContext.request.contextPath}/admin/user-list?ob=createdate_asc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                        </a>
                    </th>
                    <th>last login<br/>
                        <a title="Sort Descending" href="${pageContext.request.contextPath}/admin/user-list?ob=lastlogin_desc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                        </a>
                        <a title="Sort Ascending" href="${pageContext.request.contextPath}/admin/user-list?ob=lastlogin_asc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                        </a>
                    </th>
                    <th>api access<br/>
                        <a title="Sort Descending" href="${pageContext.request.contextPath}/admin/user-list?ob=apiaccess_desc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                        </a>
                        <a title="Sort Ascending" href="${pageContext.request.contextPath}/admin/user-list?ob=apiaccess_asc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                        </a>
                    </th>
                    <th>grabs<br/>
                        <a title="Sort Descending" href="${pageContext.request.contextPath}/admin/user-list?ob=grabs_desc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                        </a>
                        <a title="Sort Ascending" href="${pageContext.request.contextPath}/admin/user-list?ob=grabs_asc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                        </a>
                    </th>
                    <th>invites</th>
                    <th>role<br/>
                        <a title="Sort Descending" href="${pageContext.request.contextPath}/admin/user-list?ob=role_desc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                        </a>
                        <a title="Sort Ascending" href="${pageContext.request.contextPath}/admin/user-list?ob=role_asc&offset=${pagerOffset}">
                            <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                        </a>
                    </th>
                    <th>options</th>
                </tr>

                <c:forEach items="${userList}" var="user" varStatus="rowNum">
                <tr class='${(rowNum.count % 2 == 0) ? "" : "alt"}'>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/user-edit?id=${user.id}">${user.username}</a>
                    </td>
                    <td>${user.email}</td>
                    <td>${user.host}</td>
                    <td title="${user.createDate}">${date:formatDate(user.createDate)}</td>
                    <td title="${user.lastLogin}">${date:formatDate(user.lastLogin)}</td>
                    <td title="${user.apiAccess}">${date:formatDate(user.apiAccess)}</td>
                    <td>${user.grabs}</td>
                    <td>${user.invites}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.role == 1}">
                                User
                            </c:when>
                            <c:when test="${user.role == 2}">
                                Admin
                            </c:when>
                            <c:when test="${user.role == 3}">
                                Disabled
                            </c:when>
                            <c:otherwise>
                                Unknown
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:if test="${user.role != 2}">
                            <form id="user-delete-form-${user.id}" action="${pageContext.request.contextPath}/admin/user-delete?id=${user.id}" method="POST">
                            <input class="confirm_action" type="submit" value="Delete" />
                            </form>
                            <%--<a class="confirm_action" href="${pageContext.request.contextPath}/admin/user-delete?id=${user.id}">delete</a>--%>
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
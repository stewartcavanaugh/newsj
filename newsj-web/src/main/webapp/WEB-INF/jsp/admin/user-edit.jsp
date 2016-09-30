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

            <c:if test='${error != ""}'>
                <div class="error">${error}</div>
            </c:if>

            <form:form modelAttribute="user" action="${pageContext.request.contextPath}/admin/user-edit" method="POST">

                <table class="input">

                    <tr>
                        <td>Name:</td>
                        <td>
                            <form:hidden path="userId" name="id" value="${user.userId}" />
                            <form:input autocomplete="off" path="username" class="long" name="username" type="text" value="${user.username}" />
                        </td>
                    </tr>

                    <tr>
                        <td>Email:</td>
                        <td>
                            <form:input path="email" autocomplete="off" class="long" name="email" type="text" value="${user.email}" />
                        </td>
                    </tr>

                    <tr>
                        <td>Password:</td>
                        <td>
                            <form:password path="password" autocomplete="off" class="long" name="password" value="" />
                            <c:if test="${user.userId > 0}">
                                <div class="hint">Only enter a password if you want to change it.</div>
                            </c:if>
                        </td>
                    </tr>
                    <c:if test="${user.userId > 0}">
                        <tr>
                            <td>Grabs:</td>
                            <td>
                                <form:input path="grabs" class="short" name="grabs" value="${user.grabs}" />
                            </td>
                        </tr>
                    </c:if>

                    <tr>
                        <td>Invites:</td>
                        <td>
                            <form:input path="invites" class="short" name="invites"  value="${user.invites}" />
                        </td>
                    </tr>

                    <tr>
                        <td>Movie View:</td>
                        <td>
                            <form:checkbox path="movieView" name="movieview" value="1" />
                        </td>
                    </tr>

                    <tr>
                        <td>Music View:</td>
                        <td>
                            <form:checkbox path="musicView" name="musicview" value="1" />
                        </td>
                    </tr>

                    <tr>
                        <td>Console View:</td>
                        <td>
                            <form:checkbox path="consoleView" name="consoleview" value="1" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="role">Role</label>:</td>
                        <td>
                            <form:radiobuttons path="role" id="role" items="${roleMap}"/>
                        </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" value="Save" />
                        </td>
                    </tr>

                </table>

            </form:form>
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
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
  Date: 11/10/15
  Time: 4:18 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="statusbar">
    <c:choose>
        <c:when test="${loggedIn}">
            Welcome back <a href="${pageContext.request.contextPath}/profile">${userData.username}</a>. <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/login">Login</a> or <a href="${pageContext.request.contextPath}/register">Register</a>
        </c:otherwise>
    </c:choose>
</div>

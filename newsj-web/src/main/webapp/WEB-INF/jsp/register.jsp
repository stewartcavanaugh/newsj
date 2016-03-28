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
  Time: 5:36 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="common/common_head.jsp" %>
</head>
<body>
<a name="top"></a>

<%@include file="common/statusbar.jsp" %>

<%@include file="common/logo.jsp" %>
<hr/>

<div id="header">
    <div id="menu">

        <c:if test="${loggedIn}">
            <%--Header Menu--%>
            <%@include file="common/header_menu.jsp" %>
        </c:if>

    </div>
</div>

<div id="page">

    <%@include file="common/adpanel.jsp" %>

    <div id="content">
        <%--START PAGE CONTENT--%>
        <h1>Register</h1>

        <c:if test="${!(empty error)}">
            <div class="error">${error}</div>
        </c:if>

        <c:if test="${showregister}">
            <form:form modelAttribute="registerForm" method="post" action="register?action=submit">

                <table style="width:500px;" class="data">
                    <tr>
                        <th width="75px;"><label for="username">Username</label>: <em>*</em></th>
                        <td>
                            <form:input autocomplete="off" id="username" name="username" path="userName" type="text"/>
                            <div class="hint">Should be at least three characters and start with a letter.</div>
                        </td>
                    </tr>
                    <tr>
                        <th><label for="password">Password</label>: <em>*</em></th>
                        <td>
                            <form:input id="password" autocomplete="off" name="password" path="password"
                                        type="password"/>
                            <form:input id="invitecode" name="invitecode" type="hidden" path="inviteCode"/>
                            <div class="hint">Should be at least six characters long.</div>
                        </td>
                    </tr>
                    <tr>
                        <th><label for="confirmpassword">Confirm Password</label>: <em>*</em></th>
                        <td><form:input autocomplete="off" id="confirmpassword" name="confirmpassword"
                                   path="confirmPassword" type="password"/></td>
                    </tr>
                    <tr>
                        <th><label for="email">Email</label>: <em>*</em></th>
                        <td><form:input autocomplete="off" id="email" name="email" path="email" type="text"/></td>
                    </tr>
                </table>

                <table style="width:500px; margin-top:10px;" class="data">
                    <tr>
                        <th width="75px;"></th>
                        <td><input type="submit" value="Register"/>

                            <div style="float:right;" class="hint"><em>*</em> Indicates mandatory field.</div>
                        </td>
                    </tr>
                </table>

            </form:form>
        </c:if>
        <%--END PAGE CONTENT--%>
    </div>

    <c:if test="${site.menuPosition == 1}">
        <%@include file="common/sidebar.jsp" %>
    </c:if>

    <div style="clear: both;text-align:right;">
        <a class="w3validator" href="http://validator.w3.org/check?uri=referer">
            <img src="${pageContext.request.contextPath}/resources/images/valid-xhtml10.png"
                 alt="Valid XHTML 1.0 Transitional" height="31" width="88"/>
        </a>
    </div>

</div>

<div class="footer">
    <p>
        ${site.footer}
        <br/><br/><br/>
        <a title="Newznab - A usenet indexing web application with community features." href="http://www.newznab.com/">Newznab</a> is released under GPL. All rights reserved ${year}. <br/>
            <a title="Chat about newznab" href="http://www.newznab.com/chat.html">Newznab Chat</a>
            <br/>
            <a href="${pageContext.request.contextPath}/terms-and-conditions">${site.title} Terms and
        Conditions</a>
    </p>
</div>

<c:if test="${!(empty site.googleAnalyticsAcc)}">
    <%@include file="common/google_analytics.jsp" %>
</c:if>
<c:if test="${loggedIn}">
    <input type="hidden" name="UID" value="${userId}"/>
    <input type="hidden" name="RSSTOKEN" value="${rssToken}"/>
</c:if>
</body>
</html>

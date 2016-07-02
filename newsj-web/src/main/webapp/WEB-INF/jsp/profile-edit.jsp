<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            <h1>Edit your profile</h1>

            <c:if test="${!text:isNull(error)}">
                <div class="error">${error}</div>
            </c:if>

            <br/><br/>

            <form:form modelAttribute="profile" action="${pageContext.request.contextPath}/profileedit" method="post">

                <h2>User Details</h2>
                <table class="data">
                    <tr><th>Username:</th><td>${text:escapeHtml(user.username)}</td></tr>
                    <tr><th>Email:</th><td><form:input id="email" name="email" path="email" value="${text:escapeHtml(profile.email)}"/></td></tr>
                    <tr><th>Password:</th>
                        <td>
                            <form:password autocomplete="off" id="password" name="password" path="password" value=""/>
                            <div class="hint">Only enter your password if you want to change it.</div>
                        </td>
                    </tr>
                    <tr><th>Confirm Password:</th><td><form:password autocomplete="off" id="confirmpassword" name="confirmpassword" path="confirmPassword" value=""/>
                    </td></tr>
                    <tr><th>Site Api/Rss Key:</th><td>${user.rssToken}<br/><a class="confirm_action" href="${pageContext.request.contextPath}/profileedit/newapikey">Generate</a></td></tr>
                    <tr><th>View Movie Page:</th>
                        <td>
                            <form:checkbox path="movieView" id="movieview" name="movieview"/>
                            <div class="hint">Browse movie covers. Only shows movies with known IMDB info.</div>
                        </td>
                    </tr>
                    <tr><th>View Music Page:</th>
                        <td>
                            <form:checkbox path="musicView" id="musicview" name="musicview"/>
                            <div class="hint">Browse music covers. Only shows music with known lookup info.</div>
                        </td>
                    </tr>
                    <tr><th>View Console Page:</th>
                        <td>
                            <form:checkbox path="consoleView" id="consoleview" name="consoleview"/>
                            <div class="hint">Browse console covers. Only shows games with known lookup info.</div>
                        </td>
                    </tr>
                    <tr><th>Excluded Categories:</th>
                        <td>
                            <form:select path="exCatIds" items="${categoryVOList}" itemLabel="displayName" itemValue="id"/>
                            <div class="hint">Use Ctrl and click to exclude multiple categories.</div>
                        </td>
                    </tr>
                    <tr><th></th><td><input type="submit" value="Save" /></td></tr>
                </table>

                <br/><br/>

            </form:form>

            <h2>SABnzbd Integration</h2>
            <table class="data">
                <tr>
                    <th title="Not public">API Key:</th>
                    <td>
                        <input id="profile_sab_apikey" type="text" size="40" />
                    </td>
                </tr>
                <tr>
                    <th title="Not public">Hostname:</th>
                    <td>
                        <input id="profile_sab_host" type="text" size="40" value="http://localhost:8080/sabnzbd/"/>
                        <div class="hint">for example: http://localhost:8080/sabnzbd/</div>
                    </td>
                </tr>

                <tr>
                    <th title="Not public">Added Priority:</th>
                    <td>
                        <select id="profile_sab_priority">
                            <option value="1">High</option>
                            <option value="2">Normal</option>
                            <option value="3">Low</option>
                        </select>
                    </td>
                </tr>

                <tr><th title="Not public"></th><td>
                    <input id="profile_sab_clear" type="button" value="Clear" style="float:right;" />
                    <input id="profile_sab_save" type="button" value="Save to Cookie" style="float:left;" />
                    <div class="icon"></div>
                </td></tr>
            </table>

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

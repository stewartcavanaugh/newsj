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
  Date: 11/12/15
  Time: 2:47 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>${title}</title>
    <link href="${pageContext.request.contextPath}/resources/styles/install.css" rel="stylesheet" type="text/css" media="screen" />
    <link rel="shortcut icon" type="image/ico" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>
<body>
<h1 id="logo"><img alt="Newznab" src="${pageContext.request.contextPath}/resources/images/banner.jpg" /></h1>
<div class="content">
    <h2>${title}</h2>
    <%--START PAGE CONTENT--%>
    <p>You must set the NZB file path. This is the location where the NZB files are stored:</p>
    <form:form modelAttribute="installerForm" action="install2" method="post">
        <table width="100%" border="0" style="margin-top:10px;" class="data highlight">
            <tr class="alt">
                <td><label for="nzbpath">Location:</label></td>
                <td><form:input path="nzbFileLocation" id="nzbpath" type="text" name="nzbpath" value="${nzbPath}" size="70" /></td>
            </tr>
        </table>

        <div style="padding-top:20px; text-align:center;">
            <c:if test="${errorSet.size() > 0}">
                <div>
                    The following error(s) were encountered:<br />
                    <c:forEach items="${errorSet}" var="errorMessage">
                        <span class="error">&bull; ${errorMessage}</span><br />
                    </c:forEach>

                    <br />
                </div>
            </c:if>
            <input type="submit" value="Set NZB File Path" />
        </div>

    </form:form>
    <%--END PAGE CONTENT --%>
    <div class="footer">
        <p><br /><a href="http://www.newznab.com/">newznab</a> is released under GPL. All rights reserved ${year}.</p>
    </div>
</div>
</body>
</html>

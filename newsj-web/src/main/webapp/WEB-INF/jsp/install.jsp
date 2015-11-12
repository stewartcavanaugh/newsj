<%--
  ~ Copyright (c) 2015. Sten Martinez
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
  Date: 11/11/15
  Time: 9:28 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>${title}</title>
    <link href="${pageContext.request.contextPath}/resources/styles/install.css" rel="stylesheet" type="text/css" media="screen" />
    <link rel="shortcut icon" type="image/ico" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
    {$page->head}
</head>
<body>
<h1 id="logo"><img alt="Newznab" src="${pageContext.request.contextPath}/resources/images/banner.jpg" /></h1>
<div class="content">
    <h2>${title}</h2>
    <%--START PAGE CONTENT--%>

    <%--END PAGE CONTENT --%>
    <div class="footer">
        <p><br /><a href="http://www.newznab.com/">newznab</a> is released under GPL. All rights reserved {$smarty.now|date_format:"%Y"}.</p>
    </div>
</div>
</body>
</html>


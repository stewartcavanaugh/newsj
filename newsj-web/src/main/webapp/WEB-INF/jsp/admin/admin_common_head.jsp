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

<%--@elvariable id="site" type="net.longfalcon.newsj.model.Site"--%>
<%--
  User: Sten Martinez
  Date: 12/16/15
  Time: 2:47 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>${site.title} - ${pageMetaTitle}</title>
<link href="${pageContext.request.contextPath}/resources/styles/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="${pageContext.request.contextPath}/resources/styles/admin.css" rel="stylesheet" type="text/css" media="screen" />
<c:if test="${(site.style != '') && (site.style != '/')}">
    <link href="${pageContext.request.contextPath}/resources/themes/${site.style}/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="${pageContext.request.contextPath}/resources/themes/${site.style}/admin.css" rel="stylesheet" type="text/css" media="screen" />
</c:if>
<link rel="shortcut icon" type="image/ico" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/sorttable.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/utils-admin.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.multifile.js"></script>
<script type="text/javascript">
    var WWW_TOP = "${pageContext.request.contextPath}";
</script>


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
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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

            <form:form modelAttribute="consoleInfo" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/console-edit" method="post">

                <form:hidden path="id" name="id" value="${consoleInfo.id}" />

                <table class="input">

                    <tr>
                        <td><label for="title">Title</label>:</td>
                        <td>
                            <form:input path="title" id="title" class="long" name="title"  value="${text:escapeHtml(consoleInfo.title)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="asin">ASIN</label>:</td>
                        <td>
                            <form:input path="asin" id="asin" name="asin" value="${consoleInfo.asin}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="url">URL</label>:</td>
                        <td>
                            <form:input path="url" id="url" class="long" name="url"  value="${text:escapeHtml(consoleInfo.url)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="salesrank">Sales Rank</label>:</td>
                        <td>
                            <form:input path="salesRank" id="salesrank" class="short"  name="salesrank" value="${consoleInfo.salesRank}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="platform">Platform</label>:</td>
                        <td>
                            <form:input path="platform" id="platform" class="long" name="platform"  value="${consoleInfo.platform}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="publisher">Publisher</label>:</td>
                        <td>
                            <form:input path="publisher" id="publisher" class="long" name="publisher"  value="${consoleInfo.publisher}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="releasedate">Release Date</label>:</td>
                        <td>
                            <form:input path="releaseDate" id="releasedate" name="releasedate" type="date" value="${date:formatDate(consoleInfo.releaseDate)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="esrb">Rating</label>:</td>
                        <td>
                            <form:input path="esrb" id="esrb" class="short" name="esrb"  value="${consoleInfo.esrb}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="genre">Genre</label>:</td>
                        <td>
                            <form:select path="genreId" id="genre" name="genre">
                                <form:options items="${genreMap}"/>
                            </form:select>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="cover">Cover Image</label>:</td>
                        <td>
                            <input type="file" id="cover" name="file" />
                            <c:if test="${consoleInfo.cover}">
                                <img style="max-width:200px; display:block;" src="${pageContext.request.contextPath}/images/covers/console/${consoleInfo.id}.jpg" alt="" />
                            </c:if>
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
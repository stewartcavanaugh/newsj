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
<%@taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
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

            <form:form modelAttribute="release" action="${pageContext.request.contextPath}/admin/release-edit" method="POST">

                <input type="hidden" name="from" value="" />

                <table class="input">

                    <tr>
                        <td><label for="name">Original Name</label>:</td>
                        <td>
                            <form:hidden path="id" name="id" value="${release.id}" />
                            <form:input path="name" id="name" class="long" name="name" value="${text:escapeHtml(release.name)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="searchname">Search Name</label>:</td>
                        <td>
                            <form:input path="searchName" id="searchname" class="long" name="searchname" value="${text:escapeHtml(release.searchName)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="fromname">From Name</label>:</td>
                        <td>
                            <form:input path="fromName" id="fromname" class="long" name="fromname" value="${text:escapeHtml(release.fromName)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="category">Category</label>:</td>
                        <td>
                            <form:select path="categoryId" id="category" name="category">
                                <form:options items="${categoriesMap}"/>
                            </form:select>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="totalpart">Parts</label>:</td>
                        <td>
                            <form:input path="totalpart" id="totalpart" class="short" name="totalpart" value="${release.totalpart}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="grabs">Grabs</label>:</td>
                        <td>
                            <form:input path="grabs" id="grabs" class="short" name="grabs" value="${release.grabs}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="rageID">Tv Rage Id</label>:</td>
                        <td>
                            <form:input path="rageId" id="rageID" class="short" name="rageID" value="${release.rageId}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="seriesfull">Series Full</label>:</td>
                        <td>
                            <form:input path="seriesFull" id="seriesfull" class="long" name="seriesfull" value="${release.seriesFull}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="season">Season</label>:</td>
                        <td>
                            <form:input path="season" id="season" class="short" name="season" value="${release.season}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="episode">Episode</label>:</td>
                        <td>
                            <form:input path="episode" id="episode" class="short" name="episode" value="${release.episode}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="episode">IMDB Id</label>:</td>
                        <td>
                            <form:input path="imdbId" id="imdbID" class="short" name="imdbID" value="${release.imdbId}" />
                        </td>
                    </tr>

                    <tr>
                        <td>Group:</td>
                        <td>
                            ${release.groupName}
                        </td>
                    </tr>

                    <tr>
                        <td>Regex ID:</td>
                        <td>
                            ${release.regexId}
                        </td>
                    </tr>

                    <tr>
                        <td><label for="size">Size</label>:</td>
                        <td>
                            <form:input path="size" id="size" class="long" name="size" value="${release.size}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="postdate">Posted Date</label>:</td>
                        <td>
                            <form:input path="postDate" id="postdate" class="long" name="postdate" value="${release.postDate}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="adddate">Added Date</label>:</td>
                        <td>
                            <form:input path="addDate" id="adddate" class="long" name="adddate" value="${release.addDate}" />
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
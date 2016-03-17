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

            <form:form modelAttribute="movieInfo" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/movie-edit" method="post">

                <form:hidden path="id" name="id" value="${movieInfo.id}" />

                <table class="input">

                    <tr>
                        <td><label for="title">IMDB ID</label>:</td>
                        <td>${movieInfo.imdbId}</td>
                    </tr>

                    <tr>
                        <td><label for="title">TMDb ID</label>:</td>
                        <td>${movieInfo.tmdbId}</td>
                    </tr>

                    <tr>
                        <td><label for="title">Title</label>:</td>
                        <td>
                            <form:input id="title" class="long" name="title" path="title" value="${text:escapeHtml(movieInfo.title)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="tagline">Tagline</label>:</td>
                        <td>
                            <form:input id="tagline" class="long" name="tagline" path="tagline" value="${text:escapeHtml(movieInfo.tagline)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="plot">Plot</label>:</td>
                        <td>
                            <form:textarea id="plot" name="plot" path="plot"/>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="year">Year</label>:</td>
                        <td>
                            <form:input id="year" class="short" name="year" path="year" value="${movieInfo.year}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="rating">Rating</label>:</td>
                        <td>
                            <form:input id="rating" class="short" name="rating" path="rating" value="${movieInfo.rating}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="genre">Genre</label>:</td>
                        <td>
                            <form:input id="genre" class="long" name="genre" path="genre" value="${text:escapeHtml(movieInfo.genre)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="director">Director</label>:</td>
                        <td>
                            <form:input id="director" class="long" name="director" path="director" value="${text:escapeHtml(movieInfo.director)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="actors">Actors</label>:</td>
                        <td>
                            <form:textarea id="actors" name="actors" path="actors"/>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="language">Language</label>:</td>
                        <td>
                            <form:input id="language" class="long" name="language" path="language" value="${text:escapeHtml(movieInfo.language)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="cover">Cover Image</label>:</td>
                        <td>
                            <input type="file" id="cover" name="coverImage" />
                            <c:if test="${movieInfo.cover}">
                                <img style="max-width:200px; display:block;" src="${pageContext.request.contextPath}/images/covers/movies/${movieInfo.id}-cover.jpg" alt="" />
                            </c:if>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="backdrop-preview">Backdrop Image</label>:</td>
                        <td>
                            <input type="file" id="backdrop-preview" name="backdropImage" />
                            <c:if test="${movieInfo.backdrop}">
                                <img style="max-width:200px; display:block;" src="${pageContext.request.contextPath}/images/covers/movies/${movieInfo.id}-backdrop.jpg" alt="" />
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
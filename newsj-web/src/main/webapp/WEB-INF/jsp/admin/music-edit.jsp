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

            <form:form modelAttribute="musicInfo" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/music-edit" method="post">

                <form:hidden path="id" name="id" value="${musicInfo.id}" />

                <table class="input">

                    <tr>
                        <td><label for="title">Title</label>:</td>
                        <td>
                            <form:input id="title" class="long" name="title" path="title" value="${text:escapeHtml(musicInfo.title)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="asin">ASIN</label>:</td>
                        <td>
                            <form:input id="asin" name="asin" path="asin" value="${musicInfo.asin}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="url">URL</label>:</td>
                        <td>
                            <form:input id="url" class="long" name="url" path="url" value="${text:escapeHtml(musicInfo.url)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="salesrank">Sales Rank</label>:</td>
                        <td>
                            <form:input id="salesrank" class="short" path="salesRank" name="salesrank" value="${musicInfo.salesRank}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="artist">Artist</label>:</td>
                        <td>
                            <form:input id="artist" class="long" name="artist" path="artist" value="${musicInfo.artist}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="publisher">Publisher</label>:</td>
                        <td>
                            <form:input id="publisher" class="long" name="publisher" path="publisher" value="${musicInfo.publisher}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="releasedate">Release Date</label>:</td>
                        <td>
                            <form:input id="releasedate" name="releasedate" path="releaseDate" type="date" value="${date:formatDate(musicInfo.releaseDate)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="year">Year</label>:</td>
                        <td>
                            <form:input id="year" class="short" name="year" path="year" value="${musicInfo.year}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="genre">Genre</label>:</td>
                        <td>
                            <form:select path="genreId" id="genre">
                                <form:options items="${genreList}" itemValue="id" itemLabel="title"/>
                            </form:select>
                            <%--<select id="genre" name="genre">
                                {foreach from=$genres item=gen}
                                <option {if $gen.ID == $music.genreID}selected="selected"{/if} value="{$gen.ID}">{$gen.title|escape:'htmlall'}</option>
                                {/foreach}
                            </select>--%>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="tracks">Tracks</label>:</td>
                        <td>
                            <form:textarea id="tracks" name="tracks" path="tracks"/>
                            <div class="hint">Tracks separated by | (pipe) delimeter</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="cover">Cover Image</label>:</td>
                        <td>
                            <input type="file" id="cover" name="coverImage" />
                            <c:if test="${musicInfo.cover}">
                                <img style="max-width:200px; display:block;" src="${pageContext.request.contextPath}/images/covers/music/${musicInfo.id}.jpg" alt="" />
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
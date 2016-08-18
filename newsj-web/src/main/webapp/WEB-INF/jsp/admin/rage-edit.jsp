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

            <form:form modelAttribute="tvRage" enctype="multipart/form-data" action="${pageContext.request.contextPath}/admin/rage-edit" method="POST">

                <input type="hidden" name="from" value="${from}" />

                <table class="input">

                    <tr>
                        <td><label for="rageID">Rage Id</label>:</td>
                        <td>
                            <form:hidden path="id" name="id" value="${tvRage.id}" />
                            <form:input id="rageID" class="short" name="rageID" path="rageId" value="${tvRage.rageId}" />
                            <div class="hint">The numeric TVRage Id.</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="releasetitle">Show Name</label>:</td>
                        <td>
                            <form:input id="releasetitle" class="long" name="releasetitle" path="releaseTitle" value="${text:escapeHtml(tvRage.releaseTitle)}" />
                            <div class="hint">The title of the TV show.</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="description">Description</label>:</td>
                        <td>
                            <form:textarea id="description" name="description" path="description"/>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="genre">Show Genres</label>:</td>
                        <td>
                            <form:input id="genre" class="long" name="genre" path="genre" value="${text:escapeHtml(tvRage.genre)}" />
                            <div class="hint">The genres for the TV show. Separated by pipes ( | )</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="country">Show Country</label>:</td>
                        <td>
                            <form:input id="country" name="country" path="country" value="${tvRage.country}" maxlength="2" />
                            <div class="hint">The country for the TV show.</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="imagedata">Series Image</label>:</td>
                        <td>
                            <c:if test="${tvRage.hasImage()}">
                                <img style="max-width:200px; display:block;" alt="" src="${pageContext.request.contextPath}/images?type=tvrage&id=${tvRage.id}">
                            </c:if>
                            <input type="file" id="imagedata" name="imagedata">
                            <div class="hint">Shown in the TV series view page.</div>
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
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

            <p>
                Regexes are applied to group message subjects into releases. The capture groups are named to hold the release name and number of parts.
                They are applied to messages from that group in order, then any general regexes are applied in order afterwards.
            </p>
            <p>
                If you want to apply a regex to a group and all its children, then append an asterisk (i.e. a.b.blah* ) to the end.
            </p>

            <div id="message">msg</div>

            <select id="regexGroupSelect" name="groupName">
                <option <c:if test='${groupName == "-1"}'> selected="selected" </c:if> value="-1">--Please Select--</option>
                <c:forEach items="${groupNameList}" var="selectGroupName">
                    <option <c:if test='${selectGroupName == groupName}'> selected="selected" </c:if> value="${selectGroupName}">${selectGroupName}</option>
                </c:forEach>
            </select>

            <table style="margin-top:10px;" class="data Sortable highlight">

                <tr>
                    <th style="width:20px;">id</th>
                    <th>group</th>
                    <th>regex</th>
                    <th>category</th>
                    <th>status</th>
                    <th>releases</th>
                    <th>last match</th>
                    <th>ordinal</th>
                    <th style="display:none;width:60px;">Order</th>
                    <th style="width:75px;">Options</th>
                </tr>

                <c:forEach items="${releaseRegexList}" var="regex" varStatus="rowNum">
                    <tr id="row-${regex.id}" class='${text:cycle(rowNum, "", "alt")}'>
                        <td><a id="${regex.id}"></a>${regex.id}</td>
                        <td title="${regex.description}">
                            <c:choose>
                                <c:when test='${regex.groupName == null || regex.groupName == ""}'>
                                    all
                                </c:when>
                                <c:otherwise>
                                    ${text:replace(regex.groupName,"alt.binaries", "a.b")}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td title="Edit regex">
                            <a href="${pageContext.request.contextPath}/admin/regex-edit?id=${regex.id}">${text:escapeHtml(regex.regex)}</a>
                            <br>
                            ${regex.description}
                        </td>
                        <td title="${regex.categoryId}">
                            <c:if test='${regex.categoryId != ""}'>
                                ${regex.categoryId}
                            </c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${regex.status == 1}">
                                    active
                                </c:when>
                                <c:otherwise>
                                    disabled
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${regex.numberReleases}</td>
                        <td>${date:formatDate(regex.lastReleaseDate)}</td>
                        <td style="text-align:center;">${regex.ordinal}</td>
                        <td style="display:none;"><a title="Move up" href="#">up</a> | <a title="Move down" href="#">down</a></td>
                        <td>
                            <a href="javascript:ajax_releaseregex_delete(${regex.id})">delete</a>
                            <c:if test='${regex.groupName != ""}'>
                                | <a href="${pageContext.request.contextPath}/admin/regex-test?action=submit&groupname=${regex.groupName}&regex=${text:urlEncode(regex.regex)}">test</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>

            </table>

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
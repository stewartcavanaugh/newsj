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

            <c:if test="${text:isNull(error)}">
                <div class="error">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/admin/regex-test?action=submit" method="get">

                <table class="input">

                    <tr>
                        <td>Group:</td>
                        <td>
                            <select name="groupId">
                                <option <c:if test='${groupId == -1}'> selected="selected" </c:if> value="-1">--Please Select--</option>
                                <c:forEach items="${groupList}" var="selectGroup">
                                    <option <c:if test='${selectGroup.id == groupId}'> selected="selected" </c:if> value="${selectGroup.id}">
                                    ${text:replace(selectGroup.name,"alt.binaries", "a.b")}
                                    </option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td>Regex:</td>
                        <td>
                            <input id="regex" name="regex" class="long" value="${text:escapeHtml(regex)}" />
                        </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td>
                            <input type="checkbox" name="unreleased" <c:if test="${unreleased}">checked="checked"</c:if> />
                            Ignore binaries that are released, duplicates, or already matched by a regex
                        </td>
                    </tr>

                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" value="Test Regex" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="${pageContext.request.contextPath}/admin/regex-edit?regex=${text:urlEncode(regex)}&groupId=${groupId}">Add Regex</a>
                        </td>
                    </tr>
                </table>

            </form>

            <c:if test="${matchesList.size() > 0}">
                <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}" pagerOffset="${pagerOffset}"
                            pagerQueryBase="${pageContext.request.contextPath}/admin/regex-test?action=submit&groupId=${groupId}&regex=${text:urlEncode(regex)}&unreleased=${unreleased}&offset="/>
                <table style="margin-top:10px;" class="data Sortable highlight">

                    <tr>
                        <th>ID</th>
                        <th>name</th>
                        <th>req</th>
                        <th>parts</th>
                        <th>count</th>
                        <th>cat</th>
                    </tr>

                    <c:forEach items="${matchesList}" var="binaryMatch" varStatus="rowNum">
                        <tr class='${text:cycle(rowNum, "", "alt")}'>
                        <td>${binaryMatch.id}</td>
                        <td>${text:escapeHtml(binaryMatch.relName)}<br /><small>${text:escapeHtml(binaryMatch.name)}</small></td>
                        <td>${binaryMatch.reqId}</td>
                        <td>${binaryMatch.relPart}</td>
                        <td>${binaryMatch.relTotalPart}</td>
                        <td>${binaryMatch.categoryName}</td>
                        </tr>
                    </c:forEach>
                </table>
                <br />
                <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}" pagerOffset="${pagerOffset}"
                            pagerQueryBase="${pageContext.request.contextPath}/admin/regex-test?action=submit&groupId=${groupId}&regex=${text:urlEncode(regex)}&unreleased=${unreleased}&offset="/>
            </c:if>

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
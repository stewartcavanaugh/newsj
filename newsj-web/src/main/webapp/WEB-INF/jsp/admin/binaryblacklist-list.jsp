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
                Binaries can be prevented from being added to the index at all if they match a regex provided in the blacklist. They can also be included only if they match a regex (whitelist).
            </p>

            <div id="message"></div>

            <table style="margin-top:10px;" class="data Sortable highlight">

                <tr>
                    <th style="width:20px;">id</th>
                    <th>group</th>
                    <th>regex</th>
                    <th>type</th>
                    <th>field</th>
                    <th>status</th>
                    <th style="width:75px;">Options</th>
                </tr>

                <c:forEach items="${binaryBlacklistEntries}" var="bin" varStatus="rowNum">
                    <tr id="row-${bin.id}" class="${text:cycle(rowNum, "", "alt")}">
                    <td>${bin.id}</td>
                    <td title="${bin.description}">${text:replace(bin.groupName, "alt.binaries", "a.b")}</td>
                    <td title="Edit regex"><a href="${pageContext.request.contextPath}/admin/binaryblacklist-edit?id=${bin.id}">${text:escapeHtml(bin.regex)}</a><br>
                        ${bin.description}</td>
                    <td>
                        <c:choose>
                            <c:when test="${bin.opType == 1}">
                                black
                            </c:when>
                            <c:otherwise>
                                white
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${bin.msgCol == 1}">
                                subject
                            </c:when>
                            <c:when test="${bin.msgCol == 2}">
                                poster
                            </c:when>
                            <c:otherwise>
                                messageid
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${bin.status == 1}">
                                active
                            </c:when>
                            <c:otherwise>
                                disabled
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td><a href="javascript:ajax_binaryblacklist_delete(${bin.id})">delete</a></td>
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
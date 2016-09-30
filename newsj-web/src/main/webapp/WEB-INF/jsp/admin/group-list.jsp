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
<%--
  User: longfalcon
  Date: 12/17/15
  Time: 6:52 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
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
            <div id="group_list">

                <h1>${title}</h1>

                <p>
                    Below is a list of all usenet groups available to be indexed. Click 'Activate' to start indexing a group.
                </p>

                <c:choose>

                    <c:when test="${!(empty groupList)}">
                        <div style="float:right;">

                            <form name="groupsearch" action="">
                                <label for="groupname">Group</label>
                                <input id="groupname" type="text" name="groupname" value="${searchgroupname}" size="15" />
                                &nbsp;&nbsp;
                                <input type="submit" value="Go" />
                            </form>
                        </div>

                        <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}"
                                    pagerOffset="${pagerOffset}" pagerQueryBase="${pageContext.request.contextPath}/admin/group-list?offset="/>
                        <br/><br/>

                        <div id="message">msg</div>
                        <table style="width:100%;" class="data highlight">

                            <tr>
                                <th>group</th>
                                <th>First Post</th>
                                <th>Last Post</th>
                                <th>last updated</th>
                                <th>active</th>
                                <th>releases</th>
                                <th>Min Files</th>
                                <th>Backfill Days</th>
                                <th>options</th>
                            </tr>

                            <c:forEach items="${groupList}" var="group" varStatus="rowNum">
                                <tr id="grouprow-${group.id}" class='${(rowNum.count % 2 == 0) ? "" : "alt"}'>
                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/group-edit?id=${group.id}">${text:replace(group.name,'alt.binaries','a.b')}</a>
                                    <div class="hint">${group.description}</div>
                                </td>
                                <td class="less">${date:timeAgo(group.firstRecordPostdate)}</td>
                                <td class="less">${date:timeAgo(group.lastRecordPostdate)}</td>
                                <td class="less">${date:timeAgo(group.lastUpdated)} ago</td>
                                <td class="less" id="group-${group.id}">
                                    <c:choose>
                                        <c:when test="${group.active}">
                                            <a href="javascript:ajax_group_status(${group.id}, 0)" class="group_active">Deactivate</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="javascript:ajax_group_status(${group.id}, 1)" class="group_deactive">Activate</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="less">${groupService.getReleasesCount(group)}</td>
                                <td class="less">${group.minFilesToFormRelease == null ? "n/a" : group.minFilesToFormRelease}</td>
                                <td class="less">${group.backfillTarget}</td>
                                <td class="less" id="groupdel-${group.id}">
                                    <a title="Reset this group" href="javascript:ajax_group_reset(${group.id})" class="group_reset">Reset</a> |
                                    <a href="javascript:ajax_group_backfill(${group.id})" class="group_backfill">Backfill</a> |
                                    <a href="javascript:ajax_group_delete(${group.id})" class="group_delete">Delete</a> |
                                    <a href="javascript:ajax_group_purge(${group.id})" class="group_purge" onclick="return confirm('Are you sure? This will delete all releases, binaries/parts in the selected group');" >Purge</a>
                                </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <p>No groups available (eg. none have been added).</p>
                    </c:otherwise>
                </c:choose>
            </div>

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

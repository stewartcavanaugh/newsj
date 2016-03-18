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

            <c:choose>
                <c:when test="${tvRageList.size() > 0}">
                    <div style="float:right;">

                        <form name="ragesearch" action="">
                            <label for="ragename">Title</label>
                            <input id="ragename" type="text" name="rageSearch" value="${rageSearch}" size="15" />
                            &nbsp;&nbsp;
                            <input type="submit" value="Go" />
                        </form>
                    </div>
                    
                    <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}" pagerOffset="${pagerOffset}"
                                pagerQueryBase="${pageContext.request.contextPath}/admin/rage-list?${text:isNull(searchUrlFragment) ? '' : searchUrlFragment}offset="/>

                    <br/><br/>

                    <table style="width:100%;margin-top:10px;" class="data Sortable highlight">

                        <tr>
                            <th style="width:50px;">rageid</th>
                            <th>title</th>
                            <th style="width:80px;">date</th>
                            <th style="width:120px;" class="right">options</th>
                        </tr>

                        <%--TODO: with TvRage clearly gone, this needs to move to TVDB--%>
                        <c:forEach items="${tvRageList}" var="tvRage" varStatus="rowNum">
                            <tr class="${text:cycle(rowNum, "", "alt")}">
                                <td class="less"><a href="http://www.tvrage.com/shows/id-${tvRage.rageId}" title="View in TvRage">${tvRage.rageId}</a></td>
                                <td><a title="Edit" href="${pageContext.request.contextPath}/admin/rage-edit?id=${tvRage.id}">${text:escapeHtml(tvRage.releaseTitle)}</a></td>
                                <td class="less">${date:formatDate(tvRage.createDate)}</td>
                                <td class="right">
                                    <form style="display: inline" action="${pageContext.request.contextPath}/admin/rage-delete?id=${tvRage.id}" method="post">
                                        <input  type="submit" value="delete"/>
                                    </form>

                                    <form style="display: inline" action="${pageContext.request.contextPath}/admin/rage-remove?id=${tvRage.id}" method="post">
                                        <input  type="submit" value="remove"/>
                                    </form>
                                </td>
                            </tr>    
                        </c:forEach>

                    </table>        
                </c:when>
                <c:otherwise>
                    <p>No TVRage episodes available.</p>    
                </c:otherwise>
            </c:choose>
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
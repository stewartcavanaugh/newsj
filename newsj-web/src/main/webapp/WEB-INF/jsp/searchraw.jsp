<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="common/common_head.jsp"%>
</head>
<body>
<a name="top"></a>

<%@include file="common/statusbar.jsp"%>

<%@include file="common/logo.jsp"%>
<hr />

<div id="header">
    <div id="menu">

        <c:if test="${loggedIn}">
            <%--Header Menu--%>
            <%@include file="common/header_menu.jsp"%>
        </c:if>

    </div>
</div>

<div id="page">

    <%@include file="common/adpanel.jsp"%>

    <div id="content">
        <%--START PAGE CONTENT--%>
            <h1>Search Binaries</h1>

            <form method="get" action="${pageContext.request.contextPath}/searchraw">
                <div style="text-align:center;">
                    <label for="search" style="display:none;">Search</label>
                    <input id="search" name="search" value="${text:escapeHtml(search)}" type="text"/>
                    <input id="searchraw_search_button" type="submit" value="search" />
                </div>
            </form>

            <c:choose>
                <c:when test="${binaryList.size() == 0 && !text:isNull(search)}">
                    <div class="nosearchresults">
                        Your search - <strong>${text:escapeHtml(search)}</strong> - did not match any headers.
                        <br/><br/>
                        Suggestions:
                        <br/><br/>
                        <ul>
                            <li>Make sure all words are spelled correctly.</li>
                            <li>Try different keywords.</li>
                            <li>Try more general keywords.</li>
                            <li>Try fewer keywords.</li>
                        </ul>
                    </div>    
                </c:when>
                <c:when test="${text:isNull(search)}">
                    <%--do nothing--%>
                </c:when>
                <c:otherwise>
                    <form method="post" id="dl" name="dl" action="${pageContext.request.contextPath}/searchraw">
                        <table style="width:100%;" class="data" id="browsetable">
                            <tr>
                                <!--<th width="10"></th>-->
                                <th>filename</th>
                                <th>group</th>
                                <th>posted</th>
                                <c:if test="${isAdmin}">
                                    <th>Misc</th>
                                    <th>%</th>    
                                </c:if>
                                <th>Nzb</th>
                            </tr>

                            <c:forEach items="${binaryList}" var="binary" varStatus="rowNum">
                                <tr class="${text:cycle(rowNum, "", "alt")}">
                                <%--<td class="selection"><input name="file-${binary.id}" id="file-${binary.id}" value="${binary.id}" type="checkbox"/></td>--%>
                                <td title="${text:escapeHtml(binary.xref)}">
                                    ${text:escapeHtml(binary.name)}
                                </td>
                                <td class="less">
                                    ${text:replace(binary.groupName, "alt.binaries", "a.b")}
                                </td>
                                <td class="less" title="${binary.date}">
                                    ${date:formatDate(binary.date)}
                                </td>
                                <c:if test="${isAdmin}">
                                    <td>
                                        <span title="procstat">${binary.procStat}</span>/
                                        <span title="procattempts">${binary.procAttempts}</span>/
                                        <span title="totalparts">${binary.totalParts}</span>/
                                        <span title="regex"><c:if test="${binary.regexId == null || binary.regexId < 1}">_</c:if>${binary.regexId}</span>/
                                        <span title="relpart">${binary.relPart}</span>/
                                        <span title="reltotalpart">${binary.relTotalPart}</span>
                                    </td>
                                    <td class="less">
                                        <c:choose>
                                            <c:when test="${binary.numberParts < binary.totalParts}">
                                                span style="color:red;">${binary.numberParts}/${binary.totalParts}</span>
                                            </c:when>
                                            <c:otherwise>
                                                100%
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </c:if>
                                <td class="less">
                                    <c:if test="${binary.releaseId != null && binary.releaseId > 0}">
                                        <a title="View Nzb details" href="${pageContext.request.contextPath}/details/${binary.releaseGuid}/-">Yes</a>
                                    </c:if>
                                </td>
                                </tr>    
                            </c:forEach>

                        </table>
                    </form>    
                </c:otherwise>
            </c:choose>

            <!--
            <div class="checkbox_operations">
                Selection:
                <a href="#" class="select_all">All</a>
                <a href="#" class="select_none">None</a>
                <a href="#" class="select_invert">Invert</a>
                <a href="#" class="select_range">Range</a>
            </div>

            <div style="padding-top:20px;">
                <a href="#" id="searchraw_download_selected">Download selected as Nzb</a>
            </div>
            -->

        <%--END PAGE CONTENT--%>
    </div>

    <c:if test="${site.menuPosition == 1}">
        <%@include file="common/sidebar.jsp"%>
    </c:if>

    <div style="clear: both;text-align:right;">
        <a class="w3validator" href="http://validator.w3.org/check?uri=referer">
            <img src="${pageContext.request.contextPath}/resources/images/valid-xhtml10.png" alt="Valid XHTML 1.0 Transitional" height="31" width="88" />
        </a>
    </div>

</div>

<div class="footer">
    <p>
        ${site.footer}
        <br /><br /><br />
        <a title="Newznab - A usenet indexing web application with community features." href="http://www.newznab.com/">Newznab</a> is released under GPL. All rights reserved ${year}. <br/> <a title="Chat about newznab" href="http://www.newznab.com/chat.html">Newznab Chat</a> <br/><a href="${pageContext.request.contextPath}/terms-and-conditions">${site.title} Terms and Conditions</a>
    </p>
</div>

<c:if test="${!(empty site.googleAnalyticsAcc)}">
    <%@include file="common/google_analytics.jsp"%>
</c:if>
<c:if test="${loggedIn}">
    <input type="hidden" name="UID" value="${userId}" />
    <input type="hidden" name="RSSTOKEN" value="${rssToken}" />
</c:if>
</body>
</html>

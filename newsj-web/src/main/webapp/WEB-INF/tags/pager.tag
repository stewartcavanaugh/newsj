<%@ tag import="javax.servlet.jsp.jstl.core.LoopTagSupport" %>
<%@ tag import="javax.servlet.jsp.jstl.core.LoopTagStatus" %>
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
<%@attribute name="pagerTotalItems" required="true" type="java.lang.Integer" rtexprvalue="true" %>
<%@attribute name="pagerItemsPerPage" required="true" type="java.lang.Integer" rtexprvalue="true" %>
<%@attribute name="pagerOffset" required="true" type="java.lang.Integer" rtexprvalue="true" %>
<%@attribute name="pagerQueryBase" required="true" type="java.lang.String" rtexprvalue="true" %>
<%@attribute name="pagerQuerySuffix" required="false" type="java.lang.String" rtexprvalue="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag display-name="pager" description="Renders a Pager" pageEncoding="UTF-8" %>
<c:if test="${pagerTotalItems > pagerItemsPerPage}">
    <div class="pager">
        <c:forEach begin="0" end="${pagerTotalItems}" step="${pagerItemsPerPage}" varStatus="index">
            <c:set var="iteration" value="${index.count}"/>
            <c:set var="current" value="${index.current}"/>
            <c:choose>
                <c:when test="${pagerOffset == current}">
                    <span class="current" title="Current page ${iteration}">${iteration}</span>&nbsp;
                </c:when>
                <c:when test="${((pagerOffset - current) == pagerItemsPerPage) || ((pagerOffset + pagerItemsPerPage) == current)}">
                    <a title="Goto page ${iteration}" href="${pagerQueryBase}${current}${pagerQuerySuffix}">${iteration}</a>&nbsp;
                </c:when>
                <c:when test="${(pagerTotalItems + (current + pagerItemsPerPage)) < 0}">
                    ... <a title="Goto last page" href="${pagerQueryBase}${current}${pagerQuerySuffix}">${iteration}</a>
                </c:when>
                <c:when test="${(current > ((pagerTotalItems/2) + pagerOffset + 1)) && current < (((pagerTotalItems/2) + pagerOffset) + 50)}">
                    ... <a title="Goto page ${iteration}" href="${pagerQueryBase}${current}${pagerQuerySuffix}">${iteration}</a>&nbsp;
                </c:when>
                <c:when test="${(pagerTotalItems - (current + pagerItemsPerPage)) < 0}">
                    ... <a title="Goto last page" href="${pagerQueryBase}${current}${pagerQuerySuffix}">${iteration}</a>
                </c:when>
                <c:when test="${iteration == 1}">
                    <a title="Goto first page" href="${pagerQueryBase}0${pagerQuerySuffix}">1</a> ...
                </c:when>
            </c:choose>
        </c:forEach>

    </div>
</c:if>

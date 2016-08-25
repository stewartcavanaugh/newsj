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

<%--@elvariable id="isAdmin" type="boolean"--%>
<%--@elvariable id="loggedIn" type="boolean"--%>
<%--@elvariable id="userId" type="java.lang.String"--%>
<%--@elvariable id="site" type="net.longfalcon.newsj.model.Site"--%>
<%--@elvariable id="userData" type="net.longfalcon.view.UserData"--%>


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
            <h1>${title}</h1>

            <p>
                Here you can choose rss feeds from site categories. The feeds will present either descriptions or
                downloads of Nzb files.
            </p>

            <ul>
                <li>
                    Add this string to your feed URL to allow NZB downloads without logging in: <span style="font-family:courier;">&amp;i=${userData.id}&amp;r=${userData.rssToken}</span>
                </li>
                <li>
                    To remove the nzb from your cart after download add this string to your feed URL: <span style="font-family:courier;">&amp;del=1</span>
                </li>
                <li>
                    To change the default link to download an nzb: <span style="font-family:courier;">&amp;dl=1</span>
                </li>
                <li>
                    To change the number of results (default is 25, max is 100) returned: <span style="font-family:courier;">&amp;num=50</span>
                </li>
            </ul>

            <p>
                Most Nzb clients which support Nzb rss feeds will appreciate the full URL, with download link and your 
                user token.
            </p>

            <p>
                The feeds include additional attributes to help provide better filtering in your Nzb client, such as size, 
                group and categorisation. If you want to chain multiple categories together or do more advanced searching, 
                use the <a href="${pageContext.request.contextPath}/apihelp">api</a>, which returns its data in an rss compatible format.
            </p>

            <h2>Available Feeds</h2>
            <h3>General</h3>
            <ul style="text-align: left;">
                <li>
                    Full site feed<br/>
                    <a href="${pageContext.request.contextPath}/rss?t=0&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}">${pageContext.request.contextPath}/rss?t=0&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}</a>
                </li>
                <li>
                    My cart feed<br/>
                    <a href="${pageContext.request.contextPath}/rss?t=-2&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}&amp;del=1">${pageContext.request.contextPath}/rss?t=-2&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}&amp;del=1</a>
                </li>

            </ul>
            <h3>Parent Category</h3>
            <ul style="text-align: left;">
                <c:forEach items="${parentCategoryList}" var="category">
                    <li>
                            ${category.title} feed <br/>
                        <a href="${pageContext.request.contextPath}/rss?t=${category.id}&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}">${pageContext.request.contextPath}/rss?t=${category.id}&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}</a>
                    </li>
                </c:forEach>
            </ul>
            <h3>Sub Category</h3>
            <ul style="text-align: left;">
                <c:forEach items="${subCategoryList}" var="subCategory">
                    <li>
                            ${category.title} feed <br/>
                        <a href="${pageContext.request.contextPath}/rss?t=${subCategory.id}&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}">${pageContext.request.contextPath}/rss?t=${subCategory.id}&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}</a>
                    </li>
                </c:forEach>
            </ul>

            <h2>Additional Feeds</h2>
            <ul style="text-align: left;">
                <li>
                    Tv Series Feed (Use the tv rage id)<br/>
                    <a href="${pageContext.request.contextPath}/rss?rage=1234&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}">${pageContext.request.contextPath}/rss/?rage=1234&amp;dl=1&amp;i=${userData.id}&amp;r=${userData.rssToken}</a>
                </li>
            </ul>

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
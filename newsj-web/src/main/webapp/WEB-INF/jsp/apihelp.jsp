<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

            <h1>${title}</h1>

            <p>
                Here lives the documentation for the api for accessing nzb and index data. Api functions can be
                called by either logged in users, or by providing an apikey.
            </p>

            <c:if test="${loggedIn}">
                <p>
                    Your credentials should be provided as <span class="monospace">?apikey=${userData.rssToken}</span>
                </p>    
            </c:if>

            <h2>Available Functions</h2>
            <p>Use the parameter <span class="monospace">?t=</span> to specify the function being called.</p>
            <ul>
                <li>
                    <b>Capabilities</b> <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=caps">?t=caps</a></span>
                    <br/>
                    Reports the capabilities if the server. Includes information about the server name, available search categories and version number of the newznab protocol being used.
                    <br/>
                    Capabilities does not require any credentials in order to be ran.
                </li>

                <li>
                    <b>Register</b> <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=register&amp;email=user@newznab.com">?t=register&amp;email=user@newznab.com</a></span>
                    <br/>
                    Registers a new user account. Does not require any credentials in order to be ran.
                    <br/>
                    Returns either the registered username and password if successful or an error code.
                </li>

                <li>
                    <b>Search</b> <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=search&amp;q=linux">?t=search&amp;q=linux</a></span>
                    <br/>
                    Returns a list of nzbs matching a query. You can also  filter by site category by including a comma separated list of categories as follows <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=search&amp;cat=1000,2000">?t=search&amp;cat=1000,2000</a></span>. Include <span class="monospace">&amp;extended=1</span> to return extended information in the search results.
                </li>
                <li>
                    <b>TV</b> <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=tvsearch&amp;q=law%20and%20order&amp;season=7&amp;ep=12">?t=tvsearch&amp;q=law and order&amp;season=7&amp;ep=12</a></span>
                    <br/>
                    Returns a list of nzbs matching a query, category, tvrageid, season or episode. You can also filter by site category by including a comma separated list of categories as follows <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=tvsearch&amp;rid=2204&amp;cat=1000,2000">?t=tvsearch&amp;rid=2204&amp;cat=1000,2000</a></span>.  Include <span class="monospace">&amp;extended=1</span> to return extended information in the search results.
                </li>
                <li>
                    <b>Movies</b> <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=movie&amp;imdbid=1418646">?t=movie&amp;imdbid=1418646</a></span>
                    <br/>
                    Returns a list of nzbs matching a query, an imdbid and optionally a category. Filter by site category by including a comma separated list of categories as follows <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=movie&amp;imdbid=1418646&amp;cat=2030,2040">?t=movie&amp;imdbid=1418646&amp;cat=2030,2040</a></span>.  Include <span class="monospace">&amp;extended=1</span> to return extended information in the search results.
                </li>
                <li>
                    <b>Details</b> <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=details&amp;id=9ca52909ba9b9e5e6758d815fef4ecda">?t=details&amp;id=9ca52909ba9b9e5e6758d815fef4ecda</a></span>
                    <br/>
                    Returns detailed information about an nzb.
                </li>
                <li>
                    <b>Get</b> <span class="monospace"><a href="${pageContext.request.contextPath}/api?t=get&amp;id=9ca52909ba9b9e5e6758d815fef4ecda">?t=get&amp;id=9ca52909ba9b9e5e6758d815fef4ecda</a></span>
                    <br/>
                    Downloads the nzb file associated with an Id.
                </li>
            </ul>

            <h2>Output Format</h2>
            <p>Obviously not appropriate to functions which return an nzb file.</p>
            <ul>
                <li>
                    Xml (default) <span class="monospace">?t=search&amp;q=linux&amp;o=xml</span>
                    <br/>
                    Returns the data in an xml document.
                </li>
                <li>
                    Json <span class="monospace">?t=search&amp;q=linux&amp;o=json</span>
                    <br/>
                    Returns the data in a json object.
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

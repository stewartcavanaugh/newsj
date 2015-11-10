<%--@elvariable id="site" type="net.longfalcon.newsj.model.Site"--%>
<%--
  ~ Copyright (c) 2015. Sten Martinez
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
  User: Sten Martinez
  Date: 11/7/15
  Time: 6:43 AM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="common/common_head.jsp"%>
</head>
<body>
<a name="top"></a>

<div id="statusbar">
    <c:choose>
        <c:when test="${loggedIn}">
            Welcome back <a href="${pageContext.request.contextPath}/profile">${userData.username}</a>. <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/login">Login</a> or <a href="${pageContext.request.contextPath}/register">Register</a>
        </c:otherwise>
    </c:choose>
</div>

<div id="logo">
    <a class="logolink" title="${site.title} Logo" href="${pageContext.request.contextPath}/"><img class="logoimg" alt="${site.title} Logo" src="${pageContext.request.contextPath}/resources/images/clearlogo.png" /></a>

    <c:if test="${site.menuPosition == 2}">
        <ul>{$main_menu}</ul>
    </c:if>

    <h1><a href="${pageContext.request.contextPath}/">${site.title}</a></h1>
    <p><em>${site.strapLine}</em></p>

</div>
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

    <div id="adpanel">
        &nbsp;
        <c:if test="${!(empty site.googleAdSenseAcc) && !(empty site.googleAdSenseSidePanel)}">
            <script type="text/javascript"><!--
            google_ad_client = "${site.googleAdSenseAcc}";
            google_ad_slot = "${site.googleAdSenseSidePanel}";
            google_ad_width = 160;
            google_ad_height = 600;
            //-->
            </script>
            <script type="text/javascript"
                    src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
            </script>
        </c:if>
    </div>

    <div id="content">
        <%--START PAGE CONTENT--%>
            <h1>${content.title}</h1>

            ${content.body}
        <%--END PAGE CONTENT--%>
    </div>

    <c:if test="${site.menuPosition == 1}">
        <div id="sidebar">
            <ul>


                <%--{$main_menu}--%>
                <c:if test="${menuItems.size() > 0}">
                    <li class="menu_main">
                        <h2>Menu</h2>

                        <ul>
                            <c:forEach items="${menuItems}" var="menuitem">
                                <li onclick="document.location='${pageContext.request.contextPath}/${menuitem.href}';">
                                    <a title="${menuitem.tooltip}" href="${pageContext.request.contextPath}/${menuitem.href}">${menuitem.title}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </c:if>

                <%--{$article_menu}--%>
                    <c:if test="${articlecontentlist.size() > 0}">
                        <li class="menu_articles">
                            <h2>Articles</h2>
                            <ul>
                                <c:forEach items="${articlecontentlist}" var="content">
                                    <li onclick="document.location='${pageContext.request.contextPath}/content/${content.id}${content.url}'">
                                        <a title="${content.title}" href="${pageContext.request.contextPath}/content/${content.id}${content.url}">${content.title}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:if>
                <%--{$useful_menu}--%>
                <li class="menu_useful">
                    <h2>Useful Links</h2>
                    <ul>
                        <li onclick=""><a title="Contact Us" href="${pageContext.request.contextPath}/contact-us">Contact Us</a></li>
                        <li onclick="document.location='${pageContext.request.contextPath}/sitemap'"><a title="Site Map" href="${pageContext.request.contextPath}/sitemap">Site Map</a></li>
                        <c:if test="${loggedIn}">
                            <li onclick="document.location='${pageContext.request.contextPath}/rss'"><a title="${site.title} Rss Feeds" href="${pageContext.request.contextPath}/rss">Rss Feeds</a></li>
                            <li onclick="document.location='${pageContext.request.contextPath}/apihelp'"><a title="${site.title} Api" href="${pageContext.request.contextPath}/apihelp">Api</a></li>
                        </c:if>
                        <c:forEach items="${usefulcontentlist}" var="content">
                            <li onclick="document.location='${pageContext.request.contextPath}/content/${content.id}${content.url}'">
                                <a title="${content.title}" href="${pageContext.request.contextPath}/content/${content.id}${content.url}">${content.title}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>

                <c:if test="${!(empty site.googleAdSenseAcc) && !(empty site.googleAdSenseSearch)}">
                    <li>
                        <h2>Search for ?</h2>
                        <div style="padding-left:20px;">
                            <div class="cse-branding-bottom" style="background-color:#FFFFFF;color:#000000">
                                <div class="cse-branding-form">
                                    <form action="http://www.google.co.uk/cse" id="cse-search-box" target="_blank">
                                        <div>
                                            <input type="hidden" name="cx" value="partner-${site.googleAdSenseAcc}:${site.googleAdSenseSearch}" />
                                            <input type="hidden" name="ie" value="UTF-8" />
                                            <input type="text" name="q" size="10" />
                                            <input type="submit" name="sa" value="Search" />
                                        </div>
                                    </form>
                                </div>
                                <div class="cse-branding-logo">
                                    <img src="http://www.google.com/images/poweredby_transparent/poweredby_FFFFFF.gif" alt="Google" />
                                </div>
                                <div class="cse-branding-text">
                                    Custom Search
                                </div>
                            </div>
                        </div>
                    </li>
                </c:if>

                <li>
                    <a title="Sickbeard - The ultimate usenet PVR" href="http://www.sickbeard.com/"><img class="menupic" alt="Sickbeard - The ultimate usenet PVR" src="${pageContext.request.contextPath}/resources/images/sickbeard.png" /></a>
                </li>
                <li>
                    <a title="CouchPotato - Automatic movie downloader" href="http://www.couchpotatoapp.com/"><img style="padding-left:30px;"  class="menupic" alt="CouchPotato - Automatic Movie Downloader" src="${pageContext.request.contextPath}/resources/images/couchpotato.png" /></a>
                </li>
                <li>
                    <a title="Sabznbd - A great usenet binary downloader" href="http://www.sabnzbd.org/"><img class="menupic" alt="Sabznbd - A great usenet binary downloader" src="${pageContext.request.contextPath}/resources/images/sabnzbd.png" /></a>
                </li>
            </ul>
        </div>
    </c:if>

    <div style="clear: both;text-align:right;">
        <a class="w3validator" href="http://validator.w3.org/check?uri=referer">
            <img src="${pageContext.request.contextPath}/resources/images/valid-xhtml10.png" alt="Valid XHTML 1.0 Transitional" height="31" width="88" />
        </a>
    </div>

</div>

<div class="footer">
    <p>
        <%--{$site->footer}--%>
        <br /><br /><br /><a title="Newznab - A usenet indexing web application with community features." href="http://www.newznab.com/">Newznab</a> is released under GPL. All rights reserved ${year}. <br/> <a title="Chat about newznab" href="http://www.newznab.com/chat.html">Newznab Chat</a> <br/><a href="${pageContext.request.contextPath}/terms-and-conditions">${site.title} Terms and Conditions</a>
    </p>
</div>

<c:if test="${!(empty site.googleAnalyticsAcc)}">
    <script type="text/javascript">
        var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
        document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    </script>
    <script type="text/javascript">
        try {
            var pageTracker = _gat._getTracker("${site.googleAnalyticsAcc}");
            pageTracker._trackPageview();
        } catch(err) {}
    </script>
</c:if>
<c:if test="${loggedIn}">
    <input type="hidden" name="UID" value="${userId}" />
    <input type="hidden" name="RSSTOKEN" value="${rssToken}" />
</c:if>
</body>
</html>

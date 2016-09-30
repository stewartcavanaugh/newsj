<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--@elvariable id="isAdmin" type="boolean"--%>
<%--@elvariable id="loggedIn" type="boolean"--%>
<%--@elvariable id="userId" type="java.lang.String"--%>
<%--@elvariable id="site" type="net.longfalcon.newsj.model.Site"--%>
<%--@elvariable id="userData" type="net.longfalcon.view.UserData"--%>
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
            <h1>
                <c:choose>
                    <c:when test="${isAdmin}">
                        <a title="Edit rage data" href="${pageContext.request.contextPath}/admin/rage-edit?id=${tvInfo.id}&amp;from=${text:urlEncode('/series/')}${tvInfo.id}">${tvInfo.releaseTitle}</a>
                    </c:when>
                    <c:otherwise>
                        ${tvInfo.releaseTitle}
                    </c:otherwise>
                </c:choose>

                <c:if test="${catName}">
                    in ${text:escapeHtml(catName)}
                </c:if>

            </h1>

            <div class="tvseriesheading">
                <c:if test="${tvInfo.hasImage()}">
                    <img class="shadow" alt="${tvInfo.releaseTitle} Logo" src="${pageContext.request.contextPath}/images?type=tvrage&amp;id=${tvInfo.id}" />
                </c:if>
                <p>
                    <c:if test="${!text:isNull(tvInfo.genre)}">
                        <span style="font-weight: bold;">${tvInfo.genre}</span><br />
                    </c:if>
                    <span class="descinitial">
                                    ${text:truncate(text:nl2br(text:escapeHtml(tvInfo.description)), 1500)}
                                    <c:if test="${tvInfo.description.length() > 1500}">
                                        <a class="descmore" href="#">more...</a>
                                    </c:if>
                                </span>
                    <c:if test="${tvInfo.description.length() > 1500}">
                        <span class="descfull">text:nl2br(text:escapeHtml(tvInfo.description))</span>
                    </c:if>
                </p>

            </div>

            <form id="nzb_multi_operations_form" action="${pageContext.request.contextPath}/series/${tvInfo.id}<c:if test="${not empty categoryId}">?t=${categoryId}</c:if>">

                <div class="nzb_multi_operations">
                    <div style="padding-bottom:10px;" >
                        <a target="_blank" href="${site.deReferrerLink}https://trakt.tv/search/trakt/${tvInfo.traktId}?id_type=show" title="View in Trakt">View in Trakt</a> |
                        <a href="${pageContext.request.contextPath}/rss?trakt=${tvInfo.traktId}<c:if test="${not empty categoryId}">&t=${categoryId}</c:if>&amp;dl=1&amp;i=${userId}&amp;r=${userData.rssToken}">Rss Feed for this Series</a>
                    </div>
                    <small>With Selected:</small>
                    <input type="button" class="nzb_multi_operations_download" value="Download NZBs" />
                    <input type="button" class="nzb_multi_operations_cart" value="Add to Cart" />
                    <input type="button" class="nzb_multi_operations_sab" value="Send to SAB" />
                    <c:if test="${isAdmin}">
                        &nbsp;&nbsp;
                        <input type="button" class="nzb_multi_operations_edit" value="Edit" />
                        <input type="button" class="nzb_multi_operations_delete" value="Del" />
                        <input type="button" class="nzb_multi_operations_rebuild" value="Reb" />
                    </c:if>
                </div>


                <table style="width:100%;" class="data highlight icons" id="browsetable">
                    <c:forEach items="${sortedMap}" var="seasonEntry">
                        <tr>
                            <td style="padding-top:15px;" colspan="10"><a href="#top" class="top_link">Top</a><h2>Season ${seasonEntry.key}</h2></td>
                        </tr>
                        <tr>
                            <th>Ep</th>
                            <th>Name</th>
                            <th>
                                <input id="chkSelectAll${seasonEntry.key}" type="checkbox" name="${seasonEntry.key}" class="nzb_check_all_season" />
                                <label for="chkSelectAll${seasonEntry.key}" style="display:none;">Select All</label>
                            </th>
                            <th>Category</th>
                            <th style="text-align:center;">Posted</th>
                            <th>Size</th>
                            <th>Files</th>
                            <th>Stats</th>
                            <th></th>
                        </tr>
                        <c:forEach items="${seasonEntry.value}" var="episodeEntry">
                            <c:forEach items="${episodeEntry.value}" var="release" varStatus="rowNum">
                                <tr class="${text:cycle(rowNum, "", "alt")}" id="guid${release.guid}">
                                    <c:choose>
                                        <c:when test="${episodeEntry.value.size() > 1 && rowNum.first}">
                                            <td width="20" rowspan="${episodeEntry.value.size()}" class="static">${episodeEntry.key}</td>
                                        </c:when>
                                        <c:when test="${episodeEntry.value.size() == 1}">
                                            <td width="20" class="static">${episodeEntry.key}</td>
                                        </c:when>
                                    </c:choose>
                                    <td>
                                        <a title="View details" href="${pageContext.request.contextPath}/details/${release.guid}/${text:escapeHtml(release.searchName)}">${text:replace(text:escapeHtml(release.searchName), ".", " ")}</a>

                                        <div class="resextra">
                                            <div class="btns">
                                                <c:if test="${not empty release.releaseNfo}">
                                                    <a href="${pageContext.request.contextPath}/nfo/${release.guid}" title="View Nfo" class="modal_nfo rndbtn" rel="nfo">Nfo</a>
                                                </c:if>
                                                <c:if test="${not empty release.tvAirDate}">
                                                    <span class="rndbtn" title="${release.tvTitle} Aired on ${date:formatDate(release.tvAirDate)}">
                                                        Aired
                                                        <c:choose>
                                                            <c:when test="${date:isInFuture(release.tvAirDate)}">
                                                                in future
                                                            </c:when>
                                                            <c:otherwise>
                                                                ${date:timeAgo(release.tvAirDate)}
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </span>
                                                </c:if>
                                            </div>

                                            <c:if test="${isAdmin}">
                                                <div class="admin">
                                                    <a class="rndbtn" href="${pageContext.request.contextPath}/admin/release-edit?id=${release.id}&amp;from=${text:urlEncode('/series/')}${tvInfo.id}" title="Edit Release">Edit</a>
                                                    <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/release-delete?id=${release.id}&amp;from=${text:urlEncode('/series/')}${tvInfo.id}" title="Delete Release">Del</a>
                                                    <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/release-rebuild?id=${release.id}&amp;from=${text:urlEncode('/series/')}${tvInfo.id}" title="Rebuild Release - Delete and reset for reprocessing if binaries still exist.">Reb</a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </td>
                                    <td class="check"><input id="chk${release.guid.substring(0,7)}" type="checkbox" class="nzb_check" name="${seasonEntry.key}" value="${release.guid}" /></td>
                                    <td class="less"><a title="This series in ${release.categoryDisplayName}" href="${pageContext.request.contextPath}/series/${release.id}?t=${release.categoryId}">${release.categoryDisplayName}</a></td>
                                    <td class="less mid" width="40" title="${release.postDate}">${date:timeAgo(release.postDate)}</td>
                                    <td width="40" class="less right">${text:formatFileSize(release.size, true)}
                                        <c:if test="${release.completion > 0}">
                                            <br />
                                            <c:choose>
                                                <c:when test="${release.completion < 100}">
                                                    <span class="warning">${release.completion}%</span>
                                                </c:when>
                                                <c:otherwise>
                                                    ${release.completion}%
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                    </td>
                                    <td class="less mid"><a title="View file list" href="${pageContext.request.contextPath}/filelist/${release.guid}">${release.totalpart}</a></td>
                                    <td width="40" class="less" nowrap="nowrap">
                                        <a title="View comments for ${text:escapeHtml(release.searchName)}" href="${pageContext.request.contextPath}/details/${release.guid}/${text:escapeHtml(release.searchName)}#comments">
                                        ${release.comments} cmt${release.comments > 1 ? "s" : ""}
                                        </a>
                                        <br/>
                                        ${release.grabs} grab${release.grabs > 1 ? "s" : ""}
                                    </td>
                                    <td class="icons">
                                        <div class="icon icon_nzb"><a title="Download Nzb" href="${pageContext.request.contextPath}/getnzb/${release.guid}/${text:escapeHtml(release.searchName)}">&nbsp;</a></div>
                                        <div class="icon icon_cart" title="Add to Cart"></div>
                                        <div class="icon icon_sab" title="Send to my Sabnzbd"></div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:forEach>
                </table>

            </form>
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

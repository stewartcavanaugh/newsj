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
            <h1>Search</h1>

            <form method="get" action="${pageContext.request.contextPath}/search">
                <div style="text-align:center;">
                    <label for="search" style="display:none;">Search</label>
                    <input id="search" name="search" value="${text:escapeHtml(search)}" type="text"/>
                    <input id="search_search_button" type="submit" value="search" />
                    <input type="hidden" name="t" value="${searchCategories}" id="search_cat" />
                </div>
            </form>

            <c:choose>
                <c:when test="${releaseList.size() == 0 && !text:isNull(search)}">
                    <div class="nosearchresults">
                        Your search - <strong>${text:escapeHtml(search)}</strong> - did not match any releases.
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
                <c:when test="${releaseList.size() > 0}">
                    <form id="nzb_multi_operations_form" action="${pageContext.request.contextPath}/search">

                        <div class="nzb_multi_operations">
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

                        <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}"
                                    pagerOffset="${pagerOffset}"
                                    pagerQueryBase="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=${orderBy}&offset=" />

                        <table style="width:100%;" class="data highlight icons" id="browsetable">
                            <tr>
                                <th>
                                    <input id="chkSelectAll" type="checkbox" class="nzb_check_all" />
                                    <label for="chkSelectAll" style="display:none;">Select All</label>
                                </th>
                                <th>
                                    name<br/>
                                    <a title="Sort Descending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=searchName_desc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" />
                                    </a>
                                    <a title="Sort Ascending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=searchName_asc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" />
                                    </a>
                                </th>
                                <th>
                                    category<br/>
                                    <a title="Sort Descending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=category_desc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" />
                                    </a>
                                    <a title="Sort Ascending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=category_asc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" />
                                    </a>
                                </th>
                                <th>
                                    posted<br/>
                                    <a title="Sort Descending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=postDate_desc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" />
                                    </a>
                                    <a title="Sort Ascending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=postDate_asc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" />
                                    </a>
                                </th>
                                <th>
                                    size<br/>
                                    <a title="Sort Descending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=size_desc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" />
                                    </a>
                                    <a title="Sort Ascending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=size_asc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" />
                                    </a>
                                </th>
                                <th>
                                    files<br/>
                                    <a title="Sort Descending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=totalpart_desc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" />
                                    </a>
                                    <a title="Sort Ascending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=totalpart_asc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" />
                                    </a>
                                </th>
                                <th>
                                    stats<br/>
                                    <a title="Sort Descending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=grabs_desc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" />
                                    </a>
                                    <a title="Sort Ascending" href="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=grabs_asc&offset=${pagerOffset}">
                                        <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" />
                                    </a>
                                </th>
                                <th></th>
                            </tr>

                            <c:forEach items="${releaseList}" var="release" varStatus="rowNum">
                                <tr class="${text:cycle(rowNum, "", "alt")}<c:if test="${lastVisit < release.addDate}"> new</c:if>" id="guid${release.guid}">
                                    <td class="check"><input id="chk${release.guid.substring(0,7)}" type="checkbox" class="nzb_check" value="${release.guid}" /></td>
                                    <td class="item">
                                        <label for="chk${release.guid.substring(0,7)}">
                                            <a class="title" title="View details" href="${pageContext.request.contextPath}/details/${release.guid}/${text:urlEncode(release.searchName)}">
                                                    ${text:replace(text:escapeHtml(release.searchName), ".", " ")}
                                            </a>
                                        </label>

                                        <c:choose>
                                            <c:when test="${release.passwordStatus == 1}">
                                                <img title="Passworded Rar Archive" src="${pageContext.request.contextPath}/resources/images/icons/lock.gif" alt="Passworded Rar Archive" />
                                            </c:when>
                                            <c:when test="${release.passwordStatus == 2}">
                                                <img title="Contains .cab/ace Archive" src="${pageContext.request.contextPath}/resources/images/icons/lock.gif" alt="Contains .cab/ace Archive" />
                                            </c:when>
                                        </c:choose>

                                        <div class="resextra">
                                            <div class="btns">
                                                <c:if test="${release.releaseNfo != null}">
                                                    <a href="${pageContext.request.contextPath}/nfo/${release.guid}" title="View Nfo" class="modal_nfo rndbtn" rel="nfo">Nfo</a>
                                                </c:if>
                                                <c:if test="${release.imdbId > 0}">
                                                    <a href="#" name="name${release.imdbId}" title="View movie info" class="modal_imdb rndbtn" rel="movie" >Cover</a>
                                                </c:if>
                                                <c:if test="${release.musicInfoId > 0}">
                                                    <a href="#" name="name${release.musicInfoId}" title="View music info" class="modal_music rndbtn" rel="music" >Cover</a>
                                                </c:if>
                                                <c:if test="${release.consoleInfoId > 0}">
                                                    <a href="#" name="name${release.consoleInfoId}" title="View console info" class="modal_console rndbtn" rel="console" >Cover</a>
                                                </c:if>
                                                <c:if test="${release.rageId > 0}">
                                                    <a class="rndbtn" href="${pageContext.request.contextPath}/series/${release.rageId}" title="View all episodes">View Series</a>
                                                </c:if>
                                                <c:if test="${release.tvAirDate != null}">
                                            <span class="rndbtn" title="${release.tvTitle} Aired on ${date:formatDate(release.tvAirDate)}">
                                                Aired
                                                <c:choose>
                                                    <c:when test="${release.tvAirDate > now}">
                                                        in future
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${date:timeAgo(release.tvAirDate)}
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                                </c:if>
                                                <a class="rndbtn" href="${pageContext.request.contextPath}/browse?g=${release.groupName}" title="Browse releases in ${text:replace(release.groupName, "alt.binaries", "a.b")}">Grp</a>
                                            </div>
                                            <c:if test="${isAdmin}">
                                                <div class="admin">
                                                    <a class="rndbtn" href="${pageContext.request.contextPath}/admin/release-edit?id=${release.id}&amp;from=${text:urlEncode(pageContext.request.requestURI.concat("?").concat(pageContext.request.queryString))}" title="Edit Release">Edit</a>
                                                    <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/release-delete?id=${release.id}&amp;from=${text:urlEncode(pageContext.request.requestURI.concat("?").concat(pageContext.request.queryString))}" title="Delete Release">Del</a>
                                                    <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/release-rebuild?id=${release.id}&amp;from=${text:urlEncode(pageContext.request.requestURI.concat("?").concat(pageContext.request.queryString))}" title="Rebuild Release - Delete and reset for reprocessing if binaries still exist.">Reb</a>
                                                </div>
                                            </c:if>
                                        </div>
                                    </td>
                                    <td class="less"><a title="Browse {$result.category_name}" href="${pageContext.request.contextPath}/browse?t=${release.category.id}">${release.categoryDisplayName}</a></td>
                                    <td class="less mid" title="{$result.postdate}">${date:timeAgo(release.postDate)}</td>
                                    <td class="less right">
                                            ${text:formatFileSize(release.size, true)}
                                        <c:if test="${release.completion > 0}" >
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
                                    <td class="less" nowrap="nowrap">
                                        <a title="View comments for ${text:escapeHtml(release.searchName)}" href="${pageContext.request.contextPath}/details/${release.guid}/#comments">
                                                ${release.comments} cmt<c:if test="${release.comments != 1}">s</c:if>
                                        </a>
                                        <br/>${release.grabs} grab<c:if test="${release.grabs != 1}">s</c:if>
                                    </td>
                                    <td class="icons">
                                        <div class="icon icon_nzb"><a title="Download Nzb" href="${pageContext.request.contextPath}/getnzb/${release.guid}/${text:escapeHtml(release.searchName)}">&nbsp;</a></div>
                                        <div class="icon icon_cart" title="Add to Cart"></div>
                                        <div class="icon icon_sab" title="Send to my Sabnzbd"></div>
                                    </td>
                                </tr>
                            </c:forEach>

                        </table>

                        <br/>

                        <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}"
                                    pagerOffset="${pagerOffset}"
                                    pagerQueryBase="${pageContext.request.contextPath}/search/${text:urlEncode(search)}?t=${searchCategories}&g=${groupName}&ob=${orderBy}&offset=" />

                        <div class="nzb_multi_operations">
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

                    </form>
                    <br/><br/><br/>
                </c:when>
            </c:choose>


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
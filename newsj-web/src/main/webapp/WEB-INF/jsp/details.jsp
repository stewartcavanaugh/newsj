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

<%--@elvariable id="site" type="net.longfalcon.newsj.model.Site"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <h1>${text:escapeHtml(release.searchName)}</h1>

            <c:if test="${tvInfo != null && tvInfo.hasImage}">
                <img class="shadow" src="${pageContext.request.contextPath}/images?type=tvrage&id=${tvInfo.id}" width="180" alt="${text:escapeHtml(tvInfo.releaseTitle)}" style="float:right;" />
            </c:if>
            <c:if test="${movieInfo != null && movieInfo.cover}">
                <img class="shadow" src="${pageContext.request.contextPath}/images/covers/movies/${movieInfo.id}-cover.jpg" width="180" alt="${text:escapeHtml(movieInfo.title)}" style="float:right;" />
            </c:if>
            <c:if test="${gameInfo != null && gameInfo.cover}">
                <img class="shadow" src="${pageContext.request.contextPath}/images/covers/console/${gameInfo.id}.jpg" width="160" alt="${text:escapeHtml(gameInfo.title)}" style="float:right;" />
            </c:if>
            <c:if test="${musicInfo != null && musicInfo.cover}">
                <img class="shadow" src="${pageContext.request.contextPath}/images/covers/music/${musicInfo.id}.jpg" width="160" alt="${text:escapeHtml(musicInfo.title)}" style="float:right;" />
            </c:if>

            <table class="data" id="detailstable" >
                <c:if test="${isAdmin}">
                    <tr>
                        <th>Admin:</th>
                        <td>
                            <a class="rndbtn" href="${pageContext.request.contextPath}/admin/release-edit?id=${release.id}&from=${pageContext.request.contextPath}${pageContext.request.requestURI}" title="Edit Release">Edit</a>
                            <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/release-delete?id=${release.id}&amp;from=${pageContext.request.getHeader("referer")}" title="Delete Release">Delete</a>
                            <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/release-rebuild?id=${release.id}&amp;from=${pageContext.request.getHeader("referer")}" title="Rebuild Release - Delete and reset for reprocessing if binaries still exist.">Rebuild</a>
                        </td>
                    </tr>
                </c:if>

                <tr>
                    <th>Name:</th>
                    <td>${text:escapeHtml(release.name)}</td>
                </tr>

                <c:if test="${tvInfo != null}">
                    <tr>
                        <th>Tv Info:</th>
                        <td>
                            <strong>
                                <c:if test="${!text:isNull(release.tvTitle)}">
                                    ${text:escapeHtml(release.tvTitle)} -
                                </c:if>
                                ${text:replace(text:replace(release.seriesFull, "S", "Season "), "E", " Episode")}
                            </strong>
                            <br />
                            <c:if test="${!text:isNull(tvInfo.description)}">
                                <span class="descinitial">
                                    ${text:truncate(text:nl2br(text:escapeHtml(tvInfo.description)), 350)}
                                    <c:if test="${tvInfo.description.length() > 350}">
                                        <a class="descmore" href="#">more...</a>
                                    </c:if>
                                </span>
                                <c:if test="${tvInfo.description.length() > 350}">
                                    <span class="descfull">text:nl2br(text:escapeHtml(tvInfo.description))</span>
                                </c:if>
                                <br /><br />
                            </c:if>
                            <c:if test="${!text:isNull(tvInfo.genre)}">
                                <strong>Genre:</strong> ${text:replace(text:escapeHtml(tvInfo.genre), "|", ", ")}<br />
                            </c:if>
                            <c:if test="${release.tvAirDate != null}">
                                <strong>Aired:</strong> ${date:formatDate(release.tvAirDate)}<br/>
                            </c:if>
                            <c:if test="${!text:isNull(tvInfo.country)}">
                                <strong>Country:</strong> ${tvInfo.country}
                            </c:if>
                            <div style="margin-top:10px;">
                                <a class="rndbtn" title="View all episodes from this series" href="${pageContext.request.contextPath}/series/${release.rageId}">All Episodes</a>
                                <a class="rndbtn" target="_blank" href="${site.deReferrerLink}http://www.tvrage.com/shows/id-${tvInfo.rageId}" title="View at TV Rage">TV Rage</a>
                                <a class="rndbtn" href="${pageContext.request.contextPath}/rss?rage=${release.rageId}&dl=1&i=${userId}&r=${rssToken}" title="Rss feed for this series">Series Rss Feed</a>
                            </div>
                        </td>
                    </tr>
                </c:if>

                <c:if test="${movieInfo != null}">
                    <tr>
                        <th>Movie Info:</th>
                        <td>
                            <strong>${text:escapeHtml(movieInfo.title)} (${movieInfo.year}) <c:if test="${text:isNull(movieInfo.rating)}">N/A</c:if>${movieInfo.rating}/10</strong>
                            <c:if test="${!text:isNull(movieInfo.tagline)}">
                                <br />${text:escapeHtml(movieInfo.tagline)}
                            </c:if>
                            <c:if test="${!text:isNull(movieInfo.plot)}">
                                <c:choose>
                                    <c:when test="${!text:isNull(movieInfo.tagline)}"> - </c:when>
                                    <c:otherwise><br /></c:otherwise>
                                </c:choose>
                                ${text:escapeHtml(movieInfo.plot)}
                            </c:if>

                            <br/><br/>

                            <c:if test="${!text:isNull(movieInfo.director)}">
                                <strong>Director:</strong> ${movieInfo.director}<br />
                            </c:if>
                            <strong>Genre:</strong> ${movieInfo.genre}
                            <br /><strong>Starring:</strong> ${movieInfo.actors}
                            <div style="margin-top:10px;">
                                <a class="rndbtn" target="_blank" href="${site.deReferrerLink}http://www.imdb.com/title/tt${text:formatImdbId(release.imdbId)}/" title="View at IMDB">IMDB</a>
                                <c:if test="${movieInfo.tmdbId != null && movieInfo.tmdbId > 0}">
                                    <a class="rndbtn" target="_blank" href="${site.deReferrerLink}http://www.themoviedb.org/movie/${movieInfo.tmdbId}" title="View at TMDb">TMDb</a>
                                </c:if>

                            </div>
                        </td>
                    </tr>    
                </c:if>

                <c:if test="${gameInfo != null}">
                    <tr>
                        <th>Console Info:</th>
                            <td>
                            <strong>${text:escapeHtml(gameInfo.title)} (${date:formatDateCustom(gameInfo.releaseDate, "Y")})</strong>
                                <br />
                                <c:if test="${!text:isNull(gameInfo.review)}">
                                    <span class="descinitial">
                                        ${text:truncate(text:nl2br(text:escapeHtml(gameInfo.review)), 350)}
                                        <c:if test="${gameInfo.review.length() > 350}">
                                            <a class="descmore" href="#">more...</a>
                                        </c:if>
                                    </span>
                                        <c:if test="${gameInfo.review.length() > 350}">
                                            <span class="descfull">text:nl2br(text:escapeHtml(gameInfo.review))</span>
                                        </c:if>
                                    <br /><br />
                                </c:if>
                                <c:if test="${!text:isNull(gameInfo.esrb)}">
                                    <strong>ESRB:</strong> ${gameInfo.esrb}<br />
                                </c:if>
                                <c:if test="${!text:isNull(gameInfo.genreId)}">
                                    <strong>Genre:</strong> ${gameInfo.genreId}<br />
                                </c:if>
                                <c:if test="${!text:isNull(gameInfo.publisher)}">
                                    <strong>Publisher:</strong> ${gameInfo.publisher}<br />
                                </c:if>
                                <c:if test="${!text:isNull(gameInfo.platform)}">
                                    <strong>Platform:</strong> ${gameInfo.platform}<br />
                                </c:if>
                                <c:if test="${!text:isNull(gameInfo.releaseDate)}">
                                    <strong>Released:</strong> ${date:formatDate(gameInfo.releaseDate)}
                                </c:if>
                                <c:if test="${!text:isNull(gameInfo.url)}">
                                    <div style="margin-top:10px;">
                                        <a class="rndbtn" target="_blank" href="${site.deReferrerLink}${gameInfo.url}/" title="View game at Amazon">Amazon</a>
                                    </div>
                                </c:if>
                        </td>
                    </tr>
                </c:if>

                <c:if test="${musicInfo != null}">
                    <tr>
                        <th>Music Info:</th>
                        <td>
                            <strong>${text:escapeHtml(musicInfo.title)} (${musicInfo.year})</strong><br />
                            <c:if test="${!text:isNull(musicInfo.review)}">
                                <span class="descinitial">
                                        ${text:truncate(text:nl2br(text:escapeHtml(musicInfo.review)), 350)}
                                        <c:if test="${musicInfo.review.length() > 350}">
                                            <a class="descmore" href="#">more...</a>
                                        </c:if>
                                    </span>
                                <c:if test="${musicInfo.review.length() > 350}">
                                    <span class="descfull">text:nl2br(text:escapeHtml(musicInfo.review))</span>
                                </c:if>
                                <br /><br />
                            </c:if>
                            <c:if test="${!text:isNull(musicInfo.genreId)}">
                                <strong>Genre:</strong> ${musicInfo.genreId}<br />
                            </c:if>
                            <c:if test="${!text:isNull(musicInfo.publisher)}">
                                <strong>Publisher:</strong> ${musicInfo.publisher}<br />
                            </c:if>
                            <c:if test="${musicInfo.releaseDate != null}">
                                <strong>Released:</strong> ${date:formatDate(musicInfo.releaseDate)}<br />
                            </c:if>
                            <c:if test="${!text:isNull(musicInfo.url)}">
                                <div style="margin-top:10px;">
                                    <a class="rndbtn" target="_blank" href="${site.deReferrerLink}${musicInfo.url}/" title="View record at Amazon">Amazon</a>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                    <c:if test="${!text:isNull(musicInfo.tracks)}">
                        <tr>
                            <th>Track Listing:</th>
                            <td>
                                <ol class="tracklist">
                                    <c:forTokens items="${musicInfo.tracks}" delims="|" var="trackName">
                                        <li>${text:escapeHtml(trackName)}</li>
                                    </c:forTokens>
                                </ol>
                            </td>
                        </tr>
                    </c:if>
                </c:if>

                <tr>
                    <th>Group:</th>
                    <td title="${release.groupName}">
                        <a title="Browse ${release.groupName}" href="${pageContext.request.contextPath}/browse?g=${release.groupName}">${text:replace(release.groupName, "alt.binaries", "a.b")}</a>
                    </td>
                </tr>
                <tr>
                    <th>Category:</th>
                    <td>
                        <a title="Browse by ${release.categoryDisplayName}" href="${pageContext.request.contextPath}/browse?t=${release.categoryId}">${release.categoryDisplayName}</a>
                    </td>
                </tr>
                <c:if test="${nfo != null && nfo.id > 0}">
                    <tr>
                        <th>Nfo:</th>
                        <td>
                            <a href="${pageContext.request.contextPath}/nfo/${release.guid}" title="View Nfo">View Nfo</a>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <th>Size:</th>
                    <td> ${text:formatFileSize(release.size, true)}
                        <c:if test="${release.completion > 0}">
                            &nbsp;
                            (<c:choose>
                                <c:when test="${release.completion < 100}">
                                    <span class="warning">${release.completion}%</span>
                                </c:when>
                                <c:otherwise>
                                    ${release.completion}%
                                </c:otherwise>
                            </c:choose>)
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <th>Grabs:</th>
                    <td>
                    ${release.grabs} time<c:if test="${release.grabs != 1}">s</c:if>
                    </td>
                </tr>
                <tr>
                    <th>Files:</th>
                    <td>
                        <a title="View file list" href="${pageContext.request.contextPath}/filelist/${release.guid}">${release.totalpart} file<c:if test="${release.totalpart != 1}">s</c:if></a>
                    </td>
                </tr>

                <c:if test="${site.checkPasswordedRar == 1}">
                    <tr>
                        <th>Password:</th>
                        <td>
                            <c:choose>
                                <c:when test="${release.passwordStatus == 0}">
                                    None
                                </c:when>
                                <c:when test="${release.passwordStatus == 1}">
                                    Passworded Rar Archive
                                </c:when>
                                <c:when test="${release.passwordStatus == 2}">
                                    Contains Cab/Ace Archive
                                </c:when>
                                <c:otherwise>
                                    Unknown
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <th>Poster:</th>
                    <td>${text:escapeHtml(release.fromName)}</td>
                </tr>
                <tr>
                    <th>Posted:</th>
                    <td title="${release.postDate}">${date:formatDate(release.postDate)} (${date:timeAgo(release.postDate)})</td>
                </tr>
                <tr>
                    <th>Added:</th>
                    <td title="${release.addDate}">${date:formatDate(release.addDate)} (${date:timeAgo(release.addDate)})</td>
                </tr>
                <tr id="guid${release.guid}">
                    <th>Download:</th>
                    <td>
                        <div class="icon icon_nzb">
                            <a title="Download Nzb" href="${pageContext.request.contextPath}/getnzb/${release.guid}/${text:escapeHtml(release.searchName)}">&nbsp;</a>
                        </div>
                        <div class="icon icon_cart" title="Add to Cart"></div>
                        <div class="icon icon_sab" title="Send to my Sabnzbd"></div>
                    </td>
                </tr>

                <c:if test="${similars.size() > 0}">
                    <tr>
                        <th>Similar:</th>
                        <td>
                            <c:forEach items="${similars}" var="similarRelease" varStatus="rowNum">
                                <a title="View similar Nzb details" href="${pageContext.request.contextPath}/details/${similarRelease.guid}/${text:escapeHtml(similarRelease.searchName)}">${text:escapeHtml(similarRelease.searchName)}</a>
                                <br/>
                            </c:forEach>
                            <br/>
                            <a title="Search for similar Nzbs" href="${pageContext.request.contextPath}/search/${text:escapeHtml(searchTokens)}">Search for similar NZBs...</a><br/>
                        </td>
                    </tr>
                </c:if>

                <c:if test="${isAdmin}">
                    <tr>
                        <th>Release Info:</th>
                        <td>
                            Regex Id (<a href="${pageContext.request.contextPath}/admin/regex-list#${release.regexId}">${release.regexId}</a>) <br/>
                            <c:if test="${release.reqId > 0}">
                                Request Id (${release.reqId})
                            </c:if>

                        </td>
                    </tr>
                </c:if>

            </table>

            <div class="comments">
                <a id="comments"></a>
                <h2>Comments</h2>

                <c:if test="${commentsList.size() > 0}">
                    <table style="margin-bottom:20px;" class="data Sortable">
                        <tr>
                            <th width="80">User</th>
                            <th>Comment</th>
                        </tr>
                        <c:forEach items="${commentsList}" var="comment" varStatus="rowNum">
                            <tr>
                                <td class="less" title="${comment.createDate}">
                                    <a title="View ${comment.user.username}'s profile" href="${pageContext.request.contextPath}/profile?name=${comment.user.username}">${comment.user.username}</a>
                                    <br/>${date:formatDate(comment.createDate)}
                                </td>
                                <td>${text:nl2br(text:escapeHtml(comment.text))}</td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>

                <form action="" method="post">
                    <label for="txtAddComment">Add Comment</label>:<br/>
                    <textarea id="txtAddComment" name="txtAddComment" rows="6" cols="60"></textarea>
                    <br/>
                    <input type="submit" value="submit"/>
                </form>

            </div>

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

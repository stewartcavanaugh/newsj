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
<%--@elvariable id="site" type="net.longfalcon.newsj.model.Site"--%>
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
            <h1>Browse ${catName}</h1>

            <form name="browseby" action="movies">
                <table class="rndbtn" border="0" cellpadding="2" cellspacing="0">
                    <tr>
                        <th class="left"><label for="movietitle">Title</label></th>
                        <th class="left"><label for="movieactors">Actor</label></th>
                        <th class="left"><label for="moviedirector">Director</label></th>
                        <th class="left"><label for="rating">Rating</label></th>
                        <th class="left"><label for="genre">Genre</label></th>
                        <th class="left"><label for="year">Year</label></th>
                        <th class="left"><label for="category">Category</label></th>
                        <th></th>
                    </tr>
                    <tr>
                        <td><input id="movietitle" type="text" name="title" value="${titleSearch}" size="15" /></td>
                        <td><input id="movieactors" type="text" name="actors" value="${actorsSearch}" size="15" /></td>
                        <td><input id="moviedirector" type="text" name="director" value="${directorSearch}" size="15" /></td>
                        <td>
                            <select id="rating" name="rating">
                                <option class="grouping" value=""></option>
                                <c:forEach items="${ratings}" var="ratingValue">
                                    <option <c:if test="${ratingSearch == ratingValue}">selected="selected"</c:if> value="${ratingValue}">${ratingValue}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select id="genre" name="genre">
                                <option class="grouping" value=""></option>
                                <c:forEach items="${genreList}" var="genreValue">
                                    <option <c:if test="${genreSearch == genreValue}">selected="selected"</c:if> value="${genreValue}">${genreValue}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select id="year" name="year">
                                <option class="grouping" value=""></option>
                                <c:forEach items="${years}" var="yearValue">
                                    <option <c:if test="${yearSearch == yearValue}">selected="selected"</c:if> value="${yearValue}">${yearValue}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select id="category" name="t">
                                <option class="grouping" value="2000"></option>
                                <c:forEach items="${movieCats}" var="movieCat">
                                    <option <c:if test="${categoryId == movieCat.id}">selected="selected"</c:if> value="${movieCat.id}">${movieCat.title}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td><input type="submit" value="Go" /></td>
                    </tr>
                </table>
            </form>
            <p></p>

            <c:if test="${movieInfoList.size() > 0}">
                <form id="nzb_multi_operations_form" action="get">

                    <div class="nzb_multi_operations">
                        View: <b>Covers</b> | <a href="${pageContext.request.contextPath}/browse?t=${categoryId}">List</a><br />
                        <small>With Selected:</small>
                        <input type="button" class="nzb_multi_operations_download" value="Download NZBs" />
                        <input type="button" class="nzb_multi_operations_cart" value="Add to Cart" />
                        <input type="button" class="nzb_multi_operations_sab" value="Send to SAB" />
                    </div>
                    <br/>

                    <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}" pagerOffset="${pagerOffset}"
                                pagerQueryBase="${pageContext.request.contextPath}/movies?title=${titleSearch}&actors=${actorsSearch}&director=${directorSearch}&rating=${ratingSearch}&genre=${genreSearch}&year=${yearSearch}&t=${categoryId}&ob=${orderBy}&offset="/>

                    <table style="width:100%;" class="data highlight icons" id="coverstable">
                        <tr>
                            <th width="130"><input type="checkbox" class="nzb_check_all" /></th>
                            <th>title<br/>
                                <a title="Sort Descending" href="${pageContext.request.contextPath}/movies?title=${titleSearch}&actors=${actorsSearch}&director=${directorSearch}&rating=${ratingSearch}&genre=${genreSearch}&year=${yearSearch}&t=${categoryId}&ob=title_desc&offset=${pagerOffset}">
                                    <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                                </a>
                                <a title="Sort Ascending" href="${pageContext.request.contextPath}/movies?title=${titleSearch}&actors=${actorsSearch}&director=${directorSearch}&rating=${ratingSearch}&genre=${genreSearch}&year=${yearSearch}&t=${categoryId}&ob=title_asc&offset=${pagerOffset}">
                                    <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                                </a>
                            </th>
                            <th>year<br/>
                                <a title="Sort Descending" href="${pageContext.request.contextPath}/movies?title=${titleSearch}&actors=${actorsSearch}&director=${directorSearch}&rating=${ratingSearch}&genre=${genreSearch}&year=${yearSearch}&t=${categoryId}&ob=year_desc&offset=${pagerOffset}">
                                    <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                                </a>
                                <a title="Sort Ascending" href="${pageContext.request.contextPath}/movies?title=${titleSearch}&actors=${actorsSearch}&director=${directorSearch}&rating=${ratingSearch}&genre=${genreSearch}&year=${yearSearch}&t=${categoryId}&ob=year_asc&offset=${pagerOffset}">
                                    <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                                </a>
                            </th>
                            <th>rating<br/>
                                <a title="Sort Descending" href="${pageContext.request.contextPath}/movies?title=${titleSearch}&actors=${actorsSearch}&director=${directorSearch}&rating=${ratingSearch}&genre=${genreSearch}&year=${yearSearch}&t=${categoryId}&ob=rating_desc&offset=${pagerOffset}">
                                    <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="" />
                                </a>
                                <a title="Sort Ascending" href="${pageContext.request.contextPath}/movies?title=${titleSearch}&actors=${actorsSearch}&director=${directorSearch}&rating=${ratingSearch}&genre=${genreSearch}&year=${yearSearch}&t=${categoryId}&ob=rating_asc&offset=${pagerOffset}">
                                    <img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="" />
                                </a>
                            </th>
                        </tr>

                        <c:forEach items="${movieInfoList}" var="movieInfo" varStatus="rowNum">
                            <tr class="${text:cycle(rowNum, "", "alt")}">
                            <td class="mid">
                                <div class="movcover">
                                    <a target="_blank" href="${site.deReferrerLink}http://www.imdb.com/title/tt${text:formatImdbId(movieInfo.imdbId)}/" name="name${text:formatImdbId(movieInfo.imdbId)}" title="View movie info" class="modal_imdb" rel="movie" >
                                        <img class="shadow"
                                             src='<c:choose><c:when test="${movieInfo.cover}">${pageContext.request.contextPath}/images/covers/movies/${movieInfo.id}-cover.jpg</c:when><c:otherwise>${pageContext.request.contextPath}/resources/images/covers/movies/no-cover.jpg</c:otherwise></c:choose>'
                                             width="120" border="0" alt="${text:escapeHtml(movieInfo.title)}" />
                                    </a>
                                    <div class="movextra">
                                        <a target="_blank" href="${site.deReferrerLink}http://www.imdb.com/title/tt${text:formatImdbId(movieInfo.imdbId)}/" name="name${text:formatImdbId(movieInfo.imdbId)}" title="View movie info" class="rndbtn modal_imdb" rel="movie" >Cover</a>
                                        <a class="rndbtn" target="_blank" href="${site.deReferrerLink}http://www.imdb.com/title/tt${text:formatImdbId(movieInfo.imdbId)}/" name="imdb${text:formatImdbId(movieInfo.imdbId)}" title="View imdb page">Imdb</a>
                                    </div>
                                </div>
                            </td>
                            <td colspan="3" class="left">
                                <h2>${text:escapeHtml(movieInfo.title)} (<a class="title" title="${movieInfo.year}" href="${pageContext.request.contextPath}/movies?year=${movieInfo.year}">${movieInfo.year}</a>) <c:if test="${!text:isNull(movieInfo.rating)}">${movieInfo.rating}/10</c:if> </h2>
                                <c:if test="${!text:isNull(movieInfo.tagline)}"><b>${movieInfo.tagline}</b><br /></c:if>
                                <c:if test="${!text:isNull(movieInfo.plot)}"><b>${movieInfo.plot}</b><br /></c:if>
                                <c:if test="${!text:isNull(movieInfo.genre)}"><b>Genre:</b> ${movieInfo.genre}<br /></c:if>
                                <c:if test="${!text:isNull(movieInfo.director)}"><b>Director:</b> ${movieInfo.director}<br /></c:if>
                                <c:if test="${!text:isNull(movieInfo.actors)}"><b>Starring:</b> ${movieInfo.actors}<br /><br /></c:if>
                                <div class="movextra">
                                    <table>
                                        <c:forEach items="${movieReleaseInfoMap.get(movieInfo.id)}" var="release" varStatus="status">
                                            <%--@elvariable id="release" type="net.longfalcon.newsj.model.Release"--%>
                                            <tr id="guid${release.guid}" <c:if test="${status.index > 1}">class="mlextra"</c:if> >
                                                <td>
                                                    <div class="icon">
                                                        <input type="checkbox" class="nzb_check" value="${release.guid}" />
                                                    </div>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/details/${release.guid}/${text:escapeHtml(release.searchName)}">${text:escapeHtml(release.searchName)}</a>
                                                    <div>
                                                        Posted ${date:timeAgo(release.postDate)},  ${text:formatFileSize(release.size, true)},
                                                        <a title="View file list" href="${pageContext.request.contextPath}/filelist/${release.guid}">${release.totalpart} files</a>,
                                                        <a title="View comments for ${text:escapeHtml(release.searchName)}" href="${pageContext.request.contextPath}/details/${release.guid}/${text:escapeHtml(release.searchName)}#comments">
                                                            ${release.comments} cmt<c:if test="${release.comments > 1}">s</c:if>
                                                        </a>, ${release.grabs} grab<c:if test="${release.grabs > 1}">s</c:if>,
                                                        <c:if test="${release.releaseNfo != null}">
                                                            <a href="${pageContext.request.contextPath}/nfo/${release.guid}" title="View Nfo" class="modal_nfo" rel="nfo">Nfo</a>,
                                                        </c:if>
                                                        <a href="${pageContext.request.contextPath}/browse?g=${release.groupName}" title="Browse releases in ${text:replace(release.groupName, "alt.binaries", "a.b")}">Grp</a>
                                                    </div>
                                                </td>
                                                <td class="icons">
                                                    <div class="icon icon_nzb"><a title="Download Nzb" href="${pageContext.request.contextPath}/getnzb/${release.guid}/${text:escapeHtml(release.searchName)}">&nbsp;</a></div>
                                                    <div class="icon icon_cart" title="Add to Cart"></div>
                                                    <div class="icon icon_sab" title="Send to my Sabnzbd"></div>
                                                </td>
                                            </tr>
                                            <c:if test="${status.index == 1 && movieReleaseInfoMap.get(movieInfo.id).size() > 2}">
                                                <tr><td colspan="5"><a class="mlmore" href="#">${movieReleaseInfoMap.get(movieInfo.id).size()-2} more...</a></td></tr>
                                            </c:if>
                                        </c:forEach>
                                    </table>
                                </div>
                            </td>
                            </tr>    
                        </c:forEach>
                    </table>

                    <br/>

                    <tags:pager pagerTotalItems="${pagerTotalItems}" pagerItemsPerPage="${pagerItemsPerPage}" pagerOffset="${pagerOffset}"
                                pagerQueryBase="${pageContext.request.contextPath}/movies?title=${titleSearch}&actors=${actorsSearch}&director=${directorSearch}&rating=${ratingSearch}&genre=${genreSearch}&year=${yearSearch}&t=${categoryId}&ob=${orderBy}&offset="/>

                    <div class="nzb_multi_operations">
                        <small>With Selected:</small>
                        <input type="button" class="nzb_multi_operations_download" value="Download NZBs" />
                        <input type="button" class="nzb_multi_operations_cart" value="Add to Cart" />
                        <input type="button" class="nzb_multi_operations_sab" value="Send to SAB" />
                    </div>

                </form>    
            </c:if>

            <br/><br/><br/>

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

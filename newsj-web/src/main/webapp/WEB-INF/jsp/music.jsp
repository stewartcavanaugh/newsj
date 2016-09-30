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

            <h1>Browse Music</h1>

            <form name="browseby" action="music">
                <table class="rndbtn" border="0" cellpadding="2" cellspacing="0">
                    <tr>
                        <th class="left"><label for="musicartist">Artist</label></th>
                        <th class="left"><label for="musictitle">Title</label></th>
                        <th class="left"><label for="genre">Genre</label></th>
                        <th class="left"><label for="year">Year</label></th>
                        <th class="left"><label for="category">Category</label></th>
                        <th></th>
                    </tr>
                    <tr>
                        <td><input id="musicartist" type="text" name="artist" value="${artist}" size="15" /></td>
                        <td><input id="musictitle" type="text" name="title" value="${title}" size="15" /></td>
                        <td>
                            <select id="genre" name="genreId">
                                <option class="grouping" value=""></option>
                                <c:forEach items="${genreList}" var="genreItem">
                                    <option <c:if test="${genreId == genreItem.id}">selected="selected"</c:if> value="${genreItem.id}">${text:escapeHtml(genreItem.title)}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select id="year" name="year">
                                <option class="grouping" value=""></option>
                                <c:forEach items="${years}" var="yearItem">
                                    <option <c:if test="${year == yearItem}">selected="selected"</c:if> value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td>
                            <select id="category" name="t">
                                <option class="grouping" value="3000"></option>
                                <c:forEach items="${categoryList}" var="categoryItem">
                                    <option <c:if test="${categoryId == categoryItem.id}">selected="selected"</c:if> value="${categoryItem.id}">${categoryItem.title}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td><input type="submit" value="Go" /></td>
                    </tr>
                </table>
            </form>
            <p></p>
            <h1>TODO</h1>
            <%--{if $results|@count > 0}

            <form id="nzb_multi_operations_form" action="get">

                <div class="nzb_multi_operations">
                    View: <b>Covers</b> | <a href="{$smarty.const.WWW_TOP}/browse?t={$category}">List</a><br />
                    <small>With Selected:</small>
                    <input type="button" class="nzb_multi_operations_download" value="Download NZBs" />
                    <input type="button" class="nzb_multi_operations_cart" value="Add to Cart" />
                    <input type="button" class="nzb_multi_operations_sab" value="Send to SAB" />
                </div>
                <br/>

                {$pager}

                <table style="width:100%;" class="data highlight icons" id="coverstable">
                    <tr>
                        <th width="130"><input type="checkbox" class="nzb_check_all" /></th>
                        <th>artist<br/><a title="Sort Descending" href="{$orderbyartist_desc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_down.gif" alt="" /></a><a title="Sort Ascending" href="{$orderbyartist_asc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_up.gif" alt="" /></a></th>
                        <th>year<br/><a title="Sort Descending" href="{$orderbyyear_desc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_down.gif" alt="" /></a><a title="Sort Ascending" href="{$orderbyyear_asc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_up.gif" alt="" /></a></th>
                        <th>genre<br/><a title="Sort Descending" href="{$orderbygenre_desc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_down.gif" alt="" /></a><a title="Sort Ascending" href="{$orderbygenre_asc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_up.gif" alt="" /></a></th>
                        <th>posted<br/><a title="Sort Descending" href="{$orderbyposted_desc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_down.gif" alt="" /></a><a title="Sort Ascending" href="{$orderbyposted_asc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_up.gif" alt="" /></a></th>
                        <th>size<br/><a title="Sort Descending" href="{$orderbysize_desc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_down.gif" alt="" /></a><a title="Sort Ascending" href="{$orderbysize_asc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_up.gif" alt="" /></a></th>
                        <th>files<br/><a title="Sort Descending" href="{$orderbyfiles_desc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_down.gif" alt="" /></a><a title="Sort Ascending" href="{$orderbyfiles_asc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_up.gif" alt="" /></a></th>
                        <th>stats<br/><a title="Sort Descending" href="{$orderbystats_desc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_down.gif" alt="" /></a><a title="Sort Ascending" href="{$orderbystats_asc}"><img src="{$smarty.const.WWW_TOP}/views/images/sorting/arrow_up.gif" alt="" /></a></th>
                    </tr>

                    {foreach from=$results item=result}
                    <tr class="{cycle values=",alt"}">
                    <td class="mid">
                        <div class="movcover">
                            <a class="title" title="View details" href="{$smarty.const.WWW_TOP}/details/{$result.guid}/{$result.searchname|escape:"htmlall"}">
                            <img class="shadow" src="{$smarty.const.WWW_TOP}/covers/music/{if $result.cover == 1}{$result.musicinfoID}.jpg{else}no-cover.jpg{/if}" width="120" border="0" alt="{$result.artist|escape:"htmlall"} - {$result.title|escape:"htmlall"}" />
                            </a>
                            <div class="movextra">
                                {if $result.nfoID > 0}<a href="{$smarty.const.WWW_TOP}/nfo/{$result.guid}" title="View Nfo" class="rndbtn modal_nfo" rel="nfo">Nfo</a>{/if}
                                <a class="rndbtn" target="_blank" href="{$site->dereferrer_link}{$result.url}" name="amazon{$result.musicinfoID}" title="View amazon page">Amazon</a>
                                <a class="rndbtn" href="{$smarty.const.WWW_TOP}/browse?g={$result.group_name}" title="Browse releases in {$result.group_name|replace:"alt.binaries":"a.b"}">Grp</a>
                            </div>
                        </div>
                    </td>
                    <td colspan="7" class="left" id="guid{$result.guid}">
                        <h2><a class="title" title="View details" href="{$smarty.const.WWW_TOP}/details/{$result.guid}/{$result.searchname|escape:"htmlall"}">{$result.artist|escape:"htmlall"} - {$result.title|escape:"htmlall"}</a> (<a class="title" title="{$result.year}" href="{$smarty.const.WWW_TOP}/music?year={$result.year}">{$result.year}</a>)</h2>
                        {if $result.genre != ""}<b>Genre:</b> {$result.genre|escape:"htmlall"}<br />{/if}
                        {if $result.publisher != ""}<b>Publisher:</b> {$result.publisher|escape:"htmlall"}<br />{/if}
                        {if $result.releasedate != ""}<b>Released:</b> {$result.releasedate|date_format}<br />{/if}
                        <br />
                        <div class="movextra">
                            <b>{$result.searchname|escape:"htmlall"}</b> <a class="rndbtn" href="{$smarty.const.WWW_TOP}/music?artist={$result.artist}" title="View similar nzbs">Similar</a>
                            {if $isadmin}
                            <a class="rndbtn" href="{$smarty.const.WWW_TOP}/admin/release-edit.php?id={$result.releaseID}&amp;from={$smarty.server.REQUEST_URI|escape:"url"}" title="Edit Release">Edit</a> <a class="rndbtn confirm_action" href="{$smarty.const.WWW_TOP}/admin/release-delete.php?id={$result.releaseID}&amp;from={$smarty.server.REQUEST_URI|escape:"url"}" title="Delete Release">Del</a> <a class="rndbtn confirm_action" href="{$smarty.const.WWW_TOP}/admin/release-rebuild.php?id={$result.releaseID}&amp;from={$smarty.server.REQUEST_URI|escape:"url"}" title="Rebuild Release - Delete and reset for reprocessing if binaries still exist.">Reb</a>
                            {/if}
                            <br />
                            <b>Info:</b> {$result.postdate|timeago},  {$result.size|fsize_format:"MB"},  <a title="View file list" href="{$smarty.const.WWW_TOP}/filelist/{$result.guid}">{$result.totalpart} files</a>,  <a title="View comments for {$result.searchname|escape:"htmlall"}" href="{$smarty.const.WWW_TOP}/details/{$result.guid}/{$result.searchname|escape:"htmlall"}#comments">{$result.comments} cmt{if $result.comments != 1}s{/if}</a>, {$result.grabs} grab{if $result.grabs != 1}s{/if}
                            <br />
                            <div class="icon"><input type="checkbox" class="nzb_check" value="{$result.guid}" /></div>
                            <div class="icon icon_nzb"><a title="Download Nzb" href="{$smarty.const.WWW_TOP}/getnzb/{$result.guid}/{$result.searchname|escape:"htmlall"}">&nbsp;</a></div>
                            <div class="icon icon_cart" title="Add to Cart"></div>
                            <div class="icon icon_sab" title="Send to my Sabnzbd"></div>
                        </div>
                    </td>
                    </tr>
                    {/foreach}

                </table>

                <br/>

                {$pager}

                <div class="nzb_multi_operations">
                    <small>With Selected:</small>
                    <input type="button" class="nzb_multi_operations_download" value="Download NZBs" />
                    <input type="button" class="nzb_multi_operations_cart" value="Add to Cart" />
                    <input type="button" class="nzb_multi_operations_sab" value="Send to SAB" />
                </div>

            </form>

            {/if}--%>

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

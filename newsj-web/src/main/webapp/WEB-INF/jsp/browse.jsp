<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <h1>Browse {$catname|escape:"htmlall"}</h1>

            {if $results|@count > 0}

            <form id="nzb_multi_operations_form" action="get">

                <div class="nzb_multi_operations">
                    {if $section != ''}View: <a href="${pageContext.request.contextPath}/{$section}?t={$category}">Covers</a> | <b>List</b><br />{/if}
                    <small>With Selected:</small>
                    <input type="button" class="nzb_multi_operations_download" value="Download NZBs" />
                    <input type="button" class="nzb_multi_operations_cart" value="Add to Cart" />
                    <input type="button" class="nzb_multi_operations_sab" value="Send to SAB" />
                    {if $isadmin}
                    &nbsp;&nbsp;
                    <input type="button" class="nzb_multi_operations_edit" value="Edit" />
                    <input type="button" class="nzb_multi_operations_delete" value="Del" />
                    <input type="button" class="nzb_multi_operations_rebuild" value="Reb" />
                    {/if}
                </div>

                {$pager}

                <table style="width:100%;" class="data highlight icons" id="browsetable">
                    <tr>
                        <th><input id="chkSelectAll" type="checkbox" class="nzb_check_all" /><label for="chkSelectAll" style="display:none;">Select All</label></th>
                        <th>name<br/><a title="Sort Descending" href="{$orderbyname_desc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" /></a><a title="Sort Ascending" href="{$orderbyname_asc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" /></a></th>
                        <th>category<br/><a title="Sort Descending" href="{$orderbycat_desc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" /></a><a title="Sort Ascending" href="{$orderbycat_asc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" /></a></th>
                        <th>posted<br/><a title="Sort Descending" href="{$orderbyposted_desc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" /></a><a title="Sort Ascending" href="{$orderbyposted_asc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" /></a></th>
                        <th>size<br/><a title="Sort Descending" href="{$orderbysize_desc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" /></a><a title="Sort Ascending" href="{$orderbysize_asc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" /></a></th>
                        <th>files<br/><a title="Sort Descending" href="{$orderbyfiles_desc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" /></a><a title="Sort Ascending" href="{$orderbyfiles_asc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" /></a></th>
                        <th>stats<br/><a title="Sort Descending" href="{$orderbystats_desc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_down.gif" alt="Sort Descending" /></a><a title="Sort Ascending" href="{$orderbystats_asc}"><img src="${pageContext.request.contextPath}/resources/images/sorting/arrow_up.gif" alt="Sort Ascending" /></a></th>
                        <th></th>
                    </tr>

                    {foreach from=$results item=result}
                    <tr class="{cycle values=",alt"}{if $lastvisit|strtotime<$result.adddate|strtotime} new{/if}" id="guid{$result.guid}">
                    <td class="check"><input id="chk{$result.guid|substr:0:7}" type="checkbox" class="nzb_check" value="{$result.guid}" /></td>
                    <td class="item">
                        <label for="chk{$result.guid|substr:0:7}"><a class="title" title="View details" href="${pageContext.request.contextPath}/details/{$result.guid}/{$result.searchname|escape:"htmlall"}">{$result.searchname|escape:"htmlall"|replace:".":" "}</a></label>

                        {if $result.passwordstatus == 1}
                        <img title="Passworded Rar Archive" src="${pageContext.request.contextPath}/resources/images/icons/lock.gif" alt="Passworded Rar Archive" />
                        {elseif $result.passwordstatus == 2}
                        <img title="Contains .cab/ace Archive" src="${pageContext.request.contextPath}/resources/images/icons/lock.gif" alt="Contains .cab/ace Archive" />
                        {/if}

                        <div class="resextra">
                            <div class="btns">
                                {if $result.nfoID > 0}<a href="${pageContext.request.contextPath}/nfo/{$result.guid}" title="View Nfo" class="modal_nfo rndbtn" rel="nfo">Nfo</a>{/if}
                                {if $result.imdbID > 0}<a href="#" name="name{$result.imdbID}" title="View movie info" class="modal_imdb rndbtn" rel="movie" >Cover</a>{/if}
                                {if $result.musicinfoID > 0}<a href="#" name="name{$result.musicinfoID}" title="View music info" class="modal_music rndbtn" rel="music" >Cover</a>{/if}
                                {if $result.consoleinfoID > 0}<a href="#" name="name{$result.consoleinfoID}" title="View console info" class="modal_console rndbtn" rel="console" >Cover</a>{/if}
                                {if $result.rageID > 0}<a class="rndbtn" href="${pageContext.request.contextPath}/series/{$result.rageID}" title="View all episodes">View Series</a>{/if}
                                {if $result.tvairdate != ""}<span class="rndbtn" title="{$result.tvtitle} Aired on {$result.tvairdate|date_format}">Aired {if $result.tvairdate|strtotime > $smarty.now}in future{else}{$result.tvairdate|daysago}{/if}</span>{/if}
                                <a class="rndbtn" href="${pageContext.request.contextPath}/browse?g={$result.group_name}" title="Browse releases in {$result.group_name|replace:"alt.binaries":"a.b"}">Grp</a>
                            </div>
                            {if $isadmin}
                            <div class="admin">
                                <a class="rndbtn" href="${pageContext.request.contextPath}/admin/release-edit.php?id={$result.ID}&amp;from={$smarty.server.REQUEST_URI|escape:"url"}" title="Edit Release">Edit</a> <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/release-delete.php?id={$result.ID}&amp;from={$smarty.server.REQUEST_URI|escape:"url"}" title="Delete Release">Del</a> <a class="rndbtn confirm_action" href="${pageContext.request.contextPath}/admin/release-rebuild.php?id={$result.ID}&amp;from={$smarty.server.REQUEST_URI|escape:"url"}" title="Rebuild Release - Delete and reset for reprocessing if binaries still exist.">Reb</a>
                            </div>
                            {/if}
                        </div>
                    </td>
                    <td class="less"><a title="Browse {$result.category_name}" href="${pageContext.request.contextPath}/browse?t={$result.categoryID}">{$result.category_name}</a></td>
                    <td class="less mid" title="{$result.postdate}">{$result.postdate|timeago}</td>
                    <td class="less right">{$result.size|fsize_format:"MB"}{if $result.completion > 0}<br />{if $result.completion < 100}<span class="warning">{$result.completion}%</span>{else}{$result.completion}%{/if}{/if}</td>
                    <td class="less mid"><a title="View file list" href="${pageContext.request.contextPath}/filelist/{$result.guid}">{$result.totalpart}</a></td>
                    <td class="less" nowrap="nowrap"><a title="View comments" href="${pageContext.request.contextPath}/details/{$result.guid}/#comments">{$result.comments} cmt{if $result.comments != 1}s{/if}</a><br/>{$result.grabs} grab{if $result.grabs != 1}s{/if}</td>
                    <td class="icons">
                        <div class="icon icon_nzb"><a title="Download Nzb" href="${pageContext.request.contextPath}/getnzb/{$result.guid}/{$result.searchname|escape:"htmlall"}">&nbsp;</a></div>
                        <div class="icon icon_cart" title="Add to Cart"></div>
                        <div class="icon icon_sab" title="Send to my Sabnzbd"></div>
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
                    {if $isadmin}
                    &nbsp;&nbsp;
                    <input type="button" class="nzb_multi_operations_edit" value="Edit" />
                    <input type="button" class="nzb_multi_operations_delete" value="Del" />
                    <input type="button" class="nzb_multi_operations_rebuild" value="Reb" />
                    {/if}
                </div>

            </form>

            {/if}

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
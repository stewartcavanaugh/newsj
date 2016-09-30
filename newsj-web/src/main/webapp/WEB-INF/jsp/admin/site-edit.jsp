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

<%--
  User: Sten Martinez
  Date: 12/16/15
  Time: 3:54 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="admin_common_head.jsp" %>
</head>
<body>
<div id="logo" style="cursor: pointer;">
    <h1><a href="${pageContext.request.contextPath}"></a></h1>

    <p><em></em></p>
</div>
<hr/>

<div id="header">
    <div id="menu">
    </div>
    <!-- end #menu -->
</div>

<div id="page">

    <div id="adpanel">

    </div>

    <div id="content">
        <%--START PAGE CONTENT--%>
        <h1>${title}</h1>

            <form:form modelAttribute="siteObject" action="${pageContext.request.contextPath}/admin/site-edit" method="post">

                <fieldset>
                    <legend>Main Site Settings, Html Layout, Tags</legend>
                    <table class="input">

                        <tr>
                            <td><label for="title">Title</label>:</td>
                            <td>
                                <form:input id="title" class="long" name="title" path="title" value="${siteObject.title}" />
                                <div class="hint">Displayed around the site and contact form as the name for the site.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="strapline">Strapline</label>:</td>
                            <td>
                                <form:input id="strapline" class="long" name="strapline" path="strapLine" value="${siteObject.strapLine}" />
                                <div class="hint">Displayed in the header on every public page.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="metatitle">Meta Title</label>:</td>
                            <td>
                                <form:input id="metatitle" class="long" name="metatitle" path="metaTitle" value="${siteObject.metaTitle}" />
                                <div class="hint">Stem meta-tag appended to all page title tags.</div>
                            </td>
                        </tr>


                        <tr>
                            <td><label for="metadescription">Meta Description</label>:</td>
                            <td>
                                <form:textarea id="metadescription" name="metadescription" path="metaDescription"/>
                                <div class="hint">Stem meta-description appended to all page meta description tags.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="metakeywords">Meta Keywords</label>:</td>
                            <td>
                                <form:textarea id="metakeywords" name="metakeywords" path="metaKeywords"/>
                                <div class="hint">Stem meta-keywords appended to all page meta keyword tags.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="footer">Footer</label>:</td>
                            <td>
                                <form:textarea id="footer" name="footer" path="footer"/>
                                <div class="hint">Displayed in the footer section of every public page.</div>
                            </td>
                        </tr>

                        <tr>
                            <td style="width:160px;"><label for="codename">Code Name</label>:</td>
                            <td>
                                <form:input id="codename" name="code" path="code" value="${siteObject.code}" />
                                <form:hidden name="id" path="id" value="${siteObject.id}" />
                                <div class="hint">A just for fun value shown in debug and not on public pages.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="style">Theme</label>:</td>
                            <td>
                                <%--{html_options class="siteeditstyle" id="style" name='style' values=$themelist output=$themelist selected=$fsite->style}--%>
                                <form:select path="style" cssClass="siteeditstyle" id="style">
                                    <form:option value="/" label="/"/>
                                    <form:options items="${themeNameList}"/>
                                </form:select>
                                <div class="hint">The theme folder which will be loaded for css and images. (Use / for default)</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="style">User Menu Position</label>:</td>
                            <td>
                                <%--{html_options class="siteeditmenuposition" id="menuposition" name='menuposition' values=$menupos_ids output=$menupos_names selected=$fsite->menuposition}--%>
                                    <form:select path="menuPosition" cssClass="siteeditmenuposition" id="menuposition">
                                        <form:option value="1" label="Left"/>
                                        <form:option value="2" label="Top"/>
                                    </form:select>
                                <div class="hint">Where the menu should appear.
                                    Moving the menu to the top will require using a theme which widens the content panel. (e.g. nzbsu theme)</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="style">Dereferrer Link</label>:</td>
                            <td>
                                <form:input id="dereferrer_link" class="long" name="dereferrer_link" path="deReferrerLink" value="${siteObject.deReferrerLink}" />
                                <div class="hint">Optional URL to prepend to external links</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="email">Email</label>:</td>
                            <td>
                                <form:input id="email" class="long" name="email" path="email" value="${siteObject.email}" />
                                <div class="hint">Shown in the contact us page, and where the contact html form is sent to.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="tandc">Terms and Conditions</label>:</td>
                            <td>
                                <form:textarea id="tandc" name="tandc" path="tandc"/>
                                <div class="hint">Text displayed in the terms and conditions page.</div>
                            </td>
                        </tr>

                    </table>
                </fieldset>

                <fieldset>
                    <legend>Google Adsense and Analytics</legend>
                    <table class="input">
                        <tr>
                            <td style="width:160px;"><label for="google_analytics_acc">Google Analytics</label>:</td>
                            <td>
                                <form:input id="google_analytics_acc" name="google_analytics_acc" path="googleAnalyticsAcc" value="${siteObject.googleAnalyticsAcc}" />
                                <div class="hint">e.g. UA-xxxxxx-x</div>
                            </td>
                        </tr>

                        <tr>
                            <td style="width:160px;"><label for="google_adsense_acc">Google Adsense</label>:</td>
                            <td>
                                <form:input id="google_adsense_acc" name="google_adsense_acc" path="googleAdSenseAcc" value="${siteObject.googleAdSenseAcc}" />
                                <div class="hint">e.g. pub-123123123123123</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="google_adsense_sidepanel">Google Adsense Sidepanel</label>:</td>
                            <td>
                                <form:input id="google_adsense_sidepanel" name="google_adsense_sidepanel" path="googleAdSenseSidePanel" value="${siteObject.googleAdSenseSidePanel}" />
                                <div class="hint">The ID of a google skyscraper link panel displayed at the right side of every page.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="google_adsense_search">Google Adsense Search</label>:</td>
                            <td>
                                <form:input id="google_adsense_search" name="google_adsense_search" path="googleAdSenseSearch" value="${siteObject.googleAdSenseSearch}" />
                                <div class="hint">The ID of the google search ad panel displayed at the bottom of the left menu.</div>
                            </td>
                        </tr>

                    </table>
                </fieldset>


                <fieldset>
                    <legend>3rd Party API Keys</legend>
                    <table class="input">
                        <tr>
                            <td style="width:160px;"><label for="tmdbkey">TMDB API Key</label>:</td>
                            <td>
                                <form:input id="tmdbkey" class="long" name="tmdbkey" path="tmdbKey" value="${siteObject.tmdbKey}" />
                                <div class="hint">The api key used for access to tmdb</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="amazonpubkey">Amazon Public API Key</label>:</td>
                            <td>
                                <form:input id="amazonpubkey" class="long" name="amazonpubkey" path="amazonPubKey" value="${siteObject.amazonPubKey}" />
                                <div class="hint">The amazon public api key. Used for music lookups.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="amazonprivkey">Amazon Private API Key</label>:</td>
                            <td>
                                <form:input id="amazonprivkey" class="long" name="amazonprivkey" path="amazonPrivKey" value="${siteObject.amazonPrivKey}" />
                                <div class="hint">The amazon private api key. Used for music lookups.</div>
                            </td>
                        </tr>

                    </table>
                </fieldset>



                <fieldset>
                    <legend>Usenet Settings</legend>
                    <table class="input">

                        <tr>
                            <td><label for="nzbpath">Nzb File Path</label>:</td>
                            <td>
                                <form:input id="nzbpath" class="long" name="nzbpath" path="nzbPath" value="${siteObject.nzbPath}" />
                                <div class="hint">The directory where nzb files will be stored.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="rawretentiondays">Raw Search Retention</label>:</td>
                            <td>
                                <form:input class="tiny" id="rawretentiondays" name="rawretentiondays" path="rawRetentionDays" value="${siteObject.rawRetentionDays}" />
                                <div class="hint">The number of days binary and part data will be retained for use in raw search, regardless of other processes.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="attemptgroupbindays">Days to Attempt To Group</label>:</td>
                            <td>
                                <form:input class="tiny" id="attemptgroupbindays" name="attemptgroupbindays" path="attemtpGroupBinDays" value="${siteObject.attemtpGroupBinDays}" />
                                <div class="hint">The number of days an attempt will be made to group binaries into releases after being added.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="releasedays">Release Retention</label>:</td>
                            <td>
                                <form:input class="tiny" id="releasedays" name="releaseretentiondays" path="releaseRetentionDays" value="${siteObject.releaseRetentionDays}" />
                                <div class="hint">The number of days releases will be retained for use throughout site. Set to 0 to disable.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="minfilestoformrelease">Minimum Files to Make a Release</label>:</td>
                            <td>
                                <form:input class="tiny" id="minfilestoformrelease" name="minfilestoformrelease" path="minFilesToFormRelease" value="${siteObject.minFilesToFormRelease}" />
                                <div class="hint">The minimum number of files to make a release. i.e. if set to two, then releases which only contain one file will not be created.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="checkpasswordedrar">Check For Passworded Releases</label>:</td>
                            <td>
                                <%--{html_radios id="checkpasswordedrar" name='checkpasswordedrar' values=$yesno_ids output=$yesno_names selected=$fsite->checkpasswordedrar separator='<br />'}--%>
                                <form:radiobuttons path="checkPasswordedRar" id="checkpasswordedrar" items="${yesNoMap}"/>
                                <div class="hint">Whether to attempt to peek into every release, to see if rar files are password protected.<br/></div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="showpasswordedrelease">Show Passworded Releases</label>:</td>
                            <td>
                                <%--{html_options id="showpasswordedrelease" name='showpasswordedrelease' values=$passworded_ids output=$passworded_names selected=$fsite->showpasswordedrelease}--%>
                                <form:select path="showPasswordedRelease" id="showpasswordedrelease">
                                    <form:options items="${showPasswordedRelOptionsMap}"/>
                                </form:select>
                                <div class="hint">Whether to show passworded or potentially passworded releases in browse, search, api and rss feeds. Potentially passworded means releases which contain .cab or .ace files which are typically password protected.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="reqidurl">Allfilled Request Id Lookup URL</label>:</td>
                            <td>
                                <form:input class="long" id="reqidurl" name="reqidurl" path="reqIdUrl" value="${siteObject.reqIdUrl}" />
                                <div class="hint">The url to use to translate allfilled style reqid usenet posts into real release titles. Leave blank to not perform lookup.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="reqidurl">Latest Regex Lookup URL</label>:</td>
                            <td>
                                <form:input class="long" id="latestregexurl" name="latestregexurl" path="latestRegexUrl" value="${siteObject.latestRegexUrl}" />
                                <div class="hint">The url to use to get the latest default regexs. Leave blank to not perform lookup.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="lookupnfo">Lookup Nfo</label>:</td>
                            <td>
                                <%--{html_radios id="lookupnfo" name='lookupnfo' values=$yesno_ids output=$yesno_names selected=$fsite->lookupnfo separator='<br />'}--%>
                                <form:radiobuttons path="lookupNfo" id="lookupnfo" items="${yesNoMap}"/>
                                <div class="hint">Whether to attempt to retrieve the an nfo file from usenet when processing binaries.<br/><strong>NOTE: disabling nfo lookups will disable movie lookups.</strong></div>
                            </td>
                        </tr>


                        <tr>
                            <td><label for="lookuptvrage">Lookup TV Rage</label>:</td>
                            <td>
                                <%--{html_radios id="lookuptvrage" name='lookuptvrage' values=$yesno_ids output=$yesno_names selected=$fsite->lookuptvrage separator='<br />'}--%>
                                <form:radiobuttons path="lookupTvRage" id="lookuptvrage" items="${yesNoMap}"/>
                                <div class="hint">Whether to attempt to lookup tv rage ids on the web when processing binaries.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="lookupimdb">Lookup Movies</label>:</td>
                            <td>
                                <%--{html_radios id="lookupimdb" name='lookupimdb' values=$yesno_ids output=$yesno_names selected=$fsite->lookupimdb separator='<br />'}--%>
                                    <form:radiobuttons path="lookupImdb" id="lookupimdb" items="${yesNoMap}"/>
                                <div class="hint">Whether to attempt to lookup film information from IMDB or TheMovieDB when processing binaries.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="lookupmusic">Lookup Music</label>:</td>
                            <td>
                                <%--{html_radios id="lookupmusic" name='lookupmusic' values=$yesno_ids output=$yesno_names selected=$fsite->lookupmusic separator='<br />'}--%>
                                <form:radiobuttons path="lookupMusic" id="lookupmusic" items="${yesNoMap}"/>
                                <div class="hint">Whether to attempt to lookup music information from Amazon when processing binaries.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="lookupgames">Lookup Games</label>:</td>
                            <td>
                                <%--{html_radios id="lookupgames" name='lookupgames' values=$yesno_ids output=$yesno_names selected=$fsite->lookupgames separator='<br />'}--%>
                                <form:radiobuttons path="lookupGames" id="lookupgames" items="${yesNoMap}"/>
                                <div class="hint">Whether to attempt to lookup game information from Amazon when processing binaries.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="compressedheaders">Use Compressed Headers</label>:</td>
                            <td>
                                <%--{html_radios class="$compress_headers_warning" id="compressedheaders" name='compressedheaders' values=$yesno_ids output=$yesno_names selected=$fsite->compressedheaders separator='<br />'}--%>
                                <form:radiobuttons path="compressedHeaders" id="compressedheaders" items="${yesNoMap}"/>
                                <div class="hint">Some servers allow headers to be sent over in a compressed format.  If enabled this will use much less bandwidth, but processing times may increase.</div>
                            </td>
                        </tr>


                        <tr>
                            <td><label for="maxmssgs">Max Messages</label>:</td>
                            <td>
                                <form:input class="small" id="maxmssgs" name="maxmssgs" path="maxMessages" value="${siteObject.maxMessages}" />
                                <div class="hint">The maximum number of messages to fetch at a time from the server.</div>
                            </td>
                        </tr>
                        <tr>
                            <td><label for="newgroupscanmethod">Where to start new groups</label>:</td>
                            <td>
                                <%--{html_radios id="newgroupscanmethod" name='newgroupscanmethod' values=$yesno_ids output=$newgroupscan_names selected=$fsite->newgroupscanmethod separator='<br />'}--%>
                                <form:radiobuttons path="newGroupsScanMethod" id="newgroupscanmethod" items="${newGroupsScanMethodMap}"/>
                                <form:input class="tiny" id="newgroupdaystoscan" name="newgroupdaystoscan" path="newGroupDaysToScan" value="${siteObject.newGroupDaysToScan}" /> Days  or
                                <form:input class="small" id="newgroupmsgstoscan" name="newgroupmsgstoscan" path="newGroupMsgsToScan" value="${siteObject.newGroupMsgsToScan}" /> Posts<br />
                                <div class="hint">Scan back X (posts/days) for each new group?  Can backfill to scan further.</div>
                            </td>
                        </tr>
                    </table>
                </fieldset>

                <fieldset>
                    <legend>User Settings</legend>
                    <table class="input">

                        <tr>
                            <td style="width:160px;"><label for="registerstatus">Registration Status</label>:</td>
                            <td>
                                <%--{html_radios id="registerstatus" name='registerstatus' values=$registerstatus_ids output=$registerstatus_names selected=$fsite->registerstatus separator='<br />'}--%>
                                <form:radiobuttons path="registerStatus" id="registerstatus" items="${registerStatusMap}"/>
                                <div class="hint">The status of registrations to the site.</div>
                            </td>
                        </tr>

                        <tr>
                            <td><label for="storeuserips">Store User Ip</label>:</td>
                            <td>
                                <%--{html_radios id="storeuserips" name='storeuserips' values=$yesno_ids output=$yesno_names selected=$fsite->storeuserips separator='<br />'}--%>
                                <form:radiobuttons path="storeUserIps" id="storeuserips" items="${yesNoMap}"/>
                                <div class="hint">Whether to store the users ip address when they signup or login.</div>
                            </td>
                        </tr>

                    </table>
                </fieldset>

                <input type="submit" value="Save Site Settings" />

            </form:form>

        <%--END PAGE CONTENT--%>
    </div>
    <!-- end #content -->

    <div id="sidebar">
        <ul>
            <li>
                <%@include file="admin_menu.jsp"%>
            </li>

        </ul>
    </div>
    <!-- end #sidebar -->

    <div style="clear: both;">&nbsp;</div>

</div>
<!-- end #page -->

<div id="searchfooter">
    <center>
    </center>
</div>

<div class="footer">
    <p>
        ${site.footer}
        <br/><br/><br/>Copyright &copy; ${year} ${site.title}. All rights reserved.
    </p>
</div>
<!-- end #footer -->

<c:if test="${!(empty site.googleAnalyticsAcc)}">
    <%@include file="../common/google_analytics.jsp"%>
</c:if>
</body>
</html>

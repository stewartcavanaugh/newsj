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
<%--@elvariable id="year" type="java.lang.integer"--%>
<%--
  User: Sten Martinez
  Date: 12/16/15
  Time: 2:38 PM
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="admin_common_head.jsp" %>
    <style type="text/css">
        .deprecated {
            text-decoration: line-through;
        }
    </style>
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

            <div id="donate">
                <h3>Support development</h3>
                <a href="https://www.paypal.me/StenMartinez"><img alt="" border="0" src="https://www.paypal.com/en_GB/i/btn/btn_donate_SM.gif"></a>
            </div>

            <p>
                Welcome to newznab. In this area you will be able to configure many aspects of your site.<br>
                If this is your first time here, you need to start the scripts which will fill newznab.
            </p>

            <ol style="list-style-type:decimal; line-height: 180%;">
                <li style="margin-bottom: 15px;">Configure your <a href="${pageContext.request.contextPath}/admin/site-edit">site options</a>. The defaults will probably work fine.</li>
                <li style="margin-bottom: 15px;">There a default list of usenet groups provided. To get started, you will need to <a href="${pageContext.request.contextPath}/admin/group-list">activate some groups</a>. <u>Do not</u> activate every group if its your first time setting this up. Try one or two first.
                    You can also <a href="${pageContext.request.contextPath}/admin/group-edit">add your own groups</a> manually.</li>
                <li style="margin-bottom: 15px;">Next you will want to get the latest headers. <b>This should be done from the command line</b>, using the linux or windows shell scripts found in /misc/update_scripts/cron_scripts (or batch_scripts for windows users), as it can take some time. If this is your first time dont bother with the cron/init scripts just open a command prompt...
                    <div style="padding-left:20px; font-family:courier;">cd newznab/misc/update_scripts<br/><span class="deprecated">php update_binaries.php</span></div>
                </li>
                <li style="margin-bottom: 15px;">After obtaining headers, the next step is to create releases. <b>This is best done from the command line</b> using the linux or windows shell scripts found in /misc/update_scripts/cron_scripts (or batch_scripts for windows users). If this is your first time dont bother with the cron/init scripts just open a command prompt...
                    <div style="padding-left:20px; font-family:courier;">cd newznab/misc/update_scripts<br/><span class="deprecated">php update_releases.php</span></div>
                </li>
                <li style="margin-bottom: 15px;">If you intend to keep using newznab, it is best to sign up for your own api keys from <a href="http://www.themoviedb.org/account/signup">tmdb</a> and <a href="http://aws.amazon.com/">amazon</a>.</li>
                <li>If you like newznab and intend to continue using it, please consider <a href="http://www.newznab.com/">sending a donation</a> to the team who write and maintain it.</li>
            </ol>

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

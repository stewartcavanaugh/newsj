<%@ page import="net.longfalcon.newsj.util.ArrayUtil" %>
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
  Date: 11/10/15
  Time: 4:25 PM
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<!--
Request URI: ${pageContext.request.requestURI}
Request URL: ${pageContext.request.requestURL}
<c:forEach items='${headerValues.get("referer")}' var="value">referer:     ${value}</c:forEach>
Servlet Context Path: ${pageContext.servletContext.contextPath}
-->

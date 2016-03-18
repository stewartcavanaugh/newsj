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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
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

            <form:form modelAttribute="menuItem" action="${pageContext.request.contextPath}/admin/menu-edit" method="POST">

                <table class="input">

                    <tr>
                        <td><label for="title">Title</label>:</td>
                        <td>
                            <form:hidden path="id" name="id" value="${menuItem.id}" />
                            <form:input path="title" id="title" class="long" name="title"  value="${text:escapeHtml(menuItem.title)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="href">Href</label>:</td>
                        <td>
                            <form:input path="href" id="href" class="long" name="href" value="${text:escapeHtml(menuItem.href)}" />
                            <div class="hint">Use full http:// path for external URLs, otherwise use no prefix.</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="tooltip">Tooltip</label>:</td>
                        <td>
                            <form:input path="tooltip" id="tooltip" class="long" name="tooltip" value="${text:escapeHtml(menuItem.tooltip)}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="menueval">Evaluate</label>:</td>
                        <td>
                            <form:input path="menuEval" id="menueval" class="long" name="menueval" value="${text:escapeHtml(menuItem.menuEval)}" />
                            <div class="hint">Smarty expression returning -1 if the menu item should be disabled.</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="ordinal">Ordinal</label>:</td>
                        <td>
                            <form:input path="ordinal" id="ordinal" class="short" name="ordinal" value="${menuItem.ordinal}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="role">Role</label>:</td>
                        <td>
                            <form:input path="role" id="role" class="short" name="role" value="${menuItem.role}" />
                        </td>
                    </tr>


                    <tr>
                        <td></td>
                        <td>
                            <input type="submit" value="Save" />
                        </td>
                    </tr>

                </table>

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
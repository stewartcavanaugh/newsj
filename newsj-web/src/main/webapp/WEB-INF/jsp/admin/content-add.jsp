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

            <form:form modelAttribute="content" action="${pageContext.request.contextPath}/admin/content-add?action=submit" method="POST">

                <table class="input">

                    <tr>
                        <td><label for="title">Title</label>:</td>
                        <td>
                            <form:hidden path="id" name="id" value="${content.id}" />
                            <form:input path="title" id="title" class="long" name="title" type="text" value="${content.title}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="url">Url</label>:</td>
                        <td>
                            <form:input path="url" id="url" class="long" name="url" type="text" value="${content.url}" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="body">Body</label>:</td>
                        <td>
                            <form:textarea path="body" id="body" name="body"/>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="metadescription">Meta Description</label>:</td>
                        <td>
                            <form:textarea path="metaDescription" id="metadescription" name="metadescription" />
                        </td>
                    </tr>

                    <tr>
                        <td><label for="metakeywords">Meta Keywords</label>:</td>
                        <td>
                            <form:textarea path="metaKeywords" id="metakeywords" name="metakeywords"/>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="contenttype">Content Type</label>:</td>
                        <td>
                            <%--{html_options id="contenttype" name='contenttype' options=$contenttypelist selected=$content->contenttype}--%>
                                <form:select path="contentType" id="contenttype">
                                    <form:options items="${contentTypeMap}"/>
                                </form:select>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="role">Visible To</label>:</td>
                        <td>
                            <%--{html_options id="role" name='role' options=$rolelist selected=$content->role}--%>
                                <form:select path="role" id="role">
                                    <form:options items="${roleMap}"/>
                                </form:select>
                            <div class="hint">Only appropriate for articles and useful links</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="showinmenu">Show In Menu</label>:</td>
                        <td>
                            <%--{html_radios id="showinmenu" name='showinmenu' values=$yesno_ids output=$yesno_names selected=$content->showinmenu separator='<br />'}--%>
                            <form:radiobuttons path="showInMenu" id="showinmenu" items="${yesNoMap}"/>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="status">Status</label>:</td>
                        <td>
                            <%--{html_radios id="status" name='status' values=$status_ids output=$status_names selected=$content->status separator='<br />'}--%>
                            <form:radiobuttons path="status" id="status" items="${statusMap}"/>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="ordinal">Ordinal</label>:</td>
                        <td>
                            <form:input path="ordinal" id="ordinal" name="ordinal" type="text" value="${content.ordinal}" />
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


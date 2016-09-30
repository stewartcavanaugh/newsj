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
<%@ taglib prefix="date" uri="http://java.longfalcon.net/jsp/jstl/date" %>
<%@ taglib prefix="text" uri="http://java.longfalcon.net/jsp/jstl/text" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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

            <%--<c:if test="${releaseRegex.id > 0 && releaseRegex.id < 100000}">&lt;%&ndash; && site.reqid != null&ndash;%&gt;
                <div class="error">Warning: Editing system regex, these changes will be overwritten next update releases.</div>
            </c:if>--%>

            <c:if test="${!text:isNull(error)}">
                <div class="error">${error}</div>    
            </c:if>

            <form:form modelAttribute="releaseRegex" action="${pageContext.request.contextPath}/admin/regex-edit" method="POST">

                <table class="input">


                    <tr>
                        <td>Group:</td>
                        <td>
                            <form:hidden path="id" name="id" value="${releaseRegex.id}" />
                            <form:input path="groupName" id="groupname" name="groupname" value="${text:escapeHtml(releaseRegex.groupName)}" />
                            <div class="hint">The full name of a valid newsgroup. Leave blank to apply regex to all newsgroups.</div>
                        </td>
                    </tr>

                    <tr>
                        <td>Regex:</td>
                        <td>
                            <form:textarea path="regex" id="regex" name="regex" />
                            <div class="hint">The regex to be applied.<br />
                                Regex requires at least 1 named capturing group in the form of (?P&lt;name&gt;) to work.<br />
                                If the subjects contains the number of parts (ie [1/10]) then it is wise to also use (?P&lt;parts&gt;) to match the parts.
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>Description:</td>
                        <td>
                            <form:textarea path="description" id="description" name="description" />
                            <div class="hint">A description for this regex</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="category">Category</label>:</td>
                        <td>
                            <form:select path="categoryId" id="category">
                                <form:option value="-1">--Please Select--</form:option>
                                <form:options items="${categoriesMap}"/>
                            </form:select>
                            <div class="hint">If this regex indicates the release category then supply it here. If left blank the standard method of determining the category will apply.</div>
                        </td>
                    </tr>

                    <tr>
                        <td>Ordinal:</td>
                        <td>
                            <form:input path="ordinal" id="ordinal" class="short" name="ordinal" value="${releaseRegex.ordinal}" />
                            <div class="hint">The zero-based order in which the regex should be applied.</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="status">Active</label>:</td>
                        <td>
                            <form:radiobuttons path="status" id="status" items="${statusMap}"/>
                            <div class="hint">Only active regexes are applied during the release process.</div>
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
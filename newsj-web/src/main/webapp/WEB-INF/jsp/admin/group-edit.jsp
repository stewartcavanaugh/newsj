<%--@elvariable id="group" type="net.longfalcon.newsj.model.Group"--%>
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

            <form:form modelAttribute="group" action="${pageContext.request.contextPath}/admin/group-edit" method="POST">

                <table class="input">

                    <tr>
                        <td>Name:</td>
                        <td>
                            <form:hidden path="id" name="id" value="${group.id}" />
                            <form:input id="name" class="long" name="name" type="text" path="name" value="${group.name}" />
                            <div class="hint">Changing the name to an invalid group will break things.</div>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="description">Description</label>:</td>
                        <td>
                            <form:textarea id="description" path="description" name="description"/>
                        </td>
                    </tr>

                    <tr>
                        <td><label for="backfill_target">Backfill Days</label></td>
                        <td>
                            <form:input class="small" id="backfill_target" name="backfill_target" type="text" path="backfillTarget" value="${group.backfillTarget}" />
                            <div class="hint">Number of days to attempt to backfill this group.  Adjust as necessary.</div>
                        </td>
                        </td>

                    </tr>
                        <td><label for="minfilestoformrelease">Minimum Files <br/>To Form Release</label></td>
                        <td>
                            <form:input class="small" id="minfilestoformrelease" name="minfilestoformrelease" type="text" path="minFilesToFormRelease" value="${group.minFilesToFormRelease}" />
                            <div class="hint">The minimum number of files to make a release. i.e. if set to two, then releases which only contain one file will not be created. If left blank, will use the site wide setting.</div>
                        </td>
                        </td>

                    <tr>
                        <td><label for="active">Active</label>:</td>
                        <td>
                            <%--{html_radios id="active" name='active' values=$yesno_ids output=$yesno_names selected=$group.active separator='<br />'}--%>
                            <form:radiobuttons path="active" id="active" items="${yesNoMap}"/>
                            <div class="hint">Inactive groups will not have headers downloaded for them.</div>
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

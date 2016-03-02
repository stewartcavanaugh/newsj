<%--
  ~ Copyright (c) 2015. Sten Martinez
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
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${status}</title>
    <style type="text/css">
        <!--
        body { margin: 0; padding: 0; background: #FFF url(${pageContext.request.contextPath}/resources/images/menu.gif) repeat-x left top; font-family: Calibri, Arial, Helvetica, sans-serif;	font-size: 12px; color: #515151; }
        h1, h2, h3 {	margin: 0;	font-weight: normal; color: #485459; }
        PRE, TT {border: 1px dotted #525D76}
        a { color: #4D9408; text-decoration: none;}
        a:hover { text-decoration: underline; }
        -->
    </style>
    <link rel="shortcut icon" type="image/ico" href="${pageContext.request.contextPath}/resources/images/favicon.ico"/>
</head>

<body>
<h1>${status}</h1>
<p>
    The request to<br/>
    (${request_uri})<br/>
    has failed because ${reason}<br/>
</p>



</body>
</html>
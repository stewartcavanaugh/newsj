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

<div id="logo">
    <a class="logolink" title="${site.title} Logo" href="${pageContext.request.contextPath}/"><img class="logoimg" alt="${site.title} Logo" src="${pageContext.request.contextPath}/resources/images/clearlogo.png" /></a>

    <c:if test="${site.menuPosition == 2}">
        <ul>{$main_menu}</ul>
    </c:if>

    <h1><a href="${pageContext.request.contextPath}/">${site.title}</a></h1>
    <p><em>${site.strapLine}</em></p>

</div>
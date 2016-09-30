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

<%--@elvariable id="categories" type="java.util.List<net.longfalcon.newsj.model.Category>"--%>
<%--@elvariable id="userData" type="net.longfalcon.view.UserData"--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="menucontainer">
    <div id="menulink">
        <ul>

            <c:forEach items="${categories}" var="parentcat">
                <c:choose>
                    <c:when test="${(parentcat.id == 1000) && (userData.consoleView == 1)}">
                        <li><a title="Browse ${parentcat.title}" href="${pageContext.request.contextPath}/console">${parentcat.title}</a>
                            <ul>
                                <c:forEach items="${parentcat.subCategories}" var="subcat">
                                    <li>
                                        <a title="Browse ${subcat.title}" href="${pageContext.request.contextPath}/console?t=${subcat.id}">${subcat.title}</a>
                                    </li>    
                                </c:forEach>
                            </ul>
                        </li>
                    </c:when>
                    <c:when test="${(parentcat.id == 2000) && (userData.movieView == 1)}">
                        <li><a title="Browse ${parentcat.title}" href="${pageContext.request.contextPath}/movies">${parentcat.title}</a>
                            <ul>
                                <c:forEach items="${parentcat.subCategories}" var="subcat">
                                <li><a title="Browse ${subcat.title}" href="${pageContext.request.contextPath}/movies?t=${subcat.id}">${subcat.title}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:when>
                    <c:when test="${(parentcat.id == 3000) && (userData.musicView == 1)}">
                        <li><a title="Browse ${parentcat.title}" href="${pageContext.request.contextPath}/music">${parentcat.title}</a>
                            <ul>
                                <c:forEach items="${parentcat.subCategories}" var="subcat">
                                    <c:choose>
                                        <c:when test="${subcat.id == 3030}">
                                            <li><a title="Browse ${subcat.title}" href="${pageContext.request.contextPath}/browse?t=${subcat.id}">${subcat.title}</a></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a title="Browse ${subcat.title}" href="${pageContext.request.contextPath}/music?t=${subcat.id}">${subcat.title}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li><a title="Browse ${parentcat.title}" href="${pageContext.request.contextPath}/browse?t=${parentcat.id}">${parentcat.title}</a>
                            <ul>
                                <c:forEach items="${parentcat.subCategories}" var="subcat">
                                <li><a title="Browse ${subcat.title}" href="${pageContext.request.contextPath}/browse?t=${subcat.id}">${subcat.title}</a></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <li><a title="Browse All" href="${pageContext.request.contextPath}/browse">All</a>
                <ul>
                    <li><a title="Browse Groups" href="${pageContext.request.contextPath}/browsegroup">Groups</a></li>
                </ul>
            </li>
        </ul>
    </div>

    <div id="menusearchlink">
        <form id="headsearch_form" action="${pageContext.request.contextPath}/search" method="get">

            <div class="gobutton" title="Submit search"><input id="headsearch_go" type="submit" value="" tabindex="3" /></div>

            <label style="display:none;" for="headcat">Search Category</label>
            <select id="headcat" name="t" tabindex="2">
                <option class="grouping" value="-1">All</option>
                <c:forEach items="${categories}" var="parentcat">
                    <option <c:if test="${headerMenuCat == parentcat.id}">selected="selected"</c:if> class="grouping" value="${parentcat.id}">${parentcat.title}</option>
                    <c:forEach items="${parentcat.subCategories}" var="subcat">
                        <option <c:if test="${headerMenuCat == subcat.id}">selected="selected"</c:if> value="${subcat.id}">&nbsp;&nbsp;${subcat.title}</option>
                    </c:forEach>
                </c:forEach>
            </select>

            <label style="display:none;" for="headsearch">Search Text</label>
            <input id="headsearch" name="search" value="${headerMenuSearch}" style="width:85px;" type="text" tabindex="1" />

        </form>
    </div>
</div>
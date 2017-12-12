<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: fatiz
  Date: 01.12.2017
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="header">
    <div class="brannd">
        <a href="/Blog">FBlog</a>
    </div>

    <div class="rightSide">

        <div class="search">
            <form action="search" method="get" id="searchForm">
                <p><input class="search1 search1-input" type="search" name="criterion"
                          <c:if test="${criterion != null}">value="${criterion}"</c:if>
                          placeholder="Type in hash-tags or words">
                    <button class="search1 search1-btn" type="submit" form="searchForm" value="Search">Search</button></p>
                <input name="src" type="hidden" value="${src}">
            </form>
        </div>

        <c:if test="${logined}">
            <div class="wrapper-drop-nav">
                <ul class="header-drop-nav">
                    <li id="without-margin-top"><a id="logname">${login}</a>
                        <ul>
                            <li><a href="/Blog/addPost">Add post</a></li>
                            <li><a href="/Blog/showPosts?par=5">My posts</a></li>
                            <li><a href="/Blog/logout">Logout</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </c:if>
        <c:if test="${!logined}">
            <div class="loggin">
                <a href="/Blog/authorization">Login</a>
            </div>
        </c:if>

    </div>
</div>
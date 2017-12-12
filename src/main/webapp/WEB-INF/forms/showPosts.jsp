<%--
  Created by IntelliJ IDEA.
  User: fatiz
  Date: 07.12.2017
  Time: 23:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Blog</title>

    <link rel="stylesheet" href="res/css/3.3.1.bootstrap.min.css">
    <link rel="stylesheet" href="res/css/custom.css">
</head>
<body>

<jsp:include page="header.jsp"/>

<c:if test="${logined}">
    <div class="block-sort-by">
        <div class="wrapper-drop-nav">
            <ul class="header-drop-nav">
                <li id="without-margin-top"><a id="logname">Sort by</a>
                    <ul>
                        <c:if test="${src.equals('blogindex')}">
                            <li><a <c:if test="${my}">class="activesort"</c:if> href="/Blog/showPosts?par=1">my posts</a></li>
                            <li><a <c:if test="${alien}">class="activesort"</c:if> href="/Blog/showPosts?par=2">alien posts</a></li>
                        </c:if>
                        <c:if test="${src.equals('myposts')}">
                            <li><a <c:if test="${spubl}">class="activesort"</c:if> href="/Blog/showPosts?par=3">published</a></li>
                            <li><a <c:if test="${sunpubl}">class="activesort"</c:if> href="/Blog/showPosts?par=4">unpublished</a></li>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</c:if>

<div class="container">
    <div class="row">
        <c:forEach items="${posts}" var="post">
            <div name="sd" class="col-md-3 col-sm-3 col-xs-12 image-main-section<c:if test="${!post.isPublished()}"> block-post</c:if> transitionCustom">
                <div class="row img-part">
                    <div class="img-padding">
                        <div class="col-md-12 col-sm-12 colxs-12 img-section">
                            <img src=${post.getLinkImage()}>
                        </div>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 image-title">
                        <h3>${post.getTitle()}</h3>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12 image-description">
                        <p class="textInside">${post.getText()}</p>
                    </div>
                    <div class="col-md-12 col-sm-12 col-xs-12">
                        <a href="viewPost?id=${post.getId()}" class="btn btn-success add-cart-btn">READ</a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<div style="width: fit-content;margin:  auto;">

    <c:if test="${pagesAmount > 1}">

        <div class="pagination"><a class="" href="/Blog/showPosts?par=6&page=1"> << </a></div>

        <c:if test="${pagesAmount <= 3}">
            <c:forEach var = "i" begin = "1" end = "${pagesAmount}">
                <c:if test="${i == currentPage}">
                    <div class="pagination"><a class="active" href="/Blog/showPosts?par=6&page=${i}"> ${i} </a></div>
                </c:if>
                <c:if test="${i != currentPage}">
                    <div class="pagination"><a class="" href="/Blog/showPosts?par=6&page=${i}"> ${i} </a></div>
                </c:if>
            </c:forEach>
        </c:if>

        <c:if test="${pagesAmount > 3}">
            <c:forEach var = "i" begin = "${currentPage - 1}" end = "${currentPage + 1}">
                <c:if test="${currentPage != 1}">
                    <c:if test="${i == currentPage}">
                        <div class="pagination"><a class="active" href="/Blog/showPosts?par=6&page=${i}"> ${i} </a></div>
                    </c:if>
                    <c:if test="${i != currentPage}">
                        <div class="pagination"><a class="" href="/Blog/showPosts?par=6&page=${i}"> ${i} </a></div>
                    </c:if>
                </c:if>
                <c:if test="${currentPage == 1}">
                    <c:if test="${currentPage == i + 1}">
                        <div class="pagination"><a class="active" href="/Blog/showPosts?par=6&page=${i+1}"> ${i+1} </a></div>
                    </c:if>
                    <c:if test="${currentPage != i + 1}">
                        <div class="pagination"><a class="" href="/Blog/showPosts?par=6&page=${i + 1}"> ${i+1} </a></div>
                    </c:if>
                </c:if>
            </c:forEach>
        </c:if>

        <div class="pagination"><a class="" href="/Blog/showPosts?par=6&page=${pagesAmount}"> >> </a></div>

    </c:if>

</div>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: fatiz
  Date: 29.11.2017
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${post.getTitle()}</title>
    <link rel="stylesheet" href="res/css/4-0-0.beta2bootstrap.min.css">
    <link rel="stylesheet" href="res/css/custom.css">
</head>
<body>

    <jsp:include page="header.jsp"/>

    <div class="container">

        <div class="row">
            <div class="col-sm text-center title">
                <h1>${post.getTitle()}</h1>
            </div>
        </div>

        <div class="row">
            <div class="imgWrap col-6">
                <div class="image col text-center">
                    <img class="img-fluid rounded mx-auto d-block"
                         src="${post.getLinkImage()}" alt="image">
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-6 text">
                <p align="justify">${post.getText()}</p>
            </div>
        </div>

        <div class="row">

            <div class="col">
            </div>

            <div class="meta col-6">
                <p>${post.getAuthor()}</p>
                <p class="right">${post.getDate()}</p>
                <div></div>
                <p id="categories">
                    <c:if test="${tags != null}">
                        <c:forEach items="${tags}" var="tag">
                            <a href="/Blog/search?criterion=%23${tag.getTag_title()}">#${tag.getTag_title()}</a>
                        </c:forEach>
                    </c:if>
                </p>
                <c:if test="${logined}">
                    <p class="right" style="margin-right: 0px;">
                        <a class="edit-btn-custom" href="/Blog/editPost?id=${post.getId()}">Edit post</a>
                    </p>
                </c:if>
            </div>

            <div class="col">
            </div>
        </div>

        <c:if test="${logined}">
            <div class="row">
                <div class="sendComment col-6">
                    <hr />

                    <form method="post" action="sendComment">
                        <input style="display: none;" name="id" value="${post.getId()}">
                        <label for="comment">Comment:</label>
                        <textarea class="form-control" rows="5" id="comment" name="comment"></textarea>
                        <input class="button btn btn-light" type="submit" value="Send">
                    </form>

                    <hr id="hr" />
                </div>
            </div>
        </c:if>

        <c:if test="${!logined}">
            <div class="row">
                <div class="sendComment col-6">
                    <p style="text-align: center;background: antiquewhite;border: 1px solid #A36046;border-radius: 5px;padding: 5px;">Log in to comment</p>
                </div>
            </div>
        </c:if>

        <div class="row">
            <div class="comments col-6">
            <c:forEach items="${comments}" var="comment">
                    <%--class = comment--%>
                    <div class="rounded">
                        <p align="justify">${comment.getText()}</p>
                        <p>Author: ${comment.getLogin_author()}</p>
                        <p align="right">${comment.getDate()}</p>
                    </div>

            </c:forEach>
            </div>
        </div>
    </div>

<div class="footer">

</div>
</body>
</html>
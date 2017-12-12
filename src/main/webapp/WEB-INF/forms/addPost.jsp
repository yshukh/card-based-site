<%--
  Created by IntelliJ IDEA.
  User: fatiz
  Date: 27.11.2017
  Time: 23:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Post</title>
    <link rel="stylesheet" href="res/css/3.3.1.bootstrap.min.css">
    <link rel="stylesheet" href="res/css/custom.css">
    <script src="res/js/jquery-3.2.1.min.js"></script>
    <script src="res/js/3.3.1.bootstrap.min.js"></script>
</head>
<body>

<jsp:include page="header.jsp"/>

<h1 style="margin-top: 90px;text-align: center;">
    <c:if test="${edit}">
        Edit Post
    </c:if>
    <c:if test="${!edit}">
        Add Post
    </c:if>
</h1>

<c:if test="${help}">
    <c:if test="${edit}"><c:if test="${success}">
        <div class="resultActionAccess">
            <h3>POST WAS UPDATED SUCCESSFULLY</h3>
        </div>
    </c:if></c:if>
    <c:if test="${edit}"><c:if test="${!success}">
        <div class="resultActionNegative">
            <h3>POST WAS NOT UPDATED</h3>
        </div>
    </c:if></c:if>
    <c:if test="${!edit}"><c:if test="${success}">
        <div class="resultActionAccess">
            <h3>POST WAS ADDED SUCCESSFULLY</h3>
        </div>
    </c:if></c:if>
    <c:if test="${!edit}"><c:if test="${!success}">
        <div class="resultActionNegative">
            <h3>POST WAS NOT ADDED</h3>
        </div>
    </c:if></c:if>
</c:if>

<div style="width: 85%;">
    <form class="form-horizontal" role="form" method="post" <c:if test="${!edit}">action="addPost"</c:if> <c:if test="${edit}">action="editPost"</c:if> enctype = "multipart/form-data">
        <input type="hidden" value="${post.getId()}" id="post_id" name="post_id">
        <div class="form-group">
            <label for="title" class="col-sm-2 control-label">Title</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="title" name="title" placeholder=""
                       <c:if test="${edit}">value="${post.getTitle()}"</c:if>>
            </div>
        </div>
        <div class="form-group">
            <label for="text" class="col-sm-2 control-label">Text</label>
            <div class="col-sm-10">
                <textarea class="form-control" rows="17" id="text" name="text"><c:if test="${edit}">${post.getText()}</c:if></textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="text" class="col-sm-2 control-label">Hash tags</label>
            <div class="col-sm-10">
                <textarea class="form-control" rows="1" id="hashtags" name="hashtags"><c:if test="${edit}"><c:forEach items="${tags}" var="tag">#${tag.getTag_title()}</c:forEach></c:if></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">Pick image</label>
            <div class="col-sm-10">
                <div class="input-group">
                    <label class="input-group-btn">
                        <span class="btn btn-primary">Browse&hellip;
                            <input name="file" type="file" style="display: none;" multiple>
                        </span>
                    </label>

                    <input type="text" class="form-control" readonly value="<c:if test="${edit}">${post.getLinkImage()}</c:if>">
                </div>
            </div>
            <script type="text/javascript">
                $(function() {
                    // We can attach the `fileselect` event to all file inputs on the page
                    $(document).on('change', ':file', function() {
                        var input = $(this),
                                numFiles = input.get(0).files ? input.get(0).files.length : 1,
                                label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
                        input.trigger('fileselect', [numFiles, label]);
                    });

                    // We can watch for our custom `fileselect` event like this
                    $(document).ready( function() {
                        $(':file').on('fileselect', function(event, numFiles, label) {

                            var input = $(this).parents('.input-group').find(':text'),
                                    log = numFiles > 1 ? numFiles + ' files selected' : label;

                            if( input.length ) {
                                input.val(log);
                            } else {
                                if( log ) alert(log);
                            }

                        });
                    });
                });
            </script>
        </div>
        <div class="form-group">
            <label for="isvisible" class="col-sm-2 control-label">Make visible</label>
            <div class="col-sm-10 checkbox chkbox">
                <input type="checkbox" name="isvisible" id="isvisible"
                    <c:if test="${edit}">
                    <c:if test="${post.isPublished()}">
                           checked
                    </c:if>
                    <c:if test="${!post.isPublished()}">
                           unchecked
                    </c:if>
                    </c:if>
                    <c:if test="${!edit}">
                           checked
                    </c:if>
                >
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-10 col-sm-offset-2">
                <input id="submit" name="submit" type="submit" value="Send" class="btn btn-primary">
            </div>
        </div>
    </form>
</div>
</body>
</html>
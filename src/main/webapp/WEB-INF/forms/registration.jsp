<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: fatiz
  Date: 07.12.2017
  Time: 13:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign up</title>
    <link rel="stylesheet" href="res/css/3-3-0.bootstrap.min.css">
    <link rel="stylesheet" href="res/css/custom.css">
    <script src="res/js/jquery-3.2.1.min.js"></script>
    <script src="res/js/authorization.js"></script>
    <script src="res/js/3-3-7.bootstrap.min.js"></script>

</head>
<body>

<jsp:include page="header.jsp"/>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4 text-center">
            <div class="search-box">
                <div class="caption">
                    <h3>Registration</h3>
                </div>

                <c:if test="${unsuccess}">
                    <h4>This login already exists. Please choose another one</h4>
                </c:if>

                <form action="registration" method="post" class="loginForm">
                    <div class="input-group">
                        <input name="login" type="text" id="name" class="form-control" placeholder="Login">
                        <input name="password" type="password" id="paw" class="form-control" placeholder="Password">
                        <input type="submit" id="submit" class="form-control" value="Sign up">
                    </div>
                </form>
            </div>
        </div>
        <div class="col-md-4">
            <div class="aro-pswd_info">
                <div id="pswd_info">
                    <h4>Password must be requirements</h4>
                    <ul>
                        <li id="letter" class="invalid">At least <strong>one letter</strong></li>
                        <li id="capital" class="invalid">At least <strong>one capital letter</strong></li>
                        <li id="number" class="invalid">At least <strong>one number</strong></li>
                        <li id="length" class="invalid">Be at least <strong>8 characters</strong></li>
                        <li id="space" class="invalid">be<strong> use [~,!,@,#,$,%,^,&,*,-,=,.,;,']</strong></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
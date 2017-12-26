<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Log in with your account</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="sumit kumar">
    <title>Trial</title>
    <script src="https://use.fontawesome.com/07b0ce5d10.js"></script>

</head>

<body>
<nav class="top-bar">
    <div class="container">
        <div class="row">
            <div class="col-sm-4 hidden-xs text-center">
            <span class="nav-text">
                <a href="/"> <img src="https://icon-icons.com/icons2/865/PNG/512/Citycons_plane_icon-icons.com_67921.png" height="50"/> </a> </span>
            </div>


            <ul class="nav navbar-nav navbar-right">


            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <br>
    <br>
    <br>
    <h1 class="text-center form-signin-heading">Tour Agent NeverLand</h1>
    <br>

    <br>
    <br>
    <br>
    <br>
    <form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="form-heading text-center">Log in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="email" type="text" class="form-control" placeholder="Email"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
            <h4 class="text-center"><a href="${contextPath}/register">Create an account</a></h4>
        </div>

    </form>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<%@include file="../footer.jsp" %>

</body>
</html>
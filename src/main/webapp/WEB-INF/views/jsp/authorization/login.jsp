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
    <title>Login</title>
    <script src="https://use.fontawesome.com/07b0ce5d10.js"></script>

</head>

<body>
<%--<nav class="top-bar">
    <div class="container">
        <div class="row" style="margin-right: auto; margin-left: auto;">
            <div class="center-block">
            <span class="nav-text">
                <a href="/"> <img src="https://icon-icons.com/icons2/865/PNG/512/Citycons_plane_icon-icons.com_67921.png" height="50"/> </a> </span>
            </div>
        </div>
    </div>
</nav>--%>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/"><img src="https://icon-icons.com/icons2/1310/PNG/512/map_86329.png" height="36px"/></a>

        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${pageContext.request.contextPath}/register"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
            </ul>
        </div>
    </div>
</nav>
<nav class="navbar-left">
   <img src="https://static.life.ru/posts/2016/12/952847/gr/north/8dac341646c14eed4f41233514e97b4e__1440x.jpg" height="600px" width="500px"/>
</nav>

<nav class="navbar-right">
    <img src="https://www.gismeteo.ru/static/news/img/src/23433/8e806b98.jpg" height="600px" width="500px"/>
</nav>


<div class="container">
    <br>
    <br>
    <br>
    <h1 class="text-center form-signin-heading">Tour Agency NeverLand</h1>
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
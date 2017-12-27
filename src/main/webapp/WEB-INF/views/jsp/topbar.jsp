<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="sumit kumar">
    <title>Trial</title>
    <link href="css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="css/font-awesome.css" rel="stylesheet" type="text/css">
    <link href="css/style.css" rel="stylesheet" type="text/css">
    <script src="https://use.fontawesome.com/07b0ce5d10.js"></script>
</head>

<body>

</body>
</html>
--%>


<%--

<nav class="top-bar">
    <div class="container">
        <div class="row">
            <div class="col-sm-4 hidden-xs">
            <span class="nav-text">
                <a href="/"> <img src="https://icon-icons.com/icons2/865/PNG/512/Citycons_plane_icon-icons.com_67921.png" height="50"/> </a> </span>
            </div>


            <ul class="nav navbar-nav navbar-right">

                <li>
                    <a class="" href="${pageContext.request.contextPath}/logout"><i class="fa fa-sign-out" aria-hidden="true"></i>Log Out</a>

                </li>
            </ul>
        </div>
    </div>
</nav>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta charset="utf-8">
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="sumit kumar">
    <script src="https://use.fontawesome.com/07b0ce5d10.js"></script>
</head>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/"><img src="https://icon-icons.com/icons2/1310/PNG/512/map_86329.png" height="35px"/></a>

        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="?lang=en"><span class="glyphicon glyphicon-user"></span>English</a></li>
                <li><a href="?lang=ru"><span class="glyphicon glyphicon-user"></span>Русский</a></li>
                <li><a href="/logout"><span class="glyphicon glyphicon-user"></span><spring:message code="logout"/> </a></li>
            </ul>
        </div>
    </div>
</nav>
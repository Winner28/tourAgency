<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="author" content="sumit kumar">
    <script src="https://use.fontawesome.com/07b0ce5d10.js"></script>

</head>

<body>
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
                <li><a href="/login"><span class="glyphicon glyphicon-user"></span>Log in</a></li>
            </ul>
        </div>
    </div>
</nav>
<nav class="navbar-left">
    <img src="https://icon-icons.com/icons2/951/PNG/256/airplane-outline-pointing-left_icon-icons.com_74184.png"/>
</nav>

<nav class="navbar-right">
    <img src="https://icon-icons.com/icons2/951/PNG/256/road-perspective-of-curves_icon-icons.com_74166.png"/>
</nav>


<h1>${errorMessage}</h1>
<div class="container">

    <form:form method="POST" action="${contextPath}/register" class="form-signin" >
        <h2 class="text-center">Registration</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <input name="firstName" type="text" class="form-control" placeholder="First Name"
                   autofocus="true"/>
            <input name="lastName" type="text" class="form-control" placeholder="Last Name"/>
            <input name="email" type="text" class="form-control" placeholder="Email"/>
            <input name="passwordHash" type="password" class="form-control" placeholder="Password"/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
            <h4 class="text-center"><a href="${contextPath}/login">Already have account? Log In</a></h4>
        </div>

    </form:form>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
<%@include file="../footer.jsp" %>

</body>
</html>
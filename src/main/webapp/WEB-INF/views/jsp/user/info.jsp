<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: vladey
  Date: 21.12.17
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Info</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
</head>
<body>
<BR>
<h1 class="text-center" class="alert-success">${message}</h1>
<form:form method = "GET" action = "/" cssClass="form-signin">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Get back to Home Page</button>
</form:form>
</body>
</html>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: vladey
  Date: 21.12.17
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
        <title>Log in with your account</title>
        <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    </head>
</head>
<body>
<h1>Hello, ${user.toString()}!</h1>
<form:form method = "POST" action = "/logout" modelAttribute="user">

    <div class="form-group ${error != null ? 'has-error' : ''}">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Log Out</button>
    </div>
</form:form>
</form>
</body>
</html>

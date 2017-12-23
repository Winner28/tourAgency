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
        <title>Profile</title>
        <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
        <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    </head>
</head>
<body>
<h1 class="text-center" class="glyphicon-text-color">Profile Page</h1>
<br>
<div class="container">
    <h2 class="text-center"> First Name: ${user.firstName}</h2>
    <h2 class="text-center"> Last Name: ${user.lastName}</h2>
    <h2 class="text-center"> Email: ${user.email}</h2>
</div>
<br>


<c:if test="${admin.equals('admin')}">
    <h2 class="text-center glyphicon-text-color">Hello, mr.Admin</h2>

    <form:form method = "GET" action = "/users/create" cssClass="form-signin">
        <button class="btn btn-lg btn-success btn-block" type="submit">Create user</button>
    </form:form>

    <form:form method = "GET" action = "/users/update" cssClass="form-signin">
        <input type="text" name="id" placeholder="Type an id" class="form-control text-center">
        <button class="btn btn-lg btn-success btn-block" type="submit">Update User</button>
    </form:form>

    <form:form method = "POST" action = "/users/delete" cssClass="form-signin">
        <input type="text" name="id" placeholder="Type an id" class="form-control text-center">
        <button class="btn btn-lg btn-success btn-block" type="submit">Delete User</button>
    </form:form>


    <form:form method = "GET" action = "/users/all" cssClass="form-signin">
        <button class="btn btn-lg btn-success btn-block" type="submit">Show all users</button>
    </form:form>


    <form:form method = "GET" action = "/users/permissions/create" cssClass="form-signin">
        <button class="btn btn-lg btn-success btn-block" type="submit">Create permission</button>
    </form:form>


    <form:form method = "GET" action = "/users/permissions/update" cssClass="form-signin">
        <button class="btn btn-lg btn-success btn-block" type="submit">Update permission</button>
    </form:form>

    <form:form method = "POST" action = "/users/delete" cssClass="form-signin">
        <input type="text" name="id" placeholder="Type an user id" class="form-control text-center">
        <button class="btn btn-lg btn-success btn-block" type="submit">Get permission</button>
    </form:form>

</c:if>

<c:if test="${admin.equals('notAdmin')}">
<form:form method = "GET" action = "/orders/all" cssClass="form-signin">
    <input type="hidden" value="${user.id}">
    <button class="btn btn-lg btn-info btn-block" type="submit">My orders</button>
</form:form>
</c:if>
<br>
<form:form method = "POST" action = "/logout" cssClass="form-signin" cssStyle="border-bottom-width: medium">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Log Out</button>
</form:form>

</body>
</html>

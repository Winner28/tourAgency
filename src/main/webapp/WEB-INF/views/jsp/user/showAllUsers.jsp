<%--
  Created by IntelliJ IDEA.
  User: vladey
  Date: 21.12.17
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Users List</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>
<body>
<%@include file="../topbar.jsp" %>
<div class="wrapper">
    <div class="content">
<h1 class="text-center"><b>Users List</b></h1> <br>


<table class="table table-hover">
    <thead>
            <tr>
                <td class="text-center">id</td>
                <td class="text-center">First Name</td>
                <td class="text-center">Last Name</td>
                <td class="text-center">Email</td>
                <td class="text-center">Update</td>
                <td class="text-center">Delete</td>
            </tr>
    </thead>
    <tbody>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td class="text-center">${user.id}</td>
                    <td class="text-center">${user.firstName}</td>
                    <td class="text-center">${user.lastName}</td>
                    <td class="text-center">${user.email}</td>
                    <td class="text-center">
                        <form:form method = "GET" action = "/users/update">
                            <input type="hidden" name="id" value="${user.id}">
                            <button class="btn btn-lg btn-success btn-block btn-md text-center" type="submit">Update User</button>
                        </form:form>
                    </td>
                    <td class="text-center">
                        <form:form method = "POST" action = "/users/delete">
                            <input type="hidden" name="id" value="${user.id}">
                            <button class="btn btn-lg btn-success btn-block btn-md text-center" type="submit">Delete User</button>
                        </form:form>
                    </td>
                </tr>
            </c:forEach>
    </tbody>
        </table>
<form:form method = "GET" action = "/" cssClass="form-signin">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Get back to Home Page</button>
</form:form>
    </div>
</div>
<br>
<br>
<%@include file="../footer.jsp" %>

</body>
</html>

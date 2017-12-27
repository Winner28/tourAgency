<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<%@include file="topbar.jsp" %>

<nav class="navbar-left">
    <img src="https://icon-icons.com/icons2/951/PNG/256/road-perspective-of-curves_icon-icons.com_74166.png"/>
</nav>
<nav class="navbar-right">
    <img src="https://icon-icons.com/icons2/951/PNG/256/airplane-outline-pointing-left_icon-icons.com_74184.png"/>
</nav>
<h1 class="text-center" class="glyphicon-text-color"><spring:message code="profile.page"/></h1>
<br>
<div class="container">
    <h2 class="text-center"> <spring:message code="firstName"/>: ${user.firstName}</h2>
    <h2 class="text-center"> <spring:message code="lastName"/>: ${user.lastName}</h2>
    <h2 class="text-center"> <spring:message code="role"/>: ${userType}</h2>
    <h2 class="text-center"> <spring:message code="email"/>: ${user.email}</h2>
</div>
<br>

<h2 class="text-center"><b>Hello, mr.${user.lastName}</b></h2>

<c:if test="${userType.equals('admin')}">


<table class="table table-condensed">
    <tbody>
    <tr>
        <td>
            <form:form method = "GET" action = "/users/create" cssClass="form-signin">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="user.create"/></button>
            </form:form>
        </td>
        <td>
            <form:form method = "GET" action = "/users/permissions/create" cssClass="form-signin">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="permission.create"/></button>
            </form:form>
        </td>

    </tr>
    <tr>
        <td>
            <form:form method = "POST" action = "/users/delete" cssClass="form-signin">
                <input type="text" name="id" placeholder="<spring:message code="type.id"/>" class="form-control text-center">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="user.delete"/></button>
            </form:form>
        </td>
        <td>
            <form:form method = "GET" action = "/users/permissions/all" cssClass="form-signin">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="permission.all"/></button>
            </form:form>
        </td>

    </tr>
    <tr>
        <td>
            <form:form method = "GET" action = "/users/all" cssClass="form-signin">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="user.all"/></button>
            </form:form>
        </td>
        <td>
            <form:form method = "GET" action = "/users/permissions/update" cssClass="form-signin">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="permission.update"/></button>
            </form:form>
        </td>

    </tr>
    <tr>
        <td>
            <form:form method = "GET" action = "/users/update" cssClass="form-signin">
                <input type="text" name="id" placeholder="<spring:message code="type.id"/>" class="form-control text-center">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="user.update"/></button>
            </form:form>
        </td>
        <td>
            <form:form method = "POST" action = "/users/permissions/get" cssClass="form-signin">
                <input type="text" name="id" placeholder="<spring:message code="type.id"/>" class="form-control text-center">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="permission.get"/></button>
            </form:form>
        </td>

    </tr>
    <tr>
        <td>
            <form:form method = "GET" action = "/users/get" cssClass="form-signin">
                <input type="text" name="id" placeholder="<spring:message code="type.id"/>" class="form-control text-center">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="user.id"/></button>
            </form:form>
        </td>

        <td>
            <form:form method = "POST" action = "/users/permissions/name" cssClass="form-signin">
                <input type="text" name="id" placeholder="<spring:message code="type.permission.name"/>" class="form-control text-center">
                <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="permission.name"/></button>
            </form:form>
        </td>

    </tr>
    </tbody>
</table>
</c:if>


<c:if test="${userType.equals('agent')}">

    <form:form method = "GET" action = "/tours/create" cssClass="form-signin">
        <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="tours.create"/></button>
    </form:form>

    <form:form method = "GET" action = "/tours/agentTours" cssClass="form-signin">
        <input type="hidden" value="${user.id}">
        <button class="btn btn-lg btn-info btn-block" type="submit"><spring:message code="tours.mytours"/></button>
    </form:form>

    <form:form method = "GET" action = "/orders/agentOrders" cssClass="form-signin">
        <button class="btn btn-lg btn-success btn-block" type="submit"><spring:message code="tours.showorders"/></button>
    </form:form>

</c:if>


<c:if test="${userType.equals('client')}">
    <form:form method="GET" action="/orders/all" cssClass="form-signin">
        <input type="hidden" value="${user.id}">
        <button class="btn btn-lg btn-info btn-block" type="submit"><spring:message code="client.myorders"/></button>
    </form:form>
    <form:form method="GET" action="/tours/clientTours" cssClass="form-signin">
        <input type="hidden" value="${user.id}">
        <button class="btn btn-lg btn-info btn-block" type="submit"><spring:message code="client.showtours"/></button>
    </form:form>
</c:if>




<form:form method = "GET" action = "/logout" cssClass="form-signin" cssStyle="border-bottom-width: medium">
    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="logout"/></button>
</form:form>
<br>
<br>
<br>
<%@include file="footer.jsp" %>
</body>
</html>

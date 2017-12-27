<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8">

    <title>Create</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>

<body>
<%@include file="../topbar.jsp" %>

<h1 class="text-center"><spring:message code="user.create"/></h1>
<form:form method = "POST" action = "/users/create" modelAttribute="user" cssClass="form-signin">
    <div class="form-group">

        <label for="firstName"><spring:message code="firstName"/></label>
        <form:input path = "firstName" cssClass="form-control"/>
    </div>
    <div class="form-group">

        <label for="lastName"><spring:message code="lastName"/>:</label>
        <form:input path = "lastName" cssClass="form-control"/>
    </div>
    <div class="form-group">

        <label for="email"><spring:message code="email"/>:</label>
        <form:input path = "email" cssClass="form-control"/>
    </div>
    <div class="form-group">

        <label for="passwordHash"><spring:message code="password"/>:</label>
        <form:password path = "passwordHash" cssClass="form-control"/>
    </div>

    <div class="container">
        <p>Choose user role:</p>
        <div class="radio">
            <label><input type="radio" name="permission" value="2"><spring:message code="agent"/></label>
        </div>
        <div class="radio">
            <label><input type="radio" name="permission" value="3"><spring:message code="user"/></label>
        </div>

    </div>


    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="user.create"/></button>
</form:form>
<%@include file="../footer.jsp" %>

</body>

</html>
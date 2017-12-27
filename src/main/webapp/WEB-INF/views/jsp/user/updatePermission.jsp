<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <title>Update User Page</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>
<body>
<%@include file="../topbar.jsp" %>

<h1 class="text-center"><spring:message code="update.permission.label"/></h1>
<br>

<form:form method = "POST" action = "/users/permissions/update" modelAttribute="permission" cssClass="form-signin">

    <div class="form-group">

        <label for="userId"><spring:message code="update.permission.user"/></label>
        <form:input path = "userId" cssClass="form-control"/>
    </div>
    <div class="form-group">


        <label for="permissionNameId"><spring:message code="update.permission.id"/></label>
        <form:input path = "permissionNameId" cssClass="form-control"/>
    </div>

    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="update.permission"/></button>
</form:form>

<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<%@include file="../footer.jsp" %>

</body>
</html>
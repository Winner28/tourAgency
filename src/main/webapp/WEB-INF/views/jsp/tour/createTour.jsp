<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
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
<h1 class="text-center"><spring:message code="tour.create"/></h1>
<form:form method = "POST" action = "/tours/create" modelAttribute="tour" cssClass="form-signin">
    <div class="form-group">

        <label for="tourName"><spring:message code="tour.create"/>: </label>
        <form:input path = "tourName" cssClass="form-control"/>

        <label for="duration"><spring:message code="tour.duration"/>: </label>
        <form:input path = "duration" cssClass="form-control"/>
    </div>
    <div class="form-group">

        <label for="price"><spring:message code="tours.price"/>: </label>
        <form:input path = "price" cssClass="form-control"/>
    </div>
    <div class="form-group">

        <label for="tourTypesId"><spring:message code="tour.type"/>: </label>
        <form:select path = "tourTypesId" cssClass="form-control">
            <form:option value="0" label="--- Select ---"/>
            <form:option value="1">sightseeing</form:option>
            <form:option value="2">transfer</form:option>
            <form:option value="3">shopping</form:option>
            <form:option value="4">excursion</form:option>
        </form:select>
    </div>
    <div class="form-group">

    <div class="container">
        <p>Tour options:</p>
        <div class="radio">
            <label><input type="radio" name="hot" value="1">Hot</label>
            <label><input type="radio" name="hot" value="0">Casual</label>
        </div>
        <div class="radio">
            <label><input type="radio" name="active" value="1">Active</label>
            <label><input type="radio" name="active" value="0">Archive</label>
        </div>

    </div>


    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="tour.create"/></button>
</form:form>

<form:form method = "GET" action = "/" cssClass="form-signin">
    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="button.back"/></button>
</form:form>
<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<%@include file="../footer.jsp" %>

</body>
</html>
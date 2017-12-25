<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Tour Page</title>

    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>
<body>
<%@include file="../topbar.jsp" %>
<h1 class="text-center glyphicon-text-color">${message}</h1>
<h2 class="text-center">Tour information</h2>
<br>
<div class="container">
    <h3 class="text-center"> id: ${tour.id}</h3>
    <h3 class="text-center"> Tour Name: ${tour.tourName}</h3>
    <h3 class="text-center"> Active:
        <c:if test="${tour.active == true}">
            Yes
            </c:if>
        <c:if test="${tour.active == false}">
            No
        </c:if></h3>
    <h3 class="text-center"> Hot:
        <c:if test="${tour.active == true}">
        Yes
    </c:if>
        <c:if test="${tour.active==false}">
            No
        </c:if></h3>
    <h3 class="text-center"> Duration: ${tour.duration}</h3>
    <h3 class="text-center"> Price: ${tour.price}</h3>
    <h3 class="text-center"> Tour Type Id: ${tour.tourTypesId}</h3>
    <h3 class="text-center"> Agent Id: ${tour.agentId}</h3>
</div>

<form:form method = "GET" action = "/" cssClass="form-signin">
    <button class="btn btn-lg btn-primary btn-block" type="submit">Get back to Home Page</button>
</form:form>
<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<%@include file="../footer.jsp" %>

</body>
</html>
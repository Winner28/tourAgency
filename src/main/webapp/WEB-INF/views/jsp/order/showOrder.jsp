<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Order Page</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <link href="${bootstrapCss}" rel="stylesheet"/>
    <link href="${coreCss}" rel="stylesheet"/>
</head>
<body>

<%@include file="../topbar.jsp" %>
<h1 class="text-info">${message.toUpperCase()}</h1>
<h1><spring:message code="show.order.info"/></h1>
<br>
<h3>Order number<spring:message code="show.order.number"/>: ${order.id}</h3>
<h3>Order date<spring:message code="show.order.date"/>: ${order.date}</h3>
<h3>Order active<spring:message code="show.order.active"/>:
    <c:if test="${order.active}">
        Yes
    </c:if>
    <c:if test="${!order.active}">
        No
    </c:if></h3>
<h3>Order tour name<spring:message code="shiw.order.tour.name"/>: ${tourService.getTourById(order.tourId).tourName}</h3>


<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<form:form method = "GET" action = "/" cssClass="form-signin">
    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="button.back"/></button>
</form:form>
<br>
<%@include file="../footer.jsp" %>

</body>
</html>
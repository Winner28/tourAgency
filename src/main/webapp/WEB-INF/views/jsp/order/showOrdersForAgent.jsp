<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Orders List</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <link href="${bootstrapCss}" rel="stylesheet"/>
    <link href="${coreCss}" rel="stylesheet"/>
</head>
<body>
<%@include file="../topbar.jsp" %>
<h1 class="text-center"><b><spring:message code="orders.list"/> </b></h1> <br>

<ul>
    <c:if test="${empty orderList}">
        <h2 class="text-info">
            <spring:message code="orders.no"/>
        </h2>
    </c:if>


    <table class="table table-hover">
        <thead>
        <tr>
            <td><spring:message code="order.id "/></td>
            <td><spring:message code="order.date"/></td>
            <td><spring:message code="order.active"/></td>
            <td><spring:message code="order.tour.name"/></td>
            <td><spring:message code="order.user.email"/></td>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${orderList}" var="order">
            <tr>
                <td>${order.id}</td>
                <td>${order.date}</td>
                <td>${order.active}</td>
                <td>${tourService.getTourById(order.tourId).tourName}</td>
                <td>${userService.getUserById(order.userId).email}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</ul>
<form:form method = "GET" action = "/" cssClass="form-signin">
    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="button.back"/></button>
</form:form>
<br>
<%@include file="../footer.jsp" %>

</body>
</html>


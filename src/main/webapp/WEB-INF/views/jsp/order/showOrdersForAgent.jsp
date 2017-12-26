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
<h1 class="text-center"><b>List of Orders: </b></h1> <br>

<ul>
    <c:if test="${empty orderList}">
        <h2 class="text-info">
            There are no orders!
        </h2>
    </c:if>

    <table class="table table-hover">
        <thead>
        <tr>
            <td>Order ID</td>
            <td>Date</td>
            <td>Active</td>
            <td>Tour name</td>
            <td>User email</td>
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
<%@include file="../footer.jsp" %>

</body>
</html>


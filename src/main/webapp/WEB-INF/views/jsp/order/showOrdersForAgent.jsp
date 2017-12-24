<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Orders List</title>
</head>
<body>
<h1><b>List of Orders: </b></h1> <br>

<ul>
    <c:if test="${empty orderList}">
        <h4>
            There are no orders!
        </h4>
    </c:if>

    <table border="1" cellspacing="0" cellpadding="2">
        <tr>
            <td>Order ID</td>
            <td>Date</td>
            <td>Active</td>
            <td>Tour ID</td>
            <td>User ID</td>
        </tr>

        <c:forEach items="${orderList}" var="order">
            <tr>
                    <td>${order.id}</td>
                    <td>${order.date}</td>
                    <td>${order.active}</td>
                    <td>${order.tourId}</td>
                    <td>${order.userId}</td>
            </tr>
        </c:forEach>
    </table>
</ul>

</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Orders List</title>
</head>
<body>
<h1><b>List of Orders: </b></h1> <br>

<ul>
    <c:if test="${empty orderList}">
        <h4>You have no orders!<h4>
    </c:if>
    <c:forEach var="order" items="${orderList}">
        <h4><li>${order.toString()}</li></h4>
    </c:forEach>
</ul>

</body>
</html>


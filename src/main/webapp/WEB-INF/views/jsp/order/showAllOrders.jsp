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
            You have no orders!
        </h4>
    </c:if>


    <h2> Your discount is ${discount}%</h2> <br>
    <table border="1" cellspacing="0" cellpadding="2">
        <tr>
            <td>Order ID</td>
            <td>Date</td>
            <td>Active</td>
            <td>Tour ID</td>
            <td>User ID</td>
            <td>Action</td>
        </tr>

        <c:forEach items="${orderList}" var="order">
            <tr>
                <c:if test="${order.active}">
                    <td>${order.id}</td>
                    <td>${order.date}</td>
                    <td>${order.active}</td>
                    <td>${order.tourId}</td>
                    <td>${order.userId}</td>
                    <td>
                        <form:form method="POST" action="/orders/update" cssClass="form-signin"
                                   cssStyle="border-bottom-width: medium">
                            <input type="hidden" name="id" value="${order.id}">
                            <input type="hidden" name="date" value="${order.date}">
                            <input type="hidden" name="active" value="${false}">
                            <input type="hidden" name="tourId" value="${order.tourId}">
                            <input type="hidden" name="userId" value="${order.userId}">
                            <button class="btn btn-lg btn-primary btn-block" type="submit">Cancel</button>
                        </form:form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>
</ul>
<%@include file="../footer.jsp" %>

</body>
</html>


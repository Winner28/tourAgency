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
<h1 class="text-center"><b>Orders List:</b></h1> <br>

<ul>
    <c:if test="${empty orderList}">
        <h4>
            You have no orders!
        </h4>
    </c:if>


    <h2 class="text-info"> Your discount is ${discount}%</h2> <br>
    <table class="table table-hover">
        <thead>
        <tr>
            <td>Order ID</td>
            <td>Date</td>
            <td>Active</td>
            <td>Tour Name</td>
            <td>Action</td>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${orderList}" var="order">
            <tr>
                <c:if test="${order.active}">
                    <td>${order.id}</td>
                    <td>${order.date}</td>
                    <td>
                        <c:if test="${order.active==true}">
                            Yes
                        </c:if>
                        <c:if test="${order.active==false}">
                            No
                        </c:if>
                    </td>
                    <td>${tourService.getTourById(order.tourId).tourName}</td>
                    <td>
                        <form:form method="POST" action="/orders/update" cssClass="form-signin"
                                   cssStyle="border-bottom-width: medium">
                            <input type="hidden" name="id" value="${order.id}">
                            <input type="hidden" name="date" value="${order.date}">
                            <input type="hidden" name="active" value="${false}">
                            <input type="hidden" name="tourId" value="${order.tourId}">
                            <input type="hidden" name="userId" value="${order.userId}">
                            <button class="btn btn-lg btn-primary btn-block " type="submit">Cancel</button>
                        </form:form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</ul>
<%@include file="../footer.jsp" %>

</body>
</html>


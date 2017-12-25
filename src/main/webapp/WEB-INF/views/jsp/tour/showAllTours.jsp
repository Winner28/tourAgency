<%--
  Created by IntelliJ IDEA.
  User: vladey
  Date: 21.12.17
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Tours List</title>
</head>
<body>
<h1><b>List of Tours: </b></h1> <br>

<ul>
    <%--<c:forEach var="tour" items="${tourList}">--%>
       <%--<h4><li>${tour.toString()}</li></h4>--%>
    <%--</c:forEach>--%>
    <table border="1" cellspacing="0" cellpadding="2">
        <tr>
            <td>TourID</td>
            <td>Hot</td>
            <td>Price</td>
            <td>Duration</td>
            <td>Tour Type</td>
            <td>Action</td>
        </tr>

        <c:forEach items="${tourList}" var="tour">
            <tr>
                <td>${tour.id}</td>
                <td>${tour.hot}</td>
                <td>${tour.price}</td>
                <td>${tour.duration}</td>
                <td>${tour.tourTypesId}</td>
                <td>
                    <form:form method = "POST" action = "/orders/create"  cssClass="form-signin" cssStyle="border-bottom-width: medium">
                        <input type="hidden" name="tourId" value="${tour.id}">
                        <button class="btn btn-lg btn-primary btn-block" type="submit">Purchase</button>
                    </form:form></td>
            </tr>
        </c:forEach>
    </table>
</ul>

</body>
</html>

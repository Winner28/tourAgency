<%--
  Created by IntelliJ IDEA.
  User: vladey
  Date: 21.12.17
  Time: 13:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Tours List</title>
</head>
<body>
<h1><b>List of Tours: </b></h1> <br>

<ul>
    <c:forEach var="tour" items="${tourList}">
       <h4><li>${tour.toString()}</li></h4>
    </c:forEach>
</ul>

</body>
</html>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Permission List</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>
<body>
<%@include file="../topbar.jsp" %>

<h1 class="text-center glyphicon-text-color"><b>Permissions List</b></h1> <br>
<br>
<table class="table table-hover">
    <thead>
    <tr>
        <td class="text-center">User ID</td>
        <td class="text-center">Permission ID</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${permissions}" var="permission">
        <tr>
            <td class="text-center">${permission.userId}</td>
            <td class="text-center">${permission.permissionNameId}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form:form method = "POST" action = "/" cssClass="form-signin">
    <button class="btn btn-lg btn-success btn-block" type="submit">Get back to Home Page</button>
</form:form>
<%@include file="../footer.jsp" %>

</body>
</html>

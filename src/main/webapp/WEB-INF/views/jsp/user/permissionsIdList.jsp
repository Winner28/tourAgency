
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
<h1 class="text-center"><spring:message code="list.of"/><b>  ${userType}</b></h1> <br>
<br>
<table class="table table-hover">
    <thead>
    <tr>
        <td class="text-center"><spring:message code="firstName"/></td>
        <td class="text-center"><spring:message code="lastName"/></td>
        <td class="text-center"><spring:message code="email"/></td>

    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td class="text-center">${user.firstName}</td>
            <td class="text-center">${user.lastName}</td>
            <td class="text-center">${user.email}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form:form method = "GET" action = "/" cssClass="form-signin">
    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="button.back"/></button>
</form:form>
<br>
<br>
<br>
<%@include file="../footer.jsp" %>

</body>
</html>

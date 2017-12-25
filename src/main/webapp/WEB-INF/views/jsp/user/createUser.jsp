<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Create</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>

<body>
<%@include file="../topbar.jsp" %>

<h1 class="text-center">Create User</h1>
<form:form method = "POST" action = "/users/create" modelAttribute="user" cssClass="form-signin">
    <div class="form-group">

        <label for="firstName">First Name</label>
        <form:input path = "firstName" cssClass="form-control"/>
    </div>
    <div class="form-group">

        <label for="lastName">Last Name:</label>
        <form:input path = "lastName" cssClass="form-control"/>
    </div>
    <div class="form-group">

        <label for="email">Email:</label>
        <form:input path = "email" cssClass="form-control"/>
    </div>
    <div class="form-group">

        <label for="passwordHash">Password:</label>
        <form:password path = "passwordHash" cssClass="form-control"/>
    </div>

    <div class="container">
        <p>Choose user role:</p>
        <div class="radio">
            <label><input type="radio" name="permission" value="2">Tour Agent</label>
        </div>
        <div class="radio">
            <label><input type="radio" name="permission" value="3">User</label>
        </div>

    </div>

    <button class="btn btn-lg btn-primary btn-block" type="submit">Create User</button>
</form:form>
<%@include file="../footer.jsp" %>

</body>

</html>
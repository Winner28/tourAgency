<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

</head>

<body>

<h1>${errorMessage}</h1>
<div class="container">

    <form:form method="POST" action="${contextPath}/register" class="form-signin" >
        <h2 class="form-heading">Register</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <input name="firstName" type="text" class="form-control" placeholder="First Name"
                   autofocus="true"/>
            <input name="lastName" type="text" class="form-control" placeholder="Last Name"/>
            <input name="email" type="text" class="form-control" placeholder="Email"/>
            <input name="passwordHash" type="password" class="form-control" placeholder="Password"/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
            <h4 class="text-center"><a href="${contextPath}/login">Already have account? Log In</a></h4>
        </div>

    </form:form>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
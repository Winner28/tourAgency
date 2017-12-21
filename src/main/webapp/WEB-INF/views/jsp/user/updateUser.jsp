<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Update User Page</title>

    <spring:url value="/resources/core/css/hello.css" var="coreCss" />
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>
<h1>Update User Information with ID = <b> ${user.id}</b></h1>
<br>

<form:form method = "POST" action = "/users/update" modelAttribute="user">
    <input type="hidden" name="id" value="${user.id}"/>
    <table>
        <tr>
            <td><form:label path = "firstName">First Name</form:label></td>
            <td><form:input path = "firstName" /></td>
        </tr>
        <tr>
            <td><form:label path = "lastName">Last Name</form:label></td>
            <td><form:input path = "lastName" /></td>
        </tr>
        <tr>
            <td><form:label path = "email">Email</form:label></td>
            <td><form:input path = "email" /></td>
        </tr>
        <tr>
            <td><form:label path = "passwordHash">Password</form:label></td>
            <td><form:password path = "passwordHash" /></td>
        </tr>
        <tr>
            <td><input type="radio" name="permission" value="1"/>Tour Agent</td>
            <td><input type="radio" name="permission" value="2"/>Client</td>
        </tr>

        <tr>
            <td colspan = "3">
                <input type = "submit" value = "Submit"/>
            </td>
        </tr>
    </table>
</form:form>

<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</body>
</html>
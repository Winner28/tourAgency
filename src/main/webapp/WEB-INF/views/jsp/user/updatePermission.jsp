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
<h1>Update Permission Information with USER ID = <b> ${permission.userId}</b></h1>
<br>

<form:form method = "POST" action = "/permissions/update" modelAttribute="permission">
    <input type="hidden" name="id" value="${permission.userId}"/>
    <table>

        <tr>
            <td><form:label path = "permissionNameId">Permission Name Id</form:label></td>
            <td><form:input path = "permissionNameId" /></td>
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
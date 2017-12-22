<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Update Tour Page</title>

    <spring:url value="/resources/core/css/hello.css" var="coreCss" />
    <spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
</head>
<h1>Update Tour Information with ID = <b> ${tour.id}</b></h1>
<br>

<form:form method = "POST" action = "/tour/update" modelAttribute="tour">
    <input type="hidden" name="id" value="${tour.id}"/>
    <table>
        <table>
            <tr>
                <td><form:label path = "duration">Duration</form:label></td>
                <td><form:input path = "duration" /></td>
            </tr>
            <tr>
                <td><form:label path = "price">Price</form:label></td>
                <td><form:input path = "price" /></td>
            </tr>
            <tr>
                <td><form:label path = "tourTypesId">Email</form:label></td>
                <td><form:input path = "tourTypesId" /></td>
            </tr>
            <tr>
                <td><input type="radio" name="active" value="1"/>Active</td>
                <td><input type="radio" name="active" value="2"/>Archive</td>
            </tr>
            <tr>
                <td><input type="radio" name="hot" value="1"/>Casual</td>
                <td><input type="radio" name="hot" value="2"/>Hot</td>
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
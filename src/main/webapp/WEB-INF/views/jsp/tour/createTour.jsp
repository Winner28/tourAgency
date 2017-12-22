<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<head>
    <title>Creation</title>
</head>

<body>
<h2>Create Tour</h2>
<form:form method = "POST" action = "/tours/create" modelAttribute="tour">
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
        <td><form:label path = "tourTypesId">Tout Type ID</form:label></td>
        <td><form:input path = "tourTypesId" /></td>
    </tr>
        <tr>
            <td><form:label path = "agentId">Agent ID</form:label></td>
            <td><form:input path = "agentId" /></td>
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
</body>

</html>
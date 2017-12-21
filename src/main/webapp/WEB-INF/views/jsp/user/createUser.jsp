<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<head>
    <title>Create</title>
</head>

<body>
<h2>Create User</h2>
<form:form method = "POST" action = "/users/create" modelAttribute="user">
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
</body>

</html>
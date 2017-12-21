<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<head>
    <title>Create</title>
</head>

<body>
<h2>Create User Permission</h2>
<form:form method = "POST" action = "/users/permissions/create" modelAttribute="permission">
    <table>
        <tr>
            <td><form:label path = "userId">User Id</form:label></td>
            <td><form:input path = "userId" /></td>
        </tr>
        <tr>
            <td><form:label path = "permissionNameId">Permission Name</form:label></td>
            <td><form:input path = "permissionNameId" /></td>
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
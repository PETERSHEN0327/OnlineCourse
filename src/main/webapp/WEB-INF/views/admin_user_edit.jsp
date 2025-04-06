<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit User</title>
</head>
<body>
<h2>Edit User: ${user.username}</h2>

<form action="/admin/user/edit" method="post">
    <input type="hidden" name="id" value="${user.id}" />

    Full Name: <input type="text" name="fullName" value="${user.fullName}" required /><br/>
    Email: <input type="email" name="email" value="${user.email}" required /><br/>
    Phone: <input type="text" name="phone" value="${user.phone}" required /><br/>
    Role:
    <select name="role">
        <option value="ROLE_USER" ${user.role == 'ROLE_USER' ? 'selected' : ''}>User</option>
        <option value="ROLE_TEACHER" ${user.role == 'ROLE_TEACHER' ? 'selected' : ''}>Teacher</option>
        <option value="ROLE_ADMIN" ${user.role == 'ROLE_ADMIN' ? 'selected' : ''}>Admin</option>
    </select><br/><br/>

    <button type="submit">Save</button>
    <button type="reset">Cancel</button>
    <button type="button" onclick="location.href='/admin/users'">Back</button>
</form>

</body>
</html>

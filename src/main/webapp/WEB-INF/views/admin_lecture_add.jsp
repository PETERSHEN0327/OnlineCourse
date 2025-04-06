<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Lecture</title>
</head>
<body>
<h2>Add New Lecture</h2>

<form action="/admin/lecture/add" method="post">
    Title: <input type="text" name="title" required /><br/><br/>
    Material URL: <input type="text" name="materialUrl" /><br/><br/>
    <button type="submit">Save</button>
    <button type="reset">Cancel</button>
    <button type="button" onclick="location.href='/index'">Back</button>
</form>

</body>
</html>

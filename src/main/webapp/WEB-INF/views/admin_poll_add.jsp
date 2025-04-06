<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Add Poll</title></head>
<body>
<h2>Create New Poll</h2>

<form action="/admin/poll/add" method="post">
    Question: <input type="text" name="question" required /><br/><br/>
    Options (one per line):<br/>
    <textarea name="optionList" rows="5" cols="40" required></textarea><br/><br/>

    <button type="submit">Save</button>
    <button type="reset">Cancel</button>
    <button type="button" onclick="location.href='/index'">Back</button>
</form>

</body>
</html>

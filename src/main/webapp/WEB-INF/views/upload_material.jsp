<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Material</title>
</head>
<body>
<h2>Upload Lecture Material</h2>

<form action="/lecture/${lecture.id}/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="file" required />
    <br/><br/>
    <button type="submit">Save</button>
    <button type="reset">Cancel</button>
    <button type="button" onclick="location.href='/lecture/${lecture.id}'">Back</button>
</form>

</body>
</html>

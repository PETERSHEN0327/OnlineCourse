<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Manage Lecture Material</title></head>
<body>

<h2>Lecture: ${lecture.title}</h2>

<c:if test="${not empty lecture.materialUrl}">
    <p>Current material: <a href="${lecture.materialUrl}" target="_blank">View</a></p>
</c:if>

<form action="/admin/lecture/${lecture.id}/material" method="post" enctype="multipart/form-data">
    <input type="file" name="file" required /><br/><br/>
    <button type="submit">Save</button>
    <button type="reset">Cancel</button>
    <button type="button" onclick="location.href='/course/${lecture.course.id}'">Back</button>
</form>

<c:if test="${not empty lecture.materialUrl}">
    <form action="/admin/lecture/${lecture.id}/material/delete" method="get" style="margin-top: 10px;">
        <button type="submit" onclick="return confirm('Are you sure to remove this material?')">Delete Material</button>
    </form>
</c:if>

</body>
</html>

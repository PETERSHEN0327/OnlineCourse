<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Vote</title></head>
<body>

<h2>${poll.question}</h2>

<c:if test="${not empty error}">
    <p style="color:red;">${error}</p>
</c:if>

<form action="/poll/${poll.id}" method="post">
    <c:forEach var="opt" items="${poll.options}">
        <input type="radio" name="selectedOption" value="${opt}" required /> ${opt}<br/>
    </c:forEach>

    <br/>
    <button type="submit">Submit Vote</button>
    <button type="button" onclick="location.href='/index'">Back</button>
</form>

</body>
</html>

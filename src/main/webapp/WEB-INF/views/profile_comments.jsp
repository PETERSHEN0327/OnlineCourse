<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>My Comments</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            background-color: #f8f9fa;
            padding: 40px;
        }

        .container {
            max-width: 700px;
            margin: auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        .comment-box {
            border-bottom: 1px solid #ccc;
            padding: 10px 0;
        }

        .comment-box:last-child {
            border-bottom: none;
        }

        .back-button {
            margin-top: 20px;
            display: inline-block;
            background: #007bff;
            color: white;
            padding: 8px 16px;
            text-decoration: none;
            border-radius: 6px;
        }

        .source-label {
            font-weight: bold;
            color: #333;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>My Comment History</h2>

    <c:if test="${empty comments}">
        <p>You haven't posted any comments yet.</p>
    </c:if>

    <c:forEach var="comment" items="${comments}">
        <div class="comment-box">
            <c:if test="${not empty comment.course}">
                <span class="source-label">From Course:</span>
                <a href="/course/${comment.course.id}">${comment.course.name}</a><br/>
            </c:if>
            <c:if test="${not empty comment.poll}">
                <span class="source-label">From Poll:</span>
                <a href="/poll/${comment.poll.id}">${comment.poll.question}</a><br/>
            </c:if>
            <strong>Time:</strong>
                ${comment.timestamp.toLocalDate()} ${comment.timestamp.toLocalTime().toString().substring(0,5)}<br/>
            <strong>Content:</strong>
                ${comment.content}
        </div>
    </c:forEach>

    <!-- ✅ 返回首页按钮 -->
    <a href="/index" class="back-button">← Back to Home</a>
</div>

</body>
</html>

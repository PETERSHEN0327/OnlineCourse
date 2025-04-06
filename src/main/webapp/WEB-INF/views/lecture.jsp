<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Lecture Detail</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            background-color: #f5f5f5;
            padding: 40px;
        }

        .container {
            max-width: 700px;
            margin: auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        .material {
            margin-bottom: 20px;
        }

        .comments {
            margin-top: 30px;
        }

        .comment-item {
            padding: 10px 0;
            border-bottom: 1px solid #ccc;
        }

        .comment-actions {
            margin-top: 5px;
        }

        .comment-form {
            margin-top: 30px;
        }

        textarea {
            width: 100%;
            padding: 10px;
            font-size: 14px;
        }

        .submit-button {
            margin-top: 10px;
            padding: 8px 16px;
            font-size: 14px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        .back-button, .upload-button, .delete-button {
            margin-top: 20px;
            display: inline-block;
            padding: 10px 20px;
            color: white;
            border-radius: 6px;
            text-decoration: none;
        }

        .back-button {
            background-color: #007bff;
        }

        .upload-button {
            background-color: #ffc107;
            color: black;
            margin-left: 10px;
        }

        .delete-button {
            background-color: #dc3545;
            color: white;
            margin-left: 10px;
        }

        .action-link {
            font-size: 13px;
            margin-right: 10px;
        }

        .message-success {
            color: green;
            margin-bottom: 15px;
        }

        .message-error {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>

<div class="container">

    <!-- 提示信息 -->
    <c:if test="${not empty success}">
        <div class="message-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="message-error">${error}</div>
    </c:if>

    <!-- 标题 -->
    <h2>${lecture.title}</h2>

    <!-- 讲义信息 -->
    <div class="material">
        <strong>Download Material:</strong>
        <c:if test="${not empty lecture.materialUrl}">
            <a href="${lecture.materialUrl}" target="_blank">Click here</a>

            <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
                <a href="/admin/lecture/${lecture.id}/material/delete"
                   class="delete-button"
                   onclick="return confirm('Are you sure to remove this material?')">Delete Material</a>
            </c:if>
        </c:if>
        <c:if test="${empty lecture.materialUrl}">
            <span>No material provided.</span>
        </c:if>
    </div>

    <!-- 评论列表 -->
    <div class="comments">
        <h3>Comments</h3>
        <c:if test="${empty comments}">
            <p>No comments available.</p>
        </c:if>
        <c:forEach var="comment" items="${comments}">
            <div class="comment-item">
                <strong>${comment.username}</strong> at ${comment.timestamp}<br/>
                    ${comment.content}
                <c:if test="${comment.username == pageContext.request.userPrincipal.name || pageContext.request.isUserInRole('ROLE_TEACHER')}">
                    <div class="comment-actions">
                        <c:if test="${comment.username == pageContext.request.userPrincipal.name}">
                            <a class="action-link" href="/lecture/${lecture.id}/comment/${comment.id}/edit">Edit</a>
                            <a class="action-link" href="/lecture/${lecture.id}/comment/${comment.id}/delete"
                               onclick="return confirm('Are you sure you want to delete this comment?')">Delete</a>
                        </c:if>
                        <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
                            <a class="action-link" href="/admin/comment/delete/${comment.id}"
                               onclick="return confirm('Teacher delete: Confirm to remove this comment?')">[Admin Delete]</a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </div>

    <!-- 添加评论表单 -->
    <div class="comment-form">
        <h3>Add a Comment</h3>
        <form method="post" action="/lecture/${lecture.id}/comment">
            <textarea name="content" rows="4" required placeholder="Enter your comment here..."></textarea><br/>
            <button type="submit" class="submit-button">Submit</button>
        </form>
    </div>

    <!-- 页面按钮 -->
    <div style="margin-top: 30px;">
        <a href="/index" class="back-button">← Back to Index</a>

        <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
            <a href="/admin/lecture/${lecture.id}/material" class="upload-button">Manage Material</a>
            <a href="/admin/lecture/delete/${lecture.id}"
               class="delete-button"
               onclick="return confirm('Are you sure to delete this lecture?')">Delete Lecture</a>
        </c:if>
    </div>

</div>

</body>
</html>

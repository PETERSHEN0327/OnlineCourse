<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Course Details</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .comment-block { margin-top: 20px; border-top: 1px solid #ccc; padding-top: 20px; }
        .comment-form { margin-top: 20px; }
        .message { color: green; }
        .error { color: red; }
        button { margin-top: 10px; }
        .right-btn { float: right; margin-bottom: 10px; }
        .action-links { margin-left: 10px; }
    </style>
</head>
<body>

<h2>${course.name}</h2>

<c:if test="${not empty success}">
    <p class="message">${success}</p>
</c:if>

<!-- ✅ Lecture 列表 -->
<h3>Lecture List
    <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
        <a class="right-btn" href="${pageContext.request.contextPath}/admin/lecture/add?courseId=${course.id}">➕ Add Lecture</a>
    </c:if>
</h3>

<ul>
    <c:forEach var="lecture" items="${lectures}">
        <li>
                ${lecture.title}
            <c:if test="${not empty lecture.materialUrl}">
                - <a href="${lecture.materialUrl}" target="_blank">📄 Download</a>
            </c:if>

            <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
                <span class="action-links">
                    <a href="${pageContext.request.contextPath}/admin/lecture/${lecture.id}/material">📎 Manage</a>
                    <a href="${pageContext.request.contextPath}/admin/lecture/delete/${lecture.id}?courseId=${course.id}" onclick="return confirm('Delete this lecture?')">🗑️ Delete</a>
                </span>
            </c:if>
        </li>
    </c:forEach>
</ul>

<hr/>

<!-- ✅ 评论区域 -->
<h3>Comments (${totalComments})</h3>

<c:if test="${totalPages > 1}">
    <div>
        <c:forEach var="i" begin="0" end="${totalPages - 1}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <strong>[${i + 1}]</strong>
                </c:when>
                <c:otherwise>
                    <a href="/course/${course.id}?page=${i}">[${i + 1}]</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</c:if>

<c:forEach var="comment" items="${comments}">
    <div class="comment-block">
        <strong>${comment.username}</strong><br/>
            ${comment.timestamp.toLocalDate()} ${comment.timestamp.toLocalTime().toString().substring(0,5)}
        <p>${comment.content}</p>

        <c:if test="${comment.username == pageContext.request.userPrincipal.name}">
            <form action="/course/${course.id}/comment/${comment.id}/edit" method="get" style="display:inline;">
                <button type="submit">Edit</button>
            </form>
            <form action="/course/${course.id}/comment/${comment.id}/delete" method="post" style="display:inline;">
                <button type="submit">Delete</button>
            </form>
        </c:if>

        <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
            <form action="/course/${course.id}/comment/${comment.id}/delete" method="post" style="display:inline;">
                <button type="submit">Delete (Admin)</button>
            </form>
        </c:if>
    </div>
</c:forEach>

<!-- ✅ 评论提交表单 -->
<div class="comment-form">
    <h4>Leave a Comment</h4>
    <form action="/course/${course.id}/comment" method="post">
        <textarea name="content" rows="4" cols="50" required></textarea><br/>
        <button type="submit">Post Comment</button>
    </form>
</div>

<br/>
<a href="/index"><button type="button">← Back to Index</button></a>

</body>
</html>

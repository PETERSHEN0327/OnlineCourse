<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Vote</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        .comment-block { margin-top: 20px; border-top: 1px solid #ccc; padding-top: 20px; }
        .comment-form { margin-top: 20px; }
        .message { color: green; }
        .error { color: red; }
    </style>
</head>
<body>

<h2>${poll.question}</h2>

<!-- ✅ 投票错误提示 -->
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<!-- ✅ 投票成功提示 -->
<c:if test="${not empty success}">
    <p class="message">${success}</p>
</c:if>

<!-- ✅ 投票表单 -->
<form action="/poll/${poll.id}" method="post">
    <c:forEach var="opt" items="${options}">
        <input type="radio" name="selectedOptionId" value="${opt.id}" required />
        ${opt.optionText} - ${voteCounts[opt.optionText]} votes <br/>
    </c:forEach>

    <br/>
    <button type="submit">Submit Vote</button>
    <button type="button" onclick="location.href='/index'">Back</button>
</form>

<hr/>

<!-- ✅ 评论部分 -->
<h3>Comments (${totalComments})</h3>

<!-- ✅ 分页控制 -->
<c:if test="${totalPages > 1}">
    <div>
        <c:forEach var="i" begin="0" end="${totalPages - 1}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <strong>[${i + 1}]</strong>
                </c:when>
                <c:otherwise>
                    <a href="/poll/${poll.id}?page=${i}">[${i + 1}]</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>
</c:if>

<!-- ✅ 评论列表 -->
<c:forEach var="comment" items="${comments}">
    <div class="comment-block">
        <strong>${comment.username}</strong>
        <br/>
            ${comment.timestamp.toLocalDate()} ${comment.timestamp.toLocalTime().toString().substring(0,5)}
        <p>${comment.content}</p>

        <!-- ✅ 当前用户是评论作者 -->
        <c:if test="${comment.username == pageContext.request.userPrincipal.name}">
            <form action="/poll/${poll.id}/comment/${comment.id}/edit" method="get" style="display:inline;">
                <button type="submit">Edit</button>
            </form>
            <form action="/poll/${poll.id}/comment/${comment.id}/delete" method="post" style="display:inline;">
                <button type="submit">Delete</button>
            </form>
        </c:if>

        <!-- ✅ 当前用户是教师（管理员） -->
        <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
            <form action="/poll/${poll.id}/comment/${comment.id}/delete" method="post" style="display:inline;">
                <button type="submit">Delete (Admin)</button>
            </form>
        </c:if>
    </div>
</c:forEach>

<!-- ✅ 新增评论 -->
<div class="comment-form">
    <h4>Leave a Comment</h4>
    <form action="/poll/${poll.id}/comment" method="post">
        <textarea name="content" rows="4" cols="50" required></textarea><br/>
        <button type="submit">Post Comment</button>
    </form>
</div>

</body>
</html>

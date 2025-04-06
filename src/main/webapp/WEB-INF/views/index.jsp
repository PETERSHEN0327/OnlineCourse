<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Course Overview</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 30px;
        }

        .container {
            max-width: 900px;
            margin: auto;
            background-color: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        h2 {
            color: #343a40;
            margin-bottom: 20px;
        }

        h3 {
            margin-top: 30px;
            margin-bottom: 10px;
            color: #495057;
        }

        ul {
            list-style-type: disc;
            padding-left: 25px;
        }

        li {
            margin-bottom: 8px;
        }

        .right-btn {
            float: right;
            margin-top: -40px;
        }

        .action-links {
            font-size: 0.9em;
            margin-left: 10px;
        }

        .action-links a {
            margin-left: 10px;
            color: red;
            text-decoration: none;
        }

        .nav {
            margin-bottom: 20px;
        }

        .nav a {
            margin-right: 15px;
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }

        .nav a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="container">

    <!-- 顶部导航栏 -->
    <div class="nav">
        <a href="/profile/comments">📝 My Comments</a>
        <a href="/profile/votes">🗳️ My Votes</a>
        <a href="/logout" onclick="return confirm('Log out?')">🚪 Logout</a>
    </div>

    <!-- 确保用户已登录 -->
    <c:if test="${empty user}">
        <script>
            window.location.href = '/login';  // 如果用户未登录，跳转到登录页面
        </script>
    </c:if>

    <h2>Course Name</h2>
    <p>${courseName}</p>

    <!-- ========== 讲座部分 ========== -->
    <h3>Lecture List
        <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
            <a class="right-btn" href="/admin/lecture/add">➕ Add Lecture</a>
        </c:if>
    </h3>

    <ul>
        <c:forEach var="lecture" items="${lectures}">
            <li>
                <a href="/lecture/${lecture.id}">${lecture.title}</a>

                <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
                    <span class="action-links">
                        <a href="/admin/lecture/${lecture.id}/material">📎 Manage Material</a>
                        <a href="/admin/lecture/delete/${lecture.id}" onclick="return confirm('Delete this lecture?')">🗑️ Delete</a>
                    </span>
                </c:if>
            </li>
        </c:forEach>
    </ul>

    <!-- ========== 投票部分 ========== -->
    <h3>Poll List
        <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
            <a class="right-btn" href="/admin/poll/add">➕ Add Poll</a>
        </c:if>
    </h3>

    <ul>
        <c:forEach var="poll" items="${polls}">
            <li>
                <a href="/poll/${poll.id}">${poll.question}</a>

                <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
                    <span class="action-links">
                        <a href="/admin/poll/delete/${poll.id}" onclick="return confirm('Delete this poll?')">🗑️ Delete</a>
                    </span>
                </c:if>
            </li>
        </c:forEach>
    </ul>

</div>

</body>
</html>

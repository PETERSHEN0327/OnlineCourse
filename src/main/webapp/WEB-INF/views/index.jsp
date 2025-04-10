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

        h2, h3 {
            color: #343a40;
            margin-bottom: 20px;
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

        .course-list a {
            color: #007bff;
            text-decoration: none;
        }

        .course-list a:hover {
            text-decoration: underline;
        }

        .course-list {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>

<div class="container">

    <!-- ✅ 顶部导航栏 -->
    <div class="nav">
        <c:choose>
            <c:when test="${not empty pageContext.request.userPrincipal}">
                <a href="${pageContext.request.contextPath}/profile/comments">📝 My Comments</a>
                <a href="${pageContext.request.contextPath}/profile/votes">🗳️ My Votes</a>
                <a href="${pageContext.request.contextPath}/profile">👤 Personal Information</a>
                <a href="${pageContext.request.contextPath}/logout" onclick="return confirm('Log out?')">🚪 Logout</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login">📝 Login to Comment</a>
                <a href="${pageContext.request.contextPath}/login">🗳️ Login to Vote</a>
                <a href="${pageContext.request.contextPath}/register">🧾 Register</a>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- ✅ 可点击课程列表 -->
    <div class="course-list">
        <h2>Courses</h2>
        <ul>
            <c:forEach var="course" items="${courses}">
                <li>
                    <a href="${pageContext.request.contextPath}/course/${course.id}">${course.name}</a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <!-- ✅ Lecture 部分（已去除链接，只显示文字） -->
    <h3>Lecture List
        <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
            <a class="right-btn" href="${pageContext.request.contextPath}/admin/lecture/add">➕ Add Lecture</a>
        </c:if>
    </h3>

    <ul>
        <c:forEach var="lecture" items="${lectures}">
            <li>
                    ${lecture.title}
                <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
                    <span class="action-links">
                        <a href="${pageContext.request.contextPath}/admin/lecture/${lecture.id}/material">📎 Manage Material</a>
                        <a href="${pageContext.request.contextPath}/admin/lecture/delete/${lecture.id}" onclick="return confirm('Delete this lecture?')">🗑️ Delete</a>
                    </span>
                </c:if>
            </li>
        </c:forEach>
    </ul>

    <!-- ✅ Poll 部分 -->
    <h3>Poll List
        <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
            <a class="right-btn" href="${pageContext.request.contextPath}/admin/poll/add">➕ Add Poll</a>
        </c:if>
    </h3>

    <ul>
        <c:forEach var="poll" items="${polls}">
            <li>
                <a href="<c:choose>
                            <c:when test='${not empty pageContext.request.userPrincipal}'>
                                ${pageContext.request.contextPath}/poll/${poll.id}
                            </c:when>
                            <c:otherwise>
                                ${pageContext.request.contextPath}/login
                            </c:otherwise>
                        </c:choose>">
                        ${poll.question}
                </a>
                <c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
                    <span class="action-links">
                        <a href="${pageContext.request.contextPath}/admin/poll/delete/${poll.id}" onclick="return confirm('Delete this poll?')">🗑️ Delete</a>
                    </span>
                </c:if>
            </li>
        </c:forEach>
    </ul>

</div>

</body>
</html>

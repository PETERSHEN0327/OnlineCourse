<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>HKMU Login</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            background-color: #f0f2f5;
        }
        .login-box {
            background-color: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            text-align: center;
            width: 320px;
        }
        h2 {
            margin-bottom: 30px;
            color: #333;
        }
        input[type="text"],
        input[type="password"] {
            width: 90%;
            padding: 10px;
            margin: 8px 0;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }
        .btn-group {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-top: 20px;
        }
        .btn-group button {
            padding: 10px;
            border: none;
            border-radius: 6px;
            font-size: 14px;
            cursor: pointer;
            color: white;
        }
        .btn-login { background-color: #007bff; }
        .btn-register { background-color: #28a745; }
        .btn-guest { background-color: #ffc107; color: black; }
    </style>
</head>
<body>

<div class="login-box">
    <h2>Welcome to HKMU Online Courses</h2>

    <!-- 显示登出或注册成功的消息 -->
    <c:if test="${param.logout != null}">
        <div style="color: green; margin-bottom: 10px;">You have successfully logged out.</div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div style="color: green; margin-bottom: 10px;">${successMessage}</div>
    </c:if>

    <!-- 显示登录失败的消息 -->
    <c:if test="${param.error != null}">
        <div style="color: red; margin-bottom: 10px;">Invalid username or password.</div>
    </c:if>

    <!-- 登录表单 -->
    <form action="${pageContext.request.contextPath}/dologin" method="post">
        <input type="text" name="username" placeholder="Username" required />
        <input type="password" name="password" placeholder="Password" required />

        <div class="btn-group">
            <button type="submit" class="btn-login">Login</button>
            <button type="button" class="btn-register" onclick="location.href='${pageContext.request.contextPath}/register'">Register</button>
            <button type="button" class="btn-guest" onclick="location.href='${pageContext.request.contextPath}/index'">Guest Access</button>
        </div>
    </form>
</div>

</body>
</html>


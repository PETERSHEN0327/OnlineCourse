<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Registration</title>
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
        .register-box {
            background-color: white;
            padding: 40px;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            text-align: center;
            width: 350px;
        }
        h2 {
            margin-bottom: 25px;
            color: #333;
        }
        input[type="text"],
        input[type="password"],
        input[type="email"],
        input[type="tel"] {
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
        .btn-submit { background-color: #007bff; }
        .btn-cancel { background-color: #6c757d; }

        .message {
            margin-top: 15px;
            color: green;
        }
        .error {
            margin-top: 15px;
            color: red;
        }
    </style>
</head>
<body>

<div class="register-box">
    <h2>User Registration</h2>

    <!-- 成功或失败提示 -->
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="message">${successMessage}</div>
    </c:if>

    <form action="/register" method="post">
        <input type="text" name="username" placeholder="Username" required />
        <input type="password" name="password" placeholder="Password" required />
        <input type="text" name="fullName" placeholder="Full Name" required />
        <input type="email" name="email" placeholder="Email Address" required />
        <input type="tel" name="phone" placeholder="Phone Number" required />

        <div class="btn-group">
            <button type="submit" class="btn-submit">Register</button>
            <button type="button" class="btn-cancel" onclick="location.href='/'">Cancel</button>
        </div>
    </form>
</div>

</body>
</html>

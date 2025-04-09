<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
            margin: 8px 0 2px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 14px;
        }
        .error-text {
            font-size: 12px;
            color: red;
            margin-bottom: 6px;
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

    <!-- ✅ 成功/失败提示 -->
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>
    <c:if test="${not empty requestScope.successMessage}">
        <div class="message">${requestScope.successMessage}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <!-- Username -->
        <input type="text" name="username" placeholder="Username" value="${user.username}" required />
        <c:if test="${not empty errors.getFieldError('username')}">
            <div class="error-text">${errors.getFieldError('username').defaultMessage}</div>
        </c:if>

        <!-- Password -->
        <input type="password" name="password" placeholder="Password" required />
        <c:if test="${not empty errors.getFieldError('password')}">
            <div class="error-text">${errors.getFieldError('password').defaultMessage}</div>
        </c:if>

        <!-- Full Name -->
        <input type="text" name="fullName" placeholder="Full Name" value="${user.fullName}" required />
        <c:if test="${not empty errors.getFieldError('fullName')}">
            <div class="error-text">${errors.getFieldError('fullName').defaultMessage}</div>
        </c:if>

        <!-- Email -->
        <input type="email" name="email" placeholder="Email Address" value="${user.email}" required />
        <c:if test="${not empty errors.getFieldError('email')}">
            <div class="error-text">${errors.getFieldError('email').defaultMessage}</div>
        </c:if>

        <!-- Phone -->
        <input type="tel" name="phone" placeholder="Phone Number" value="${user.phone}" required />
        <c:if test="${not empty errors.getFieldError('phone')}">
            <div class="error-text">${errors.getFieldError('phone').defaultMessage}</div>
        </c:if>

        <!-- CSRF -->
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

        <div class="btn-group">
            <button type="submit" class="btn-submit">Register</button>
            <button type="button" class="btn-cancel" onclick="location.href='${pageContext.request.contextPath}/'">Cancel</button>
        </div>
    </form>
</div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Profile</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            padding: 30px;
            background-color: #f4f4f4;
        }

        .profile-box {
            max-width: 500px;
            margin: auto;
            background-color: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
        }

        input[type="text"],
        input[type="email"],
        input[type="tel"],
        input[type="password"] {
            width: 95%;
            padding: 10px;
            margin: 10px 0 2px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }

        .error-text {
            font-size: 12px;
            color: red;
            margin-bottom: 8px;
            text-align: left;
        }

        .btn-group {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }

        .btn-group button {
            padding: 10px 20px;
            font-size: 14px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }

        .btn-save { background-color: #007bff; color: white; }
        .btn-cancel { background-color: #6c757d; color: white; }
        .btn-back { background-color: #28a745; color: white; }

        .message {
            color: green;
            text-align: center;
            margin-top: 15px;
        }

        .links {
            text-align: center;
            margin-top: 30px;
        }

        .links a {
            margin: 0 10px;
            text-decoration: none;
            color: #007bff;
        }

        .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<div class="profile-box">
    <h2>User Profile</h2>

    <form action="${pageContext.request.contextPath}/profile/update" method="post">
        <!-- Username (readonly) -->
        <label>Username (readonly):</label><br/>
        <input type="text" name="username" value="${user.username}" readonly />

        <!-- Password -->
        <label>New Password:</label><br/>
        <input type="password" name="password" placeholder="Leave blank to keep current" />
        <c:if test="${not empty errors.getFieldError('password')}">
            <div class="error-text">${errors.getFieldError('password').defaultMessage}</div>
        </c:if>

        <!-- Full Name -->
        <label>Full Name:</label><br/>
        <input type="text" name="fullName" value="${user.fullName}" required />
        <c:if test="${not empty errors.getFieldError('fullName')}">
            <div class="error-text">${errors.getFieldError('fullName').defaultMessage}</div>
        </c:if>

        <!-- Email -->
        <label>Email:</label><br/>
        <input type="email" name="email" value="${user.email}" required />
        <c:if test="${not empty errors.getFieldError('email')}">
            <div class="error-text">${errors.getFieldError('email').defaultMessage}</div>
        </c:if>

        <!-- Phone -->
        <label>Phone:</label><br/>
        <input type="tel" name="phone" value="${user.phone}" required />
        <c:if test="${not empty errors.getFieldError('phone')}">
            <div class="error-text">${errors.getFieldError('phone').defaultMessage}</div>
        </c:if>

        <!-- Buttons -->
        <div class="btn-group">
            <button type="submit" class="btn-save" onclick="return confirm('Save changes?')">Save</button>
            <button type="button" class="btn-cancel" onclick="location.reload()">Cancel</button>
            <button type="button" class="btn-back" onclick="location.href='${pageContext.request.contextPath}/index'">Back</button>
        </div>
    </form>

    <!-- Optional server-side success message -->
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <!-- Quick links -->
    <div class="links">
        <a href="${pageContext.request.contextPath}/profile/comments">📝 My Comments</a>
        |
        <a href="${pageContext.request.contextPath}/profile/votes">🗳️ My Votes</a>
    </div>
</div>

</body>
</html>

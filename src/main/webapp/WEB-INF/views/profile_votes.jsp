<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>My Votes</title>
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

        .vote-box {
            border-bottom: 1px solid #ccc;
            padding: 10px 0;
        }

        .vote-box:last-child {
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
    </style>
</head>
<body>

<div class="container">
    <h2>My Vote History</h2>

    <c:if test="${empty votes}">
        <p>You haven't participated in any polls yet.</p>
    </c:if>

    <c:forEach var="vote" items="${votes}">
        <div class="vote-box">
            <strong>Poll:</strong> ${vote.poll.question}<br/>
            <strong>Your Choice:</strong> ${vote.selectedOption}<br/>
            <strong>Time:</strong> ${vote.timestamp}
        </div>
    </c:forEach>

    <a href="/profile" class="back-button">← Back to Profile</a>
</div>

</body>
</html>

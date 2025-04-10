<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add New Lecture</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            background-color: #f5f5f5;
            padding: 30px;
        }

        .form-container {
            background: white;
            padding: 25px;
            max-width: 600px;
            margin: auto;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin: 15px 0 5px;
        }

        input, select {
            width: 100%;
            padding: 8px;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        .button-group {
            margin-top: 20px;
            text-align: center;
        }

        button {
            margin: 0 10px;
            padding: 8px 16px;
            border: none;
            border-radius: 6px;
            background-color: #007bff;
            color: white;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }

        .cancel-btn {
            background-color: #6c757d;
        }

        .cancel-btn:hover {
            background-color: #5a6268;
        }

        .back-btn {
            background-color: #28a745;
        }

        .back-btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Add New Lecture</h2>

    <form action="/admin/lecture/add" method="post">
        <label for="title">Lecture Title:</label>
        <input type="text" name="title" id="title" required />

        <label for="materialUrl">Material URL (Optional):</label>
        <input type="text" name="materialUrl" id="materialUrl" />

        <label for="course">Select Course:</label>
        <select name="course.id" id="course" required>
            <c:forEach var="course" items="${courses}">
                <option value="${course.id}"
                        <c:if test="${course.id == selectedCourseId}">selected</c:if>>
                        ${course.name}
                </option>
            </c:forEach>
        </select>

        <div class="button-group">
            <button type="submit">Save</button>
            <button type="reset" class="cancel-btn">Cancel</button>
            <button type="button" class="back-btn" onclick="location.href='/index'">Back</button>
        </div>
    </form>
</div>

</body>
</html>

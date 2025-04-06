<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Comment</title>
    <style>
        body {
            font-family: "Segoe UI", sans-serif;
            background-color: #f2f2f2;
            padding: 40px;
        }

        .container {
            max-width: 600px;
            margin: auto;
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        textarea {
            width: 100%;
            height: 120px;
            padding: 10px;
            font-size: 14px;
            border-radius: 6px;
            border: 1px solid #ccc;
            margin-bottom: 20px;
        }

        .buttons {
            display: flex;
            justify-content: space-between;
        }

        button, .btn-link {
            padding: 10px 20px;
            font-size: 14px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            text-decoration: none;
        }

        .btn-save { background-color: #28a745; color: white; }
        .btn-cancel { background-color: #6c757d; color: white; }
        .btn-back { background-color: #007bff; color: white; margin-top: 20px; display: inline-block; }
    </style>
</head>
<body>

<div class="container">
    <h2>Edit Your Comment</h2>

    <form action="/lecture/${lecture.id}/comment/${commentEdit.id}/edit" method="post">
        <textarea name="content" required>${commentEdit.content}</textarea>

        <div class="buttons">
            <button type="submit" class="btn-save" onclick="return confirm('Save changes?')">Save</button>
            <button type="button" class="btn-cancel" onclick="window.location.href='/lecture/${lecture.id}'">Cancel</button>
        </div>
    </form>

    <a href="/lecture/${lecture.id}" class="btn-back">← Back to Lecture</a>
</div>

</body>
</html>

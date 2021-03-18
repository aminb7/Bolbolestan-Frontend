<%@ page import="com.example.CA3.model.BolbolestanApplication" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Courses</title>
</head>
<body>
<ul>
    <li id="std_id">Student Id: <%= BolbolestanApplication.getInstance().getLoggedInStudent() %></li>
    <li>
        <a href="/courses">Select Courses</a>
    </li>
    <li>
        <a href="/plan">Submited plan</a>
    </li>
    <li>
        <a href="/profile">Profile</a>
    </li>
    <li>
        <a href="/logout">Log Out</a>
    </li>
</ul>
</body>
</html>
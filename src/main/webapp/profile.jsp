<%@ page import="com.example.CA3.model.BolbolestanApplication" %>
<%@ page import="com.example.CA3.model.Student" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.CA3.model.SelectedCourse" %>
<%@ page import="com.example.CA3.model.GradedCourse" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <style>
        li {
            padding: 5px
        }
        table{
            width: 10%;
            text-align: center;
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<%
    Student student = BolbolestanApplication.getInstance().getLoggedInStudent();
%>
<ul>
    <li id="std_id">Student Id: <%= student.getId() %></li>
    <li id="first_name">First Name: <%= student.getName() %></li>
    <li id="last_name">Last Name: <%= student.getSecondName() %></li>
    <li id="birthdate">Birthdate: <%= student.getBirthDate() %></li>
    <li id="gpa">GPA: <%= student.getGPA() %></li>
    <li id="tpu">Total Passed Units: <%= student.getTotalPassedUnits() %></li>
</ul>
<table>
    <tr>
        <th>Code</th>
        <th>Grade</th>
    </tr>
    <% for (Map.Entry<String, GradedCourse> entry : student.getGradedCourses().entrySet()) {%>
        <tr>
            <th> <%= entry.getKey() %> </th>
            <th> <%= entry.getValue().getGrade() %> </th>
        </tr>
    <%}%>
</table>
</body>
</html>
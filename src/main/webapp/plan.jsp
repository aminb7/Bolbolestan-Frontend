<%@ page import="com.example.CA3.model.BolbolestanApplication" %>
<%@ page import="com.example.CA3.model.Student" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Plan</title>
    <style>
        table{
            width: 100%;
            text-align: center;

        }
        table, th, td{
            border: 1px solid black;
            border-collapse: collapse;
        }
    </style>
</head>
<body>
<a href="/">Home</a>
<%
    Student student = BolbolestanApplication.getInstance().getLoggedInStudent();
%>
<li id="code">Student Id: <%= student.getId() %></li>
<br>
<table>
    <tr>
        <th></th>
        <th>7:30-9:00</th>
        <th>9:00-10:30</th>
        <th>10:30-12:00</th>
        <th>14:00-15:30</th>
        <th>16:00-17:30</th>
    </tr>
    <%
        String[] days  = new String[] {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday"};
        final int numOfDays = 5;
        final int numOfSessionsPerDay = 5;
        for (int dayIndex = 0; dayIndex < numOfDays; dayIndex++) {
    %>
    <tr>
        <td><%= days[dayIndex] %></td>
        <%
            for (int sessionIndex = 0; sessionIndex < numOfSessionsPerDay; sessionIndex++) {
        %>
            <td>
                <%= student.getSubmittedCourseNameByTime(dayIndex, sessionIndex)%>
            </td>
        <%
            }
        %>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
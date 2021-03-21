<%@ page import="com.example.CA3.CoursesServlet" %><%--
  Created by IntelliJ IDEA.
  User: mrazimi
  Date: 3/21/2021
  Time: 9:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Submit Failed</title>
	<style>
        h1 {
            color: rgb(207, 3, 3);
        }
	</style>
</head>
<body>
<a href="/">Home</a>
<h1>
	Error:
</h1>
<br>
<h3>
	<%= CoursesServlet.getMessage()%>
</h3>
</body>
</html>

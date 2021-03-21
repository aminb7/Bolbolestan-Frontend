<%@ page import="com.example.CA3.model.BolbolestanApplication" %>
<%@ page import="com.example.CA3.model.Student" %>
<%@ page import="com.example.CA3.model.SelectedCourse" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.CA3.model.Course" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Courses</title>
	<style>
        .course_table {
            width: 100%;
            text-align: center;
        }
        .search_form {
            text-align: center;
        }
	</style>
</head>
<body>
<%
	BolbolestanApplication app = BolbolestanApplication.getInstance();
	Student student = BolbolestanApplication.getInstance().getLoggedInStudent();
%>
<a href="/">Home</a>
<li id="code">Student Id: <%= student.getId() %></li>
<li id="units">Total Selected Units: <%= student.getSelectedUnits() %></li>

<br>

<table>
	<tr>
		<th>Code</th>
		<th>Class Code</th>
		<th>Name</th>
		<th>Units</th>
		<th></th>
	</tr>
	<% for (Map.Entry<String, SelectedCourse> entry : student.getSelectedCourses().entrySet()) {%>
	<tr>
		<td><%= entry.getKey() %></td>
		<td><%= entry.getValue().getCourse().getClassCode() %></td>
		<td><%= entry.getValue().getCourse().getName() %></td>
		<td><%= entry.getValue().getCourse().getUnits() %></td>
		<td>
			<form action="" method="POST" >
				<input id="form_action" type="hidden" name="action" value="remove">
				<input id="form_course_code" type="hidden" name="course_code" value=<%= entry.getKey()%>>
				<input id="form_class_code" type="hidden" name="class_code" value=<%= entry.getValue().getCourse().getClassCode()%>>
				<button type="submit">Remove</button>
			</form>
		</td>
	</tr>
	<%}%>
</table>

<br>

<form action="" method="POST">
	<button type="submit" name="action" value="submit">Submit Plan</button>
	<button type="submit" name="action" value="reset">Reset</button>
</form>

<br>

<form class="search_form" action="" method="POST">
	<label>Search:</label>
	<input type="text" name="search" value="">
	<button type="submit" name="action" value="search">Search</button>
	<button type="submit" name="action" value="clear">Clear Search</button>
</form>



<br>

<table class="course_table">
	<tr>
		<th>Code</th>
		<th>Class Code</th>
		<th>Name</th>
		<th>Units</th>
		<th>Signed Up</th>
		<th>Capacity</th>
		<th>Type</th>
		<th>Days</th>
		<th>Time</th>
		<th>Exam Start</th>
		<th>Exam End</th>
		<th>Prerequisites</th>
		<th></th>
	</tr>
	<% for (Map.Entry<String, Map<String, Course>> groupEntry : app.getCourses().entrySet()) {%>
	<% for (Map.Entry<String, Course> entry : groupEntry.getValue().entrySet()) {%>
	<% Course course = entry.getValue();%>
	<tr>
		<td><%= course.getCode()%></td>
		<td><%= course.getClassCode()%></td>
		<td><%= course.getName()%></td>
		<td><%= course.getUnits()%></td>
		<td><%= course.getNumberOfStudents()%></td>
		<td><%= course.getCapacity()%></td>
		<td><%= course.getType()%></td>
		<td><%= String.join("|", course.getClassTime().getDays())%></td>
		<td><%= course.getClassTime().getStart() + "-" + course.getClassTime().getEnd()%></td>
		<td><%= course.getExamTime().getStart()%></td>
		<td><%= course.getExamTime().getEnd()%></td>
		<td><%= String.join("|", course.getPrerequisites())%></td>
		<td>
			<form action="" method="POST" >
				<input id="form_action" type="hidden" name="action" value="add">
				<input id="form_class_code" type="hidden" name="course_code" value=<%= course.getCode()%>>
				<input id="form_class_code" type="hidden" name="class_code" value=<%= course.getClassCode()%>>
				<button type="submit">Add</button>
			</form>
		</td>
	</tr>
	<%}%>
	<%}%>
</table>
</body>
</html>
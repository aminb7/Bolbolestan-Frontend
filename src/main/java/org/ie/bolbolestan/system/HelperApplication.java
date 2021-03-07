package org.ie.bolbolestan.system;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.ie.bolbolestan.utility.JsonParser;
import org.ie.bolbolestan.entity.*;
import org.ie.bolbolestan.exception.*;
import org.ie.bolbolestan.utility.RawDataCollector;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HelperApplication {
	private Map<String, Map<String, Course>> courses;
	private Map<String, Student> students;

	public class GetCoursesHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) {
			File input = new File("src/main/resources/templates/courses.html");
			Document document = null;
			try {
				document = Jsoup.parse(input, "UTF-8");
			}
			catch (Exception e){
				return;
			}

			List<Map<String, Course>> coursesGroup = new ArrayList(courses.values());
			document.body().selectFirst("table").select("tr").get(1).remove();
			document.body().selectFirst("table").select("tr").get(1).remove();

			for (Map<String, Course> courseGroup : coursesGroup) {
				for (Map.Entry<String, Course> course : courseGroup.entrySet()) {
					document.body().selectFirst("table").append("<tr>");
					document.body().selectFirst("table").append(course.getValue().getHtmlTable());
					document.body().selectFirst("table").append("</tr>");
				}
			}

			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class GetProfileHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) {
			File input = new File("src/main/resources/templates/profile.html");
			Document document = null;
			try {
				document = Jsoup.parse(input, "UTF-8");
			}
			catch (Exception e) {
				return;
			}
			String key = context.req.getRequestURI().split("/")[2];
			Student student = students.get(key);

			if (student == null) {
				send404(context);
				return;
			}

			document.getElementById("std_id").text("Student Id: " + student.getId());
			document.getElementById("first_name").text("First Name: " + student.getName());
			document.getElementById("last_name").text("Last Name: " + student.getSecondName());
			document.getElementById("birthdate").text("Birthdate: " + student.getBirthDate());
			document.getElementById("gpa").text("GPA: " + student.getGPA());
			document.getElementById("tpu").text("Total Passed Units: " + student.getTPU());

			document.body().selectFirst("table").select("tr").get(1).remove();
			document.body().selectFirst("table").select("tr").get(1).remove();
			document.body().selectFirst("table").select("tr").get(1).remove();

			for (GradedCourse course : new ArrayList<>(student.getGradedCourses().values())) {
				document.body().selectFirst("table")
						.append("<tr><td>" + course.getCode() + "</td><td>" + course.getGrade() + "</td></tr>");
			}

			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class ViewAddCourseHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) {
			File input = new File("src/main/resources/templates/course.html");
			Document document = null;
			try {
				document = Jsoup.parse(input, "UTF-8");
			} catch (Exception e) {
				return;
			}
			String[] uriParts = context.req.getRequestURI().split("/");
			String code = uriParts[2];
			String classCode = uriParts[3];
			Map<String, Course> courseGroup = courses.get(code);
			Course course = null;

			if (courseGroup == null){
				send404(context);
				return;
			}

			course = courseGroup.get(classCode);

			if (course == null) {
				send404(context);
				return;
			}

			document.getElementById("code").text("Code: " + course.getCode());
			document.getElementById("class_code").text("Class Code: " + course.getClassCode());
			document.getElementById("units").text("units: " + course.getUnits());
			ClassTime classTime = course.getClassTime();
			document.getElementById("days").text("Days: " + String.join(", ", classTime.getDays()));
			document.getElementById("time").text("Time: " + String.join("-", classTime.getStart().toString(),
					classTime.getEnd().toString()));

			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class AddCourseHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) {
			File input = new File("src/main/resources/templates/submit_ok.html");
			Document document;
			try {
				document = Jsoup.parse(input, "UTF-8");
			}
			catch (IOException e) {
				return;
			}
			String[] uriParts = context.req.getRequestURI().split("/");
			String code = uriParts[2];
			String classCode = uriParts[3];
			Map<String, Course> courseGroup = courses.get(code);
			Course course = null;

			if (courseGroup == null) {
				send404(context);
				return;
			}

			course = courseGroup.get(classCode);

			Student student = students.get(context.formParam("std_id"));

			if (course == null || student == null) {
				send404(context);
				return;
			}

			boolean hasPreconditions = true;

			for (String courseCode : course.getPrerequisites()) {
				GradedCourse gradedCourse = student.getGradedCourses().get(courseCode);

				if (gradedCourse == null || gradedCourse.getGrade() < 10)
					hasPreconditions = false;
			}

			String result = "";
			document.title("Course Is Not Added");
			context.status(400);

			if (hasPreconditions) {
				if (!student.getSelectedCourses().containsKey(course.getCode())) {
					student.addCourse(course);
					context.status(200);
					document.title("Course Added");
					result = "Course added successfully.";
				}
				else {
					result = "You have already added the course.";
				}
			}
			else {
				result = "You have not passed prerequisites.";
			}

			document.body().text(result);
			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class ChangePlanHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) throws Exception {
			File input = new File("src/main/resources/templates/change_plan.html");
			Document document = Jsoup.parse(input, "UTF-8");

			String[] uriParts = context.req.getRequestURI().split("/");
			String studentId = uriParts[2];

			Student student = students.get(studentId);

			if (student == null)
				send404(context);

			document.body().selectFirst("table").select("tr").get(1).remove();
			document.body().selectFirst("table").select("tr").get(1).remove();

			for (Map.Entry<String, SelectedCourse> selectedCourse : student.getSelectedCourses().entrySet()) {
				document.body().selectFirst("table").child(0).append("<tr>");
				document.body().selectFirst("table").child(0).append(
						"<td>" + selectedCourse.getValue().getCourse().getCode() + "</td>\n"
						+ "<td>" + selectedCourse.getValue().getCourse().getClassCode() + "</td> \n"
						+ "<td>" + selectedCourse.getValue().getCourse().getName() + "</td>\n"
						+ "<td>" + selectedCourse.getValue().getCourse().getUnits() + "</td>\n"
						+ "<td>\n"
						+ "<form action=\"\" method=\"POST\" >\n"
						+ "<input id=\"form_course_code\" type=\"hidden\" name=\"course_code\" value=\""
						+ selectedCourse.getValue().getCourse().getCode() + "\">\n"
						+ "<input id=\"form_class_code\" type=\"hidden\" name=\"class_code\" value=\""
						+ selectedCourse.getValue().getCourse().getClassCode() + "\">\n"
						+ "<button type=\"submit\">Remove</button>\n"
						+ "</form>\n"
						+ "</td>");
				document.body().selectFirst("table").child(0).append("</tr>");
			}

			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class RemoveCourseHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) throws Exception {
			File input = new File("src/main/resources/templates/submit_ok.html");
			Document document = Jsoup.parse(input, "UTF-8");

			String[] uriParts = context.req.getRequestURI().split("/");
			String studentId = uriParts[2];

			Student student = students.get(studentId);

			if (student == null)
				send404(context);

			String courseCode = context.formParam("course_code");
			String classCode = context.formParam("class_code");
			if (student.removeCourse(courseCode) != null) {
				courses.get(courseCode).get(classCode).decrementNumOfStudents();
				document.body().text("Course removed successfully.");
			}
			else
				document.body().text("The course is not selected.");

			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class ViewPlanHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) throws Exception {
			File input = new File("src/main/resources/templates/plan.html");
			Document document = Jsoup.parse(input, "UTF-8");

			String[] uriParts = context.req.getRequestURI().split("/");
			String studentId = uriParts[2];

			Student student = students.get(studentId);

			if (student == null)
				send404(context);

			for (Map.Entry<String, SelectedCourse> course : student.getSelectedCourses().entrySet()) {
				ClassTime classTime = course.getValue().getCourse().getClassTime();
				for (String day : classTime.getDays()) {
					int classIndex;
					switch (classTime.getStart().toString()) {
						case "07:30" -> classIndex = 0;
						case "09:00" -> classIndex = 1;
						case "10:30" -> classIndex = 2;
						case "14:00" -> classIndex = 3;
						case "16:00" -> classIndex = 4;
						default -> classIndex = 0;
					}
					document.body().selectFirst("td:contains(" + day + ")").parent().children().get(classIndex + 1).text(course.getValue().getCourse().getName());
				}
			}

			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class ViewCoursesSubmissionHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) throws Exception {
			File input = new File("src/main/resources/templates/submit.html");
			Document document = Jsoup.parse(input, "UTF-8");

			String[] uriParts = context.req.getRequestURI().split("/");
			String studentId = uriParts[2];

			Student student = students.get(studentId);

			if (student == null)
				send404(context);

			document.getElementById("code").text("Student Id: " + student.getId());
			document.getElementById("units").text("Total Units: : " + student.getSelectedUnits());

			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class CoursesSubmissionHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) throws Exception {
			String[] uriParts = context.req.getRequestURI().split("/");
			String studentId = uriParts[2];

			Student student = students.get(studentId);

			if (student == null)
				send404(context);

			File input = null;
			int selectedUnits = student.getSelectedUnits();
			if (selectedUnits < 12 || selectedUnits > 20)
				input = new File("src/main/resources/templates/submit_failed.html");
			else {
				student.finalizeCourses();
				input = new File("src/main/resources/templates/submit_ok.html");
			}

			Document document = Jsoup.parse(input, "UTF-8");
			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public HelperApplication() {
		this.courses = new HashMap<>();
		this.students = new HashMap<>();
	}

	public void fillInformation() throws IOException, InterruptedException {
		String host = "http://138.197.181.131:5000";
		Course[] coursesList = JsonParser.createObject(RawDataCollector.requestCourses(host), Course[].class);

		List.of(coursesList).forEach(course -> {
			if (!courses.containsKey(course.getCode()))
				courses.put(course.getCode(), new HashMap<>());

			this.courses.get(course.getCode()).put(course.getClassCode(), course);
		});

		String a = RawDataCollector.requestStudents(host);
		System.out.println(a);
		Student[] studentsList = JsonParser.createObject(a, Student[].class);

		List<String> studentIds = new ArrayList<>();
		List.of(studentsList).forEach(student -> studentIds.add(student.getId()));

		Map<String, String> rawGrades = RawDataCollector.requestGrades(host, studentIds);
		for (Student student : studentsList) {
			student.setGradedCourses(JsonParser.createObject(rawGrades.get(student.getId()), GradedCourse[].class));

			for (Map.Entry<String, GradedCourse> entry : student.getGradedCourses().entrySet()) {
				entry.getValue().setCourse(this.courses.get(entry.getKey()).entrySet().iterator().next().getValue());
			}
		}

		List.of(studentsList).forEach(student -> this.students.put(student.getId(), student));
	}

	private static void send404(Context context) {
		context.status(404);
		context.contentType("text/html");
		try {
			context.result(Jsoup.parse(new File("src/main/resources/templates/404.html"), "UTF-8").toString());
		}
		catch (IOException e){
		}
	}
}

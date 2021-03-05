package org.ie.bolbolestan.system;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.ie.bolbolestan.utility.JsonParser;
import org.ie.bolbolestan.entity.*;
import org.ie.bolbolestan.exception.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import org.ie.bolbolestan.utility.RawDataCollector;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HelperApplication {
	private Map<String, Map<String, Course>> courses;
	private Map<String, Student> students;

	private final ObjectMapper objectMapper;

	public class GetCoursesHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) throws Exception {
			File input = new File("target/classes/templates/courses.html");
			Document document = Jsoup.parse(input, "UTF-8");
			List<Course> courses = Arrays.asList(HelperApplication.this.courses.values().toArray(new Course[0]));
			document.body().selectFirst("table").select("tr").get(1).remove();
			document.body().selectFirst("table").select("tr").get(1).remove();

			for (Course course : courses) {
				document.body().selectFirst("table").append("<tr>");
				document.body().selectFirst("table").append(course.getHtmlTable());
				document.body().selectFirst("table").append("</tr>");
			}

			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class GetProfileHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) throws Exception {
			File input = new File("target/classes/templates/profile.html");
			Document document = Jsoup.parse(input, "UTF-8");
			String key = context.req.getRequestURI().split("/")[2];
			Student student = HelperApplication.this.students.get(key);

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
			document.body().selectFirst("table").append(student.getGradesHtml());

			context.contentType("text/html");
			context.result(document.toString());
		}
	}

	public class ViewAddCourseHandler implements Handler {
		@Override
		public void handle(@NotNull Context context) throws Exception {
			File input = new File("target/classes/templates/course.html");
			Document document = Jsoup.parse(input, "UTF-8");
			String[] uriParts = context.req.getRequestURI().split("/");
			String code = uriParts[2];
			String classCode = uriParts[3];
			Course course = HelperApplication.this.courses.get(code).get(classCode);

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

	public HelperApplication() {
		this.courses = new HashMap<>();
		this.students = new HashMap<>();
		this.objectMapper = new ObjectMapper();
	}

	public void fillInformation() throws IOException, InterruptedException {
		String host = "http://138.197.181.131:5000";
		Course[] coursesList = JsonParser.createObject(RawDataCollector.requestCourses(host), Course[].class);

		List.of(coursesList).forEach(course -> {
			if (!courses.containsKey(course.getCode()))
				courses.put(course.getCode(), new HashMap<>());

			this.courses.get(course.getClassCode()).put(course.getClassCode(), course);
		});

		Student[] studentsList = JsonParser.createObject(RawDataCollector.requestStudents(host), Student[].class);

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

	private static void send404(Context context) throws IOException {
		context.status(404);
		context.contentType("text/html");
		context.result(Jsoup.parse(new File("target/classes/templates/404.html"), "UTF-8").toString());
	}

	protected void checkFinalizing(Student student) throws MultiException {
		MultiException exception = new MultiException();

		Map<String, SelectedCourse> studentCourses = student.getSelectedCourses();
		List<SelectedCourse> selectedCourses = Arrays.asList(studentCourses.values().toArray(new SelectedCourse[0]));

		int selectedUnits = student.getSelectedUnits();
		if (selectedUnits < 12)
			exception.addError(new MinimumUnitsException());

		if (selectedUnits > 20)
			exception.addError(new MaximumUnitsException());

		for (int i = 0; i < selectedCourses.size(); i++) {
			SelectedCourse selectedCourse = selectedCourses.get(i);
//			if ((selectedCourse.getState() == CourseState.NON_FINALIZED) &&
//					(selectedCourse.getCourse().getNumberOfStudents() >= selectedCourse.getCourse().getCapacity()))
//				exception.addError(new CapacityException(selectedCourses.get(i).getCourse().getCode()));

			// Checking Conflicts.
			for (int j = i; j < selectedCourses.size(); j++) {
				if (i != j) {
					// Check Class Time Conflict.
//					if (selectedCourse.getCourse().getClassTime().overlaps(selectedCourses.get(j).getCourse().getClassTime()))
//						exception.addError(new ClassTimeCollisionException(selectedCourse.getCourse().getCode(),
//								selectedCourses.get(j).getCourse().getCode()));
//
//					// Check Exam Time Conflict.
//					if (selectedCourse.getCourse().getExamTime().overlaps(selectedCourses.get(j).getCourse().getExamTime()))
//						exception.addError(new ExamTimeCollisionException(selectedCourse.getCourse().getCode(),
//								selectedCourses.get(j).getCourse().getCode()));
				}
			}
		}

		if (exception.hasError())
			throw exception;
	}
}

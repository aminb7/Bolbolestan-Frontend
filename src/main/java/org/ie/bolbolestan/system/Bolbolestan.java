package org.ie.bolbolestan.system;

import org.ie.bolbolestan.entity.*;
import org.ie.bolbolestan.exception.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Bolbolestan {
	private final Map<Integer, Course> courses;
	private final Map<Integer, Student> students;
	private final ObjectMapper objectMapper;

	public Bolbolestan() {
		this.courses = new HashMap<>();
		this.students = new HashMap<>();
		this.objectMapper = new ObjectMapper();
	}

	public ObjectNode execute(String command, String inputData) {
		ObjectNode message = this.objectMapper.createObjectNode();

		try {
			message.put("success", true);
			JsonNode jsonData = objectMapper.readTree(inputData);

			// Call the command handler.
			JsonNode outputData = switch (command) {
				case "addOffering" -> this.addOffering(jsonData);
				case "addStudent" -> this.addStudent(jsonData);
				case "getOfferings" -> this.getOfferings(jsonData);
				case "getOffering" -> this.getOffering(jsonData);
				case "addToWeeklySchedule" -> this.addToWeeklySchedule(jsonData);
				case "removeFromWeeklySchedule" -> this.removeFromWeeklySchedule(jsonData);
				case "getWeeklySchedule" -> this.getWeeklySchedule(jsonData);
				case "finalize" -> this.finalize(jsonData);
				default -> throw new CommandNotFoundException();
			};

			message.set("data", outputData);
		}
		catch (Exception error) {
			message.put("success", false);
			message.put("error", error.getMessage());
		}

		return message;
	}

	protected void addCourse(Course course) throws CourseAlreadyExistsException {
		if (courses.containsKey(course.getCode()))
			throw new CourseAlreadyExistsException();

		this.courses.put(course.getCode(), course);
	}

	protected void addStudent(Student student) throws StudentAlreadyExistsException {
		if (students.containsKey(student.getStudentId()))
			throw new StudentAlreadyExistsException();

		this.students.put(student.getStudentId(), student);
	}

	public Course getCourse(Integer code) throws OfferingNotFoundException {
		Course course = courses.get(code);
		if (course == null)
			throw new OfferingNotFoundException();

		return course;
	}

	public List<Course> getCourses() {
		return Arrays.asList(courses.values().toArray(new Course[0]));
	}

	public Student getStudent(Integer studentId) throws StudentNotFoundException {
		Student student = students.get(studentId);
		if (student == null)
			throw new StudentNotFoundException();

		return student;
	}

	public void checkStudentExists(Integer studentId) throws StudentNotFoundException {
		if (!students.containsKey(studentId))
			throw new StudentNotFoundException();
	}

	public List<Student> getStudents() {
		return Arrays.asList(students.values().toArray(new Student[0]));
	}

	protected ObjectNode addOffering(JsonNode jsonInput) throws CourseAlreadyExistsException {
		JsonNode classTimeNode = jsonInput.with("classTime");
		String[] days = this.objectMapper.convertValue(classTimeNode.withArray("days"), String[].class);
		JsonNode examTimeNode = jsonInput.get("examTime");

		int code = jsonInput.get("code").asInt();
		String name = jsonInput.get("name").asText();
		String instructor = jsonInput.get("Instructor").asText();
		int units = jsonInput.get("units").asInt();
		ClassTime classTime = new ClassTime(days, classTimeNode.get("time").asText());
		ExamTime examTime = new ExamTime(LocalDateTime.parse(examTimeNode.get("start").asText(),
				DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse(examTimeNode.get("end").asText(),
						DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));

		int capacity = jsonInput.get("capacity").asInt();
		String[] prerequisites = this.objectMapper.convertValue(jsonInput.withArray("prerequisites"), String[].class);
		this.addCourse(new Course(code, name, instructor, units, classTime, examTime, capacity, prerequisites));
		return this.objectMapper.createObjectNode();
	}

	protected ObjectNode addStudent(JsonNode jsonInput) throws StudentAlreadyExistsException {
		Student newStudent = new Student(jsonInput.get("studentId").asInt(), jsonInput.get("name").asText(),
				Year.of(jsonInput.get("enteredAt").asInt()));

		this.addStudent(newStudent);
		return this.objectMapper.createObjectNode();
	}

	protected ArrayNode getOfferings(JsonNode jsonInput) throws Exception {
		checkStudentExists(jsonInput.get("StudentId").asInt());

		ArrayNode answerData = this.objectMapper.createArrayNode();
		List<Course> coursesList = this.getCourses();
		coursesList.sort(Comparator.comparing(Course::getName));

		for (Course course : coursesList)
			answerData.add(course.getJsonSummary());

		return answerData;
	}

	protected ObjectNode getOffering(JsonNode jsonInput) throws Exception {
		checkStudentExists(jsonInput.get("StudentId").asInt());
		Course course = this.getCourse(jsonInput.get("StudentId").asInt());
		return course.getJsonFullInfo();
	}

	protected ObjectNode addToWeeklySchedule(JsonNode jsonInput) throws Exception {
		Student student = this.getStudent(jsonInput.get("StudentId").asInt());
		Course course = this.getCourse(jsonInput.get("code").asInt());
		student.addCourse(course);
		return this.objectMapper.createObjectNode();
	}

	protected ObjectNode removeFromWeeklySchedule(JsonNode jsonInput) throws Exception {
		Student student = this.getStudent(jsonInput.get("StudentId").asInt());
		Course course = this.getCourse(jsonInput.get("code").asInt());
		student.removeCourse(course);
		return this.objectMapper.createObjectNode();
	}

	protected ObjectNode getWeeklySchedule(JsonNode jsonInput) throws Exception {
		Student student = this.getStudent(jsonInput.get("StudentId").asInt());
		ObjectNode answerData = this.objectMapper.createObjectNode();
		ArrayNode weeklySchedule = this.objectMapper.createArrayNode();

		Map<Integer, SelectedCourse> courses = student.getCourses();
		SelectedCourse[] coursesList = courses.values().toArray(new SelectedCourse[0]);

		for (SelectedCourse selectedCourse : coursesList) {
			ObjectNode courseData = selectedCourse.getCourse().getJsonFullInfo();
			courseData.put("status", selectedCourse.getState().toString());
			weeklySchedule.add(courseData);
		}

		answerData.set("weeklySchedule", weeklySchedule);
		return answerData;
	}

	protected void checkFinalizing(Student student) throws MultiException {
		MultiException exception = new MultiException();

		Map<Integer, SelectedCourse> studentCourses = student.getCourses();
		List<SelectedCourse> selectedCourses = Arrays.asList(studentCourses.values().toArray(new SelectedCourse[0]));

		int selectedUnits = student.getSelectedUnits();
		if (selectedUnits < 12)
			exception.addError(new MinimumUnitsException());

		if (selectedUnits > 20)
			exception.addError(new MaximumUnitsException());

		for (int i = 0; i < selectedCourses.size(); i++) {
			SelectedCourse selectedCourse = selectedCourses.get(i);
			if ((selectedCourse.getState() == CourseState.NON_FINALIZED) &&
					(selectedCourse.getCourse().getNumberOfStudents() >= selectedCourse.getCourse().getCapacity()))
				exception.addError(new CapacityException(selectedCourses.get(i).getCourse().getCode()));

			// Checking Conflicts.
			for (int j = 0; j < selectedCourses.size(); j++) {
				if (i != j) {
					// Check Class Time Conflict.
					if (selectedCourse.getCourse().getClassTime().overlaps(selectedCourses.get(j).getCourse().getClassTime()))
						exception.addError(new ClassTimeCollisionException(selectedCourse.getCourse().getCode(),
								selectedCourses.get(j).getCourse().getCode()));

					// Check Exam Time Conflict.
					if (selectedCourse.getCourse().getExamTime().overlaps(selectedCourses.get(j).getCourse().getExamTime()))
						exception.addError(new ExamTimeCollisionException(selectedCourse.getCourse().getCode(),
								selectedCourses.get(j).getCourse().getCode()));
				}
			}
		}

		if (exception.hasError())
			throw exception;
	}

	protected ObjectNode finalize(JsonNode jsonInput) throws Exception {
		Student student = this.getStudent(jsonInput.get("StudentId").asInt());
		checkFinalizing(student);
		student.finalizeCourses();
		return this.objectMapper.createObjectNode();
	}
}

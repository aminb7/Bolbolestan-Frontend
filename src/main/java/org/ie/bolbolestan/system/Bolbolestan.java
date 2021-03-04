package org.ie.bolbolestan.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.ie.bolbolestan.entity.*;
import org.ie.bolbolestan.exception.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Bolbolestan {
	private final Map<String, Course> courses;
	private final Map<String, Student> students;
	private final ObjectMapper objectMapper;

	public Bolbolestan() {
		this.courses = new HashMap<>();
		this.students = new HashMap<>();
		this.objectMapper = new ObjectMapper();


		try {
			Course[] x = objectMapper.readValue("[{\"code\": \"8101001\", \"classCode\": \"01\", \"name\": \"Advanced Programming\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 45, \"prerequisites\": [\"8101013\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"10:30-12:00\"}, \"examTime\": {\"start\": \"2021-06-21T14:00:00\", \"end\": \"2021-06-21T17:00:00\"}}, {\"code\": \"8101002\", \"classCode\": \"01\", \"name\": \"Algorithm Design 1\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 45, \"prerequisites\": [\"8101009\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"7:30-9:00\"}, \"examTime\": {\"start\": \"2021-06-12T14:00:00\", \"end\": \"2021-06-12T17:00:00\"}}, {\"code\": \"8101003\", \"classCode\": \"01\", \"name\": \"Artificial Intelligence\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [\"8101002\"], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"16:00-17:30\"}, \"examTime\": {\"start\": \"2021-06-22T08:30:00\", \"end\": \"2021-06-22T11:30:00\"}}, {\"code\": \"8101004\", \"classCode\": \"01\", \"name\": \"Calculus 1\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"14:00-15:30\"}, \"examTime\": {\"start\": \"2021-06-16T14:00:00\", \"end\": \"2021-06-16T17:00:00\"}}, {\"code\": \"8101005\", \"classCode\": \"01\", \"name\": \"Calculus 2\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 45, \"prerequisites\": [\"8101004\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"10:30-12:00\"}, \"examTime\": {\"start\": \"2021-06-04T14:00:00\", \"end\": \"2021-06-04T17:00:00\"}}, {\"code\": \"8101006\", \"classCode\": \"01\", \"name\": \"Computer Aided Digital System Design\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [\"8101007\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"7:30-9:00\"}, \"examTime\": {\"start\": \"2021-06-19T14:00:00\", \"end\": \"2021-06-19T17:00:00\"}}, {\"code\": \"8101007\", \"classCode\": \"01\", \"name\": \"Computer Architecture\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [\"8101014\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"10:30-12:00\"}, \"examTime\": {\"start\": \"2021-06-17T08:30:00\", \"end\": \"2021-06-17T11:30:00\"}}, {\"code\": \"8101008\", \"classCode\": \"01\", \"name\": \"Computer Workshop\", \"units\": 1, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 15, \"prerequisites\": [], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-25T14:00:00\", \"end\": \"2021-06-25T17:00:00\"}}, {\"code\": \"8101009\", \"classCode\": \"01\", \"name\": \"Data Structures\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [\"8101001\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"7:30-9:00\"}, \"examTime\": {\"start\": \"2021-06-01T08:30:00\", \"end\": \"2021-06-01T11:30:00\"}}, {\"code\": \"8101010\", \"classCode\": \"01\", \"name\": \"Discrete Mathematics\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 45, \"prerequisites\": [], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"7:30-9:00\"}, \"examTime\": {\"start\": \"2021-06-18T08:30:00\", \"end\": \"2021-06-18T11:30:00\"}}, {\"code\": \"8101011\", \"classCode\": \"01\", \"name\": \"Electric Circuits\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [\"8101018\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"14:00-15:30\"}, \"examTime\": {\"start\": \"2021-06-18T08:30:00\", \"end\": \"2021-06-18T11:30:00\"}}, {\"code\": \"8101012\", \"classCode\": \"01\", \"name\": \"Engineering Probability and Statistics\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 135, \"prerequisites\": [\"8101005\"], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-09T08:30:00\", \"end\": \"2021-06-09T11:30:00\"}}, {\"code\": \"8101013\", \"classCode\": \"01\", \"name\": \"Introduction to Computing Systems and Programming\", \"units\": 4, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 120, \"prerequisites\": [], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"14:00-15:30\"}, \"examTime\": {\"start\": \"2021-06-02T14:00:00\", \"end\": \"2021-06-02T17:00:00\"}}, {\"code\": \"8101014\", \"classCode\": \"01\", \"name\": \"Logic Circuits\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [\"8101018\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-25T14:00:00\", \"end\": \"2021-06-25T17:00:00\"}}, {\"code\": \"8101015\", \"classCode\": \"01\", \"name\": \"Operating Systems\", \"units\": 4, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 120, \"prerequisites\": [\"8101007\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-12T14:00:00\", \"end\": \"2021-06-12T17:00:00\"}}, {\"code\": \"8101016\", \"classCode\": \"01\", \"name\": \"Physical Education\", \"units\": 1, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 15, \"prerequisites\": [], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-08T08:30:00\", \"end\": \"2021-06-08T11:30:00\"}}, {\"code\": \"8101017\", \"classCode\": \"01\", \"name\": \"Physics 1\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"7:30-9:00\"}, \"examTime\": {\"start\": \"2021-06-15T14:00:00\", \"end\": \"2021-06-15T17:00:00\"}}, {\"code\": \"8101018\", \"classCode\": \"01\", \"name\": \"Physics 2\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 135, \"prerequisites\": [\"8101017\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"14:00-15:30\"}, \"examTime\": {\"start\": \"2021-06-06T08:30:00\", \"end\": \"2021-06-06T11:30:00\"}}, {\"code\": \"8101019\", \"classCode\": \"01\", \"name\": \"Real Time Embedded Systems\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-23T14:00:00\", \"end\": \"2021-06-23T17:00:00\"}}, {\"code\": \"8101020\", \"classCode\": \"01\", \"name\": \"Systems Analysis\", \"units\": 3, \"type\": \"Asli\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"7:30-9:00\"}, \"examTime\": {\"start\": \"2021-06-16T14:00:00\", \"end\": \"2021-06-16T17:00:00\"}}, {\"code\": \"8101021\", \"classCode\": \"01\", \"name\": \"Computer Graphics\", \"units\": 3, \"type\": \"Ekhtiary\", \"instructor\": \"sample\", \"capacity\": 45, \"prerequisites\": [], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"7:30-9:00\"}, \"examTime\": {\"start\": \"2021-06-17T08:30:00\", \"end\": \"2021-06-17T11:30:00\"}}, {\"code\": \"8101022\", \"classCode\": \"01\", \"name\": \"Microprocessors 1\", \"units\": 3, \"type\": \"Ekhtiary\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-21T08:30:00\", \"end\": \"2021-06-21T11:30:00\"}}, {\"code\": \"8101023\", \"classCode\": \"01\", \"name\": \"Parallel Programming\", \"units\": 3, \"type\": \"Ekhtiary\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-14T08:30:00\", \"end\": \"2021-06-14T11:30:00\"}}, {\"code\": \"8101024\", \"classCode\": \"01\", \"name\": \"Database Design\", \"units\": 3, \"type\": \"Takhasosi\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [\"8101015\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"14:00-15:30\"}, \"examTime\": {\"start\": \"2021-06-01T08:30:00\", \"end\": \"2021-06-01T11:30:00\"}}, {\"code\": \"8101025\", \"classCode\": \"01\", \"name\": \"Database Laboratory\", \"units\": 1, \"type\": \"Takhasosi\", \"instructor\": \"sample\", \"capacity\": 25, \"prerequisites\": [\"8101024\"], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-15T14:00:00\", \"end\": \"2021-06-15T17:00:00\"}}, {\"code\": \"8101026\", \"classCode\": \"01\", \"name\": \"Internet Engineering\", \"units\": 3, \"type\": \"Takhasosi\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [\"8101015\", \"8101001\"], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"7:30-9:00\"}, \"examTime\": {\"start\": \"2021-06-14T08:30:00\", \"end\": \"2021-06-14T11:30:00\"}}, {\"code\": \"8101027\", \"classCode\": \"01\", \"name\": \"Object Oriented Systems Design\", \"units\": 3, \"type\": \"Takhasosi\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [], \"classTime\": {\"days\": [\"Sunday\", \"Tuesday\"], \"time\": \"10:30-12:00\"}, \"examTime\": {\"start\": \"2021-06-09T14:00:00\", \"end\": \"2021-06-09T17:00:00\"}}, {\"code\": \"8101028\", \"classCode\": \"01\", \"name\": \"Software Engineering\", \"units\": 3, \"type\": \"Takhasosi\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"10:30-12:00\"}, \"examTime\": {\"start\": \"2021-06-14T08:30:00\", \"end\": \"2021-06-14T11:30:00\"}}, {\"code\": \"8101029\", \"classCode\": \"01\", \"name\": \"Systems Analysis and Design\", \"units\": 3, \"type\": \"Takhasosi\", \"instructor\": \"sample\", \"capacity\": 80, \"prerequisites\": [\"8101015\"], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"10:30-12:00\"}, \"examTime\": {\"start\": \"2021-06-15T14:00:00\", \"end\": \"2021-06-15T17:00:00\"}}, {\"code\": \"8101030\", \"classCode\": \"01\", \"name\": \"Enghelab Islami\", \"units\": 2, \"type\": \"Umumi\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [], \"classTime\": {\"days\": [\"Wednesday\"], \"time\": \"16:00-17:30\"}, \"examTime\": {\"start\": \"2021-06-23T14:00:00\", \"end\": \"2021-06-23T17:00:00\"}}, {\"code\": \"8101031\", \"classCode\": \"01\", \"name\": \"English Language\", \"units\": 3, \"type\": \"Umumi\", \"instructor\": \"sample\", \"capacity\": 45, \"prerequisites\": [], \"classTime\": {\"days\": [\"Saturday\", \"Monday\"], \"time\": \"9:00-10:30\"}, \"examTime\": {\"start\": \"2021-06-04T08:30:00\", \"end\": \"2021-06-04T11:30:00\"}}, {\"code\": \"8101032\", \"classCode\": \"01\", \"name\": \"Islamic Revolution in Iran\", \"units\": 2, \"type\": \"Umumi\", \"instructor\": \"sample\", \"capacity\": 90, \"prerequisites\": [], \"classTime\": {\"days\": [\"Monday\"], \"time\": \"10:30-12:00\"}, \"examTime\": {\"start\": \"2021-06-03T08:30:00\", \"end\": \"2021-06-03T11:30:00\"}}, {\"code\": \"8101033\", \"classCode\": \"01\", \"name\": \"Islamic Thought 1\", \"units\": 2, \"type\": \"Umumi\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [], \"classTime\": {\"days\": [\"Sunday\"], \"time\": \"7:30-9:00\"}, \"examTime\": {\"start\": \"2021-06-02T08:30:00\", \"end\": \"2021-06-02T11:30:00\"}}, {\"code\": \"8101034\", \"classCode\": \"01\", \"name\": \"Islamic Thought 2 (Prophethood and Imamat)\", \"units\": 2, \"type\": \"Umumi\", \"instructor\": \"sample\", \"capacity\": 60, \"prerequisites\": [\"8101033\"], \"classTime\": {\"days\": [\"Saturday\"], \"time\": \"16:00-17:30\"}, \"examTime\": {\"start\": \"2021-06-10T14:00:00\", \"end\": \"2021-06-10T17:00:00\"}}]",
					Course[].class);

//			System.out.println(x.getCode() + "," + x.getExamTime().getStart());
			System.out.println(x.length);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
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
		if (students.containsKey(student.getId()))
			throw new StudentAlreadyExistsException();

		this.students.put(student.getId(), student);
	}

	protected Course getCourse(Integer code) throws OfferingNotFoundException {
		Course course = courses.get(code);
		if (course == null)
			throw new OfferingNotFoundException();

		return course;
	}

	protected List<Course> getCourses() {
		return Arrays.asList(courses.values().toArray(new Course[0]));
	}

	protected Student getStudent(Integer studentId) throws StudentNotFoundException {
		Student student = students.get(studentId);
		if (student == null)
			throw new StudentNotFoundException();

		return student;
	}

	protected void checkStudentExists(Integer studentId) throws StudentNotFoundException {
		if (!students.containsKey(studentId))
			throw new StudentNotFoundException();
	}

	protected List<Student> getStudents() {
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
//		this.addCourse(new Course(code, name, instructor, units, classTime, examTime, capacity, prerequisites));
		return this.objectMapper.createObjectNode();
	}

	protected ObjectNode addStudent(JsonNode jsonInput) throws StudentAlreadyExistsException {
//		Student newStudent = new Student(jsonInput.get("studentId").asInt(), jsonInput.get("name").asText(),
//				Year.of(jsonInput.get("enteredAt").asInt()));

//		this.addStudent(newStudent);
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
		Course course = this.getCourse(jsonInput.get("code").asInt());
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

		Map<String, SelectedCourse> courses = student.getSelectedCourses();
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

	protected ObjectNode finalize(JsonNode jsonInput) throws StudentNotFoundException, MultiException {
		Student student = this.getStudent(jsonInput.get("StudentId").asInt());
		checkFinalizing(student);
		student.finalizeCourses();
		return this.objectMapper.createObjectNode();
	}
}

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Bolbolestan {
	private Map<Integer, Course> courses;
	private Map<Integer, Student> students;
	private ObjectMapper objectMapper;

	public Bolbolestan() {
		this.courses = new HashMap<>();
		this.students = new HashMap<>();
		this.objectMapper = new ObjectMapper();
	}

	public void execute(String command, String data) {
		ObjectNode message = this.objectMapper.createObjectNode();

		try {
			message.put("success", true);
			JsonNode jsonData = objectMapper.readTree(data);

			// Call the command handler.
			JsonNode jsonAnswer = switch (command) {
				case "addOffering" -> this.addOffering(jsonData);
				case "addStudent" -> this.addStudent(jsonData);
				case "getOfferings" -> this.getOfferings(jsonData);
				case "getOffering" -> this.getOffering(jsonData);
				case "addToWeeklySchedule" -> this.addToWeeklySchedule(jsonData);
				case "removeFromWeeklySchedule" -> this.removeFromWeeklySchedule(jsonData);
				case "getWeeklySchedule" -> this.getWeeklySchedule(jsonData);
				case "finalize" -> this.finalize(jsonData);
				default -> this.objectMapper.createObjectNode();
			};

			message.set("data", jsonAnswer);
		}
		catch (Exception error) {
			message.put("success", false);
			message.put("error", error.getMessage());
		}

		try {
			System.out.println(this.objectMapper.writeValueAsString(message));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private ObjectNode addOffering(JsonNode jsonInput) {
		JsonNode classTimeNode = jsonInput.with("classTime");
		String[] days = this.objectMapper.convertValue(classTimeNode.withArray("days"), String[].class);
		JsonNode examTimeNode = jsonInput.get("examTime");

		int code = jsonInput.get("code").asInt();
		String name = jsonInput.get("name").asText();
		String instructor = jsonInput.get("Instructor").asText();
		int units = jsonInput.get("units").asInt();
		ClassTime classTime = new ClassTime(days, classTimeNode.get("time").asText());
		ExamTime examTime = new ExamTime(
				LocalDate.parse(examTimeNode.get("start").asText(), DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDate.parse(examTimeNode.get("end").asText(), DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));

		int capacity = jsonInput.get("capacity").asInt();
		String[] prerequisites = this.objectMapper.convertValue(jsonInput.withArray("prerequisites"), String[].class);

		this.courses.put(code, new Course(code, name, instructor, units, classTime, examTime, capacity, prerequisites));

		return this.objectMapper.createObjectNode();
	}

	private ObjectNode addStudent(JsonNode jsonInput) {
		Student newStudent = new Student(jsonInput.get("studentId").asInt(), jsonInput.get("name").asText(), Year.of(jsonInput.get("enteredAt").asInt()));
		this.students.put(jsonInput.get("studentId").asInt(), newStudent);

		return this.objectMapper.createObjectNode();
	}

	private ArrayNode getOfferings(JsonNode jsonInput) throws Exception {
		if (!students.containsKey(jsonInput.get("StudentId").asInt()))
			throw new Exception("StudentNotFound");

		ArrayNode answerData = this.objectMapper.createArrayNode();
//		Shayad bayad mostaghim az Course, ObjectNode sakht (ba objectMapper)
		List<Course> coursesList = Arrays.asList(courses.values().toArray(new Course[0]));
		coursesList.sort(Comparator.comparing(Course::getName));

		for (Course course : coursesList) {
			ObjectNode courseData = this.objectMapper.createObjectNode();
			courseData.put("code", course.getCode());
			courseData.put("name", course.getName());
			courseData.put("Instructor", course.getInstructor());

			answerData.add(courseData);
		}

		return answerData;
	}

	private ObjectNode getOffering(JsonNode jsonInput) throws Exception {
		if (!students.containsKey(jsonInput.get("StudentId").asInt()))
			throw new Exception("StudentNotFound");

		Course course = courses.get(jsonInput.get("code").asInt());
		if (course == null)
			throw new Exception("OfferingNotFound");

//		todo Shayad bayad mostaghim az Course, ObjectNode sakht (ba objectMapper)
		ObjectNode answerData = this.objectMapper.createObjectNode();
		answerData.put("code", course.getCode());
		answerData.put("name", course.getName());
		answerData.put("Instructor", course.getInstructor());
		answerData.put("units", course.getUnits());
//		answerData.put("classTime", course.getUnits());
//		answerData.put("examTime", course.getUnits());
		answerData.put("capacity", course.getCapacity());
		return answerData;
	}

	private ObjectNode addToWeeklySchedule(JsonNode jsonInput) throws Exception {
		Student student = students.get(jsonInput.get("code").asInt());
		if (student == null)
			throw new Exception("StudentNotFound");

		Course course = courses.get(jsonInput.get("code").asInt());
		if (course == null)
			throw new Exception("OfferingNotFound");

		student.addCourse(course);
		return this.objectMapper.createObjectNode();
	}

	private ObjectNode removeFromWeeklySchedule(JsonNode jsonInput) throws Exception {
		Student student = students.get(jsonInput.get("code").asInt());
		if (student == null)
			throw new Exception("StudentNotFound");

		Course course = courses.get(jsonInput.get("code").asInt());
		if (course == null)
			throw new Exception("OfferingNotFound");

		student.removeCourse(course);
		return this.objectMapper.createObjectNode();
	}

	private ObjectNode getWeeklySchedule(JsonNode jsonInput) throws Exception {
		Student student = students.get(jsonInput.get("code").asInt());
		if (student == null)
			throw new Exception("StudentNotFound");

		ObjectNode answerData = this.objectMapper.createObjectNode();
		ArrayNode weeklySchedule = this.objectMapper.createArrayNode();

		Map<Integer, SelectedCourse> courses = student.getCourses();
		List<SelectedCourse> coursesList = Arrays.asList(courses.values().toArray(new SelectedCourse[0]));

		for (SelectedCourse selectedCourse : coursesList) {
			ObjectNode courseData = this.objectMapper.createObjectNode();
			courseData.put("code", selectedCourse.getCourse().getCode());
			courseData.put("name", selectedCourse.getCourse().getName());
//			answerData.put("classTime", course.getUnits());
//			answerData.put("examTime", course.getUnits());
			// state

			weeklySchedule.add(courseData);
		}

		answerData.set("weeklySchedule", weeklySchedule);
		return answerData;
	}

	private void checkFinalizing(Student student) throws MultiException {
		MultiException exception = new MultiException();

		Map<Integer, SelectedCourse> courses = student.getCourses();
		List<SelectedCourse> coursesList = Arrays.asList(courses.values().toArray(new SelectedCourse[0]));

		int selectedUnits = student.getSelectedUnits();
		if (selectedUnits < 12)
			exception.addMessage("MinimumUnitsError");

		if (selectedUnits > 20)
			exception.addMessage("MaximumUnitsError");

		for (int i = 0; i < coursesList.size(); i++) {
			if ((coursesList.get(i).getState() == CourseState.NON_FINALIZED) &&
					(coursesList.get(i).getCourse().getNumberOfStudents() >= coursesList.get(i).getCourse().getCapacity()))
				exception.addMessage("CapacityError " + coursesList.get(i).getCourse().getCode());

			// Checking Conflicts.
			for (int j = 0; j < coursesList.size(); j++) {
				if (i != j) {
					// Check Class Time Conflict.
					if (coursesList.get(i).getCourse().getClassTime().overlaps(coursesList.get(j).getCourse().getClassTime()))
						exception.addMessage("ClassTimeCollisionError " + coursesList.get(i).getCourse().getCode()
								+ " " + coursesList.get(j).getCourse().getCode());

					// Check Exam Time Conflict.
					if (coursesList.get(i).getCourse().getExamTime().overlaps(coursesList.get(j).getCourse().getExamTime()))
						exception.addMessage("ExamTimeCollisionError " + coursesList.get(i).getCourse().getCode()
								+ " " + coursesList.get(j).getCourse().getCode());
				}
			}
		}

		if (exception.getHasError())
			throw exception;
	}

	private ObjectNode finalize(JsonNode json) throws Exception {
		Student student = students.get(json.get("code").asInt());
		if (student == null)
			throw new Exception("StudentNotFound");

		checkFinalizing(student);
		student.finalizeCourses();

		return this.objectMapper.createObjectNode();
	}
}

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

	public Bolbolestan() {
		this.courses = new HashMap<>();
		this.students = new HashMap<>();
	}

	public void execute(String command, JsonNode data, ObjectMapper objectMapper) {
		// Call Command Handler.
		JsonNode jsonAnswer = switch (command) {
			case "addOffering" -> this.addOffering(data, objectMapper);
			case "addStudent" -> this.addStudent(data, objectMapper);
			case "getOfferings" -> this.getOfferings(data, objectMapper);
			case "getOffering" -> this.getOffering(data, objectMapper);
			case "addToWeeklySchedule" -> this.addToWeeklySchedule(data, objectMapper);
			case "removeFromWeeklySchedule" -> this.removeFromWeeklySchedule(data, objectMapper);
			case "getWeeklySchedule" -> this.getWeeklySchedule(data, objectMapper);
			case "finalize" -> this.finalize(data, objectMapper);
			default -> objectMapper.createObjectNode();
		};

		// Print Output.
		try {
			System.out.println(objectMapper.writeValueAsString(jsonAnswer));
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private ObjectNode addOffering(JsonNode json, ObjectMapper objectMapper) {
		ObjectNode answer = objectMapper.createObjectNode();
		answer.put("success", true);
		answer.set("data", objectMapper.createObjectNode());

		JsonNode classTimeNode = json.with("classTime");
		String[] days = objectMapper.convertValue(classTimeNode.withArray("days"), String[].class);
		JsonNode examTimeNode = json.get("examTime");

		int code = json.get("code").asInt();
		String name = json.get("name").asText();
		String instructor = json.get("Instructor").asText();
		int units = json.get("units").asInt();
		ClassTime classTime = new ClassTime(days, classTimeNode.get("time").asText());
		LocalDate[] examTime = {LocalDate.parse(examTimeNode.get("start").asText(), DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDate.parse(examTimeNode.get("end").asText(), DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss"))};
		int capacity = json.get("capacity").asInt();
		String[] prerequisites = objectMapper.convertValue(json.withArray("prerequisites"), String[].class);

		this.courses.put(code, new Course(code, name, instructor, units, classTime, examTime, capacity, prerequisites));

		return answer;
	}

	private ObjectNode addStudent(JsonNode json, ObjectMapper objectMapper) {
		ObjectNode answer = objectMapper.createObjectNode();
		answer.put("success", true);
		answer.set("data", objectMapper.createObjectNode());

		System.out.println("adding student");
		Student newStudent = new Student(json.get("studentId").asInt(), json.get("name").asText(), Year.of(json.get("enteredAt").asInt()));
		this.students.put(json.get("studentId").asInt(), newStudent);

		return answer;
	}

	private ObjectNode getOfferings(JsonNode json, ObjectMapper objectMapper) {
		ObjectNode answer = objectMapper.createObjectNode();
		answer.put("success", true);
		ArrayNode answerData = objectMapper.createArrayNode();

//		Shayad bayad mostaghim az Course, ObjectNode sakht (ba objectMapper)
		List<Course> coursesList = Arrays.asList(courses.values().toArray(new Course[0]));
		coursesList.sort(Comparator.comparing(Course::getName));
		for (Course course : coursesList) {
			ObjectNode courseData = objectMapper.createObjectNode();
			courseData.put("code", course.getCode());
			courseData.put("name", course.getName());
			courseData.put("Instructor", course.getInstructor());

			answerData.add(courseData);
		}

		answer.set("data", answerData);
		return answer;
	}

	private ObjectNode getOffering(JsonNode json, ObjectMapper objectMapper) {
		ObjectNode answer = objectMapper.createObjectNode();

//		Shayad bayad mostaghim az Course, ObjectNode sakht (ba objectMapper)
		Course course = courses.get(json.get("code").asInt());
		if (course != null) {
			answer.put("success", true);
			ObjectNode answerData = objectMapper.createObjectNode();
			answerData.put("code", course.getCode());
			answerData.put("name", course.getName());
			answerData.put("Instructor", course.getInstructor());
			answerData.put("units", course.getUnits());
//				answerData.put("classTime", course.getUnits());
//				answerData.put("examTime", course.getUnits());
			answerData.put("capacity", course.getCapacity());

			answer.set("data", answerData);
			return answer;
		}
//		shayad OfferingNotFound bayad json bashe.
		answer.put("success", false);
		answer.put("error", "OfferingNotFound");
		return answer;
	}

	private ObjectNode addToWeeklySchedule(JsonNode json, ObjectMapper objectMapper) {
		ObjectNode answer = objectMapper.createObjectNode();
		Course course = courses.get(json.get("code").asInt());
		if (course == null) {
			answer.put("success", false);
			answer.put("error", "OfferingNotFound");
			return answer;
		}

		Student student = students.get(json.get("code").asInt());
		if (student == null) {
			answer.put("success", false);
			answer.put("error", "StudentNotFound");
			return answer;
		}

		student.addCourse(course);
		answer.put("success", true);
		answer.set("data", objectMapper.createObjectNode());
		return answer;
	}

	private ObjectNode removeFromWeeklySchedule(JsonNode json, ObjectMapper objectMapper) {
		ObjectNode answer = objectMapper.createObjectNode();
		Course course = courses.get(json.get("code").asInt());
		if (course == null) {
			answer.put("success", false);
			answer.put("error", "OfferingNotFound");
			return answer;
		}

		Student student = students.get(json.get("code").asInt());
		if (student == null) {
			answer.put("success", false);
			answer.put("error", "StudentNotFound");
			return answer;
		}

		student.removeCourse(course);
		answer.put("success", true);
		answer.set("data", objectMapper.createObjectNode());
		return answer;
	}

	private ObjectNode getWeeklySchedule(JsonNode json, ObjectMapper objectMapper) {
		ObjectNode answer = objectMapper.createObjectNode();

		return answer;
	}

	private ObjectNode finalize(JsonNode json, ObjectMapper objectMapper) {
		ObjectNode answer = objectMapper.createObjectNode();
		answer.put("success", true);
		answer.put("data", objectMapper.createObjectNode());
		return answer;
	}
}

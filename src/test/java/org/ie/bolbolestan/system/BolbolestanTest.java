package org.ie.bolbolestan.system;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.ie.bolbolestan.entity.ClassTime;
import org.ie.bolbolestan.entity.Course;
import org.ie.bolbolestan.entity.ExamTime;
import org.ie.bolbolestan.entity.Student;
import org.ie.bolbolestan.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BolbolestanTest {

	Bolbolestan bolbolestan;
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		bolbolestan = new Bolbolestan();
		objectMapper = new ObjectMapper();
	}

	@AfterEach
	void tearDown() {
		bolbolestan = null;
	}

	@Test
	void addCourseShouldProperlyAddNewCourse() throws CourseAlreadyExistsException {
//		final String days[] = {"Sunday", "Tuesday"};
//		ClassTime classTime = new ClassTime(days, "16-17:30");
//		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
//				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
//		String[] prerequisites = {};
//		int code = 1;
//		Course course = new Course(code, "Internet Engineering", "Ehsan Khamespanah", 3, classTime, examTime, 100, prerequisites);
//
//		bolbolestan.addCourse(course);
//
//		List<Course> courses = bolbolestan.getCourses();
//
//		assertEquals(courses.size(), 1);
//		assertEquals(courses.get(0), course);
	}

	@Test
	void addStudentShouldProperlyAddNewStudentFromAnInstance() throws StudentAlreadyExistsException {
//		String studentId = "810111111";
//		Student student = new Student(studentId, "student", Year.of(1999));
//
//		bolbolestan.addStudent(student);
//
//		List<Student> students = bolbolestan.getStudents();
//
//		assertEquals(students.size(), 1);
//		assertEquals(students.get(0), student);
	}

	@Test
	void getOfferingsShouldThrowExceptionIfTheStudentDoesNotFound() {
//		ObjectNode data = objectMapper.createObjectNode();
//		data.put("StudentId", 1);
//
//		assertThrows(StudentNotFoundException.class, () -> {
//			bolbolestan.getOfferings(data);
//		});
	}

	@Test
	void getOfferingShouldThrowExceptionIfTheStudentDoesNotFound() throws CourseAlreadyExistsException {
//		ObjectNode data = objectMapper.createObjectNode();
//		data.put("StudentId", 1);
//		data.put("code", 1);
//		final String days[] = {"Sunday", "Tuesday"};
//		ClassTime classTime = new ClassTime(days, "16-17:30");
//		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
//				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
//		String[] prerequisites = {};
//		Course course = new Course(1, "Internet Engineering", "Ehsan Khamespanah", 3, classTime, examTime, 100, prerequisites);
//
//		bolbolestan.addCourse(course);
//
//		assertThrows(StudentNotFoundException.class, () -> {
//			bolbolestan.getOffering(data);
//		});
	}

	@Test
	void getOfferingShouldThrowExceptionIfTheCourseDoesNotFound() throws StudentAlreadyExistsException {
//		int studentId = 810111111;
//		Student student = new Student(studentId, "student", Year.of(1999));
//		bolbolestan.addStudent(student);
//		ObjectNode data = objectMapper.createObjectNode();
//		data.put("StudentId", student.getId());
//		data.put("code", 1);
//
//		assertThrows(OfferingNotFoundException.class, () -> {
//			bolbolestan.getOffering(data);
//		});
	}

	@Test
	void getOfferingsShouldProperlyReturnJsonArray() throws Exception {
//		final String days[] = {"Sunday", "Tuesday"};
//		ClassTime classTime = new ClassTime(days, "16-17:30");
//		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
//				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
//		String[] prerequisites = {};
//		Course course = new Course(1, "Internet Engineering", "Ehsan Khamespanah", 3, classTime, examTime, 100, prerequisites);
//
//		bolbolestan.addCourse(course);
//
//		int studentId = 810111111;
//		Student student = new Student(studentId, "student", Year.of(1999));
//
//		bolbolestan.addStudent(student);
//
//		ObjectNode inputData = objectMapper.createObjectNode();
//		inputData.put("StudentId", student.getId());
//
//		ObjectNode expectedData = objectMapper.createObjectNode();
//		expectedData.put("code", course.getCode());
//		expectedData.put("name", course.getName());
//		expectedData.put("Instructor", course.getInstructor());
//		ArrayNode expectedArray = objectMapper.createArrayNode();
//		expectedArray.add(expectedData);
//
//		ArrayNode resultArray = bolbolestan.getOfferings((JsonNode) inputData);
//
//		assertEquals(expectedArray, resultArray);
	}

	@Test
	void getOfferingShouldProperlyReturnJsonObject() throws Exception {
//		final String days[] = {"Sunday", "Tuesday"};
//		ClassTime classTime = new ClassTime(days, "16-17:30");
//		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00",
//				DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
//				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
//		String[] prerequisites = {};
//		Course course = new Course(1, "Internet Engineering", "Ehsan Khamespanah", 3,
//				classTime, examTime, 100, prerequisites);
//
//		bolbolestan.addCourse(course);
//
//		int studentId = 810111111;
//		Student student = new Student(studentId, "student", Year.of(1999));
//		bolbolestan.addStudent(student);
//
//		ObjectNode inputData = objectMapper.createObjectNode();
//		inputData.put("StudentId", student.getId());
//		inputData.put("code", course.getCode());
//
//		ObjectNode expectedData = objectMapper.createObjectNode();
//		expectedData.put("code", course.getCode());
//		expectedData.put("name", course.getName());
//		expectedData.put("Instructor", course.getInstructor());
//		expectedData.put("units", course.getUnits());
//		expectedData.set("classTime", course.getClassTime().getJsonInfo());
//		expectedData.set("examTime", course.getExamTime().getJsonInfo());
//		expectedData.put("capacity", course.getCapacity());
//		ArrayNode prerequisitesJson = objectMapper.valueToTree(course.getPrerequisites());
//		expectedData.set("prerequisites", prerequisitesJson);
//
//		ObjectNode resultArray = bolbolestan.getOffering((JsonNode) inputData);
//
//		assertEquals(expectedData, resultArray);
	}

	@Test
	void finalizeShouldThrowExceptionIfSumOfUnitsIsUnder12() throws StudentAlreadyExistsException, StudentNotFoundException {
//		int studentId = 810111111;
//		Student student = new Student(studentId, "student", Year.of(1999));
//		bolbolestan.addStudent(student);
//		ObjectNode data = objectMapper.createObjectNode();
//		data.put("StudentId", student.getId());
//
//		try {
//			bolbolestan.finalize(data);
//			fail();
//		} catch (MultiException exception) {
//			assertEquals(MinimumUnitsException.class, exception.getExceptions().get(0).getClass());
//		}
	}
}
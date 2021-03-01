package org.ie.bolbolestan.system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.ie.bolbolestan.entity.ClassTime;
import org.ie.bolbolestan.entity.Course;
import org.ie.bolbolestan.entity.ExamTime;
import org.ie.bolbolestan.entity.Student;
import org.ie.bolbolestan.exception.CourseAlreadyExistsException;
import org.ie.bolbolestan.exception.StudentAlreadyExistsException;
import org.ie.bolbolestan.exception.StudentNotFoundException;
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

	@BeforeEach
	void setUp() {
		bolbolestan = new Bolbolestan();
	}

	@AfterEach
	void tearDown() {
	}

	@Test
	void addOfferingShouldProperlyAddNewOffering() throws CourseAlreadyExistsException {
		final String days[] = {"Sunday", "Tuesday"};
		ClassTime classTime = new ClassTime(days, "16-17:30");
		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
		String[] prerequisites = {};
		int code = 1;
		Course course = new Course(code, "Internet Engineering", "Ehsan Khamespanah", 3, classTime, examTime, 100, prerequisites);

		bolbolestan.addCourse(course);

		List<Course> courses = bolbolestan.getCourses();

		assertEquals(courses.size(), 1);
		assertEquals(courses.get(0), course);
	}

	@Test
	void addStudentShouldProperlyAddNewStudent() throws StudentAlreadyExistsException {
		int studentId = 810111111;
		Student student = new Student(studentId, "student", Year.of(1999));

		bolbolestan.addStudent(student);

		List<Student> students = bolbolestan.getStudents();

		assertEquals(students.size(), 1);
		assertEquals(students.get(0), student);
	}

	@Test
	void getOfferingsShouldThrowExceptionBecauseTheStudentDoesNotFound() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode data = objectMapper.createObjectNode();
		data.put("StudentId", 1);

		bolbolestan.getOfferings(data);

//		exception.expect(StudentNotFoundException.class);
	}
}
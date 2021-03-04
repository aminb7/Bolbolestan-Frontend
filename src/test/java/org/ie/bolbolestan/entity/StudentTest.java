package org.ie.bolbolestan.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

	@Test
	public void addCourseShouldProperlyAddNewCourse() {
//		Student student = new Student(2, "student1", Year.of(1999));
//
//		final String days[] = {"Sunday", "Tuesday"};
//		ClassTime classTime = new ClassTime(days, "16-17:30");
//		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00",
//				DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
//				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
//		String[] prerequisites = {};
//		Course course = new Course(1, "Internet Engineering", "Ehsan Khamespanah", 3,
//				classTime, examTime, 100, prerequisites);
//
//		student.addCourse(course);
//
//		assertFalse(student.getCourses().isEmpty());
	}

	@Test
	public void removeCourseShouldProperlyRemoveNewCourse() {
//		Student student = new Student(2, "student1", Year.of(1999));
//
//		final String days[] = {"Sunday", "Tuesday"};
//		ClassTime classTime = new ClassTime(days, "16-17:30");
//		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00",
//				DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
//				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
//		String[] prerequisites = {};
//		Course course = new Course(1, "Internet Engineering", "Ehsan Khamespanah", 3,
//				classTime, examTime, 100, prerequisites);
//
//		student.addCourse(course);
//		student.removeCourse(course);
//
//		assertTrue(student.getCourses().isEmpty());
	}

	@Test
	public void finalizeCoursesShouldProperlyChangeStateOfAllCourses() {
//		Student student = new Student(2, "student1", Year.of(1999));
//
//		final String days[] = {"Sunday", "Tuesday"};
//		ClassTime classTime = new ClassTime(days, "16-17:30");
//		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00",
//				DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
//				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
//		String[] prerequisites = {};
//		Course course1 = new Course(1, "Internet Engineering", "Ehsan Khamespanah", 3,
//				classTime, examTime, 100, prerequisites);
//		Course course2 = new Course(2, "Internet Engineering", "Ehsan Khamespanah", 3,
//				classTime, examTime, 100, prerequisites);
//		Course course3 = new Course(3, "Internet Engineering", "Ehsan Khamespanah", 3,
//				classTime, examTime, 100, prerequisites);
//
//		student.addCourse(course1);
//		student.addCourse(course2);
//		student.addCourse(course3);
//
//		student.finalizeCourses();
//
//		Map<Integer, SelectedCourse> courses = student.getCourses();
//
//		for (Map.Entry<Integer, SelectedCourse> entry : courses.entrySet())
//			assertSame(entry.getValue().getState(), CourseState.FINALIZED);
//
//		assertEquals(course1.getNumberOfStudents(), 1);
//		assertEquals(course2.getNumberOfStudents(), 1);
//		assertEquals(course3.getNumberOfStudents(), 1);
	}
}

package org.ie.bolbolestan.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class CourseTest {

	@Test
	public void getJsonSummaryShouldProperlyGivesJsonObject() {
		final String days[] = {"Sunday", "Tuesday"};
		ClassTime classTime = new ClassTime(days, "16-17:30");
		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
		String[] prerequisites = {};
		int code = 1;
		String name = "Internet Engineering";
		String instructor = "Ehsan Khamespanah";
		Course course = new Course(code, name, instructor, 3, classTime, examTime, 100, prerequisites);

		ObjectNode jsonNode = course.getJsonSummary();

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode expectedJsonNode = objectMapper.createObjectNode();
		expectedJsonNode.put("code", code);
		expectedJsonNode.put("name", name);
		expectedJsonNode.put("Instructor", instructor);

		assertEquals(expectedJsonNode, jsonNode);
	}

	@Test
	public void getJsonFullInfoShouldProperlyGivesJsonObject() {
		final String days[] = {"Sunday", "Tuesday"};
		ClassTime classTime = new ClassTime(days, "16-17:30");
		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
		String[] prerequisites = {};
		int code = 1;
		String name = "Internet Engineering";
		String instructor = "Ehsan Khamespanah";
		int units = 3;
		int capacity = 100;
		Course course = new Course(code, name, instructor, units, classTime, examTime, capacity, prerequisites);

		ObjectNode jsonNode = course.getJsonFullInfo();

		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode expectedJsonNode = objectMapper.createObjectNode();
		expectedJsonNode.put("code", code);
		expectedJsonNode.put("name", name);
		expectedJsonNode.put("Instructor", instructor);
		expectedJsonNode.put("units", units);
		expectedJsonNode.set("classTime", classTime.getJsonInfo());
		expectedJsonNode.set("examTime", examTime.getJsonInfo());
		expectedJsonNode.put("capacity", capacity);
		ArrayNode prerequisitesNode = objectMapper.valueToTree(prerequisites);
		expectedJsonNode.set("prerequisites", prerequisitesNode);

		assertEquals(expectedJsonNode, jsonNode);
	}

	@Test
	public void getJsonFullInfoShouldProperlyGivesJsonObjectShouldProperlyIncreamentNumberOfStudents() {
		final String days[] = {"Sunday", "Tuesday"};
		ClassTime classTime = new ClassTime(days, "16-17:30");
		ExamTime examTime = new ExamTime(LocalDateTime.parse("2020-9-01T16:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2020-9-01T16:30:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
		String[] prerequisites = {};
		Course course = new Course(1, "Internet Engineering", "Ehsan Khamespanah", 3, classTime, examTime, 100, prerequisites);

		course.incrementNumOfStudents();

		assertEquals(course.getNumberOfStudents(), 1);
	}
}

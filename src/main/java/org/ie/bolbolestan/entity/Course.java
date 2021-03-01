package org.ie.bolbolestan.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Objects;

public class Course {

	private final int code;
	private final String name;
	private final String instructor;
	private final int units;
	private final ClassTime classTime;
	private final ExamTime examTime;
	private final int capacity;
	private final String[] prerequisites;
	private int numberOfStudents;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Course course = (Course) o;
		return code == course.code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	public Course(int code, String name, String instructor, int units, ClassTime classTime, ExamTime examTime,
	              int capacity, String[] prerequisites) {
		this.code = code;
		this.name = name;
		this.instructor = instructor;
		this.units = units;
		this.classTime = classTime;
		this.examTime = examTime;
		this.capacity = capacity;
		this.prerequisites = prerequisites;
		this.numberOfStudents = 0;
	}

	public String getName() {
		return this.name;
	}

	public int getCode() {
		return this.code;
	}

	public int getUnits() {
		return this.units;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getNumberOfStudents() {return this.numberOfStudents; }

	public ClassTime getClassTime() {
		return classTime;
	}

	public ExamTime getExamTime() {
		return examTime;
	}

	public ObjectNode getJsonSummary() {
		ObjectNode result = new ObjectMapper().createObjectNode();
		result.put("code", code);
		result.put("name", name);
		result.put("Instructor", instructor);
		return result;
	}

	public ObjectNode getJsonFullInfo() {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode result = objectMapper.createObjectNode();
		result.put("code", this.code);
		result.put("name", this.name);
		result.put("Instructor", this.instructor);
		result.put("units", this.units);
		result.set("classTime", this.classTime.getJsonInfo());
		result.set("examTime", this.examTime.getJsonInfo());
		result.put("capacity", this.capacity);
		ArrayNode prerequisites = objectMapper.valueToTree(this.prerequisites);
		result.set("prerequisites", prerequisites);
		return result;
	}
}

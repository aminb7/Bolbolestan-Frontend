package org.ie.bolbolestan.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Objects;

public class Course {
	private final String code;
	private final String classCode;
	private final String name;
	private final int units;
	private final String type;
	private final String instructor;
	private final int capacity;
	private final String[] prerequisites;
	private final ClassTime classTime;
	private final ExamTime examTime;

	private int numberOfStudents;

	@JsonCreator
	public Course(@JsonProperty("code") String code, @JsonProperty("classCode") String classCode,
	              @JsonProperty("name") String name, @JsonProperty("units") int units,
	              @JsonProperty("type") String type, @JsonProperty("instructor") String instructor,
	              @JsonProperty("capacity") int capacity, @JsonProperty("prerequisites") String[] prerequisites,
	              @JsonProperty("classTime") ClassTime classTime, @JsonProperty("examTime") ExamTime examTime) {
		this.code = code;
		this.classCode = classCode;
		this.name = name;
		this.units = units;
		this.type = type;
		this.instructor = instructor;
		this.capacity = capacity;
		this.prerequisites = prerequisites;
		this.classTime = classTime;
		this.examTime = examTime;
	}

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

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public String getInstructor() {
		return instructor;
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

	public String[] getPrerequisites() {
		return prerequisites;
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

	public void incrementNumOfStudents() {
		numberOfStudents += 1;
	}
}

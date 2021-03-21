package com.example.CA3.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public String getClassCode() {
		return classCode;
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

	public void incrementNumOfStudents() {
		numberOfStudents += 1;
	}

	public void decrementNumOfStudents() {
		numberOfStudents += 1;
	}

	public String getType() {
		return type;
	}

	public String getHtmlTable() {
		String result = "<td>" + this.code + "</td>"
				+ "<td>" + this.classCode + "</td>"
				+ "<td>" + this.name + "</td>"
				+ "<td>" + this.units + "</td>"
				+ "<td>" + this.capacity + "</td>"
				+ "<td>" + this.type + "</td>"
				+ this.classTime.getHtmlTable()
				+ this.examTime.getHtmlTable()
				+ "<td>" + String.join("|", this.prerequisites) + "</td>"
				+ "<td><a href=\"/course/" + this.code + "/" + this.classCode + "\">Link</a></td>";
		return result;
	}
}

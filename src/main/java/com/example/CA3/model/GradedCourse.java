package com.example.CA3.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GradedCourse {
	final private String code;
	final private int grade;
	private Course course;

	@JsonCreator
	public GradedCourse(@JsonProperty("code") String code, @JsonProperty("grade") int grade) {
		this.code = code;
		this.grade = grade;
	}

	public String getCode() {
		return code;
	}

	public int getGrade() {
		return grade;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}

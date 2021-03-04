package org.ie.bolbolestan.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PassedCourse {
	final private String code;
	final private String grade;

	@JsonCreator
	public PassedCourse(@JsonProperty("code") String code, @JsonProperty("grade") String grade) {
		this.code = code;
		this.grade = grade;
	}

	public String getCode() {
		return code;
	}

	public String getGrade() {
		return grade;
	}
}

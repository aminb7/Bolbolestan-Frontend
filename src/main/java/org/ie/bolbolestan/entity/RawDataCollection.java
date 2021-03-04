package org.ie.bolbolestan.entity;

import org.ie.bolbolestan.utility.HttpGetter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RawDataCollection {
	String Courses;
	String students;
	Map<String, String> grades;

	final String coursesURI = "http://138.197.181.131:5000/api/courses";
	final String studentsURI = "http://138.197.181.131:5000/api/students";
	final String gradesURI = "http://138.197.181.131:5000/api/grades";

	public RawDataCollection() {
		this.Courses = "";
		this.students = "";
		this.grades = new HashMap<>();
	}

	public void requestCourses() throws IOException, InterruptedException {
		this.Courses = HttpGetter.Get(coursesURI);
	}

	public void requestStudents() throws IOException, InterruptedException {
		this.students = HttpGetter.Get(studentsURI);
	}

	public void requestGrades(List<String> studentIdList) throws IOException, InterruptedException {
		for (String StudentId : studentIdList) {
			String currentStudentGrades = HttpGetter.Get(gradesURI + "/" + StudentId);
			this.grades.put(StudentId, currentStudentGrades);
		}
	}

	public String getCourses() {
		return Courses;
	}

	public String getStudents() {
		return students;
	}

	public Map<String, String> getGrades() {
		return grades;
	}
}

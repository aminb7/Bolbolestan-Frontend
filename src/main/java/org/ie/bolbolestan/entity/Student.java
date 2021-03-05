package org.ie.bolbolestan.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Student {
	private final String id;
	private final String name;
	private final String secondName;
	private final String birthDate;

	private Map<String, SelectedCourse> selectedCourses;
	private Map<String, GradedCourse> gradedCourses;

	@JsonCreator
	public Student(@JsonProperty("id") String id, @JsonProperty("name") String name,
	               @JsonProperty("secondName") String secondName, @JsonProperty("birthDate") String birthDate) {
		this.id = id;
		this.name = name;
		this.secondName = secondName;
		this.birthDate = birthDate;
		selectedCourses = new HashMap<>();
		gradedCourses = new HashMap<>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSecondName() {
		return secondName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void addCourse(Course course) {
		selectedCourses.put(course.getCode(), new SelectedCourse(course));
	}

	public void removeCourse(Course course) {
		selectedCourses.remove(course.getCode());
	}

	public Map<String, SelectedCourse> getSelectedCourses() {
		return selectedCourses;
	}

	public int getSelectedUnits() {
		SelectedCourse[] coursesList = selectedCourses.values().toArray(new SelectedCourse[0]);

		int selectedUnits = 0;
		for (SelectedCourse course : coursesList) {
			selectedUnits = selectedUnits + course.getCourse().getUnits();
		}

		return selectedUnits;
	}

	public Map<String, GradedCourse> getGradedCourses() {
		return gradedCourses;
	}

	public void setGradedCourses(GradedCourse[] gradedCourses) {
		List.of(gradedCourses).forEach(gradedCourse -> this.gradedCourses.put(gradedCourse.getCode(), gradedCourse));
	}

	public void finalizeCourses() {
		for (Map.Entry<String, SelectedCourse> entry : selectedCourses.entrySet()){
			if (entry.getValue().getState() == CourseState.NON_FINALIZED)
				entry.getValue().getCourse().incrementNumOfStudents();
			entry.getValue().setState(CourseState.FINALIZED);
		}
	}

	public int getTPU() {
		int result = 0;

		for (GradedCourse course : new ArrayList<>(gradedCourses.values())) {
			if (course.getGrade() >= 10)
				result += course.getCourse().getUnits();
		}

		return result;
	}

	public float getGPA() {
		float result = 0;
		int unitsSum = 0;

		for (GradedCourse course : new ArrayList<>(gradedCourses.values())) {
			int units = course.getCourse().getUnits();
			result += course.getGrade() * units;
			unitsSum += units;
		}

		return result / unitsSum;
	}
}

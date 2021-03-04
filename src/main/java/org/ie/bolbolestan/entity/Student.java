package org.ie.bolbolestan.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
	private final String id;
	private final String name;
	private final String secondName;
	private final String birthDate;

	private Map<String, SelectedCourse> selectedCourses;
	private Map<String, PassedCourse> passedCourses;

	@JsonCreator
	public Student(@JsonProperty("id") String id, @JsonProperty("name") String name,
	               @JsonProperty("secondName") String secondName, @JsonProperty("birthDate") String birthDate) {
		this.id = id;
		this.name = name;
		this.secondName = secondName;
		this.birthDate = birthDate;
		selectedCourses = new HashMap<>();
		passedCourses = new HashMap<>();
	}

	public String getId() {
		return id;
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

	public void setPassedCourses(PassedCourse[] passedCourses) {
		List.of(passedCourses).forEach(passedCourse -> this.passedCourses.put(passedCourse.getCode(), passedCourse));
	}

	public void finalizeCourses() {
		for (Map.Entry<String, SelectedCourse> entry : selectedCourses.entrySet()){
			if (entry.getValue().getState() == CourseState.NON_FINALIZED)
				entry.getValue().getCourse().incrementNumOfStudents();
			entry.getValue().setState(CourseState.FINALIZED);
		}
	}
}

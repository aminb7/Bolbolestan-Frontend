package org.ie.bolbolestan.entity;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class Student {

	private final int studentId;
	private final String name;
	private final Year enteredAt;
	private final Map<Integer, SelectedCourse> courses;

	public Student(int studentId, String name, Year enteredAt) {
		this.studentId = studentId;
		this.name = name;
		this.enteredAt = enteredAt;
		this.courses = new HashMap<>();
	}

	public void addCourse(Course course) {
		courses.put(course.getCode(), new SelectedCourse(course, CourseState.NON_FINALIZED));
	}

	public void removeCourse(Course course) {
		courses.remove(course.getCode());
	}

	public Map<Integer, SelectedCourse> getCourses() {
		return courses;
	}

	public int getSelectedUnits() {
		SelectedCourse[] coursesList = courses.values().toArray(new SelectedCourse[0]);

		int selectedUnits = 0;
		for (SelectedCourse course : coursesList) {
			selectedUnits = selectedUnits + course.getCourse().getUnits();
		}

		return selectedUnits;
	}

	public void finalizeCourses() {
		for (Map.Entry<Integer, SelectedCourse> entry : courses.entrySet()){
			entry.getValue().setState(CourseState.FINALIZED);
			entry.getValue().getCourse().incrementNumOfStudents();
		}
	}
}

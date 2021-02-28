import java.time.Year;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {

	private int studentId;
	private String name;
	private Year enteredAt;
	private Map<Integer, SelectedCourse> courses;

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
		List<SelectedCourse> coursesList = Arrays.asList(courses.values().toArray(new SelectedCourse[0]));

		int selectedUnits = 0;
		for (SelectedCourse course : coursesList) {
			selectedUnits = selectedUnits + course.getCourse().getUnits();
		}

		return selectedUnits;
	}

	public void finalizeCourses() {
		for (Map.Entry<Integer, SelectedCourse> entry : courses.entrySet()){
			entry.getValue().setState(CourseState.FINALIZED);
		}
	}
}

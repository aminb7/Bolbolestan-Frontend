import java.time.Year;
import java.util.HashMap;
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
}

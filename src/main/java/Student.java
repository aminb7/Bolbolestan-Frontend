import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class Student {

	private int studentId;
	private String name;
	private Year enteredAt;
	private Map<Course, CourseState> courses;

	public Student(int studentId, String name, Year enteredAt) {
		this.studentId = studentId;
		this.name = name;
		this.enteredAt = enteredAt;
		this.courses = new HashMap<>();
	}
}

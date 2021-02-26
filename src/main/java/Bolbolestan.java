import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;

public class Bolbolestan {
	private List<Course> courses;
	private List<Student> students;

	public void addStudent(int studentId, String name, Year enteredAt) {
		this.students.add(new Student(studentId, name, enteredAt));
	}

	public void addCourse(int code, String name, String instructor, int units, ClassTime classTime, LocalDate[] examTime,
	                      int capacity, String[] prerequisites) {
		this.courses.add(new Course(code, name, instructor, units, classTime, examTime, capacity, prerequisites));
	}
}

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Course {

	private int code;
	private String name;
	private String instructor;
	private int units;
	private ClassTime classTime;
	private ExamTime examTime;
	private int capacity;
	private String[] prerequisites;
	private int numberOfStudents;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Course course = (Course) o;
		return code == course.code;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	public Course(int code, String name, String instructor, int units, ClassTime classTime, ExamTime examTime,
	              int capacity, String[] prerequisites) {
		this.code = code;
		this.name = name;
		this.instructor = instructor;
		this.units = units;
		this.classTime = classTime;
		this.examTime = examTime;
		this.capacity = capacity;
		this.prerequisites = prerequisites;
		this.numberOfStudents = 0;
	}

	public String getName() {
		return this.name;
	}

	public int getCode() {
		return this.code;
	}

	public String getInstructor() {
		return this.instructor;
	}

	public int getUnits() {
		return this.units;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public int getNumberOfStudents() {return this.numberOfStudents; }

	public ClassTime getClassTime() {
		return classTime;
	}

	public ExamTime getExamTime() {
		return examTime;
	}

	public String[] getPrerequisites() {
		return prerequisites;
	}

	public void incrementNumOfStudents() {
		this.numberOfStudents = this.numberOfStudents + 1;
	}
}

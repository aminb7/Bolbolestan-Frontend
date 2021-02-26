import java.util.Date;

public class Course {

	private int code;
	private String name;
	private String instructor;
	private int units;
	private ClassTime classTime;
	private Date[] examTime;
	private int capacity;
	private String[] prerequisites;

	public Course(int code, String name, String instructor, int units, ClassTime classTime, Date[] examTime,
	              int capacity, String[] prerequisites) {
		this.code = code;
		this.name = name;
		this.instructor = instructor;
		this.units = units;
		this.classTime = classTime;
		this.examTime = examTime;
		this.capacity = capacity;
		this.prerequisites = prerequisites;
	}
}

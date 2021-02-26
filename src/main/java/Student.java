import java.time.Year;

public class Student {

	private int studentId;
	private String name;
	private Year enteredAt;

	public Student(int studentId, String name, Year enteredAt) {
		this.studentId = studentId;
		this.name = name;
		this.enteredAt = enteredAt;
	}
}

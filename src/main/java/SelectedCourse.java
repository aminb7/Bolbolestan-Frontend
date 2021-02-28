public class SelectedCourse {
	private Course course;
	private CourseState state;

	public SelectedCourse(Course course, CourseState state) {
		this.course = course;
		this.state = state;
	}

	public Course getCourse() {
		return course;
	}

	public CourseState getState() {
		return state;
	}

	public void setState(CourseState state) {this.state = state; }
}

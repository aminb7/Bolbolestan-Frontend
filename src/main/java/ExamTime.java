import java.time.LocalDate;

public class ExamTime implements EventTime {
	private LocalDate start;
	private LocalDate end;

	public ExamTime(LocalDate start, LocalDate end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public boolean overlaps(EventTime otherEventTime) {
		if (getClass() != otherEventTime.getClass())
			return false;

		ExamTime other = (ExamTime) otherEventTime;
		if (this.start.isBefore(other.end) && this.end.isAfter(other.start))
			return true;

		return false;
	}
}

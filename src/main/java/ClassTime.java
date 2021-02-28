import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClassTime implements EventTime {

	private String[] days;
	private LocalTime start;
	private LocalTime end;

	public ClassTime(String[] days, String time) {
		this.days = days;
		String[] times = time.split("-");
		this.start = LocalTime.parse(times[0], DateTimeFormatter.ofPattern("HH[:mm]"));
		this.end = LocalTime.parse(times[1], DateTimeFormatter.ofPattern("HH[:mm]"));
	}

	@Override
	public boolean overlaps(EventTime otherEventTime) {
		if (getClass() != otherEventTime.getClass())
			return false;

		ClassTime other = (ClassTime) otherEventTime;
		if (this.days[0].equals(other.days[0]) || this.days[this.days.length - 1].equals(other.days[0])
				|| this.days[0].equals(other.days[other.days.length - 1])
				|| this.days[this.days.length - 1].equals(other.days[other.days.length - 1])) {
			return true;
		}

		if (this.start.isBefore(other.end) && this.end.isAfter(other.start))
			return true;

		return false;
	}
}

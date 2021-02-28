import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;

public class ExamTime implements EventTime {
	private final LocalDateTime start;
	private final LocalDateTime end;

	public ExamTime(LocalDateTime start, LocalDateTime end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public boolean overlaps(EventTime otherEventTime) {
		if (getClass() != otherEventTime.getClass())
			return false;

		ExamTime other = (ExamTime) otherEventTime;
		return this.start.isBefore(other.end) && this.end.isAfter(other.start);
	}

	@Override
	public ObjectNode getJsonInfo() {
		ObjectNode result = new ObjectMapper().createObjectNode();
		result.put("start", this.start.toString());
		result.put("end", this.start.toString());
		return result;
	}
}

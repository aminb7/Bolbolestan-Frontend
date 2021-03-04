package org.ie.bolbolestan.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

public class ExamTime implements EventTime {
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private final LocalDateTime start;
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private final LocalDateTime end;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ExamTime(@JsonProperty("start") LocalDateTime start, @JsonProperty("end") LocalDateTime end) {
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

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}
}

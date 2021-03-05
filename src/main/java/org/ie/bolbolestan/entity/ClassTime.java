package org.ie.bolbolestan.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClassTime implements EventTime {

	private final String[] days;
	private final LocalTime start;
	private final LocalTime end;

	@JsonCreator
	public ClassTime(@JsonProperty("days") String[] days, @JsonProperty("time") String time) {
		this.days = days;
		String[] times = time.split("-");
		this.start = LocalTime.parse(times[0], DateTimeFormatter.ofPattern("H[:m]"));
		this.end = LocalTime.parse(times[1], DateTimeFormatter.ofPattern("H[:m]"));
	}

	public String[] getDays() {
		return days;
	}

	public LocalTime getStart() {
		return start;
	}

	public LocalTime getEnd() {
		return end;
	}

	@Override
	public boolean overlaps(EventTime otherEventTime) {
		if (getClass() != otherEventTime.getClass())
			return false;

		ClassTime other = (ClassTime) otherEventTime;
		if (this.days[0].equals(other.days[0]) || this.days[this.days.length - 1].equals(other.days[0])
				|| this.days[0].equals(other.days[other.days.length - 1])
				|| this.days[this.days.length - 1].equals(other.days[other.days.length - 1])) {
			return this.start.isBefore(other.end) && this.end.isAfter(other.start);
		}

		return false;
	}

	@Override
	public ObjectNode getJsonInfo() {
		ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode result = objectMapper.createObjectNode();
		ArrayNode days = objectMapper.valueToTree(this.days);
		result.set("days", days);
		result.put("time", this.start.toString() + "-" + this.end.toString());
		return result;
	}

	@Override
	public String getHtmlTable() {
		String result = "<td>";

		for (int i = 0; i < this.days.length; i++) {
			if (i > 0)
				result += "|";

			result += this.days[i];
		}

		result += "</td>";
		result += "<td>" + this.start + "-" + this.end + "</td>";

		return result;
	}

	public String getHtmlPresentationList() {
		String result = "<li id=\"days\">Days: ";

		for (int i = 0; i < this.days.length; i++) {
			if (i > 0)
				result += ", ";

			result += this.days[i];
		}

		result += "</li>";
		result += "<li id=\"time\">Time: " + this.start + "-" + this.end + "</li>";

		return result;
	}
}

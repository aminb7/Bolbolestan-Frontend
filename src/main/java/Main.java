import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Bolbolestan bolbolestan = new Bolbolestan();

		while (true) {
			String line = scanner.nextLine();
			String[] splittedLine = line.split(" ", 2);
			String command = splittedLine[0];
			String data = splittedLine[1];
			ObjectMapper objectMapper = new ObjectMapper();

			JsonNode json = null;
			try {
				json = objectMapper.readTree(data);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			if (command.equals("addOffering")) {
				JsonNode classTimeNode = json.with("classTime");
				String[] days = objectMapper.convertValue(classTimeNode.withArray("days"), String[].class);
				String[] prerequisites = objectMapper.convertValue(json.withArray("prerequisites"), String[].class);

				JsonNode examTimeNode = json.get("examTime");
				LocalDate[] examTime = {LocalDate.parse(examTimeNode.get("start").asText(), DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")), LocalDate.parse(examTimeNode.get("end").asText(), DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss"))};

				ClassTime classTime = new ClassTime(days, classTimeNode.get("time").asText());
				bolbolestan.addCourse(json.get("code").asInt(), json.get("name").asText(),
						json.get("Instructor").asText(), json.get("units").asInt(), classTime, examTime,
						json.get("capacity").asInt(), prerequisites);
			}
			else if (command.equals("addStudent")) {
				///
			}
			else if (command.equals("getOfferings")) {
				///
			}
			else if (command.equals("getOffering")) {
				///
			}
			else if (command.equals("addToWeeklySchedule")) {
				///
			}
			else if (command.equals("removeFromWeeklySchedule")) {
				///
			}
			else if (command.equals("getWeeklySchedule")) {
				///
			}
			else if (command.equals("finalize")) {
				///
			}
		}
	}
}

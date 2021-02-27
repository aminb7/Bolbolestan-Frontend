import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Year;
import java.util.Date;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Bolbolestan bolbolestan = new Bolbolestan();
		while (true) {
			System.out.println("---- while loop ----");
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

			bolbolestan.run(command, json, objectMapper);
		}
	}
}

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

			JsonNode jsonData = null;
			try {
				jsonData = objectMapper.readTree(data);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			bolbolestan.execute(command, jsonData, objectMapper);
		}
	}
}

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
			String[] splitLine = line.split(" ", 2);
			String command = splitLine[0];
			String data = splitLine[1];
			bolbolestan.execute(command, data);
		}
	}
}

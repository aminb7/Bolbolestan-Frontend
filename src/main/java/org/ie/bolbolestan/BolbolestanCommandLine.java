package org.ie.bolbolestan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.ie.bolbolestan.system.Bolbolestan;

import java.util.Scanner;

public class BolbolestanCommandLine {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Bolbolestan bolbolestan = new Bolbolestan();
		while (true) {
			String line = scanner.nextLine();
			if (line.isEmpty())
				continue;

			String[] splitLine = line.split(" ", 2);
			String command = splitLine[0];
			String data = "";

			if (splitLine.length > 1)
				data = splitLine[1];

			ObjectNode message = bolbolestan.execute(command, data);

			try {
				System.out.println(new ObjectMapper().writeValueAsString(message));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
}

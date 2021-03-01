package org.ie.bolbolestan.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class MultiException extends Exception {
	private final List<Exception> exceptions;

	public MultiException() {
		this.exceptions = new ArrayList<>();
	}

	@Override
	public String getMessage() {
		String result = "";
		List<String> messages = new ArrayList<>();
		exceptions.forEach(exception -> messages.add(exception.getMessage()));

		try {
			result = new ObjectMapper().writeValueAsString(messages);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return result;
	}

	public void addMessage(Exception exception) {
		this.exceptions.add(exception);
	}

	public boolean hasError() {
		return !exceptions.isEmpty();
	}
}

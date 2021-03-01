package org.ie.bolbolestan.exception;

public class StudentNotFoundException extends Exception {
	@Override
	public String getMessage() {
		return "StudentNotFound";
	}
}

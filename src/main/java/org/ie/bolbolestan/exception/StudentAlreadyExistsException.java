package org.ie.bolbolestan.exception;

public class StudentAlreadyExistsException extends Exception {
	@Override
	public String getMessage() {
		return "StudentAlreadyExists";
	}
}

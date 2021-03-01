package org.ie.bolbolestan.exception;

public class CourseAlreadyExistsException extends Exception {
	@Override
	public String getMessage() {
		return "CourseAlreadyExists";
	}
}

package org.ie.bolbolestan.exception;

public class ExamTimeCollisionException extends Exception {
	private final Integer code1;
	private final Integer code2;

	public ExamTimeCollisionException(Integer code1, Integer code2) {
		this.code1 = code1;
		this.code2 = code2;
	}

	@Override
	public String getMessage() {
		return "ExamTimeCollisionError " + code1 + " " + code2;
	}
}

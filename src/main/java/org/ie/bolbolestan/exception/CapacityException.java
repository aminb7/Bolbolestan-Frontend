package org.ie.bolbolestan.exception;

public class CapacityException extends Exception {
	private final Integer code;

	public CapacityException(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return "CapacityError " + code;
	}
}

package org.ie.bolbolestan.exception;

public class CommandNotFoundException extends Exception {
	@Override
	public String getMessage() {
		return "CommandNotFound";
	}
}

package org.ie.bolbolestan.exception;

public class OfferingNotFoundException extends Exception {
	@Override
	public String getMessage() {
		return "OfferingNotFound";
	}
}

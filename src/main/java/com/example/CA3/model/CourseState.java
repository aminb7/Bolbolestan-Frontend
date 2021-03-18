package com.example.CA3.model;

public enum CourseState {
	FINALIZED,
	NON_FINALIZED;

	@Override
	public String toString() {
		return switch (this) {
			case FINALIZED -> "finalized";
			case NON_FINALIZED -> "non-finalized";
			default -> "";
		};
	}
}

package org.ie.bolbolestan.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExamTimeTest {

	@Test
	public void overlapsShouldReturnTrueWhenTwoExamTimesAreSame() {
		ExamTime first = new ExamTime(LocalDateTime.parse("2021-9-01T08:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2021-9-01T09:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
		ExamTime second = new ExamTime(LocalDateTime.parse("2021-9-01T08:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2021-9-01T09:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));

		assertTrue(first.overlaps(second));
	}

	@Test
	public void overlapsShouldReturnFalseWhenTwoExamTimesHaveDifferentDaysButSameClassHours() {
		ExamTime first = new ExamTime(LocalDateTime.parse("2021-9-01T08:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2021-9-01T09:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
		ExamTime second = new ExamTime(LocalDateTime.parse("2021-9-02T08:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2021-9-02T09:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));

		assertFalse(first.overlaps(second));
	}

	@Test
	public void overlapsShouldReturnFalseWhenTwoExamTimesHaveDifferentYearsButSameClassHours() {
		ExamTime first = new ExamTime(LocalDateTime.parse("2021-9-01T08:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2021-9-01T09:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
		ExamTime second = new ExamTime(LocalDateTime.parse("2020-9-01T08:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse("2020-9-01T09:00:00", DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));

		assertFalse(first.overlaps(second));
	}

	@ParameterizedTest
	@CsvSource({"2021-9-01T16:00:00,2021-9-01T17:30:00,2021-9-01T16:00:00,2021-9-01T17:30:00",
			"2021-9-01T16:00:00,2021-9-01T17:30:00,2021-9-01T17:00:00,2021-9-01T17:30:00",
			"2021-9-01T16:00:00,2021-9-01T17:30:00,2021-9-01T15:00:00,2021-9-01T17:30:00",
			"2021-9-01T16:00:00,2021-9-01T17:30:00,2021-9-01T15:00:00,2021-9-01T17:00:00",
			"2021-9-01T16:00:00,2021-9-01T17:30:00,2021-9-01T15:00:00,2021-9-01T18:30:00",
			"2021-9-01T16:00:00,2021-9-01T17:30:00,2021-9-01T17:00:00,2021-9-01T18:30:00",
			"2021-9-01T16:00:00,2021-9-01T17:30:00,2021-9-01T16:00:00,2021-9-01T18:30:00",
			"2021-9-01T16:00:00,2021-9-01T17:30:00,2021-9-01T16:00:00,2021-9-01T17:00:00"
	})
	public void overlapsShouldReturnTrueWhenTwoExamTimesHaveOverlapInClassHours(String firstStart, String firstEnd,
	                                                                            String secondStart, String secondEnd) {
		ExamTime first = new ExamTime(LocalDateTime.parse(firstStart, DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse(firstEnd, DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
		ExamTime second = new ExamTime(LocalDateTime.parse(secondStart, DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse(secondEnd, DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));

		assertTrue(first.overlaps(second));
	}

	@ParameterizedTest
	@CsvSource({"2020-9-01T16:00:00,2020-9-01T17:30:00,2021-9-01T16:00:00,2021-9-01T17:30:00",
			"2021-10-01T16:00:00,2021-10-01T17:30:00,2021-9-01T17:00:00,2021-9-01T17:30:00",
			"2021-9-01T14:00:00,2021-9-01T15:00:00,2021-9-01T15:00:00,2021-9-01T17:30:00",
			"2021-9-02T16:00:00,2021-9-02T17:30:00,2021-9-01T15:00:00,2021-9-01T17:00:00",
			"2024-9-02T16:00:00,2024-9-02T17:30:00,2021-9-01T15:00:00,2021-9-01T17:00:00",
	})
	public void overlapsShouldReturnTrueWhenTwoExamTimesHaveNoOverlap(String firstStart, String firstEnd,
	                                                                  String secondStart, String secondEnd) {
		ExamTime first = new ExamTime(LocalDateTime.parse(firstStart, DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse(firstEnd, DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));
		ExamTime second = new ExamTime(LocalDateTime.parse(secondStart, DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")),
				LocalDateTime.parse(secondEnd, DateTimeFormatter.ofPattern("yyyy-M-d'T'HH:mm:ss")));

		assertFalse(first.overlaps(second));
	}
}

package com.it.loganalyze.log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Debug extends Log {
	public LinkedHashMap<String, String> logLine = new LinkedHashMap<>();
	private final ArrayList<String> keys = new ArrayList<>(Arrays.asList(
			"Time", "Attackname", "id", "Msg", "Severity", "Data"));

	public Debug(LinkedHashMap<String, String> line) {
		logLine = line;
	}

	@Override
	public String getField(String fieldName) {
		String value = logLine.get(fieldName);
		return value;
	}

	@Override
	public ArrayList<String> getAllField() {
		return keys;
	}

	@Override
	public LocalDateTime getDate() {
		String dateString = getField("Time");
		System.out.println(dateString);
		LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.of(0, 1));
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss.SSSSSS");
			dateTime = LocalDateTime.parse(dateString, formatter);
		} catch (DateTimeParseException e) {
			
		}
		return dateTime;
	}

	@Override
	public String getSrcIp() {
		// TODO Auto-generated method stub
		return "None";
	}

	@Override
	public ArrayList<String> getMainField() {
		ArrayList<String> mainKeys = new ArrayList<>(Arrays.asList(
				"Time", "Attackname", "id", "Msg", "Severity"));
		return mainKeys;
	}

	public LinkedHashMap<String, String> getLogLine() {
		return logLine;
	}
}

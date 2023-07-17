package com.it.loganalyze.log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Audit extends Log implements GetField {
	public LinkedHashMap<String, String> logLine = new LinkedHashMap<>();
	private final ArrayList<String> keys = new ArrayList<>(Arrays.asList(
			"Timestamp","attacktype","transaction_id", "local_port", "remote_port", "local_address", "remote_address","request_line","respond_body"));
	
	
	public Audit(LinkedHashMap<String, String> line) {
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
	    String dateString = getField("Timestamp");
	    System.out.println(dateString.charAt(14));
	    LocalDateTime dateTime = LocalDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.of(0, 1));
	    try {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss.SSSSSS Z");
	        dateTime = LocalDateTime.parse(dateString, formatter);
	    } catch (DateTimeParseException e) {
	        System.err.println(e.getMessage());
	    }
	    System.out.println(dateTime);
	    return dateTime;
	}

	@Override
	public String getSrcIp() {
		String res = logLine.get("remote_address");
		if(res == null) {			
			return "";
		}
		return res;
	}

	@Override
	public ArrayList<String> getMainField() {
		ArrayList<String> mainKeys = new ArrayList<>(Arrays.asList(
				"Timestamp","attack type","transaction_id", "local_port", "remote_port", "local_address", "remote_address","request_line"));
		return mainKeys;
	}

	@Override
	public LinkedHashMap<String, String> getLogLine() {
		return logLine;
	}
}

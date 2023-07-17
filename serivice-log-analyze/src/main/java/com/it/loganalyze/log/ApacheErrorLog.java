package com.it.loganalyze.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;

public class ApacheErrorLog extends Log {
	public LinkedHashMap<String, String> logLine = new LinkedHashMap<>();
	private final ArrayList<String> keys = new ArrayList<>(Arrays.asList(
			"Timestamp", "configure", "Emerge_level", "Process_id", "Thread_id", "Client_Ip", "Port", "Message",
			"Referer"));

	public ApacheErrorLog(LinkedHashMap<String, String> line) {
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
		String dateString = logLine.get("Timestamp");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
		return LocalDateTime.parse(dateString, formatter);
	}

	@Override
	public String getSrcIp() {
		String res = logLine.get("Client_Ip");
		return res;
	}

	@Override
	public ArrayList<String> getMainField() {
		ArrayList<String> mainKeys = new ArrayList<>(Arrays.asList(
				"Timestamp", "configure", "Emerge_level", "Process_id", "Client_Ip", "Port"));
		return mainKeys;
	}
}

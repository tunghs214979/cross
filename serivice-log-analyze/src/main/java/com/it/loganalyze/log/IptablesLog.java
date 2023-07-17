package com.it.loganalyze.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.LinkedHashMap;

public class IptablesLog extends Log {
	public LinkedHashMap<String, String> logLine = new LinkedHashMap<String, String>();
	private final ArrayList<String> keys = new ArrayList<>(Arrays.asList(
			"Date", "Host name", "Log type", "Time from boot", "Prefix", "Incoming interface (IN)",
			"Outgoing interface (OUT)", "MAC address (MAC)",
			"Source ip address (SRC)", "Destination ip address (DST)", "Packet length (LEN)", "Type of service (TOS)",
			"Precedence type of service (PREC)",
			"Time to live (TTL)", "ID of ip diagram (ID)", "Protocol (PROTO)", "Source port (SPT)",
			"Destination port (DPT)",
			"SEQ number (SEQ)", "ACK number (ACK)", "TCP receive window size", "Reserved bits (RES)",
			"ICMP type (TYPE)",
			"ICMP code (CODE)", "ACK flag (ACK)", "SYN flag (SYN)", "PSH flag (PSH)", "URGP", "ECN flag (CE)",
			"Dont fragment flag (DF)",
			"More fragments to follow (MF)"));

	public IptablesLog(LinkedHashMap<String, String> line) {
		logLine = line;
	}

	@Override
	public LinkedHashMap<String, String> getLogLine() {
		return logLine;
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
		String dateString = LocalDateTime.now().getYear() + " " + getField("Date");
		LocalDateTime dateTime = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM  d HH:mm:ss");
			dateTime = LocalDateTime.parse(dateString, formatter);
		} catch (DateTimeParseException e) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd HH:mm:ss");
				dateTime = LocalDateTime.parse(dateString, formatter);
			} catch (Exception e2) {
				System.err.println("Error while parsing date");
			}
		}
		return dateTime;
	}

	@Override
	public String getSrcIp() {
		String res = logLine.get("Source ip address (SRC)");
		if (res == null) {
			return "";
		}
		return res;
	}

	@Override
	public ArrayList<String> getMainField() {
		ArrayList<String> mainKeys = new ArrayList<>(Arrays.asList(
				"Date", "Host name", "Log type", "MAC address (MAC)", "Source ip address (SRC)",
				"Destination ip address (DST)",
				"Packet length (LEN)", "Protocol (PROTO)", "Source port (SPT)", "Destination port (DPT)"));
		return mainKeys;
	}

}

package com.it.loganalyze.log;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class LogData {
	private ArrayList<Log> data;
	
	
	public  ArrayList<Log> getData() {
		return data;
	}
	
	public LogData(ArrayList<Log> log) {
		data = log;
	}
	
	public ArrayList<String> getKeys(){
		if(data.isEmpty()) {
			return null;
		}
		return data.get(0).getAllField();
	}
	
	public ArrayList<String> getMainKeys() {
		if(data.isEmpty()) {
			return null;
		}
		return data.get(0).getMainField();
	}
	
	public LogData filterByDate(LocalDate keyword) {
		ArrayList<Log> filteredLog = new ArrayList<>();
		for (Log l: data) {
			if(l.getDate().toLocalDate().equals(keyword)) {
				filteredLog.add(l);
			}
			System.out.println(l.getDate().toLocalDate());
			System.out.println(keyword);
		}
		System.out.println(filteredLog);
		return new LogData(filteredLog);
	}
	
	public LogData filterByIpAddress(String ipString, int significant) {
		ArrayList<Log> filteredLog = new ArrayList<>();
		for (Log l: data) {
			if(l.getSrcIp()!=null) {
				if(l.getSrcIp().contains(ipString)) {
					filteredLog.add(l);
				}
			}
		}
		return new LogData(filteredLog);
	}
	
	public ArrayList<Log> filterByFields(HashMap<String, String> keywords) {
		ArrayList<Log> filteredLog = new ArrayList<>();
		logs: for (Log l: data) {
			for (Map.Entry<String, String> entry :keywords.entrySet()) {
				if(!l.getField(entry.getKey()).equals(entry.getValue())) {
					continue logs;
				}
			}
			filteredLog.add(l);
		}
		return filteredLog;
	}
	
}

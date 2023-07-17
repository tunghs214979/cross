package com.it.loganalyze.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.google.gson.JsonObject;
import com.it.loganalyze.log.ApacheAccessLog;
import com.it.loganalyze.log.ApacheErrorLog;
import com.it.loganalyze.log.Audit;
import com.it.loganalyze.log.Debug;
import com.it.loganalyze.log.IptablesLog;
import com.it.loganalyze.log.Log;
import com.it.loganalyze.log.LogData;


public class CreateLog {
	public static LogData createApacheAccessLog(String fileName) {
		LogData logData;
		convertRawToJson.accessLogCode.Convert.Start(fileName);
		JsonObject data = Util.readJsonFile("src/main/resources/com/it/loganalyze/log/access.json").getAsJsonObject();
		ArrayList<Log> logList = new ArrayList<>();
		// logList contains logLine in the form <key, value>

		for (String line : data.keySet()) {
			// line is the index of each logLine
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			JsonObject logLine = data.get(line).getAsJsonObject();
			for (String key : logLine.keySet()) {
				// key is field in a log
				// value is the value of that key field
				String value = logLine.get(key).getAsString();
				map.put(key, value);
				// so map will be a LinkedHashMap that contains all pair <key, value> in a log
			}
			Log apacheAccessLog = new ApacheAccessLog(map);
			logList.add(apacheAccessLog);
		}
		logData = new LogData(logList);
		return logData;

	}

	public static LogData createApacheErrorLog(String fileName) {
		LogData logData;
		convertRawToJson.ErrorLogCode.Convert.Start(fileName);
		JsonObject data = Util.readJsonFile("src/main/resources/com/it/loganalyze/log/error.json").getAsJsonObject();
		ArrayList<Log> logList = new ArrayList<>();
		// logList contains logLine in the form <key, value>

		for (String line : data.keySet()) {
			// line is the index of each logLine
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			JsonObject logLine = data.get(line).getAsJsonObject();
			for (String key : logLine.keySet()) {
				// key is field in a log
				// value is the value of that key field
				String value = logLine.get(key).getAsString();
				map.put(key, value);
				// so map will be a LinkedHashMap that contains all pair <key, value> in a log
			}
			Log apacheErrorLog = new ApacheErrorLog(map);
			logList.add(apacheErrorLog);
		}
		logData = new LogData(logList);
		return logData;

	}

	public static LogData createIptablesLog(String fileName) {
		LogData logData;
		String data = Util.readFile(fileName);
		JsonObject regexMap = Util.getIptablesRegexMap();
		ArrayList<Log> logList = new ArrayList<>();

		for (String s : data.split("\n")) {
			if (s.contains("iptables: ")) {
				LinkedHashMap<String, String> map = new LinkedHashMap<>();
				for (String attr : regexMap.keySet()) {
					String value = Util.regexFind(regexMap.get(attr).getAsString(), s);
					if (!value.isEmpty() || attr.equals("Outgoing interface (OUT)")
							|| attr.equals("Incoming interface (IN)")) {
						map.put(attr, value);
					}
				}
				Log iptableLog = new IptablesLog(map);
				logList.add(iptableLog);
			}

		}
		logData = new LogData(logList);
		return logData;
	}
	public static LogData createAudit(String inputFileName) {
	    // Set a fixed output file name
	    String outputFileName = "src/main/resources/com/it/loganalyze/log/audit.json";

	    // Create a new instance of JsonConverter and convert the input file
	    JsonConverter converter = new JsonConverter(inputFileName, outputFileName);
	    try {
	        converter.convert();
	    } catch (IOException e) {
	        System.err.println("Error converting file: " + e.getMessage());
	    }

	    // Read the converted data from the output file
	    LogData logData;
	    JsonObject data = Util.readJsonFile(outputFileName).getAsJsonObject();
	    ArrayList<Log> logList = new ArrayList<>();

	    for (String line : data.keySet()) {
	        LinkedHashMap<String, String> map = new LinkedHashMap<>();
	        JsonObject logLine = data.get(line).getAsJsonObject();
	        for (String key : logLine.keySet()) {
	            String value = logLine.get(key).getAsString();
	            map.put(key, value);
	        }
	        Log auditlog = new Audit(map);
	        logList.add(auditlog);
	    }
	    logData = new LogData(logList);
	    return logData;
	}

	public static LogData createDebug(String fileName) {
	    LogData logData;
	    JsonConverterForDebug.parseLogFile(fileName);
	    JsonObject data = Util.readJsonFile("src/main/resources/com/it/loganalyze/log/debug.json").getAsJsonObject();
	    ArrayList<Log> logList = new ArrayList<>();
	    // logList contains logLine in the form <key, value>

	    for (String line : data.keySet()) {
	        // line is the index of each logLine
	        LinkedHashMap<String, String> map = new LinkedHashMap<>();
	        JsonObject logLine = data.get(line).getAsJsonObject();
	        for (String key : logLine.keySet()) {
	            // key is field in a log
	            // value is the value of that key field
	            String value = logLine.get(key).getAsString();
	            map.put(key, value);
	            // so map will be a LinkedHashMap that contains all pair <key, value> in a log
	        }
	        Log debug = new Debug(map);
	        logList.add(debug);
	    }
	    logData = new LogData(logList);
	    return logData;
	}
}

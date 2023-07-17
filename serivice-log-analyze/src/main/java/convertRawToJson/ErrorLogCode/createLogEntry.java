package convertRawToJson.ErrorLogCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

public class createLogEntry {
	static JsonObject create(LogEntry logEntry) {
		JsonObject logObject = new JsonObject();
		logObject.addProperty("Timestamp", logEntry.getTimestamp());
		logObject.addProperty("configure", logEntry.getConfigure());
		logObject.addProperty("Emerge_level", logEntry.getEmergeLevel());
		logObject.addProperty("Process_id", logEntry.getPID());
		logObject.addProperty("Thread_id", logEntry.getTID());
		logObject.addProperty("Client_Ip", logEntry.getIpAddress());
		logObject.addProperty("Port", logEntry.getPort());
		logObject.addProperty("Message", logEntry.getMessage());
		logObject.addProperty("Referer", logEntry.getReferer());

		return logObject;

	}

	static LogEntry extractLogEntry(String line) {
		LogParser logParser = new LogParser(line);

		String timestampStr = logParser.getTimestamp();
		String configure = logParser.getConfigure();
		String emerge_level = logParser.getEmergeLevel();
		String pID = logParser.getPID();
		String tID = logParser.getTID();
		String ip_address = logParser.getIpAddress();
		String port = logParser.getPort();
		String message = logParser.getMessage();
		String referer = logParser.getReferer();

		SimpleDateFormat dateFormat;
		Date timestamp;
		try {
			dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss.SSSSSS yyyy");
			timestamp = dateFormat.parse(timestampStr);
		} catch (ParseException e) {
			try {
				dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
				timestamp = dateFormat.parse(timestampStr);
			} catch (ParseException e1) {
				timestamp = null;
			}
		}

		return new LogEntry(timestamp, configure, emerge_level, pID, tID, ip_address, port, message, referer);
	}
}
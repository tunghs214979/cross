package convertRawToJson.accessLogCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

public class createLogEntry {
	static JsonObject create(LogEntry logEntry){
        JsonObject logObject = new JsonObject();
        logObject.addProperty("Ip_address", logEntry.getIpAddress());
        logObject.addProperty("User_identity", logEntry.getIdentity());
        logObject.addProperty("User_name", logEntry.getUser());
        logObject.addProperty("Timestamp", logEntry.getTimestamp());
        logObject.addProperty("HttpMethod", logEntry.getHttpMethod());
        logObject.addProperty("Url", logEntry.getURL());
        logObject.addProperty("Version", logEntry.getVersion());
        logObject.addProperty("Status_code", logEntry.getStatusCode());
        logObject.addProperty("Bytesize", logEntry.getByteSize());
        logObject.addProperty("UrlSource", logEntry.getUrlSource());
        logObject.addProperty("User_agent", logEntry.getUserAgent());
//        logObject.addProperty("Cookie", logEntry.getCookie());
//        logObject.addProperty("Proxy", logEntry.getXForwardedFor());
//        logObject.addProperty("Server_name", logEntry.getServerName());
//        logObject.addProperty("Remote_name", logEntry.getRemoteName());
//        logObject.addProperty("Response_time", logEntry.getResponeTime());
//        logObject.addProperty("Connection_status", logEntry.getConnectionStatus());
//        logObject.addProperty("Respone_header", logEntry.getResponeHeader());

        return logObject;
	}
	
	static LogEntry extractLogEntry(String line){
		LogParser logParser = new LogParser(line);
		
		String part[] = line.split(" ");
        //extract log information from line
		String ipAddress = logParser.getIpAddress();
        String user_identity = part[1];
        String user_name = part[2];
        String timestampStr = logParser.getTimestamp();

        String httpMethod = logParser.getRequestLine().split(" ")[0]; // GET
        String URL = logParser.getRequestLine().split(" ")[1]; //example.html
        String version = logParser.getRequestLine().split(" ")[2]; //HTTP/1.1
        
        String statusCode = logParser.getStatusByteS().split(" ")[0]; //200
        String bytesize = logParser.getStatusByteS().split(" ")[1]; // 1024
        String UrlSource = logParser.getUrlSource(); // http://referer.com
        String userAgent = logParser.getUserAgent(); // Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36\
//        String cookie = logParser.getCookie(); // my_cookie_value
//        String X_forwarded_for = logParser.getProxy(); //proxy_ip_address
//        String servername = logParser.getServerName(); //example.com
//        String remotename = logParser.getRemoteName(); //john_doe
//        String respone_time = logParser.getResponeTime(); //0.123
//        String connection = logParser.getConnectionStatus(); //keep-alive
//        String respone_header = logParser.getResponeHeader(); //Content-Type: text/html; charset=UTF-8, X-Powered-By: PHP/7.4.1

        //parse the timestamp into a Date object;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss ZZZZ");
        Date timestamp;
        try{
                timestamp = dateFormat.parse(timestampStr);
        }catch(ParseException e){
                timestamp = null;
        }

        return new LogEntry(ipAddress, user_identity, user_name, timestamp, httpMethod, URL, version, statusCode, bytesize, UrlSource, userAgent);
	}
}
package convertRawToJson.accessLogCode;

public class StringParser {
	private static String ip_addressString = "\\d+\\.\\d+\\.\\d+\\.\\d+";
	private static String timestampString = "\\[\\d+\\/\\w+\\/\\d+\\:\\d+\\:\\d+\\:\\d+\\s[\\+|\\-]\\d+\\]";
	private static String request_lineString = "\"([^\"]+)\"";
	private static String status_bytesizeString = "(\\d+)\s(\\d+)";
	private static String urlSourceString = "\"([^ ]+)\"";
	private static String user_agentString = "\"([^\"]*)\"";
	
	
	public static String getIpAddress(){
        return ip_addressString;
	}

	public static String getTimestamp(){
        return  timestampString;
	}

	public static String getRequestLine(){
        return request_lineString;
	}

	public static String getStatusByteS(){
        return status_bytesizeString;
	}

	public static String getUrlSource(){
        return urlSourceString;
	}
	
	public static String getUserAgent(){
        return user_agentString;
	}

}
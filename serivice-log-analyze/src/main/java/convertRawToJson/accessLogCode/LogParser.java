package convertRawToJson.accessLogCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {

	private String line;
	
//	StringParser stringParser = new StringParser();
		//create Pattern
	Pattern ip_addressPattern = Pattern.compile(StringParser.getIpAddress());
	Pattern timestampPattern = Pattern.compile(StringParser.getTimestamp());
	Pattern request_linePattern = Pattern.compile(StringParser.getRequestLine());
	Pattern status_bytesizePattern = Pattern.compile(StringParser.getStatusByteS());
	Pattern urlSourcePattern = Pattern.compile(StringParser.getUrlSource());
	Pattern user_agentPattern = Pattern.compile(StringParser.getUserAgent());
//	Pattern cookiesPattern = Pattern.compile(StringParser.getCookie());
//	Pattern proxyPattern = Pattern.compile(StringParser.getProxy());
//	Pattern servernamePattern = Pattern.compile(StringParser.getServerName());
//	Pattern remotenamePattern = Pattern.compile(StringParser.getRemoteName());
//	Pattern respone_timePattern = Pattern.compile(StringParser.getResponeTime());
//	Pattern connection_statusPattern = Pattern.compile(StringParser.getConnectionStatus());
//	Pattern respone_headerPattern = Pattern.compile(StringParser.getResponeHeader());
	
	
	public LogParser(String line) {
		this.line = line;
	}
	
	public String getIpAddress(){
		Matcher ip_addressMatcher = ip_addressPattern.matcher(line);
		if(ip_addressMatcher.find()) {
			return ip_addressMatcher.group();
		}
		return "-";
	}

	public String getTimestamp(){
		Matcher timestampMatcher = timestampPattern.matcher(line);
		if(timestampMatcher.find()) {
			String timestampString = timestampMatcher.group();
			return  timestampString.substring(1,timestampString.length() - 1);
		}
		return "-";
	}

	public String getRequestLine(){
		Matcher request_lineMatcher = request_linePattern.matcher(line);
		if(request_lineMatcher.find()) {
			String requeString =  request_lineMatcher.group(1);
			if(requeString.equals("-")) {
				return "- - -";
			}
			return requeString;
		}
		return "- - -";
	}

	public String getStatusByteS(){
		Matcher status_bytesizeMatcher = status_bytesizePattern.matcher(line);
		if(status_bytesizeMatcher.find()) {
			return status_bytesizeMatcher.group();
		}
		return "- -";
	}

	public String getUrlSource(){
		Matcher urlSourceMatcher = urlSourcePattern.matcher(line);
		if(urlSourceMatcher.find()) {
			return urlSourceMatcher.group(1);
		}
		return "-";
	}
	
	public String getUserAgent(){
		Matcher user_agentMatcher = user_agentPattern.matcher(line);			
		int cnt = 1;
		while(user_agentMatcher.find()){
			if(cnt == 3) {
				String useragentString = user_agentMatcher.group(1);
				return useragentString;
			}
			cnt ++;
		}
		return "-";
	}

//	public String getCookie(){
//		Matcher cookiesMatcher = cookiesPattern.matcher(line);
//		int cnt = 1;
//		while(cookiesMatcher.find()) {
//			if(cnt == 2) {
//				return cookiesMatcher.group(1);
//			}
//			cnt ++;
//		}
//		
//        return "-";
//	}
//
//	public String getProxy(){
//		Matcher proxyMatcher = proxyPattern.matcher(line);
//		int cnt = 1;
//		while(proxyMatcher.find()) {
//			if(cnt == 3) {
//				return proxyMatcher.group(1);
//			}
//			cnt ++;
//		}
//		
//        return "-";
//	}
//
//	public String getServerName(){
//		Matcher servernameMatcher = servernamePattern.matcher(line);
//		int cnt = 1;
//		while(servernameMatcher.find()) {
//			if(cnt == 4) {
//				return servernameMatcher.group(1);
//			}
//			cnt ++;
//		}
//		
//        return "-";
//	}
//
//	public String getRemoteName(){
//		Matcher remotenameMatcher = remotenamePattern.matcher(line);
//		int cnt = 1;
//		while(remotenameMatcher.find()) {
//			if(cnt == 5) {
//				return remotenameMatcher.group(1);
//			}
//			cnt ++;
//		}
//		
//        return "-";
//	}
//
//	public String getResponeTime(){
//		Matcher respone_timeMatcher = respone_timePattern.matcher(line);
//		if(respone_timeMatcher.find()) {
//			return respone_timeMatcher.group(1);
//		}
//		return "-";
//	}
//
//	public String getConnectionStatus(){
//		Matcher connection_statusMatcher = connection_statusPattern.matcher(line);
//		int cnt = 1;
//		while(connection_statusMatcher.find()) {
//			if(cnt == 7) {
//				return connection_statusMatcher.group(1);
//			}
//			cnt ++;
//		}
//		
//        return "-";
//	}
//
//	public String getResponeHeader(){
//		Matcher respone_headerMatcher = respone_headerPattern.matcher(line);
//		int cnt = 1;
//		while(respone_headerMatcher.find()) {
//			if(cnt == 10) {
//				return respone_headerMatcher.group(1);
//			}
//			cnt ++;
//		}
//		
//        return "-";
//	}
}
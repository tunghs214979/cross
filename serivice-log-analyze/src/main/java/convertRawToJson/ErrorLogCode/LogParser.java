package convertRawToJson.ErrorLogCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogParser {
	private String line;
	
	Pattern timestamPattern = Pattern.compile(StringParser.getTimestamp());
	Pattern configurePattern = Pattern.compile(StringParser.getConfigure());
	Pattern emerge_levelPattern = Pattern.compile(StringParser.getEmergeLevel());
	Pattern pIDPattern = Pattern.compile(StringParser.getPID());
	Pattern tIDPattern = Pattern.compile(StringParser.getTID());
	Pattern ip_addressPattern = Pattern.compile(StringParser.getIpAddress());
	Pattern messagePattern = Pattern.compile(StringParser.getMessage());
	Pattern refererPattern = Pattern.compile(StringParser.getReferer());
	
	public LogParser(String line) {
		this.line = line;
	}
	
	public String getTimestamp() {
		Matcher timestampMatcher = timestamPattern.matcher(line);
		if(timestampMatcher.find()) {
			String timestampString = timestampMatcher.group();
			return timestampString.substring(1, timestampString.length() - 1);
		}
		return "-";
	}
	
	public String getConfigure() {
		Matcher configureMatcher = configurePattern.matcher(line);
		if(configureMatcher.find()) {
			String configureString = configureMatcher.group();
			if (configureString.contains(":")) {
				return configureString.substring(1,configureString.indexOf(':'));				
			}
		}
		return "-";
	}
	
	public String getEmergeLevel() {
		Matcher emerge_levelMatcher = emerge_levelPattern.matcher(line);
		if(emerge_levelMatcher.find()) {
			String emerge_levelString = emerge_levelMatcher.group();
			if(emerge_levelString.contains(":")) {
				return emerge_levelString.substring(emerge_levelString.indexOf(':') + 1, emerge_levelString.length() - 1);				
			}
			else {
				return emerge_levelString.substring(1,emerge_levelString.length() - 1);
			}
		}
		return "-";
	}
	
	public String getPID() {
		Matcher pIDMatcher = pIDPattern.matcher(line);
		if(pIDMatcher.find()) {
			return pIDMatcher.group();
		}
		return "-";
	}
	
	public String getTID() {
		Matcher tIDMatcher = tIDPattern.matcher(line);
		if(tIDMatcher.find()) {
			return tIDMatcher.group();
		}
		return "-";
	}
	public String getIpAddress() {
		Matcher ip_addressMatcher = ip_addressPattern.matcher(line);
		if(ip_addressMatcher.find()) {
			String ipString = ip_addressMatcher.group();
			return ipString.substring(1,ipString.indexOf(":"));
		}
		return "-";
	}
	
	public String getPort() {
		Matcher ip_addressMatcher = ip_addressPattern.matcher(line);
		if(ip_addressMatcher.find()) {
			String portString = ip_addressMatcher.group();
			return portString.substring(portString.indexOf(":") + 1);
		}
		return "-";
	}
	
	public String getMessage() {
		Matcher messageMatcher = messagePattern.matcher(line);
		if(messageMatcher.find()) {
			String messageString = messageMatcher.group();
			return messageString.substring(2, messageString.length() - 1);
		}
		return "-";
	}
	
	public String getReferer() {
		Matcher refererMatcher = refererPattern.matcher(line);
		if(refererMatcher.find()) {
			String refererString = refererMatcher.group();
			return refererString;
		}
		return "-";
	}
}
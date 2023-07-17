package convertRawToJson.ErrorLogCode;

import java.util.Date;

public class LogEntry {
	private final Date timestampString;
	private final String configureString ;
	private final String emerge_levelString;
	private final String pIDString;
	private final String tIDString;
	private final String ip_addressString;
	private final String portString;
	private final String messageString;
	private final String refererString;
	
	public LogEntry(Date timestamp, String configure, String emerge_level, String pIDString, String tIDString, String ip_AddressString, String portString, String message, String refererString) {
		this.timestampString = timestamp;
		this.configureString = configure;
		this.emerge_levelString = emerge_level;
		this.pIDString = pIDString;
		this.tIDString = tIDString;
		this.ip_addressString = ip_AddressString;
		this.portString = portString;
		this.messageString = message;
		this.refererString = refererString;
	}
	
	public String getTimestamp() {
		if(this.timestampString != null) {
			return this.timestampString.toString();			
		}
		return "-";
	}
	
	public String getConfigure() {
		return this.configureString;
	}
	
	public String getEmergeLevel() {
		return this.emerge_levelString;
	}
	
	public String getPID() {
		return this.pIDString;
	}
	
	public String getTID() {
		return this.tIDString;
	}
	public String getIpAddress() {
		return this.ip_addressString;
	}
	
	public String getPort() {
		return this.portString;
	}
	
	public String getMessage() {
		return this.messageString;
	}
	
	public String getReferer() {
		return this.refererString;
	}
}
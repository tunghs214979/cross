package convertRawToJson.ErrorLogCode;

public class StringParser {
	private static String timestampString = "\\[\\w+\\s\\w+\\s\\d+\\s\\d+:\\d+:\\d+(\\.\\d+)?\\s\\d+\\]";
	private static String configure_emergeString = "\\[[^\\s]+\\]";
	private static String pIDString = "(?<=pid\\s)\\d+";
	private static String tIDString = "(?<=tid\\s)\\d+";
	private static String ip_addressString = "\\s\\d+\\.\\d+\\.\\d+\\.\\d+\\:\\d+"; //cái này thừa kí tự space ở đầu
	private static String messageString = "\\][^\\[\\],]+(,|$)";// regex này lấy thừa ra so với yêu cầu 2 kí tự đầu tiên và dấu phẩy ở cuối"
	private static String refererString = "(?<=referer\\:\\s).+";
	
	public static String getTimestamp() {
		return timestampString;
	}
	
	public static String getConfigure() {
		String configureString = configure_emergeString;
		return configureString;
	}
	
	public static String getEmergeLevel() {
		String emerge_levelString = configure_emergeString;
		return emerge_levelString;
	}
	
	public static String getPID() {
		return pIDString;
	}
	
	public static String getTID() {
		return tIDString;
	}
	
	public static String getIpAddress() {
		return ip_addressString;
	}
	
	public static String getMessage() {
		return messageString;
	}
	
	public static String getReferer() {
		return refererString;
	}
	
}
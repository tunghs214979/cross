package convertRawToJson.accessLogCode;

import java.util.Date;

public class LogEntry {
	private final String ip_address;
    private final String user_identity; // user_id often like be -
    private final String user_name; // user_name often like be -
    private final Date timestamp;
    //often that 3 attributes below are put in 1 field Request_line
    private final String http_method; // GET, POST, PUT, DELETE...
    private final String url; // /page.html
    private final String version; //phiên bản giao thức: http/1.1
                                  //
    private final String status_code;
    private final String bytesize;
    private final String url_source;// often "-", it can be called Referer: http://referer.com
    private final String user_agent;
//    private final String cookie;//my_cookie_value
//    private final String x_forwarded_for;// proxy_ip_address
//    private final String server_name;//the host name or domain name of the server: "example.com"
//    private final String remote_name;//the authenticated user making the request, if applicable: "john_doe"
//    private final String respone_time;
//    private final String connection_status;//the status of the connection, such as "closed" or "keep-alive"
//    private final String respone_header;// the http respone headers returned by the server: "Conten-Type: text/htmp; charset=UTF-8, X-Powered-By: PHP/7.4.1"

    public LogEntry(String ip_address, String user_identity, String user_name, Date timestamp, String http_method, String url, String version, String status_code, String bytesize, String url_source, String user_agent){
            this.ip_address = ip_address;
            this.user_identity = user_identity;
            this.user_name = user_name;
            this.timestamp = timestamp;
            this.http_method = http_method;
            this.url = url;
            this.version = version;
            this.status_code = status_code;
            this.bytesize = bytesize;
            this.url_source = url_source;
            this.user_agent = user_agent;
//            this.cookie = cookie;
//            this.x_forwarded_for = x_forwarded_for;
//            this.server_name = server_name;
//            this.remote_name = remote_name;
//            this.respone_time = respone_time;
//            this.connection_status = connection_status;
//            this.respone_header = respone_header;
    }

    public String getIpAddress(){
            return ip_address;
    }

    public String getIdentity(){
            return user_identity;
    }

    public String getUser(){
            return user_name;
    }

    public String getTimestamp(){
            return  timestamp.toString() ;
    }

    public String getHttpMethod(){
            return http_method;
    }

    public String getURL(){
            return url;
    }

    public String getVersion(){
            return version;
    }

    public String getStatusCode(){
            return status_code;
    }

    public String getByteSize(){
            return bytesize;
    }

    public String getUrlSource(){
            return url_source;
    }

    public String getUserAgent(){
            return user_agent;
    }

//    public String getCookie(){
//            return cookie;
//    }
//
//    public String getXForwardedFor(){
//            return x_forwarded_for;
//    }
//
//    public String getServerName(){
//            return server_name;
//    }
//
//    public String getRemoteName(){
//            return remote_name;
//    }
//
//    public String getResponeTime(){
//            return respone_time;
//    }
//
//    public String getConnectionStatus(){
//            return connection_status;
//    }
//
//    public String getResponeHeader(){
//            return respone_header;
//    }
}
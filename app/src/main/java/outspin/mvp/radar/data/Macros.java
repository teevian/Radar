package outspin.mvp.radar.data;

public class Macros {
    public static final int CONST_INTERNET_NUM_OF_DOWNLOADED_MOMENTS = 4;

    public static final short CONST_RADAR_INSIDE_NUM_OF_COLUMNS = 4;

    // internet
    public static final String CONST_INTERNET_SERVER_NAME   = "outspin.vps.ovh.pt";
    public static final String CONST_INTERNET_SERVER_IPV4   = "92.222.10.201";
    public static final String CONST_INTERNET_SERVER_IPV6   = "2001:41d0:401:3200::4092";
    public static final int CONST_INTERNET_TCP_PORT         = 52126;        // open port for ping
    public static final int CONST_INTERNET_SOCKET_TIMEOUT   = 1000;  // milliseconds

    // shared preferences
    public static final String PREFERENCE_FILE_AUTHENTICATION = "outspin.mvp.radar.PREFERENCE_FILE_TOKEN";
    public static final String PREFERENCE_PASSWORD_KEY      = "password";
    public static final String PREFERENCE_PHONE_NUMBER_KEY  = "phoneNumber";

    // SERVER STATUS
    public static final int SERVER_STATUS_OK                = 200;
    public static final int SERVER_STATUS_CREATED           = 201;
    public static final int SERVER_STATUS_ACCEPTED          = 202;

    public static final int SERVER_STATUS_INVALID_REQUEST   = 400;
    public static final int SERVER_STATUS_NOT_AUTHORIZED    = 401;
    public static final int SERVER_STATUS_NOT_FOUND         = 404;
    public static final int SERVER_STATUS_TIMED_OUT         = 408;
    public static final int SERVER_STATUS_CONFLICT          = 409;
    public static final int SERVER_STATUS_GONE              = 410;
    public static final int SERVER_STATUS_CUP_OF_TEA        = 418;

    public static final int SERVER_STATUS_INTERNAL_ERROR    = 500;
    public static final int SERVER_STATUS_NOT_IMPLEMENTED   = 501;
    public static final int SERVER_STATUS_BAD_GATEWAY       = 502;
    public static final int SERVER_STATUS_UNAVAILABLE       = 503;
}

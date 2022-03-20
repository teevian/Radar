package outspin.mvp.radar.data;

public class Macros {
    public static final short CONST_RADAR_INSIDE_NUM_OF_COLUMNS = 4;
    public static final String CONST_OUTSPIN_VERSION        = "0.1";
    public static final String CONST_OUTSPIN_USER_AGENT     = "Outspin/0.1 (Android v4.4; Mobile; rv:41.0)";

    // internet
    public static final String CONST_INTERNET_SERVER_NAME   = "outspin.vps.ovh.pt";
    public static final String CONST_INTERNET_SERVER_IPV4   = "92.222.10.201";
    public static final String CONST_INTERNET_SERVER_IPV6   = "2001:41d0:401:3200::4092";
    public static final int CONST_INTERNET_TCP_PORT         = 62126;        // open port for ping
    public static final String CONST_API_URI                = "https://" + CONST_INTERNET_SERVER_IPV4
                                                                    + ":" + CONST_INTERNET_TCP_PORT;

    public static final int CONST_INTERNET_SOCKET_TIMEOUT   = 1000;  // milliseconds
    public static final int CONST_INTERNET_HTTP_TIMEOUT     = 30000;

    // shared preferences
    public static final String PREFERENCE_FILE_AUTHENTICATION = "outspin.mvp.radar.PREFERENCE_FILE_TOKEN";
    public static final String PREFERENCE_PASSWORD_KEY      = "password";
    public static final String PREFERENCE_PHONE_NUMBER_KEY  = "phoneNumber";

    // API
    public static final String PREFERENCES_AUTH_TOKEN       = "AuthToken";

    public static final String NOTIFICATION_WAVE = "waved at you!";
}

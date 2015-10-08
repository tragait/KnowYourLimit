package com.ericsson.hack.knowyourlimit;

public class Constants {

    public static final String SCHEME = "coap://";
    public static final String HOST = "100.96.67.117";
    public static final int PORT = 5683;
    public static final String PATH_SPEED_LIMIT = "getSpeedLimit";
    public static final String QUERY_PARAM = "?deviceId=1&lng=28.408296&lat=77.241027";

    public static final String URI_SPEED_LIMIT = SCHEME + HOST + PORT + PATH_SPEED_LIMIT + QUERY_PARAM;
}

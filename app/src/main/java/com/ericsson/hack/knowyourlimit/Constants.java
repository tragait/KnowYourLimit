package com.ericsson.hack.knowyourlimit;

public class Constants {

    public static final String SCHEME = "coap://";
    public static final String HOST = "100.96.67.117";
    public static final int PORT = 5683;
    public static final String PATH_SPEED_LIMIT = "getSpeedLimit";
    public static final String QUERY_PARAM_1 = "?deviceId=1&lng=28.403883&lat=77.271470";
    public static final String QUERY_PARAM_2 = "?deviceId=1&lng=28.469822&lat=77.105554";
    public static final String QUERY_PARAM_3 = "?deviceId=1&lng=28.411920&lat=77.235337";

    public static final String URI_1 = SCHEME + HOST + ":" + PORT + "/" + PATH_SPEED_LIMIT + QUERY_PARAM_1;
    public static final String URI_2 = SCHEME + HOST + ":" + PORT + "/" + PATH_SPEED_LIMIT + QUERY_PARAM_2;
    public static final String URI_3 = SCHEME + HOST + ":" + PORT + "/" + PATH_SPEED_LIMIT + QUERY_PARAM_3;
}

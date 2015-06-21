package com.qferiz.trafficjam.extras;

/**
 * Created by Qferiz on 12-06-2015.
 */
public class UrlEndpoints {
    public static final String URL_SERVER_ADDR = "http://trafficjam.qferiz.com/";
    public static final String URL_SERVER_API_APPS = "api-apps/";
    public static final String URL_IMAGES_TRAFFIC = "images_traffic/";
    public static final String GET_TRAFFIC_JAM = URL_SERVER_ADDR + URL_SERVER_API_APPS + "get-trafficjam.php";
    public static final String GET_IMAGES_TRAFFIC = URL_SERVER_ADDR + URL_IMAGES_TRAFFIC;
    public static final String INSERT_TRAFFIC_JAM = URL_SERVER_ADDR + URL_SERVER_API_APPS + "insert-trafficjam.php";
    public static final String URL_CHAR_QUESTION = "?";
    public static final String URL_CHAR_AMEPERSAND = "&";
    public static final String URL_PARAM_API_KEY = "apikey=";
    public static final String URL_PARAM_LIMIT = "limit=";
}

package com.qferiz.trafficjam.json;

import com.qferiz.trafficjam.extras.UrlEndpoints;

/**
 * Created by Qferiz on 14-06-2015.
 */
public class Endpoints {
    public static String getRequestUrlInfoTrafficJam(int limit) {
        return UrlEndpoints.GET_TRAFFIC_JAM
                + UrlEndpoints.URL_CHAR_QUESTION
                + UrlEndpoints.URL_PARAM_LIMIT + limit;
    }

}

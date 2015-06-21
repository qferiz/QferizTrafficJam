package com.qferiz.trafficjam.json;

import org.json.JSONObject;

/**
 * Created by Qferiz on 14-06-2015.
 */
public class UtilsJson {
    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }
}

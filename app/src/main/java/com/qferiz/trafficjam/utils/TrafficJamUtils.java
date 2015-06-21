package com.qferiz.trafficjam.utils;

import com.android.volley.RequestQueue;
import com.qferiz.trafficjam.extras.MyApplication;
import com.qferiz.trafficjam.extras.TrafficJam;
import com.qferiz.trafficjam.json.Endpoints;
import com.qferiz.trafficjam.json.Parser;
import com.qferiz.trafficjam.json.Requestor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Qferiz on 16-06-2015.
 */
public class TrafficJamUtils {
    public static ArrayList<TrafficJam> loadInfoTrafficJam(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestInfoTrafficJSON(requestQueue, Endpoints.getRequestUrlInfoTrafficJam(30));
        ArrayList<TrafficJam> listTraffic = Parser.parseInfoTrafficJSON(response);
        MyApplication.getWritableDatabase().insertTrafficJam(listTraffic, true);
        return listTraffic;
    }
}

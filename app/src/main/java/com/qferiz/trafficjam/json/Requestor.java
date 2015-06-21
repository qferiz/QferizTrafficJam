package com.qferiz.trafficjam.json;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.qferiz.trafficjam.logging.L;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by Qferiz on 16-06-2015.
 */
public class Requestor {
    public static JSONObject requestInfoTrafficJSON(RequestQueue requestQueue, String url) {
        JSONObject response = null;
        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, (String) null, requestFuture, requestFuture);

        requestQueue.add(request);
        try {
            response = requestFuture.get(30000, TimeUnit.MILLISECONDS); // 30 detik
        } catch (InterruptedException e) {
            L.m(e + "Call InterruptedException");
        } catch (ExecutionException e) {
            L.m(e + "Call ExecutionException");
        } catch (TimeoutException e) {
            L.m(e + "Call TimeoutException");
        }

        return response;
    }
}

package com.qferiz.trafficjam.json;

import com.android.volley.DefaultRetryPolicy;
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
        int SOCKET_TIMEOUT_MS = 5000; // 5000 MS / 5 Seconds
        JSONObject response = null;

        RequestFuture<JSONObject> requestFuture = RequestFuture.newFuture();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, (String) null, requestFuture, requestFuture);

        requestQueue.add(request);
        try {
            response = requestFuture.get(30, TimeUnit.SECONDS); // 10 detik
        } catch (InterruptedException e) {
            L.m(e + " Call InterruptedException");
            e.printStackTrace();
        } catch (ExecutionException e) {
            L.m(e + " Call ExecutionException");
            e.printStackTrace();
        } catch (TimeoutException e) {
            // Retry JsonObjectRequest if TimeOut Exeption
            //Set a retry policy in case of SocketTimeout & ConnectionTimeout Exceptions.
            //Volley does retry for you if you have specified the policy.

            L.m(e + " Call TimeoutException");
            e.printStackTrace();

//            requestQueue.add(request);

//            return response;

        }

        request.setRetryPolicy(new DefaultRetryPolicy(SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        return response;
    }
}

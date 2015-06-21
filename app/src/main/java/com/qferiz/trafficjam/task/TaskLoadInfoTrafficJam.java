package com.qferiz.trafficjam.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.qferiz.trafficjam.callback.InfoTrafficJamLoadedListener;
import com.qferiz.trafficjam.extras.TrafficJam;
import com.qferiz.trafficjam.network.VolleySingleton;
import com.qferiz.trafficjam.utils.TrafficJamUtils;

import java.util.ArrayList;

/**
 * Created by Qferiz on 16-06-2015.
 */
public class TaskLoadInfoTrafficJam extends AsyncTask<Void, Void, ArrayList<TrafficJam>> {
    private InfoTrafficJamLoadedListener myComponent;
    private VolleySingleton mVolleySingleton;
    private RequestQueue mRequestQueue;

    public TaskLoadInfoTrafficJam(InfoTrafficJamLoadedListener myComponent) {
        this.myComponent = myComponent;
        mVolleySingleton = VolleySingleton.getInstance();
        mRequestQueue = mVolleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<TrafficJam> doInBackground(Void... voids) {
        ArrayList<TrafficJam> listTraffic = TrafficJamUtils.loadInfoTrafficJam(mRequestQueue);
        return listTraffic;
    }

    @Override
    protected void onPostExecute(ArrayList<TrafficJam> listTraffic) {
//        super.onPostExecute(trafficJams);
        if (myComponent != null) {
            myComponent.onInfoTrafficJamLoaded(listTraffic);
        }
    }
}

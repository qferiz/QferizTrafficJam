package com.qferiz.trafficjam.callback;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Qferiz on 02-07-2015.
 */
public interface MyLocationListener extends LocationListener {
    @Override
    void onLocationChanged(Location location);

    @Override
    void onStatusChanged(String s, int i, Bundle bundle);

    @Override
    void onProviderDisabled(String s);

    @Override
    void onProviderEnabled(String s);
}

package com.qferiz.trafficjam.extras;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.qferiz.trafficjam.database.TrafficJamDB;

/**
 * Created by Qferiz on 13-06-2015.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;
    private static TrafficJamDB mDatabase;


    public static MyApplication getInstance(){
        return sInstance;
    }

    public static Context getAppContext()

    {
        return sInstance.getApplicationContext();
    }

    public synchronized static TrafficJamDB getWritableDatabase() {
        if (mDatabase == null){
            mDatabase = new TrafficJamDB(getAppContext());
        }
        return mDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mDatabase = new TrafficJamDB(this);
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

}

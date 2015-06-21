package com.qferiz.trafficjam.logging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by Qferiz on 10-06-2015.
 */

public class L {
    public static void m(String message) {
        Log.d("QFERIZ", "" + message);
    }

    public static void t(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
    }
    public static void T(Context context, String message) {
        Toast.makeText(context, message + "", Toast.LENGTH_LONG).show();
    }

/*    public static void s(Context context, String message) {
        Snackbar.make(context, message, Snackbar.LENGTH_SHORT).show();
    }*/
}

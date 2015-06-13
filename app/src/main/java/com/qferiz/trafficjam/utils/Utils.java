package com.qferiz.trafficjam.utils;

import android.content.Context;
import android.os.Build;

import com.qferiz.trafficjam.R;

/**
 * Created by Qferiz on 11-06-2015.
 */
public class Utils {
    public static int getToolbarHeight(Context _context) {
        int height = (int) _context.getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
        return height;
    }

    public static int getStatusBarHeight(Context _context) {
        int height = (int) _context.getResources().getDimension(R.dimen.statusbar_size);
        return height;
    }

    public static boolean isLollipopOrGreater(){
        return Build.VERSION.SDK_INT >= 21 ? true : false;
    }

    public static boolean isJellyBeanOrGreater(){
        return Build.VERSION.SDK_INT >= 16 ? true : false;
    }
}

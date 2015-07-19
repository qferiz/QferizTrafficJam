package com.qferiz.trafficjam.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import com.qferiz.trafficjam.R;

/**
 * Created by Qferiz on 11-06-2015.
 *
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

    public static Bitmap lessResolution(String filePath, int reqWidth, int reqHeight) {
//        int reqHeight = 120;
//        int reqWidth = 120;
        BitmapFactory.Options options = new BitmapFactory.Options();

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}

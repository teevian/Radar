package outspin.mvp.radar.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public class ScreenHelper {

    @NonNull
    //public static Pair<Integer, Integer> getScreenResolution(Context context) {
    public static int[] getScreenResolution(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return new int[]{ metrics.widthPixels, metrics.heightPixels };
    }

    /* returns Screen Size: 0 - SMALL; 1 - NORMAL; 2 - LARGE; 3 - ELSE */
    public static int getScreenSize(@NonNull Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:     return 0;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:    return 1;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:     return 2;
            default:    return 3;
        }
    }

    /* returns Screen density: 0 - LDPI; 1 - MDPI; 2 - HDPI; 3 - XHDPI; 4 - ELSE */
    public static int getScreenDensity(@NonNull Context context) {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        switch(density)
        {
            case DisplayMetrics.DENSITY_LOW:    return 0;
            case DisplayMetrics.DENSITY_MEDIUM: return 1;
            case DisplayMetrics.DENSITY_HIGH:   return 2;
            case DisplayMetrics.DENSITY_XHIGH:  return 3;
            default:    return 4;
        }
    }

}

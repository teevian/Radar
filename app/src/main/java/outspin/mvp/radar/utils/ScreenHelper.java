package outspin.mvp.radar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

/**
 *  Screen helper functions
 */
public class ScreenHelper {

    public static void hideKeyboard(Activity activity) {
        /*if (activity.getCurrentFocus() == null || !(activity.getCurrentFocus() instanceof EditText)) {
            editText.requestFocus();
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /*
    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }*/
    /**
     * Calls for the screen resolution.
     *
     * @param context activity context
     * @return int array where [width, height] in pixels
     */
    @NonNull
    public static int[] getScreenResolution(@NonNull Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();

        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        return new int[]{ metrics.widthPixels, metrics.heightPixels };
    }

    /**
     * Calls for the size of the screen.
     *
     * @param context activity context
     * @return screen size: 0 - SMALL; 1 - NORMAL; 2 - LARGE; 3 - ELSE
     */
    public static int getScreenSize(@NonNull Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:     return 0;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:    return 1;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:     return 2;
            default:    return 3;
        }
    }

    /**
     * Calls display metrics for the density DPI.
     *
     * @param context activity context
     * @return screen density: 0 - LPDI; 1 - MDPI; 2 - HDPI; 3 - XHDPI; 4 - ELSE
     */
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


    // TO TEST
    @ColorInt
    public static int getAppropriateTextColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double luminance = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return luminance < 0.5 ? Color.BLACK : Color.WHITE;
    }



}

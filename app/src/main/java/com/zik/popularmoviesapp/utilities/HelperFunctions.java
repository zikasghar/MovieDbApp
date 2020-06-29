package com.zik.popularmoviesapp.utilities;

import android.content.Context;
import android.util.DisplayMetrics;

public class HelperFunctions {

    // Helper method to calculate the best amount of columns to be displayed on a device
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 180;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }
}

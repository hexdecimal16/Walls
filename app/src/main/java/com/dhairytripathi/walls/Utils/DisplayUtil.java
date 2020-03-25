package com.dhairytripathi.walls.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {

    public static int getWidth(int size, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / size);
        return noOfColumns;
    }
}

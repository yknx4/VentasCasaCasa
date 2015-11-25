package com.arekar.android.ventascasacasa.helpers;

import android.content.Context;

import com.arekar.android.ventascasacasa.R;

/**
 * Created by yknx4 on 25/11/15.
 */
public class SalesHelper {
    public static String[] getNamesFromPosition(Context ctx, int stringArray, Integer[] positions){
        String[] arrays = ctx.getResources().getStringArray(stringArray);
        String[] result = new String [positions.length];
        int cnt = 0;
        for (int p :positions) {
            result[cnt] = arrays[p];
            cnt++;
        }
        return result;
    }
    public static String[] getDaysFromPosition(Context context,Integer[] positions){
        return getNamesFromPosition(context, R.array.days,positions);
    }
    public static String getDayStringFromPosition(Context context,Integer[] positions){
        return Methods.getStringFromStringArray(getDaysFromPosition(context, positions), true);
    }
    public static String[] getTimeFromProsition(Context context,Integer[] positions){
        return getNamesFromPosition(context, R.array.time, positions);
    }
    public static String getTimeStringFromProsition(Context context,Integer[] positions){
        return Methods.getStringFromStringArray(getTimeFromProsition(context, positions),true);
    }
}

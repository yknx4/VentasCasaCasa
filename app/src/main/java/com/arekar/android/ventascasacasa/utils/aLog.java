package com.arekar.android.ventascasacasa.utils;

import android.util.Log;

import com.arekar.android.ventascasacasa.Constants;



public class aLog {
  public static void w (String TAG, String msg){
    if(Constants.LOGGING) {
      if (TAG != null && msg != null)
        Log.w(TAG, msg);
    }
  }

}

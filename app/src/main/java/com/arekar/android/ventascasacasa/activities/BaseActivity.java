package com.arekar.android.ventascasacasa.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.R;

import java.lang.ref.WeakReference;

/**
 * Created by yknx4 on 25/10/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected static String INVALID_TOKEN = "INVALID_TOKEN";
    protected static String INVALID_UID = "INVALID_UID";

    protected String getUserId()
    {
        return this.sharedPreferences.getString(Constants.Preferences.USER_ID, INVALID_UID);
    }
    protected String getToken()
    {
        return this.sharedPreferences.getString(Constants.Preferences.TOKEN, INVALID_TOKEN);
    }
    protected boolean setUserId(String user_id)
    {
        return this.sharedPreferences.edit().putString(Constants.Preferences.USER_ID,user_id).commit();
    }
    protected boolean setToken(String token)
    {
        return this.sharedPreferences.edit().putString(Constants.Preferences.TOKEN,token).commit();
    }
    protected SharedPreferences sharedPreferences;

    protected boolean checkTokenAndUser() {
        return !getUserId().equals(INVALID_UID) && !getToken().equals(INVALID_TOKEN);
    }
    protected void checkLogin(){
        if(checkTokenAndUser()) return;
        Intent login = new Intent(getApplicationContext(),LoginActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        login.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCES,MODE_PRIVATE);
        super.onCreate(savedInstanceState);
//        SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
//        localEditor.putString(Constants.Preferences.USER_ID, getString(R.string.static_user_id));
//        localEditor.putString(Constants.Preferences.TOKEN, getString(R.string.static_token));
//        localEditor.apply();

    }

    public abstract Messenger getMessenger();

    static class IncomingHandler extends Handler {
        private final WeakReference<BaseActivity> mService;

        IncomingHandler(BaseActivity service) {
            mService = new WeakReference<>(service);
        }
        @Override
        public void handleMessage(Message msg)
        {
            BaseActivity service = mService.get();
            if (service != null) {
                service.handleMessage(msg);
            }
        }
    }

    public abstract void handleMessage(Message msg);
}

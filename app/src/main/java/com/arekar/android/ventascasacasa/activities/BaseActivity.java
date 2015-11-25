package com.arekar.android.ventascasacasa.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.R;

import java.lang.ref.WeakReference;

public abstract class BaseActivity extends AppCompatActivity {
    protected String getUserId()
    {
        return this.sharedPreferences.getString(Constants.Preferences.USER_ID, getString(R.string.static_user_id));
    }
    protected String getToken()
    {
        return this.sharedPreferences.getString(Constants.Preferences.TOKEN, getString(R.string.static_token));
    }
    protected SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor localEditor = this.sharedPreferences.edit();
        localEditor.putString(Constants.Preferences.USER_ID, getString(R.string.static_user_id));
        localEditor.putString(Constants.Preferences.TOKEN, getString(R.string.static_token));
        localEditor.apply();

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

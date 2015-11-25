package com.arekar.android.ventascasacasa.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.R;

public class BaseFragment extends Fragment {
    private String getUserId()
    {
        return this.sharedPreferences.getString(Constants.Preferences.USER_ID, getString(R.string.static_user_id));
    }
    protected String getToken()
    {
        return this.sharedPreferences.getString(Constants.Preferences.TOKEN, getString(R.string.static_token));
    }
    SharedPreferences sharedPreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
    }
}

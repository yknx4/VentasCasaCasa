package com.arekar.android.ventascasacasa.rest;

import android.support.annotation.NonNull;

import com.arekar.android.ventascasacasa.Constants;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.octo.android.robospice.GoogleHttpClientSpiceService;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;

import roboguice.util.temp.Ln;

/**
 * Created by yknx4 on 23/10/2015.
 */
public class SalesSpiceRequest extends GoogleHttpClientSpiceRequest<JsonArray> implements JsonSpiceRequest {
    String userid;
    String baseUrl = Constants.Connections.API_URL+Constants.Connections.PATH_SALES;

    @Override
    public JsonArray loadDataFromNetwork() throws IOException
    {
        Ln.d("Call web service " + this.baseUrl, new Object[0]);
        Object localObject = new GenericUrl(this.baseUrl);
        ((GenericUrl)localObject).put("user_id", this.userid);
        Ln.d("User: " + this.userid, new Object[0]);
        Ln.d("Generic URL: " + ((GenericUrl)localObject).toString(), new Object[0]);
        localObject = getHttpRequestFactory().buildGetRequest((GenericUrl)localObject);
        ((HttpRequest)localObject).setParser(new GsonFactory().createJsonObjectParser());
        localObject = ((HttpRequest)localObject).execute();
        return (JsonArray)(JsonArray)new Gson().fromJson(new InputStreamReader(((HttpResponse)localObject).getContent()), JsonArray.class);
    }

    @Override
    public Boolean insertData() {
        return null;
    }

    @Override
    public Boolean updateData(JSONObject data) {
        return null;
    }

    @Override
    public JsonElement updateData(String id, String data) {
        return null;
    }

    @Override
    public Boolean updateData(String field, String value, String data) {
        return null;
    }

    @Override
    public Boolean updateData(String[] fields, String[] values, String data) {
        return null;
    }

    @Override
    public Boolean deleteData(String id) {
        return null;
    }

    public SalesSpiceRequest(@NonNull String paramString)
    {
        super(JsonArray.class);
        userid = paramString;
        setHttpRequestFactory(GoogleHttpClientSpiceService.createRequestFactory());
    }
}

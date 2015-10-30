package com.arekar.android.ventascasacasa.rest;

import android.support.annotation.NonNull;

import com.arekar.android.ventascasacasa.Constants;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;

import roboguice.util.temp.Ln;

/**
 * Created by yknx4 on 23/10/2015.
 */
public class SalesSpiceRequest extends JsonSpiceRequest<JsonArray> {

    String baseUrl = Constants.Connections.API_URL+Constants.Connections.PATH_SALES;

    @Override
    public JsonArray loadDataFromNetwork() throws IOException
    {
        GenericUrl localUrl = new GenericUrl(this.baseUrl);
        localUrl.put("user_id", getUserId());
        localUrl.put("token", getToken());
        Ln.d("User: " + getUserId());
        return loadDataFromNetwork(localUrl).getAsJsonArray();
    }

    @Override
    public Boolean updateData(String element, String elementId) throws IOException {
        throw new NotImplementedException("You shall not pass!");
    }


    public SalesSpiceRequest(@NonNull String token, @NonNull String userid)
    {
        super(JsonArray.class,token,userid);
    }

    @Override
    public JsonElement insertData(String element) throws IOException{
        String url = baseUrl;
        GenericUrl gUrl = new GenericUrl(url);
        Ln.d("User: " + getUserId());
        Ln.d("Generic URL: " + gUrl.toString());
        gUrl.put("token", getToken());
        Ln.d("Inserting: " + element);
        JsonElement response = insertData(gUrl,element);
        Ln.d("JSON Response: "+response.toString());
        return response;
    }

    @Override
    public boolean deleteFromNetwork(String elementid) throws IOException {
        return false;
    }
}

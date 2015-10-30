package com.arekar.android.ventascasacasa.rest;

import android.support.annotation.NonNull;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.model.Payment;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.json.JsonHttpContent;
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
 * Created by yknx4 on 24/10/2015.
 */
public class PaymentsSpiceRequest extends  JsonSpiceRequest<JsonArray> {
    private String clientid;

    public PaymentsSpiceRequest(@NonNull String token, @NonNull String userid, @NonNull String clientid) {
        super(JsonArray.class,token,userid);
        this.clientid = clientid;
    }
    public PaymentsSpiceRequest(@NonNull String token,@NonNull String userid){
        super(JsonArray.class, token, userid);

    }
    private PaymentsSpiceRequest(){
        super(JsonArray.class);
        setHttpRequestFactory(GoogleHttpClientSpiceService.createRequestFactory());

    }

    final String baseUrl = Constants.Connections.API_URL+Constants.Connections.PATH_PAYMENTS;
    @Override
    public JsonArray loadDataFromNetwork() throws IOException
    {
        Ln.d("Call web service " + this.baseUrl);
        GenericUrl localObject = new GenericUrl(this.baseUrl);
        ((GenericUrl)localObject).put("user_id", getUserId());
        Ln.d("User: " + getUserId());
        Ln.d("Generic URL: " + localObject.toString());
        return loadDataFromNetwork(localObject).getAsJsonArray();
    }

    @Override
    public Boolean updateData(String element, String elementId) throws IOException {
        return null;
    }

    @Override
    public JsonElement insertData(String element) throws IOException
    {
        GenericUrl gUrl = new GenericUrl(baseUrl);
        gUrl.put("token",getToken());
        return insertData(gUrl,element);
    }

    @Override
    public boolean deleteFromNetwork(String elementid) throws IOException {
        return false;
    }

}

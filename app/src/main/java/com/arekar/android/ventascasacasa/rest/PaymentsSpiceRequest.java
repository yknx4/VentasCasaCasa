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
public class PaymentsSpiceRequest extends GoogleHttpClientSpiceRequest<JsonArray>
        implements JsonSpiceRequest {
    private String userId;
    private String clientid;

    public PaymentsSpiceRequest(@NonNull String userid, @NonNull String clientid) {
        this(userid);
        this.clientid = clientid;
    }
    public PaymentsSpiceRequest(@NonNull String userid){
        this();
        userId = userid;
    }
    private PaymentsSpiceRequest(){
        super(JsonArray.class);
        setHttpRequestFactory(GoogleHttpClientSpiceService.createRequestFactory());

    }



    @Override
    public Boolean updateData(JSONObject data) {
        return null;
    }

    @Override
    public JsonElement updateData(String id, String data) {
        //return updateData("_id",id,data);
        return null;
    }

    @Override
    public Boolean updateData(String field, String value, String data) {
       //return  updateData(new String[]{field},new String[]{value},data);
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
    final String baseUrl = Constants.Connections.API_URL+Constants.Connections.PATH_PAYMENTS;
    @Override
    public JsonArray loadDataFromNetwork() throws IOException
    {
        Ln.d("Call web service " + this.baseUrl);
        Object localObject = new GenericUrl(this.baseUrl);
        ((GenericUrl)localObject).put("user_id", userId);
        Ln.d("User: " + userId);
        Ln.d("Generic URL: " + ((GenericUrl)localObject).toString());
        localObject = getHttpRequestFactory().buildGetRequest((GenericUrl)localObject);
        ((HttpRequest)localObject).setParser(new GsonFactory().createJsonObjectParser());
        localObject = ((HttpRequest)localObject).execute();
        return (JsonArray)(JsonArray)new Gson().fromJson(new InputStreamReader(((HttpResponse)localObject).getContent()), JsonArray.class);
    }

    @Override
    public Boolean insertData() {
        return null;
    }

    public JsonElement insertData(String url, String element) throws IOException
    {
        Ln.d("Call web service " + url);
        GenericUrl gUrl = new GenericUrl(url);
        Ln.d("User: " + userId);
        Ln.d("Generic URL: " + gUrl.toString());
        Ln.d("Inserting: "+element );
        HttpContent json = ByteArrayContent.fromString("application/json", element);
        HttpRequest httpRequest = getHttpRequestFactory().buildPostRequest(gUrl, json);
        httpRequest.setParser(new GsonFactory().createJsonObjectParser());
        //httpRequest.setHeaders(httpRequest.getHeaders().setContentType("application/json"));
        HttpResponse httpResponse = httpRequest.execute();
        JsonElement response = new Gson().fromJson(new InputStreamReader(httpResponse.getContent()), JsonElement.class);
        Ln.d("JSON Response: "+response.toString());
        return response;
    }
    public JsonElement insertData(String url, Payment pay) throws IOException
    {
        Ln.d("Call web service " + url);
        GenericUrl gUrl = new GenericUrl(url);
        Ln.d("User: " + userId);
        Ln.d("Generic URL: " + gUrl.toString());
        HttpContent json = new JsonHttpContent(new GsonFactory(),pay);
        HttpRequest httpRequest = getHttpRequestFactory().buildPostRequest(gUrl, json);
        httpRequest.setParser(new GsonFactory().createJsonObjectParser());
        httpRequest.setHeaders(httpRequest.getHeaders().setContentType("application/json"));
        HttpResponse httpResponse = httpRequest.execute();
        JsonElement response = new Gson().fromJson(new InputStreamReader(httpResponse.getContent()), JsonElement.class);
        Ln.d("JSON Response: "+response.toString());
        return response;
//        JsonHttpContent jsonHttpContent = new JsonHttpContent(new GsonFactory(), pay);
//
//        //ByteArrayContent.fromString("application/json", jsonObject.toString())
//        HttpRequest request = getHttpRequestFactory().buildPostRequest(
//                new GenericUrl(baseUrl),
//                jsonHttpContent);
//
//
//        request.getHeaders().setContentType("application/json");
//
//        request.setParser(new GsonFactory().createJsonObjectParser());
//
//        request.setContent(jsonHttpContent);
//
//        HttpResponse httpResponse = request.execute();
//
//        JsonElement result = new Gson().fromJson(new InputStreamReader(httpResponse.getContent()), JsonElement.class);
//
//        return result;
    }
}

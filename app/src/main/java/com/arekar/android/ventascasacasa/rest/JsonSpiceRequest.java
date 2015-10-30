package com.arekar.android.ventascasacasa.rest;

import android.support.annotation.NonNull;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.octo.android.robospice.GoogleHttpClientSpiceService;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import java.io.IOException;
import java.io.InputStreamReader;

import roboguice.util.temp.Ln;

public abstract class JsonSpiceRequest<T extends JsonElement> extends GoogleHttpClientSpiceRequest<T> {
    protected Class type;
    private String userid;
    private String token;

    public JsonSpiceRequest(Class<T> clazz) {
        super(clazz);
        type = clazz;
        setHttpRequestFactory(GoogleHttpClientSpiceService.createRequestFactory());
    }

    public JsonSpiceRequest(Class<T> clazz, String token) {
        this(clazz);
        this.token = token;
    }

    public JsonSpiceRequest(Class<T> clazz, String token, String userid) {
        this(clazz, token);
        this.userid = userid;
    }

    @Override
    public abstract T loadDataFromNetwork()
            throws IOException;

    protected String getUserId() {
        return userid;
    }

    protected String getToken() {
        return token;
    }

    protected JsonElement loadDataFromNetwork(@NonNull GenericUrl url) throws IOException {
        return loadDataFromNetwork(url, RequestMethod.get, null);
    }

    protected JsonElement loadDataFromNetwork(@NonNull GenericUrl url, @NonNull RequestMethod method, HttpContent data) throws IOException {
        return new Gson().fromJson(new InputStreamReader(execRequestToNetwork(url, method, data).getContent()), JsonElement.class);

    }

    protected HttpResponse execRequestToNetwork(@NonNull GenericUrl url, @NonNull RequestMethod method, HttpContent data) throws IOException {
        Ln.d("Call web service " + url.toString());
        HttpRequest request;
        HttpContent content = data;
        HttpResponse response;
        if (data == null) content = new EmptyContent();
        switch (method) {
            default:
            case get:
                request = getHttpRequestFactory().buildGetRequest(url);
                break;
            case delete:
                request = getHttpRequestFactory().buildDeleteRequest(url);
                break;
            case put:
                request = getHttpRequestFactory().buildPutRequest(url, content);
                break;
            case head:
                request = getHttpRequestFactory().buildHeadRequest(url);
                break;
            case post:
                request = getHttpRequestFactory().buildPostRequest(url, content);
                break;
        }
        Ln.d("Request type: "+method);
        request.setParser(new GsonFactory().createJsonObjectParser());
        response = request.execute();
        return response;
    }



    protected Boolean updateData(GenericUrl gUrl, String element) throws IOException {
        Ln.d("Generic URL: " + gUrl.toString());
        Ln.d("Inserting: " + element);
        HttpContent json = ByteArrayContent.fromString("application/json", element);
        return doRequest(gUrl, RequestMethod.put, json);
    }

    protected boolean doRequest(@NonNull GenericUrl url, @NonNull RequestMethod method, HttpContent data) throws IOException {
        return execRequestToNetwork(url, method, data).isSuccessStatusCode();
    }

    abstract public JsonElement insertData(String data) throws IOException;

    abstract public boolean deleteFromNetwork(String elementid) throws IOException;

    abstract public Boolean updateData(String element, String elementId) throws IOException;

    protected Boolean deleteFromNetwork(GenericUrl localObject) throws IOException {
        Ln.d("Generic URL: " + localObject.toString());
        JsonElement response = loadDataFromNetwork(localObject, RequestMethod.delete, null);
        return response.getAsJsonObject().get("ok").getAsInt() > 0;
    }

    protected JsonElement insertData(GenericUrl gUrl, String data) throws IOException {
        Ln.d("User: " + getUserId());
        Ln.d("Generic URL: " + gUrl.toString());
        Ln.d("Inserting: " + data);
        HttpContent json = ByteArrayContent.fromString("application/json", data);
        JsonElement response = loadDataFromNetwork(gUrl, RequestMethod.post, json);
        Ln.d("JSON Response: " + response.toString());
        return response;
    }

    public enum RequestMethod {
        get, post, delete, put, head
    }


}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.JsonSpiceRequest
 * JD-Core Version:    0.6.0
 */
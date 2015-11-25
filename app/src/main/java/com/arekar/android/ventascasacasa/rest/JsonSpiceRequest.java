package com.arekar.android.ventascasacasa.rest;

import android.support.annotation.NonNull;
import android.util.Log;

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

/**
 * The type Json spice request.
 * This abstract class is used as an interface for the web service, all the interactions with the web service are done through JsonSpiceRequests.
 *
 * @param <T> the type parameter
 */
public abstract class JsonSpiceRequest<T extends JsonElement> extends GoogleHttpClientSpiceRequest<T> {
    /**
     * The Type of the data that is got from the web service, it must extend JsonElement.
     */
    protected Class type;
    private String userid;
    private String token;

    /**
     * Instantiates a new Json spice request.
     *
     * @param clazz the class it will return when loading data
     */
    public JsonSpiceRequest(Class<T> clazz) {
        super(clazz);
        type = clazz;
        setHttpRequestFactory(GoogleHttpClientSpiceService.createRequestFactory());
    }

    /**
     * Instantiates a new Json spice request.
     *
     * @param clazz the class it will return when loading data
     * @param token the token
     */
    public JsonSpiceRequest(Class<T> clazz, String token) {
        this(clazz);
        this.token = token;
        Log.d(LOGT,"Global token: "+token);
    }
     private static final String LOGT = JsonSpiceRequest.class.getSimpleName();
    /**
     * Instantiates a new Json spice request.
     *
     * @param clazz  the class it will return when loading data
     * @param token  the token
     * @param userid the userid
     */
    public JsonSpiceRequest(Class<T> clazz, String token, String userid) {
        this(clazz, token);
        this.userid = userid;
        Log.d(LOGT,"Global user id: "+userid);
    }

    @Override
    public abstract T loadDataFromNetwork()
            throws IOException;

    /**
     * Gets user id.
     *
     * @return the user id
     */
    protected String getUserId() {
        return userid;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    protected String getToken() {
        return token;
    }

    /**
     * Load data from network returning a json element.
     *
     * @param url the url
     * @return the json element
     * @throws IOException the io exception
     */
    protected JsonElement loadDataFromNetwork(@NonNull GenericUrl url) throws IOException {
        return loadDataFromNetwork(url, RequestMethod.get, null);
    }

    /**
     * Load data from network json element.
     *
     * @param url    the url
     * @param method the HTTP method (Get, Post, Put, Delete, etc...)
     * @param data   the data to be processed
     * @return the json element
     * @throws IOException the io exception
     */
    protected JsonElement loadDataFromNetwork(@NonNull GenericUrl url, @NonNull RequestMethod method, HttpContent data) throws IOException {
        return new Gson().fromJson(new InputStreamReader(execRequestToNetwork(url, method, data).getContent()), JsonElement.class);

    }

    /**
     * Exec request to network http response.
     *
     * @param url    the url
     * @param method the HTTP method (Get, Post, Put, Delete, etc...)
     * @param data   the data to be processed
     * @return the http response
     * @throws IOException the io exception
     */
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


    /**
     * Updates data in the Web Service.
     *
     * @param gUrl    the url
     * @param element the element
     * @return if it was updated successful
     * @throws IOException the io exception
     */
    protected Boolean updateData(GenericUrl gUrl, String element) throws IOException {
        Ln.d("Generic URL: " + gUrl.toString());
        Ln.d("Inserting: " + element);
        HttpContent json = ByteArrayContent.fromString("application/json", element);
        return doRequest(gUrl, RequestMethod.put, json);
    }

    /**
     * Performs a request to the web service.
     *
     * @param url    the url
     * @param method the HTTP method (Get, Post, Put, Delete, etc...)
     * @param data   the data to be processed
     * @return If the request was successful
     * @throws IOException the io exception
     */
    protected boolean doRequest(@NonNull GenericUrl url, @NonNull RequestMethod method, HttpContent data) throws IOException {
        return execRequestToNetwork(url, method, data).isSuccessStatusCode();
    }

    /**
     * Insert data to a web service.
     *
     * @param data the data to be processed
     * @return the json element of the inserted element
     * @throws IOException the io exception
     */
    abstract public JsonElement insertData(String data) throws IOException;

    /**
     * Delete data from Web Serviece.
     *
     * @param elementid the element id
     * @return If the request was perfomed successful (This doesn't guaranties it was deleted)
     * @throws IOException the io exception
     */
    abstract public boolean deleteFromNetwork(String elementid) throws IOException;

    /**
     *  Updates data in the Web Service.
     *
     * @param element   the element to update as Json String
     * @param elementId the element id
     * @return Whether it was updated
     * @throws IOException the io exception
     */
    abstract public Boolean updateData(String element, String elementId) throws IOException;

    /**
     * Delete data from Web Serviece.
     *
     * @param localObject Web service url.
     * @return If the request was perfomed successful (This doesn't guaranties it was deleted)
     * @throws IOException the io exception
     */
    protected Boolean deleteFromNetwork(GenericUrl localObject) throws IOException {
        Ln.d("Generic URL: " + localObject.toString());
        JsonElement response = loadDataFromNetwork(localObject, RequestMethod.delete, null);
        return response.getAsJsonObject().get("ok").getAsInt() > 0;
    }

    /**
     * Insert data  to the web service.
     *
     * @param gUrl the url
     * @param data the data to be processed
     * @return the json element inserted
     * @throws IOException the io exception
     */
    protected JsonElement insertData(GenericUrl gUrl, String data) throws IOException {
        Ln.d("User: " + getUserId());
        Ln.d("Generic URL: " + gUrl.toString());
        Ln.d("Inserting: " + data);
        HttpContent json = ByteArrayContent.fromString("application/json", data);
        JsonElement response = loadDataFromNetwork(gUrl, RequestMethod.post, json);
        Ln.d("JSON Response: " + response.toString());
        return response;
    }

    /**
     * The enum Request method.
     */
    public enum RequestMethod {
        /**
         * Get request method.
         */
        get, /**
         * Post request method.
         */
        post, /**
         * Delete request method.
         */
        delete, /**
         * Put request method.
         */
        put, /**
         * Head request method.
         */
        head
    }


}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.JsonSpiceRequest
 * JD-Core Version:    0.6.0
 */
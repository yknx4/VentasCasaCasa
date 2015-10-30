package com.arekar.android.ventascasacasa.rest;

import android.support.annotation.NonNull;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.model.AddressGPS;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.octo.android.robospice.GoogleHttpClientSpiceService;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;

import roboguice.util.temp.Ln;

/**
 * Created by yknx4 on 25/10/2015.
 */
public class GoogleGeocodingSpiceRequest extends JsonSpiceRequest<JsonObject> {

    String baseurl = "https://maps.googleapis.com/maps/api/geocode/json";
    private String address;

    public GoogleGeocodingSpiceRequest(@NonNull String address)
    {
        super(JsonObject.class);
        this.address = address;
        setHttpRequestFactory(GoogleHttpClientSpiceService.createRequestFactory());
    }

    @Override
    public JsonObject loadDataFromNetwork() throws IOException {
        Ln.d("Call web service " + baseurl);
        GenericUrl gUrl = new GenericUrl(baseurl);
        gUrl.put("key", Constants.GOOGLE_HTTP_ID);
        gUrl.put("address", address);
        Ln.d("Generic URL: " + gUrl.toString());
        return loadDataFromNetwork(gUrl).getAsJsonObject();
    }

    @Override
    public Boolean updateData(String element, String elementId) throws IOException {
        return null;
    }


    @Override
    public JsonElement insertData(String data) throws IOException {
        return null;
    }

    @Override
    public boolean deleteFromNetwork(String elementid) throws IOException {
        return false;
    }


    public AddressGPS loadLocationFromNetwork() {
        AddressGPS add = new AddressGPS();
        add.setLatitude(0.0);
        add.setLongitude(0.0);
        try {
            JsonObject r = loadDataFromNetwork();
            if(r.has("status")){
                if(r.get("status").getAsString().equals("OK")){
                    JsonObject closest = r.get("results").getAsJsonArray().get(0).getAsJsonObject().get("geometry").getAsJsonObject().get("location").getAsJsonObject();
                    add.setLongitude(closest.get("lng").getAsDouble());
                    add.setLatitude(closest.get("lat").getAsDouble());
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return add;
    }
}

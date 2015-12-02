package com.arekar.android.ventascasacasa.rest;

import android.util.Base64;
import android.util.Log;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.model.User;
import com.google.api.client.http.GenericUrl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import roboguice.util.temp.Ln;

/**
 * Created by yknx4 on 30/11/15.
 */
public class SignUpSpiceRequest extends JsonSpiceRequest<JsonObject> {
    private static String baseUrl = Constants.Connections.API_URL+ Constants.Connections.PATH_USER;

    private static String stat_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiI1NWZmMGY1NmFjODcyZDI0NDJiNTcwNjQiLCJuYW1lIjoiSm9yZ2UgQWxlamFuZHJvIEZpZ3Vlcm9hIFDDqXJleiIsInVzZXIiOiJ5a254NCIsImltYWdlIjoiaHR0cDovL2xvcmVtcGl4ZWwuY29tLzI1Ni8yNTYvZmFzaGlvbi8iLCJjcmVhdGVkIjoxNDQyNzc4OTQzNjM3LCJtYXN0ZXIiOnRydWUsInNlZWQiOjEwNDYxODA3NiwiaWF0IjoxNDQ0NDE1NDIyfQ.2bx-x8yr-rXKyP4tlgF1GlBbTOp3JQp6P9Mw3cDL28k";

    public SignUpSpiceRequest() {
        super(JsonObject.class);
    }

    public JsonObject signUp(User user){
        String username = user.getUser();
        String password = user.getPassword();
        String email = user.getEmail();
        JsonElement inserted;
        if(!checkUserAndEmail(username,email)) return null;
        User nUser = new User();
        nUser.setEmail(email);
        nUser.setUser(username);
        nUser.setPassword(getHash(password));
        nUser.setCreated(new Date().getTime());
        try {
            inserted = insertData(new Gson().toJson(nUser));
            return inserted.getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public boolean checkUserAndEmail(String username, String email){
        try {
            JsonArray currentUsers = loadDataFromNetwork(new GenericUrl(baseUrl)).getAsJsonArray();
            for(JsonElement el:currentUsers){
                JsonObject user = el.getAsJsonObject();
                String exEmail =user.get("email").getAsString();
                String exUser =user.get("user").getAsString();
                Log.d(TAG,"Testing "+exUser+" against "+username);
                Log.d(TAG,"Testing "+exEmail+" against "+email);
                if(exEmail.equals(email)) return false;
                if(exUser.equals(username)) return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private static String TAG = SignUpSpiceRequest.class.getSimpleName();

    @Override
    public JsonObject loadDataFromNetwork() throws IOException {
        return null;
    }

    private String getHash(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.trim().getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.encodeToString(md.digest(),Base64.DEFAULT).trim();
    }

    @Override
    public JsonElement insertData(String data) throws IOException {
        Ln.d("Call web service " + baseUrl);
        GenericUrl gUrl = new GenericUrl(baseUrl);
        Ln.d("Generic URL: " + gUrl.toString());
        Ln.d("Inserting: "+data);
        gUrl.set("token",stat_token);
        JsonElement response = insertData(gUrl,data);
        Ln.d("JSON Response: "+response.toString());
        return response;
    }

    @Override
    public boolean deleteFromNetwork(String elementid) throws IOException {
        return false;
    }

    @Override
    public Boolean updateData(String element, String elementId) throws IOException {
        return null;
    }
}

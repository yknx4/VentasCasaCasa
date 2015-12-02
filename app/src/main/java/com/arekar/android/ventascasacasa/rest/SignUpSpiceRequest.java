package com.arekar.android.ventascasacasa.rest;

import android.util.Base64;

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

import roboguice.util.temp.Ln;

/**
 * Created by yknx4 on 30/11/15.
 */
public class SignUpSpiceRequest extends JsonSpiceRequest<JsonObject> {
    private static String baseUrl = Constants.Connections.API_URL+ Constants.Connections.PATH_USER;

    public SignUpSpiceRequest() {
        super(JsonObject.class);
    }

    public JsonObject signUp(String username, String password, String email){
        JsonElement inserted;
        if(!checkUserAndEmail(username,email)) return null;
        User nUser = new User();
        nUser.setEmail(email);
        nUser.setUser(username);
        nUser.setPassword(getHash(password));
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
                if(user.get("email").getAsString().equals(email)) return false;
                if(user.get("user").getAsString().equals(username)) return false;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    @Override
    public JsonObject loadDataFromNetwork() throws IOException {
        return null;
    }

    private String getHash(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return Base64.encodeToString(md.digest(),Base64.DEFAULT);
    }

    @Override
    public JsonElement insertData(String data) throws IOException {
        Ln.d("Call web service " + baseUrl);
        GenericUrl gUrl = new GenericUrl(baseUrl);
        Ln.d("Generic URL: " + gUrl.toString());
        Ln.d("Inserting: "+data);

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

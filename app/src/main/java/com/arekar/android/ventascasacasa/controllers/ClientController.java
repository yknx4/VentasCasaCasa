package com.arekar.android.ventascasacasa.controllers;

import com.arekar.android.ventascasacasa.model.Client;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ClientController {
    public static Client fromActivityRespone(String response){
        JsonObject actResponse = new Gson().fromJson(response,JsonObject.class);
        Client f = new Client();


        return f;
    }
}

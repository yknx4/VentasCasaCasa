package com.arekar.android.ventascasacasa.helpers;

import com.arekar.android.ventascasacasa.model.Client;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yknx4 on 23/10/2015.
 */
public class ClientJsonHandler extends AbstractJsonHandler<Client> {
    private static final String TAG = "ClientJsonHandler";

    public ClientJsonHandler(JsonArray clients){
        super(clients);
        Type clientListType = new TypeToken<List<Client>>(){}.getType();
        itemsList = new Gson().fromJson(clients,clientListType);
    }

}

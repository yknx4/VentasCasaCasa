package com.arekar.android.ventascasacasa.helpers;

import android.util.Log;

import com.arekar.android.ventascasacasa.model.Client;
import com.arekar.android.ventascasacasa.model.Sale;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yknx4 on 23/10/2015.
 * Json Handler for Sales
 */
public class SalesJsonHandler extends AbstractJsonHandler<Sale> {
    private static final String TAG = "SalesJsonHandler";

    /**
     * Instantiates a new Sales json handler.
     *
     * @param items the JsonArray item to be handled
     */
    public SalesJsonHandler(JsonArray items) {
        super(items);
        itemsList = new ArrayList<>();
        Type clientListType = new TypeToken<List<Sale>>(){}.getType();
        itemsList = new Gson().fromJson(items,clientListType);
    }


    /**
     * Get sales by client id.
     *
     * @param clientId the client id
     * @return the list of sales
     */
    public List<Sale> getSalesByClientId(String clientId){
        Log.d(TAG,"Looking for sales with client id: "+clientId+" in "+itemsList.size()+" elements.");
        List<Sale> sales = new ArrayList<>();
        for(Sale sale: itemsList){
            if(sale.getClientId().equals(clientId))sales.add(sale);
        }
        Log.d(TAG,"Found: "+sales.size());
        return sales;
    }

}

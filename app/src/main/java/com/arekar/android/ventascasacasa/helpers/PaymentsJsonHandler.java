package com.arekar.android.ventascasacasa.helpers;

import android.util.Log;

import com.arekar.android.ventascasacasa.model.Payment;
import com.arekar.android.ventascasacasa.model.Sale;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yknx4 on 25/10/2015.
 */
public class PaymentsJsonHandler extends AbstractJsonHandler<Payment> {
    private static final String TAG = "PaymentsJsonHandler";

    public PaymentsJsonHandler(JsonArray item) {
        super(item);
        itemsList = new ArrayList<>();
        Type clientListType = new TypeToken<List<Payment>>(){}.getType();
        itemsList = new Gson().fromJson(items,clientListType);
    }

    public List<Payment> getSalesBySaleId(String saleId){
        Log.d(TAG, "Looking for payments with sale id: " + saleId + " in " + itemsList.size() + " elements.");
        List<Payment> payments = new ArrayList<>();
        for(Payment pay: itemsList){
            if(pay.getSaleId().equals(saleId))payments.add(pay);
        }
        Log.d(TAG, "Found: " + payments.size());
        return payments;
    }

    public Double getAmountPaid(List<Payment> pays){
        if(pays==null || pays.size()==0) return 0.0;
        Double cnt= 0.0;
        for(Payment pay:pays){
            cnt+=pay.getPrice();
        }
        return cnt;
    }
    public Double getAmountPending(Sale sale){
        return sale.getPrice()-getAmountPaid(getSalesBySaleId(sale.getId()));
    }


}

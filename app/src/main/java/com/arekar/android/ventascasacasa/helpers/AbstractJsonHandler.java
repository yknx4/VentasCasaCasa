package com.arekar.android.ventascasacasa.helpers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arekar.android.ventascasacasa.model.AbstractItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yknx4 on 23/10/2015.
 */
public class AbstractJsonHandler<T extends AbstractItem> {
    protected JsonArray items;
    protected List<T> itemsList;
    protected String TAG = "AbstractJsonHandler";

    public AbstractJsonHandler(JsonArray item) {
        this.items = item;
    }


    public T getById(@NonNull String id){
        if(id.isEmpty()){
            Log.d(TAG, "getById()\n Input id is empty, are you sure it wasn't a mistake?");
            return null;
        }
        for(T item: itemsList){
            if(item.getId().equals(id)) return  item;
        }
        return null;
    }
}

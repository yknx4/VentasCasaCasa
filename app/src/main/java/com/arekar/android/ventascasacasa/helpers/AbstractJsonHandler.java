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
 *
 * Class for handling Json Array data as POJO
 *
 * @param <T> the type parameter
 */
public class AbstractJsonHandler<T extends AbstractItem> {
    /**
     * The Items.
     */
    protected JsonArray items;
    /**
     * The Items list.
     */
    protected List<T> itemsList;
    /**
     * The Log Tag.
     */
    protected String TAG = "AbstractJsonHandler";

    /**
     * Instantiates a new Abstract json handler.
     *
     * @param item the JsonArray item to be handled
     */
    public AbstractJsonHandler(JsonArray item) {
        this.items = item;
    }


    /**
     * Get an element from JsonArray by ID
     *
     * @param id the id
     * @return the element
     */
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

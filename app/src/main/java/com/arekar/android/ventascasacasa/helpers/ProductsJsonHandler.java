package com.arekar.android.ventascasacasa.helpers;

import com.arekar.android.ventascasacasa.model.Client;
import com.arekar.android.ventascasacasa.model.Product;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by yknx4 on 26/10/2015.
 */
public class ProductsJsonHandler extends AbstractJsonHandler<Product> {
    public ProductsJsonHandler(JsonArray item) {
        super(item);
        Type listType = new TypeToken<List<Product>>(){}.getType();
        itemsList = new Gson().fromJson(item,listType);
    }
}

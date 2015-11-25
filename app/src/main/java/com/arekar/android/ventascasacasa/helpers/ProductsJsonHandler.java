package com.arekar.android.ventascasacasa.helpers;

import com.arekar.android.ventascasacasa.model.Client;
import com.arekar.android.ventascasacasa.model.Product;
import com.arekar.android.ventascasacasa.model.Sale;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yknx4 on 26/10/2015.
 * Json Handler for Products
 */
public class ProductsJsonHandler extends AbstractJsonHandler<Product> {
    /**
     * Instantiates a new Products json handler.
     *
     * @param item the Json Array item from which Products will be loaded.
     */
    public ProductsJsonHandler(JsonArray item) {
        super(item);
        Type listType = new TypeToken<List<Product>>(){}.getType();
        itemsList = new Gson().fromJson(item,listType);
    }

    /**
     * Get products given positions in the arrai.
     *
     * @param positions the positions
     * @return the list
     */
    public List<Product> getProductFromPosition(Integer[] positions){
        List<Product> res = new ArrayList<>();
        for(Integer pos:positions){
            res.add(itemsList.get(pos));
        }
        return res;
    }

    /**
     * Get products id from positions in the array.
     *
     * @param positions the positions
     * @return the list containing the ID's
     */
    public List<String> getProductsIdFromPosition(Integer[] positions){
        List<String> res = new ArrayList<>();
        for(Integer pos:positions){
            res.add(itemsList.get(pos).getId());
        }
        return res;
    }

    /**
     * Get products string from positions in the array
     *
     * @param positions the positions
     * @return the string
     */
    public String getProductsStringFromPosition(Integer[] positions){
        StringBuilder sb = new StringBuilder();
        sb.append("");
        for(Integer pos:positions){
            sb.append(itemsList.get(pos).getName()).append("\n");
        }
        return sb.toString();

    }

    /**
     * Get products accumulated price from positions.
     *
     * @param positions the positions
     * @return the amount
     */
    public double getProductsPriceFromPosition(Integer[] positions){
        double total = 0;
        for(Integer pos:positions){
           total+=itemsList.get(pos).getPrice();
        }
        return total;

    }

    /**
     * Get products title string [ ].
     *
     * @return the string [ ]
     */
    public String[] getProductsTitle(){
        List<String> titles = new ArrayList<>();
        for(Product item:itemsList){
            titles.add(item.getName()+" - "+Methods.getMoneyString(item.getPrice()));
        }
        return titles.toArray(new String[titles.size()]);
    }

}

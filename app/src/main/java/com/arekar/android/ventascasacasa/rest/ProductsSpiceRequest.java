package com.arekar.android.ventascasacasa.rest;

import android.support.annotation.NonNull;

import com.google.api.client.http.GenericUrl;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;

import roboguice.util.temp.Ln;

public class ProductsSpiceRequest extends JsonSpiceRequest<JsonArray>
{
  private String baseUrl = "http://sales-yknx4.rhcloud.com/product";
  private String updateUrl="http://sales-yknx4.rhcloud.com/product/";
  private String deleteUrl="http://sales-yknx4.rhcloud.com/product/";

  public ProductsSpiceRequest(@NonNull String token,@NonNull String userid)
  {
    super(JsonArray.class, token, userid);
  }

  public JsonArray loadDataFromNetwork()
    throws IOException
  {

    GenericUrl localObject = new GenericUrl(this.baseUrl);
    localObject.put("user_id", getUserId());
    localObject.put("sort", "name");
    Ln.d("User: " + getUserId());
    Ln.d("Generic URL: " + localObject.toString());
    return loadDataFromNetwork(localObject).getAsJsonArray();
  }


  @Override
  public Boolean updateData(String element, String elementId) throws IOException {
    Ln.d("Call web service " + updateUrl);
    GenericUrl gUrl = new GenericUrl(updateUrl+elementId);
    gUrl.put("token", getToken());
    Ln.d("Generic URL: " + gUrl.toString());
    Ln.d("Inserting: " + element);
    return updateData(gUrl,element);
  }
  @Override
  public JsonElement insertData(String element) throws IOException
  {
    String url = baseUrl;
    GenericUrl gUrl = new GenericUrl(url);
    JsonElement response = insertData(gUrl,element);
    Ln.d("JSON Response: "+response.toString());
    return response;
  }
@Override
  public boolean deleteFromNetwork(String elementid) throws IOException {
    Ln.d("Call web service " + this.deleteUrl + elementid);
    GenericUrl localObject = new GenericUrl(this.deleteUrl+elementid);
    localObject.put("token", getToken());
    return deleteFromNetwork(localObject);
  }

}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.ProductsSpiceRequest
 * JD-Core Version:    0.6.0
 */
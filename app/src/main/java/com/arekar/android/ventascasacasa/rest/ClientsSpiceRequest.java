package com.arekar.android.ventascasacasa.rest;

import android.support.annotation.NonNull;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.octo.android.robospice.GoogleHttpClientSpiceService;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import roboguice.util.temp.Ln;

/**
 * The type Clients spice request.
 * This is the class to perform requests to the Web Service, it handles everything related to Clients
 */
public class ClientsSpiceRequest extends JsonSpiceRequest<JsonArray>
{
  private String baseUrl = "http://sales-yknx4.rhcloud.com/client";
  private String deleteUrl = "http://sales-yknx4.rhcloud.com/client/";
  private String updateUrl = "http://sales-yknx4.rhcloud.com/client/";

  /**
   * Instantiates a new Clients spice request.
   *
   * @param userId the user id
   * @param token  the token
   */
  public ClientsSpiceRequest(@NonNull String userId,@NonNull String token)
  {
    super(JsonArray.class, userId, token);
    }

  public JsonArray loadDataFromNetwork()
    throws IOException
  {
    Ln.d("Call web service " + this.baseUrl);
    GenericUrl localObject = new GenericUrl(this.baseUrl);
    localObject.put("user_id", getUserId());
    localObject.put("sort", "name");
    Ln.d("User: " + getUserId());
    Ln.d("Generic URL: " + localObject.toString());
    return loadDataFromNetwork(localObject).getAsJsonArray();
  }


  @Override
  public boolean deleteFromNetwork(String client_id) throws IOException {
    Ln.d("Call web service " + this.deleteUrl + client_id);
   GenericUrl localObject = new GenericUrl(this.deleteUrl+client_id);
    localObject.put("token", getToken());
    Ln.d("Generic URL: " + localObject.toString());

    return deleteFromNetwork(localObject);
  }


  @Override
  public Boolean updateData(String element, String elementid) throws IOException {
    Ln.d("Call web service " + updateUrl);
    GenericUrl gUrl = new GenericUrl(updateUrl+elementid);
    gUrl.put("token",getToken());

    Ln.d("Generic URL: " + gUrl.toString());
    Ln.d("Inserting: " + element);
    return updateData(gUrl,element);
  }

  @Override
  public JsonElement insertData( String element) throws IOException
  {
    Ln.d("Call web service " + baseUrl);
    GenericUrl gUrl = new GenericUrl(baseUrl);
    Ln.d("Generic URL: " + gUrl.toString());
    Ln.d("Inserting: "+element );

    JsonElement response = insertData(gUrl,element);
    Ln.d("JSON Response: "+response.toString());
    return response;
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.ClientsSpiceRequest
 * JD-Core Version:    0.6.0
 */
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

public class ProductsSpiceRequest extends GoogleHttpClientSpiceRequest<JsonArray>
  implements JsonSpiceRequest
{
  private String baseUrl = "http://sales-yknx4.rhcloud.com/product";
  private String userid;
  private String updateUrl="http://sales-yknx4.rhcloud.com/product/";
  private String deleteUrl="http://sales-yknx4.rhcloud.com/product/";

  public ProductsSpiceRequest(@NonNull String paramString)
  {
    super(JsonArray.class);
    this.userid = paramString;
    setHttpRequestFactory(GoogleHttpClientSpiceService.createRequestFactory());
  }

  public JsonArray loadDataFromNetwork()
    throws IOException
  {
    Ln.d("Call web service " + this.baseUrl, new Object[0]);
    Object localObject = new GenericUrl(this.baseUrl);
    ((GenericUrl)localObject).put("user_id", this.userid);
    Ln.d("User: " + this.userid, new Object[0]);
    Ln.d("Generic URL: " + ((GenericUrl)localObject).toString(), new Object[0]);
    localObject = getHttpRequestFactory().buildGetRequest((GenericUrl)localObject);
    ((HttpRequest)localObject).setParser(new GsonFactory().createJsonObjectParser());
    localObject = ((HttpRequest)localObject).execute();
    return (JsonArray)(JsonArray)new Gson().fromJson(new InputStreamReader(((HttpResponse)localObject).getContent()), JsonArray.class);
  }

  @Override
  public Boolean insertData() {
    return null;
  }

  @Override
  public Boolean updateData(JSONObject data) {
    return null;
  }

  @Override
  public JsonElement updateData(String element, String elementid) throws IOException {
    return null;
  }
  @Override
  public Boolean updateData(String element, String elementid, String token) throws IOException {
    Ln.d("Call web service " + updateUrl);
    GenericUrl gUrl = new GenericUrl(updateUrl+elementid);
    gUrl.put("token",token);
    Ln.d("User: " + userid);
    Ln.d("Generic URL: " + gUrl.toString());
    Ln.d("Inserting: " + element);
    HttpContent json = ByteArrayContent.fromString("application/json", element);
    HttpRequest httpRequest = getHttpRequestFactory().buildPutRequest(gUrl, json);
    httpRequest.setParser(new GsonFactory().createJsonObjectParser());
    //httpRequest.setHeaders(httpRequest.getHeaders().setContentType("application/json"));
    HttpResponse httpResponse = httpRequest.execute();

    return httpResponse.isSuccessStatusCode();
  }
  public JsonElement insertData(String element) throws IOException
  {
    String url = baseUrl;
    Ln.d("Call web service " + url);
    GenericUrl gUrl = new GenericUrl(url);
    Ln.d("User: " + userid);
    Ln.d("Generic URL: " + gUrl.toString());
    Ln.d("Inserting: "+element );
    HttpContent json = ByteArrayContent.fromString("application/json", element);
    HttpRequest httpRequest = getHttpRequestFactory().buildPostRequest(gUrl, json);
    httpRequest.setParser(new GsonFactory().createJsonObjectParser());
    //httpRequest.setHeaders(httpRequest.getHeaders().setContentType("application/json"));
    HttpResponse httpResponse = httpRequest.execute();
    JsonElement response = new Gson().fromJson(new InputStreamReader(httpResponse.getContent()), JsonElement.class);
    Ln.d("JSON Response: "+response.toString());
    return response;
  }

  public Boolean deleteFromNetwork(String client_id,String token) throws IOException {
    Ln.d("Call web service " + this.deleteUrl + client_id);
    Object localObject = new GenericUrl(this.deleteUrl+client_id);
    ((GenericUrl)localObject).put("token", token);
    Ln.d("Generic URL: " + localObject.toString());
    localObject = getHttpRequestFactory().buildDeleteRequest((GenericUrl) localObject);
    ((HttpRequest)localObject).setParser(new GsonFactory().createJsonObjectParser());
    localObject = ((HttpRequest)localObject).execute();
    return new Gson().fromJson(new InputStreamReader(((HttpResponse)localObject).getContent()), JsonObject.class).get("ok").getAsInt()>0;
  }
  @Override
  public Boolean updateData(String[] fields, String[] values, String data) {
    return null;
  }

  @Override
  public Boolean deleteData(String id) {
    return null;
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.ProductsSpiceRequest
 * JD-Core Version:    0.6.0
 */
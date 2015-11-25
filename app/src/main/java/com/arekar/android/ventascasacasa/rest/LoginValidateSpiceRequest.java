package com.arekar.android.ventascasacasa.rest;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;

import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.io.InputStreamReader;
import roboguice.util.temp.Ln;

/**
 * The type Login validate spice request.
 * This is used to validate a token in the Web Service
 */
public class LoginValidateSpiceRequest extends JsonSpiceRequest<JsonObject>
{
  private String baseUrl = "http://sales-yknx4.rhcloud.com/login";


  /**
   * Instantiates a new Login validate spice request.
   *
   * @param token the token
   */
  public LoginValidateSpiceRequest(String token)
  {
    super(JsonObject.class, token);

  }

  public JsonObject loadDataFromNetwork()
    throws IOException
  {
    Ln.d("Call web service " + this.baseUrl);
    GenericUrl localObject = new GenericUrl(this.baseUrl);
    localObject.put("token", getToken());
    Ln.d("Token: " + getToken());
    Ln.d("Generic URL: " + localObject.toString());
    return loadDataFromNetwork(localObject).getAsJsonObject();
  }

  @Override
  public Boolean updateData(String element, String elementId) throws IOException {
    return null;
  }

  @Override
  public JsonElement insertData(String data) throws IOException {
    throw new NotImplementedException("You shall not pass!");
  }

  @Override
  public boolean deleteFromNetwork(String elementid) throws IOException {
    return false;
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.LoginValidateSpiceRequest
 * JD-Core Version:    0.6.0
 */
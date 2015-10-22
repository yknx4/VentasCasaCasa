package com.arekar.android.ventascasacasa.rest;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import roboguice.util.temp.Ln;

public class LoginValidateSpiceRequest extends GoogleHttpClientSpiceRequest<JsonObject>
{
  private String baseUrl = "http://sales-yknx4.rhcloud.com/login";
  private String token;

  public LoginValidateSpiceRequest(String paramString)
  {
    super(JsonObject.class);
    this.token = paramString;
  }

  public JsonObject loadDataFromNetwork()
    throws IOException
  {
    Ln.d("Call web service " + this.baseUrl, new Object[0]);
    Object localObject = new GenericUrl(this.baseUrl);
    ((GenericUrl)localObject).put("token", this.token);
    Ln.d("Token: " + this.token, new Object[0]);
    Ln.d("Generic URL: " + ((GenericUrl)localObject).toString(), new Object[0]);
    localObject = getHttpRequestFactory().buildGetRequest((GenericUrl)localObject);
    ((HttpRequest)localObject).setParser(new GsonFactory().createJsonObjectParser());
    localObject = ((HttpRequest)localObject).execute();
    return (JsonObject)(JsonObject)new Gson().fromJson(new InputStreamReader(((HttpResponse)localObject).getContent()), JsonObject.class);
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.LoginValidateSpiceRequest
 * JD-Core Version:    0.6.0
 */
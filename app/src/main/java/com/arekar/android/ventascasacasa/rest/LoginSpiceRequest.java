package com.arekar.android.ventascasacasa.rest;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.testing.http.MockHttpContent;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import roboguice.util.temp.Ln;

public class LoginSpiceRequest extends GoogleHttpClientSpiceRequest<JsonObject>
{
  private String baseUrl = "http://sales-yknx4.rhcloud.com/login";
  private String password;
  private String user;

  public LoginSpiceRequest(String paramString1, String paramString2)
  {
    super(JsonObject.class);
    this.user = paramString1;
    this.password = paramString2;
  }

  public JsonObject loadDataFromNetwork()
    throws IOException
  {
    Ln.d("Call web service " + this.baseUrl, new Object[0]);
    Object localObject = new GenericUrl(this.baseUrl);
    ((GenericUrl)localObject).put("user", this.user);
    ((GenericUrl)localObject).put("password", this.password);
    Ln.d("User: " + this.user, new Object[0]);
    Ln.d("Generic URL: " + ((GenericUrl)localObject).toString(), new Object[0]);
    MockHttpContent localMockHttpContent = new MockHttpContent();
    localObject = getHttpRequestFactory().buildPostRequest((GenericUrl)localObject, localMockHttpContent);
    ((HttpRequest)localObject).setParser(new GsonFactory().createJsonObjectParser());
    localObject = ((HttpRequest)localObject).execute();
    return (JsonObject)(JsonObject)new Gson().fromJson(new InputStreamReader(((HttpResponse)localObject).getContent()), JsonObject.class);
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.LoginSpiceRequest
 * JD-Core Version:    0.6.0
 */
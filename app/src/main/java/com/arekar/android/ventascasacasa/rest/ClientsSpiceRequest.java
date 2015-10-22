package com.arekar.android.ventascasacasa.rest;

import android.support.annotation.NonNull;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.octo.android.robospice.GoogleHttpClientSpiceService;
import com.octo.android.robospice.request.googlehttpclient.GoogleHttpClientSpiceRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import roboguice.util.temp.Ln;

public class ClientsSpiceRequest extends GoogleHttpClientSpiceRequest<JsonArray>
  implements JsonSpiceRequest
{
  private String baseUrl = "http://sales-yknx4.rhcloud.com/client";
  private String userid;

  public ClientsSpiceRequest(@NonNull String paramString)
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
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.ClientsSpiceRequest
 * JD-Core Version:    0.6.0
 */
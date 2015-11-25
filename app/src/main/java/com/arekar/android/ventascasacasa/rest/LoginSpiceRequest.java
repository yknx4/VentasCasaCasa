package com.arekar.android.ventascasacasa.rest;

import com.google.api.client.http.GenericUrl;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;

import roboguice.util.temp.Ln;

/**
 * The type Login spice request.
 * This is used to login to the web service.
 */
public class LoginSpiceRequest extends JsonSpiceRequest<JsonObject>
{
  private String baseUrl = "http://sales-yknx4.rhcloud.com/login";
  private String password;
  private String user;

  /**
   * Instantiates a new Login spice request.
   *
   * @param username the username
   * @param password the password
   */
  public LoginSpiceRequest(String username, String password)
  {
    super(JsonObject.class);
    this.user = username;
    this.password = password;
  }

  public JsonObject loadDataFromNetwork()
    throws IOException
  {
    Ln.d("Call web service " + this.baseUrl, new Object[0]);
    GenericUrl localObject = new GenericUrl(this.baseUrl);
    localObject.put("user", this.user);
    localObject.put("password", this.password);
    Ln.d("User: " + this.user, new Object[0]);
    Ln.d("Generic URL: " + localObject.toString(), new Object[0]);

    return loadDataFromNetwork(localObject,RequestMethod.post,null).getAsJsonObject();
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
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.LoginSpiceRequest
 * JD-Core Version:    0.6.0
 */
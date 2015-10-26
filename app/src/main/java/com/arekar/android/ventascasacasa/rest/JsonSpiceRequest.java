package com.arekar.android.ventascasacasa.rest;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.io.IOException;

public interface JsonSpiceRequest
{
  JsonArray loadDataFromNetwork()
    throws IOException;
  Boolean insertData();
  Boolean updateData(JSONObject data);
  JsonElement updateData(String id, String data) throws IOException;
  Boolean updateData(String field, String value, String data) throws IOException;
  Boolean updateData(String[] fields, String values[], String data);
  Boolean deleteData(String id);
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.JsonSpiceRequest
 * JD-Core Version:    0.6.0
 */
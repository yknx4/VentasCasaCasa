package com.arekar.android.ventascasacasa.rest;

import com.google.gson.JsonArray;
import java.io.IOException;

public abstract interface JsonSpiceRequest
{
  public abstract JsonArray loadDataFromNetwork()
    throws IOException;
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.rest.JsonSpiceRequest
 * JD-Core Version:    0.6.0
 */
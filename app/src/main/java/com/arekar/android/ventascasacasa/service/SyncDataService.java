package com.arekar.android.ventascasacasa.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonColumns;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonContentValues;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonCursor;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonSelection;
import com.arekar.android.ventascasacasa.rest.ClientsSpiceRequest;
import com.arekar.android.ventascasacasa.rest.JsonSpiceRequest;
import com.arekar.android.ventascasacasa.rest.ProductsSpiceRequest;
import com.google.api.client.http.GenericUrl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.builder.Builders.Any.B;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class SyncDataService extends IntentService
{
  private static final String ACTION_FETCH_ALL = "com.arekar.android.ventascasacasa.service.action.FETCH_ALL";
  private static final String ACTION_FETCH_CLIENTS = "com.arekar.android.ventascasacasa.service.action.FETCH_CLIENTS";
  private static final String ACTION_FETCH_PRODUCTS = "com.arekar.android.ventascasacasa.service.action.FETCH_PRODUCTS";
  private static final String EXTRA_PARAM2 = "com.arekar.android.ventascasacasa.service.extra.PARAM2";
  private static final String EXTRA_PARAM_USER_ID = "com.arekar.android.ventascasacasa.service.extra.PARAM_USER_ID";
  private static final String LOG_TAG = SyncDataService.class.getSimpleName();
  private static final Map<Long, String> paths = new HashMap(2);

  static
  {
    paths.put(JsonColumns.ROW_CLIENTS_ID, "/client");
    paths.put(JsonColumns.ROW_PRODUCTS_ID, "/product");
  }

  public SyncDataService()
  {
    super("SyncDataService");
  }

  private Updated checkIfUpdatedJson(String url, long row_id)
  {
    Log.d(LOG_TAG, "chechIfUpdatedJson(): " + url);
    boolean updated = false;
      Log.d(LOG_TAG, "HEAD");
    Response paramString1 = null;
    try {
      paramString1 = (Response)((B) Ion.with(getBaseContext()).load("HEAD", url)).asString().withResponse().get();
      Log.d(LOG_TAG, "RESULT");
      String localObject = paramString1.getHeaders().getHeaders().get("Last-Modified");
      Log.d(LOG_TAG, "Last-mod: " + (String)localObject);
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss Z", Locale.US);
      Date newD = localSimpleDateFormat.parse(localObject);
      JsonSelection sel = new JsonSelection().id(row_id);
      Cursor c = sel.query(this);
      if(c== null){
        return new Updated(true,newD);
      }
      JsonCursor jc = new JsonCursor(c);

      if(jc.move((int) row_id)) {
        if (newD.after(jc.getLastModified())) {
          return new Updated(true, newD);
        }
        else {
          return new Updated(false, null);
        }
      }
      else {
        return new Updated(true, newD);
      }



    } catch (ParseException e) {
      e.printStackTrace();

    }
    catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();

    }
    return new Updated(false,null);

  }



  private String getUrl(@NonNull String userid, long row_id)
  {
    if(userid.isEmpty()) throw new InvalidParameterException("userid cannot be empty");
    GenericUrl localGenericUrl = new GenericUrl("http://sales-yknx4.rhcloud.com" + paths.get(row_id));
    localGenericUrl.put("user_id", userid);
    return localGenericUrl.build();
  }

  private void handleActionFetchClients(String userId)
  {
    Updated localUpdated = checkIfUpdatedJson(getUrl(userId, JsonColumns.ROW_CLIENTS_ID), JsonColumns.ROW_CLIENTS_ID);
    if (localUpdated.isUpdated())
      syncData(userId, localUpdated.getWhen(), JsonColumns.ROW_CLIENTS_ID, new ClientsSpiceRequest(userId));
  }

  private void handleActionFetchProducts(String userid)
  {
    Updated localUpdated = checkIfUpdatedJson(getUrl(userid,JsonColumns.ROW_PRODUCTS_ID), JsonColumns.ROW_PRODUCTS_ID);
    if (localUpdated.isUpdated())
      syncData(userid, localUpdated.getWhen(), JsonColumns.ROW_PRODUCTS_ID, new ProductsSpiceRequest(userid));
  }

  public static void startActionFetchAll(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent(paramContext, SyncDataService.class);
    localIntent.setAction("com.arekar.android.ventascasacasa.service.action.FETCH_ALL");
    localIntent.putExtra("com.arekar.android.ventascasacasa.service.extra.PARAM_USER_ID", paramString);
    paramContext.startService(localIntent);
  }

  public static void startActionFetchClients(Context paramContext, String paramString)
  {
    Log.d(LOG_TAG, "Fetching Clients");
    Intent localIntent = new Intent(paramContext, SyncDataService.class);
    localIntent.setAction("com.arekar.android.ventascasacasa.service.action.FETCH_CLIENTS");
    localIntent.putExtra("com.arekar.android.ventascasacasa.service.extra.PARAM_USER_ID", paramString);
    paramContext.startService(localIntent);
  }

  public static void startActionFetchProducts(Context paramContext, String paramString)
  {
    Log.d(LOG_TAG, "Fetching Products");
    Intent localIntent = new Intent(paramContext, SyncDataService.class);
    localIntent.setAction("com.arekar.android.ventascasacasa.service.action.FETCH_PRODUCTS");
    localIntent.putExtra("com.arekar.android.ventascasacasa.service.extra.PARAM_USER_ID", paramString);
    paramContext.startService(localIntent);
  }

  private void syncData(String userid, Date date, long row_id, JsonSpiceRequest paramJsonSpiceRequest)
  {
    if(row_id<0) throw new InvalidParameterException("ID must be greater than 0");
    Log.d(LOG_TAG, "Start");
    ContentResolver cr = getContentResolver();
    try
    {
      JsonArray ja = paramJsonSpiceRequest.loadDataFromNetwork();
      String js = new Gson().toJson(ja);
      Log.d(LOG_TAG, "Json: " + paramJsonSpiceRequest);
      JsonContentValues localJsonContentValues = new JsonContentValues();
      localJsonContentValues.putId(row_id);
      localJsonContentValues.putData(js);
      localJsonContentValues.putLastModified(date);
      localJsonContentValues.putMd5("");
      localJsonContentValues.putUserId(userid);
      Uri r = localJsonContentValues.insert(cr);
      Log.d(LOG_TAG, "Response: " + r);
      Log.d(LOG_TAG, "Finished");

    }
    catch (IOException e)
    {

       e.printStackTrace();
    }
  }

  protected void onHandleIntent(Intent paramIntent)
  {

    String act = paramIntent.getAction();
    String userId = paramIntent.getStringExtra(EXTRA_PARAM_USER_ID);
    Log.d(LOG_TAG,"Intent with userid: "+userId);

    switch (act){
      case ACTION_FETCH_PRODUCTS:
        handleActionFetchProducts(userId);
        break;
      case ACTION_FETCH_CLIENTS:
        handleActionFetchClients(userId);
        break;
      case ACTION_FETCH_ALL:
        handleActionFetchProducts(userId);
        handleActionFetchClients(userId);
        break;
    }
  }

  private class Updated
  {
    private boolean updated;
    private Date when;

    public Updated(boolean paramDate, Date arg3)
    {
      this.updated = paramDate;
      this.when = arg3;
    }
    public Updated(boolean param){
      this.updated = param;

      this.when = null;
    }

    public Date getWhen()
    {
      if (!this.updated)
        return null;
      return this.when;
    }

    public boolean isUpdated()
    {
      return this.updated;
    }
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasacasa.service.SyncDataService
 * JD-Core Version:    0.6.0
 */
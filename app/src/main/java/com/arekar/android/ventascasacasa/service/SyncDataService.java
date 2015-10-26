package com.arekar.android.ventascasacasa.service;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.activities.AddClientActivity;
import com.arekar.android.ventascasacasa.activities.AddSaleActivity;
import com.arekar.android.ventascasacasa.model.Client;
import com.arekar.android.ventascasacasa.model.Payment;
import com.arekar.android.ventascasacasa.model.Product;
import com.arekar.android.ventascasacasa.model.Sale;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonColumns;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonContentValues;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonCursor;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonSelection;
import com.arekar.android.ventascasacasa.rest.ClientsSpiceRequest;
import com.arekar.android.ventascasacasa.rest.GoogleGeocodingSpiceRequest;
import com.arekar.android.ventascasacasa.rest.JsonSpiceRequest;
import com.arekar.android.ventascasacasa.rest.PaymentsSpiceRequest;
import com.arekar.android.ventascasacasa.rest.ProductsSpiceRequest;
import com.arekar.android.ventascasacasa.rest.SalesSpiceRequest;
import com.google.api.client.http.GenericUrl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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

public class SyncDataService extends IntentService {
    public static final String MSG_TYPE_PAYMENT = "TYPE.PAYMENT";
    public static final String MSG_TYPE_CLIENT = "TYPE.CLIENT";
    public static final String MSG_TYPE_PRODUCT = "TYPE.PRODUCT";
    public static final String MSG_PARAM_TYPE = "MSG_PARAM_TYPE";
    public static final String MSG_ADDED = "MSG_ADDED";
    public static final String MSG_TYPE_SALE = "MSG_TYPE_SALE";
    private static final String ACTION_FETCH_ALL = "com.arekar.android.ventascasacasa.service.action.FETCH_ALL";
    private static final String ACTION_FETCH_CLIENTS = "com.arekar.android.ventascasacasa.service.action.FETCH_CLIENTS";
    private static final String ACTION_FETCH_PRODUCTS = "com.arekar.android.ventascasacasa.service.action.FETCH_PRODUCTS";
    private static final String ACTION_FETCH_SALES = "com.arekar.android.ventascasacasa.service.action.FETCH_SALES";
    private static final String ACTION_FETCH_PAYMENTS = "com.arekar.android.ventascasacasa.service.action.FETCH_PAYMENTS";
    private static final String ACTION_DO_PAYMENT = "com.arekar.android.ventascasacasa.service.action.ACTION_DO_PAYMENT";
    private static final String ACTION_ADD_CLIENT = "com.arekar.android.ventascasacasa.service.action.ACTION_CREATE_CLIENT";
    private static final String ACTION_ADD_SALE = "com.arekar.android.ventascasacasa.service.action.ACTION_ADD_SALE";
    private static final String ACTION_DELETE_CLIENT = "com.arekar.android.ventascasacasa.service.action.ACTION_DELETE_CLIENT";
    private static final String ACTION_UPDATE_CLIENT = "com.arekar.android.ventascasacasa.service.action.ACTION_UPDATE_CLIENT";
    private static final String ACTION_ADD_PRODUCT = "com.arekar.android.ventascasacasa.service.action.ACTION_CREATE_PRODUCT";
    private static final String ACTION_DELETE_PRODUCT = "com.arekar.android.ventascasacasa.service.action.ACTION_DELETE_PRODUCT";
    private static final String ACTION_UPDATE_PRODUCT = "com.arekar.android.ventascasacasa.service.action.ACTION_UPDATE_PRODUCT";
    private static final String EXTRA_PARAM_SALE_ID = "com.arekar.android.ventascasacasa.service.extra.PARAM_SALE_ID";
    private static final String EXTRA_PARAM_CLIENT_ID = "com.arekar.android.ventascasacasa.service.extra.PARAM_CLIENT_ID";
    private static final String EXTRA_PARAM_PRODUCT_ID = "com.arekar.android.ventascasacasa.service.extra.PARAM_PRODUCT_ID";
    //  private static final String EXTRA_PARAM_TOKEN = "com.arekar.android.ventascasacasa.service.extra.PARAM_TOKEN";
    private static final String EXTRA_PARAM_AMOUNT = "com.arekar.android.ventascasacasa.service.extra.PARAM_AMOUNT";
    private static final String EXTRA_PARAM_MESSENGER = "com.arekar.android.ventascasacasa.service.extra.PARAM_MESSENGER";
    //  private static final String EXTRA_PARAM_USER_ID = "com.arekar.android.ventascasacasa.service.extra.PARAM_USER_ID";
    private static final String LOG_TAG = SyncDataService.class.getSimpleName();
    private static final Map<Long, String> paths = new HashMap(2);
    private static final String EXTRA_PARAM_CLIENT = "EXTRA_PARAM_CLIENT";
    private static final String EXTRA_PARAM_PRODUCT = "EXTRA_PARAM_PRODUCT";
    private static final String EXTRA_PARAM_SALE = "EXTRA_PARAM_PRODUCT";

    static {
        paths.put(JsonColumns.ROW_CLIENTS_ID, Constants.Connections.PATH_CLIENTS);
        paths.put(JsonColumns.ROW_PRODUCTS_ID, Constants.Connections.PATH_PRODUCTS);
        paths.put(JsonColumns.ROW_SALES_ID, Constants.Connections.PATH_SALES);
        paths.put(JsonColumns.ROW_PAYMENTS_ID, Constants.Connections.PATH_PAYMENTS);
    }

    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss Z");
    ///Mon, 26 Oct 2015 05:44:46 GMT
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
    private SharedPreferences sharedPreferences;
    public SyncDataService() {
        super("SyncDataService");
    }

    public static void startActionFetchAll(Context paramContext, String paramString) {
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction("com.arekar.android.ventascasacasa.service.action.FETCH_ALL");
        localIntent.putExtra("com.arekar.android.ventascasacasa.service.extra.PARAM_USER_ID", paramString);
        paramContext.startService(localIntent);
    }

    public static void startActionFetchClients(Context paramContext, String paramString) {
        Log.d(LOG_TAG, "Fetching Clients");
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction("com.arekar.android.ventascasacasa.service.action.FETCH_CLIENTS");
        localIntent.putExtra("com.arekar.android.ventascasacasa.service.extra.PARAM_USER_ID", paramString);
        paramContext.startService(localIntent);
    }

    public static void startActionFetchProducts(Context paramContext, String paramString) {
        Log.d(LOG_TAG, "Fetching Products");
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction("com.arekar.android.ventascasacasa.service.action.FETCH_PRODUCTS");
        localIntent.putExtra("com.arekar.android.ventascasacasa.service.extra.PARAM_USER_ID", paramString);
        paramContext.startService(localIntent);
    }

    public static void startActionFetchPayments(Context paramContext, String paramString) {
        Log.d(LOG_TAG, "Fetching Payments");
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction(ACTION_FETCH_PAYMENTS);
//    localIntent.putExtra(EXTRA_PARAM_USER_ID, paramString);
        paramContext.startService(localIntent);
    }

    public static void startActionFetchSales(Context paramContext, String paramString) {
        Log.d(LOG_TAG, "Fetching Sales");
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction(ACTION_FETCH_SALES);
//    localIntent.putExtra(EXTRA_PARAM_USER_ID, paramString);
        paramContext.startService(localIntent);
    }

    public static void startActionDoPayment(Context paramContext, String user_id, String sale_id, Double amount, Messenger msg) {
        Log.d(LOG_TAG, "Doing Payment: " + amount);
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction(ACTION_DO_PAYMENT);
//    localIntent.putExtra(EXTRA_PARAM_USER_ID, user_id);
        localIntent.putExtra(EXTRA_PARAM_SALE_ID, sale_id);
        localIntent.putExtra(EXTRA_PARAM_AMOUNT, amount);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
        paramContext.startService(localIntent);
    }

    public static void startActionDeleteClient(Context context, String client_id, String token, String userid) {
        Log.d(LOG_TAG, "Deleting client " + client_id);
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_DELETE_CLIENT);
        localIntent.putExtra(EXTRA_PARAM_CLIENT_ID, client_id);
//    localIntent.putExtra(EXTRA_PARAM_TOKEN, token);
//    localIntent.putExtra(EXTRA_PARAM_USER_ID,userid);
        context.startService(localIntent);
    }

    public static void startActionDeleteProduct(Context context, String client_id) {
        Log.d(LOG_TAG, "Deleting product " + client_id);
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_DELETE_PRODUCT);
        localIntent.putExtra(EXTRA_PARAM_PRODUCT_ID, client_id);
        context.startService(localIntent);
    }

    public static void startActionAddClient(Context context, Client client, Messenger msg) {
        Log.d(LOG_TAG, "Adding: " + client.getName());
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_ADD_CLIENT);
        localIntent.putExtra(EXTRA_PARAM_CLIENT, client);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
        context.startService(localIntent);
    }

    public static void startActionAddProduct(Context context, Product product, Messenger msg) {
        Log.d(LOG_TAG, "Adding: " + product.getName());
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_ADD_PRODUCT);
        localIntent.putExtra(EXTRA_PARAM_PRODUCT, product);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
        context.startService(localIntent);
    }

    public static void startActionUpdateClient(Context context, Client client, Messenger msg, String token) {
        Log.d(LOG_TAG, "Adding: " + client.getName());
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_UPDATE_CLIENT);
        localIntent.putExtra(EXTRA_PARAM_CLIENT, client);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
//    localIntent.putExtra(EXTRA_PARAM_TOKEN,token);
        context.startService(localIntent);
    }

    public static void startActionUpdateProduct(Context context, Product product, Messenger msg) {
        Log.d(LOG_TAG, "Adding: " + product.getName());
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_UPDATE_PRODUCT);
        localIntent.putExtra(EXTRA_PARAM_PRODUCT, product);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
        context.startService(localIntent);
    }

    public static void startActionAddSale(Context context, Sale newSale, Messenger messenger) {
        Log.d(LOG_TAG, "Adding sale ");
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_ADD_SALE);
        localIntent.putExtra(EXTRA_PARAM_SALE, newSale);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, messenger);
        context.startService(localIntent);
    }

    private Updated checkIfUpdatedJson(String url, long row_id) {
        Log.d(LOG_TAG, "chechIfUpdatedJson(): " + url);
        boolean updated = false;
        Log.d(LOG_TAG, "HEAD");
        Response paramString1 = null;
        try {
            paramString1 = (Response) ((B) Ion.with(getBaseContext()).load("HEAD", url)).asString().withResponse().get();
            Log.d(LOG_TAG, "RESULT");
            String localObject = paramString1.getHeaders().getHeaders().get("Last-Modified");
            Log.d(LOG_TAG, "Last-mod: " + (String) localObject);
            Date newD = null;
            try {
                newD = localSimpleDateFormat.parse(localObject);
            } catch (ParseException e) {
                try {
                    newD = localSimpleDateFormat2.parse(localObject);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                    return new Updated(true, new Date());
                }
            }


            JsonSelection sel = new JsonSelection().id(row_id);
            Cursor c = sel.query(this);
            if (c == null) {
                return new Updated(true, newD);
            }
            JsonCursor jc = new JsonCursor(c);

            if (jc.move((int) row_id)) {
                if (newD.after(jc.getLastModified())) {
                    return new Updated(true, newD);
                } else {
                    return new Updated(false);
                }
            } else {
                return new Updated(true, newD);
            }


        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
        return new Updated(false);

    }

    private String getUrl(@NonNull String userid, long row_id) {
        if (userid.isEmpty()) throw new InvalidParameterException("userid cannot be empty");
        GenericUrl localGenericUrl = new GenericUrl("http://sales-yknx4.rhcloud.com" + paths.get(row_id));
        localGenericUrl.put("user_id", userid);
        switch ((int) row_id) {
            case (int) JsonColumns.ROW_PRODUCTS_ID:
            case (int) JsonColumns.ROW_CLIENTS_ID:
                localGenericUrl.put("sort", "name");
                break;
        }
        return localGenericUrl.build();
    }

    private void handleActionDeleteClient(Intent inte) {
        String clientid = inte.getStringExtra(EXTRA_PARAM_CLIENT_ID);
//    String token = inte.getStringExtra(EXTRA_PARAM_TOKEN);
//    String userid = inte.getStringExtra(EXTRA_PARAM_USER_ID);
        String token = getToken();
        String userid = getUserId();

        ClientsSpiceRequest req = new ClientsSpiceRequest("");
        try {
            if (req.deleteFromNetwork(clientid, token)) {
                handleActionFetchClients(userid, true);
                Toast.makeText(this, "Client deleted successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Deleting failed.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Deleting failed with IOException", Toast.LENGTH_SHORT).show();
        }

    }

    private void handleActionDeleteProduct(Intent inte) {
        String clientid = inte.getStringExtra(EXTRA_PARAM_PRODUCT_ID);
        String token = getToken();
        String userid = getUserId();

        ProductsSpiceRequest req = new ProductsSpiceRequest(userid);
        try {
            if (req.deleteFromNetwork(clientid, token)) {
                handleActionFetchClients(userid, true);
                Toast.makeText(this, "Product deleted successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Deleting failed.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Deleting failed with IOException", Toast.LENGTH_SHORT).show();
        }

    }

    private void handleActionFetchClients(String userid, boolean b) {
        if (b) {
            syncData(userid, new Date(), JsonColumns.ROW_CLIENTS_ID, new ClientsSpiceRequest(userid));
        } else {
            handleActionFetchClients(userid);
        }
    }

    private void handleActionFetchClients(String userId) {
        Updated localUpdated = checkIfUpdatedJson(getUrl(userId, JsonColumns.ROW_CLIENTS_ID), JsonColumns.ROW_CLIENTS_ID);
        if (localUpdated.isUpdated())
            syncData(userId, localUpdated.getWhen(), JsonColumns.ROW_CLIENTS_ID, new ClientsSpiceRequest(userId));
    }

    private void handleActionFetchSales(String userId) {
        Updated localUpdated = checkIfUpdatedJson(getUrl(userId, JsonColumns.ROW_SALES_ID), JsonColumns.ROW_SALES_ID);
        if (localUpdated.isUpdated())
            syncData(userId, localUpdated.getWhen(), JsonColumns.ROW_SALES_ID, new SalesSpiceRequest(userId));
    }

    private void handleActionFetchProducts(String userid) {
        Updated localUpdated = checkIfUpdatedJson(getUrl(userid, JsonColumns.ROW_PRODUCTS_ID), JsonColumns.ROW_PRODUCTS_ID);
        if (localUpdated.isUpdated())
            syncData(userid, localUpdated.getWhen(), JsonColumns.ROW_PRODUCTS_ID, new ProductsSpiceRequest(userid));
    }

    private void handleActionFetchPayments(String userid) {
        Updated localUpdated = checkIfUpdatedJson(getUrl(userid, JsonColumns.ROW_PAYMENTS_ID), JsonColumns.ROW_PAYMENTS_ID);
        if (localUpdated.isUpdated())
            syncData(userid, localUpdated.getWhen(), JsonColumns.ROW_PAYMENTS_ID, new PaymentsSpiceRequest(userid));
    }

    private void syncData(String userid, Date date, long row_id, JsonSpiceRequest paramJsonSpiceRequest) {
        if (row_id < 0) throw new InvalidParameterException("ID must be greater than 0");
        Log.d(LOG_TAG, "Start");
        ContentResolver cr = getContentResolver();
        try {
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

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences(Constants.APP_PREFERENCES, MODE_PRIVATE);
    }

    protected void onHandleIntent(Intent paramIntent) {

        String act = paramIntent.getAction();
//    String userId = paramIntent.getStringExtra(EXTRA_PARAM_USER_ID);
        String userId = getUserId();
        Log.d(LOG_TAG, "Intent with userid: " + userId);

        switch (act) {
            case ACTION_DO_PAYMENT:
                handleActionDoPayment(paramIntent);
                break;
            case ACTION_ADD_CLIENT:
                handleActionAddClient(paramIntent);
                break;
            case ACTION_UPDATE_CLIENT:
                handleActionUpdateClient(paramIntent);
                break;
            case ACTION_ADD_PRODUCT:
                handleActionAddProduct(paramIntent);
                break;
            case ACTION_ADD_SALE:
                handleActionAddSale(paramIntent);
                break;
            case ACTION_UPDATE_PRODUCT:
                handleActionUpdateProduct(paramIntent);
                break;
            case ACTION_FETCH_PRODUCTS:
                handleActionFetchProducts(userId);
                break;
            case ACTION_FETCH_CLIENTS:
                handleActionFetchClients(userId);
                break;
            case ACTION_FETCH_SALES:
                handleActionFetchSales(userId);
                break;
            case ACTION_FETCH_PAYMENTS:
                handleActionFetchPayments(userId);
                break;
            case ACTION_DELETE_CLIENT:
                handleActionDeleteClient(paramIntent);
                break;
            case ACTION_DELETE_PRODUCT:
                handleActionDeleteProduct(paramIntent);
                break;
            case ACTION_FETCH_ALL:
                handleActionFetchProducts(userId);
                handleActionFetchClients(userId);
                handleActionFetchSales(userId);
                handleActionFetchPayments(userId);
                break;
        }
    }

    private void handleActionAddSale(Intent paramIntent) {
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_SALE);
        data.putBoolean(MSG_ADDED, false);
        Sale sale = paramIntent.getParcelableExtra(EXTRA_PARAM_SALE);
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        SalesSpiceRequest request = new SalesSpiceRequest(sale.getUserId());
        try {
            String clientJson = new Gson().toJson(sale);
            JsonElement response = request.insertData(clientJson);
            JsonObject r = response.getAsJsonObject();
            data.putString("message", "Unknown Error");
            if (r != null) {
                if (r.has("_id")) {
                    data.putString("message", "Sale was added successful.");
                    data.putString("user_id", sale.getUserId());
                    data.putString("_id", r.get("_id").getAsString());

                    data.putBoolean(MSG_ADDED, true);
                } else {
                    data.putString("message", "Cannot insert sale, try again later.");
                }
            }
        } catch (IOException e) {

            data.putString("message", "Couldn't insert sale due to IOException");

            e.printStackTrace();
        }
        msg.setData(data);
        sendMessage(msg, msgr);
    }

    private void handleActionAddClient(Intent paramIntent) {
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_CLIENT);
        data.putBoolean(MSG_ADDED, false);
        Client client = paramIntent.getParcelableExtra(EXTRA_PARAM_CLIENT);
        client.setAddressGPS(new GoogleGeocodingSpiceRequest(client.getAddress()).loadLocationFromNetwork());
        Log.v(LOG_TAG, "GPS for " + client.getName() + ": " + client.getAddressGPS().toString());
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        ClientsSpiceRequest request = new ClientsSpiceRequest(client.getUserId());
        try {
            String clientJson = new Gson().toJson(client);
            JsonElement response = request.insertData(getUrl(client.getUserId(), JsonColumns.ROW_CLIENTS_ID), clientJson);
            JsonObject r = response.getAsJsonObject();
            data.putString("message", "Unknown Error");
            if (r != null) {
                if (r.has("_id")) {
                    data.putString("message", "Client was added successful.");
                    data.putString("user_id", client.getUserId());
                    data.putString("_id", r.get("_id").getAsString());

                    data.putBoolean(MSG_ADDED, true);
                } else {
                    data.putString("message", "Cannot insert client, try again later.");
                }
            }
        } catch (IOException e) {

            data.putString("message", "Couldn't insert client due to IOException");

            e.printStackTrace();
        }
        msg.setData(data);
        sendMessage(msg, msgr);
    }

    private void handleActionAddProduct(Intent paramIntent) {
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_PRODUCT);
        data.putBoolean(MSG_ADDED, false);
        Product product = paramIntent.getParcelableExtra(EXTRA_PARAM_PRODUCT);
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        ProductsSpiceRequest request = new ProductsSpiceRequest(product.getUserId());
        try {
            String clientJson = new Gson().toJson(product);
            JsonElement response = request.insertData(clientJson);
            JsonObject r = response.getAsJsonObject();
            data.putString("message", "Unknown Error");
            if (r != null) {
                if (r.has("_id")) {
                    data.putString("message", "Product was added successful.");
                    data.putString("user_id", product.getUserId());
                    data.putString("_id", r.get("_id").getAsString());

                    data.putBoolean(MSG_ADDED, true);
                } else {
                    data.putString("message", "Cannot insert product, try again later.");
                }
            }
        } catch (IOException e) {

            data.putString("message", "Couldn't insert product due to IOException");

            e.printStackTrace();
        }
        msg.setData(data);
        sendMessage(msg, msgr);
    }

    private void handleActionUpdateClient(Intent paramIntent) {
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_CLIENT);
        data.putBoolean(MSG_ADDED, false);
        Client client = paramIntent.getParcelableExtra(EXTRA_PARAM_CLIENT);
//    String token = paramIntent.getStringExtra(EXTRA_PARAM_TOKEN);
        String token = getToken();
        String userid = client.getUserId();
        String clientid = client.getId();
        client.setAddressGPS(new GoogleGeocodingSpiceRequest(client.getAddress()).loadLocationFromNetwork());
        Log.v(LOG_TAG, "GPS for " + client.getName() + ": " + client.getAddressGPS().toString());
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        ClientsSpiceRequest request = new ClientsSpiceRequest(userid);
        try {
            client.setId(null);
            String clientJson = new Gson().toJson(client);
            Boolean response = request.updateData(clientJson, clientid, token);

            data.putString("message", "Unknown Error");
            if (response) {

                data.putString("message", "Client was updated successful.");
                data.putString("user_id", userid);
                data.putString("_id", clientid);

                data.putBoolean(MSG_ADDED, true);
            } else {
                data.putString("message", "Cannot insert client, try again later.");
            }

        } catch (IOException e) {

            data.putString("message", "Couldn't insert client due to IOException");

            e.printStackTrace();
        }
        msg.setData(data);
        sendMessage(msg, msgr);
    }

    private void handleActionUpdateProduct(Intent paramIntent) {
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_PRODUCT);
        data.putBoolean(MSG_ADDED, false);
        Product product = paramIntent.getParcelableExtra(EXTRA_PARAM_PRODUCT);
        String token = getToken();
        String userid = product.getUserId();
        String productId = product.getId();
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        ProductsSpiceRequest request = new ProductsSpiceRequest(userid);
        try {
            product.setId(null);
            String clientJson = new Gson().toJson(product);
            Boolean response = request.updateData(clientJson, productId, token);
            data.putString("message", "Unknown Error");
            if (response) {

                data.putString("message", "Product was updated successful.");
                data.putString("user_id", userid);
                data.putString("_id", productId);

                data.putBoolean(MSG_ADDED, true);
            } else {
                data.putString("message", "Cannot insert product, try again later.");
            }

        } catch (IOException e) {

            data.putString("message", "Couldn't insert product due to IOException");

            e.printStackTrace();
        }
        msg.setData(data);
        sendMessage(msg, msgr);
    }

    private void handleActionDoPayment(Intent paramIntent) {
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_PAYMENT);
        data.putBoolean("paid", false);
        //String userId = paramIntent.getStringExtra(EXTRA_PARAM_USER_ID);/
        String userId = getUserId();
        String saleId = paramIntent.getStringExtra(EXTRA_PARAM_SALE_ID);
        Double amount = paramIntent.getDoubleExtra(EXTRA_PARAM_AMOUNT, 0);
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        Payment pay = new Payment();
        pay.setDate(String.valueOf(new Date().getTime()));
        pay.setUserId(userId);
        pay.setPrice(amount);
        pay.setSaleId(saleId);
        PaymentsSpiceRequest request = new PaymentsSpiceRequest(userId);
        try {
            String payment = new Gson().toJson(pay);
            JsonElement response = request.insertData(getUrl(userId, JsonColumns.ROW_PAYMENTS_ID), payment);
            JsonObject r = response.getAsJsonObject();
            data.putString("message", "Unknown Error");
            if (r != null) {
                if (r.has("_id")) {
                    data.putString("message", "Payment was successful.");
                    data.putString("user_id", userId);
                    data.putString("sale_id", saleId);
                    data.putString("_id", r.get("_id").getAsString());
                    data.putDouble("amount", amount);
                    data.putBoolean("paid", true);
                } else {
                    data.putString("message", "Cannot do payment, try again later.");
                }
            }
        } catch (IOException e) {

            data.putString("message", "Couldn't do payment due to IOException");

            e.printStackTrace();
        }
        msg.setData(data);
        sendMessage(msg, msgr);
    }

    private void sendMessage(Message ifExists, Messenger messenger) {
        String msg = ifExists.getData().getString("message");
        if (messenger != null) {
            try {
                messenger.send(ifExists);
            } catch (RemoteException e) {
                e.printStackTrace();
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMessage(String message, Messenger messenger) {
        Message m = new Message();
        Bundle b = new Bundle();
        b.putString("message", message);
        m.setData(b);
        sendMessage(m, messenger);
    }

    protected String getUserId() {
        return this.sharedPreferences.getString(Constants.Preferences.USER_ID, getString(R.string.static_user_id));
    }

    protected String getToken() {
        return this.sharedPreferences.getString(Constants.Preferences.TOKEN, getString(R.string.static_token));
    }

    private class Updated {
        private boolean updated;
        private Date when;

        public Updated(boolean paramDate, Date arg3) {
            this.updated = paramDate;
            this.when = arg3;
        }

        public Updated(boolean param) {
            this.updated = param;

            this.when = null;
        }

        public Date getWhen() {
            if (!this.updated)
                return null;
            return this.when;
        }

        public boolean isUpdated() {
            return this.updated;
        }
    }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasacasa.service.SyncDataService
 * JD-Core Version:    0.6.0
 */
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
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * The type Sync data service used for synchronizing data from the web service and data in the application.
 */
public class SyncDataService extends IntentService {
    /**
     * The constant MSG_TYPE_PAYMENT.
     */
    public static final String MSG_TYPE_PAYMENT = "TYPE.PAYMENT";
    /**
     * The constant MSG_TYPE_CLIENT.
     */
    public static final String MSG_TYPE_CLIENT = "TYPE.CLIENT";
    /**
     * The constant MSG_TYPE_PRODUCT.
     */
    public static final String MSG_TYPE_PRODUCT = "TYPE.PRODUCT";
    /**
     * The constant MSG_PARAM_TYPE.
     */
    public static final String MSG_PARAM_TYPE = "MSG_PARAM_TYPE";
    /**
     * The constant MSG_ADDED.
     */
    public static final String MSG_ADDED = "MSG_ADDED";
    /**
     * The constant MSG_TYPE_SALE.
     */
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

    /**
     * The Local simple date format for parsing HTTP Headers Date.
     */
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss Z");
    /**
     * The Local simple date format for parsing HTTP Headers Date.
     */
///Mon, 26 Oct 2015 05:44:46 GMT
    SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
    private SharedPreferences sharedPreferences;

    /**
     * Instantiates a new Sync data service.
     */
    public SyncDataService() {
        super("SyncDataService");
    }

    /**
     * Start the action of fetching all.
     *
     * @param paramContext the context of the intent
     */
    public static void startActionFetchAll(Context paramContext) {
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction("com.arekar.android.ventascasacasa.service.action.FETCH_ALL");

        paramContext.startService(localIntent);
    }

    /**
     * Start the action of fetching clients.
     *
     * @param paramContext the context of the intent
     */
    public static void startActionFetchClients(Context paramContext) {
        Log.d(LOG_TAG, "Fetching Clients");
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction("com.arekar.android.ventascasacasa.service.action.FETCH_CLIENTS");

        paramContext.startService(localIntent);
    }

    /**
     * Start the action of fetching products.
     *
     * @param paramContext the context of the intent
     */
    public static void startActionFetchProducts(Context paramContext) {
        Log.d(LOG_TAG, "Fetching Products");
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction("com.arekar.android.ventascasacasa.service.action.FETCH_PRODUCTS");

        paramContext.startService(localIntent);
    }

    /**
     * Start the action of fetching payments.
     *
     * @param paramContext the context of the intent
     */
    public static void startActionFetchPayments(Context paramContext) {
        Log.d(LOG_TAG, "Fetching Payments");
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction(ACTION_FETCH_PAYMENTS);
//    localIntent.putExtra(EXTRA_PARAM_USER_ID, paramString);
        paramContext.startService(localIntent);
    }

    /**
     * Start the action of fetching sales.
     *
     * @param paramContext the context of the intent
     */
    public static void startActionFetchSales(Context paramContext) {
        Log.d(LOG_TAG, "Fetching Sales");
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction(ACTION_FETCH_SALES);
//    localIntent.putExtra(EXTRA_PARAM_USER_ID, paramString);
        paramContext.startService(localIntent);
    }

    /**
     * Start action to do payments.
     * This action will register a payment in the JSON Web Service.
     *
     * @param paramContext the context of the intent
     * @param sale_id      the sale id
     * @param amount       the amount
     * @param msg          the message
     */
    public static void startActionDoPayment(Context paramContext, String sale_id, Double amount, Messenger msg) {
        Log.d(LOG_TAG, "Doing Payment: " + amount);
        Intent localIntent = new Intent(paramContext, SyncDataService.class);
        localIntent.setAction(ACTION_DO_PAYMENT);
//    localIntent.putExtra(EXTRA_PARAM_USER_ID, user_id);
        localIntent.putExtra(EXTRA_PARAM_SALE_ID, sale_id);
        localIntent.putExtra(EXTRA_PARAM_AMOUNT, amount);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
        paramContext.startService(localIntent);
    }

    /**
     * Start action delete client.
     *
     * @param context   the context
     * @param client_id the client id
     */
    public static void startActionDeleteClient(Context context, String client_id) {
        Log.d(LOG_TAG, "Deleting client " + client_id);
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_DELETE_CLIENT);
        localIntent.putExtra(EXTRA_PARAM_CLIENT_ID, client_id);
//    localIntent.putExtra(EXTRA_PARAM_TOKEN, token);
//    localIntent.putExtra(EXTRA_PARAM_USER_ID,userid);
        context.startService(localIntent);
    }

    /**
     * Start action delete product.
     *
     * @param context   the context
     * @param product_id the client id
     */
    public static void startActionDeleteProduct(Context context, String product_id) {
        Log.d(LOG_TAG, "Deleting product " + product_id);
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_DELETE_PRODUCT);
        localIntent.putExtra(EXTRA_PARAM_PRODUCT_ID, product_id);
        context.startService(localIntent);
    }

    /**
     * Start action add client.
     *
     * @param context the context
     * @param client  the client
     * @param msg     the msg
     */
    public static void startActionAddClient(Context context, Client client, Messenger msg) {
        Log.d(LOG_TAG, "Adding: " + client.getName());
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_ADD_CLIENT);
        localIntent.putExtra(EXTRA_PARAM_CLIENT, client);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
        context.startService(localIntent);
    }

    /**
     * Start action add product.
     *
     * @param context the context
     * @param product the product
     * @param msg     the msg
     */
    public static void startActionAddProduct(Context context, Product product, Messenger msg) {
        Log.d(LOG_TAG, "Adding: " + product.getName());
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_ADD_PRODUCT);
        localIntent.putExtra(EXTRA_PARAM_PRODUCT, product);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
        context.startService(localIntent);
    }

    /**
     * Start action update client.
     *
     * @param context the context
     * @param client  the client
     * @param msg     the msg
     */
    public static void startActionUpdateClient(Context context, Client client, Messenger msg) {
        Log.d(LOG_TAG, "Adding: " + client.getName());
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_UPDATE_CLIENT);
        localIntent.putExtra(EXTRA_PARAM_CLIENT, client);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
//    localIntent.putExtra(EXTRA_PARAM_TOKEN,token);
        context.startService(localIntent);
    }

    /**
     * Start action update product.
     *
     * @param context the context
     * @param product the product
     * @param msg     the msg
     */
    public static void startActionUpdateProduct(Context context, Product product, Messenger msg) {
        Log.d(LOG_TAG, "Adding: " + product.getName());
        Intent localIntent = new Intent(context, SyncDataService.class);
        localIntent.setAction(ACTION_UPDATE_PRODUCT);
        localIntent.putExtra(EXTRA_PARAM_PRODUCT, product);
        localIntent.putExtra(EXTRA_PARAM_MESSENGER, msg);
        context.startService(localIntent);
    }

    /**
     * Start action add sale.
     *
     * @param context   the context
     * @param newSale   the new sale
     * @param messenger the messenger
     */
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
            paramString1 = (Response) Ion.with(getBaseContext()).load("HEAD", url).asString().withResponse().get();
            Log.d(LOG_TAG, "RESULT");
            String localObject = paramString1.getHeaders().getHeaders().get("Last-Modified");
            Log.d(LOG_TAG, "Last-mod: " + localObject);
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
        String token = getToken();
        String userid = getUserId();
        ClientsSpiceRequest req = new ClientsSpiceRequest(userid,token);
        if(handleActionDelete("Client", clientid,req)){
            handleActionFetchClients();
        }
    }

    private boolean handleActionDelete(String title, String id, JsonSpiceRequest req){
        try {
            if (req.deleteFromNetwork(id)) {
                Toast.makeText(this, title+" deleted successful", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(this, "Deleting failed.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Deleting failed with IOException", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void handleActionDeleteProduct(Intent inte) {
        String productid = inte.getStringExtra(EXTRA_PARAM_PRODUCT_ID);
        String token = getToken();
        String userid = getUserId();

        ProductsSpiceRequest req = new ProductsSpiceRequest(token,userid);
        if(handleActionDelete("Product", productid,req)){
            handleActionFetchProducts();
        }

    }

    private void handleActionFetchClients(String userid, boolean b) {
        if (b) {
            syncData(userid, new Date(), JsonColumns.ROW_CLIENTS_ID, new ClientsSpiceRequest(getToken(),userid));
        } else {
            handleActionFetchClients();
        }
    }

    private void handleActionFetchClients() {
        String userId = getUserId();
        String token = getToken();
        Updated localUpdated = checkIfUpdatedJson(getUrl(userId, JsonColumns.ROW_CLIENTS_ID), JsonColumns.ROW_CLIENTS_ID);
        if (localUpdated.isUpdated())
            syncData(userId, localUpdated.getWhen(), JsonColumns.ROW_CLIENTS_ID, new ClientsSpiceRequest(userId,token));
    }

    private void handleActionFetchSales() {
        String userId = getUserId();
        String token = getToken();
        Updated localUpdated = checkIfUpdatedJson(getUrl(userId, JsonColumns.ROW_SALES_ID), JsonColumns.ROW_SALES_ID);
        if (localUpdated.isUpdated())
            syncData(userId, localUpdated.getWhen(), JsonColumns.ROW_SALES_ID, new SalesSpiceRequest(token,userId));
    }

    private void handleActionFetchProducts( ){
        String userid = getUserId();
        String token = getToken();
        Updated localUpdated = checkIfUpdatedJson(getUrl(userid, JsonColumns.ROW_PRODUCTS_ID), JsonColumns.ROW_PRODUCTS_ID);
        if (localUpdated.isUpdated())
            syncData(userid, localUpdated.getWhen(), JsonColumns.ROW_PRODUCTS_ID, new ProductsSpiceRequest(token,userid));
    }

    private void handleActionFetchPayments() {
        String userid = getUserId();
        String token = getToken();
        Updated localUpdated = checkIfUpdatedJson(getUrl(userid, JsonColumns.ROW_PAYMENTS_ID), JsonColumns.ROW_PAYMENTS_ID);
        if (localUpdated.isUpdated())
            syncData(userid, localUpdated.getWhen(), JsonColumns.ROW_PAYMENTS_ID, new PaymentsSpiceRequest(token,userid));
    }

    private void syncData(String userid, Date date, long row_id, JsonSpiceRequest paramJsonSpiceRequest) {
        if (row_id < 0) throw new InvalidParameterException("ID must be greater than 0");
        Log.d(LOG_TAG, "Start");
        ContentResolver cr = getContentResolver();
        try {
            JsonArray ja = paramJsonSpiceRequest.loadDataFromNetwork().getAsJsonArray();
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
//        String userId = getUserId();
//        Log.d(LOG_TAG, "Intent with userid: " + userId);

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
                handleActionFetchProducts();
                break;
            case ACTION_FETCH_CLIENTS:
                handleActionFetchClients();
                break;
            case ACTION_FETCH_SALES:
                handleActionFetchSales();
                break;
            case ACTION_FETCH_PAYMENTS:
                handleActionFetchPayments();
                break;
            case ACTION_DELETE_CLIENT:
                handleActionDeleteClient(paramIntent);
                break;
            case ACTION_DELETE_PRODUCT:
                handleActionDeleteProduct(paramIntent);
                break;
            case ACTION_FETCH_ALL:
                handleActionFetchProducts();
                handleActionFetchClients();
                handleActionFetchSales();
                handleActionFetchPayments();
                break;
        }
    }


    private boolean handleActionAdd(String title, Messenger msgr, JsonSpiceRequest req, Bundle data, String toInsert){
        Message msg = new Message();
        try {
            JsonElement response = req.insertData(toInsert);
            JsonObject r = response.getAsJsonObject();
            data.putString("message", "Unknown Error");
            if (r != null) {
                if (r.has("_id")) {
                    data.putString("message", title+" was added successful.");
                    data.putString("user_id", getUserId());
                    data.putString("_id", r.get("_id").getAsString());
                    data.putString("json",r.toString());
                    data.putBoolean(MSG_ADDED, true);
                } else {
                    data.putString("message", "Cannot insert, try again later.");
                }
            }
        } catch (IOException e) {
            data.putString("message", "Couldn't insert due to IOException");
            e.printStackTrace();
        }
        msg.setData(data);
        sendMessage(msg, msgr);
        return data.getBoolean(MSG_ADDED);
    }

    private boolean handleActionUpdate(String title,String elementid, Messenger msgr, JsonSpiceRequest req, Bundle data, String toInsert){
        Message msg = new Message();
        try {
            Boolean response = req.updateData(toInsert, elementid);

            data.putString("message", "Unknown Error");
            if (response) {

                data.putString("message", title+" was updated successful.");
                data.putString("user_id", getUserId());
                data.putString("_id", elementid);

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
        return data.getBoolean(MSG_ADDED);
    }

    private void handleActionAddSale(Intent paramIntent) {

        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_SALE);
        data.putBoolean(MSG_ADDED, false);
        Sale sale = paramIntent.getParcelableExtra(EXTRA_PARAM_SALE);
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        SalesSpiceRequest request = new SalesSpiceRequest(getToken(),getUserId());
        String saleJson = new Gson().toJson(sale);
        handleActionAdd("Sale",msgr,request,data,saleJson);
    }

    private void handleActionAddClient(Intent paramIntent) {
        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_CLIENT);
        data.putBoolean(MSG_ADDED, false);
        Client client = paramIntent.getParcelableExtra(EXTRA_PARAM_CLIENT);
        client.setAddressGPS(new GoogleGeocodingSpiceRequest(client.getAddress()).loadLocationFromNetwork());
        Log.v(LOG_TAG, "GPS for " + client.getName() + ": " + client.getAddressGPS().toString());
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        ClientsSpiceRequest request = new ClientsSpiceRequest(getToken(),getUserId());
        String clientJson = new Gson().toJson(client);
        handleActionAdd("Client",msgr,request,data,clientJson);
    }

    private void handleActionAddProduct(Intent paramIntent) {
        Message msg = new Message();
        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_PRODUCT);
        data.putBoolean(MSG_ADDED, false);
        Product product = paramIntent.getParcelableExtra(EXTRA_PARAM_PRODUCT);
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        ProductsSpiceRequest request = new ProductsSpiceRequest(getToken(),getUserId());
        String productJson = new Gson().toJson(product);
        handleActionAdd("Product",msgr,request,data,productJson);
    }

    private void handleActionUpdateClient(Intent paramIntent) {

        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_CLIENT);
        data.putBoolean(MSG_ADDED, false);
        Client client = paramIntent.getParcelableExtra(EXTRA_PARAM_CLIENT);
        String clientid = client.getId();
        client.setAddressGPS(new GoogleGeocodingSpiceRequest(client.getAddress()).loadLocationFromNetwork());
        Log.v(LOG_TAG, "GPS for " + client.getName() + ": " + client.getAddressGPS().toString());
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        ClientsSpiceRequest request = new ClientsSpiceRequest(getUserId(),getToken());
        client.setId(null);
        String clientJson = new Gson().toJson(client);
        handleActionUpdate("Client", clientid, msgr, request, data, clientJson);
    }

    private void handleActionUpdateProduct(Intent paramIntent) {
        Bundle data = new Bundle();
        data.putString(MSG_PARAM_TYPE, MSG_TYPE_PRODUCT);
        data.putBoolean(MSG_ADDED, false);
        Product product = paramIntent.getParcelableExtra(EXTRA_PARAM_PRODUCT);
        String productId = product.getId();
        Messenger msgr = (Messenger) paramIntent.getExtras().get(EXTRA_PARAM_MESSENGER);
        ProductsSpiceRequest request = new ProductsSpiceRequest(getToken(),getUserId());
        product.setId(null);
        String clientJson = new Gson().toJson(product);
        handleActionUpdate("Product",productId,msgr,request,data,clientJson);
    }

    private void handleActionDoPayment(Intent paramIntent) {
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
        PaymentsSpiceRequest request = new PaymentsSpiceRequest(getToken(),getUserId());
        String payment = new Gson().toJson(pay);
        handleActionAdd("Payment",msgr,request,data,payment);
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

    /**
     * Gets user id stored on the Application Preferences.
     *
     * @return the user id
     */
    protected String getUserId() {
        return this.sharedPreferences.getString(Constants.Preferences.USER_ID, getString(R.string.static_user_id));
    }

    /**
     * Gets token stored on the Application Preferences.
     *
     * @return the token
     */
    protected String getToken() {
        return this.sharedPreferences.getString(Constants.Preferences.TOKEN, getString(R.string.static_token));
    }

    private class Updated {
        private boolean updated;
        private Date when;

        /**
         * Instantiates a new Updated Object.
         * This object represents whether a content has been updated and when it was updated.
         *
         * @param paramDate the param date
         * @param arg3      the arg 3
         */
        public Updated(boolean paramDate, Date arg3) {
            this.updated = paramDate;
            this.when = arg3;
        }

        /**
         * Instantiates a new Updated.
         *
         * @param param the param
         */
        public Updated(boolean param) {
            this.updated = param;

            this.when = null;
        }

        /**
         * Gets when.
         *
         * @return the when
         */
        public Date getWhen() {
            if (!this.updated)
                return null;
            return this.when;
        }

        /**
         * Returns if the content is updated.
         *
         * @return the boolean
         */
        public boolean isUpdated() {
            return this.updated;
        }
    }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasacasa.service.SyncDataService
 * JD-Core Version:    0.6.0
 */
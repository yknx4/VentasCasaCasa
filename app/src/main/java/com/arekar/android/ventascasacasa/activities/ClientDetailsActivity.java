package com.arekar.android.ventascasacasa.activities;

import android.app.SharedElementCallback;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.adapters.SalesRvAdapter;
import com.arekar.android.ventascasacasa.helpers.ClientJsonHandler;
import com.arekar.android.ventascasacasa.helpers.Methods;
import com.arekar.android.ventascasacasa.helpers.PaymentsJsonHandler;
import com.arekar.android.ventascasacasa.helpers.SalesJsonHandler;
import com.arekar.android.ventascasacasa.model.Client;
import com.arekar.android.ventascasacasa.model.Payment;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonColumns;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonCursor;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonSelection;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import roboguice.util.temp.Ln;

public class ClientDetailsActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = "ClientDetailsActivity";
    private static final int LOADER_CLIENT = 827;
    private static final int LOADER_SALES = 995;
    private static final int LOADER_PAYMENTS = 123;
    private static final String TAG = "ClientDetailsActivity";
    public static final String CLIENT_ID = "CLIENT_ID";
    private String client_id;
    private String name;
    private String address;
    private String imageUrl;
    private ClientJsonHandler clientHandler;
    private RecyclerView rv;
    private Handler paymentServiceHandler = new IncomingHandler(this);
    private CoordinatorLayout coordinatorLayout;
    private SalesJsonHandler salesJsonHandler;
    private SalesRvAdapter salesRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JodaTimeAndroid.init(this);
        setContentView(R.layout.activity_client_details);

        setEnterAnimation();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordination_layout);


        final Context context = this;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent open = new Intent(context, AddSaleActivity.class);
                open.putExtra(CLIENT_ID,current.getId());
                startActivity(open);
            }
        });

        name = getIntent().getStringExtra("name");
        address = getIntent().getStringExtra("address");
        imageUrl = getIntent().getStringExtra("image");
        client_id = getIntent().getStringExtra("id");
        TextView title = (TextView) findViewById(R.id.title);
        TextView subtitle = (TextView) findViewById(R.id.subtitle);
        CircleImageView image = (CircleImageView) findViewById(R.id.image);
        ImageView bgimage = (ImageView) findViewById(R.id.bgimage);
        //ImageView rib = (ImageView) findViewById(R.id.bgheader);
//        Glide.with(this).load("http://lorempixel.com/256/256/people/").crossFade().into(rib);
        //Glide.with(this).fromResource().load(R.drawable.shica).into(image);
        Glide.with(this).load(imageUrl).into(image);
        Glide.with(this).load(imageUrl).into(bgimage);
        setTitle(name);
        toolbar.setTitle(name);
        Log.d(LOG_TAG, "Address: " + address);
        toolbar.setSubtitle(address);
        title.setText(name);
        subtitle.setText(address);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rv = (RecyclerView) findViewById(R.id.rv);
        setRecyclerViewLayoutManager(rv);
        rv.setVisibility(View.VISIBLE);

        getSupportLoaderManager().initLoader(LOADER_CLIENT, null, this);
        getSupportLoaderManager().initLoader(LOADER_SALES, null, this);
        getSupportLoaderManager().initLoader(LOADER_PAYMENTS, null, this);
        SyncDataService.startActionFetchPayments(this);

    }

    @Override
    public Messenger getMessenger() {
        return new Messenger(paymentServiceHandler);
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg == null) return;
        Bundle data = msg.getData();
        String type = data.getString(SyncDataService.MSG_PARAM_TYPE);
        if (type == null) return;

        switch (type) {
            case SyncDataService.MSG_TYPE_PAYMENT:
                Boolean paid = data.getBoolean(SyncDataService.MSG_ADDED, false);
                String msgtxt = data.getString("message");
                String snackText = msgtxt;
                if (paid) {
                    Payment nPay = new Gson().fromJson(data.getString("json"),Payment.class);
                    //Double amount = data.getDouble("amount");
                    Double amount = nPay.getPrice();
                    snackText = current.getName() + " paid " + Methods.getMoneyString(amount) + "";
                    SyncDataService.startActionFetchPayments(this);
                }
                Snackbar snack = Snackbar.make(coordinatorLayout, snackText, Snackbar.LENGTH_SHORT);
//                View view = snack.getView();
//                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
//                tv.setTextColor(Color.WHITE);
                snack.show();
                Log.v(TAG, msgtxt);
                break;
        }


    }

    public void setRecyclerViewLayoutManager(RecyclerView paramRecyclerView) {
        int i = 0;
        if (paramRecyclerView.getLayoutManager() != null)
            i = ((LinearLayoutManager) paramRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        paramRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paramRecyclerView.scrollToPosition(i);
    }

    @Override
    public void finishAfterTransition() {
        super.finishAfterTransition();
        findViewById(R.id.bgimage).setVisibility(View.VISIBLE);
    }


    private void setEnterAnimation() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.image).setVisibility(View.GONE);
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setEnterSharedElementCallback(new SharedElementCallback() {
                View mSnapshot;

                @Override
                public void onSharedElementStart(List<String> sharedElementNames,
                                                 List<View> sharedElements,
                                                 List<View> sharedElementSnapshots) {
                    addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots, false);
                    if (mSnapshot != null) {
                        mSnapshot.setVisibility(View.VISIBLE);
                    }
                    findViewById(R.id.bgimage).setVisibility(View.INVISIBLE);
                }

                @Override
                public void onSharedElementEnd(List<String> sharedElementNames,
                                               List<View> sharedElements,
                                               List<View> sharedElementSnapshots) {
                    addSnapshot(sharedElementNames, sharedElements, sharedElementSnapshots,
                            true);
                    if (mSnapshot != null) {
                        mSnapshot.setVisibility(View.INVISIBLE);
                    }
                    findViewById(R.id.bgimage).setVisibility(View.VISIBLE);
                }

                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    findViewById(R.id.bgimage).setVisibility(View.INVISIBLE);
                }

                private void addSnapshot(List<String> sharedElementNames,
                                         List<View> sharedElements,
                                         List<View> sharedElementSnapshots,
                                         boolean relayoutContainer) {
                    if (mSnapshot == null) {
                        for (int i = 0; i < sharedElementNames.size(); i++) {
                            if ("hello".equals(sharedElementNames.get(i))) {
                                FrameLayout element = (FrameLayout) sharedElements.get(i);
                                mSnapshot = sharedElementSnapshots.get(i);
                                int width = mSnapshot.getWidth();
                                int height = mSnapshot.getHeight();
                                FrameLayout.LayoutParams layoutParams =
                                        new FrameLayout.LayoutParams(width, height);
                                layoutParams.gravity = Gravity.CENTER;
                                int widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
                                int heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
                                mSnapshot.measure(widthSpec, heightSpec);
                                mSnapshot.layout(0, 0, width, height);
                                mSnapshot.setTransitionName("snapshot");
                                if (relayoutContainer) {
                                    ViewGroup container = (ViewGroup) findViewById(R.id.container);
                                    int left = (container.getWidth() - width) / 2;
                                    int top = (container.getHeight() - height) / 2;
                                    element.measure(widthSpec, heightSpec);
                                    element.layout(left, top, left + width, top + height);
                                }
                                element.addView(mSnapshot, layoutParams);
                                break;
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int sid, Bundle args) {

        long id = 0;
        switch (sid) {
            case LOADER_CLIENT:
                id = JsonColumns.ROW_CLIENTS_ID;
                break;
            case LOADER_PAYMENTS:
                id = JsonColumns.ROW_PAYMENTS_ID;
                break;
            case LOADER_SALES:
                id = JsonColumns.ROW_SALES_ID;
                break;
            default:
                return null;
        }
        JsonSelection selection = new JsonSelection();
        selection.id(id);
        //uri(), projection, sel(), args(), order()
        return new CursorLoader(this, selection.uri(), null, selection.sel(), selection.args(), selection.order());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch (loader.getId()) {
            case LOADER_CLIENT:
                onClientsLoadFinished(data);
                return;
            case LOADER_SALES:
                onSalesLoadFinished(data);
                return;
            case LOADER_PAYMENTS:
                onPaymentsLoadFinished(data);
                return;
        }

    }


    PaymentsJsonHandler paymentsJsonHandler;
    private void onPaymentsLoadFinished(Cursor data) {
        if (data.moveToFirst()) {
            JsonCursor c = new JsonCursor(data);
            paymentsJsonHandler = new PaymentsJsonHandler(new Gson().fromJson(c.getData(), JsonArray.class));
            if(salesRvAdapter!=null) {
                salesRvAdapter.setPaymentsJsonHandler(paymentsJsonHandler);
                salesRvAdapter.notifyDataSetChanged();}
            return;
        }

        Log.d(LOG_TAG, "No data received");
        SyncDataService.startActionFetchPayments(this);

    }

    private SalesJsonHandler salesHandler;

    private void onSalesLoadFinished(Cursor data) {
        if (data.moveToFirst()) {
            JsonCursor c = new JsonCursor(data);
            salesHandler = new SalesJsonHandler(new Gson().fromJson(c.getData(), JsonArray.class));
            loadSales(c);
            return;
        }
        Log.d(LOG_TAG, "No data received");
        SyncDataService.startActionFetchClients(this);
    }

    private void loadSales(JsonCursor c) {

        if (c.moveToFirst()) {
            salesJsonHandler = new SalesJsonHandler(new Gson().fromJson(c.getData(), JsonArray.class));
            salesRvAdapter = new SalesRvAdapter(salesJsonHandler.getSalesByClientId(client_id), this);
            if(paymentsJsonHandler!=null) salesRvAdapter.setPaymentsJsonHandler(paymentsJsonHandler);
            rv.setAdapter(salesRvAdapter);
            Log.d(TAG, "disqueSiCargo");
        } else {
            Ln.d("Didn't get data, syncing.");
            SyncDataService.startActionFetchSales(this);
        }

    }

    private void onClientsLoadFinished(Cursor data) {
//        data.moveToFirst();
//        int pos = (int) (JsonColumns.ROW_CLIENTS_ID - 1);
        if (data.moveToFirst()) {
            JsonCursor c = new JsonCursor(data);
            clientHandler = new ClientJsonHandler(new Gson().fromJson(c.getData(), JsonArray.class));
            loadClient();
        } else {
            Log.d(LOG_TAG, "No data received");
            SyncDataService.startActionFetchClients(this);
        }
    }

    private Client current;

    private void loadClient() {
        current = clientHandler.getById(client_id);
        if (current != null) {
            //TODO: USE THE CLIENT DATA
            Log.d(TAG, "Do something with the client data");
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            case LOADER_SALES:
                if (rv != null) rv.swapAdapter(null, true);

        }
    }
}

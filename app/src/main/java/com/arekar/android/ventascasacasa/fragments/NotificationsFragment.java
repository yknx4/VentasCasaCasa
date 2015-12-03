package com.arekar.android.ventascasacasa.fragments;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.activities.BaseActivity;
import com.arekar.android.ventascasacasa.adapters.NotificationsRvAdapter;
import com.arekar.android.ventascasacasa.helpers.ClientJsonHandler;
import com.arekar.android.ventascasacasa.helpers.PaymentsJsonHandler;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonColumns;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonCursor;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonSelection;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

/**
 * Created by yknx4 on 2/12/15.
 */
public class NotificationsFragment extends BaseFragment
        implements LoaderManager.LoaderCallbacks<Cursor>
{
    private static final int SALE_LOADER = 2;
    private static final String LOG_TAG = ClientsFragment.class.getSimpleName();
    private static final int PAYMENTS_LOADER = 4;
    NotificationsRvAdapter  adapter = null;
    private FloatingActionButton fabProducts;
    //  private VerticalRecyclerViewFastScroller fastScroller;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private Parcelable savedRecyclerLayoutState;

    private String getUserId()
    {
        return this.sharedPreferences.getString(Constants.Preferences.USER_ID, getString(R.string.static_user_id));
    }

    public Loader onCreateLoader(int paramInt, Bundle paramBundle)
    {
        long row = 0;
        switch (paramInt){
            case SALE_LOADER:
                row = JsonColumns.ROW_SALES_ID;
                break;
            case PAYMENTS_LOADER:
                row = JsonColumns.ROW_PAYMENTS_ID;
                break;
        }
        JsonSelection sel = new JsonSelection();
        sel = sel.id(row);
        return new CursorLoader(getContext(), sel.uri(), null, sel.sel(), sel.args(), sel.order());
    }

    private void restoreLayoutManagerPosition() {
        if (savedRecyclerLayoutState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }
    private void saveLayoutManagerPosition(){
        savedRecyclerLayoutState = recyclerView.getLayoutManager().onSaveInstanceState();
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
        this.sharedPreferences = getContext().getSharedPreferences("APP_PREFERENCES", 0);
        return paramLayoutInflater.inflate(R.layout.notifications_fragment, null);
    }

    public void onDestroyView()
    {
        super.onDestroyView();
//    this.recyclerView.removeOnScrollListener(this.fastScroller.getOnScrollListener());
    }




    private final Gson gson = new Gson();

    public void onLoadFinished(Loader paramLoader, Cursor paramCursor)
    {
        switch (paramLoader.getId()){
            case SALE_LOADER:
                if (paramCursor.moveToFirst())
                {
                    JsonSelection selection = new JsonSelection();
                    selection.id(JsonColumns.ROW_CLIENTS_ID);
                    Cursor rawClients = selection.query(getContext().getContentResolver());
                    JsonCursor clientes = new JsonCursor(rawClients);
                    selection = new JsonSelection();
                    selection.id(JsonColumns.ROW_PAYMENTS_ID);
                    Cursor rawPayments = selection.query(getContext().getContentResolver());
                    JsonCursor payments = new JsonCursor(rawPayments);
                    if(!clientes.moveToFirst()) return;
                    if(!payments.moveToFirst()) return;
                    saveLayoutManagerPosition();
                    JsonCursor c  = new JsonCursor(paramCursor);
                    this.adapter = new NotificationsRvAdapter(new Gson().fromJson(c.getData(), JsonArray.class), (BaseActivity) getActivity());
                    ClientJsonHandler jsonHandler = new ClientJsonHandler(gson.fromJson(clientes.getData(),JsonArray.class));
                    this.adapter.setClientJsonHandler(jsonHandler);
                    PaymentsJsonHandler paymentsJsonHandler = new PaymentsJsonHandler(gson.fromJson(payments.getData(),JsonArray.class));
                    this.adapter.setPaymentsJsonHandler(paymentsJsonHandler);
                    this.adapter.sortByNextPayment();

                    this.recyclerView.setAdapter(this.adapter);
                    restoreLayoutManagerPosition();
                    return;
                }
                break;
            case PAYMENTS_LOADER:
                JsonCursor payments = new JsonCursor(paramCursor);
                if(!payments.moveToFirst()) break;
                PaymentsJsonHandler paymentsJsonHandler = new PaymentsJsonHandler(gson.fromJson(payments.getData(),JsonArray.class));
                if(adapter==null) break;
                this.adapter.setPaymentsJsonHandler(paymentsJsonHandler);
                this.adapter.sortByNextPayment();
                if(recyclerView.getAdapter()==null) recyclerView.setAdapter(adapter);
                recyclerView.invalidate();
                break;
        }

        Log.d(LOG_TAG, "No data received");
    }

    public void onLoaderReset(Loader paramLoader)
    {
        saveLayoutManagerPosition();
        this.recyclerView.swapAdapter(null, true);
    }

    public void onViewCreated(View paramView, Bundle paramBundle)
    {
        super.onViewCreated(paramView, paramBundle);
        this.recyclerView = ((RecyclerView)paramView.findViewById(R.id.notif_rv));
        setRecyclerViewLayoutManager(this.recyclerView);
        getLoaderManager().initLoader(SALE_LOADER, null, this);
        getLoaderManager().initLoader(PAYMENTS_LOADER, null, this);
    }

    public void setRecyclerViewLayoutManager(RecyclerView paramRecyclerView)
    {

        paramRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";

    int restoredPos= -1;
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
    }




}


/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.fragments.ClientsFragment
 * JD-Core Version:    0.6.0
 */
package com.arekar.android.ventascasacasa.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
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
import com.arekar.android.ventascasacasa.adapters.ProductRvAdapter;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonColumns;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonContentValues;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonCursor;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class ProductsFragment extends Fragment
  implements LoaderCallbacks<Cursor>
{
  private static final int PRODUCTS_LOADER = 1;
  private final String LOG_TAG = getClass().getSimpleName();
  ProductRvAdapter adapter = null;
  private FloatingActionButton fabProducts;
  private RecyclerView prodRv;
  private SharedPreferences sharedPreferences;

  private String getUserId()
  {
    return this.sharedPreferences.getString(Constants.Preferences.USER_ID, getString(R.string.static_user_id));
  }

  public Loader onCreateLoader(int paramInt, Bundle paramBundle)
  {
    JsonContentValues c = new JsonContentValues();
    return new CursorLoader(getContext(),c.uri(), null, null, null, null);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View v = paramLayoutInflater.inflate(R.layout.products_fragment, paramViewGroup, false);
    this.sharedPreferences = getContext().getSharedPreferences(Constants.APP_PREFERENCES, 0);
    return v;
  }

  public void onLoadFinished(Loader paramLoader, Cursor paramCursor)
  {
    if (paramCursor.move((int) JsonColumns.ROW_PRODUCTS_ID))
    {
      JsonCursor c = new JsonCursor(paramCursor);
      this.adapter = new ProductRvAdapter((JsonArray)new Gson().fromJson(c.getData(), JsonArray.class));
      this.prodRv.setAdapter(this.adapter);
      return;
    }
    Log.d(this.LOG_TAG, "No data received");
    SyncDataService.startActionFetchProducts(getContext(), getUserId());
  }

  public void onLoaderReset(Loader paramLoader)
  {
    this.prodRv.swapAdapter(null, true);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.fabProducts = ((FloatingActionButton)paramView.findViewById(R.id.fab));
    this.prodRv = ((RecyclerView)paramView.findViewById(R.id.prod_rv));

      final LinearLayoutManager llm = new LinearLayoutManager(getContext());
      this.prodRv.setLayoutManager(llm);
      getLoaderManager().initLoader(1, null, this);


  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.fragments.ProductsFragment
 * JD-Core Version:    0.6.0
 */
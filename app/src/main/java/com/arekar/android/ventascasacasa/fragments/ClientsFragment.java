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

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.adapters.ClientRvAdapter;
import com.arekar.android.ventascasacasa.jsonprovider.JsonProvider;
import com.arekar.android.ventascasacasa.jsonprovider.json.JsonContentValues;
import com.arekar.android.ventascasacasa.jsonprovider.json.JsonCursor;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


public class ClientsFragment extends Fragment
  implements LoaderCallbacks<Cursor>
{
  private static final int CLIENTS_LOADER = 2;
  private static final String LOG_TAG = ClientsFragment.class.getSimpleName();
  ClientRvAdapter adapter = null;
  private FloatingActionButton fabProducts;
//  private VerticalRecyclerViewFastScroller fastScroller;
  private RecyclerView recyclerView;
  private SharedPreferences sharedPreferences;

  private String getUserId()
  {
    return this.sharedPreferences.getString("CURRENT_USER_ID", "");
  }

  public Loader onCreateLoader(int paramInt, Bundle paramBundle)
  {
    JsonContentValues jcv = new JsonContentValues();
    jcv.putID(JsonProvider.CLIENTS_RECORD_ID);
    return new CursorLoader(getContext(), jcv.uri(), null, null, null, null);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.sharedPreferences = getContext().getSharedPreferences("APP_PREFERENCES", 0);
    return paramLayoutInflater.inflate(R.layout.clients_fragment, null);
  }

  public void onDestroyView()
  {
    super.onDestroyView();
//    this.recyclerView.removeOnScrollListener(this.fastScroller.getOnScrollListener());
  }

  public void onLoadFinished(Loader paramLoader, Cursor paramCursor)
  {
    if ((paramCursor.moveToFirst()) && (paramCursor.move(1)))
    {
      JsonCursor c  = new JsonCursor(paramCursor);
      this.adapter = new ClientRvAdapter((JsonArray)new Gson().fromJson(c.getJson(), JsonArray.class), getActivity());
      this.recyclerView.setAdapter(this.adapter);
      return;
    }
    Log.d(LOG_TAG, "No data received");
    SyncDataService.startActionFetchClients(getContext(), getUserId());
  }

  public void onLoaderReset(Loader paramLoader)
  {
    this.recyclerView.swapAdapter(null, true);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.recyclerView = ((RecyclerView)paramView.findViewById(R.id.client_rv));
//    this.fastScroller = ((VerticalRecyclerViewFastScroller)paramView.findViewById(2131492990));
    this.fabProducts = ((FloatingActionButton)paramView.findViewById(R.id.fab));
//    paramView = (VerticalRecyclerViewFastScroller)paramView.findViewById(2131492990);
//    paramView.setRecyclerView(this.recyclerView);
//    this.recyclerView.addOnScrollListener(paramView.getOnScrollListener());
    setRecyclerViewLayoutManager(this.recyclerView);
    getLoaderManager().initLoader(2, null, this);
  }

  public void setRecyclerViewLayoutManager(RecyclerView paramRecyclerView)
  {
    int i = 0;
    if (paramRecyclerView.getLayoutManager() != null)
      i = ((LinearLayoutManager)paramRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    paramRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    paramRecyclerView.scrollToPosition(i);
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.fragments.ClientsFragment
 * JD-Core Version:    0.6.0
 */
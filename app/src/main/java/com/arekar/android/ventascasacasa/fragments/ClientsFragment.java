package com.arekar.android.ventascasacasa.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.activities.AddClientActivity;
import com.arekar.android.ventascasacasa.adapters.ClientRvAdapter;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonColumns;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonCursor;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonSelection;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;


public class ClientsFragment extends BaseFragment
  implements LoaderCallbacks<Cursor>
{
  private static final int CLIENTS_LOADER = 2;
  private static final String LOG_TAG = ClientsFragment.class.getSimpleName();
  ClientRvAdapter adapter = null;
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
    JsonSelection sel = new JsonSelection();
    sel = sel.id(JsonColumns.ROW_CLIENTS_ID);
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
    return paramLayoutInflater.inflate(R.layout.clients_fragment, null);
  }

  public void onDestroyView()
  {
    super.onDestroyView();
//    this.recyclerView.removeOnScrollListener(this.fastScroller.getOnScrollListener());
  }

  @Override
  public boolean onContextItemSelected(MenuItem item) {
    Log.d(LOG_TAG, "Clicked: " + item.getTitle().toString() + " in " + adapter.getPosition());
//    Toast.makeText(getContext(),"Clicked "+ item.getTitle().toString(),Toast.LENGTH_SHORT).show();
    switch (item.getTitle().toString()){
      case "Delete":
        SyncDataService.startActionDeleteClient(getContext(),adapter.getPosition(),getToken(),getUserId());
        break;
      case "Edit":
        Intent intent = new Intent(getContext(), AddClientActivity.class);
        intent.putExtra(AddClientActivity.BUNDLE_CLIENT,adapter.getClientById(adapter.getPosition()));
        startActivity(intent);
        break;
    }

    return super.onContextItemSelected(item);
  }

  public void onLoadFinished(Loader paramLoader, Cursor paramCursor)
  {
    if (paramCursor.moveToFirst())
    {
      JsonCursor c  = new JsonCursor(paramCursor);
      this.adapter = new ClientRvAdapter((JsonArray)new Gson().fromJson(c.getData(), JsonArray.class), getActivity());
      this.recyclerView.setAdapter(this.adapter);
      restoreLayoutManagerPosition();
      return;
    }
    Log.d(LOG_TAG, "No data received");
    SyncDataService.startActionFetchClients(getContext(), getUserId());
  }

  public void onLoaderReset(Loader paramLoader)
  {
    saveLayoutManagerPosition();
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
    registerForContextMenu(recyclerView);
  }

  public void setRecyclerViewLayoutManager(RecyclerView paramRecyclerView)
  {

    if (paramRecyclerView.getLayoutManager() != null ){
      if(restoredPos<0)
      restoredPos = ((LinearLayoutManager)paramRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }
    paramRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    paramRecyclerView.scrollToPosition(restoredPos);
  }

  private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";

  /**
   * This is a method for Fragment.
   * You can do the same in onCreate or onRestoreInstanceState
   */

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
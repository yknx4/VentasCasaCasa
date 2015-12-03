package com.arekar.android.ventascasacasa.adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.activities.ClientDetailsActivity;
import com.arekar.android.ventascasacasa.helpers.Methods;
import com.arekar.android.ventascasacasa.model.AddressGPS;
import com.arekar.android.ventascasacasa.model.Client;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;

public class ClientRvAdapter extends AbstractAdapter<ClientRvAdapter.ClientViewHolder>
{
    WeakReference<AppCompatActivity> act;
    //AppCompatActivity act;
    List<Client> clients;
    private static final String TAG = "ClientRvAdapter";
    public ClientRvAdapter(JsonArray paramJsonArray, Activity paramActivity)
    {
        act = new WeakReference<>((AppCompatActivity) paramActivity);
        Type list = new TypeToken<List<Client>>(){}.getType();
        Log.d(TAG,"JSON: "+paramJsonArray.toString());
        this.clients = ((List)new Gson().fromJson(paramJsonArray, list));
    }

    public Client getClientById(String id){
        if(clients==null || clients.size()==0) return null;
        for(Client client: clients){
            if(client.getId().equals(id)) return client;
        }
        return null;
    }
    public int getItemCount()
    {
        return this.clients.size();
    }

    public void onBindViewHolder(final ClientViewHolder paramClientViewHolder, int paramInt)
    {
        final Client localClient = (Client)this.clients.get(paramInt);

        paramClientViewHolder.cardViewClient.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(localClient.getId());
                return false;
            }
        });
        paramClientViewHolder.clientName.setText(localClient.getName());
        paramClientViewHolder.clientAddress.setText(localClient.getAddress());
        if(localClient.getAddressGPS().getLatitude()==0.0&&localClient.getAddressGPS().getLongitude()==0.0) paramClientViewHolder.locationButton.setVisibility(View.GONE);
        paramClientViewHolder.locationButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                AddressGPS localAddressGPS = localClient.getAddressGPS();
                Methods.launchGoogleMaps(paramView.getContext(), localAddressGPS.getLatitude().doubleValue(), localAddressGPS.getLongitude().doubleValue(), localClient.getName());
            }
        });
        paramClientViewHolder.callButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Methods.makeCall(paramView.getContext(), localClient.getPhone());
            }
        });
        paramClientViewHolder.emailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Methods.sendMail(paramView.getContext(), localClient.getEmail(), localClient.getId());
            }
        });
        new AlphaAnimation(0.0F, 1.0F).setDuration(1000L);
        //Glide.with(paramClientViewHolder.clientImage.getContext()).load("http://lorempixel.com/256/256/people/").crossFade().into(paramClientViewHolder.clientImage);
//        Glide.with(paramClientViewHolder.clientImage.getContext()).fromResource().load(R.drawable.shica).crossFade().into(paramClientViewHolder.clientImage);
        Glide.with(paramClientViewHolder.clientImage.getContext()).load(localClient.getImage()).crossFade().into(paramClientViewHolder.clientImage);
        paramClientViewHolder.cardViewClient.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Intent localIntent = new Intent(paramView.getContext(), ClientDetailsActivity.class);
                localIntent.putExtra("address", localClient.getAddress());
                localIntent.putExtra("name", localClient.getName());
                localIntent.putExtra("image",localClient.getImage());
                localIntent.putExtra("id",localClient.getId());
                Object localObject2 = paramView.getContext().getResources();
//                Pair localPair = Pair.create(paramClientViewHolder.clientName, ((Resources) localObject2).getString(R.string.transition_name));
//                Pair localPair1 = Pair.create(paramClientViewHolder.clientAddress, ((Resources)localObject2).getString(R.string.transition_address));
//                Pair localPair2 = Pair.create(paramClientViewHolder.callButton, ((Resources)localObject2).getString(R.string.transition_button_call));
//                Pair localPair3 = Pair.create(paramClientViewHolder.emailButton, ((Resources)localObject2).getString(R.string.transition_button_email));
//                Pair localPair4 = Pair.create(paramClientViewHolder.locationButton, ((Resources)localObject2).getString(R.string.transition_button_location));
                Pair localPair5 = Pair.create(paramClientViewHolder.clientImage, ((Resources)localObject2).getString(R.string.transition_profile));
                //localObject2 = Pair.create(ClientRvAdapter.this.act.findViewById(), ((Resources)localObject2).getString(2131099688));
//                ActivityOptionsCompat tran = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) paramView.getContext(), new Pair[]{localPair, localPair1, localPair2, localPair3, localPair4, localPair5});

                ActivityOptionsCompat tran = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) paramView.getContext(), new Pair[]{localPair5});
                if (Build.VERSION.SDK_INT >= 16)
                {
                    paramView.getContext().startActivity(localIntent, tran.toBundle());
                    return;
                }
                ((Activity)paramView.getContext()).startActivity(localIntent, tran.toBundle());
            }
        });
    }

    public ClientViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int paramInt)
    {
        return new ClientViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.clients_item, paramViewGroup, false));
    }

    public void onViewRecycled(ClientViewHolder paramClientViewHolder)
    {
        super.onViewRecycled(paramClientViewHolder);
    }

    public static class ClientViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private LinearLayout buttonLayout;
        private ImageButton callButton;
        private CardView cardViewClient;
        private TextView clientAddress;
        private ImageView clientImage;
        private TextView clientName;
        private ImageButton emailButton;
        private ImageButton locationButton;

        ClientViewHolder(View paramView)
        {
            super(paramView);
            this.cardViewClient = ((CardView)paramView.findViewById(R.id.card_view_client));
            this.clientImage = ((ImageView)paramView.findViewById(R.id.client_image));
            this.clientName = ((TextView)paramView.findViewById(R.id.client_name));
            this.clientAddress = ((TextView)paramView.findViewById(R.id.next_payment));
            this.buttonLayout = ((LinearLayout)paramView.findViewById(R.id.button_layout));
            this.locationButton = ((ImageButton)paramView.findViewById(R.id.location_button));
            this.emailButton = ((ImageButton)paramView.findViewById(R.id.pay_button));
            this.callButton = ((ImageButton)paramView.findViewById(R.id.call_button));
            cardViewClient.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(R.string.action_client);
            menu.add(0, v.getId(), 0, "Edit client");//groupId, itemId, order, title
            menu.add(0, v.getId(), 0, "Delete client");
        }
    }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.adapters.ClientRvAdapter
 * JD-Core Version:    0.6.0
 */
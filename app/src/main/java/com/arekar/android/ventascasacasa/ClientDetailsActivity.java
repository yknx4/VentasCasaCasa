package com.arekar.android.ventascasacasa;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClientDetailsActivity extends AppCompatActivity
  implements OnClickListener
{
  private CircleImageView circleView;
  private CoordinatorLayout coordinationLayout;
  private TextView email;
  private FloatingActionButton fab;
  private TextView name;
  private RecyclerView rv;
  private LinearLayout textLayout;
  private Toolbar toolbar;

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131492984:
    case 2131492985:
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.client_details_activity);
    coordinationLayout = ((CoordinatorLayout)findViewById(R.id.coordination_layout));
    toolbar = ((Toolbar)findViewById(R.id.toolbar));
    setSupportActionBar(this.toolbar);
//    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//    getSupportActionBar().setDisplayShowHomeEnabled(true);
    this.textLayout = ((LinearLayout)findViewById(R.id.textLayout));
    this.name = ((TextView)findViewById(R.id.name));
    this.email = ((TextView)findViewById(R.id.email));
    this.circleView = ((CircleImageView)findViewById(R.id.circleView));
    findViewById(R.id.call_button).setOnClickListener(this);
    findViewById(R.id.email_button).setOnClickListener(this);
    findViewById(R.id.location_button).setOnClickListener(this);
    this.fab = ((FloatingActionButton)findViewById(R.id.fab));
    this.rv = ((RecyclerView)findViewById(R.id.rv));
    this.name.setText(getIntent().getStringExtra("name"));
    this.email.setText(getIntent().getStringExtra("address"));
   // Glide.with(this).load("http://lorempixel.com/256/256/people/").crossFade().into(this.circleView);
    Glide.with(this).fromResource().load(R.drawable.shica).crossFade().into(this.circleView);
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.ClientDetailsActivity
 * JD-Core Version:    0.6.0
 */
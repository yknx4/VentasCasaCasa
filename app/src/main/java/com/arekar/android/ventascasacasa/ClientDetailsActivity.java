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
    setContentView(2130968603);
    this.coordinationLayout = ((CoordinatorLayout)findViewById(2131492975));
    this.toolbar = ((Toolbar)findViewById(2131492976));
    setSupportActionBar(this.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    this.textLayout = ((LinearLayout)findViewById(2131492980));
    this.name = ((TextView)findViewById(2131492982));
    this.email = ((TextView)findViewById(2131492971));
    this.circleView = ((CircleImageView)findViewById(2131492981));
    findViewById(2131492984).setOnClickListener(this);
    findViewById(2131492985).setOnClickListener(this);
    findViewById(2131492986).setOnClickListener(this);
    this.fab = ((FloatingActionButton)findViewById(2131492987));
    this.rv = ((RecyclerView)findViewById(2131492988));
    this.name.setText(getIntent().getStringExtra("name"));
    this.email.setText(getIntent().getStringExtra("address"));
    Glide.with(this).load("http://lorempixel.com/256/256/people/").crossFade().into(this.circleView);
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.ClientDetailsActivity
 * JD-Core Version:    0.6.0
 */
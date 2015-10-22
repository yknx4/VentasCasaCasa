package com.arekar.android.ventascasacasa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

public class ClientPHActivity extends AppCompatActivity
{
  private ImageView circleView;
  private TextView text;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968600);
    this.circleView = ((ImageView)findViewById(2131492981));
    this.text = ((TextView)findViewById(2131492971));
    Glide.with(this).load("http://lorempixel.com/256/256/people/").crossFade().into(this.circleView);
    if (getIntent() != null)
      this.text.setText(getIntent().getStringExtra("address"));
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.ClientPHActivity
 * JD-Core Version:    0.6.0
 */
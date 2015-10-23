package com.arekar.android.ventascasacasa.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.arekar.android.ventascasacasa.R;
import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientDetailsAct extends AppCompatActivity {
    private static final String LOG_TAG = "ClientDetailsAct";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String name = getIntent().getStringExtra("name");
        String address = getIntent().getStringExtra("address");
        TextView title = (TextView) findViewById(R.id.title);
        TextView subtitle = (TextView) findViewById(R.id.subtitle);
        CircleImageView image = (CircleImageView) findViewById(R.id.image);
        //ImageView rib = (ImageView) findViewById(R.id.bgheader);
//        Glide.with(this).load("http://lorempixel.com/256/256/people/").crossFade().into(rib);
        Glide.with(this).fromResource().load(R.drawable.shica).into(image);
        setTitle(name);
        toolbar.setTitle(name);
        Log.d(LOG_TAG, "Address: " + address);
        toolbar.setSubtitle(address);
        title.setText(name);
        subtitle.setText(address);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}

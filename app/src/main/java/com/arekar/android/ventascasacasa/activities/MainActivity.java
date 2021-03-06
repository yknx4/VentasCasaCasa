package com.arekar.android.ventascasacasa.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.adapters.ViewPagerAdapter;
import com.arekar.android.ventascasacasa.fragments.ClientsFragment;
import com.arekar.android.ventascasacasa.fragments.FragmentFourFragment;
import com.arekar.android.ventascasacasa.fragments.NotificationsFragment;
import com.arekar.android.ventascasacasa.fragments.ProductsFragment;
import com.arekar.android.ventascasacasa.helpers.Methods;
import com.arekar.android.ventascasacasa.model.Payment;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.google.gson.Gson;

public class MainActivity extends BaseActivity {

    private static int[] tabIcons = { R.drawable.bell46, R.drawable.worker37, R.drawable.full22 };
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;


    private void hideFAB()
    {
        fab.hide();
        this.fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        this.fab.setOnClickListener(null);
    }

    public static int REQUEST_CODE_GET_JSON = 1;
    private static final String TAG = "MainActivity";
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_GET_JSON && resultCode == Activity.RESULT_OK) {
            Log.d(TAG, data.getStringExtra("json"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    private void setFloatingActionButtonForClients()
    {
        fab.setOnClickListener(null);
        fab.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fab.setForegroundTintList(getResources().getColorStateList(R.color.primary_light));
        }
        this.fab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
        final Context ctx = this;
        this.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent intent = new Intent(ctx, AddClientActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setFloatingActionButtonForProducts()
    {
        fab.setOnClickListener(null);
        fab.show();
        this.fab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fab.setForegroundTintList(getResources().getColorStateList(R.color.primary_light));
        }
        final Context ctx = this;
        this.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent intent = new Intent(ctx, AddProductActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setupActivityState()
    {

        //TODO: Uncomment when recover Data Service
        SyncDataService.startActionFetchAll(getApplicationContext());

        hideFAB();
        this.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            public void onTabReselected(TabLayout.Tab paramTab) {
            }

            public void onTabSelected(TabLayout.Tab paramTab) {
                viewPager.setCurrentItem(paramTab.getPosition());
                switch (paramTab.getPosition()) {
                    default:
                        return;
                    case 0:
                        hideFAB();
                        return;
                    case 1:
                        setFloatingActionButtonForClients();
                        return;
                    case 2:
                        setFloatingActionButtonForProducts();
                        return;
                }

            }

            public void onTabUnselected(TabLayout.Tab paramTab) {
            }
        });
    }

    private void setupActivityState(Bundle paramBundle)
    {
    }

    private void setupTabIcon(int paramInt)
    {
        this.tabLayout.getTabAt(paramInt).setIcon(tabIcons[paramInt]);
    }

    private void setupTabIcons()
    {
        setupTabIcon(0);
        setupTabIcon(1);
        setupTabIcon(2);
    }

    private void setupViewPager(ViewPager paramViewPager)
    {

        ViewPagerAdapter localViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        localViewPagerAdapter.addFragment(new NotificationsFragment(), "TWO");
        localViewPagerAdapter.addFragment(new ClientsFragment(), "THREE");
        localViewPagerAdapter.addFragment(new ProductsFragment(), "FOUR");
        paramViewPager.setAdapter(localViewPagerAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       checkLogin();

        setContentView(R.layout.activity_main);
        setupToolbar();
        viewPager = ((ViewPager)findViewById(R.id.viewpager));
        setupViewPager(viewPager);
        tabLayout = ((TabLayout)findViewById(R.id.tabs));
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordination_layout);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        setupActivityState();
        if (savedInstanceState != null)
            setupActivityState(savedInstanceState);
    }

    IncomingHandler paymentServiceHandler = new IncomingHandler(this);

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
                    snackText = "Paid " + Methods.getMoneyString(amount) + "";
                    SyncDataService.startActionFetchPayments(this);
                }
                Snackbar snack = Snackbar.make(coordinatorLayout, snackText, Snackbar.LENGTH_SHORT);

                snack.show();
                Log.v(TAG, msgtxt);
                break;
        }


    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
//        final ActionBar ab = getSupportActionBar();
//        ab.setHomeAsUpIndicator(R.drawable.ic_add_black_24dp);
//
//        ab.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id== R.id.action_logout){
            setToken(INVALID_TOKEN);
            setUserId(INVALID_UID);
            checkLogin();
            //finish();
        }

        return super.onOptionsItemSelected(item);
    }
}

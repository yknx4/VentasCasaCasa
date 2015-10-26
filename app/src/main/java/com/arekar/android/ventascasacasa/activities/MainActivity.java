package com.arekar.android.ventascasacasa.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.arekar.android.ventascasacasa.Constants;
import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.adapters.ViewPagerAdapter;
import com.arekar.android.ventascasacasa.fragments.ClientsFragment;
import com.arekar.android.ventascasacasa.fragments.FragmentFourFragment;
import com.arekar.android.ventascasacasa.fragments.FragmentTwoFragment;
import com.arekar.android.ventascasacasa.fragments.ProductsFragment;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.vijay.jsonwizard.activities.JsonFormActivity;

public class MainActivity extends BaseActivity {

    private static int[] tabIcons = { R.drawable.bell46, R.drawable.worker37, R.drawable.full22 };
    private CoordinatorLayout coordinatorLayout;
    private FloatingActionButton fab;

    private TabLayout tabLayout;
    private Toolbar toolbar;
    private ViewPager viewPager;


    private void hideFAB()
    {
        this.fab.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        this.fab.setOnClickListener(null);
        fab.hide();
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
        fab.show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fab.setForegroundTintList(getResources().getColorStateList(R.color.primary_light));
        }
        this.fab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
        final Context ctx = this;
        this.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
//                Snackbar.make(coordinatorLayout, "Clients", Snackbar.LENGTH_SHORT).show();
                Intent intent = new Intent(ctx, AddClientActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setFloatingActionButtonForProducts()
    {
        fab.show();
        this.fab.setBackgroundTintList(getResources().getColorStateList(R.color.primary));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fab.setForegroundTintList(getResources().getColorStateList(R.color.primary_light));
        }
        this.fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Snackbar.make(coordinatorLayout, "Products", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
    private void setupActivityState()
    {

        //TODO: Uncomment when recover Data Service
        SyncDataService.startActionFetchAll(getApplicationContext(), getUserId());

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
        localViewPagerAdapter.addFragment(new FragmentFourFragment(), "TWO");
        localViewPagerAdapter.addFragment(new ClientsFragment(), "THREE");
        localViewPagerAdapter.addFragment(new ProductsFragment(), "FOUR");
        paramViewPager.setAdapter(localViewPagerAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





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

    @Override
    public Messenger getMessenger() {
        return null;
    }

    @Override
    public void handleMessage(Message msg) {

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

        return super.onOptionsItemSelected(item);
    }
}

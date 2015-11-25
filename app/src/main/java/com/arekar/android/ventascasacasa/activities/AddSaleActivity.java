package com.arekar.android.ventascasacasa.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.helpers.ProductsJsonHandler;
import com.arekar.android.ventascasacasa.model.Sale;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonColumns;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonCursor;
import com.arekar.android.ventascasacasa.provider.jsondataprovider.json.JsonSelection;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddSaleActivity extends BaseActivity implements View.OnClickListener {

    private CoordinatorLayout coordinationLayout;
    private Toolbar toolbar;
    private TextInputLayout inputLayoutProducts;
    private TextInputLayout inputLayoutPrice;
    private TextInputLayout inputLayoutDays;
    private TextInputLayout inputLayoutTime;
    private TextInputLayout inputLayoutWhen;
    private Button btn_add;
    private Button btn_days;
    private Button btn_time;
    private Button btn_signup;
    IncomingHandler handler = new IncomingHandler(this);
    ProductsJsonHandler productsJsonHandler;
    String clientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        coordinationLayout = (CoordinatorLayout) findViewById(R.id.coordination_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        inputLayoutProducts = (TextInputLayout) findViewById(R.id.input_layout_products);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        inputLayoutPrice = (TextInputLayout) findViewById(R.id.input_layout_price);
        inputLayoutDays = (TextInputLayout) findViewById(R.id.input_layout_days);
        inputLayoutTime = (TextInputLayout) findViewById(R.id.input_layout_time);
        inputLayoutWhen = (TextInputLayout) findViewById(R.id.input_layout_when);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_days = (Button) findViewById(R.id.btn_days);
        btn_days.setOnClickListener(this);
        btn_time = (Button) findViewById(R.id.btn_time);
        btn_time.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
        JsonSelection getProd = new JsonSelection();
        getProd.id(JsonColumns.ROW_PRODUCTS_ID);
        Cursor q = getProd.query(this);
        JsonCursor jc = new JsonCursor(q);
        if(jc.moveToFirst()){
         productsJsonHandler = new ProductsJsonHandler(new Gson().fromJson(jc.getData(), JsonArray.class));
        }
        clientId = getIntent().getStringExtra(ClientDetailsActivity.CLIENT_ID);
    }

    @Override
    public Messenger getMessenger() {
        return new Messenger(handler);
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg == null) return;
        Bundle data = msg.getData();
        String type = data.getString(SyncDataService.MSG_PARAM_TYPE);
        String msgtxt = data.getString("message");
        Log.v(TAG, "Response: " + msgtxt);
        if (type == null) return;

        switch (type) {
            case SyncDataService.MSG_TYPE_SALE:

                Boolean add = data.getBoolean(SyncDataService.MSG_ADDED, false);

                String snackText = msgtxt;
                if (add) {
                    snackText = "Sale done.";

//                    if(update) snackText = getInputName().getText().toString() + " updated.";
                    SyncDataService.startActionFetchSales(this);
                }
                Snackbar snack = Snackbar.make(coordinationLayout, snackText, Snackbar.LENGTH_LONG);
                snack.show();
                thread.start();
                Log.v(TAG, msgtxt);
                break;
        }
    }

    Thread thread = new Thread(){
        @Override
        public void run() {
            try {
                Thread.sleep(3500); // As I am using LENGTH_LONG in Toast
                AddSaleActivity.this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    private static final String TAG = "AddSaleActivity";

    Integer[] selectedProducts=null;
    private void productChooser(){
        new MaterialDialog.Builder(this)
                .title("Pick product")
                .items(productsJsonHandler.getProductsTitle())
                .itemsCallbackMultiChoice(selectedProducts, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected.
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
                        selectedProducts = which;
                        Snackbar.make(coordinationLayout,"Selected products",Snackbar.LENGTH_SHORT).show();
                        getInputProducts().setText(productsJsonHandler.getProductsStringFromPosition(which));
                        getInputPrice().setText(String.valueOf(productsJsonHandler.getProductsPriceFromPosition(which)));
                        return true;
                    }
                })
                .positiveText("ok")
                .show();
    }

    Integer[] selectedDays=null;

    private void daysChooser(){
        new MaterialDialog.Builder(this)
                .title("Pick days")
                .items(R.array.days)
                .itemsCallbackMultiChoice(selectedDays, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected.
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
                        selectedDays = which;
                        Snackbar.make(coordinationLayout, "Selected days", Snackbar.LENGTH_SHORT).show();
//                        getInputDays().setText(productsJsonHandler.getProductsStringFromPosition(which));
                        return true;
                    }
                })
                .positiveText("ok")
                .show();
    }

    Integer[] selectedTimes=null;

    private void timeChooser(){
        new MaterialDialog.Builder(this)
                .title("Pick hours")
                .items(R.array.days)
                .itemsCallbackMultiChoice(selectedTimes, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected.
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
                        selectedTimes = which;
                        Snackbar.make(coordinationLayout, "Selected hours", Snackbar.LENGTH_SHORT).show();
//                        getInputTime().setText(productsJsonHandler.getProductsStringFromPosition(which));
                        return true;
                    }
                })
                .positiveText("ok")
                .show();
    }

    private EditText getInputProducts(){
        return (EditText) findViewById(R.id.input_products);
    }

    private EditText getInputPrice(){
        return (EditText) findViewById(R.id.input_price);
    }

    private EditText getInputDays(){
        return (EditText) findViewById(R.id.input_days);
    }

    private EditText getInputTime(){
        return (EditText) findViewById(R.id.input_time);
    }

    private EditText getInputWhen(){
        return (EditText) findViewById(R.id.input_when);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                productChooser();
                break;
            case R.id.btn_signup:
                doSale();
                break;
            case R.id.btn_time:
                timeChooser();
                break;
            case R.id.btn_days:
                daysChooser();
                break;
        }
    }

    private List<Integer> getIntegerFromString(String input){
        String[] ints = input.split(",");
        List<Integer> result = new ArrayList<>();
        for(String inte:ints){
            result.add(Integer.parseInt(inte));
        }
        return result;
    }




    private void doSale() {
        Sale newSale = new Sale();
        newSale.setMultiplePayments(true);
        newSale.setDate(String.valueOf(new Date().getTime()));
        newSale.setUserId(getUserId());
        newSale.setPrice(Double.valueOf(getInputPrice().getText().toString()));
        newSale.setAvailableDays(Arrays.asList(selectedDays));
        newSale.setAvailableTime(Arrays.asList(selectedTimes));
        newSale.setProducts(productsJsonHandler.getProductsIdFromPosition(selectedProducts));
        newSale.setPaymentDue(Integer.valueOf(getInputWhen().getText().toString()));
        newSale.setClientId(clientId);
        newSale.setPaymentCost(newSale.getPrice()/8);
        SyncDataService.startActionAddSale(this, newSale, getMessenger());
    }
}

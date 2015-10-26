package com.arekar.android.ventascasacasa.activities;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.imgurmodel.ImageResponse;
import com.arekar.android.ventascasacasa.imgurmodel.Upload;
import com.arekar.android.ventascasacasa.model.Product;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.arekar.android.ventascasacasa.service.UploadService;
import com.bumptech.glide.Glide;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddProductActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 85;
    public static final String BUNDLE_PRODUCT = "BUNDLE_PRODUCT";
    private CoordinatorLayout coordinationLayout;
    private Toolbar toolbar;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutBrand;
    private TextInputLayout inputLayoutModel;
    private TextInputLayout inputLayoutPrice;
    private TextInputLayout inputLayoutCost;
    private TextInputLayout inputLayoutDescription;
    private Uri selectedImageUri;
    private ImageButton img_btn;
    private Button btn;
    @NotEmpty
    private EditText inputName;
    @NotEmpty
    private EditText inputBrand;
    @NotEmpty
    private EditText inputModel;
    @NotEmpty
    private EditText inputPrice;
    @NotEmpty
    private EditText inputCost;
    @NotEmpty
    private EditText inputDescription;
    private Validator validator;
    private boolean update;
    private Product currentProduct;
    private Spinner spn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_add_product);
        coordinationLayout = (CoordinatorLayout) findViewById(R.id.coordination_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        img_btn = (ImageButton) findViewById(R.id.image_button);
        img_btn.setOnClickListener(this);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputName = inputLayoutName.getEditText();
        inputLayoutBrand = (TextInputLayout) findViewById(R.id.input_layout_brand);
        inputBrand = inputLayoutBrand.getEditText();
        inputLayoutModel = (TextInputLayout) findViewById(R.id.input_layout_model);
        inputModel = inputLayoutModel.getEditText();
        inputLayoutPrice = (TextInputLayout) findViewById(R.id.input_layout_price);
        inputPrice = inputLayoutPrice.getEditText();
        inputLayoutCost = (TextInputLayout) findViewById(R.id.input_layout_cost);
        inputCost = inputLayoutCost.getEditText();
        inputLayoutDescription = (TextInputLayout) findViewById(R.id.input_layout_description);
        inputDescription = inputLayoutDescription.getEditText();
        btn = (Button) findViewById(R.id.btn_signup);
        spn = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_colors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);


        btn.setOnClickListener(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            if(extras!=null) {
                Product cl = extras.getParcelable(BUNDLE_PRODUCT);
                if (cl != null) {
                    update = true;
                    currentProduct = cl;
                    Glide.with(this).load(cl.getImage()).crossFade().into(img_btn);
                    loadProduct();
                }
            }
        }
    }

    private void loadProduct() {
        inputDescription.setText(currentProduct.getDescription());
        inputName.setText(currentProduct.getName());
        inputCost.setText(currentProduct.getCost().toString());
        inputBrand.setText(currentProduct.getBrand());
        inputPrice.setText(currentProduct.getPrice().toString());
        inputModel.setText(currentProduct.getModel());
        spn.setSelection(getColorPosition(currentProduct.getColor()));

    }

    private int getColorPosition(String color){
        String[] colors = getResources().getStringArray(R.array.product_colors);
        int pos = 0;
        for(String colorS:colors){
            if(colorS.equals(color)) return pos;
            pos++;
        }

        return  0;
    }




    @Override
    public Messenger getMessenger() {
        return new Messenger(paymentServiceHandler);
    }

    private Upload upload; // Upload object containging image and meta data
    private File chosenFile; //chosen file from intent

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_button:
                openImageIntent();
                break;
            case R.id.btn_signup:
                //TODO implement
                validator.validate();
                break;
        }
    }

    private EditText getInputName(){
        return (EditText) findViewById(R.id.input_name);
    }

    private EditText getInputBrand(){
        return (EditText) findViewById(R.id.input_brand);
    }

    private EditText getInputModel(){
        return (EditText) findViewById(R.id.input_model);
    }

    private EditText getInputPrice(){
        return (EditText) findViewById(R.id.input_price);
    }

    private EditText getInputCost(){
        return (EditText) findViewById(R.id.input_cost);
    }

    private EditText getInputDescription(){
        return (EditText) findViewById(R.id.input_description);
    }

    private Uri outputFileUri;

    private void openImageIntent() {

// Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = getInputName().toString()+new Date().getTime();
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, YOUR_SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == YOUR_SELECT_PICTURE_REQUEST_CODE) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }
                if (isCamera) {
                    selectedImageUri = outputFileUri;
                } else {
                    selectedImageUri = data == null ? null : data.getData();
                }



                Glide.with(this).load(selectedImageUri).crossFade().into(img_btn);
            }
        }
    }

    @Override
    public void onValidationSucceeded() {

        Snackbar.make(coordinationLayout, "Yay! Now creating!", Snackbar.LENGTH_SHORT).show();
        findViewById(R.id.btn_signup).setEnabled(false);
        if (selectedImageUri == null){
            addProduct();
            return;
        }
        upload = new Upload();
        try {
            upload.image = getContentResolver().openInputStream(selectedImageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        upload.title = getInputName().getText().toString()+ Math.round(Math.random() * 1000);
        upload.description = getInputName().getText().toString()+getInputDescription().getText().toString();
        new UploadService(this).Execute(upload, new UiCallback());

    }


    private String imgUrl="";

    private class UiCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {
            imgUrl = imageResponse.data.link;
            addProduct();
        }

        @Override
        public void failure(RetrofitError error) {

            if (error == null) {
                Snackbar.make(coordinationLayout, "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
            else {
                Snackbar.make(coordinationLayout, "Couldn't upload photo", Snackbar.LENGTH_SHORT).show();
                addProduct();
            }
        }
    }
    private Handler paymentServiceHandler = new IncomingHandler(this);
    private void addProduct(){
        Product newProduct = new Product();
        if(update) newProduct = currentProduct;
        newProduct.setUserId(getUserId());
        newProduct.setBrand(getInputBrand().getText().toString());
        newProduct.setName(getInputName().getText().toString());
        newProduct.setColor(spn.getSelectedItem().toString());
        newProduct.setDescription(getInputDescription().getText().toString());
        newProduct.setModel(getInputModel().getText().toString());
        newProduct.setCost(Double.parseDouble(getInputCost().getText().toString()));
        newProduct.setPrice(Double.parseDouble(getInputPrice().getText().toString()));
        if(!imgUrl.isEmpty())newProduct.setImage(imgUrl);
        else if(!update) newProduct.setImage("http://lorempixel.com/200/200/");

        if(update){
            SyncDataService.startActionUpdateProduct(this, newProduct, getMessenger());
        }
        else {
            SyncDataService.startActionAddProduct(this, newProduct, getMessenger());
        }


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
            case SyncDataService.MSG_TYPE_PRODUCT:

                Boolean add = data.getBoolean(SyncDataService.MSG_ADDED, false);

                String snackText = msgtxt;
                if (add) {

                    snackText = getInputName().getText().toString() + " added.";
                    if(update) snackText = getInputName().getText().toString() + " updated.";
                    SyncDataService.startActionFetchProducts(this, getUserId());
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
                AddProductActivity.this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private static final String TAG = "AddProductActivity";

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Snackbar.make(coordinationLayout, message, Snackbar.LENGTH_LONG).show();
            }
        }
    }


}

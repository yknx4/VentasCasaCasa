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
import android.widget.ImageButton;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.imgurmodel.ImageResponse;
import com.arekar.android.ventascasacasa.imgurmodel.Upload;
import com.arekar.android.ventascasacasa.model.Client;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.arekar.android.ventascasacasa.service.UploadService;
import com.bumptech.glide.Glide;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AddClientActivity extends BaseActivity implements View.OnClickListener, Validator.ValidationListener {

    private static final int YOUR_SELECT_PICTURE_REQUEST_CODE = 43;
    private Toolbar toolbar;
    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutPhone;
    private TextInputLayout inputLayoutAddress;
    private ImageButton img_btn;
    private Validator validator;
    private CoordinatorLayout coord;
    private Uri selectedImageUri;
    private boolean update = false;
    public static final String BUNDLE_CLIENT = "BUNDLE_CLIENT";
    private Client currentClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        img_btn = (ImageButton) findViewById(R.id.image_button);
        img_btn.setOnClickListener(this);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.input_layout_phone);
        inputLayoutAddress = (TextInputLayout) findViewById(R.id.input_layout_address);
        inputName =  (EditText) findViewById(R.id.input_name);
        inputAddress =(EditText) findViewById(R.id.input_address);
        inputEmail =  (EditText) findViewById(R.id.input_email);
        inputPhone =  (EditText) findViewById(R.id.input_phone);
        coord = (CoordinatorLayout) findViewById(R.id.coordination_layout);
         findViewById(R.id.btn_signup).setOnClickListener(this);
        validator = new Validator(this);
        validator.setValidationListener(this);
        Intent intent = getIntent();
        if (intent!=null){
            Bundle extras = intent.getExtras();
            if(extras!=null) {
                Client cl = extras.getParcelable(BUNDLE_CLIENT);
                if (cl != null) {
                    update = true;
                    currentClient = cl;
                    Glide.with(this).load(cl.getImage()).crossFade().into(img_btn);
                    loadClient();
                }
            }
        }


    }

    private void loadClient() {
        inputEmail.setText(currentClient.getEmail());
        inputPhone.setText(currentClient.getPhone());
        inputAddress.setText(currentClient.getAddress());
        inputName.setText(currentClient.getName());
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

    @NotEmpty
    EditText inputName;
    @NotEmpty
    @Email
    EditText inputEmail;
    @NotEmpty
    EditText inputPhone;
    @NotEmpty
    EditText inputAddress;

    private EditText getInputName(){
        return (EditText) findViewById(R.id.input_name);
    }

    private EditText getInputEmail(){
        return (EditText) findViewById(R.id.input_email);
    }

    private EditText getInputPhone(){
        return (EditText) findViewById(R.id.input_phone);
    }

    private EditText getInputAddress(){
        return (EditText) findViewById(R.id.input_address);
    }

    @Override
    public void onValidationSucceeded() {

        Snackbar.make(coord,"Yay! Now creating!", Snackbar.LENGTH_SHORT).show();
        findViewById(R.id.btn_signup).setEnabled(false);
        if (selectedImageUri == null){
            addClient();
            return;
        }
        upload = new Upload();
        try {
            upload.image = getContentResolver().openInputStream(selectedImageUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        upload.title = getInputName().getText().toString()+ Math.round(Math.random() * 1000);
        upload.description = getInputName().getText().toString()+getInputEmail().getText().toString();
        new UploadService(this).Execute(upload, new UiCallback());

    }


    private String imgUrl="";

    private class UiCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {
            imgUrl = imageResponse.data.link;
            addClient();
        }

        @Override
        public void failure(RetrofitError error) {

            if (error == null) {
                Snackbar.make(coord, "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
            else {
                Snackbar.make(coord, "Couldn't upload photo", Snackbar.LENGTH_SHORT).show();
                addClient();
            }
        }
    }
    private Handler paymentServiceHandler = new IncomingHandler(this);
    private void addClient(){
        Client newClient = new Client();
        if(update) newClient = currentClient;
        newClient.setUserId(getUserId());
        newClient.setAddress(getInputAddress().getText().toString());
        newClient.setEmail(getInputEmail().getText().toString());
        if(!imgUrl.isEmpty())newClient.setImage(imgUrl);
        else if(!update) newClient.setImage("http://lorempixel.com/200/200/people/");
        newClient.setName(getInputName().getText().toString());
        newClient.setPhone(getInputPhone().getText().toString());
        newClient.setEnabled(true);
        if(update){
            SyncDataService.startActionUpdateClient(this, newClient, getMessenger());
        }
        else {
            SyncDataService.startActionAddClient(this, newClient, getMessenger());
        }


    }

    @Override
    public void handleMessage(Message msg) {
        if (msg == null) return;
        Bundle data = msg.getData();
        String type = data.getString(SyncDataService.MSG_PARAM_TYPE);
        String msgtxt = data.getString("message");
        Log.v(TAG,"Response: "+msgtxt);
        if (type == null) return;

        switch (type) {
            case SyncDataService.MSG_TYPE_CLIENT:

                Boolean add = data.getBoolean(SyncDataService.MSG_ADDED, false);

                String snackText = msgtxt;
                if (add) {

                    snackText = getInputName().getText().toString() + " added.";
                    if(update) snackText = getInputName().getText().toString() + " updated.";
                    SyncDataService.startActionFetchClients(this);
                }
                Snackbar snack = Snackbar.make(coord, snackText, Snackbar.LENGTH_LONG);
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
                AddClientActivity.this.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private static final String TAG = "AddClientActivity";

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Snackbar.make(coord, message, Snackbar.LENGTH_LONG).show();
            }
        }
    }


}

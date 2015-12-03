package com.arekar.android.ventascasacasa.adapters;

import android.os.Messenger;
import android.provider.SyncStateContract;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.activities.BaseActivity;
import com.arekar.android.ventascasacasa.controllers.SaleController;
import com.arekar.android.ventascasacasa.helpers.ClientJsonHandler;
import com.arekar.android.ventascasacasa.helpers.Methods;
import com.arekar.android.ventascasacasa.helpers.PaymentsJsonHandler;
import com.arekar.android.ventascasacasa.model.AddressGPS;
import com.arekar.android.ventascasacasa.model.Client;
import com.arekar.android.ventascasacasa.model.Sale;
import com.arekar.android.ventascasacasa.service.SyncDataService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yknx4 on 2/12/15.
 */
public class NotificationsRvAdapter extends RecyclerView.Adapter<NotificationsRvAdapter.NotificationViewHolder> {

    private static final int TYPE_PAID = 343;
    private static final int TYPE_PAID_PAYMENTS = 346;
    private static final int TYPE_ON_PAYMENTS = 16;
    private static final String TAG = "SalesRvAdapter";

    WeakReference<BaseActivity> act;
    List<Sale> data;

    public PaymentsJsonHandler getPaymentsJsonHandler() {
        return paymentsJsonHandler;
    }

    public void setPaymentsJsonHandler(PaymentsJsonHandler paymentsJsonHandler) {
        this.paymentsJsonHandler = paymentsJsonHandler;
    }

    PaymentsJsonHandler paymentsJsonHandler = null;
    ClientJsonHandler clientJsonHandler = null;

    public void setClientJsonHandler(ClientJsonHandler clientJsonHandler){
        this.clientJsonHandler = clientJsonHandler;
    }

    public NotificationsRvAdapter(JsonArray paramJsonArray)
    {
        Type list = new TypeToken<List<Sale>>(){}.getType();
        data = ((List)new Gson().fromJson(paramJsonArray, list));
    }
    public NotificationsRvAdapter(List<Sale> data){
        Log.d(TAG, "Loading " + TAG + " Size: " + data.size());
        this.data = data;
    }
    public NotificationsRvAdapter(List<Sale> data, BaseActivity actv){
        this(data);
        act = new WeakReference<>(actv);
    }
    public NotificationsRvAdapter(JsonArray array,BaseActivity actv){
        this(array);
        act = new WeakReference<>(actv);
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup paramViewGroup, int viewType) {
        Log.d(TAG, "onCreateViewHolder()");
        int viewPaid = R.layout.sale_item;
        int viewPaidPayments = R.layout.sale_item;
        int viewOnPayments = R.layout.sale_item;
        int chosenView = R.layout.sale_item;

        switch (viewType){
            case TYPE_PAID_PAYMENTS:
                chosenView = viewPaidPayments;
                break;
            case TYPE_PAID:
                chosenView = viewPaid;
                break;
            case TYPE_ON_PAYMENTS:
                chosenView = viewOnPayments;
                break;
        }
        Log.d(TAG,"Inflating view");
        return  new NotificationViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.notification_item, paramViewGroup, false));

    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder()");
        Sale cr = data.get(position);
        switch (getItemViewType(position)){
            case TYPE_PAID_PAYMENTS:
                populatePaidPaymentsView(holder,cr);
                break;
            case TYPE_PAID:
                populatePaidView(holder, cr);
                break;
            case TYPE_ON_PAYMENTS:
                populateOnPaymentsView(holder,cr);
                break;
        }


    }

    private void populateOnPaymentsView(NotificationViewHolder holder, final Sale cr) {
        holder.cardViewClient.setVisibility(View.VISIBLE);
        SaleController saleController = SaleController.with(cr);
        if(paymentsJsonHandler!=null){
            saleController = saleController.payments(paymentsJsonHandler);
        }
        final Client localClient = clientJsonHandler.getById(cr.getClientId());

        Glide.with(holder.clientImage.getContext()).load(localClient.getImage()).crossFade().into(holder.clientImage);
        holder.locationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                AddressGPS localAddressGPS = localClient.getAddressGPS();
                Methods.launchGoogleMaps(paramView.getContext(), localAddressGPS.getLatitude().doubleValue(), localAddressGPS.getLongitude().doubleValue(), localClient.getName());
            }
        });
        holder.callButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View paramView)
            {
                Methods.makeCall(paramView.getContext(), localClient.getPhone());
            }
        });

        holder.clientName.setText(localClient.getName()+ " - "+ Methods.getMoneyString(cr.getPaymentCost()));


        holder.txtTotalCost.setText("Remaining: "+saleController.getRemainingString());
        if(saleController.getRemaining()<=0) holder.cardViewClient.setVisibility(View.GONE);
        String nextPay = holder.txtNextPayment.getContext().getString(R.string.msg_next_payment)+saleController.getNextPaymentString(holder.txtNextPayment.getContext());
        holder.txtNextPayment.setText(nextPay);
        final MaterialDialog dialog = new MaterialDialog.Builder(holder.btnPayment.getContext())
                .title(R.string.title_payment)
                .content(R.string.title_amount)
                .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .alwaysCallInputCallback()
                .input("Cantidad", cr.getPaymentCost().toString(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        Double max = cr.getPaymentCost();
                        Double current = Double.parseDouble(input.toString());
                        if (current > max) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        } else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        }
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                        //Toast.makeText(materialDialog.getContext(), "Pagaste: " + materialDialog.getInputEditText().getText(), Toast.LENGTH_SHORT).show();
                        Double amount = Double.valueOf(materialDialog.getInputEditText().getText().toString());
                        BaseActivity ba = act.get();
                        Messenger ms = null;
                        if (act != null) {
                            ms = ba.getMessenger();
                        }
                        SyncDataService.startActionDoPayment(materialDialog.getContext(), cr.getId(), amount, ms);
                    }
                })
                .build();
        holder.btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }

    private void populatePaidView(NotificationViewHolder holder, Sale cr) {
        populateOnPaymentsView(holder, cr);
    }

    private void populatePaidPaymentsView(NotificationViewHolder holder, Sale cr) {
        populateOnPaymentsView(holder,cr);
    }



    @Override
    public int getItemViewType(int position) {
        Log.d(TAG, "getItemViewType()");
        return TYPE_ON_PAYMENTS;

//        Sale cr = data.get(position);
//        if(!cr.getMultiplePayments()){
//            if(cr.getPaid())
//                return TYPE_PAID_PAYMENTS;
//            else
//                return TYPE_ON_PAYMENTS;
//        }
//        return TYPE_PAID;
    }

    public void onViewRecycled(NotificationViewHolder paramProductViewHolder)
    {
        super.onViewRecycled(paramProductViewHolder);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void sortByNextPayment() {
        if(paymentsJsonHandler==null) return;
        Collections.sort(data, new Comparator<Sale>() {
            @Override
            public int compare(Sale lhs, Sale rhs) {
                SaleController lhsc = SaleController.with(lhs).payments(paymentsJsonHandler);
                SaleController rhsc = SaleController.with(rhs).payments(paymentsJsonHandler);

                Long thisDate = lhsc.getNextPayment().getTime();
                Long anotherDate = rhsc.getNextPayment().getTime();
                if(thisDate>anotherDate)
                    return 1;
                if(thisDate<anotherDate)
                    return -1;
                else
                    return 0;
            }


        });
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
        private CardView cardViewClient;
        private TextView txtTotalCost;
        private TextView txtNextPayment;
        private ImageButton btnPayment;
        private ImageButton locationButton;
        private ImageButton callButton;
        private ImageView clientImage;
        private TextView clientName;
        public NotificationViewHolder(View convertView) {
            super(convertView);
            cardViewClient = (CardView) convertView.findViewById(R.id.card_view_client);
            txtTotalCost = (TextView) convertView.findViewById(R.id.payment_amount);
            txtNextPayment = (TextView) convertView.findViewById(R.id.next_payment);
            btnPayment = (ImageButton) convertView.findViewById(R.id.pay_button);
            locationButton = ((ImageButton)convertView.findViewById(R.id.location_button));
            callButton = ((ImageButton)convertView.findViewById(R.id.call_button));
           clientImage = ((ImageView)convertView.findViewById(R.id.client_image));
            clientName = ((TextView)convertView.findViewById(R.id.client_name));
        }
    }
}

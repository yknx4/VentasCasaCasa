package com.arekar.android.ventascasacasa.controllers;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.helpers.PaymentsJsonHandler;
import com.arekar.android.ventascasacasa.model.Sale;
import com.google.api.client.util.DateTime;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.ReadableInstant;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by yknx4 on 23/10/2015.
 */
public class SaleController {
    Sale item;
    PaymentsJsonHandler payments = null;
    private SaleController(Sale item){
        this.item = item;
    }
    public static SaleController with(Sale item){

        return new SaleController(item);
    }

    public SaleController payments(PaymentsJsonHandler p){
        payments = p;
        return this;
    }

    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
    public String getTotalCostString(){
        double amount =item.getPrice();

        return currencyFormatter.format(amount);
    }

    private static final String TAG = "SaleController";
    public Double getRemaining(){
        if(payments==null){
            Log.w(TAG,"No payments loaded, returning full cost.");
            return item.getPrice();
        }
        return  payments.getAmountPending(item);
    }


    public String getRemainingString(){

        return currencyFormatter.format(getRemaining());
    }

    private int getClosestDayCount(int targetDay, List<Integer> availableDays){
        int val= Integer.MAX_VALUE;
        for (int avail:availableDays){
            int avail_target = avail-targetDay;
            if(Math.abs(avail_target)<Math.abs(val)) val = avail_target;
        }
        return val;
    }


    public Date getNextPayment(){
        Date firstDay = new Date(Integer.parseInt(item.getDate()));
        Date currentDate = new Date();
        ReadableInstant first = new Instant(firstDay.getTime());
        ReadableInstant current = new Instant(currentDate.getTime());
        int daysBetween = Days.daysBetween(first,current).getDays();
        daysBetween %= item.getPaymentDue();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,daysBetween);
        int closestDate = getClosestDayCount(cal.get(Calendar.DAY_OF_WEEK),item.getAvailableDays());
        cal.add(Calendar.DAY_OF_YEAR,closestDate);
        return cal.getTime();
    }

    public String getNextPaymentString(Context con){
        Date nextPayment = getNextPayment();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextPayment);
        String month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault());
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH ));
        String full = String.format(con.getResources().getString(R.string.simple_pay_day_string), month, day);
        return full;
    }


}

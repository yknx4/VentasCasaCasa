package com.arekar.android.ventascasacasa.controllers;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.helpers.PaymentsJsonHandler;
import com.arekar.android.ventascasacasa.model.Payment;
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
    /**
     * The Item.
     */
    Sale item;
    /**
     * The Payments.
     */
    PaymentsJsonHandler payments = null;
    private SaleController(Sale item){
        this.item = item;
    }

    /**
     * Method to generate a SaleController related to an specific Sale
     *
     * @param item the item
     * @return the sale controller
     */
    public static SaleController with(Sale item){

        return new SaleController(item);
    }

    /**
     * Method to include payments context to the class.
     *
     * @param p the p
     * @return the sale controller
     */
    public SaleController payments(PaymentsJsonHandler p){
        payments = p;
        return this;
    }

    /**
     * The Currency formatter.
     */
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());

    /**
     * Get total cost string.
     *
     * @return the string
     */
    public String getTotalCostString(){
        double amount =item.getPrice();

        return currencyFormatter.format(amount);
    }

    private static final String TAG = "SaleController";

    /**
     * Get debt of this sale
     *
     * @return the remaining cost to pay. (Returns full cost if no payments are associated)
     */
    public Double getRemaining(){
        if(payments==null){
            Log.w(TAG,"No payments loaded, returning full cost.");
            return item.getPrice();
        }
        return  payments.getAmountPending(item);
    }


    /**
     * Get remaining debt as string.
     *
     * @return the string
     */
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


    /**
     * Get next payment date.
     *
     * @return the date
     */
    public Date getNextPayment(){
        Date firstDay = new Date(Long.parseLong(item.getDate()));
        Date currentDate = new Date();
        ReadableInstant first = new Instant(firstDay.getTime());
        ReadableInstant current = new Instant(currentDate.getTime());
        int daysBetween = Days.daysBetween(first,current).getDays();
        daysBetween %= item.getPaymentDue();
        Calendar cal = Calendar.getInstance();
        /*CLEAN*/
        cal.set(Calendar.HOUR,1);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        /*CLEAN*/
        cal.add(Calendar.DAY_OF_YEAR,daysBetween);
        int closestDate = getClosestDayCount(cal.get(Calendar.DAY_OF_WEEK),item.getAvailableDays());
        cal.add(Calendar.DAY_OF_YEAR,closestDate);
        if(payments!=null){
            Payment last = payments.getLastPayment(item.getId());
            Long dateMs = Long.valueOf(last.getDate());
            Date lDate = new Date(dateMs);
            Calendar lCal = Calendar.getInstance();
            lCal.setTime(lDate);
            /*CLEAN*/
            lCal.set(Calendar.HOUR,1);
            lCal.set(Calendar.MINUTE,0);
            lCal.set(Calendar.SECOND,0);
            lCal.set(Calendar.MILLISECOND, 0);
            /*CLEAN*/
            Log.d(TAG,"Difference: "+(cal.getTimeInMillis()-lCal.getTimeInMillis()));
            if(cal.getTimeInMillis()==lCal.getTimeInMillis()){
                Log.d(TAG,"Paid already done today. Next is...");
                cal.add(Calendar.DAY_OF_YEAR,daysBetween);
                int closestDateAgain = getClosestDayCount(cal.get(Calendar.DAY_OF_WEEK),item.getAvailableDays());
                cal.add(Calendar.DAY_OF_YEAR,closestDateAgain);
            }
        }
        return cal.getTime();
    }

    /**
     * Get next payment string .
     *
     * @param con the context
     * @return the string
     */
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

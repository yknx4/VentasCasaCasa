package com.arekar.android.ventascasacasa.controllers;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;

import com.arekar.android.ventascasacasa.R;
import com.arekar.android.ventascasacasa.helpers.PaymentsJsonHandler;
import com.arekar.android.ventascasacasa.model.Payment;
import com.arekar.android.ventascasacasa.model.Sale;
import com.google.api.client.util.DateTime;
import com.koushikdutta.ion.LoadDeepZoom;

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
        Log.d(TAG,"SaleController for "+item.getId());
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

    private Date getLastPaymentDate(){
        Payment lastP=null;
        if(payments!=null)lastP = payments.getLastPayment(item.getId());
        if(lastP!= null)return new Date(Long.parseLong(lastP.getDate()));
        return new Date(Long.parseLong(item.getDate()));
    }


    /**
     * Get next payment date.
     *
     * @return the date
     */
    public Date getNextPayment(){
        Calendar lastDate = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        Date lastP = getLastPaymentDate();
        lastDate.setTime(lastP);
        if(isSameDate(lastP,new Date())){
            lastDate.add(Calendar.DATE,item.getPaymentDue());
        }
        while (now.after(lastDate)){
            lastDate.add(Calendar.DATE,item.getPaymentDue());
        }
        while (!inAvailableDays(lastDate.get(Calendar.DAY_OF_WEEK))){
            lastDate.add(Calendar.DATE,1);
        }
        //TODO: Ver si es cierto que jala.
        return lastDate.getTime();
    }

    public boolean inAvailableDays(int number){
        for(int day:item.getAvailableDays()){
            if(day==number) return true;
        }
        return false;
    }

    Calendar cleanerCalendar = Calendar.getInstance();
    private Date cleanDate(Date inputDate){
        cleanerCalendar.setTime(inputDate);
        cleanerCalendar.set(Calendar.HOUR,1);
        cleanerCalendar.set(Calendar.MINUTE,0);
        cleanerCalendar.set(Calendar.SECOND,0);
        cleanerCalendar.set(Calendar.MILLISECOND, 0);
        return cleanerCalendar.getTime();
    }

    private boolean isSameDate(Date d1, Date d2){
        return cleanDate(d1).getTime()==cleanDate(d2).getTime();
    }
    /**
     * Get next payment string .
     *
     * @param con the context
     * @return the string
     */
    public String getNextPaymentString(Context con){
        Date nextPayment = getNextPayment();
        if(isSameDate(getNextPayment(),new Date())) return con.getString(R.string.string_today);
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextPayment);
        String month = cal.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault());
        String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH ));
        String full = String.format(con.getResources().getString(R.string.simple_pay_day_string), month, day);
        return full;
    }


}

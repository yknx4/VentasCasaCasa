package com.arekar.android.ventascasacasa.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;

public abstract class Methods
{
  private static final String filter = "[^0-9]";

  public static byte[] getBytes(InputStream is)  {

    try {
      int len;
      int size = 1024;
      byte[] buf;

      if (is instanceof ByteArrayInputStream) {
        size = is.available();
        buf = new byte[size];
        len = is.read(buf, 0, size);
      } else {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        buf = new byte[size];
        while ((len = is.read(buf, 0, size)) != -1)
          bos.write(buf, 0, len);
        buf = bos.toByteArray();
      }
      return buf;
    }
    catch (Exception e){
      return new byte[0];
    }
  }


  private boolean haveNetworkConnection(Context context) {
    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;

    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
    for (NetworkInfo ni : netInfo) {
      if (ni.getTypeName().equalsIgnoreCase("WIFI"))
        if (ni.isConnected())
          haveConnectedWifi = true;
      if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
        if (ni.isConnected())
          haveConnectedMobile = true;
    }
    return haveConnectedWifi || haveConnectedMobile;
  }

  public static void launchGoogleMaps(Context paramContext, double paramDouble1, double paramDouble2, String paramString)
  {
    Intent in = new Intent("android.intent.action.VIEW", Uri.parse("geo:0,0?q=" + Double.toString(paramDouble1) + "," + Double.toString(paramDouble2) + "(" + paramString + ")"));
    in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    paramContext.startActivity(in);
  }

  public static void makeCall(Context paramContext, String paramString)
  {
    paramString = paramString.replaceAll("[^0-9]", "");
    Intent in = new Intent("android.intent.action.CALL", Uri.parse("tel:" + paramString));
    if ((Build.VERSION.SDK_INT >= 23) && (paramContext.checkSelfPermission("android.permission.CALL_PHONE") != PackageManager.PERMISSION_GRANTED))
    {
      Toast.makeText(paramContext, "No permission to make calls", Toast.LENGTH_SHORT).show();
      return;
    }
    paramContext.startActivity(in);
  }
  static NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
  public static void sendMail(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.putExtra("android.intent.extra.EMAIL", new String[] { paramString });
    localIntent.setType("message/rfc822");
    paramContext.startActivity(Intent.createChooser(localIntent, "Choose an Email client"));
  }

  public static String getMoneyString(Double moni){
    return currencyFormatter.format(moni);
  }

}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.helpers.Methods
 * JD-Core Version:    0.6.0
 */
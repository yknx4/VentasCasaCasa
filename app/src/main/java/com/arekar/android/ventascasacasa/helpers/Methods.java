package com.arekar.android.ventascasacasa.helpers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.widget.Toast;

public abstract class Methods
{
  private static final String filter = "[^0-9]";

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
    if ((Build.VERSION.SDK_INT >= 23) && (paramContext.checkSelfPermission("android.permission.CALL_PHONE") != 0))
    {
      Toast.makeText(paramContext, "No permission to make calls", Toast.LENGTH_SHORT).show();
      return;
    }
    paramContext.startActivity(in);
  }

  public static void sendMail(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.putExtra("android.intent.extra.EMAIL", new String[] { paramString });
    localIntent.setType("message/rfc822");
    paramContext.startActivity(Intent.createChooser(localIntent, "Choose an Email client"));
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.helpers.Methods
 * JD-Core Version:    0.6.0
 */
package com.arekar.android.ventascasacasa.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yknx4 on 23/10/2015.
 */
public abstract class AbstractItem implements Parcelable{
  @Expose
  @SerializedName("_id")
  protected String Id;

  public String getId()
  {
    return this.Id;
  }

  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  @Expose
  @SerializedName("user_id")
  protected String userId;

  public String getUserId()
  {
    return this.userId;
  }

  public void setId(String paramString)
  {
    this.Id = paramString;
  }
}

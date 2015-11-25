package com.arekar.android.ventascasacasa.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by yknx4 on 23/10/2015.
 * An object that represents an element stored in a Web Service
 */
public abstract class AbstractItem implements Parcelable{
  /**
   * The Id.
   */
  @Expose
  @SerializedName("_id")
  protected String Id;

  /**
   * Gets id.
   *
   * @return the id
   */
  public String getId()
  {
    return this.Id;
  }

  /**
   * Sets user id.
   *
   * @param userId the user id
   */
  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  /**
   * The User id. Each elements is binded to an user id to avoid collisions in the web service.
   */
  @Expose
  @SerializedName("user_id")
  protected String userId;

  /**
   * Gets user id.
   *
   * @return the user id
   */
  public String getUserId()
  {
    return this.userId;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(String id)
  {
    this.Id = id;
  }
}

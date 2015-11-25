package com.arekar.android.ventascasacasa.model;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The type Payment.
 * This type represents a payment in the webb service
 */
public class Payment extends AbstractItem
{

  @Expose
  @SerializedName("date")
  private String date;

  @Expose
  @SerializedName("price")
  private Double price;

  @Expose
  @SerializedName("sale_id")
  private String saleId;


  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Payment))
      return false;
    Payment paramObject1 = (Payment)paramObject;

    return new EqualsBuilder().append(this.saleId, paramObject1.saleId).append(this.price, paramObject1.price).append(this.date, paramObject1.date).isEquals();
  }

  /**
   * Gets date.
   *
   * @return the date
   */
  public String getDate()
  {
    return this.date;
  }

  /**
   * Gets price.
   *
   * @return the price
   */
  public Double getPrice()
  {
    return this.price;
  }

  /**
   * Gets sale id.
   *
   * @return the sale id
   */
  public String getSaleId()
  {
    return this.saleId;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.saleId).append(this.price).append(this.date).toHashCode();
  }

  /**
   * Sets date.
   *
   * @param date the date
   */
  public void setDate(String date)
  {
    this.date = date;
  }

  /**
   * Sets price.
   *
   * @param price the price
   */
  public void setPrice(Double price)
  {
    this.price = price;
  }

  /**
   * Sets sale id.
   *
   * @param saleId the sale id
   */
  public void setSaleId(String saleId)
  {
    this.saleId = saleId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * With date payment.
   *
   * @param paramString the param string
   * @return the payment
   */
  public Payment withDate(String paramString)
  {
    this.date = paramString;
    return this;
  }

  /**
   * With price payment.
   *
   * @param paramDouble the param double
   * @return the payment
   */
  public Payment withPrice(Double paramDouble)
  {
    this.price = paramDouble;
    return this;
  }

  /**
   * With sale id payment.
   *
   * @param paramString the param string
   * @return the payment
   */
  public Payment withSaleId(String paramString)
  {
    this.saleId = paramString;
    return this;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.date);
    dest.writeValue(this.price);
    dest.writeString(this.saleId);
    dest.writeString(getUserId());
    dest.writeString(getId());
  }

  /**
   * Instantiates a new Payment.
   */
  public Payment() {
  }

  /**
   * Instantiates a new Payment.
   *
   * @param in the in
   */
  protected Payment(Parcel in) {
    this.date = in.readString();
    this.price = (Double) in.readValue(Double.class.getClassLoader());
    this.saleId = in.readString();
    setUserId(in.readString());
    setId(in.readString());
  }

  /**
   * The constant CREATOR.
   */
  public static final Creator<Payment> CREATOR = new Creator<Payment>() {
    public Payment createFromParcel(Parcel source) {
      return new Payment(source);
    }

    public Payment[] newArray(int size) {
      return new Payment[size];
    }
  };
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.model.Payment
 * JD-Core Version:    0.6.0
 */
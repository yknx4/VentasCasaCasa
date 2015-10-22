package com.arekar.android.ventascasacasa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Sale
{

  @Expose
  @SerializedName("_id")
  private String Id;

  @Expose
  @SerializedName("available_days")
  private List<Integer> availableDays = new ArrayList();

  @Expose
  @SerializedName("available_time")
  private List<Integer> availableTime = new ArrayList();

  @Expose
  @SerializedName("client_id")
  private String clientId;

  @Expose
  @SerializedName("date")
  private String date;

  @Expose
  @SerializedName("multiple_payments")
  private Boolean multiplePayments;

  @Expose
  @SerializedName("paid")
  private Boolean paid;

  @Expose
  @SerializedName("payment_cost")
  private Double paymentCost;

  @Expose
  @SerializedName("payment_due")
  private Integer paymentDue;

  @Expose
  @SerializedName("price")
  private Double price;

  @Expose
  @SerializedName("products")
  private List<String> products = new ArrayList();

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Sale))
      return false;
    Sale paramObjec = (Sale)paramObject;
    return new EqualsBuilder().append(this.Id, paramObjec.Id).append(this.date, paramObjec.date).append(this.clientId, paramObjec.clientId).append(this.products, paramObjec.products).append(this.price, paramObjec.price).append(this.paid, paramObjec.paid).append(this.multiplePayments, paramObjec.multiplePayments).append(this.paymentCost, paramObjec.paymentCost).append(this.availableDays, paramObjec.availableDays).append(this.availableTime, paramObjec.availableTime).append(this.paymentDue, paramObjec.paymentDue).isEquals();
  }

  public List<Integer> getAvailableDays()
  {
    return this.availableDays;
  }

  public List<Integer> getAvailableTime()
  {
    return this.availableTime;
  }

  public String getClientId()
  {
    return this.clientId;
  }

  public String getDate()
  {
    return this.date;
  }

  public String getId()
  {
    return this.Id;
  }

  public Boolean getMultiplePayments()
  {
    return this.multiplePayments;
  }

  public Boolean getPaid()
  {
    return this.paid;
  }

  public Double getPaymentCost()
  {
    return this.paymentCost;
  }

  public Integer getPaymentDue()
  {
    return this.paymentDue;
  }

  public Double getPrice()
  {
    return this.price;
  }

  public List<String> getProducts()
  {
    return this.products;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.Id).append(this.date).append(this.clientId).append(this.products).append(this.price).append(this.paid).append(this.multiplePayments).append(this.paymentCost).append(this.availableDays).append(this.availableTime).append(this.paymentDue).toHashCode();
  }

  public void setAvailableDays(List<Integer> paramList)
  {
    this.availableDays = paramList;
  }

  public void setAvailableTime(List<Integer> paramList)
  {
    this.availableTime = paramList;
  }

  public void setClientId(String paramString)
  {
    this.clientId = paramString;
  }

  public void setDate(String paramString)
  {
    this.date = paramString;
  }

  public void setId(String paramString)
  {
    this.Id = paramString;
  }

  public void setMultiplePayments(Boolean paramBoolean)
  {
    this.multiplePayments = paramBoolean;
  }

  public void setPaid(Boolean paramBoolean)
  {
    this.paid = paramBoolean;
  }

  public void setPaymentCost(Double paramDouble)
  {
    this.paymentCost = paramDouble;
  }

  public void setPaymentDue(Integer paramInteger)
  {
    this.paymentDue = paramInteger;
  }

  public void setPrice(Double paramDouble)
  {
    this.price = paramDouble;
  }

  public void setProducts(List<String> paramList)
  {
    this.products = paramList;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  public Sale withAvailableDays(List<Integer> paramList)
  {
    this.availableDays = paramList;
    return this;
  }

  public Sale withAvailableTime(List<Integer> paramList)
  {
    this.availableTime = paramList;
    return this;
  }

  public Sale withClientId(String paramString)
  {
    this.clientId = paramString;
    return this;
  }

  public Sale withDate(String paramString)
  {
    this.date = paramString;
    return this;
  }

  public Sale withId(String paramString)
  {
    this.Id = paramString;
    return this;
  }

  public Sale withMultiplePayments(Boolean paramBoolean)
  {
    this.multiplePayments = paramBoolean;
    return this;
  }

  public Sale withPaid(Boolean paramBoolean)
  {
    this.paid = paramBoolean;
    return this;
  }

  public Sale withPaymentCost(Double paramDouble)
  {
    this.paymentCost = paramDouble;
    return this;
  }

  public Sale withPaymentDue(Integer paramInteger)
  {
    this.paymentDue = paramInteger;
    return this;
  }

  public Sale withPrice(Double paramDouble)
  {
    this.price = paramDouble;
    return this;
  }

  public Sale withProducts(List<String> paramList)
  {
    this.products = paramList;
    return this;
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.model.Sale
 * JD-Core Version:    0.6.0
 */
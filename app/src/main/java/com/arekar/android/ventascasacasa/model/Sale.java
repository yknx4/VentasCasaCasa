package com.arekar.android.ventascasacasa.model;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The type Sale.
 * This represents a sale in the Web Service
 */
public class Sale extends AbstractItem {

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

  /**
   * Gets available days of the week.
   * These are represented as the number of the day starting on Sunday.
   * Ex. Sunday = 0, Monday = 1....
   *
   * @return the available days
   */
  public List<Integer> getAvailableDays()
  {
    return this.availableDays;
  }

  /**
   * Gets available time.
   * These are stored as 24hr format
   * Ex. 14 = 2:00pm
   *
   * @return the available time
   */
  public List<Integer> getAvailableTime()
  {
    return this.availableTime;
  }

  /**
   * Gets client id.
   *
   * @return the client id
   */
  public String getClientId()
  {
    return this.clientId;
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
   * Gets multiple payments.
   *
   * @return the multiple payments
   */
  public Boolean getMultiplePayments()
  {
    return this.multiplePayments;
  }

  /**
   * Gets paid.
   *
   * @return the paid
   */
  public Boolean getPaid()
  {
    return this.paid;
  }

  /**
   * Gets payment cost.
   *
   * @return the payment cost
   */
  public Double getPaymentCost()
  {
    return this.paymentCost;
  }

  /**
   * Gets payment due.
   *
   * @return the payment due
   */
  public Integer getPaymentDue()
  {
    return this.paymentDue;
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
   * Gets products.
   *
   * @return the products
   */
  public List<String> getProducts()
  {
    return this.products;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.Id).append(this.date).append(this.clientId).append(this.products).append(this.price).append(this.paid).append(this.multiplePayments).append(this.paymentCost).append(this.availableDays).append(this.availableTime).append(this.paymentDue).toHashCode();
  }

  /**
   * Sets available days.
   *
   * @param availableDays the available days
   */
  public void setAvailableDays(List<Integer> availableDays)
  {
    this.availableDays = availableDays;
  }

  /**
   * Sets available time.
   *
   * @param availableTime the available time
   */
  public void setAvailableTime(List<Integer> availableTime)
  {
    this.availableTime = availableTime;
  }



  private static String[] IntArrayToString(Integer[] ints){
    int l = ints.length;
    String[] str = new String[l];
    for(int i = 0;i<l;i++)
    {
      str[i] = ints[i].toString();
    }
    return str;
  }

  /**
   * Sets client id.
   *
   * @param clientId the client id
   */
  public void setClientId(String clientId)
  {
    this.clientId = clientId;
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
   * Sets multiple payments.
   *
   * @param multiplePayments the multiple payments
   */
  public void setMultiplePayments(Boolean multiplePayments)
  {
    this.multiplePayments = multiplePayments;
  }

  /**
   * Sets paid.
   *
   * @param paid the paid
   */
  public void setPaid(Boolean paid)
  {
    this.paid = paid;
  }

  /**
   * Sets payment cost.
   *
   * @param paymentCost the payment cost
   */
  public void setPaymentCost(Double paymentCost)
  {
    this.paymentCost = paymentCost;
  }

  /**
   * Sets payment due.
   *
   * @param paymentDue the payment due
   */
  public void setPaymentDue(Integer paymentDue)
  {
    this.paymentDue = paymentDue;
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
   * Sets products.
   *
   * @param products the products
   */
  public void setProducts(List<String> products)
  {
    this.products = products;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * With available days sale.
   *
   * @param availableDays the available days
   * @return the sale
   */
  public Sale withAvailableDays(List<Integer> availableDays)
  {
    this.availableDays = availableDays;
    return this;
  }

  /**
   * With available time sale.
   *
   * @param availableTimes the available times
   * @return the sale
   */
  public Sale withAvailableTime(List<Integer> availableTimes)
  {
    this.availableTime = availableTimes;
    return this;
  }

  /**
   * With client id sale.
   *
   * @param clientId the client id
   * @return the sale
   */
  public Sale withClientId(String clientId)
  {
    this.clientId = clientId;
    return this;
  }

  /**
   * With date sale.
   *
   * @param date the date
   * @return the sale
   */
  public Sale withDate(String date)
  {
    this.date = date;
    return this;
  }

  /**
   * With id sale.
   *
   * @param id the id
   * @return the sale
   */
  public Sale withId(String id)
  {
    this.Id = id;
    return this;
  }

  /**
   * With multiple payments sale.
   *
   * @param hasMultiplePayments the has multiple payments
   * @return the sale
   */
  public Sale withMultiplePayments(Boolean hasMultiplePayments)
  {
    this.multiplePayments = hasMultiplePayments;
    return this;
  }

  /**
   * With paid sale.
   *
   * @param isAlreadyPaid the is already paid
   * @return the sale
   */
  public Sale withPaid(Boolean isAlreadyPaid)
  {
    this.paid = isAlreadyPaid;
    return this;
  }

  /**
   * With payment cost sale.
   *
   * @param PaymentFee the payment fee
   * @return the sale
   */
  public Sale withPaymentCost(Double PaymentFee)
  {
    this.paymentCost = PaymentFee;
    return this;
  }

  /**
   * With payment due sale.
   *
   * @param PaymentDue the payment due
   * @return the sale
   */
  public Sale withPaymentDue(Integer PaymentDue)
  {
    this.paymentDue = PaymentDue;
    return this;
  }

  /**
   * With price sale.
   *
   * @param price the price
   * @return the sale
   */
  public Sale withPrice(Double price)
  {
    this.price = price;
    return this;
  }

  /**
   * With products sale.
   *
   * @param productsList the products list
   * @return the sale
   */
  public Sale withProducts(List<String> productsList)
  {
    this.products = productsList;
    return this;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeList(this.availableDays);
    dest.writeList(this.availableTime);
    dest.writeString(this.clientId);
    dest.writeString(this.date);
    dest.writeValue(this.multiplePayments);
    dest.writeValue(this.paid);
    dest.writeValue(this.paymentCost);
    dest.writeValue(this.paymentDue);
    dest.writeValue(this.price);
    dest.writeStringList(this.products);
    dest.writeString(getId());
    dest.writeString(getUserId());
  }

  /**
   * Instantiates a new Sale.
   */
  public Sale() {
  }

  /**
   * Instantiates a new Sale.
   *
   * @param in the in
   */
  protected Sale(Parcel in) {
    this.availableDays = new ArrayList<Integer>();
    in.readList(this.availableDays, List.class.getClassLoader());
    this.availableTime = new ArrayList<Integer>();
    in.readList(this.availableTime, List.class.getClassLoader());
    this.clientId = in.readString();
    this.date = in.readString();
    this.multiplePayments = (Boolean) in.readValue(Boolean.class.getClassLoader());
    this.paid = (Boolean) in.readValue(Boolean.class.getClassLoader());
    this.paymentCost = (Double) in.readValue(Double.class.getClassLoader());
    this.paymentDue = (Integer) in.readValue(Integer.class.getClassLoader());
    this.price = (Double) in.readValue(Double.class.getClassLoader());
    this.products = in.createStringArrayList();
    setId(in.readString());
    setUserId(in.readString());
  }

  /**
   * The constant CREATOR.
   */
  public static final Creator<Sale> CREATOR = new Creator<Sale>() {
    public Sale createFromParcel(Parcel source) {
      return new Sale(source);
    }

    public Sale[] newArray(int size) {
      return new Sale[size];
    }
  };
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.model.Sale
 * JD-Core Version:    0.6.0
 */
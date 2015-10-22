package com.arekar.android.ventascasacasa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Payment
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

  public String getDate()
  {
    return this.date;
  }

  public Double getPrice()
  {
    return this.price;
  }

  public String getSaleId()
  {
    return this.saleId;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.saleId).append(this.price).append(this.date).toHashCode();
  }

  public void setDate(String paramString)
  {
    this.date = paramString;
  }

  public void setPrice(Double paramDouble)
  {
    this.price = paramDouble;
  }

  public void setSaleId(String paramString)
  {
    this.saleId = paramString;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  public Payment withDate(String paramString)
  {
    this.date = paramString;
    return this;
  }

  public Payment withPrice(Double paramDouble)
  {
    this.price = paramDouble;
    return this;
  }

  public Payment withSaleId(String paramString)
  {
    this.saleId = paramString;
    return this;
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.model.Payment
 * JD-Core Version:    0.6.0
 */
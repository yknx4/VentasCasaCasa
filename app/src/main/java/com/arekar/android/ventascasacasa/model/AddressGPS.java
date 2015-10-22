package com.arekar.android.ventascasacasa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AddressGPS
{

  @Expose
  @SerializedName("latitude")
  private Double latitude;

  @Expose
  @SerializedName("longitude")
  private Double longitude;

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof AddressGPS))
      return false;
    AddressGPS paramObject2 = (AddressGPS)paramObject;
    return new EqualsBuilder().append(this.longitude, paramObject2.longitude).append(this.latitude, paramObject2.latitude).isEquals();
  }

  public Double getLatitude()
  {
    return this.latitude;
  }

  public Double getLongitude()
  {
    return this.longitude;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.longitude).append(this.latitude).toHashCode();
  }

  public void setLatitude(Double paramDouble)
  {
    this.latitude = paramDouble;
  }

  public void setLongitude(Double paramDouble)
  {
    this.longitude = paramDouble;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  public AddressGPS withLatitude(Double paramDouble)
  {
    this.latitude = paramDouble;
    return this;
  }

  public AddressGPS withLongitude(Double paramDouble)
  {
    this.longitude = paramDouble;
    return this;
  }
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.model.AddressGPS
 * JD-Core Version:    0.6.0
 */
package com.arekar.android.ventascasacasa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The type Address gps.
 */
public class AddressGPS implements Parcelable {

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

  /**
   * Gets latitude.
   *
   * @return the latitude
   */
  public Double getLatitude()
  {
    return this.latitude;
  }

  /**
   * Gets longitude.
   *
   * @return the longitude
   */
  public Double getLongitude()
  {
    return this.longitude;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.longitude).append(this.latitude).toHashCode();
  }

  /**
   * Sets latitude.
   *
   * @param latitude the latitude
   */
  public void setLatitude(Double latitude)
  {
    this.latitude = latitude;
  }

  /**
   * Sets longitude.
   *
   * @param longitude the longitude
   */
  public void setLongitude(Double longitude)
  {
    this.longitude = longitude;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * With latitude address gps.
   *
   * @param latitude the latitude
   * @return the address gps
   */
  public AddressGPS withLatitude(Double latitude)
  {
    this.latitude = latitude;
    return this;
  }

  /**
   * With longitude address gps.
   *
   * @param longitude the param double
   * @return the address gps
   */
  public AddressGPS withLongitude(Double longitude)
  {
    this.longitude = longitude;
    return this;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(this.latitude);
    dest.writeValue(this.longitude);
  }

  /**
   * Instantiates a new Address gps.
   */
  public AddressGPS() {
  }

  /**
   * Instantiates a new Address gps.
   *
   * @param in the in
   */
  protected AddressGPS(Parcel in) {
    this.latitude = (Double) in.readValue(Double.class.getClassLoader());
    this.longitude = (Double) in.readValue(Double.class.getClassLoader());
  }

  /**
   * The constant CREATOR.
   */
  public static final Parcelable.Creator<AddressGPS> CREATOR = new Parcelable.Creator<AddressGPS>() {
    public AddressGPS createFromParcel(Parcel source) {
      return new AddressGPS(source);
    }

    public AddressGPS[] newArray(int size) {
      return new AddressGPS[size];
    }
  };
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.model.AddressGPS
 * JD-Core Version:    0.6.0
 */
package com.arekar.android.ventascasacasa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Client extends AbstractItem implements Parcelable {



  @Expose
  @SerializedName("address")
  private String address;

  @Expose
  @SerializedName("addressGPS")
  private AddressGPS addressGPS;

  @Expose
  @SerializedName("email")
  private String email;

  @Expose
  @SerializedName("enabled")
  private Boolean enabled;

  @Expose
  @SerializedName("image")
  private String image;

  @Expose
  @SerializedName("name")
  private String name;

  @Expose
  @SerializedName("phone")
  private String phone;


  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Client))
      return false;
    Client paramObject1 = (Client)paramObject;
    return new EqualsBuilder().append(this.Id, paramObject1.Id).append(this.name, paramObject1.name).append(this.email, paramObject1.email).append(this.image, paramObject1.image).append(this.phone, paramObject1.phone).append(this.address, paramObject1.address).append(this.addressGPS, paramObject1.addressGPS).append(this.enabled, paramObject1.enabled).isEquals();
  }

  public String getAddress()
  {
    return this.address;
  }

  public AddressGPS getAddressGPS()
  {
    return this.addressGPS;
  }

  public String getEmail()
  {
    return this.email;
  }

  public Boolean getEnabled()
  {
    return this.enabled;
  }



  public String getImage()
  {
    return this.image;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.Id).append(this.name).append(this.email).append(this.image).append(this.phone).append(this.address).append(this.addressGPS).append(this.enabled).toHashCode();
  }

  public void setAddress(String paramString)
  {
    this.address = paramString;
  }

  public void setAddressGPS(AddressGPS paramAddressGPS)
  {
    this.addressGPS = paramAddressGPS;
  }

  public void setEmail(String paramString)
  {
    this.email = paramString;
  }

  public void setEnabled(Boolean paramBoolean)
  {
    this.enabled = paramBoolean;
  }



  public void setImage(String paramString)
  {
    this.image = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPhone(String paramString)
  {
    this.phone = paramString;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  public Client withAddress(String paramString)
  {
    this.address = paramString;
    return this;
  }

  public Client withAddressGPS(AddressGPS paramAddressGPS)
  {
    this.addressGPS = paramAddressGPS;
    return this;
  }

  public Client withEmail(String paramString)
  {
    this.email = paramString;
    return this;
  }

  public Client withEnabled(Boolean paramBoolean)
  {
    this.enabled = paramBoolean;
    return this;
  }

  public Client withId(String paramString)
  {
    this.Id = paramString;
    return this;
  }

  public Client withImage(String paramString)
  {
    this.image = paramString;
    return this;
  }

  public Client withName(String paramString)
  {
    this.name = paramString;
    return this;
  }

  public Client withPhone(String paramString)
  {
    this.phone = paramString;
    return this;
  }

  public Client() {
  }



  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.address);
    dest.writeParcelable(this.addressGPS, 0);
    dest.writeString(this.email);
    dest.writeValue(this.enabled);
    dest.writeString(this.image);
    dest.writeString(this.name);
    dest.writeString(this.phone);
    dest.writeString(getUserId());
    dest.writeString(this.getId());
  }

  protected Client(Parcel in) {
    this.address = in.readString();
    this.addressGPS = in.readParcelable(AddressGPS.class.getClassLoader());
    this.email = in.readString();
    this.enabled = (Boolean) in.readValue(Boolean.class.getClassLoader());
    this.image = in.readString();
    this.name = in.readString();
    this.phone = in.readString();
    setUserId(in.readString());
    this.setId(in.readString());
  }

  public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>() {
    public Client createFromParcel(Parcel source) {
      return new Client(source);
    }

    public Client[] newArray(int size) {
      return new Client[size];
    }
  };
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.model.Client
 * JD-Core Version:    0.6.0
 */
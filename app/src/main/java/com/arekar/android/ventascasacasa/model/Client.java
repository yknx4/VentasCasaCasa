package com.arekar.android.ventascasacasa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The type Client that represents a Client in the web service.
 */
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

  /**
   * Gets address.
   *
   * @return the address
   */
  public String getAddress()
  {
    return this.address;
  }

  /**
   * Gets address gps data.
   *
   * @return the address gps
   */
  public AddressGPS getAddressGPS()
  {
    return this.addressGPS;
  }

  /**
   * Gets email.
   *
   * @return the email
   */
  public String getEmail()
  {
    return this.email;
  }

  /**
   *  if enabled.
   *
   * @return the enabled
   */
  public Boolean getEnabled()
  {
    return this.enabled;
  }


  /**
   * Gets image.
   *
   * @return the image
   */
  public String getImage()
  {
    return this.image;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName()
  {
    return this.name;
  }

  /**
   * Gets phone.
   *
   * @return the phone
   */
  public String getPhone()
  {
    return this.phone;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.Id).append(this.name).append(this.email).append(this.image).append(this.phone).append(this.address).append(this.addressGPS).append(this.enabled).toHashCode();
  }

  /**
   * Sets address.
   *
   * @param address the address
   */
  public void setAddress(String address)
  {
    this.address = address;
  }

  /**
   * Sets address gps.
   *
   * @param addressGPS the address gps data
   */
  public void setAddressGPS(AddressGPS addressGPS)
  {
    this.addressGPS = addressGPS;
  }

  /**
   * Sets email.
   *
   * @param email the email
   */
  public void setEmail(String email)
  {
    this.email = email;
  }

  /**
   * Sets enabled.
   *
   * @param isEnabled the enabled state
   */
  public void setEnabled(Boolean isEnabled)
  {
    this.enabled = isEnabled;
  }


  /**
   * Sets image.
   *
   * @param image the image URL
   */
  public void setImage(String image)
  {
    this.image = image;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * Sets phone.
   *
   * @param phone the phone
   */
  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * With address client.
   *
   * @param paramString the param string
   * @return the client
   */
  public Client withAddress(String paramString)
  {
    this.address = paramString;
    return this;
  }

  /**
   * With address gps client.
   *
   * @param paramAddressGPS the param address gps
   * @return the client
   */
  public Client withAddressGPS(AddressGPS paramAddressGPS)
  {
    this.addressGPS = paramAddressGPS;
    return this;
  }

  /**
   * With email client.
   *
   * @param paramString the param string
   * @return the client
   */
  public Client withEmail(String paramString)
  {
    this.email = paramString;
    return this;
  }

  /**
   * With enabled client.
   *
   * @param paramBoolean the param boolean
   * @return the client
   */
  public Client withEnabled(Boolean paramBoolean)
  {
    this.enabled = paramBoolean;
    return this;
  }

  /**
   * With id client.
   *
   * @param paramString the param string
   * @return the client
   */
  public Client withId(String paramString)
  {
    this.Id = paramString;
    return this;
  }

  /**
   * With image client.
   *
   * @param paramString the param string
   * @return the client
   */
  public Client withImage(String paramString)
  {
    this.image = paramString;
    return this;
  }

  /**
   * With name client.
   *
   * @param paramString the param string
   * @return the client
   */
  public Client withName(String paramString)
  {
    this.name = paramString;
    return this;
  }

  /**
   * With phone client.
   *
   * @param paramString the param string
   * @return the client
   */
  public Client withPhone(String paramString)
  {
    this.phone = paramString;
    return this;
  }

  /**
   * Instantiates a new Client.
   */
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

  /**
   * Instantiates a new Client.
   *
   * @param in the in
   */
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

  /**
   * The constant CREATOR.
   */
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
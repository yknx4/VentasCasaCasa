package com.arekar.android.ventascasacasa.model;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Product extends AbstractItem
{



  @Expose
  @SerializedName("brand")
  private String brand;

  @Expose
  @SerializedName("color")
  private String color;

  @Expose
  @SerializedName("cost")
  private Double cost;

  @Expose
  @SerializedName("description")
  private String description;

  @Expose
  @SerializedName("image")
  private String image;

  @Expose
  @SerializedName("model")
  private String model;

  @Expose
  @SerializedName("name")
  private String name;

  @Expose
  @SerializedName("price")
  private Double price;

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Product))
      return false;
    Product paramObject1 = (Product)paramObject;
    return new EqualsBuilder().append(this.Id, paramObject1.Id).append(this.name, paramObject1.name).append(this.brand, paramObject1.brand).append(this.model, paramObject1.model).append(this.cost, paramObject1.cost).append(this.price, paramObject1.price).append(this.color, paramObject1.color).append(this.image, paramObject1.image).append(this.description, paramObject1.description).isEquals();
  }

  public String getBrand()
  {
    return this.brand;
  }

  public String getColor()
  {
    return this.color;
  }

  public Double getCost()
  {
    return this.cost;
  }

  public String getDescription()
  {
    return this.description;
  }



  public String getImage()
  {
    return this.image;
  }

  public String getModel()
  {
    return this.model;
  }

  public String getName()
  {
    return this.name;
  }

  public Double getPrice()
  {
    return this.price;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.Id).append(this.name).append(this.brand).append(this.model).append(this.cost).append(this.price).append(this.color).append(this.image).append(this.description).toHashCode();
  }

  public void setBrand(String paramString)
  {
    this.brand = paramString;
  }

  public void setColor(String paramString)
  {
    this.color = paramString;
  }

  public void setCost(Double paramDouble)
  {
    this.cost = paramDouble;
  }

  public void setDescription(String paramString)
  {
    this.description = paramString;
  }



  public void setImage(String paramString)
  {
    this.image = paramString;
  }

  public void setModel(String paramString)
  {
    this.model = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPrice(Double paramDouble)
  {
    this.price = paramDouble;
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  public Product withBrand(String paramString)
  {
    this.brand = paramString;
    return this;
  }

  public Product withColor(String paramString)
  {
    this.color = paramString;
    return this;
  }

  public Product withCost(Double paramDouble)
  {
    this.cost = paramDouble;
    return this;
  }

  public Product withDescription(String paramString)
  {
    this.description = paramString;
    return this;
  }

  public Product withId(String paramString)
  {
    this.Id = paramString;
    return this;
  }

  public Product withImage(String paramString)
  {
    this.image = paramString;
    return this;
  }

  public Product withModel(String paramString)
  {
    this.model = paramString;
    return this;
  }

  public Product withName(String paramString)
  {
    this.name = paramString;
    return this;
  }

  public Product withPrice(Double paramDouble)
  {
    this.price = paramDouble;
    return this;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.brand);
    dest.writeString(this.color);
    dest.writeValue(this.cost);
    dest.writeString(this.description);
    dest.writeString(this.image);
    dest.writeString(this.model);
    dest.writeString(this.name);
    dest.writeValue(this.price);
    dest.writeString(getId());
    dest.writeString(getUserId());
  }

  public Product() {
  }

  protected Product(Parcel in) {
    this.brand = in.readString();
    this.color = in.readString();
    this.cost = (Double) in.readValue(Double.class.getClassLoader());
    this.description = in.readString();
    this.image = in.readString();
    this.model = in.readString();
    this.name = in.readString();
    this.price = (Double) in.readValue(Double.class.getClassLoader());
    setId(in.readString());
    setUserId(in.readString());
  }

  public static final Creator<Product> CREATOR = new Creator<Product>() {
    public Product createFromParcel(Parcel source) {
      return new Product(source);
    }

    public Product[] newArray(int size) {
      return new Product[size];
    }
  };
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.model.Product
 * JD-Core Version:    0.6.0
 */
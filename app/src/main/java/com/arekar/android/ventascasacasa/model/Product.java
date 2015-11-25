package com.arekar.android.ventascasacasa.model;

import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The type Product.
 * This represents a product in the Web Service
 */
public class Product extends AbstractItem
{



  /**
   * The constant CREATOR.
   */
  public static final Creator<Product> CREATOR = new Creator<Product>() {
    public Product createFromParcel(Parcel source) {
      return new Product(source);
    }

    public Product[] newArray(int size) {
      return new Product[size];
    }
  };
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

  /**
   * Instantiates a new Product.
   */
  public Product() {
  }

  /**
   * Instantiates a new Product.
   *
   * @param in the in
   */
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

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Product))
      return false;
    Product paramObject1 = (Product)paramObject;
    return new EqualsBuilder().append(this.Id, paramObject1.Id).append(this.name, paramObject1.name).append(this.brand, paramObject1.brand).append(this.model, paramObject1.model).append(this.cost, paramObject1.cost).append(this.price, paramObject1.price).append(this.color, paramObject1.color).append(this.image, paramObject1.image).append(this.description, paramObject1.description).isEquals();
  }

  /**
   * Gets brand.
   *
   * @return the brand
   */
  public String getBrand()
  {
    return this.brand;
  }

  /**
   * Sets brand.
   *
   * @param brand the brand
   */
  public void setBrand(String brand)
  {
    this.brand = brand;
  }

  /**
   * Gets color.
   *
   * @return the color
   */
  public String getColor()
  {
    return this.color;
  }

  /**
   * Sets color.
   *
   * @param color the color
   */
  public void setColor(String color)
  {
    this.color = color;
  }

  /**
   * Gets cost.
   *
   * @return the cost
   */
  public Double getCost()
  {
    return this.cost;
  }

  /**
   * Sets cost.
   *
   * @param cost the cost
   */
  public void setCost(Double cost)
  {
    this.cost = cost;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription()
  {
    return this.description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description)
  {
    this.description = description;
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
   * Sets image.
   *
   * @param image the image
   */
  public void setImage(String image)
  {
    this.image = image;
  }

  /**
   * Gets model.
   *
   * @return the model
   */
  public String getModel()
  {
    return this.model;
  }

  /**
   * Sets model.
   *
   * @param model the model
   */
  public void setModel(String model)
  {
    this.model = model;
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
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name)
  {
    this.name = name;
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
   * Sets price.
   *
   * @param price the price
   */
  public void setPrice(Double price)
  {
    this.price = price;
  }

  public int hashCode()
  {
    return new HashCodeBuilder().append(this.Id).append(this.name).append(this.brand).append(this.model).append(this.cost).append(this.price).append(this.color).append(this.image).append(this.description).toHashCode();
  }

  public String toString()
  {
    return ToStringBuilder.reflectionToString(this);
  }

  /**
   * With brand product.
   *
   * @param brand the brand
   * @return the product
   */
  public Product withBrand(String brand)
  {
    this.brand = brand;
    return this;
  }

  /**
   * With color product.
   *
   * @param color the color
   * @return the product
   */
  public Product withColor(String color)
  {
    this.color = color;
    return this;
  }

  /**
   * With cost product.
   *
   * @param cost the cost
   * @return the product
   */
  public Product withCost(Double cost)
  {
    this.cost = cost;
    return this;
  }

  /**
   * With description product.
   *
   * @param description the description
   * @return the product
   */
  public Product withDescription(String description)
  {
    this.description = description;
    return this;
  }

  /**
   * With id product.
   *
   * @param id the id
   * @return the product
   */
  public Product withId(String id)
  {
    this.Id = id;
    return this;
  }

  /**
   * With image product.
   *
   * @param image the image
   * @return the product
   */
  public Product withImage(String image)
  {
    this.image = image;
    return this;
  }

  /**
   * With model product.
   *
   * @param model the model
   * @return the product
   */
  public Product withModel(String model)
  {
    this.model = model;
    return this;
  }

  /**
   * With name product.
   *
   * @param name the name
   * @return the product
   */
  public Product withName(String name)
  {
    this.name = name;
    return this;
  }

  /**
   * With price product.
   *
   * @param price the price
   * @return the product
   */
  public Product withPrice(Double price)
  {
    this.price = price;
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
}

/* Location:           D:\Apps\Apk2Java\tools\classes-dex2jar.jar
 * Qualified Name:     com.arekar.android.ventascasaencasa.model.Product
 * JD-Core Version:    0.6.0
 */
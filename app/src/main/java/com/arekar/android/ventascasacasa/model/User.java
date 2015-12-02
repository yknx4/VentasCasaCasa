package com.arekar.android.ventascasacasa.model;


import android.os.Parcel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class User extends AbstractItem{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("created")
    @Expose
    private Long created;
    @SerializedName("password")
    @Expose
    private String password;



    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user The user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The created
     */
    public Long getCreated() {
        return created;
    }

    /**
     * @param created The created
     */
    public void setCreated(Long created) {
        this.created = created;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(Id).append(name).append(user).append(image).append(email).append(created).append(password).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof User) == false) {
            return false;
        }
        User rhs = ((User) other);
        return new EqualsBuilder().append(Id, rhs.Id).append(name, rhs.name).append(user, rhs.user).append(image, rhs.image).append(email, rhs.email).append(created, rhs.created).append(password, rhs.password).isEquals();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.user);
        dest.writeString(this.image);
        dest.writeString(this.email);
        dest.writeValue(this.created);
        dest.writeString(this.password);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.user = in.readString();
        this.image = in.readString();
        this.email = in.readString();
        this.created = (Long) in.readValue(Long.class.getClassLoader());
        this.password = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
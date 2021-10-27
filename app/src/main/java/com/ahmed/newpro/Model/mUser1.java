package com.ahmed.newpro.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class mUser1 {

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("birth_date")
    @Expose
    private Object birthDate;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("cridet_card")
    @Expose
    private Object cridetCard;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("Blocked")
    @Expose
    private Integer blocked;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("msg")
    @Expose
    private String msg;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public mUser1(String email, String fullName, String password, String phone) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.phone = phone;
    }

    public mUser1(Integer customerId, String email, String fullName, String password, Object address, Object birthDate, String phone, Object cridetCard, String image, Integer blocked) {
        this.customerId = customerId;
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.address = address;
        this.birthDate = birthDate;
        this.phone = phone;
        this.cridetCard = cridetCard;
        this.image = image;
        this.blocked = blocked;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Object birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getCridetCard() {
        return cridetCard;
    }

    public void setCridetCard(Object cridetCard) {
        this.cridetCard = cridetCard;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getBlocked() {
        return blocked;
    }

    public void setBlocked(Integer blocked) {
        this.blocked = blocked;
    }

}

package com.example.villageservice.model;

import com.example.villageservice.utility.ConstantVariable;
import com.google.gson.annotations.SerializedName;

public class Admin {

    @SerializedName("name")
    private String name;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("username")
    private String username = ConstantVariable.ADMIN_USERNAME;

    @SerializedName("password")
    private String password = ConstantVariable.ADMIN_PASSWORD;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.villageservice.model;

import com.google.gson.annotations.SerializedName;

public class Admin {

    @SerializedName("name")
    private String name;

    @SerializedName("phone_number")
    private String phoneNumber;

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
}

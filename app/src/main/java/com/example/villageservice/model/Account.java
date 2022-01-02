package com.example.villageservice.model;

import com.google.gson.annotations.SerializedName;

public class Account {

    @SerializedName("id_kk")
    private String idKK;

    @SerializedName("password")
    private String password;

    public String getIdKK() {
        return idKK;
    }

    public void setIdKK(String idKK) {
        this.idKK = idKK;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.example.villageservice.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KartuKeluargaList {

    @SerializedName("kartu_keluarga_list")
    private List<KartuKeluarga> kartuKeluargaList;

    public List<KartuKeluarga> getKartuKeluargaList() {
        return kartuKeluargaList;
    }

    public void setKartuKeluargaList(List<KartuKeluarga> kartuKeluargaList) {
        this.kartuKeluargaList = kartuKeluargaList;
    }
}

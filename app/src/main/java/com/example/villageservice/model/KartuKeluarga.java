package com.example.villageservice.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class KartuKeluarga {

    @SerializedName("id_kartu_keluarga")
    private String idKartuKeluarga;

    @SerializedName("nama_kepala_keluarga")
    private String namaKepalaKeluarga = "";

    @SerializedName("alamat_lengkap")
    private String alamatLengkap = "";

    @SerializedName("alamat_rumah")
    private String alamatRumah = "";

    @SerializedName("nomor_rt")
    private String nomorRt;

    @SerializedName("nomor_rw")
    private String nomorRw;

    @SerializedName("kelurahan")
    private String kelurahan = "";

    @SerializedName("kecamatan")
    private String kecamatan = "";

    @SerializedName("kota")
    private String kota = "";

    @SerializedName("kode_pos")
    private String kodePos;

    @SerializedName("provinsi")
    private String provinsi = "";

    @SerializedName("status")
    private String status = "";

    @SerializedName("anggota_keluarga")
    private List<User> anggotaKeluarga = new ArrayList<>();

    @SerializedName("password")
    private String password = "";

    @SerializedName("is_notification")
    private boolean isNotificationOn;

    public String getIdKartuKeluarga() {
        return idKartuKeluarga;
    }

    public void setIdKartuKeluarga(String idKartuKeluarga) {
        this.idKartuKeluarga = idKartuKeluarga;
    }

    public String getNamaKepalaKeluarga() {
        return namaKepalaKeluarga;
    }

    public void setNamaKepalaKeluarga(String namaKepalaKeluarga) {
        this.namaKepalaKeluarga = namaKepalaKeluarga;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getAlamatRumah() {
        return alamatRumah;
    }

    public void setAlamatRumah(String alamatRumah) {
        this.alamatRumah = alamatRumah;
    }

    public String getNomorRt() {
        return nomorRt;
    }

    public void setNomorRt(String nomorRt) {
        this.nomorRt = nomorRt;
    }

    public String getNomorRw() {
        return nomorRw;
    }

    public void setNomorRw(String nomorRw) {
        this.nomorRw = nomorRw;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getKeluargaList() {
        return anggotaKeluarga;
    }

    public void setKeluargaList(List<User> anggotaKeluarga) {
        this.anggotaKeluarga = anggotaKeluarga;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNotificationOn() {
        return isNotificationOn;
    }

    public void setNotificationOn(boolean notificationOn) {
        isNotificationOn = notificationOn;
    }
}

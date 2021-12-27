package com.example.villageservice.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class KartuKeluarga {

    @SerializedName("id_kartu_keluarga")
    private long idKartuKeluarga;

    @SerializedName("nama_kepala_keluarga")
    private String namaKepalaKeluarga = "";

    @SerializedName("alamat")
    private String alamat = "";

    @SerializedName("nomor_rt")
    private int nomorRt;

    @SerializedName("nomor_rw")
    private int nomorRw;

    @SerializedName("kelurahan")
    private String kelurahan = "";

    @SerializedName("kecamatan")
    private String kecamatan = "";

    @SerializedName("kota")
    private String kota = "";

    @SerializedName("kode_pos")
    private int kodePos;

    @SerializedName("provinsi")
    private String provinsi = "";

    @SerializedName("anggota_keluarga")
    private List<User> anggotaKeluarga = new ArrayList<>();

    public long getIdKartuKeluarga() {
        return idKartuKeluarga;
    }

    public void setIdKartuKeluarga(long idKartuKeluarga) {
        this.idKartuKeluarga = idKartuKeluarga;
    }

    public String getNamaKepalaKeluarga() {
        return namaKepalaKeluarga;
    }

    public void setNamaKepalaKeluarga(String namaKepalaKeluarga) {
        this.namaKepalaKeluarga = namaKepalaKeluarga;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getNomorRt() {
        return nomorRt;
    }

    public void setNomorRt(int nomorRt) {
        this.nomorRt = nomorRt;
    }

    public int getNomorRw() {
        return nomorRw;
    }

    public void setNomorRw(int nomorRw) {
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

    public int getKodePos() {
        return kodePos;
    }

    public void setKodePos(int kodePos) {
        this.kodePos = kodePos;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public List<User> getAnggotaKeluarga() {
        return anggotaKeluarga;
    }

    public void setAnggotaKeluarga(List<User> anggotaKeluarga) {
        this.anggotaKeluarga = anggotaKeluarga;
    }
}

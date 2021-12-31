package com.example.villageservice.model;

import com.google.gson.annotations.SerializedName;

public class CoveringLetter {

    @SerializedName("cl_lampiran")
    String clLampiran;

    @SerializedName("cl_nomor_header")
    String clNomorHeader;

    @SerializedName("cl_nama")
    String clNama;

    @SerializedName("cl_jenis_kelamin")
    String clJenisKelamin;

    @SerializedName("cl_tanggal_lahir")
    String clTanggalLahir;

    @SerializedName("cl_pekerjaan")
    String clPekerjaan;

    @SerializedName("cl_ktp")
    String clKtp;

    @SerializedName("cl_kewarganegaraan")
    String clKewarganegaraan;

    @SerializedName("cl_pendidikan")
    String clPendidikan;

    @SerializedName("cl_agama")
    String clAgama;

    @SerializedName("cl_alamat")
    String clAlamat;

    @SerializedName("cl_keperluan")
    String clKeperluan;

    @SerializedName("cl_nomor_footer")
    String clNomorFooter;

    @SerializedName("cl_tanggal_footer_rw")
    String clTanggalFooterRw;

    @SerializedName("cl_nama_rw")
    String clNamaRw;

    @SerializedName("cl_tanggal_rt")
    String clTanggalRt;

    @SerializedName("cl_nama_rt")
    String clNamaRt;

    @SerializedName("cl_is_approved")
    boolean isApproved;

    public CoveringLetter() {

    }

    public CoveringLetter(String clLampiran, String clNomorHeader, String clNama, String clJenisKelamin,
                          String clTanggalLahir, String clPekerjaan, String clKtp, String clKewarganegaraan,
                          String clPendidikan, String clAgama, String clAlamat, String clKeperluan, String clNomorFooter,
                          String clTanggalFooterRw, String clNamaRw, String clTanggalRt, String clNamaRt) {
        this.clLampiran = clLampiran;
        this.clNomorHeader = clNomorHeader;
        this.clNama = clNama;
        this.clJenisKelamin = clJenisKelamin;
        this.clTanggalLahir = clTanggalLahir;
        this.clPekerjaan = clPekerjaan;
        this.clKtp = clKtp;
        this.clKewarganegaraan = clKewarganegaraan;
        this.clPendidikan = clPendidikan;
        this.clAgama = clAgama;
        this.clAlamat = clAlamat;
        this.clKeperluan = clKeperluan;
        this.clNomorFooter = clNomorFooter;
        this.clTanggalFooterRw = clTanggalFooterRw;
        this.clNamaRw = clNamaRw;
        this.clTanggalRt = clTanggalRt;
        this.clNamaRt = clNamaRt;
    }

    public String getClLampiran() {
        return clLampiran;
    }

    public void setClLampiran(String clLampiran) {
        this.clLampiran = clLampiran;
    }

    public String getClNomorHeader() {
        return clNomorHeader;
    }

    public void setClNomorHeader(String clNomorHeader) {
        this.clNomorHeader = clNomorHeader;
    }

    public String getClNama() {
        return clNama;
    }

    public void setClNama(String clNama) {
        this.clNama = clNama;
    }

    public String getClJenisKelamin() {
        return clJenisKelamin;
    }

    public void setClJenisKelamin(String clJenisKelamin) {
        this.clJenisKelamin = clJenisKelamin;
    }

    public String getClTanggalLahir() {
        return clTanggalLahir;
    }

    public void setClTanggalLahir(String clTanggalLahir) {
        this.clTanggalLahir = clTanggalLahir;
    }

    public String getClPekerjaan() {
        return clPekerjaan;
    }

    public void setClPekerjaan(String clPekerjaan) {
        this.clPekerjaan = clPekerjaan;
    }

    public String getClKtp() {
        return clKtp;
    }

    public void setClKtp(String clKtp) {
        this.clKtp = clKtp;
    }

    public String getClKewarganegaraan() {
        return clKewarganegaraan;
    }

    public void setClKewarganegaraan(String clKewarganegaraan) {
        this.clKewarganegaraan = clKewarganegaraan;
    }

    public String getClPendidikan() {
        return clPendidikan;
    }

    public void setClPendidikan(String clPendidikan) {
        this.clPendidikan = clPendidikan;
    }

    public String getClAgama() {
        return clAgama;
    }

    public void setClAgama(String clAgama) {
        this.clAgama = clAgama;
    }

    public String getClAlamat() {
        return clAlamat;
    }

    public void setClAlamat(String clAlamat) {
        this.clAlamat = clAlamat;
    }

    public String getClKeperluan() {
        return clKeperluan;
    }

    public void setClKeperluan(String clKeperluan) {
        this.clKeperluan = clKeperluan;
    }

    public String getClNomorFooter() {
        return clNomorFooter;
    }

    public void setClNomorFooter(String clNomorFooter) {
        this.clNomorFooter = clNomorFooter;
    }

    public String getClTanggalFooterRw() {
        return clTanggalFooterRw;
    }

    public void setClTanggalFooterRw(String clTanggalFooterRw) {
        this.clTanggalFooterRw = clTanggalFooterRw;
    }

    public String getClNamaRw() {
        return clNamaRw;
    }

    public void setClNamaRw(String clNamaRw) {
        this.clNamaRw = clNamaRw;
    }

    public String getClTanggalRt() {
        return clTanggalRt;
    }

    public void setClTanggalRt(String clTanggalRt) {
        this.clTanggalRt = clTanggalRt;
    }

    public String getClNamaRt() {
        return clNamaRt;
    }

    public void setClNamaRt(String clNamaRt) {
        this.clNamaRt = clNamaRt;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }
}

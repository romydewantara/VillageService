package com.example.villageservice.model;

import com.google.gson.annotations.SerializedName;

public class CoveringLetter {

    @SerializedName("cl_id")
    String clId;

    @SerializedName("cl_lampiran")
    String clLampiran;

    @SerializedName("cl_nomor_header")
    String clNomorHeader;

    @SerializedName("cl_nama")
    String clNama;

    @SerializedName("cl_jenis_kelamin")
    String clJenisKelamin;

    @SerializedName("cl_tempat_tanggal_lahir")
    String clTempatTanggalLahir;

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

    @SerializedName("cl_is_opened")
    boolean isOpened;

    @SerializedName("cl_type")
    String clType;

    @SerializedName("cl_tanggal_pengajuan")
    String clTglPengajuan;

    public CoveringLetter() {

    }

    public CoveringLetter(String clId, String clLampiran, String clNomorHeader, String clNama, String clJenisKelamin,
                          String clTempatTanggalLahir, String clPekerjaan, String clKtp, String clKewarganegaraan,
                          String clPendidikan, String clAgama, String clAlamat, String clKeperluan, String clNomorFooter,
                          String clTanggalFooterRw, String clNamaRw, String clTanggalRt, String clNamaRt, String clType,
                          String clTglPengajuan, boolean isApproved) {
        this.clId = clId;
        this.clLampiran = clLampiran;
        this.clNomorHeader = clNomorHeader;
        this.clNama = clNama;
        this.clJenisKelamin = clJenisKelamin;
        this.clTempatTanggalLahir = clTempatTanggalLahir;
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
        this.clType = clType;
        this.clTglPengajuan = clTglPengajuan;
        this.isApproved = isApproved;
    }

    public String getClId() {
        return clId;
    }

    public void setClId(String clId) {
        this.clId = clId;
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

    public String getClTempatTanggalLahir() {
        return clTempatTanggalLahir;
    }

    public void setClTempatTanggalLahir(String clTempatTanggalLahir) {
        this.clTempatTanggalLahir = clTempatTanggalLahir;
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

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public String getClType() {
        return clType;
    }

    public void setClType(String clType) {
        this.clType = clType;
    }

    public String getClTglPengajuan() {
        return clTglPengajuan;
    }

    public void setClTglPengajuan(String clTglPengajuan) {
        this.clTglPengajuan = clTglPengajuan;
    }
}

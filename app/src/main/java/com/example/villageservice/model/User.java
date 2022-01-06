package com.example.villageservice.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id_ktp")
    private String idKtp = "";

    @SerializedName("id_kartu_keluarga")
    private String idKartuKeluarga = "";

    @SerializedName("nama_lengkap")
    private String namaLengkap = "";

    @SerializedName("jenis_kelamin")
    private String jenisKelamin = "";

    @SerializedName("tempat_lahir")
    private String tempatLahir = "";

    @SerializedName("tanggal_lahir")
    private TanggalDetail tanggalLahir = null;

    @SerializedName("tanggal_bergabung")
    private TanggalDetail tanggalBergabung = null;

    @SerializedName("agama")
    private String agama = "";

    @SerializedName("pendidikan")
    private String pendidikan = "";

    @SerializedName("jenis_pekerjaan")
    private String jenisPekerjaan = "";

    @SerializedName("status_pernikahan")
    private String statusPernikahan = "";

    @SerializedName("status_hubungan_dalam_keluarga")
    private String statusHubunganDalamKeluarga = "";

    @SerializedName("kewarganegaraan")
    private String kewarganegaraan = "";

    @SerializedName("nama_ayah")
    private String namaAyah = "";

    @SerializedName("nama_ibu")
    private String namaIbu = "";

    public String getIdKtp() {
        return idKtp;
    }

    public void setIdKtp(String idKtp) {
        this.idKtp = idKtp;
    }

    public String getIdKartuKeluarga() {
        return idKartuKeluarga;
    }

    public void setIdKartuKeluarga(String idKartuKeluarga) {
        this.idKartuKeluarga = idKartuKeluarga;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public TanggalDetail getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(TanggalDetail tanggalDetail) {
        this.tanggalLahir = tanggalDetail;
    }

    public TanggalDetail getTanggalBergabung() {
        return tanggalBergabung;
    }

    public void setTanggalBergabung(TanggalDetail tanggalBergabung) {
        this.tanggalBergabung = tanggalBergabung;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getJenisPekerjaan() {
        return jenisPekerjaan;
    }

    public void setJenisPekerjaan(String jenisPekerjaan) {
        this.jenisPekerjaan = jenisPekerjaan;
    }

    public String getStatusPernikahan() {
        return statusPernikahan;
    }

    public void setStatusPernikahan(String statusPernikahan) {
        this.statusPernikahan = statusPernikahan;
    }

    public String getStatusHubunganDalamKeluarga() {
        return statusHubunganDalamKeluarga;
    }

    public void setStatusHubunganDalamKeluarga(String statusHubunganDalamKeluarga) {
        this.statusHubunganDalamKeluarga = statusHubunganDalamKeluarga;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public void setKewarganegaraan(String kewarganegaraan) {
        this.kewarganegaraan = kewarganegaraan;
    }

    public String getNamaAyah() {
        return namaAyah;
    }

    public void setNamaAyah(String namaAyah) {
        this.namaAyah = namaAyah;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }

    public static class TanggalDetail {

        @SerializedName("tanggal")
        private String tanggal;

        @SerializedName("bulan")
        private String bulan;

        @SerializedName("tahun")
        private String tahun;

        public String getTanggal() {
            return tanggal;
        }

        public void setTanggal(String tanggal) {
            this.tanggal = tanggal;
        }

        public String getBulan() {
            return bulan;
        }

        public void setBulan(String bulan) {
            this.bulan = bulan;
        }

        public String getTahun() {
            return tahun;
        }

        public void setTahun(String tahun) {
            this.tahun = tahun;
        }
    }

}

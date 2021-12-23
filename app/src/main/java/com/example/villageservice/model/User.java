package com.example.villageservice.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id_ktp")
    private long idKtp;

    @SerializedName("id_kartu_keluarga")
    private long idKartuKeluarga;

    @SerializedName("nama_lengkap")
    private String namaLengkap;

    @SerializedName("jenis_kelamin")
    private String jenisKelamin;

    @SerializedName("tempat_lahir")
    private String tempatLahir;

    @SerializedName("tanggal_lahir")
    private TanggalLahir tanggalLahir;

    @SerializedName("agama")
    private String agama;

    @SerializedName("pendidikan")
    private String pendidikan;

    @SerializedName("jenis_pekerjaan")
    private String jenisPekerjaan;

    @SerializedName("status_pernikahan")
    private String statusPernikahan;

    @SerializedName("status_hubungan_dalam_keluarga")
    private String statusHubunganDalamKeluarga;

    @SerializedName("kewarganegaraan")
    private String kewarganegaraan;

    @SerializedName("nama_ayah")
    private String namaAyah;

    @SerializedName("nama_ibu")
    private String namaIbu;

    public long getIdKtp() {
        return idKtp;
    }

    public void setIdKtp(long idKtp) {
        this.idKtp = idKtp;
    }

    public long getIdKartuKeluarga() {
        return idKartuKeluarga;
    }

    public void setIdKartuKeluarga(long idKartuKeluarga) {
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

    public TanggalLahir getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(TanggalLahir tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
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

    class TanggalLahir {

        @SerializedName("tanggal")
        private int tanggal;

        @SerializedName("bulan")
        private int bulan;

        @SerializedName("tahun")
        private int tahun;

        public int getTanggal() {
            return tanggal;
        }

        public void setTanggal(int tanggal) {
            this.tanggal = tanggal;
        }

        public int getBulan() {
            return bulan;
        }

        public void setBulan(int bulan) {
            this.bulan = bulan;
        }

        public int getTahun() {
            return tahun;
        }

        public void setTahun(int tahun) {
            this.tahun = tahun;
        }
    }

}

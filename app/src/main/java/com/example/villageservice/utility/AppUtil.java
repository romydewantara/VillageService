package com.example.villageservice.utility;

import android.content.Context;

import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AppUtil {

    public String readTextFileFromAssets(Context context, String fileName) {
        InputStream is;
        try {
            is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // byte buffer into a string
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }

    public void importKartuKeluarga(Context context, List<KartuKeluarga> kartuKeluargaList) {
        ArrayList<Object> kartuKeluargaArrayList = new ArrayList<>();
        for (int i = 0; i < kartuKeluargaList.size(); i++) {
            KartuKeluarga kartuKeluarga = new KartuKeluarga();

            List<User> users = kartuKeluargaList.get(i).getKeluargaList();
            for (int j = 0; j < users.size(); j++) {
                User user = new User();
                User.TanggalDetail tanggalDetail = new User.TanggalDetail();
                tanggalDetail.setTanggal(users.get(j).getTanggalLahir().getTanggal());
                tanggalDetail.setBulan(users.get(j).getTanggalLahir().getBulan());
                tanggalDetail.setTahun(users.get(j).getTanggalLahir().getTahun());

                user.setIdKtp(users.get(j).getIdKtp());
                user.setIdKartuKeluarga(users.get(j).getIdKartuKeluarga());
                user.setNamaLengkap(users.get(j).getNamaLengkap());
                user.setJenisKelamin(users.get(j).getJenisKelamin());
                user.setTempatLahir(users.get(j).getTempatLahir());
                user.setTempatLahir(users.get(j).getTempatLahir());
                user.setTanggalLahir(tanggalDetail);
                user.setAgama(users.get(j).getAgama());
                user.setPendidikan(users.get(j).getPendidikan());
                user.setJenisPekerjaan(users.get(j).getJenisPekerjaan());
                user.setStatusPernikahan(users.get(j).getStatusPernikahan());
                user.setStatusHubunganDalamKeluarga(users.get(j).getStatusHubunganDalamKeluarga());
                user.setKewarganegaraan(users.get(j).getKewarganegaraan());
                user.setNamaAyah(users.get(j).getNamaAyah());
                user.setNamaIbu(users.get(j).getNamaIbu());

            }

            kartuKeluarga.setIdKartuKeluarga(kartuKeluargaList.get(i).getIdKartuKeluarga());
            kartuKeluarga.setNamaKepalaKeluarga(kartuKeluargaList.get(i).getNamaKepalaKeluarga());
            kartuKeluarga.setIdKartuKeluarga(kartuKeluargaList.get(i).getIdKartuKeluarga());
            kartuKeluarga.setAlamatLengkap(kartuKeluargaList.get(i).getAlamatLengkap());
            kartuKeluarga.setAlamatRumah(kartuKeluargaList.get(i).getAlamatRumah());
            kartuKeluarga.setNomorRt(kartuKeluargaList.get(i).getNomorRt());
            kartuKeluarga.setNomorRw(kartuKeluargaList.get(i).getNomorRw());
            kartuKeluarga.setKelurahan(kartuKeluargaList.get(i).getKelurahan());
            kartuKeluarga.setKecamatan(kartuKeluargaList.get(i).getKecamatan());
            kartuKeluarga.setKota(kartuKeluargaList.get(i).getKota());
            kartuKeluarga.setKodePos(kartuKeluargaList.get(i).getKodePos());
            kartuKeluarga.setProvinsi(kartuKeluargaList.get(i).getProvinsi());
            kartuKeluarga.setStatus(kartuKeluargaList.get(i).getStatus());
            kartuKeluarga.setPassword(kartuKeluargaList.get(i).getPassword());
            kartuKeluarga.setKeluargaList(users);
            kartuKeluargaArrayList.add(kartuKeluarga);
        }
        VSPreference.getInstance(context).saveKKList(kartuKeluargaArrayList);
    }

    public void updateUserData(Context context, String key) {

    }

    public void deleteUserData() {

    }

}

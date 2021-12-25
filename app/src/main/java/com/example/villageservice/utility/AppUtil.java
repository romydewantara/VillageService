package com.example.villageservice.utility;

import android.content.Context;

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

    public void importUserData(Context context, List<User> userList) {
        ArrayList<Object> objUserList = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            User user = new User();
            user.setNamaLengkap(userList.get(i).getNamaLengkap());
            user.setIdKtp(userList.get(i).getIdKtp());
            user.setIdKartuKeluarga(userList.get(i).getIdKartuKeluarga());
            user.setJenisKelamin(userList.get(i).getJenisKelamin());
            user.setTempatLahir(userList.get(i).getTempatLahir());
            user.setTanggalLahir(userList.get(i).getTanggalLahir());
            user.setAgama(userList.get(i).getAgama());
            user.setPendidikan(userList.get(i).getPendidikan());
            user.setJenisPekerjaan(userList.get(i).getJenisPekerjaan());
            user.setStatusPernikahan(userList.get(i).getStatusPernikahan());
            user.setStatusHubunganDalamKeluarga(userList.get(i).getStatusHubunganDalamKeluarga());
            user.setKewarganegaraan(userList.get(i).getKewarganegaraan());
            user.setNamaAyah(userList.get(i).getNamaAyah());
            user.setNamaIbu(userList.get(i).getNamaIbu());
            objUserList.add(user);
        }
        VSPreference.getInstance(context).saveUserList(objUserList);
    }

    public void updateUserData(Context context, String key) {

    }

    public void deleteUserData() {

    }

}

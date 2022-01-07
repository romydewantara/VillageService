package com.example.villageservice.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.example.villageservice.model.Admin;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VSPreference {

    private Context context;
    private final static String prefName = "VSPrefs";
    private static VSPreference instance = null;
    private static SharedPreferences preferences;

    public static VSPreference getInstance(Context context) {
        if (instance == null) {
            instance = new VSPreference();
            preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

        }
        return instance;
    }

    public boolean isSignIn() {
        return getBoolean(ConstantVariable.KEY_SIGNED_IN);
    }

    public void setSignIn(boolean isSignedIn) {
        putBoolean(ConstantVariable.KEY_SIGNED_IN, isSignedIn);
    }

    public void setRole(String role) {
        putString(ConstantVariable.KEY_ROLE, role);
    }

    public String getRole() {
        return getString(ConstantVariable.KEY_ROLE);
    }

    public void setUser(User user) {
        putObject(ConstantVariable.KEY_USER, user);
    }

    public Object getUser() {
        return getObject(ConstantVariable.KEY_USER, User.class);
    }

    public void setPhoneNum(String key, String phone) {
        putString(key, phone);
    }

    public String getPhoneNum(String key) {
        return getString(key);
    }

    public void setAdmin(Admin admin) {
        putObject(ConstantVariable.KEY_ADMIN, admin);
    }

    public Admin getAdmin() {
        return (Admin) getObject(ConstantVariable.KEY_ADMIN, Admin.class);
    }

    public void setKK(KartuKeluarga kartuKeluarga) {
        putObject(ConstantVariable.KEY_KARTU_KELUARGA, kartuKeluarga);
    }

    public KartuKeluarga getKK() {
        return (KartuKeluarga) getObject(ConstantVariable.KEY_KARTU_KELUARGA, KartuKeluarga.class);
    }

    public void saveKKList(ArrayList<Object> kartuKeluargaList) {
        putListObject(ConstantVariable.KEY_KARTU_KELUARGA_LIST, kartuKeluargaList);
    }

    public ArrayList<Object> getKKList() {
        return getListObject(ConstantVariable.KEY_KARTU_KELUARGA_LIST, KartuKeluarga.class);
    }

    public void deleteUser() {

    }

    public void saveUserList(ArrayList<Object> users) {
        putListObject(ConstantVariable.KEY_USER_LIST, users);
    }

    public ArrayList<Object> loadUserList() {
        return getListObject(ConstantVariable.KEY_USER_LIST, User.class);
    }

    public void setCoveringLetter(String key, CoveringLetter coveringLetter) {
        putObject(key, coveringLetter);
    }

    public CoveringLetter getCoveringLetter(String key) {
        return (CoveringLetter) getObject(key, CoveringLetter.class);
    }

    public void setCoveringLetterList(String key, ArrayList<Object> coveringLetterList) {
        putListObject(key, coveringLetterList);
    }

    public ArrayList<Object> getCoveringLetterList(String key) {
        return getListObject(key, CoveringLetter.class);
    }

    public void logout() {
        setRole("");
        setUser(null);
        setSignIn(false);
        //Delete all sharedpref except checksum values
        Map<String, ?> map = getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            String key = entry.getKey();
            if (!key.contains(ConstantVariable.KEY_USER)){
                //remove(key);
            }
        }
    }

    /** method for storing something more detailed */

    public void putObject(String key, Object obj) {
        keyNullChecker(key);
        Gson gson = new Gson();
        putString(key, gson.toJson(obj));
    }

    public Object getObject(String key, Class<?> classOfT) {

        String json = getString(key);
        Object value = new Gson().fromJson(json, classOfT);

        return value;
    }

    public void putString(String key, String value) {
        keyNullChecker(key);
        valueNullChecker(value);
        preferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return preferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value) {
        keyNullChecker(key);
        preferences.edit().putBoolean(key, value).apply();
    }

    public void putListObject(String key, ArrayList<Object> objArrayList) {
        keyNullChecker(key);
        Gson gson = new Gson();
        ArrayList<String> objString = new ArrayList<>();
        for (Object o : objArrayList) {
            objString.add(gson.toJson(o));
        }
        putListString(key, objString);
    }

    public ArrayList<Object> getListObject(String key, Class<?> mClass) {
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(key);
        ArrayList<Object> objects = new ArrayList<Object>();

        for (String jObjString : objStrings) {
            Object value = gson.fromJson(jObjString, mClass);
            objects.add(value);
        }
        return objects;
    }

    public void putListString(String key, ArrayList<String> stringListParam) {
        String[] stringList = stringListParam.toArray(new String[stringListParam.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", stringList)).apply();
    }

    public ArrayList<String> getListString(String key) {
        return new ArrayList<>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    public void remove(String key) {
        preferences.edit().remove(key).apply();
    }

    private void keyNullChecker(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    public void valueNullChecker(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
    }

}

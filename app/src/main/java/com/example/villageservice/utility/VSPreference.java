package com.example.villageservice.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.villageservice.model.User;
import com.example.villageservice.model.UserList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class VSPreference {

    private Context context;
    private final static String prefName = "VSPrefs";
    private static VSPreference instance = null;
    private static SharedPreferences preferences;

    public final static String KEY_LOGGED_IN = "";
    public final static String KEY_KARTU_KELUARGA = "";
    public final static String KEY_USER = "";
    public final static String KEY_USER_LIST = "";

    public static VSPreference getInstance(Context context) {
        if (instance == null) {
            instance = new VSPreference();
            preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

        }
        return instance;
    }

    public void saveUserList(String key, ArrayList<Object> users) {
        putListObject(key, users);
    }

    public ArrayList<Object> loadUserList() {
        return getListObject(KEY_USER_LIST, User.class);
    }

    /** method for storing something more detailed  */

    public void putListObject(String key, ArrayList<Object> objArrayList) {
        keyNullChecker(key);
        Gson gson = new Gson();
        ArrayList<String> objString = new ArrayList<>();
        for (Object o : objArrayList) {
            objString.add(gson.toJson(o));
        }
        putListString(key, objString);
    }

    public ArrayList<Object> getListObject(String key, Class<?> mClass){
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(key);
        ArrayList<Object> objects =  new ArrayList<Object>();

        for(String jObjString : objStrings){
            Object value  = gson.fromJson(jObjString,  mClass);
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

    private void keyNullChecker(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

}

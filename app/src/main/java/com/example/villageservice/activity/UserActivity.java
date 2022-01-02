package com.example.villageservice.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.villageservice.R;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

public class UserActivity extends AppCompatActivity {

    private KartuKeluarga kartuKeluarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        kartuKeluarga = VSPreference.getInstance(getApplicationContext()).getKK();
        Log.d("XXXLOG", "onCreate - KK logged in: " + new Gson().toJson(kartuKeluarga));
    }
}
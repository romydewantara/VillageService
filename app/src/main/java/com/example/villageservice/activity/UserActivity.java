package com.example.villageservice.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.villageservice.R;
import com.example.villageservice.utility.DatabaseConfig;

public class UserActivity extends AppCompatActivity {

    private Button send;
    private EditText etUsername;
    private EditText etGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        send = findViewById(R.id.btnSend);
        etUsername = findViewById(R.id.etUsername);
        etGender = findViewById(R.id.etGender);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String gender = etGender.getText().toString();
                String type = "adduser";
                DatabaseConfig databaseConfig = new DatabaseConfig(UserActivity.this);
                databaseConfig.execute(type, username, gender);
            }
        });
    }
}
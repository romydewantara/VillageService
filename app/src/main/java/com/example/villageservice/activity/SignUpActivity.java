package com.example.villageservice.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.villageservice.R;
import com.example.villageservice.utility.Fonts;

public class SignUpActivity extends AppCompatActivity {

    private Fonts fonts;
    private AppCompatTextView tvTitle;
    private AppCompatTextView tvSignIn;
    private AppCompatTextView tvQuestion;
    private AppCompatTextView tvBottom;
    private EditText etKtp;
    private EditText etName;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fonts = new Fonts(getApplicationContext());
        tvTitle = findViewById(R.id.tvTitle);
        tvSignIn = findViewById(R.id.tvSignIn);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvBottom = findViewById(R.id.tvBottom);
        etKtp = findViewById(R.id.etKtp);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        backButton = findViewById(R.id.backButton);

        setFonts();
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        finish();
    }

    private void setFonts() {
        tvTitle.setTypeface(fonts.rBoldExtra());
        tvSignIn.setTypeface(fonts.rBold());
        tvQuestion.setTypeface(fonts.rRegular());
        tvBottom.setTypeface(fonts.rMedium());
        etKtp.setTypeface(fonts.rRegular());
        etName.setTypeface(fonts.rRegular());
        etPassword.setTypeface(fonts.rRegular());
        etConfirmPassword.setTypeface(fonts.rRegular());
    }
}

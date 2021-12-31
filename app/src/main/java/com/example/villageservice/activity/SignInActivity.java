package com.example.villageservice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.villageservice.R;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.utility.Fonts;
import com.example.villageservice.utility.VSPreference;

public class SignInActivity extends AppCompatActivity {

    Fonts fonts;

    // 6 TextView
    private AppCompatTextView tvAplName;
    private AppCompatTextView tvInfo;
    private AppCompatTextView tvQuestion;
    private AppCompatTextView tvRegister;
    private AppCompatTextView tvGuide;

    // 2 EditText
    private EditText etEmail;
    private EditText etPassword;

    // 1 Button
    private Button signInButton;

    // 1 Lottie
    private LottieAnimationView lottieLayer;

    private CustomLoadingDialog customLoadingDialog;

    private RelativeLayout overlay;

    // TEMPORARY MAIL
    private String userMail = "user@test.com";
    private String adminMail = "admin@test.com";
    private boolean isMailFilled = false;
    private boolean isPasswordFilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initMandatory();
        initListener();
        setFonts();
    }

    private void initMandatory() {
        fonts = new Fonts(getApplicationContext());
        customLoadingDialog = new CustomLoadingDialog(this);
        tvAplName = findViewById(R.id.tvAplName);
        tvInfo = findViewById(R.id.tvInfo);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        signInButton = findViewById(R.id.signInButton);
        tvGuide = findViewById(R.id.tvGuide);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvRegister = findViewById(R.id.tvRegister);
        lottieLayer = findViewById(R.id.lottieLayer);
        overlay = findViewById(R.id.overlay);

        //signInButton.setEnabled(false);
        signInButton.setBackgroundResource(R.drawable.bg_button_disabled);
    }

    private void initListener() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipLogin();
                //credentialCheck();
            }
        });
        tvGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCAD(
                        "Informasi",
                        "Akun user:\nuser@test.com | password: 123pass\n\nAkun admin:\nadmin@test.com | password: pass123",
                        "Tutup", "");
            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieLayer.cancelAnimation();
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("EMAIL", "afterTextChanged - s: " + s);
                if (s.toString().contains("@test.com"))
                    isMailFilled = true;
                else
                    isMailFilled = false;
                checkButtonState();
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("PASSWORD", "afterTextChanged - s: " + s);
                if (s.toString().length() >= 6)
                    isPasswordFilled = true;
                else
                    isPasswordFilled = false;
                checkButtonState();
            }
        });
    }

    private void setFonts() {
        tvAplName.setTypeface(fonts.rBold());
        tvInfo.setTypeface(fonts.rMedium());
        etEmail.setTypeface(fonts.rMedium());
        etPassword.setTypeface(fonts.rMedium());
        signInButton.setTypeface(fonts.rBold());
        tvGuide.setTypeface(fonts.rMedium());
        tvQuestion.setTypeface(fonts.rRegular());
        tvRegister.setTypeface(fonts.rMedium());

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvAplName, 1, 20, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvInfo, 1, 18, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvGuide, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvQuestion, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvRegister, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        etEmail.setTextSize(16f);
        etPassword.setTextSize(16f);
    }

    private void showCAD(String title, String message, String pButton, String nButton) {
        FragmentManager fm = getSupportFragmentManager();
        CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance(this, title, message)
                .setButton(pButton, nButton, new CustomAlertDialogListener() {
                    @Override
                    public void onNegativePressed() {}
                    @Override
                    public void onPositivePressed() {}
                });
        if (fm != null) {
            customAlertDialog.show(fm, "custom_alert_dialog");
        }
    }

    private void checkButtonState() {
        if (isMailFilled && isPasswordFilled) {
            signInButton.setEnabled(true);
            signInButton.setBackgroundResource(R.drawable.bg_button_enabled);
        } else {
            signInButton.setEnabled(false);
            signInButton.setBackgroundResource(R.drawable.bg_button_disabled);
        }
    }

    private void credentialCheck() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (!email.isEmpty()) {
            if (email.equalsIgnoreCase(userMail) && password.equalsIgnoreCase("123pass")) {
                login(UserActivity.class);
            } else if (email.equalsIgnoreCase(adminMail) && password.equalsIgnoreCase("pass123")) {
                login(AdminActivity.class);
            } else  {
                showCAD(
                        "Gagal masuk",
                        "Oops… Email atau password yang anda masukkan salah\nSilakan membaca Panduan terlebih dahulu",
                        "Setuju", "");
            }
        }
    }

    private void login(final Class destination) {
        overlay.setVisibility(View.VISIBLE);
        customLoadingDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                VSPreference.getInstance(getApplicationContext()).setSignIn(true);
                customLoadingDialog.dismiss();
                overlay.setVisibility(View.INVISIBLE);
                lottieLayer.cancelAnimation();
                startActivity(new Intent(getApplicationContext(), destination).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                finish();
            }
        }, 3000);
    }

    private void skipLogin() {
        login(AdminActivity.class);
    }
}

package com.example.villageservice.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.villageservice.R;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.utility.Fonts;

public class SignInActivity extends AppCompatActivity {

    Fonts fonts;

    // 6 TextView
    private TextView tvAplName;
    private TextView tvInfo;
    private TextView tvQuestion;
    private TextView tvRegister;
    private TextView tvGuide;

    // 2 EditText
    private EditText etEmail;
    private EditText etPassword;

    // 1 Button
    private Button signInButton;

    // 1 Lottie
    private LottieAnimationView lottieLayer;

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
        tvAplName = findViewById(R.id.tvAplName);
        tvInfo = findViewById(R.id.tvInfo);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        signInButton = findViewById(R.id.signInButton);
        tvGuide = findViewById(R.id.tvGuide);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvRegister = findViewById(R.id.tvRegister);
        lottieLayer = findViewById(R.id.lottieLayer);

        signInButton.setEnabled(false);
        signInButton.setBackgroundResource(R.drawable.bg_button_disabled);
    }

    private void initListener() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                Log.d("SignIn", "onClick - email: " + email);
                if (!email.isEmpty()) {
                    if ((email.equalsIgnoreCase(userMail) && password.equalsIgnoreCase("123pass"))
                            || (email.equalsIgnoreCase(adminMail) && password.equalsIgnoreCase("pass123"))) {
                        lottieLayer.cancelAnimation();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                        finish();
                    } else {
                        showCAD(
                                "Gagal masuk",
                                "Oops… Email atau password yang anda masukkan salah\nSilakan membaca Panduan terlebih dahulu",
                                "Setuju", "");
                    }
                }
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
        tvGuide.setTypeface(fonts.rRegular());
        tvQuestion.setTypeface(fonts.rMedium());
        tvRegister.setTypeface(fonts.rRegular());
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
}

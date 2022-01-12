package com.example.villageservice.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.villageservice.R;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.model.Admin;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.model.KartuKeluargaList;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.AppUtil;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.Fonts;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SignInActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private AppUtil appUtil;
    private KartuKeluargaList kartuKeluargaList;
    private ArrayList<KartuKeluarga> kartuKeluargaArrayList;
    private ArrayList<Object> objListUser;

    // 6 TextView
    private AppCompatTextView tvAplName;
    private AppCompatTextView tvInfo;
    private AppCompatTextView tvQuestion;
    private AppCompatTextView tvRegister;
    private AppCompatTextView tvGuide;

    // 2 EditText
    private EditText etEmail;
    private AppCompatEditText etPassword;

    // 1 Button
    private Button signInButton;

    // 1 Lottie
    private LottieAnimationView lottieLayer;

    private CustomLoadingDialog customLoadingDialog;

    private RelativeLayout overlay;

    private Admin admin;
    // TEMPORARY MAIL
    private boolean isMailFilled = false;
    private boolean isPasswordFilled = false;

    public ArrayList<String> permissionsToRequest;
    public ArrayList<String> permissionsRejected;
    private String[] perms = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public final static int PERMISSION_REQ_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initMandatory();
        initListener();
        setFonts();
        fetchUsers();
    }

    private void initMandatory() {
        customLoadingDialog = new CustomLoadingDialog(this);
        appUtil = new AppUtil();
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

        signInButton.setBackgroundResource(R.drawable.bg_button_disabled);
        admin = new Admin();
        if (admin.getUsername().equalsIgnoreCase("")) {
            admin.setUsername(ConstantVariable.ADMIN_USERNAME);
            admin.setPassword(ConstantVariable.ADMIN_PASSWORD);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //skipLogin();
                requestPermissions();
            }
        });
        tvGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomAlertDialog(
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
                if (s.toString().length() >= 6 || s.toString().equalsIgnoreCase(ConstantVariable.ADMIN_USERNAME))
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
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[2].getBounds().width())) {
                        if(etPassword.getTransformationMethod() instanceof PasswordTransformationMethod){
                            etPassword.setTransformationMethod(null);
                            Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_visibility_off);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(null,null, img,null);
                        } else {
                            etPassword.setTransformationMethod(new PasswordTransformationMethod());
                            Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_visibility_on);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(null,null, img,null);}
                    }
                }
                return false;
            }
        });
    }

    private void setFonts() {
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvAplName, 1, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvInfo, 1, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvGuide, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvQuestion, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvRegister, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
    }

    private void showCustomAlertDialog(String title, String message, String pButton, String nButton) {
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
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        if (!email.isEmpty()) {
            if (email.equalsIgnoreCase(admin.getUsername())) {
                if (password.equalsIgnoreCase(admin.getPassword())) {
                    VSPreference.getInstance(getApplicationContext()).setRole(ConstantVariable.ADMIN);
                    login(AdminActivity.class, 3000);
                } else {
                    showCustomAlertDialog("Gagal masuk", "Maaf… Password yang anda masukkan salah", "Coba Lagi", "");
                }
            } else {
                final ArrayList<Object> kartuKeluargaArrayList = VSPreference.getInstance(getApplicationContext()).getKKList();
                Log.d("XXXLOG", "SignIn (credentialCheck) - KK List: " + new Gson().toJson(kartuKeluargaArrayList));
                if (!kartuKeluargaArrayList.isEmpty()) {
                    overlay.setVisibility(View.VISIBLE);
                    customLoadingDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            KartuKeluarga kkObj = new KartuKeluarga();
                            boolean isEmailFound = false;
                            boolean isPasswordMatch = false;
                            for (int i = 0; i < kartuKeluargaArrayList.size(); i++) {
                                kkObj = (KartuKeluarga) kartuKeluargaArrayList.get(i);
                                if (email.equalsIgnoreCase(kkObj.getIdKartuKeluarga())) {
                                    isEmailFound = true;
                                    if (password.equalsIgnoreCase(kkObj.getPassword())) {
                                        isPasswordMatch = true;
                                        break;
                                    }
                                    break;
                                }
                            }
                            if (isEmailFound) {
                                if (isPasswordMatch) {
                                    VSPreference.getInstance(getApplicationContext()).setKK(kkObj);
                                    VSPreference.getInstance(getApplicationContext()).setRole(ConstantVariable.USER);
                                    login(UserActivity.class, 0);
                                } else {
                                    customLoadingDialog.dismiss();
                                    overlay.setVisibility(View.INVISIBLE);
                                    showCustomAlertDialog("Gagal masuk", "Maaf… Password yang anda masukkan salah", "Coba Lagi", "");
                                }
                            } else {
                                customLoadingDialog.dismiss();
                                overlay.setVisibility(View.INVISIBLE);
                                showCustomAlertDialog("Gagal masuk", "Maaf… Nomor Kartu Keluarga yang anda masukkan salah. Silakan periksa kembali atau hubungi pihak RT", "Coba Lagi", "");
                            }
                        }
                    }, 3000);
                }
            }
        }
    }

    private void login(final Class destination, long delayMillis) {
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
        }, delayMillis);
    }

    private void requestPermissions() {
        if (EasyPermissions.hasPermissions(getApplicationContext(), perms)) {
            credentialCheck();
        } else {
            EasyPermissions.requestPermissions(
                    this, "Aplikasi ini membutuhkan akses ke penyimpanan Anda agar Anda dapat mengakses surat.",
                    PERMISSION_REQ_CODE, perms);
        }
    }

    private void skipLogin() {
        login(AdminActivity.class, 3000);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(EasyPermissions.hasPermissions(getApplicationContext(), this.perms)){
            credentialCheck();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setRationale(getString(R.string.rationale_ask_again))
                    .setPositiveButton("Settings")
                    .setNegativeButton("Cancel")
                    .setRequestCode(PERMISSION_REQ_CODE)
                    .build()
                    .show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void fetchUsers() {
        String jsonFile = appUtil.readTextFileFromAssets(getApplicationContext(), "json/citizen_data.json");
        Type listData = new TypeToken<KartuKeluargaList>() {
        }.getType();
        kartuKeluargaList = new Gson().fromJson(jsonFile, listData);
        kartuKeluargaArrayList = new ArrayList<>();
        if (!kartuKeluargaList.getKartuKeluargaList().isEmpty()) {
            kartuKeluargaArrayList.addAll(kartuKeluargaList.getKartuKeluargaList());
        }
        objListUser = VSPreference.getInstance(getApplicationContext()).getKKList();
        if (objListUser.isEmpty()) {
            appUtil.importKartuKeluarga(getApplicationContext(), kartuKeluargaArrayList);
        } else {
            //TODO: show list of kartuKeluargaArrayList to recyclerView and check if exists then hide import button
        }
    }
}

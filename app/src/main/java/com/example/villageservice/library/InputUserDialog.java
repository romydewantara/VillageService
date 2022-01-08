package com.example.villageservice.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.villageservice.R;
import com.example.villageservice.adapter.CustomSpinnerAdapter;
import com.example.villageservice.listener.InputUserDialogListener;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class InputUserDialog extends DialogFragment {

    private TextView titleAlert;
    private TextView positiveButton;
    private TextView negativeButton;

    private EditText et1, et2, et3, et4;
    private AppCompatTextView tvError1;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;
    private Spinner spinnerAgama;
    private Spinner spinnerGender;
    private Spinner spinnerPd;
    private Spinner spinnerKerja;
    private Spinner spinnerMaritalStatus;
    private Spinner spinnerKewarganegaraan;

    private RelativeLayout relativeGender;
    private RelativeLayout relativeBulan;
    private RelativeLayout relativeTahun;
    private RelativeLayout relativeAgama;
    private RelativeLayout relativePd;
    private RelativeLayout relativeKerja;
    private RelativeLayout relativeStatusMarital;
    private RelativeLayout relativeKewarganegaraan;

    private String gender;
    private String bulan;
    private String tahun;
    private String agama;
    private String pendidikan;
    private String pekerjaan;
    private String status;
    private String kewarganegaraan;

    private CharSequence pButtonText = "";
    private CharSequence nButtonText = "";
    private boolean shown = false;
    private boolean isKTPValid = false;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String textTitle;
    private static String textKk;
    private static User currentUser;
    private static ArrayList<Object> userList;

    private InputUserDialogListener inputUserDialogListener;

    public InputUserDialog() {

    }

    public static InputUserDialog newInstance(Context context, String title, String kk, User user) {
        InputUserDialog fragment = new InputUserDialog();
        Bundle arguments = new Bundle();
        mContext = context;
        textTitle = title;
        textKk = kk;
        currentUser = user;
        userList = VSPreference.getInstance(context).loadUserList();
        Log.d("XXXLOG", "newInstance - userList: " + new Gson().toJson(userList));

        arguments.putString("title", title);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_input_user_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_cad_rounded));
        }
        initMandatory(view);
        populateData();
        initListener();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mContext.getApplicationContext() instanceof InputUserDialogListener) {
            inputUserDialogListener = (InputUserDialogListener) mContext;
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        if (shown) return;
        try {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(this, tag);
            fragmentTransaction.commitAllowingStateLoss();
            shown = true;
        } catch (IllegalStateException e) {
            Log.d("InputUserDialog", "Exception: ", e);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        shown = false;
        super.onDismiss(dialog);
    }

    private void initMandatory(View view) {
        titleAlert = view.findViewById(R.id.titleAlert);
        titleAlert.setText(textTitle);
        tvError1 = view.findViewById(R.id.tvError1);

        /** EditText */
        et1 = view.findViewById(R.id.et1);
        et2 = view.findViewById(R.id.et2);
        et3 = view.findViewById(R.id.et3);
        et4 = view.findViewById(R.id.et4);

        /** Spinners */
        spinnerMonth = view.findViewById(R.id.spinnerMonth);
        spinnerYear = view.findViewById(R.id.spinnerYear);
        spinnerGender = view.findViewById(R.id.spinnerGender);
        spinnerAgama = view.findViewById(R.id.spinnerAgama);
        spinnerPd = view.findViewById(R.id.spinnerPd);
        spinnerKerja = view.findViewById(R.id.spinnerKerja);
        spinnerMaritalStatus = view.findViewById(R.id.spinnerStatusMarital);
        spinnerKewarganegaraan = view.findViewById(R.id.spinnerKewarganegaraan);

        /** RelativeLayouts */
        relativeGender = view.findViewById(R.id.relativeGender);
        relativeBulan = view.findViewById(R.id.relativeBulan);
        relativeTahun = view.findViewById(R.id.relativeTahun);
        relativeAgama = view.findViewById(R.id.relativeAgama);
        relativePd = view.findViewById(R.id.relativePd);
        relativeKerja = view.findViewById(R.id.relativeKerja);
        relativeStatusMarital = view.findViewById(R.id.relativeStatusMarital);
        relativeKewarganegaraan = view.findViewById(R.id.relativeKewarganegaraan);

        /** Buttons */
        negativeButton = view.findViewById(R.id.negativeButton);
        positiveButton = view.findViewById(R.id.positiveButton);

        /** Strings */
        gender = mContext.getResources().getString(R.string.gender);
        bulan = mContext.getResources().getString(R.string.bulan);
        tahun = mContext.getResources().getString(R.string.tahun);
        agama = mContext.getResources().getString(R.string.agama);
        pendidikan = mContext.getResources().getString(R.string.pd);
        pekerjaan = mContext.getResources().getString(R.string.kerja);
        status = mContext.getResources().getString(R.string.status);
        kewarganegaraan = mContext.getResources().getString(R.string.kewarganegaraan);
    }

    private void initListener() {
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputUserDialogListener.onCancelButtonPressed();
                dismiss();
            }
        });
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isExists = false;
                if (!userList.isEmpty()) {
                    for (int i = 0; i < userList.size(); i++) {
                        User user = (User) userList.get(i);
                        if (user.getIdKtp().equalsIgnoreCase(s.toString())) {
                            isExists = true;
                            break;
                        } else {
                            isExists = false;
                        }
                    }
                } else {
                    isKTPValid = true;
                }

                if (s.toString().equalsIgnoreCase(textKk) || isExists) {
                    et1.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
                    tvError1.setVisibility(View.VISIBLE);
                    isKTPValid = false;
                } else {
                    et1.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                    tvError1.setVisibility(View.GONE);
                    isKTPValid = true;
                }
            }
        });
        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equalsIgnoreCase("")) {
                    if (s.toString().length() > 2 || Integer.parseInt(String.valueOf(s)) > 31) {
                        et4.setText("31");
                        et4.setSelection(et4.getText().length());
                    }
                }
            }
        });
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerGender.getSelectedItem().toString().equalsIgnoreCase(gender)) {
                    relativeGender.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerMonth.getSelectedItem().toString().equalsIgnoreCase(bulan)) {
                    relativeBulan.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerYear.getSelectedItem().toString().equalsIgnoreCase(tahun)) {
                    relativeTahun.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerAgama.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerAgama.getSelectedItem().toString().equalsIgnoreCase(agama)) {
                    relativeAgama.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerPd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerPd.getSelectedItem().toString().equalsIgnoreCase(pendidikan)) {
                    relativePd.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerKerja.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerKerja.getSelectedItem().toString().equalsIgnoreCase(pekerjaan)) {
                    relativeKerja.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerMaritalStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerMaritalStatus.getSelectedItem().toString().equalsIgnoreCase(status)) {
                    relativeStatusMarital.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerKewarganegaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnerKewarganegaraan.getSelectedItem().toString().equalsIgnoreCase(kewarganegaraan)) {
                    relativeKewarganegaraan.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveData() {
        User user = new User();

        User.TanggalDetail tanggalDetail = new User.TanggalDetail();
        tanggalDetail.setTanggal(et4.getText().toString());
        tanggalDetail.setBulan(String.valueOf(spinnerMonth.getSelectedItem()));
        tanggalDetail.setTahun(String.valueOf(spinnerYear.getSelectedItem()));

        user.setIdKtp(et1.getText().toString());
        user.setNamaLengkap(et2.getText().toString());
        user.setJenisKelamin(String.valueOf(spinnerGender.getSelectedItem()));
        user.setTempatLahir(et3.getText().toString());
        user.setTanggalLahir(tanggalDetail);
        user.setAgama(String.valueOf(spinnerAgama.getSelectedItem()));
        user.setPendidikan(String.valueOf(spinnerPd.getSelectedItem()));
        user.setJenisPekerjaan(String.valueOf(spinnerKerja.getSelectedItem()));
        user.setStatusPernikahan(String.valueOf(spinnerMaritalStatus.getSelectedItem()));
        user.setKewarganegaraan(String.valueOf(spinnerKewarganegaraan.getSelectedItem()));

        if (isCompleted()) {
            inputUserDialogListener.onAddButtonPressed(user);
            dismiss();
        }
    }

    private boolean isCompleted() {
        boolean isCompleted = false;
        if (spinnerGender.getSelectedItem().toString().equalsIgnoreCase(gender)) {
            relativeGender.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
        } else if (spinnerMonth.getSelectedItem().toString().equalsIgnoreCase(bulan)) {
            relativeBulan.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
        } else if (spinnerYear.getSelectedItem().toString().equalsIgnoreCase(tahun)) {
            relativeTahun.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
        } else if (spinnerAgama.getSelectedItem().toString().equalsIgnoreCase(agama)) {
            relativeAgama.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
        } else if (spinnerPd.getSelectedItem().toString().equalsIgnoreCase(pendidikan)) {
            relativePd.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
        } else if (spinnerKerja.getSelectedItem().toString().equalsIgnoreCase(pekerjaan)) {
            relativeKerja.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
        } else if (spinnerMaritalStatus.getSelectedItem().toString().equalsIgnoreCase(status)) {
            relativeStatusMarital.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
        } else if (spinnerKewarganegaraan.getSelectedItem().toString().equalsIgnoreCase(kewarganegaraan)) {
            relativeKewarganegaraan.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
        } else if (!et1.getText().toString().isEmpty() && !et2.getText().toString().isEmpty() &&
                !et3.getText().toString().isEmpty() && !et4.getText().toString().isEmpty() &&
                isKTPValid) {
            isCompleted = true;
        }
        return isCompleted;
    }

    private void populateData() {
        ArrayList<String> genderList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_GENDER));
        ArrayList<String> monthList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_MONTH));
        ArrayList<String> yearList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_YEAR));
        ArrayList<String> agamaList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_AGAMA));
        ArrayList<String> pendidikanList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_PENDIDIKAN));
        ArrayList<String> pekerjaanList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_PEKERJAAN));
        ArrayList<String> maritalStatusList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_MARITAL_STATUS));
        ArrayList<String> kewarganegaraanList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_CITIZEN_STATE));

        CustomSpinnerAdapter genderAdapter = new CustomSpinnerAdapter(mContext, R.layout.spinner_items, genderList);
        CustomSpinnerAdapter monthAdapter = new CustomSpinnerAdapter(mContext, R.layout.spinner_items, monthList);
        CustomSpinnerAdapter yearAdapter = new CustomSpinnerAdapter(mContext, R.layout.spinner_items, yearList);
        CustomSpinnerAdapter agamaAdapter = new CustomSpinnerAdapter(mContext, R.layout.spinner_items, agamaList);
        CustomSpinnerAdapter pendidikanAdapter = new CustomSpinnerAdapter(mContext, R.layout.spinner_items, pendidikanList);
        CustomSpinnerAdapter pekerjaanAdapter = new CustomSpinnerAdapter(mContext, R.layout.spinner_items, pekerjaanList);
        CustomSpinnerAdapter maritalStatusAdapter = new CustomSpinnerAdapter(mContext, R.layout.spinner_items, maritalStatusList);
        CustomSpinnerAdapter kewarganegaraanAdapter = new CustomSpinnerAdapter(mContext, R.layout.spinner_items, kewarganegaraanList);

        spinnerGender.setAdapter(genderAdapter);
        spinnerMonth.setAdapter(monthAdapter);
        spinnerYear.setAdapter(yearAdapter);
        spinnerAgama.setAdapter(agamaAdapter);
        spinnerPd.setAdapter(pendidikanAdapter);
        spinnerKerja.setAdapter(pekerjaanAdapter);
        spinnerMaritalStatus.setAdapter(maritalStatusAdapter);
        spinnerKewarganegaraan.setAdapter(kewarganegaraanAdapter);

        if (!currentUser.getIdKtp().equalsIgnoreCase(ConstantVariable.empty_ktp)) {
            Log.d("XXXPOP", "populateData not null - user: " + new Gson().toJson(currentUser));
            et1.setText(String.valueOf(currentUser.getIdKtp()));
            et2.setText(String.valueOf(currentUser.getNamaLengkap()));
            et3.setText(String.valueOf(currentUser.getTempatLahir()));
            et4.setText(String.valueOf(currentUser.getTanggalLahir().getTanggal()));
            spinnerGender.setSelection(genderAdapter.getPosition(currentUser.getJenisKelamin()));
            spinnerMonth.setSelection(monthAdapter.getPosition(currentUser.getTanggalLahir().getBulan()));
            spinnerYear.setSelection(yearAdapter.getPosition(currentUser.getTanggalLahir().getTahun()));
            spinnerAgama.setSelection(agamaAdapter.getPosition(currentUser.getAgama()));
            spinnerPd.setSelection(pendidikanAdapter.getPosition(currentUser.getPendidikan()));
            spinnerKerja.setSelection(pekerjaanAdapter.getPosition(currentUser.getJenisPekerjaan()));
            spinnerMaritalStatus.setSelection(maritalStatusAdapter.getPosition(currentUser.getStatusPernikahan()));
            spinnerKewarganegaraan.setSelection(kewarganegaraanAdapter.getPosition(currentUser.getKewarganegaraan()));
        } else {
            Log.d("XXXPOP", "populateData null");
        }
    }

    public InputUserDialog setButton(CharSequence positiveText, CharSequence negativeText, InputUserDialogListener listener) {
        pButtonText = positiveText;
        nButtonText = negativeText;
        this.inputUserDialogListener = listener;
        return this;
    }
}

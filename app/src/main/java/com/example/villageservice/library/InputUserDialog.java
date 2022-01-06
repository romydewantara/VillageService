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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.villageservice.R;
import com.example.villageservice.listener.InputUserDialogListener;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

public class InputUserDialog extends DialogFragment {

    private TextView titleAlert;
    private TextView positiveButton;
    private TextView negativeButton;

    private EditText et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14;
    private AppCompatTextView tvError1;
    private Spinner spinnerMonth;
    private Spinner spinnerGender;
    private Spinner spinnerPd;
    private Spinner spinnerKerja;
    private Spinner spinnerMaritalStatus;
    private Spinner spinnerKewarganegaraan;

    private CharSequence pButtonText = "";
    private CharSequence nButtonText = "";
    private boolean shown = false;
    private boolean isComplete = false;

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
        titleAlert = view.findViewById(R.id.titleAlert);
        negativeButton = view.findViewById(R.id.negativeButton);
        positiveButton = view.findViewById(R.id.positiveButton);
        et1 = view.findViewById(R.id.et1);
        et2 = view.findViewById(R.id.et2);
        et3 = view.findViewById(R.id.et3);
        et4 = view.findViewById(R.id.et4);
        et5 = view.findViewById(R.id.et5);
        et6 = view.findViewById(R.id.et6);
        tvError1 = view.findViewById(R.id.tvError1);
        spinnerMonth = view.findViewById(R.id.spinnerMonth);
        spinnerGender = view.findViewById(R.id.spinnerGender);
        spinnerPd = view.findViewById(R.id.spinnerPd);
        spinnerKerja = view.findViewById(R.id.spinnerKerja);
        spinnerMaritalStatus = view.findViewById(R.id.spinnerStatusMarital);
        spinnerKewarganegaraan = view.findViewById(R.id.spinnerKewarganegaraan);

        titleAlert.setText(textTitle);
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

    private void initListener() {
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();

                User.TanggalDetail tanggalDetail = new User.TanggalDetail();
                tanggalDetail.setTanggal(et4.getText().toString());
                tanggalDetail.setBulan(String.valueOf(spinnerMonth.getSelectedItem()));
                tanggalDetail.setTahun(et5.getText().toString());

                user.setIdKtp(et1.getText().toString());
                user.setNamaLengkap(et2.getText().toString());
                user.setJenisKelamin(String.valueOf(spinnerGender.getSelectedItem()));
                user.setTempatLahir(et3.getText().toString());
                user.setTanggalLahir(tanggalDetail);
                user.setAgama(et6.getText().toString());
                user.setPendidikan(String.valueOf(spinnerPd.getSelectedItem()));
                user.setJenisPekerjaan(String.valueOf(spinnerKerja.getSelectedItem()));
                user.setStatusPernikahan(String.valueOf(spinnerMaritalStatus.getSelectedItem()));
                user.setKewarganegaraan(String.valueOf(spinnerKewarganegaraan.getSelectedItem()));

                if (!et1.getText().toString().isEmpty() && !et2.getText().toString().isEmpty() &&
                        !et3.getText().toString().isEmpty() && !et4.getText().toString().isEmpty() &&
                        !et5.getText().toString().isEmpty() && !et6.getText().toString().isEmpty() &&
                        isComplete) {
                    inputUserDialogListener.onAddButtonPressed(user);
                    dismiss();
                }
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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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
                    isComplete = true;
                }

                if (s.toString().equalsIgnoreCase(textKk) || isExists) {
                    et1.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
                    tvError1.setVisibility(View.VISIBLE);
                    isComplete = false;
                } else {
                    et1.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                    tvError1.setVisibility(View.GONE);
                    isComplete = true;
                }
            }
        });
    }

    private void populateData() {
        if (!currentUser.getIdKtp().equalsIgnoreCase(ConstantVariable.empty_ktp)) {
            Log.d("XXXPOP", "populateData not null - user: " + new Gson().toJson(currentUser));
            et1.setText(String.valueOf(currentUser.getIdKtp()));
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

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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.villageservice.R;
import com.example.villageservice.listener.InputUserDialogListener;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

public class InputUserDialog extends DialogFragment {

    private TextView titleAlert;
    private TextView positiveButton;
    private TextView negativeButton;

    private EditText et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14;
    private Spinner monthChooser;

    private CharSequence pButtonText = "";
    private CharSequence nButtonText = "";
    private boolean shown = false;
    private boolean isComplete = false;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String textTitle;
    private static String textKtp;
    private static ArrayList<Object> userList;

    private InputUserDialogListener inputUserDialogListener;

    public InputUserDialog() {

    }

    public static InputUserDialog newInstance(Context context, String title, String ktp) {
        InputUserDialog fragment = new InputUserDialog();
        Bundle arguments = new Bundle();
        mContext = context;
        textTitle = title;
        textKtp = ktp;
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
        et7 = view.findViewById(R.id.et7);
        et8 = view.findViewById(R.id.et8);
        et9 = view.findViewById(R.id.et9);
        et10 = view.findViewById(R.id.et10);
        et11 = view.findViewById(R.id.et11);
        et12 = view.findViewById(R.id.et12);
        et13 = view.findViewById(R.id.et13);
        et14 = view.findViewById(R.id.et14);
        monthChooser = view.findViewById(R.id.monthChooser);

        titleAlert.setText(textTitle);

        initListener();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("Xnull", "onAttach - context: " + mContext);
        if (mContext.getApplicationContext() instanceof InputUserDialogListener) {
            inputUserDialogListener = (InputUserDialogListener) mContext;
        }
        Log.d("Xnull", "onAttach - inputUserDialogListener: " + inputUserDialogListener);
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
        populateData();
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
                tanggalDetail.setTanggal(Integer.parseInt(et5.getText().toString()));
                tanggalDetail.setBulan(String.valueOf(monthChooser.getSelectedItem()));
                tanggalDetail.setTahun(Integer.parseInt(et6.getText().toString()));

                user.setIdKtp(et1.getText().toString());
                user.setNamaLengkap(et2.getText().toString());
                user.setJenisKelamin(et3.getText().toString());
                user.setTempatLahir(et4.getText().toString());
                user.setTanggalLahir(tanggalDetail);
                user.setAgama(et7.getText().toString());
                user.setPendidikan(et8.getText().toString());
                user.setJenisPekerjaan(et9.getText().toString());
                user.setStatusPernikahan(et10.getText().toString());
                user.setStatusHubunganDalamKeluarga(et11.getText().toString());
                user.setKewarganegaraan(et12.getText().toString());
                user.setNamaAyah(et13.getText().toString());
                user.setNamaIbu(et14.getText().toString());

                if (!et1.getText().toString().isEmpty() && !et2.getText().toString().isEmpty() &&
                        !et3.getText().toString().isEmpty() && !et4.getText().toString().isEmpty() &&
                        !et5.getText().toString().isEmpty() && !et6.getText().toString().isEmpty() &&
                        !et7.getText().toString().isEmpty() && !et8.getText().toString().isEmpty() &&
                        !et9.getText().toString().isEmpty() && !et10.getText().toString().isEmpty() &&
                        !et12.getText().toString().isEmpty() && isComplete) {
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
                for (int i = 0; i < userList.size(); i++) {
                    User user = (User) userList.get(i);
                    if (user.getIdKtp().equalsIgnoreCase(s.toString())) {
                        et1.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
                        isComplete = false;
                    } else {
                        et1.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                        isComplete = true;
                    }
                }
            }
        });
    }

    private void populateData() {
        User user = VSPreference.getInstance(mContext).getDataUser(textKtp);
        Log.d("XXXPOP", "populateData - ktp: " + textKtp);
        if (user != null) {
            Log.d("XXXPOP", "populateData not null - user: " + new Gson().toJson(user));
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

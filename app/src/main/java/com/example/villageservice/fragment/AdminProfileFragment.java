package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.villageservice.R;
import com.example.villageservice.activity.AdminActivity;
import com.example.villageservice.activity.UserActivity;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.Admin;
import com.example.villageservice.utility.VSPreference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminProfileFragment extends Fragment {

    private Context context;
    private CustomLoadingDialog customLoadingDialog;
    private FragmentListener fragmentListener;
    private View view;
    private RelativeLayout overlay;

    private EditText etName;
    private EditText etPhone;
    private AppCompatTextView tvNameError;
    private AppCompatTextView tvPhoneError;
    private Button saveButton;
    private ImageView backButton;

    private String previousFragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public void addPreviousFragmentTag(String previousFragment) {
        Log.d("PREVIOUS", "previousFragmentTag: " + previousFragment);
        this.previousFragment = previousFragment;
    }

    public AdminProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminProfileFragment newInstance(String param1, String param2) {
        AdminProfileFragment fragment = new AdminProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        fragmentListener.onFragmentCreated(AdminProfileFragment.this, previousFragment);
        customLoadingDialog = new CustomLoadingDialog(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_profile, container, false);
        backButton = view.findViewById(R.id.backButton);
        etName = view.findViewById(R.id.etName);
        etPhone = view.findViewById(R.id.etPhone);
        tvNameError = view.findViewById(R.id.tvNameError);
        tvPhoneError = view.findViewById(R.id.tvPhoneError);
        saveButton = view.findViewById(R.id.saveButton);
        overlay = view.findViewById(R.id.overlay);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareLayout();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString().trim();
                if (name.equalsIgnoreCase("")) {
                    etName.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
                    tvNameError.setVisibility(View.VISIBLE);
                } else if (phone.equalsIgnoreCase("")) {
                    etPhone.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
                    tvPhoneError.setVisibility(View.VISIBLE);
                } else if (phone.contains("+62")) {
                    etPhone.setBackgroundResource(R.drawable.bg_edit_text_red_rounded);
                    tvPhoneError.setText("Tidak boleh mengandung simbol…*");
                    tvPhoneError.setVisibility(View.VISIBLE);
                } else {
                    Admin admin = new Admin();
                    admin.setName(name);
                    admin.setPhoneNumber("+62" + phone);
                    VSPreference.getInstance(context).setAdmin(admin);
                    FragmentManager fm = getFragmentManager();
                    CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance(context, "", "Data berhasil disimpan!")
                            .setButton("Tutup", "", new CustomAlertDialogListener() {
                                @Override
                                public void onNegativePressed() {
                                }

                                @Override
                                public void onPositivePressed() {
                                    fragmentListener.onFragmentFinish(AdminProfileFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_CITIZENS, false);
                                }
                            });
                    if (fm != null) {
                        customAlertDialog.show(fm, "custom_alert_dialog");
                    }
                }
            }
        });
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    tvNameError.setVisibility(View.GONE);
                    etName.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }
        });
        etPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    char t = s.charAt(0);

                    if (s.length() > 1 && Integer.parseInt(String.valueOf(t)) == 0) {
                        etPhone.setText(String.valueOf(s).substring(1));
                        etPhone.setSelection(etPhone.getText().length());
                    }
                    tvPhoneError.setText("Nomor handphone tidak boleh kosong…*");
                    tvPhoneError.setVisibility(View.GONE);
                    etPhone.setBackgroundResource(R.drawable.bg_edit_text_white_rounded);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onActivityBackPressed();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof FragmentListener) {
            fragmentListener = (FragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void prepareLayout() {
        showOverlay(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showOverlay(false);
            }
        }, 1800);
    }


    public void showOverlay(boolean isShow) {
        if (isShow) {
            overlay.setVisibility(View.VISIBLE);
            customLoadingDialog.show();
        } else {
            customLoadingDialog.dismiss();
            overlay.setVisibility(View.INVISIBLE);
        }
    }
}
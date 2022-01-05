package com.example.villageservice.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villageservice.R;
import com.example.villageservice.activity.AdminActivity;
import com.example.villageservice.adapter.MembersAdapter;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.library.CustomMembershipDialog;
import com.example.villageservice.library.InputUserDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.listener.CustomMembershipDialogListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.listener.InputUserDialogListener;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InputNewUsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InputNewUsersFragment extends Fragment implements MembersAdapter.ItemClickListener {

    private Context context;
    private FragmentListener fragmentListener;
    private CustomLoadingDialog customLoadingDialog;

    private List<User> temporaryUserAdded;

    private RelativeLayout overlay;
    private RecyclerView recyclerViewMember;
    private View view;

    private EditText etIdKK;
    private EditText etKepKK;
    private EditText etAddress;
    private EditText etRT;
    private EditText etRW;
    private EditText etKel;
    private EditText etKec;
    private EditText etKota;
    private EditText etPostal;
    private EditText etProvinsi;
    private AppCompatEditText etPassword;
    private AppCompatTextView tvEmpty;

    private CardView cvAddMember;
    private ImageView backButton;
    private Button saveButton;

    private MembersAdapter membersAdapter;
    private String previousFragment = "";

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

    public InputNewUsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InputNewUsersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InputNewUsersFragment newInstance(String param1, String param2) {
        InputNewUsersFragment fragment = new InputNewUsersFragment();
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
        initMandatory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_input_new_users, container, false);
        recyclerViewMember = view.findViewById(R.id.recyclerViewMember);
        etIdKK = view.findViewById(R.id.etIdKK);
        etKepKK = view.findViewById(R.id.etKepKK);
        etAddress = view.findViewById(R.id.etAddress);
        etRT = view.findViewById(R.id.etRT);
        etRW = view.findViewById(R.id.etRW);
        etKel = view.findViewById(R.id.etKel);
        etKec = view.findViewById(R.id.etKec);
        etKota = view.findViewById(R.id.etKota);
        etPostal = view.findViewById(R.id.etPostal);
        etProvinsi = view.findViewById(R.id.etProvinsi);
        etPassword = view.findViewById(R.id.etPassword);
        cvAddMember = view.findViewById(R.id.cvAddMember);
        backButton = view.findViewById(R.id.backButton);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        saveButton = view.findViewById(R.id.saveButton);
        overlay = view.findViewById(R.id.overlay);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpText();
        initListener();
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

    private void initMandatory() {
        fragmentListener.onFragmentCreated(InputNewUsersFragment.this, previousFragment);
        temporaryUserAdded = new ArrayList<>();
        customLoadingDialog = new CustomLoadingDialog(context);
    }

    private void initListener() {
        cvAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                CustomMembershipDialog customMembershipDialog = CustomMembershipDialog.newInstance(context, "Jumlah Anggota Keluarga")
                        .setButton("Tambah", "Batal", new CustomMembershipDialogListener() {
                            @Override
                            public void onButtonPositivePressed(int total) {
                                User user = new User();
                                user.setNamaLengkap("-- Nama wajib diisi --");
                                user.setIdKtp("-- KTP wajib diisi --");
                                ArrayList<User> arrayList = new ArrayList<>();
                                for (int i = 1; i <= total; i++) {
                                    arrayList.add(user);
                                }
                                membersAdapter = new MembersAdapter(context, arrayList);
                                membersAdapter.setClickListener(InputNewUsersFragment.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                                recyclerViewMember.setLayoutManager(layoutManager);
                                recyclerViewMember.setAdapter(membersAdapter);
                                tvEmpty.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onButtonNegativePressed() {

                            }
                        });
                if (fm != null) {
                    customMembershipDialog.show(fm, "custom_membership_dialog");
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etIdKK.getText().toString().isEmpty() && !etKepKK.getText().toString().isEmpty() &&
                        !etAddress.getText().toString().isEmpty() && !etRT.getText().toString().isEmpty() &&
                        !etRW.getText().toString().isEmpty() && !etKel.getText().toString().isEmpty() &&
                        !etKec.getText().toString().isEmpty() && !etKota.getText().toString().isEmpty() &&
                        !etPostal.getText().toString().isEmpty() && !etProvinsi.getText().toString().isEmpty()) {
                    showOverlay(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            saveDataKK();
                            showOverlay(false);
                            FragmentManager fm = getFragmentManager();
                            CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance(context,
                                    "", "Data Kartu Keluarga berhasil didaftarkan")
                                    .setButton("Tutup", "", new CustomAlertDialogListener() {
                                        @Override
                                        public void onNegativePressed() {
                                        }

                                        @Override
                                        public void onPositivePressed() {
                                            fragmentListener.onFragmentFinish(InputNewUsersFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_HOME_ADMIN, false);
                                        }
                                    });
                            if (fm != null) {
                                customAlertDialog.show(fm, "custom_alert_dialog");
                            }
                        }
                    }, 3000);
                } else {
                    FragmentManager fm = getFragmentManager();
                    CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance(context,
                            "Informasi", "Silakan melengkapi seluruh data\nKartu Keluarga terlebih dahulu")
                            .setButton("Tutup", "", new CustomAlertDialogListener() {
                                @Override
                                public void onNegativePressed() {
                                }

                                @Override
                                public void onPositivePressed() {
                                }
                            });
                    if (fm != null) {
                        customAlertDialog.show(fm, "custom_alert_dialog");
                    }
                }
            }
        });
        etPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etPassword.getRight() - etPassword.getCompoundDrawables()[2].getBounds().width())) {
                        if (etPassword.getTransformationMethod() instanceof PasswordTransformationMethod) {
                            etPassword.setTransformationMethod(null);
                            Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_visibility_off);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                        } else {
                            etPassword.setTransformationMethod(new PasswordTransformationMethod());
                            Drawable img = ContextCompat.getDrawable(v.getContext(), R.drawable.ic_visibility_on);
                            etPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                        }
                    }
                }
                return false;
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onActivityBackPressed();
            }
        });
    }

    private void saveDataKK() {
        String detailAddress = etAddress.getText().toString() + ", RT. " +
                etRT.getText().toString() + ", RW. " +
                etRW.getText().toString() + ", Kel. " +
                etKel.getText().toString() + ", Kec. " +
                etKec.getText().toString() + ", Kota " +
                etKota.getText().toString() + ", Kode Pos: " +
                etPostal.getText().toString();

        KartuKeluarga tempKKObj = new KartuKeluarga();
        tempKKObj.setIdKartuKeluarga(etIdKK.getText().toString());
        tempKKObj.setNamaKepalaKeluarga(etKepKK.getText().toString());
        tempKKObj.setAlamatLengkap(detailAddress);
        tempKKObj.setAlamatRumah(etAddress.getText().toString());
        tempKKObj.setNomorRt(Integer.parseInt(etRT.getText().toString()));
        tempKKObj.setNomorRw(Integer.parseInt(etRW.getText().toString()));
        tempKKObj.setKelurahan(etKel.getText().toString());
        tempKKObj.setKecamatan(etKec.getText().toString());
        tempKKObj.setKota(etKota.getText().toString());
        tempKKObj.setKodePos(Integer.parseInt(etPostal.getText().toString()));
        tempKKObj.setProvinsi(etProvinsi.getText().toString());
        tempKKObj.setKeluargaList(temporaryUserAdded);
        tempKKObj.setPassword(etPassword.getText().toString());

        ArrayList<Object> kkObjList = VSPreference.getInstance(context).getKKList(); //get KK List from Pref
        ArrayList<Object> kartuKeluargaList = new ArrayList<>(); //create new temporary KK obj List
        if (kkObjList.size() > 0) {
            for (int i = 0; i < kkObjList.size(); i++) {
                KartuKeluarga kkObj = (KartuKeluarga) kkObjList.get(i);
                kartuKeluargaList.add(kkObj);
            }
        }
        kartuKeluargaList.add(tempKKObj);
        Log.d("XXXLOG", "saveDataKK - KK List: " + new Gson().toJson(kartuKeluargaList));
        VSPreference.getInstance(context).saveKKList(kartuKeluargaList);

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

    private void showAddMemberDialog(final MembersAdapter.MemberHolder memberHolder, final View view, int position, String ktp) {
        FragmentManager fm = getFragmentManager();
        InputUserDialog inputUserDialog = InputUserDialog.newInstance(context, "Anggota Keluarga", ktp)
                .setButton("Tambah", "Batal", new InputUserDialogListener() {
                    @Override
                    public void onAddButtonPressed(User user) {
                        //TODO: Update Name AND KTP from selected item
                        memberHolder.setUpdateData(user.getNamaLengkap(), user.getIdKtp());
                        temporaryUserAdded.add(user);
                    }

                    @Override
                    public void onCancelButtonPressed() {
                    }
                });
        if (fm != null) {
            inputUserDialog.show(fm, "input_user_dialog");
        }
    }

    private void setUpText() {
        etAddress.setText("Jl. Raya Penggilingan");
        etRT.setText("007");
        etRW.setText("014");
        etKel.setText("Penggilingan");
        etKec.setText("Cakung");
        etKota.setText("Jakarta Timur");
        etPostal.setText("13490");
        etProvinsi.setText("DKI Jakarta");
    }

    @Override
    public void onItemClick(MembersAdapter.MemberHolder memberHolder, View view, int position, String ktp) {
        showAddMemberDialog(memberHolder, view, position, ktp);
    }

}
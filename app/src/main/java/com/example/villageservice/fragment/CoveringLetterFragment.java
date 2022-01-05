package com.example.villageservice.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.villageservice.R;
import com.example.villageservice.activity.UserActivity;
import com.example.villageservice.adapter.CustomSpinnerAdapter;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoveringLetterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoveringLetterFragment extends Fragment {

    private Context context;
    private FragmentListener fragmentListener;
    private CustomLoadingDialog customLoadingDialog;
    private ArrayList<String> ktpArrayList;

    private User user;
    private KartuKeluarga kartuKeluarga;

    private View view;
    private ImageView backButton;
    private RelativeLayout overlay;
    private EditText etIdNama;
    private EditText etTempatLahir;
    private EditText etTanggal;
    private EditText etTahun;
    private EditText etPekerjaan;
    private EditText etPendidikan;
    private EditText etAgama;
    private EditText etAddress;
    private EditText etRT;
    private EditText etRW;
    private EditText etKel;
    private EditText etKec;
    private EditText etKota;
    private EditText etPostal;
    private EditText etMaksud;
    private Button sendButton;
    private Spinner ktpChooser;
    private Spinner genderChooser;
    private Spinner monthChooser;
    private Spinner kewarganegaraanChooser;

    private String clType = "";
    private String menuSelected = "";
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

    public CoveringLetterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoveringLetterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoveringLetterFragment newInstance(String param1, String param2) {
        CoveringLetterFragment fragment = new CoveringLetterFragment();
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
            Log.d("XXXLOG", "onCreate - menu: " + getArguments().getString(ConstantVariable.KEY_CL_BUNDLE));
            switch (getArguments().getString(ConstantVariable.KEY_CL_BUNDLE)) {
                case ConstantVariable.KEY_CL_NIKAH:
                    clType = ConstantVariable.KEY_CL_NIKAH;
                    menuSelected = "Mengajukan Surat Pengantar Layak Nikah";
                    break;
                case ConstantVariable.KEY_CL_UMKM:
                    clType = ConstantVariable.KEY_CL_UMKM;
                    menuSelected = "Mengajukan Surat Keterangan Usaha (UMKM)";
                    break;
                case ConstantVariable.KEY_CL_DOMISILI_KTP:
                    clType = ConstantVariable.KEY_CL_DOMISILI_KTP;
                    menuSelected = "Mengajukan Surat Pindah Domisili KTP";
                    break;
                case ConstantVariable.KEY_CL_KK_BARU:
                    clType = ConstantVariable.KEY_CL_KK_BARU;
                    menuSelected = "Mengajukan Surat Pengantar Pembuatan KK Baru";
                    break;
                case ConstantVariable.KEY_CL_AKTA_LAHIR:
                    clType = ConstantVariable.KEY_CL_AKTA_LAHIR;
                    menuSelected = "Mengajukan Surat Pengantar Akta Kelahiran";
                    break;
                case ConstantVariable.KEY_CL_AKTA_KEMATIAN:
                    clType = ConstantVariable.KEY_CL_AKTA_KEMATIAN;
                    menuSelected = "Mengajukan Surat Pengantar Akta Kematian";
                    break;
            }
        }
        initMandatory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_covering_letter, container, false);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < kartuKeluarga.getKeluargaList().size(); i++) {
            ktpArrayList.add(kartuKeluarga.getKeluargaList().get(i).getIdKtp());
        }

        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(context, R.layout.spinner_items, ktpArrayList);
        ktpChooser.setAdapter(spinnerAdapter);

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
        fragmentListener.onFragmentCreated(CoveringLetterFragment.this, previousFragment);
        customLoadingDialog = new CustomLoadingDialog(context);
        kartuKeluarga = VSPreference.getInstance(context).getKK();
        user = new User();
        ktpArrayList = new ArrayList<>();
    }

    private void initView() {
        backButton = view.findViewById(R.id.backButton);
        etIdNama = view.findViewById(R.id.etIdNama);
        etTempatLahir = view.findViewById(R.id.etTempatLahir);
        etTanggal = view.findViewById(R.id.etTanggal);
        etTahun = view.findViewById(R.id.etTahun);
        etPekerjaan = view.findViewById(R.id.etPekerjaan);
        etPendidikan = view.findViewById(R.id.etPendidikan);
        etAgama = view.findViewById(R.id.etAgama);
        etAddress = view.findViewById(R.id.etAddress);
        etRT = view.findViewById(R.id.etRT);
        etRW = view.findViewById(R.id.etRW);
        etKel = view.findViewById(R.id.etKel);
        etKec = view.findViewById(R.id.etKec);
        etKota = view.findViewById(R.id.etKota);
        etPostal = view.findViewById(R.id.etPostal);
        etMaksud = view.findViewById(R.id.etMaksud);
        sendButton = view.findViewById(R.id.sendButton);
        ktpChooser = view.findViewById(R.id.ktpChooser);
        genderChooser = view.findViewById(R.id.genderChooser);
        monthChooser = view.findViewById(R.id.monthChooser);
        kewarganegaraanChooser = view.findViewById(R.id.kewarganegaraanChooser);
        overlay = view.findViewById(R.id.overlay);
    }

    private void initListener() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance(context, "", "Apakah semua data yang Anda isi sudah benar?")
                        .setButton("Ya", "Tidak", new CustomAlertDialogListener() {
                            @Override
                            public void onNegativePressed() {
                            }

                            @Override
                            public void onPositivePressed() {
                                send();
                            }
                        });
                if (fm != null) {
                    customAlertDialog.show(fm, "custom_alert_dialog");
                }
            }
        });
        ktpChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ktp = String.valueOf(ktpChooser.getSelectedItem());
                Log.d("XXXLOG", "onItemSelected - ktp: " + ktp);
                for (int i = 0; i < kartuKeluarga.getKeluargaList().size(); i++) {
                    if (ktp.equalsIgnoreCase(kartuKeluarga.getKeluargaList().get(i).getIdKtp())) {
                        user = kartuKeluarga.getKeluargaList().get(i);
                        populateData();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onActivityBackPressed();
            }
        });
    }

    private void send() {
        overlay.setVisibility(View.VISIBLE);
        customLoadingDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String alamatLengkap = etAddress.getText().toString() +
                        ", RT. " + etRT.getText().toString() +
                        ", RW. " + etRW.getText().toString() +
                        ", Kel. " + etKel.getText().toString() +
                        ", Kec. " + etKec.getText().toString() +
                        ", Kota " + etKota.getText().toString() +
                        ", Kode Pos: " + etPostal.getText().toString();
                String tempatTanggalLahir = etTempatLahir.getText().toString() + ", " + etTanggal.getText().toString() + " " + monthChooser.getSelectedItem() + " " + etTahun.getText().toString();
                CoveringLetter coveringLetter = new CoveringLetter("LAMPIRAN XIII: MODEL AA.05", "Nomor: 12345678910987645",
                        etIdNama.getText().toString(), String.valueOf(genderChooser.getSelectedItem()), tempatTanggalLahir,
                        etPekerjaan.getText().toString(), String.valueOf(ktpChooser.getSelectedItem()), String.valueOf(kewarganegaraanChooser.getSelectedItem()),
                        etPendidikan.getText().toString(), etAgama.getText().toString(), alamatLengkap, etMaksud.getText().toString(),
                        "…/JT/VI/3/014/…/2022", "05/02/2022", "Bpk. Rudi", "05/02/2022", "Sukina");
                coveringLetter.setOpened(false);
                coveringLetter.setApproved(false);
                coveringLetter.setClType(clType);
                Log.d("XXXLOG", "CL Fragment - coveringLetter to be send: " + new Gson().toJson(coveringLetter));

                ArrayList<Object> coveringLetterArrayList = new ArrayList<>();
                ArrayList<Object> tempObj = VSPreference.getInstance(context).getCoveringLetterList(ConstantVariable.KEY_CL_NIKAH);
                if (tempObj.size() > 0) {
                    for (int i = 0; i < tempObj.size(); i++) {
                        CoveringLetter clTempObj = (CoveringLetter) tempObj.get(i);
                        coveringLetterArrayList.add(clTempObj);
                    }
                }
                coveringLetterArrayList.add(coveringLetter);
                VSPreference.getInstance(context).setCoveringLetterList(ConstantVariable.KEY_CL_NIKAH, coveringLetterArrayList);

                customLoadingDialog.dismiss();
                overlay.setVisibility(View.INVISIBLE);

                FragmentManager fm = getFragmentManager();
                CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance(context, "", "Data berhasil terkirim")
                        .setButton("Tutup", "", new CustomAlertDialogListener() {
                            @Override
                            public void onNegativePressed() {
                            }

                            @Override
                            public void onPositivePressed() {
                                fragmentListener.onFragmentFinish(CoveringLetterFragment.this, UserActivity.FRAGMENT_FINISH_GOTO_HOME, false);
                            }
                        });
                if (fm != null) {
                    customAlertDialog.show(fm, "custom_alert_dialog");
                }
            }
        }, 3000);
    }

    private void populateData() {
        etIdNama.setText(user.getNamaLengkap());
        etTempatLahir.setText(user.getTempatLahir());
        etTanggal.setText(String.valueOf(user.getTanggalLahir().getTanggal()));
        etTahun.setText(String.valueOf(user.getTanggalLahir().getTahun()));
        etPekerjaan.setText(user.getJenisPekerjaan());
        etPendidikan.setText(user.getPendidikan());
        etAgama.setText(user.getAgama());
        etAddress.setText(kartuKeluarga.getAlamatRumah());
        etRT.setText(String.valueOf(kartuKeluarga.getNomorRt()));
        etRW.setText(String.valueOf(kartuKeluarga.getNomorRw()));
        etKel.setText(kartuKeluarga.getKelurahan());
        etKec.setText(kartuKeluarga.getKecamatan());
        etKota.setText(kartuKeluarga.getKota());
        etPostal.setText(String.valueOf(kartuKeluarga.getKodePos()));
        etMaksud.setText(menuSelected);
        etMaksud.setEnabled(false);

        ArrayList<String> genderList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_GENDER));
        ArrayList<String> monthList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_MONTH));
        ArrayList<String> citizensStateList = new ArrayList<>(Arrays.asList(ConstantVariable.SPINNER_CITIZEN_STATE));
        CustomSpinnerAdapter genderAdapter = new CustomSpinnerAdapter(context, R.layout.spinner_items, genderList);
        CustomSpinnerAdapter monthAdapter = new CustomSpinnerAdapter(context, R.layout.spinner_items, monthList);
        CustomSpinnerAdapter csAdapter = new CustomSpinnerAdapter(context, R.layout.spinner_items, citizensStateList);

        genderChooser.setAdapter(genderAdapter);
        monthChooser.setAdapter(monthAdapter);
        kewarganegaraanChooser.setAdapter(csAdapter);
    }
}
package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.villageservice.R;
import com.example.villageservice.adapter.CustomSpinnerAdapter;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;

import java.util.ArrayList;

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

    private View view;
    private RelativeLayout spinnerIdKTP;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        }
        fragmentListener.onFragmentCreated(CoveringLetterFragment.this);
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
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance(context, "", "Apakah semua data yang Anda isi sudah benar?")
                        .setButton("Ya", "Tidak", new CustomAlertDialogListener() {
                            @Override
                            public void onNegativePressed() {}
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

        KartuKeluarga kartuKeluarga = VSPreference.getInstance(context).getKK();
        ktpArrayList = new ArrayList<>();
        for (int i = 0; i < kartuKeluarga.getKeluargaList().size(); i++) {
            ktpArrayList.add(kartuKeluarga.getKeluargaList().get(i).getIdKtp());
        }

        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(context, R.layout.spinner_items, ktpArrayList);
        ktpChooser.setAdapter(spinnerAdapter);
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

    private void initView() {
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
        spinnerIdKTP = view.findViewById(R.id.spinnerIdKTP);
    }

    private void send() {
        String address = etAddress.getText().toString() + ", RT. " +
                etRT.getText().toString() + ", RW. " +
                etRW.getText().toString() + ", Kel. " +
                etKel.getText().toString() + ", Kec. " +
                etKec.getText().toString() + ", Kota " +
                etKota.getText().toString() + ", Kode Pos: " +
                etPostal.getText().toString();
        String tanggalLahir = etTanggal.getText().toString() + " " + monthChooser.getSelectedItem() + " " + etTahun.getText().toString();
        CoveringLetter coveringLetter = new CoveringLetter("LAMPIRAN XIII: MODEL AA.05",
                "Nomor: " + " 12345678910111213", etIdNama.getText().toString(),
                String.valueOf(genderChooser.getSelectedItem()), etTempatLahir.getText().toString() + tanggalLahir, etPekerjaan.getText().toString(),
                String.valueOf(ktpChooser.getSelectedItem()), String.valueOf(kewarganegaraanChooser.getSelectedItem()),
                etPendidikan.getText().toString(), etAgama.getText().toString(), address, etMaksud.getText().toString(),
                "…/JT/VI/3/014/…/2022", "05/02/2022", "Rudi", "05/02/2022", "Sukina");
        coveringLetter.setOpened(false);

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
    }
}
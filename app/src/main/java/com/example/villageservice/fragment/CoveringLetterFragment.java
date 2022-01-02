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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.villageservice.R;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.utility.VSPreference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoveringLetterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoveringLetterFragment extends Fragment {

    private Context context;
    private FragmentListener fragmentListener;
    private CustomLoadingDialog customLoadingDialog;

    private View view;

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
        kewarganegaraanChooser = view.findViewById(R.id.kewarganegaraanChooser);
    }

    private void send() {
        String idKtp = String.valueOf(ktpChooser.getSelectedItem());
        String address = etAddress.getText().toString() + ", RT. " +
                etRT.getText().toString() + ", RW. " +
                etRW.getText().toString() + ", Kel. " +
                etKel.getText().toString() + ", Kec. " +
                etKec.getText().toString() + ", Kota " +
                etKota.getText().toString() + ", Kode Pos: " +
                etPostal.getText().toString();
        Log.d("XXXCL", "send - id selected: " + idKtp);
        CoveringLetter coveringLetter = new CoveringLetter("LAMPIRAN XIII: MODEL AA.05", "Nomor" + " 132912830918230912",
                etIdNama.getText().toString(), String.valueOf(genderChooser.getSelectedItem()), etTempatLahir.getText().toString() + ", 20-12-1995", etPekerjaan.getText().toString(),
                "12931029310293102", String.valueOf(kewarganegaraanChooser.getSelectedItem()), etPendidikan.getText().toString(), etAgama.getText().toString(), address, etMaksud.getText().toString(),
                "NomorFooter", "tanggalRW", "namaRW", "tanggalRT", "namaRT");
        coveringLetter.setOpened(false);
        VSPreference.getInstance(context).setCoveringLetter(idKtp, coveringLetter);
    }
}
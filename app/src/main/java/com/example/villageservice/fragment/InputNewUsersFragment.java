package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.villageservice.R;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.library.InputUserDialog;
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
public class InputNewUsersFragment extends Fragment {

    private Context context;
    private FragmentListener fragmentListener;
    private CustomLoadingDialog customLoadingDialog;

    private List<User> temporaryUserAdded;

    private View view;
    private ImageView addMemberButton;
    private EditText etIdKK;
    private EditText etNamaLengkap;
    private EditText etKewarganegaraan;
    private EditText etPekerjaan;
    private Button btn1;

    KartuKeluarga kartuKeluarga = new KartuKeluarga();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        fragmentListener.onFragmentCreated(InputNewUsersFragment.this);
        initMandatory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_input_new_users, container, false);
        addMemberButton = view.findViewById(R.id.addMemberButton);
        etIdKK = view.findViewById(R.id.etIdKK);
        etNamaLengkap = view.findViewById(R.id.etKepKK);
        etPekerjaan = view.findViewById(R.id.etAddress);
        etKewarganegaraan = view.findViewById(R.id.etKel);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addMemberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveDataKK();
                //showInputUserDialog();
                long idKtp = Long.parseLong(etIdKK.getText().toString());
                String namaLengkap = etNamaLengkap.getText().toString();
                String pekerjaan = etPekerjaan.getText().toString();
                String kewarganegaraan = etKewarganegaraan.getText().toString();

                User user = new User();
                user.setIdKtp(idKtp);
                user.setNamaLengkap(namaLengkap);
                user.setJenisPekerjaan(pekerjaan);
                user.setKewarganegaraan(kewarganegaraan);
                VSPreference.getInstance(context).setUser(user);
                Log.d("USERS", "onClick - data: " + VSPreference.getInstance(context).getUser());

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

    private void initMandatory() {
        temporaryUserAdded = new ArrayList<>();
    }

    private void showInputUserDialog() {
        FragmentManager fm = getFragmentManager();
        InputUserDialog inputUserDialog = InputUserDialog.newInstance(context, "Anggota Keluarga")
                .setButton("Tambah", "Batal", new InputUserDialogListener() {
                    @Override
                    public void onAddButtonPressed(User user) {
                        temporaryUserAdded.add(user);
                    }
                    @Override
                    public void onCancelButtonPressed() {}
                });
        if (fm != null) {
            inputUserDialog.show(fm, "input_user_dialog");
        }
    }
    private void saveDataKK() {
        String key = etIdKK.getText().toString();
        long id = Long.parseLong(etIdKK.getText().toString());
        kartuKeluarga.setIdKartuKeluarga(id);
        kartuKeluarga.setAnggotaKeluarga(temporaryUserAdded);

        VSPreference.getInstance(context).inputKK(key, kartuKeluarga);
        Log.d("KartuKeluarga", "onViewCreated - data: " + new Gson().toJson(VSPreference.getInstance(context).getKK(key)));
    }

    private void insertKTP(User user) {
        String key = String.valueOf(user.getIdKtp());
        VSPreference.getInstance(context).insertUser(key, user);
    }

    private void updateFamilyMember() {

    }

    private void deleteFamilyMember() {

    }

}
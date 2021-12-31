package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.villageservice.R;
import com.example.villageservice.activity.AdminActivity;
import com.example.villageservice.adapter.FormListAdapter;
import com.example.villageservice.listener.FormUserRequestedListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.Fonts;
import com.example.villageservice.utility.VSPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormListFragment extends Fragment implements FormUserRequestedListener {

    private Context context;
    private View view;
    private ListView listForm;
    private FragmentListener fragmentListener;

    private List<User> users;

    private ImageView buttonLeft;
    private ImageView buttonRight;
    private AppCompatTextView tvPageTitle;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FormListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FormListFragment newInstance(String param1, String param2) {
        FormListFragment fragment = new FormListFragment();
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
        fragmentListener.onFragmentCreated(FormListFragment.this);
        users = new ArrayList<>();
        users = getUserRequestList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_form_list, container, false);
        tvPageTitle = view.findViewById(R.id.tvTitlePage);
        buttonLeft = view.findViewById(R.id.buttonLeft);
        buttonRight = view.findViewById(R.id.buttonRight);

        FormListAdapter formListAdapter = new FormListAdapter(context, users, this);
        listForm = view.findViewById(R.id.listForm);
        listForm.setDivider(null);
        listForm.setDividerHeight(0);
        listForm.setAdapter(formListAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvPageTitle.setTypeface(new Fonts(context).rBoldExtra());
        tvPageTitle.setTextColor(context.getResources().getColor(R.color.white));
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvPageTitle, 14, 24, 1, TypedValue.COMPLEX_UNIT_SP);

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

    private List<User> getUserRequestList() {
        List<Object> objListUser = VSPreference.getInstance(context).loadUserList();
        for (int i = 0; i < objListUser.size(); i++) {
            User user = (User) objListUser.get(i);
            users.add(user);
        }
        return users;
    }

    private void initListener() {
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onFragmentFinish(FormListFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_HOME_ADMIN, false);
            }
        });
    }

    @Override
    public void onSelectedUserRequest(long ktp, String name) {
        User user = new User();
        CoveringLetter coveringLetter = new CoveringLetter();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getIdKtp() == ktp && users.get(i).getNamaLengkap().equalsIgnoreCase(name)) {
                user.setIdKtp(ktp);
                user.setIdKartuKeluarga(users.get(i).getIdKartuKeluarga());
                user.setNamaLengkap(users.get(i).getNamaLengkap());
                user.setJenisKelamin(users.get(i).getJenisKelamin());
                user.setTempatLahir(users.get(i).getTempatLahir());
                user.setTanggalLahir(users.get(i).getTanggalLahir());
                user.setTanggalBergabung(users.get(i).getTanggalBergabung());
                user.setAgama(users.get(i).getAgama());
                user.setPendidikan(users.get(i).getPendidikan());
                user.setJenisPekerjaan(users.get(i).getJenisPekerjaan());
                user.setStatusPernikahan(users.get(i).getStatusPernikahan());
                user.setStatusHubunganDalamKeluarga(users.get(i).getStatusHubunganDalamKeluarga());
                user.setKewarganegaraan(users.get(i).getKewarganegaraan());
                user.setNamaAyah(users.get(i).getNamaAyah());
                user.setNamaIbu(users.get(i).getNamaIbu());
            }
        }
        appliedCoveringLetter(user);
        VSPreference.getInstance(context).setUser(user);
        fragmentListener.onFragmentFinish(FormListFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_PDF_VIEWER, true);
    }

    private void appliedCoveringLetter(User user) {
        CoveringLetter coveringLetter = new CoveringLetter("LAMPIRAN XIII: MODEL AA.05", "Nomor" + "132912830918230912",
                user.getNamaLengkap(), user.getJenisKelamin(), "20-12-1995", user.getJenisPekerjaan(),
                String.valueOf(user.getIdKtp()), user.getKewarganegaraan(), user.getPendidikan(), user.getAgama(), "", "keperluan",
                "NomorFooter", "tanggalRW", "namaRW", "tanggalRT", "namaRT");
        VSPreference.getInstance(context).setCoveringLetter(String.valueOf(user.getIdKtp()), coveringLetter);
    }
}
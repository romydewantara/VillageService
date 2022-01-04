package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.Fonts;
import com.example.villageservice.utility.VSPreference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    private String previousFragment;
    private String menuSelected;

    private List<CoveringLetter> coveringLetters;
    private ArrayList<Object> coveringLetterArrayList;

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

    public void addPreviousFragmentTag(String previousFragment) {
        Log.d("PREVIOUS", "previousFragmentTag: " + previousFragment);
        this.previousFragment = previousFragment;
    }

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
            menuSelected = getArguments().getString("menu");
            Log.d("XXXLOG", "onCreate - menuSelected: " + menuSelected);
        }
        fragmentListener.onFragmentCreated(FormListFragment.this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_form_list, container, false);
        tvPageTitle = view.findViewById(R.id.tvTitlePage);
        buttonLeft = view.findViewById(R.id.buttonLeft);
        buttonRight = view.findViewById(R.id.buttonRight);
        listForm = view.findViewById(R.id.listForm);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvPageTitle.setTypeface(new Fonts(context).rBoldExtra());
        tvPageTitle.setTextColor(context.getResources().getColor(R.color.white));
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvPageTitle, 14, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        coveringLetterArrayList = VSPreference.getInstance(context).getCoveringLetterList(menuSelected);

        coveringLetters = new ArrayList<>();
        for (int i = 0; i < coveringLetterArrayList.size(); i++) {
            CoveringLetter coveringLetter = (CoveringLetter) coveringLetterArrayList.get(i);
            Log.d("XXXLOG", "onViewCreated - keperluan: " + coveringLetter.getClKeperluan());
            if (coveringLetter.getClType() != null) {
                if (coveringLetter.getClType().equalsIgnoreCase(menuSelected)) {
                    coveringLetters.add(coveringLetter);
                }
            }
        }
        Collections.reverse(coveringLetters);
        FormListAdapter formListAdapter = new FormListAdapter(context, coveringLetters, this);
        listForm.setDivider(null);
        listForm.setDividerHeight(0);
        listForm.setAdapter(formListAdapter);

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

    private void initListener() {
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onFragmentFinish(FormListFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_HOME_ADMIN, false);
            }
        });
    }

    @Override
    public void onSelectedUserRequest(String ktp, String name) {
        CoveringLetter coveringLetter = new CoveringLetter();
        for (int i = 0; i < coveringLetters.size(); i++) {
            if (coveringLetters.get(i).getClKtp().equalsIgnoreCase(ktp) && coveringLetters.get(i).getClNama().equalsIgnoreCase(name)) {
                coveringLetter.setClNama(coveringLetters.get(i).getClNama());
                coveringLetter.setClKtp(coveringLetters.get(i).getClKtp());
                coveringLetter.setClJenisKelamin(coveringLetters.get(i).getClJenisKelamin());
                coveringLetter.setClPekerjaan(coveringLetters.get(i).getClPekerjaan());
                coveringLetter.setClTempatTanggalLahir(coveringLetters.get(i).getClTempatTanggalLahir());
                Log.d("XXXLOG", "onSelectedUserRequest - ttl: " + coveringLetters.get(i).getClTempatTanggalLahir());
                coveringLetter.setClAgama(coveringLetters.get(i).getClAgama());
                coveringLetter.setClAlamat(coveringLetters.get(i).getClAlamat());
                coveringLetter.setClPendidikan(coveringLetters.get(i).getClPendidikan());
                coveringLetter.setClKewarganegaraan(coveringLetters.get(i).getClKewarganegaraan());
                coveringLetter.setClKeperluan(coveringLetters.get(i).getClKeperluan());
                break;
            }
        }

        appliedCoveringLetter(coveringLetter);
        fragmentListener.onFragmentFinish(FormListFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_PDF_VIEWER, true);
    }

    private void appliedCoveringLetter(CoveringLetter cl) {
        CoveringLetter coveringLetter = new CoveringLetter("LAMPIRAN XIII: MODEL AA.05",
                "Nomor: " + "…/JT/VI/3/007/014/…/2022",
                cl.getClNama(), cl.getClJenisKelamin(), cl.getClTempatTanggalLahir(), cl.getClPekerjaan(),
                String.valueOf(cl.getClKtp()), cl.getClKewarganegaraan(), cl.getClPendidikan(), cl.getClAgama(), cl.getClAlamat(), cl.getClKeperluan(),
                "…/JT/VI/3/014/…/2022", "05/02/2022", "Bpk. Rudi", "05/02/2022", "Bpk. Sukina");
        VSPreference.getInstance(context).setCoveringLetter(ConstantVariable.KEY_COVERING_LETTER, coveringLetter);
    }
}
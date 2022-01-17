package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.villageservice.R;
import com.example.villageservice.activity.AdminActivity;
import com.example.villageservice.activity.UserActivity;
import com.example.villageservice.adapter.NotificationsAdapter;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment implements NotificationsAdapter.ItemClickListener {

    private Context context;
    private CustomLoadingDialog customLoadingDialog;
    private FragmentListener fragmentListener;
    private View view;
    private AppCompatTextView tvSubTitle;
    private RelativeLayout overlay;
    private ConstraintLayout constraintEmptyNotification;
    private RecyclerView recyclerNotification;
    private ConstraintLayout constraintNotifications;

    private List<Object> coveringLetters;
    private KartuKeluarga kartuKeluarga;
    private ArrayList<CoveringLetter> coveringLetterArrayList;
    private NotificationsAdapter notificationsAdapter;

    private boolean isAdmin = false;

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

    public NotificationsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NotificationsFragment newInstance(String param1, String param2) {
        NotificationsFragment fragment = new NotificationsFragment();
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
        view = inflater.inflate(R.layout.fragment_notifications, container, false);
        tvSubTitle = view.findViewById(R.id.tvSubTitle);
        recyclerNotification = view.findViewById(R.id.recyclerNotification);
        constraintNotifications = view.findViewById(R.id.constraintNotifications);
        constraintEmptyNotification = view.findViewById(R.id.constraintEmptyNotification);
        overlay = view.findViewById(R.id.overlay);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prepareLayout();
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
        fragmentListener.onFragmentCreated(NotificationsFragment.this, previousFragment);
        customLoadingDialog = new CustomLoadingDialog(context);
        coveringLetterArrayList = new ArrayList<>();
        coveringLetters = new ArrayList<>();

        if (VSPreference.getInstance(context).getRole().equalsIgnoreCase(ConstantVariable.ADMIN))
            isAdmin = true;

        coveringLetters = VSPreference.getInstance(context).getCoveringLetterGroupList(ConstantVariable.KEY_COVERING_LETTERS_LIST);
        if (!isAdmin) {
            kartuKeluarga = VSPreference.getInstance(context).getKK();
        }
        Log.d("XXXLOG", "initMandatory - cl: " + new Gson().toJson(coveringLetters));
    }

    private void initListener() {

    }

    private void prepareLayout() {
        showOverlay(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                constraintNotifications.setVisibility(View.VISIBLE);
                tvSubTitle.setVisibility(View.VISIBLE);
                populateData();
                showOverlay(false);
            }
        }, 1400);
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

    private void populateData() {
        if (!coveringLetters.isEmpty()) {
            for (int i = 0; i < coveringLetters.size(); i++) {
                CoveringLetter coveringLetter = (CoveringLetter) coveringLetters.get(i);
                if (isAdmin) {
                    coveringLetterArrayList.add(coveringLetter);
                } else {
                    for (int j = 0; j < kartuKeluarga.getKeluargaList().size(); j++) {
                        if (kartuKeluarga.getKeluargaList().get(j).getIdKtp().equalsIgnoreCase(coveringLetter.getClKtp())) {
                            coveringLetterArrayList.add(coveringLetter);
                        }
                    }
                }
            }
            Collections.reverse(coveringLetterArrayList);
            notificationsAdapter = new NotificationsAdapter(coveringLetterArrayList);
            notificationsAdapter.setClickListener(NotificationsFragment.this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerNotification.setLayoutManager(layoutManager);
            recyclerNotification.setAdapter(notificationsAdapter);
        } else {
            constraintEmptyNotification.setVisibility(View.VISIBLE);
        }
    }

    private void appliedCoveringLetter(CoveringLetter cl) {
        Log.d("XXXLOG", "appliedCoveringLetter - header: " + cl.getClNomorHeader());
        CoveringLetter coveringLetter = new CoveringLetter(cl.getClId(), cl.getClLampiran(),
                cl.getClNomorHeader(), cl.getClNama(), cl.getClJenisKelamin(), cl.getClTempatTanggalLahir(),
                cl.getClPekerjaan(), String.valueOf(cl.getClKtp()), cl.getClKewarganegaraan(), cl.getClPendidikan(),
                cl.getClAgama(), cl.getClAlamat(), cl.getClKeperluan(), cl.getClNomorFooter(), "05/02/2022",
                "Bpk. Rudi", "05/02/2022", "Bpk. Sukina", cl.getClType(), cl.getClTglPengajuan(), cl.isApproved());
        VSPreference.getInstance(context).setCoveringLetter(ConstantVariable.KEY_COVERING_LETTER, coveringLetter);
    }

    @Override
    public void onItemClicked(NotificationsAdapter.NotificationsHolder notificationsHolder, CoveringLetter coveringLetter, int position) {
        Log.d("XXXLOG", "onItemClicked - id: " + position + " | cl: " + new Gson().toJson(coveringLetter));

        appliedCoveringLetter(coveringLetter);
        fragmentListener.onFragmentPassingData(coveringLetter.getClType());
        if (isAdmin) {
            fragmentListener.onFragmentFinish(NotificationsFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_PDF_VIEWER, true);
        } else {
            fragmentListener.onFragmentFinish(NotificationsFragment.this, UserActivity.FRAGMENT_FINISH_GOTO_PDF_VIEWER, true);
        }
    }
}
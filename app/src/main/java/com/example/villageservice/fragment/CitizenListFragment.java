package com.example.villageservice.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.villageservice.R;
import com.example.villageservice.activity.AdminActivity;
import com.example.villageservice.activity.SignInActivity;
import com.example.villageservice.adapter.FormListAdapter;
import com.example.villageservice.adapter.KartuKeluargaListAdapter;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CitizenListFragment extends Fragment implements KartuKeluargaListAdapter.ItemClickListener {

    private Context context;
    private CustomLoadingDialog customLoadingDialog;
    private FragmentListener fragmentListener;
    private View view;
    private RelativeLayout overlay;

    private ConstraintLayout constraintEmptyData;
    private LottieAnimationView lottieEmptyData;
    private ImageView buttonLeft;
    private AppCompatTextView tvSubTitle;
    private AppCompatTextView tvEmptyData;
    private Animation fadeIn;
    private String menuSelected;
    private String clType;

    private ArrayList<KartuKeluarga> kartuKeluargaList;
    private KartuKeluargaListAdapter kkListAdapter;
    private RecyclerView recyclerViewKK;

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

    public CitizenListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CitizenListFragment newInstance(String param1, String param2) {
        CitizenListFragment fragment = new CitizenListFragment();
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
        view = inflater.inflate(R.layout.fragment_citizen_list, container, false);

        buttonLeft = view.findViewById(R.id.buttonLeft);
        tvSubTitle = view.findViewById(R.id.tvSubTitle);
        recyclerViewKK = view.findViewById(R.id.recyclerViewKK);
        constraintEmptyData = view.findViewById(R.id.constraintEmptyData);
        lottieEmptyData = view.findViewById(R.id.lottieEmptyData);
        tvEmptyData = view.findViewById(R.id.tvEmptyData);
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

    private void initListener() {
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onActivityBackPressed();
            }
        });
    }

    private void initMandatory() {
        fragmentListener.onFragmentCreated(CitizenListFragment.this, previousFragment);
        customLoadingDialog = new CustomLoadingDialog(context);
        kartuKeluargaList = new ArrayList<>();
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }

    private void fetchList() {
        if (kkListAdapter != null) {
            kkListAdapter = null;
            kartuKeluargaList.clear();
            recyclerViewKK.removeAllViews();
        }
        ArrayList<Object> kkObjList = VSPreference.getInstance(context).getKKList();
        if (!kkObjList.isEmpty()) {
            for (int i = 0; i < kkObjList.size(); i++) {
                KartuKeluarga kartuKeluarga = (KartuKeluarga) kkObjList.get(i);
                kartuKeluargaList.add(kartuKeluarga);
            }
        }

        Collections.reverse(kartuKeluargaList);
        kkListAdapter = new KartuKeluargaListAdapter(context, kartuKeluargaList);
        kkListAdapter.setItemClickListener(CitizenListFragment.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerViewKK.setLayoutManager(layoutManager);
        recyclerViewKK.setAdapter(kkListAdapter);

        if (kartuKeluargaList.isEmpty()) {
            constraintEmptyData.setVisibility(View.VISIBLE);
            tvEmptyData.setText("Oops! belum ada data warga…");
        }
    }

    private void prepareLayout() {
        showOverlay(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvSubTitle.setVisibility(View.VISIBLE);
                tvSubTitle.startAnimation(fadeIn);

                fetchList();
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

    @Override
    public void onDelete(final int position) {
        FragmentManager fm = getFragmentManager();
        CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance(context, "", "Apakah Anda yakin ingin menghapus data kartu keluarga Bpk. " + kartuKeluargaList.get(position).getNamaKepalaKeluarga())
                .setButton("Ya", "Batal", new CustomAlertDialogListener() {
                    @Override
                    public void onNegativePressed() {}
                    @Override
                    public void onPositivePressed() {
                        Log.d("XXXLOG", "onPositivePressed - kartu keluarga yg akan dihapus: " + kartuKeluargaList.get(position).getIdKartuKeluarga());
                        for (int i = 0; i < kartuKeluargaList.size(); i++) {
                            if (kartuKeluargaList.get(i).getIdKartuKeluarga().equalsIgnoreCase(kartuKeluargaList.get(position).getIdKartuKeluarga())) {
                                kartuKeluargaList.remove(i);
                                break;
                            }
                        }
                        Collections.reverse(kartuKeluargaList);
                        ArrayList<Object> kkUpdated = new ArrayList<Object>(kartuKeluargaList);
                        VSPreference.getInstance(context).saveKKList(kkUpdated);
                        fetchList();
                    }
                });
        if (fm != null) {
            customAlertDialog.show(fm, "custom_alert_dialog");
        }
    }

    @Override
    public void onEdit(KartuKeluarga kartuKeluarga, int position) {
        VSPreference.getInstance(context).setKK(kartuKeluarga);
        fragmentListener.onFragmentFinish(CitizenListFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_INPUT_USER, true);
    }
}
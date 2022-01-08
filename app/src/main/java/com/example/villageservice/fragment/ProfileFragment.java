package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
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
import android.widget.Toast;

import com.example.villageservice.R;
import com.example.villageservice.adapter.DataKeluargaAdapter;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.VSPreference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements DataKeluargaAdapter.ItemClickListener {

    private Context context;
    private FragmentListener fragmentListener;
    private CustomLoadingDialog customLoadingDialog;
    private KartuKeluarga kartuKeluarga;
    private View view;
    private RelativeLayout overlay;
    private CardView cvPhotoProfile;
    private ConstraintLayout constraintIdentity;
    private AppCompatTextView tvKepalaKeluarga;
    private AppCompatTextView tvNomorKK;
    private RecyclerView recyclerMember;

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

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initView();
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
        fragmentListener.onFragmentCreated(ProfileFragment.this, previousFragment);
        customLoadingDialog = new CustomLoadingDialog(context);
        kartuKeluarga = VSPreference.getInstance(context).getKK();
    }

    private void initView() {
        cvPhotoProfile = view.findViewById(R.id.cvPhotoProfile);
        constraintIdentity = view.findViewById(R.id.constraintIdentity);
        tvKepalaKeluarga = view.findViewById(R.id.tvKepalaKeluarga);
        tvNomorKK = view.findViewById(R.id.tvNomorKK);
        recyclerMember = view.findViewById(R.id.recyclerMember);
        overlay = view.findViewById(R.id.overlay);

        tvKepalaKeluarga.setText(kartuKeluarga.getNamaKepalaKeluarga());
        tvNomorKK.setText("No. " + kartuKeluarga.getIdKartuKeluarga());
        DataKeluargaAdapter dkAdapter = new DataKeluargaAdapter(kartuKeluarga.getKeluargaList());
        dkAdapter.setClickListener(ProfileFragment.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerMember.setLayoutManager(layoutManager);
        recyclerMember.setAdapter(dkAdapter);
    }

    private void initListener() {

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

    private void prepareLayout() {
        showOverlay(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.bounched_show);
                constraintIdentity.setVisibility(View.VISIBLE);
                cvPhotoProfile.setVisibility(View.VISIBLE);
                cvPhotoProfile.startAnimation(slideUp);
                showOverlay(false);
            }
        }, 1800);
    }

    @Override
    public void onItemClick(DataKeluargaAdapter.DataKeluargaHolder dataKeluargaHolder, String ktp) {
        Toast.makeText(context, "detail for ID:" + ktp + " is coming soon…", Toast.LENGTH_SHORT).show();
    }
}
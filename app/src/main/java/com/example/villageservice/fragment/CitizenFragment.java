package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.activity.AdminActivity;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.Admin;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CitizenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CitizenFragment extends Fragment {

    private Context context;
    private CustomLoadingDialog customLoadingDialog;
    private FragmentListener fragmentListener;

    private LinearLayout linearMiddle;
    private LinearLayout linearMainMenu;
    private ConstraintLayout constraintContainer1;
    private ConstraintLayout constraintContainer2;
    private RelativeLayout overlay;
    private View view;
    private AppCompatTextView tvJWT1;
    private AppCompatTextView tvJWT2;
    private AppCompatTextView tvName;
    private AppCompatTextView tvPhoneNum;
    private CardView cvInfoLeader;
    private CardView cvInfoWargaBaru;
    private Button addUserButton;
    private Button editProfileButton;

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

    public CitizenFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CitizenFragment newInstance(String param1, String param2) {
        CitizenFragment fragment = new CitizenFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_citizen, container, false);
        linearMiddle = view.findViewById(R.id.linearMiddle);
        linearMainMenu = view.findViewById(R.id.linearMainMenu);
        constraintContainer1 = view.findViewById(R.id.constraintContainer1);
        constraintContainer2 = view.findViewById(R.id.constraintContainer2);
        addUserButton = view.findViewById(R.id.addUserButton);
        editProfileButton = view.findViewById(R.id.editProfileButton);
        cvInfoLeader = view.findViewById(R.id.cvInfoLeader);
        cvInfoWargaBaru = view.findViewById(R.id.cvInfoWargaBaru);
        tvJWT1 = view.findViewById(R.id.tvJWT1);
        tvJWT2 = view.findViewById(R.id.tvJWT2);
        tvName = view.findViewById(R.id.tvName);
        tvPhoneNum = view.findViewById(R.id.tvPhoneNum);
        overlay = view.findViewById(R.id.overlay);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListener();
        setUpData();
        prepareLayout();
        Admin admin = VSPreference.getInstance(context).getAdmin();
        if (admin != null) {
            tvName.setText("Bpk. " + admin.getName());
            tvPhoneNum.setText(admin.getPhoneNumber());
        }
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
        fragmentListener.onFragmentCreated(CitizenFragment.this, previousFragment);
        customLoadingDialog = new CustomLoadingDialog(context);
    }

    private void initListener() {
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onFragmentFinish(CitizenFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_INPUT_USER, true);
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onFragmentFinish(CitizenFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_ADMIN_PROFILE, true);
            }
        });
        constraintContainer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onFragmentFinish(CitizenFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_CITIZEN_LIST, true);
            }
        });
    }

    private void setUpData() {
        ArrayList<Object> kkObjList = VSPreference.getInstance(context).getKKList();
        if (!kkObjList.isEmpty()) {
            for (int i = 0; i < kkObjList.size(); i++) {
                Log.d("XXXLOG", "onViewCreated - list: " + new Gson().toJson(kkObjList.get(i)));
            }
        }

        //set data to layout
        String wargaTetap = kkObjList.size() + " jiwa";
        tvJWT1.setText(wargaTetap);
        tvJWT2.setText(wargaTetap);
    }

    private void prepareLayout() {
        showOverlay(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                linearMiddle.setVisibility(View.VISIBLE);
                linearMainMenu.setVisibility(View.VISIBLE);
                addUserButton.setVisibility(View.VISIBLE);
                cvInfoLeader.setVisibility(View.VISIBLE);
                cvInfoWargaBaru.setVisibility(View.VISIBLE);
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
}
package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.villageservice.R;
import com.example.villageservice.activity.AdminActivity;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.utility.VSPreference;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CitizenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CitizenFragment extends Fragment {

    private Context context;
    private FragmentListener fragmentListener;

    private View view;
    private Button addUserButton;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_citizen, container, false);
        addUserButton = view.findViewById(R.id.addUserButton);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onFragmentFinish(CitizenFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_INPUT_USER, true);
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

    }

    private void initListener() {

    }

    private void setFonts() {

    }

    private List<Object> retrieveDataKK() {
        return VSPreference.getInstance(context).getKKList();
    }

}
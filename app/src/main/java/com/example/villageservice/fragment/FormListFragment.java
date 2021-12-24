package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.villageservice.adapter.FormListAdapter;
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
public class FormListFragment extends Fragment {

    private Context context;
    private View view;
    private ListView listForm;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_form_list, container, false);
        tvPageTitle = view.findViewById(R.id.tvTitlePage);
        buttonLeft = view.findViewById(R.id.buttonLeft);
        buttonRight = view.findViewById(R.id.buttonRight);

        tvPageTitle.setTypeface(new Fonts(context).rBoldExtra());
        tvPageTitle.setTextColor(context.getResources().getColor(R.color.white));
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvPageTitle, 14, 24, 1, TypedValue.COMPLEX_UNIT_SP);

        List<Object> objListUser = VSPreference.getInstance(context).loadUserList();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < objListUser.size(); i++) {
            User user = (User) objListUser.get(i);
            users.add(user);
        }
        FormListAdapter formListAdapter = new FormListAdapter(context, users);
        listForm = view.findViewById(R.id.listForm);
        listForm.setAdapter(formListAdapter);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
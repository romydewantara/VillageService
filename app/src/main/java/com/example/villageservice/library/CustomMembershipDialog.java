package com.example.villageservice.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.villageservice.R;
import com.example.villageservice.listener.CustomMembershipDialogListener;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.VSPreference;

public class CustomMembershipDialog extends DialogFragment {

    private TextView titleAlert;
    private TextView positiveButton;
    private TextView negativeButton;
    private ImageView minButton;
    private ImageView plusButton;
    private TextView tvTotal;

    private CharSequence pButtonText = "";
    private CharSequence nButtonText = "";
    private boolean shown = false;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String textTitle;

    private int total = 1;
    private CustomMembershipDialogListener customMembershipDialogListener;

    public CustomMembershipDialog() {

    }

    public static CustomMembershipDialog newInstance(Context context, String title) {
        CustomMembershipDialog fragment = new CustomMembershipDialog();
        Bundle arguments = new Bundle();
        mContext = context;
        textTitle = title;

        arguments.putString("title", title);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_membership_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_cad_rounded));
        }
        titleAlert = view.findViewById(R.id.titleAlert);
        negativeButton = view.findViewById(R.id.negativeButton);
        positiveButton = view.findViewById(R.id.positiveButton);
        minButton = view.findViewById(R.id.minButton);
        plusButton = view.findViewById(R.id.plusButton);
        tvTotal = view.findViewById(R.id.tvTotal);

        titleAlert.setText(textTitle);

        initListener();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (mContext.getApplicationContext() instanceof CustomMembershipDialogListener) {
            customMembershipDialogListener = (CustomMembershipDialogListener) mContext;
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        if (shown) return;
        try {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(this, tag);
            fragmentTransaction.commitAllowingStateLoss();
            shown = true;
        } catch (IllegalStateException e) {
            Log.d("InputUserDialog", "Exception: ", e);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        shown = false;
        super.onDismiss(dialog);
    }

    private void initListener() {
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TOTAL", "onClick total: " + total);
                customMembershipDialogListener.onButtonPositivePressed(total);
                dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customMembershipDialogListener.onButtonNegativePressed();
                dismiss();
            }
        });
        minButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total--;
                if (total <= 0) {
                    total = 0;
                }
                tvTotal.setText(String.valueOf(total));
            }
        });
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                total++;
                if (total >= 15) {
                    total = 15;
                }
                tvTotal.setText(String.valueOf(total));
            }
        });
    }

    public CustomMembershipDialog setButton(CharSequence positiveText, CharSequence negativeText, CustomMembershipDialogListener listener) {
        pButtonText = positiveText;
        nButtonText = negativeText;
        this.customMembershipDialogListener = listener;
        return this;
    }
}
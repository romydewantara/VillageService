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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.villageservice.R;
import com.example.villageservice.listener.InputHeaderDialogListener;

public class InputHeaderDialog extends DialogFragment {

    private TextView titleAlert;
    private TextView messageAlert;
    private TextView positiveButton;
    private EditText etHeaderCode;
    private boolean shown = false;

    private CharSequence buttonText = "";

    private InputHeaderDialogListener inputHeaderDialogListener;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private static String textTitle = "";

    public InputHeaderDialog() {

    }

    public static InputHeaderDialog newInstance(Context context, String title) {
        InputHeaderDialog frag = new InputHeaderDialog();
        Bundle args = new Bundle();
        mContext = context;
        textTitle = title;

        args.putString("title", title);
        frag.setArguments(args);
        return frag;
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
        return inflater.inflate(R.layout.layout_input_header_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCancelable(false);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.bg_cad_rounded));
        }

        titleAlert = view.findViewById(R.id.titleAlert);
        messageAlert = view.findViewById(R.id.messageAlert);
        positiveButton = view.findViewById(R.id.submitButton);
        etHeaderCode = view.findViewById(R.id.etHeaderCode);
        setCosmetic();
        initListener();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d("Xnull", "onAttach - context: " + mContext);
        if (mContext.getApplicationContext() instanceof InputHeaderDialogListener) {
            inputHeaderDialogListener = (InputHeaderDialogListener) mContext;
        }
        Log.d("Xnull", "onAttach - customDialogListener: " + inputHeaderDialogListener);
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
            Log.d("InputHeaderDialog", "Exception: ", e);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        shown = false;
        super.onDismiss(dialog);
    }

    private void setCosmetic() {
        if (!textTitle.equals("")) {
            titleAlert.setVisibility(View.VISIBLE);
        }

        titleAlert.setText(textTitle);
        setButton();
    }

    private void initListener() {
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etHeaderCode.getText().toString().isEmpty()) {
                    inputHeaderDialogListener.onButtonSubmit(etHeaderCode.getText().toString());
                    dismiss();
                }
            }
        });
    }

    private void setButton() {
        if (!buttonText.equals("")) {
            positiveButton.setText(buttonText);
            positiveButton.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_cad_single_button, null));
            positiveButton.setVisibility(View.VISIBLE);
        }
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    public InputHeaderDialog setButton(CharSequence positiveText, InputHeaderDialogListener listener) {
        buttonText = positiveText;
        this.inputHeaderDialogListener = listener;
        return this;
    }
}

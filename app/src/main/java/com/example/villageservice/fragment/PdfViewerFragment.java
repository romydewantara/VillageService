package com.example.villageservice.fragment;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.villageservice.R;
import com.example.villageservice.library.InputHeaderDialog;
import com.example.villageservice.library.ZoomableRelativeLayout;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.listener.InputHeaderDialogListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.PortableDocumentFormat;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PdfViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PdfViewerFragment extends Fragment {

    private Context context;
    private FragmentListener fragmentListener;
    private PortableDocumentFormat portableDocumentFormat;
    private CoveringLetter coveringLetter;
    private LottieAnimationView lottieStampLoading;

    private View view;
    private RelativeLayout relativeStamp;
    private RelativeLayout relativePdf;
    private ZoomableRelativeLayout layoutPdf;
    private ImageView backButton;
    private Button pdfViewerButton;
    private AppCompatTextView tvLampiran;
    private AppCompatTextView tvNumber;
    private AppCompatTextView tvNumberFooterR;
    private AppCompatTextView tvTanggalFooterR;
    private AppCompatTextView tvRWName;
    private AppCompatTextView tvTanggalRT;
    private AppCompatTextView tvRTName;
    private ScaleGestureDetector scaleGestureDetector;

    private AppCompatTextView tvNameR1;
    private AppCompatTextView tvNameR2;
    private AppCompatTextView tvNameR3;
    private AppCompatTextView tvNameR4;
    private AppCompatTextView tvNameR5;
    private AppCompatTextView tvNameR6;
    private AppCompatTextView tvNameR7;
    private AppCompatTextView tvNameR8;
    private AppCompatTextView tvNameR19;
    private AppCompatTextView tvNameR10;

    private AppCompatTextView tv1;
    private AppCompatTextView tv2a;
    private AppCompatTextView tv2b;
    private AppCompatTextView tv3;
    private AppCompatTextView tv4;

    private ImageView imageSignature;
    private Animation bouncedShow;
    private Animation fadeIn;

    private boolean isAdmin = false;
    private ArrayList<Object> coveringLettersList;

    private String previousFragment = "";
    private String clType;

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

    public PdfViewerFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PdfViewerFragment newInstance(String param1, String param2) {
        PdfViewerFragment fragment = new PdfViewerFragment();
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
            Log.d("XXXLOG", "onCreate - cl: " + getArguments().getString(ConstantVariable.KEY_CL_BUNDLE));
            switch (getArguments().getString(ConstantVariable.KEY_CL_BUNDLE)) {
                case ConstantVariable.KEY_CL_NIKAH:
                    clType = ConstantVariable.KEY_CL_NIKAH;
                    break;
                case ConstantVariable.KEY_CL_UMKM:
                    clType = ConstantVariable.KEY_CL_UMKM;
                    break;
                case ConstantVariable.KEY_CL_DOMISILI_KTP:
                    clType = ConstantVariable.KEY_CL_DOMISILI_KTP;
                    break;
                case ConstantVariable.KEY_CL_KK_BARU:
                    clType = ConstantVariable.KEY_CL_KK_BARU;
                    break;
                case ConstantVariable.KEY_CL_AKTA_LAHIR:
                    clType = ConstantVariable.KEY_CL_AKTA_LAHIR;
                    break;
                case ConstantVariable.KEY_CL_AKTA_KEMATIAN:
                    clType = ConstantVariable.KEY_CL_AKTA_KEMATIAN;
                    break;
            }
        }
        fragmentListener.onFragmentCreated(PdfViewerFragment.this, previousFragment);
        portableDocumentFormat = new PortableDocumentFormat(context);
        coveringLettersList = new ArrayList<>();
        scaleGestureDetector = new ScaleGestureDetector(context, new OnPinchListener());
        bouncedShow = AnimationUtils.loadAnimation(context, R.anim.button_bounched);
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_preview_pdf, container, false);
        backButton = view.findViewById(R.id.backButton);
        layoutPdf = view.findViewById(R.id.layout_pdf);
        relativePdf = view.findViewById(R.id.relativePdf);
        pdfViewerButton = view.findViewById(R.id.pdfViewerButton);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (VSPreference.getInstance(context).getRole().equalsIgnoreCase("admin")) {
            isAdmin = true;
            pdfViewerButton.setText("Setuju");
        } else {
            pdfViewerButton.setText("Download");
        }
        loadData();
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

    private void initView() {
        tvLampiran = view.findViewById(R.id.tvLampiran);
        tvNumber = view.findViewById(R.id.tvNumber);
        tvNumberFooterR = view.findViewById(R.id.tvNumberFooterR);
        tvTanggalFooterR = view.findViewById(R.id.tvTanggalFooterR);
        tvRWName = view.findViewById(R.id.tvRWName);
        tvTanggalRT = view.findViewById(R.id.tvTanggalRT);
        tvRTName = view.findViewById(R.id.tvRTName);
        tvNameR1 = view.findViewById(R.id.tvNameR1);
        tvNameR2 = view.findViewById(R.id.tvNameR2);
        tvNameR3 = view.findViewById(R.id.tvNameR3);
        tvNameR4 = view.findViewById(R.id.tvNameR4);
        tvNameR5 = view.findViewById(R.id.tvNameR5);
        tvNameR6 = view.findViewById(R.id.tvNameR6);
        tvNameR7 = view.findViewById(R.id.tvNameR7);
        tvNameR8 = view.findViewById(R.id.tvNameR8);
        tvNameR19 = view.findViewById(R.id.tvNameR9);
        tvNameR10 = view.findViewById(R.id.tvNameR10);
        lottieStampLoading = view.findViewById(R.id.lottieStampLoading);

        tv1 = view.findViewById(R.id.tv1);
        tv2a = view.findViewById(R.id.tv2a);
        tv2b = view.findViewById(R.id.tv2b);
        tv3 = view.findViewById(R.id.tv3);
        tv4 = view.findViewById(R.id.tv4);

        relativeStamp = view.findViewById(R.id.relativeStamp);
        imageSignature = view.findViewById(R.id.imageSignature);
    }

    private void loadData() {
        coveringLetter = VSPreference.getInstance(context).getCoveringLetter(ConstantVariable.KEY_COVERING_LETTER);
        Log.d("XXXCL", "loadData - data: " + new Gson().toJson(coveringLetter));
        tvLampiran.setText(coveringLetter.getClLampiran());
        tvNumber.setText(coveringLetter.getClNomorHeader());
        tvNumberFooterR.setText(coveringLetter.getClNomorFooter());
        tvTanggalFooterR.setText(coveringLetter.getClTanggalFooterRw());
        tvRWName.setText(coveringLetter.getClNamaRw());
        tvTanggalRT.setText(coveringLetter.getClTanggalRt());
        tvRTName.setText(coveringLetter.getClNamaRt());
        tvNameR1.setText(coveringLetter.getClNama());
        tvNameR2.setText(coveringLetter.getClJenisKelamin());
        tvNameR3.setText(coveringLetter.getClTempatTanggalLahir());
        tvNameR4.setText(coveringLetter.getClPekerjaan());
        tvNameR5.setText(coveringLetter.getClKtp());
        tvNameR6.setText(coveringLetter.getClKewarganegaraan());
        tvNameR7.setText(coveringLetter.getClPendidikan());
        tvNameR8.setText(coveringLetter.getClAgama());
        tvNameR19.setText(coveringLetter.getClAlamat());
        tvNameR10.setText(coveringLetter.getClKeperluan());

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvNumberFooterR, 1, 10, 1, TypedValue.COMPLEX_UNIT_SP);

        /** STAMP */
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv1, 1, 16, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv2a, 1, 8, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv2b, 1, 8, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv3, 1, 16, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tv4, 1, 16, 1, TypedValue.COMPLEX_UNIT_SP);
        if (coveringLetter.isApproved()) {
            relativeStamp.setVisibility(View.VISIBLE);
            imageSignature.setVisibility(View.VISIBLE);
            if (isAdmin) {
                disableButton();
            }
        } else {
            if (!isAdmin) {
                disableButton();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        pdfViewerButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                if (isAdmin && !coveringLetter.isApproved()) {
                    FragmentManager fm = getFragmentManager();
                    InputHeaderDialog customAlertDialog = InputHeaderDialog.newInstance(context, "Masukkan Nomor\nSurat Pengantar")
                            .setButton("Tanda Tangan", new InputHeaderDialogListener() {
                                @Override
                                public void onButtonSubmit(String headerCode) {
                                    tvNumber.setText("Nomor: " + headerCode);
                                    tvNumberFooterR.setText(headerCode);
                                    tvNumber.setAnimation(bouncedShow);
                                    tvNumberFooterR.setAnimation(bouncedShow);
                                    coveringLetter.setClNomorHeader(headerCode);
                                    coveringLetter.setClNomorFooter(headerCode);
                                    coveringLetter.setApproved(true);
                                    approved();
                                }
                            });
                    if (fm != null) {
                        customAlertDialog.show(fm, "input_header_dialog");
                    }
                } else {
                    if (coveringLetter.isApproved()) {
                        relativeStamp.setVisibility(View.VISIBLE);
                        imageSignature.setVisibility(View.VISIBLE);
                        portableDocumentFormat.generatePdf(relativePdf, coveringLetter.getClNama().trim() + "_" + coveringLetter.getClKeperluan().trim());
                    }
                }
                disableButton();
            }
        });
        layoutPdf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("XXXLOG", "onTouch - pdfFile");
                scaleGestureDetector.onTouchEvent(event);
                return true;
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentListener.onActivityBackPressed();
            }
        });

    }

    @SuppressLint("NewApi")
    private void disableButton() {
        pdfViewerButton.setBackgroundResource(R.drawable.bg_button_disabled);
        pdfViewerButton.setTextColor(Color.parseColor("#A9A9A9"));
        pdfViewerButton.setEnabled(false);
    }

    private void approved() {
        lottieStampLoading.setVisibility(View.VISIBLE);
        lottieStampLoading.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                lottieStampLoading.setVisibility(View.GONE);
                relativeStamp.setVisibility(View.VISIBLE);
                imageSignature.setVisibility(View.VISIBLE);
                relativeStamp.startAnimation(bouncedShow);
                imageSignature.startAnimation(fadeIn);

                saveChanges();
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });
    }

    private void saveChanges() {
        ArrayList<Object> clArrayList = VSPreference.getInstance(context).getCoveringLetterGroupList(clType);
        for (int i = 0; i < clArrayList.size(); i++) {
            CoveringLetter clTemp = (CoveringLetter) clArrayList.get(i);
            if (clTemp.getClKtp().equalsIgnoreCase(coveringLetter.getClKtp()) && clTemp.getClId().equalsIgnoreCase(coveringLetter.getClId())) {
                clArrayList.set(i, coveringLetter);
                Log.d("XXXLOG", "saveChanges - cl: " + new Gson().toJson(coveringLetter));
            }
        }
        VSPreference.getInstance(context).setCoveringLetterGroupList(clType, clArrayList);
        updateCLNotifications();
    }

    private void updateCLNotifications() {
        ArrayList<Object> notificationCLList = VSPreference.getInstance(context).getCoveringLetterGroupList(ConstantVariable.KEY_COVERING_LETTERS_LIST);
        for (int i = 0; i < notificationCLList.size(); i++) {
            CoveringLetter clTemp = (CoveringLetter) notificationCLList.get(i);
            if (clTemp.getClKtp().equalsIgnoreCase(coveringLetter.getClKtp()) &&
                    clTemp.getClId().equalsIgnoreCase(coveringLetter.getClId())) {
                notificationCLList.set(i, coveringLetter);
            }
        }
        VSPreference.getInstance(context).setCoveringLetterGroupList(ConstantVariable.KEY_COVERING_LETTERS_LIST, notificationCLList);
    }

    private class OnPinchListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        float startingSpan;
        float endSpan;
        float startFocusX;
        float startFocusY;

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            layoutPdf.scale(detector.getCurrentSpan() / startingSpan, startFocusX, startFocusY);
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            startingSpan = detector.getCurrentSpan();
            startFocusX = detector.getFocusX();
            startFocusY = detector.getFocusY();
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            layoutPdf.restore();
        }
    }
}
package com.example.villageservice.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.villageservice.R;
import com.example.villageservice.activity.AdminActivity;
import com.example.villageservice.activity.SignInActivity;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.Fonts;
import com.example.villageservice.utility.VSPreference;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeAdminFragment extends Fragment {

    private Context context;
    private CustomLoadingDialog customLoadingDialog;

    private RelativeLayout overlay;
    private RelativeLayout relativeSignOut;
    private ConstraintLayout constraintContainer;
    private ConstraintLayout constraintHeader;
    private View view;
    private ImageView signOutButton;
    private AppCompatTextView tvHeader;
    private AppCompatTextView tvContentHeader;
    private AppCompatTextView tvInfoTitle1;
    private AppCompatTextView tvInfoTitle2;
    private AppCompatTextView tvInfoTitle3;
    private AppCompatTextView tvInfoTitle4;
    private AppCompatTextView tvInfoTitle5;
    private AppCompatTextView tvInfoTitle6;
    private CardView cvInfo1;
    private CardView cvInfo2;
    private CardView cvInfo3;
    private CardView cvInfo4;
    private CardView cvInfo5;
    private CardView cvInfo6;
    private CardView cvBanner;
    private ImageSwitcher imageSwitcher;

    private FragmentListener fragmentListener;

    private final int[] images = {
            R.drawable.banner_one,
            R.drawable.banner_two,
            R.drawable.banner_three,
            R.drawable.banner_four
    };
    private Handler imageSwitcherHandler;
    private Runnable imageSwitcherRunnable;

    private Animation imgAnimationIn;
    private Animation imgAnimationOut;

    private int currentIndex = -1;

    private String previousFragment = "";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public void addPreviousFragmentTag(String previousFragment) {
        Log.d("PREVIOUS", "previousFragmentTag: " + previousFragment);
        this.previousFragment = previousFragment;
    }

    public HomeAdminFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeAdminFragment newInstance(String param1, String param2) {
        HomeAdminFragment fragment = new HomeAdminFragment();
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
        iniMandatory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_admin, container, false);
        fragmentListener.onFragmentCreated(this, previousFragment);
        constraintContainer = view.findViewById(R.id.constraintContainer);
        constraintHeader = view.findViewById(R.id.constraintHeader);
        signOutButton = view.findViewById(R.id.signOutButton);
        relativeSignOut = view.findViewById(R.id.relativeSignOut);
        tvHeader = view.findViewById(R.id.tvHeader);
        tvContentHeader = view.findViewById(R.id.tvContentHeader);
        tvInfoTitle1 = view.findViewById(R.id.tvInfoTitle1);
        tvInfoTitle2 = view.findViewById(R.id.tvInfoTitle2);
        tvInfoTitle3 = view.findViewById(R.id.tvInfoTitle3);
        tvInfoTitle4 = view.findViewById(R.id.tvInfoTitle4);
        tvInfoTitle5 = view.findViewById(R.id.tvInfoTitle5);
        tvInfoTitle6 = view.findViewById(R.id.tvInfoTitle6);
        cvInfo1 = view.findViewById(R.id.cvInfo1);
        cvInfo2 = view.findViewById(R.id.cvInfo2);
        cvInfo3 = view.findViewById(R.id.cvInfo3);
        cvInfo4 = view.findViewById(R.id.cvInfo4);
        cvInfo5 = view.findViewById(R.id.cvInfo5);
        cvInfo6 = view.findViewById(R.id.cvInfo6);
        cvBanner = view.findViewById(R.id.cvBanner);
        imageSwitcher = view.findViewById(R.id.imageSwitcher);
        overlay = view.findViewById(R.id.overlay);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setFonts();
        initListener();
        prepareLayout();
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

    private void iniMandatory() {
        customLoadingDialog = new CustomLoadingDialog(context);
        imgAnimationIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        imgAnimationIn.setDuration(1200);
        imgAnimationOut = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
        imgAnimationOut.setDuration(1200);
    }

    private void initListener() {
        cvInfo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_NIKAH);
            }
        });
        cvInfo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_UMKM);
                //Toast.makeText(context, "Permintaan Persetujuan Surat Pengantar UMKM", Toast.LENGTH_SHORT).show();
            }
        });
        cvInfo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_DOMISILI_KTP);
                //Toast.makeText(context, "Permintaan Persetujuan Formulir Pindah Rumah", Toast.LENGTH_SHORT).show();
            }
        });
        cvInfo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_KK_BARU);
                //Toast.makeText(context, "Permintaan Persetujuan Surat Pengantar Pembuatan Kartu Keluarga", Toast.LENGTH_SHORT).show();
            }
        });
        cvInfo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_AKTA_LAHIR);
                //Toast.makeText(context, "Permintaan Persetujuan Surat Pengantar Pembuatan Akta Lahir", Toast.LENGTH_SHORT).show();
            }
        });
        cvInfo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_AKTA_KEMATIAN);
                //Toast.makeText(context, "Permintaan Persetujuan Pembuatan Surat Kematian", Toast.LENGTH_SHORT).show();
            }
        });
        relativeSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("", "Apakah anda yakin anda ingin keluar?", "Ya", "Batal");
            }
        });
    }

    private void setFonts() {
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvHeader, 1, 24, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvContentHeader, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvInfoTitle1, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvInfoTitle2, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvInfoTitle3, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvInfoTitle4, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvInfoTitle5, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvInfoTitle6, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
    }

    private Runnable getDurationRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                currentIndex++;
                if (currentIndex == images.length) currentIndex = 0;
                imageSwitcher.setImageResource(images[currentIndex]);
            }
        };
    }

    private void prepareLayout() {
        showOverlay(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation slideUp = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                tvHeader.setVisibility(View.VISIBLE);
                cvBanner.startAnimation(slideUp);
                cvBanner.setVisibility(View.VISIBLE);
                tvContentHeader.setVisibility(View.VISIBLE);
                constraintContainer.setVisibility(View.VISIBLE);

                imageSwitcher.setInAnimation(imgAnimationIn);
                imageSwitcher.setOutAnimation(imgAnimationOut);
                imageSwitcher.setVisibility(View.VISIBLE);
                imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
                    @Override
                    public View makeView() {
                        ImageView imageView = new ImageView(context);
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        return imageView;
                    }
                });

                imageSwitcherHandler = new Handler();
                imageSwitcherRunnable = getDurationRunnable();
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        imageSwitcherHandler.post(imageSwitcherRunnable);
                    }
                };
                timer.scheduleAtFixedRate(timerTask, 0, 4500);

                showOverlay(false);
            }
        }, 1800);
    }

    private void stopImageSwitcher() {
        imageSwitcherHandler.removeCallbacks(imageSwitcherRunnable);
    }

    private void goToNextFragment(String menuSelected) {
        fragmentListener.onFragmentPassingData(menuSelected);
        fragmentListener.onFragmentFinish(HomeAdminFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_FORM_LIST, true);
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

    private void showCustomDialog(String title, String message, String pButton, String nButton) {
        FragmentManager fm = getFragmentManager();
        CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance(context, title, message)
                .setButton(pButton, nButton, new CustomAlertDialogListener() {
                    @Override
                    public void onNegativePressed() {}
                    @Override
                    public void onPositivePressed() {
                        VSPreference.getInstance(context).logout();
                        startActivity(new Intent(context, SignInActivity.class));
                        fragmentListener.onActivityFinish();
                    }
                });
        if (fm != null) {
            customAlertDialog.show(fm, "custom_alert_dialog");
        }
    }
}
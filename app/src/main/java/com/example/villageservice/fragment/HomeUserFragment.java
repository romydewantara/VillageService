package com.example.villageservice.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.example.villageservice.R;
import com.example.villageservice.activity.SignInActivity;
import com.example.villageservice.activity.UserActivity;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.Fonts;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeUserFragment extends Fragment {

    private Context context;
    private CustomLoadingDialog customLoadingDialog;
    private FragmentListener fragmentListener;
    private KartuKeluarga kartuKeluarga;
    private View view;

    private Handler imageSwitcherHandler;
    private Runnable imageSwitcherRunnable;
    private ImageSwitcher imageSwitcher;

    private Animation imgAnimationIn;
    private Animation imgAnimationOut;

    private RelativeLayout overlay;
    private AppCompatTextView tvKKNumber;
    private CardView cvInfo1;
    private CardView cvInfo2;
    private CardView cvInfo3;
    private CardView cvInfo4;
    private CardView cvInfo5;
    private CardView cvInfo6;

    private ImageView signOutButton;
    private ConstraintLayout layoutSpace;
    private ConstraintLayout constraintContainer;
    private CardView cvBanner;

    private int currentIndex = -1;
    private final int[] images = {
            R.drawable.banner_one,
            R.drawable.banner_two,
            R.drawable.banner_three,
            R.drawable.banner_four
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeUserFragment newInstance(String param1, String param2) {
        HomeUserFragment fragment = new HomeUserFragment();
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
        view = inflater.inflate(R.layout.fragment_home_user, container, false);
        tvKKNumber = view.findViewById(R.id.tvKKNumber);
        cvInfo1 = view.findViewById(R.id.cvInfo1);
        cvInfo2 = view.findViewById(R.id.cvInfo2);
        cvInfo3 = view.findViewById(R.id.cvInfo3);
        cvInfo4 = view.findViewById(R.id.cvInfo4);
        cvInfo5 = view.findViewById(R.id.cvInfo5);
        cvInfo6 = view.findViewById(R.id.cvInfo6);
        overlay = view.findViewById(R.id.overlay);

        imageSwitcher = view.findViewById(R.id.imageSwitcher);
        signOutButton = view.findViewById(R.id.signOutButton);
        layoutSpace = view.findViewById(R.id.layoutSpace);
        constraintContainer = view.findViewById(R.id.constraintContainer);
        cvBanner = view.findViewById(R.id.cvBanner);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prepareLayout();
        String textKK = "Nomor Kartu Keluarga:\n" + kartuKeluarga.getIdKartuKeluarga();
        tvKKNumber.setText(textKK);
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
            }
        });
        cvInfo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_DOMISILI_KTP);
            }
        });
        cvInfo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_KK_BARU);
            }
        });
        cvInfo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_AKTA_LAHIR);
            }
        });
        cvInfo6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextFragment(ConstantVariable.KEY_CL_AKTA_KEMATIAN);
            }
        });
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog("", "Apakah anda yakin anda ingin keluar?", "Ya", "Batal");
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
        fragmentListener.onFragmentCreated(HomeUserFragment.this);
        customLoadingDialog = new CustomLoadingDialog(context);
        imgAnimationIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        imgAnimationIn.setDuration(1200);
        imgAnimationOut = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
        imgAnimationOut.setDuration(1200);

        //fetch family registered
        kartuKeluarga = VSPreference.getInstance(context).getKK();
        Log.d("XXXLOG", "onCreate - KK logged in: " + new Gson().toJson(kartuKeluarga));
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
                signOutButton.setVisibility(View.VISIBLE);
                layoutSpace.setVisibility(View.VISIBLE);
                constraintContainer.setVisibility(View.VISIBLE);
                cvBanner.setVisibility(View.VISIBLE);
                //cvBanner.startAnimation(slideUp);
                showOverlay(false);
            }
        }, 1800);
    }

    private void goToNextFragment(String menuSelected) {
        fragmentListener.onFragmentPassingData(menuSelected);
        fragmentListener.onFragmentFinish(HomeUserFragment.this, UserActivity.FRAGMENT_FINISH_GOTO_ENTRY, true);
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
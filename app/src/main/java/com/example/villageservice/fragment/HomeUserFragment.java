package com.example.villageservice.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.villageservice.R;
import com.example.villageservice.activity.SignInActivity;
import com.example.villageservice.activity.UserActivity;
import com.example.villageservice.library.CustomAlertDialog;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.CustomAlertDialogListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.Admin;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;
import com.google.gson.Gson;

import java.net.URLEncoder;

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
    private Admin admin;
    private View view;

    private Animation imgAnimationIn;
    private Animation imgAnimationOut;

    private RelativeLayout overlay;
    private RelativeLayout relativeSignOut;
    private AppCompatTextView tvKKNumber;
    private AppCompatTextView tvKKKep;
    private AppCompatTextView tvWhatsapp;
    private AppCompatTextView tvCall;
    private CardView cvInfo1;
    private CardView cvInfo2;
    private CardView cvInfo3;
    private CardView cvInfo4;
    private CardView cvInfo5;
    private CardView cvInfo6;

    private ImageView imageWhatsapp;
    private ImageView imageCall;
    private ImageView signOutButton;
    private ConstraintLayout constraintContact;
    private ConstraintLayout layoutSpace;
    private ConstraintLayout constraintContainer;

    Animation slideUp;
    Animation animContact;
    Animation clickEffect;
    private int currentIndex = -1;
    private final int[] images = {
            R.drawable.banner_one,
            R.drawable.banner_two,
            R.drawable.banner_three,
            R.drawable.banner_four
    };

    private String previousFragment = "";
    private String phoneNumber;

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

    public HomeUserFragment() {
        // Required empty public constructor
    }

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
        tvKKKep = view.findViewById(R.id.tvKKKep);
        tvWhatsapp = view.findViewById(R.id.tvWhatsapp);
        tvCall = view.findViewById(R.id.tvCall);
        cvInfo1 = view.findViewById(R.id.cvInfo1);
        cvInfo2 = view.findViewById(R.id.cvInfo2);
        cvInfo3 = view.findViewById(R.id.cvInfo3);
        cvInfo4 = view.findViewById(R.id.cvInfo4);
        cvInfo5 = view.findViewById(R.id.cvInfo5);
        cvInfo6 = view.findViewById(R.id.cvInfo6);
        overlay = view.findViewById(R.id.overlay);

        signOutButton = view.findViewById(R.id.signOutButton);
        relativeSignOut = view.findViewById(R.id.relativeSignOut);
        layoutSpace = view.findViewById(R.id.layoutSpace);
        constraintContact = view.findViewById(R.id.constraintContact);
        imageWhatsapp = view.findViewById(R.id.imageWhatsapp);
        imageCall = view.findViewById(R.id.imageCall);
        constraintContainer = view.findViewById(R.id.constraintContainer);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prepareLayout();
        String textKK = kartuKeluarga.getIdKartuKeluarga();
        String textKep = kartuKeluarga.getNamaKepalaKeluarga();
        tvKKNumber.setText(textKK);
        tvKKKep.setText(textKep);
        admin = VSPreference.getInstance(context).getAdmin();
        if (admin != null) {
            tvWhatsapp.setText("WhatsApp\n" + admin.getPhoneNumber());
            tvCall.setText("Panggil\n" + admin.getPhoneNumber());
            phoneNumber = admin.getPhoneNumber();
        } else {
            phoneNumber = "+6281932634438";
        }
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvKKNumber, 1,14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvKKKep, 1,14, 1, TypedValue.COMPLEX_UNIT_SP);

        cvInfo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cvInfo1.startAnimation(clickEffect);
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
        imageWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageWhatsapp.startAnimation(animContact);
                String message = "Assalamu'alaikum, permisi Bapak/Ibu RT, maaf mengganggu waktunya.\nSaya dari keluarga Bpk. " + kartuKeluarga.getNamaKepalaKeluarga() + " ingin bertanya.";
                try{
                    PackageManager packageManager = getActivity().getPackageManager();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    String url = "https://api.whatsapp.com/send?phone="+ phoneNumber +"&text=" + URLEncoder.encode(message, "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }else {
                        Toast.makeText(context, "Aplikasi Whatsapp belum terinstal di Handphone Anda", Toast.LENGTH_SHORT).show();
                    }
                } catch(Exception e) {
                    Log.e("ERROR WHATSAPP",e.toString());
                    Toast.makeText(context, "Aplikasi Whatsapp belum terinstal di Handphone Anda", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCall.startAnimation(animContact);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });
        relativeSignOut.setOnClickListener(new View.OnClickListener() {
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
        fragmentListener.onFragmentCreated(HomeUserFragment.this, previousFragment);
        customLoadingDialog = new CustomLoadingDialog(context);
        imgAnimationIn = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        imgAnimationIn.setDuration(1200);
        imgAnimationOut = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
        imgAnimationOut.setDuration(1200);
        slideUp = AnimationUtils.loadAnimation(context, R.anim.bounched_show);
        clickEffect = AnimationUtils.loadAnimation(context, R.anim.button_bounched);
        animContact = AnimationUtils.loadAnimation(context, R.anim.small_button_bounched);

        //fetch family registered
        kartuKeluarga = VSPreference.getInstance(context).getKK();
        Log.d("XXXLOG", "HomeUser (onCreate) - current KK signed in: " + new Gson().toJson(kartuKeluarga));
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
                signOutButton.setVisibility(View.VISIBLE);
                layoutSpace.setVisibility(View.VISIBLE);
                constraintContainer.setVisibility(View.VISIBLE);
                constraintContact.setVisibility(View.VISIBLE);
                constraintContact.startAnimation(slideUp);
                showOverlay(false);
            }
        }, 1800);
    }

    private void goToNextFragment(String menuSelected) {
        fragmentListener.onFragmentPassingData(menuSelected);
        fragmentListener.onFragmentFinish(HomeUserFragment.this, UserActivity.FRAGMENT_FINISH_GOTO_ENTRY, true);
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
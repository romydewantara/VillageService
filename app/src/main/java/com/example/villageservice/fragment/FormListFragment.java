package com.example.villageservice.fragment;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.activity.AdminActivity;
import com.example.villageservice.activity.UserActivity;
import com.example.villageservice.adapter.FormListAdapter;
import com.example.villageservice.library.CustomLoadingDialog;
import com.example.villageservice.listener.FormUserRequestedListener;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.utility.ConstantVariable;
import com.example.villageservice.utility.VSPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FormListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FormListFragment extends Fragment implements FormUserRequestedListener {

    private Context context;
    private FragmentListener fragmentListener;
    private CustomLoadingDialog customLoadingDialog;

    private ConstraintLayout constraintEmptyData;
    private View view;
    private ListView listForm;
    private ImageView buttonLeft;
    private RelativeLayout overlay;
    private AppCompatTextView tvPageTitle;
    private AppCompatTextView tvSubTitle;
    private AppCompatTextView tvEmptyData;

    private List<CoveringLetter> coveringLetters;
    private KartuKeluarga kartuKeluarga;
    private ArrayList<Object> coveringLetterArrayList;

    private Animation fadeIn;

    private String menuSelected;
    private String clType;
    private boolean isAdmin = false;
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
            switch (getArguments().getString(ConstantVariable.KEY_CL_BUNDLE)) {
                case ConstantVariable.KEY_CL_NIKAH:
                    clType = ConstantVariable.KEY_CL_NIKAH;
                    menuSelected = "Mengajukan Surat Pengantar Layak Nikah";
                    break;
                case ConstantVariable.KEY_CL_UMKM:
                    clType = ConstantVariable.KEY_CL_UMKM;
                    menuSelected = "Mengajukan Surat Keterangan Usaha (UMKM)";
                    break;
                case ConstantVariable.KEY_CL_DOMISILI_KTP:
                    clType = ConstantVariable.KEY_CL_DOMISILI_KTP;
                    menuSelected = "Mengajukan Surat Pindah Domisili KTP";
                    break;
                case ConstantVariable.KEY_CL_KK_BARU:
                    clType = ConstantVariable.KEY_CL_KK_BARU;
                    menuSelected = "Mengajukan Surat Pengantar Pembuatan KK Baru";
                    break;
                case ConstantVariable.KEY_CL_AKTA_LAHIR:
                    clType = ConstantVariable.KEY_CL_AKTA_LAHIR;
                    menuSelected = "Mengajukan Surat Pengantar Akta Kelahiran";
                    break;
                case ConstantVariable.KEY_CL_AKTA_KEMATIAN:
                    clType = ConstantVariable.KEY_CL_AKTA_KEMATIAN;
                    menuSelected = "Mengajukan Surat Pengantar Akta Kematian";
                    break;
            }
        }
        initMandatory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_form_list, container, false);
        tvPageTitle = view.findViewById(R.id.tvTitlePage);
        tvSubTitle = view.findViewById(R.id.tvSubTitle);
        buttonLeft = view.findViewById(R.id.buttonLeft);
        listForm = view.findViewById(R.id.listForm);
        constraintEmptyData = view.findViewById(R.id.constraintEmptyData);
        tvEmptyData = view.findViewById(R.id.tvEmptyData);
        overlay = view.findViewById(R.id.overlay);

        tvSubTitle.setText(menuSelected);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(tvSubTitle, 1, 18, 1, TypedValue.COMPLEX_UNIT_SP);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /** DIFFERENCE BETWEEN ADMIN AND USER ROLE
         *
         * Admin:
         * fetch covering letters which the cl_type is CL_NIKAH
         *
         * Users:
         * fetch covering letters which the cl_type is CL_NIKAH and show the covering letters from KartuKeluarga list only
         *
         * */
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
        fragmentListener.onFragmentCreated(FormListFragment.this, previousFragment);
        customLoadingDialog = new CustomLoadingDialog(context);
        coveringLetterArrayList = VSPreference.getInstance(context).getCoveringLetterGroupList(clType);
        coveringLetters = new ArrayList<>();
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        if (VSPreference.getInstance(context).getRole().equalsIgnoreCase(ConstantVariable.ADMIN))
            isAdmin = true;
        if (!isAdmin) {
            kartuKeluarga = VSPreference.getInstance(context).getKK();
        }
    }

    private void initListener() {
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin) {
                    fragmentListener.onFragmentFinish(FormListFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_HOME_ADMIN, false);
                } else {
                    fragmentListener.onFragmentPassingData(clType);
                    fragmentListener.onActivityBackPressed();
                }
            }
        });
    }

    @Override
    public void onSelectedUserRequest(String ktp, String id) {
        CoveringLetter coveringLetter = new CoveringLetter();
        for (int i = 0; i < coveringLetters.size(); i++) {
            if (coveringLetters.get(i).getClKtp().equalsIgnoreCase(ktp) && coveringLetters.get(i).getClId().equalsIgnoreCase(id)) {
                coveringLetter.setClId(coveringLetters.get(i).getClId());
                coveringLetter.setClNama(coveringLetters.get(i).getClNama());
                coveringLetter.setClKtp(coveringLetters.get(i).getClKtp());
                coveringLetter.setClJenisKelamin(coveringLetters.get(i).getClJenisKelamin());
                coveringLetter.setClPekerjaan(coveringLetters.get(i).getClPekerjaan());
                coveringLetter.setClTempatTanggalLahir(coveringLetters.get(i).getClTempatTanggalLahir());
                coveringLetter.setClAgama(coveringLetters.get(i).getClAgama());
                coveringLetter.setClAlamat(coveringLetters.get(i).getClAlamat());
                coveringLetter.setClPendidikan(coveringLetters.get(i).getClPendidikan());
                coveringLetter.setClKewarganegaraan(coveringLetters.get(i).getClKewarganegaraan());
                coveringLetter.setClKeperluan(coveringLetters.get(i).getClKeperluan());
                coveringLetter.setClTglPengajuan(coveringLetters.get(i).getClTglPengajuan());
                coveringLetter.setApproved(coveringLetters.get(i).isApproved());
                break;
            }
        }

        appliedCoveringLetter(coveringLetter);
        if (isAdmin) {
            fragmentListener.onFragmentPassingData(clType);
            fragmentListener.onFragmentFinish(FormListFragment.this, AdminActivity.FRAGMENT_FINISH_GOTO_PDF_VIEWER, true);
        } else {
            fragmentListener.onFragmentPassingData(clType);
            fragmentListener.onFragmentFinish(FormListFragment.this, UserActivity.FRAGMENT_FINISH_GOTO_PDF_VIEWER, true);
        }
    }

    private void appliedCoveringLetter(CoveringLetter cl) {
        CoveringLetter coveringLetter = new CoveringLetter(cl.getClId(), "LAMPIRAN XIII: MODEL AA.05",
                "Nomor: " + "……/JT/VI/3/007/014/……/2022",
                cl.getClNama(), cl.getClJenisKelamin(), cl.getClTempatTanggalLahir(), cl.getClPekerjaan(),
                String.valueOf(cl.getClKtp()), cl.getClKewarganegaraan(), cl.getClPendidikan(), cl.getClAgama(), cl.getClAlamat(), cl.getClKeperluan(),
                "……/JT/VI/3/014/……/2022", "05/02/2022", "Bpk. Rudi", "05/02/2022", "Bpk. Sukina",
                clType, cl.getClTglPengajuan(), cl.isApproved());
        VSPreference.getInstance(context).setCoveringLetter(ConstantVariable.KEY_COVERING_LETTER, coveringLetter);
    }

    private void prepareLayout() {
        showOverlay(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvSubTitle.setVisibility(View.VISIBLE);
                tvSubTitle.startAnimation(fadeIn);
                for (int i = 0; i < coveringLetterArrayList.size(); i++) {
                    CoveringLetter coveringLetter = (CoveringLetter) coveringLetterArrayList.get(i);
                    if (coveringLetter.getClType() != null) {
                        if (coveringLetter.getClType().equalsIgnoreCase(clType)) {
                            if (isAdmin) {
                                coveringLetters.add(coveringLetter);
                            } else {
                                for (int j = 0; j < kartuKeluarga.getKeluargaList().size(); j++) {
                                    if (coveringLetter.getClKtp().equalsIgnoreCase(kartuKeluarga.getKeluargaList().get(j).getIdKtp())) {
                                        coveringLetters.add(coveringLetter);
                                    }
                                }
                            }
                        }
                    }
                }
                Collections.reverse(coveringLetters);
                FormListAdapter formListAdapter = new FormListAdapter(context, coveringLetters, FormListFragment.this);
                listForm.setDivider(null);
                listForm.setDividerHeight(0);
                listForm.setAdapter(formListAdapter);

                if (coveringLetters.isEmpty()) {
                    constraintEmptyData.setVisibility(View.VISIBLE);
                    tvEmptyData.setText("Oops! belum ada warga yang\n" + menuSelected + "…");
                }
                showOverlay(false);
            }
        }, 1800);
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
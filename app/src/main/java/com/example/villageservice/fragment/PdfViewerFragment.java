package com.example.villageservice.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.example.villageservice.R;
import com.example.villageservice.listener.FragmentListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.PortableDocumentFormat;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.VSPreference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PdfViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PdfViewerFragment extends Fragment {

    private Context context;
    private View view;
    private View pdfFile;
    private Button downloadButton;
    private PortableDocumentFormat portableDocumentFormat;

    private AppCompatTextView tvLampiran;
    private AppCompatTextView tvNumber;
    private AppCompatTextView tvNumberFooterR;
    private AppCompatTextView tvTanggalFooterR;
    private AppCompatTextView tvRWName;
    private AppCompatTextView tvTanggalRT;
    private AppCompatTextView tvRTName;

    /**
     * FORM
     */
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

    private ImageView imageStamp;
    private ImageView imageSignature;

    private String key;
    private boolean isAdmin = false;

    private FragmentListener fragmentListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        }
        fragmentListener.onFragmentCreated(PdfViewerFragment.this);
        portableDocumentFormat = new PortableDocumentFormat(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_preview_pdf, container, false);
        pdfFile = view.findViewById(R.id.relativeLayoutPdf);
        downloadButton = view.findViewById(R.id.downloadButton);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (VSPreference.getInstance(context).getRole().equalsIgnoreCase("admin")) {
            isAdmin = true;
            downloadButton.setText("Setuju");
        } else {
            downloadButton.setText("Download");
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
        imageStamp = view.findViewById(R.id.imageStamp);
        imageSignature = view.findViewById(R.id.imageSignature);
    }

    private void loadData() {
        User user = (User) VSPreference.getInstance(context).getUser();
        key = String.valueOf(user.getIdKtp());

        CoveringLetter coveringLetter = VSPreference.getInstance(context).getCoveringLetter(key);
        tvLampiran.setText(coveringLetter.getClLampiran());
        tvNumber.setText(coveringLetter.getClNomorHeader());
        tvNumberFooterR.setText(coveringLetter.getClNomorFooter());
        tvTanggalFooterR.setText(coveringLetter.getClTanggalFooterRw());
        tvRWName.setText(coveringLetter.getClNamaRw());
        tvTanggalRT.setText(coveringLetter.getClTanggalRt());
        tvRTName.setText(coveringLetter.getClNamaRt());
        tvNameR1.setText(coveringLetter.getClNama());
        tvNameR2.setText(coveringLetter.getClJenisKelamin());
        tvNameR3.setText(coveringLetter.getClTanggalLahir());
        tvNameR4.setText(coveringLetter.getClPekerjaan());
        tvNameR5.setText(coveringLetter.getClKtp());
        tvNameR6.setText(coveringLetter.getClKewarganegaraan());
        tvNameR7.setText(coveringLetter.getClPendidikan());
        tvNameR8.setText(coveringLetter.getClAgama());
        tvNameR19.setText(coveringLetter.getClAlamat());
        tvNameR10.setText(coveringLetter.getClKeperluan());
    }

    private void initListener() {
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAdmin) {
                    //TODO: show dialog first
                    imageStamp.setVisibility(View.VISIBLE);
                    imageSignature.setVisibility(View.VISIBLE);
                } else {

                }
                portableDocumentFormat.generatePdf(pdfFile, "GanjarPranowo", "This is the PDF trial");
            }
        });
    }
}
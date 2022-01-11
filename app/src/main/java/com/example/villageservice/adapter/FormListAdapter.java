package com.example.villageservice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.villageservice.R;
import com.example.villageservice.listener.FormUserRequestedListener;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.Fonts;

import java.util.List;

public class FormListAdapter extends BaseAdapter {

    private Context context;
    private List<CoveringLetter> coveringLetterList;
    private FormUserRequestedListener mListener;

    public FormListAdapter(Context context, List<CoveringLetter> coveringLetterList, FormUserRequestedListener mListener) {
        this.context = context;
        this.coveringLetterList = coveringLetterList;
        this.mListener = mListener;
    }

    @Override
    public int getCount() {
        return coveringLetterList.size();
    }

    @Override
    public Object getItem(int position) {
        return coveringLetterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View v = View.inflate(context, R.layout.layout_form_requested, null);
        ConstraintLayout userRequest = v.findViewById(R.id.userRequest);
        AppCompatTextView tvName = v.findViewById(R.id.tvName);
        AppCompatTextView tvKtp = v.findViewById(R.id.tvKTP);
        AppCompatTextView tvDate = v.findViewById(R.id.tvDate);
        AppCompatTextView tvStatus = v.findViewById(R.id.tvStatus);
        CardView tvButtonOpen = v.findViewById(R.id.tvButtonOpen);
        ImageView imageStatus = v.findViewById(R.id.imageStatus);

        tvName.setText(coveringLetterList.get(position).getClNama());
        tvKtp.setText(coveringLetterList.get(position).getClKtp());
        tvDate.setText(coveringLetterList.get(position).getClTglPengajuan());
        if (coveringLetterList.get(position).isApproved()) {
            tvStatus.setText("Approved");
            imageStatus.setImageResource(R.mipmap.ic_approved);
        } else {
            tvStatus.setText("Pending");
            imageStatus.setImageResource(R.mipmap.ic_pending);
        }


        tvButtonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectedUserRequest(coveringLetterList.get(position).getClKtp(), coveringLetterList.get(position).getClId());
                //Toast.makeText(context, user.get(position).getNamaLengkap(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}

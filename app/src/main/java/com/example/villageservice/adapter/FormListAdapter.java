package com.example.villageservice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.widget.AppCompatTextView;
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
    private Fonts fonts;
    private FormUserRequestedListener mListener;

    public FormListAdapter(Context context, List<CoveringLetter> coveringLetterList, FormUserRequestedListener mListener) {
        this.context = context;
        this.coveringLetterList = coveringLetterList;
        this.mListener = mListener;
        fonts = new Fonts(context);
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

        tvName.setText(coveringLetterList.get(position).getClNama());
        tvKtp.setText(coveringLetterList.get(position).getClKtp());
        tvDate.setText(coveringLetterList.get(position).getClKeperluan());

        userRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectedUserRequest(coveringLetterList.get(position).getClKtp(), coveringLetterList.get(position).getClNama());
                //Toast.makeText(context, user.get(position).getNamaLengkap(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}

package com.example.villageservice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.villageservice.R;
import com.example.villageservice.listener.FormUserRequestedListener;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.Fonts;

import java.util.List;

public class FormListAdapter extends BaseAdapter {

    private Context context;
    private List<User> user;
    private Fonts fonts;
    private FormUserRequestedListener mListener;

    public FormListAdapter(Context context, List<User> user, FormUserRequestedListener mListener) {
        this.context = context;
        this.user = user;
        this.mListener = mListener;
        fonts = new Fonts(context);
    }

    @Override
    public int getCount() {
        return user.size();
    }

    @Override
    public Object getItem(int position) {
        return user.get(position);
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
        AppCompatTextView tvStatus = v.findViewById(R.id.tvStatus);
        AppCompatTextView tvDate = v.findViewById(R.id.tvDate);

        tvName.setTypeface(fonts.rBoldExtra());
        tvStatus.setTypeface(fonts.rRegular());
        tvName.setTypeface(fonts.rRegular());

        tvName.setText(user.get(position).getNamaLengkap());
        tvStatus.setText(user.get(position).getStatusPernikahan());
        tvDate.setText(user.get(position).getJenisKelamin());

        userRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onSelectedUserRequest(user.get(position).getIdKtp(), user.get(position).getNamaLengkap());
                //Toast.makeText(context, user.get(position).getNamaLengkap(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}

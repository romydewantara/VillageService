package com.example.villageservice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.villageservice.R;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.Fonts;

import java.util.List;

public class FormListAdapter extends BaseAdapter {

    private Context context;
    private List<User> user;
    private Fonts fonts;

    public FormListAdapter(Context context, List<User> user) {
        this.context = context;
        this.user = user;
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
        TextView tvName = v.findViewById(R.id.tvName);
        TextView tvStatus = v.findViewById(R.id.tvStatus);
        TextView tvDate = v.findViewById(R.id.tvDate);

        tvName.setText(user.get(position).getNamaLengkap());
        tvStatus.setText(user.get(position).getStatusPernikahan());
        tvDate.setText(user.get(position).getJenisKelamin());

        tvName.setTypeface(fonts.rBold());
        tvStatus.setTypeface(fonts.rRegular());
        tvName.setTypeface(fonts.rRegular());

        userRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, user.get(position).getNamaLengkap(), Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}

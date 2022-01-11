package com.example.villageservice.adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villageservice.R;
import com.example.villageservice.model.User;

import java.util.List;

public class KeluargaListAdapter extends RecyclerView.Adapter<KeluargaListAdapter.KeluargaListHolder> {

    List<User> userList;

    public KeluargaListAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public KeluargaListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_name_list, parent, false);
        return new KeluargaListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeluargaListHolder holder, int position) {
        holder.tvName.setText(userList.get(position).getNamaLengkap());
        holder.tvKTP.setText(userList.get(position).getIdKtp());

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(holder.tvName, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(holder.tvKTP, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class KeluargaListHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvName;
        AppCompatTextView tvKTP;

        public KeluargaListHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvKTP = itemView.findViewById(R.id.tvKTP);
        }
    }
}

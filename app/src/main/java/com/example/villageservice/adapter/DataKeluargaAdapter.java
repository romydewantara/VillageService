package com.example.villageservice.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villageservice.R;
import com.example.villageservice.model.User;

import java.util.List;

public class DataKeluargaAdapter extends RecyclerView.Adapter<DataKeluargaAdapter.DataKeluargaHolder> {

    List<User> userArrayList;
    private ItemClickListener itemClickListener;

    public DataKeluargaAdapter(List<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public DataKeluargaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_data_anggota_keluarga, parent, false);
        return new DataKeluargaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataKeluargaHolder holder, int position) {
        Log.d("XXXLOG", "onBindViewHolder - ktp: " + userArrayList.get(position).getIdKtp());
        holder.tvKTP.setText(userArrayList.get(position).getIdKtp());
        holder.tvName.setText(userArrayList.get(position).getNamaLengkap());
        holder.tvJenKel.setText(userArrayList.get(position).getJenisKelamin());
        holder.tvPekerjaan.setText(userArrayList.get(position).getJenisPekerjaan());
        holder.tvAgama.setText(userArrayList.get(position).getAgama());
        holder.tvStatus.setText(userArrayList.get(position).getStatusPernikahan());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class DataKeluargaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final private AppCompatTextView tvKTP;
        final private AppCompatTextView tvName;
        final private AppCompatTextView tvJenKel;
        final private AppCompatTextView tvPekerjaan;
        final private AppCompatTextView tvAgama;
        final private AppCompatTextView tvStatus;

        public DataKeluargaHolder(@NonNull View itemView) {
            super(itemView);
            tvKTP = itemView.findViewById(R.id.tvKTP);
            tvName = itemView.findViewById(R.id.tvName);
            tvJenKel = itemView.findViewById(R.id.tvJenKel);
            tvPekerjaan = itemView.findViewById(R.id.tvPekerjaan);
            tvAgama = itemView.findViewById(R.id.tvAgama);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            Log.d("XXXLOG", "DataKeluargaHolder - tvKTP: " + tvKTP);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Animation clickEffect = AnimationUtils.loadAnimation(v.getContext(), R.anim.button_bounched);
            v.startAnimation(clickEffect);
            if (itemClickListener != null) itemClickListener.onItemClick(this, tvKTP.getText().toString());
        }
    }

    public interface ItemClickListener {
        void onItemClick(DataKeluargaHolder dataKeluargaHolder, String ktp);
    }
}

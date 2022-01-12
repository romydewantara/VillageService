package com.example.villageservice.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villageservice.R;
import com.example.villageservice.model.KartuKeluarga;
import com.example.villageservice.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class KartuKeluargaListAdapter extends RecyclerView.Adapter<KartuKeluargaListAdapter.KartuKeluargaHolder> {

    private ArrayList<KartuKeluarga> kartuKeluargaArrayList;
    private Context context;
    private ItemClickListener itemClickListener;

    public KartuKeluargaListAdapter(Context context, ArrayList<KartuKeluarga> kartuKeluargaArrayList) {
        this.context = context;
        this.kartuKeluargaArrayList = kartuKeluargaArrayList;
    }

    @NonNull
    @Override
    public KartuKeluargaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("XXXLOG", "onCreateViewHolder - viewType: " + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_data_kartu_keluarga, parent, false);
        return new KartuKeluargaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KartuKeluargaHolder holder, int position) {
        holder.tvNomorKK.setText(kartuKeluargaArrayList.get(position).getIdKartuKeluarga());
        holder.tvKepKKName.setText(kartuKeluargaArrayList.get(position).getNamaKepalaKeluarga());

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(holder.tvNomorKK, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(holder.tvKepKKName, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        KeluargaListAdapter keluargaListAdapter;
        KartuKeluarga kartuKeluarga = kartuKeluargaArrayList.get(position);
        List<User> userList = new ArrayList<>(kartuKeluarga.getKeluargaList());
        keluargaListAdapter = new KeluargaListAdapter(userList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        holder.recyclerDataKeluarga.setLayoutManager(layoutManager);
        holder.recyclerDataKeluarga.setAdapter(keluargaListAdapter);
    }

    @Override
    public int getItemCount() {
        return kartuKeluargaArrayList.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class KartuKeluargaHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final private AppCompatTextView tvNomorKK;
        final private AppCompatTextView tvKepKKName;
        final private RecyclerView recyclerDataKeluarga;
        final private Button negButton;
        final private Button posButton;

        public KartuKeluargaHolder(@NonNull View itemView) {
            super(itemView);

            tvNomorKK = itemView.findViewById(R.id.tvNomorKK);
            tvKepKKName = itemView.findViewById(R.id.tvKepKKName);
            recyclerDataKeluarga = itemView.findViewById(R.id.recyclerDataKeluarga);
            negButton = itemView.findViewById(R.id.negButton);
            posButton = itemView.findViewById(R.id.posButton);

            negButton.setOnClickListener(this);
            posButton.setOnClickListener(this);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.posButton:
                    itemClickListener.onEdit(kartuKeluargaArrayList.get(getAdapterPosition()), getAdapterPosition());
                    break;
                case R.id.negButton:
                    itemClickListener.onDelete(getAdapterPosition());
                    break;
            }
        }
    }

    public interface ItemClickListener {
        void onDelete(int position);
        void onEdit(KartuKeluarga kartuKeluarga, int position);
    }
}

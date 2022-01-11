package com.example.villageservice.adapter;

import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villageservice.R;
import com.example.villageservice.model.CoveringLetter;
import com.example.villageservice.utility.ConstantVariable;

import java.util.ArrayList;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationsHolder> {

    private ArrayList<CoveringLetter> coveringLetterArrayList;
    private ItemClickListener itemClickListener;

    public NotificationsAdapter(ArrayList<CoveringLetter> coveringLetterArrayList) {
        this.coveringLetterArrayList = coveringLetterArrayList;
    }

    @NonNull
    @Override
    public NotificationsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_item_notifications, parent, false);
        return new NotificationsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsHolder holder, int position) {
        holder.tvName.setText(coveringLetterArrayList.get(position).getClNama());
        holder.tvKTP.setText(coveringLetterArrayList.get(position).getClKtp());
        holder.tvDate.setText(coveringLetterArrayList.get(position).getClTglPengajuan());
        holder.tvNumHeader.setText(coveringLetterArrayList.get(position).getClNomorHeader());

        String color = "#A3DA8D";
        Log.d("XXXLOG", "onBindViewHolder - CL TYPE: " + coveringLetterArrayList.get(position).getClType());
        switch (coveringLetterArrayList.get(position).getClType()) {
            case ConstantVariable.KEY_CL_NIKAH:
                color = "#2FDD92"; //normal
                break;
            case ConstantVariable.KEY_CL_UMKM:
                color = "#7CD1B8"; //low
                break;
            case ConstantVariable.KEY_CL_DOMISILI_KTP:
                color = "#980F5A"; //asap
                break;
            case ConstantVariable.KEY_CL_KK_BARU:
                color = "#EF2F88"; //critical
                break;
            case ConstantVariable.KEY_CL_AKTA_LAHIR:
                color = "#EA99D5"; //major
                break;
            case ConstantVariable.KEY_CL_AKTA_KEMATIAN:
                color = "#92A9BD"; //minor
                break;
        }
        Log.d("XXXLOG", "onBindViewHolder - color: " +color);
        holder.tvKeperluan.setText(coveringLetterArrayList.get(position).getClKeperluan());
        holder.tvKeperluan.setTextColor(Color.parseColor(color));
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(holder.tvName, 1, 14, 1, TypedValue.COMPLEX_UNIT_SP);
        if (coveringLetterArrayList.get(position).isApproved()) {
            holder.imageStatus.setImageResource(R.mipmap.ic_approved);
            holder.tvStatus.setText("Approved");
        } else {
            holder.imageStatus.setImageResource(R.mipmap.ic_pending);
            holder.tvStatus.setText("Pending");
        }
    }

    @Override
    public int getItemCount() {
        return coveringLetterArrayList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class NotificationsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AppCompatTextView tvName;
        private AppCompatTextView tvKTP;
        private AppCompatTextView tvDate;
        private ImageView imageStatus;
        private AppCompatTextView tvStatus;
        private AppCompatTextView tvNumHeader;
        private AppCompatTextView tvKeperluan;

        public NotificationsHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvKTP = itemView.findViewById(R.id.tvKTP);
            tvDate = itemView.findViewById(R.id.tvDate);
            imageStatus = itemView.findViewById(R.id.imageStatus);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvNumHeader = itemView.findViewById(R.id.tvNumHeader);
            tvKeperluan = itemView.findViewById(R.id.tvKeperluan);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.onItemClicked(this, coveringLetterArrayList.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClicked(NotificationsHolder notificationsHolder, CoveringLetter coveringLetter, int position);
    }
}

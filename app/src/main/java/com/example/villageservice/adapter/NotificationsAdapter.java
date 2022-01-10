package com.example.villageservice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villageservice.R;
import com.example.villageservice.model.CoveringLetter;

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
        holder.tvKeperluan.setText(coveringLetterArrayList.get(position).getClKeperluan());
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

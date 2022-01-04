package com.example.villageservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villageservice.R;
import com.example.villageservice.model.User;

import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberHolder> {

    private Context context;
    private ArrayList<User> userArrayList;
    private ItemClickListener itemClickListener;

    public MembersAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MemberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_member, parent, false);
        return new MemberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberHolder holder, int position) {
        holder.tvMemberName.setText(userArrayList.get(position).getNamaLengkap());
        holder.tvMemberKTP.setText(userArrayList.get(position).getIdKtp());
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public User getItem(int id) {
        return userArrayList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class MemberHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final AppCompatTextView tvMemberName;
        private final AppCompatTextView tvMemberKTP;
        private final AppCompatTextView editButton;

        public MemberHolder(@NonNull View itemView) {
            super(itemView);
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
            tvMemberKTP = itemView.findViewById(R.id.tvMemberKTP);
            editButton = itemView.findViewById(R.id.editButton);

            editButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null) itemClickListener.onItemClick(this, v, getAdapterPosition(), tvMemberKTP.getText().toString());
        }

        public void setUpdateData(String name, String ktp) {
            tvMemberName.setText(name);
            tvMemberKTP.setText(ktp);
        }

    }

    public interface ItemClickListener {
        void onItemClick(MemberHolder memberHolder, View view, int position, String ktp);
    }
}

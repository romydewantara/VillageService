package com.example.villageservice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.villageservice.R;
import com.example.villageservice.model.User;
import com.example.villageservice.utility.ConstantVariable;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberHolder> {

    private Context context;
    private ItemClickListener itemClickListener;
    private final ArrayList<User> userArrayList;

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
            if (itemClickListener != null) itemClickListener.onItemClick(this, userArrayList.get(getAdapterPosition()), getAdapterPosition());
        }

        public void setUpdateData(User user, int position) {
            tvMemberName.setText(user.getNamaLengkap());
            tvMemberKTP.setText(user.getIdKtp());
            Log.d("XXXLOG", "setUpdateData - arrayList (BEFORE): " + new Gson().toJson(userArrayList));
            userArrayList.set(position, user);
            Log.d("XXXLOG", "setUpdateData - arrayList (AFTER): " + new Gson().toJson(userArrayList));
        }

    }

    public interface ItemClickListener {
        void onItemClick(MemberHolder memberHolder, User user, int position);
    }
}

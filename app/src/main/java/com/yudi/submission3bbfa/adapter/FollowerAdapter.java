package com.yudi.submission3bbfa.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yudi.submission3bbfa.DetailListActivity;
import com.yudi.submission3bbfa.R;
import com.yudi.submission3bbfa.model.FollowerUserModel;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder> {

    private Context context;
    private ArrayList<FollowerUserModel> data = new ArrayList<>();

    public FollowerAdapter(Context context, ArrayList<FollowerUserModel> listFollower) {
        this.context = context;
        this.data = listFollower;
    }

    //1. Method yang menyambungkan layout item
    @NonNull
    @Override
    public FollowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.follower_items, parent, false);
        return new FollowerViewHolder(itemview);
    }

    //2. Set data
    @Override
    public void onBindViewHolder(@NonNull FollowerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvUsernameFollower.setText(data.get(position).getLogin());
        Glide.with(context)
                .load(data.get(position).getAvatarUrl())
                .into(holder.ivAvatarFollower);
    }

    //3. Jumlah data
    @Override
    public int getItemCount() {
        return data.size();
    }

    //4. mengenalkan komponen item
    public class FollowerViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsernameFollower;
        ImageView ivAvatarFollower;

        public FollowerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsernameFollower = itemView.findViewById(R.id.tv_username_follower);
            ivAvatarFollower = itemView.findViewById(R.id.iv_avatar_follower);

        }
    }
}

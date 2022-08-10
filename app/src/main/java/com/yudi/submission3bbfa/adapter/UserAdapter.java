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
import com.yudi.submission3bbfa.model.UserModel;
import com.yudi.submission3bbfa.model.Users;

import org.parceler.Parcels;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ListViewHolder>
{

	public static final String DATA_USER = "datauser";
	public static final String DATA_EXTRA = "dataextra";
	private Context context;
	private List<UserModel> data;

	public UserAdapter(Context context, List<UserModel> list) {
		this.data = list;
		this.context = context;
	}

	@NonNull
	@Override
	public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_items, parent, false);
		return new ListViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ListViewHolder holder, @SuppressLint("RecyclerView") int position) {

		holder.tvUsername.setText(data.get(position).getLogin());
		Glide.with(context)
				.load(data.get(position).getAvatarUrl())
				.into(holder.ivAvatar);

		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent moveIntent = new Intent(context, DetailListActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable(DATA_USER, Parcels.wrap(data.get(position)));
				moveIntent.putExtra(DATA_EXTRA, bundle);
				context.startActivity(moveIntent);
			}
		});

	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	public class ListViewHolder extends RecyclerView.ViewHolder {

		TextView tvUsername;
		ImageView ivAvatar;

		public ListViewHolder(@NonNull View itemView) {
			super(itemView);
			tvUsername = itemView.findViewById(R.id.tv_username);
			ivAvatar = itemView.findViewById(R.id.iv_avatar);
		}
	}

}

package com.yudi.submission3bbfa.adapter;

import static com.yudi.submission3bbfa.adapter.UserAdapter.DATA_USER;
import static com.yudi.submission3bbfa.adapter.UserAdapter.DATA_EXTRA;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.yudi.submission3bbfa.DetailListActivity;
import com.yudi.submission3bbfa.R;
import com.yudi.submission3bbfa.db.AppDatabase;
import com.yudi.submission3bbfa.db.UserDAO;
import com.yudi.submission3bbfa.model.UserModel;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<UserModel> listUser = new ArrayList<>();
    private Context context;
    private OnItemClickCallback onItemClickCallback;
    private List<UserModel> data;

    public FavoriteAdapter(Context context, List<UserModel> list) {
        this.data = list;
        this.context = context;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setData(ArrayList<UserModel> items) {
        listUser.clear();
        listUser.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_items, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setItem(listUser.get(position));

       // holder.itemView.setOnClickListener(view -> onItemClickCallback.onItemClicked(listUser.get(holder.getAdapterPosition())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveIntent = new Intent(context, DetailListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(DATA_USER, Parcels.wrap(listUser.get(holder.getAdapterPosition())));
                moveIntent.putExtra(DATA_EXTRA, bundle);
                context.startActivity(moveIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fav_txt_username) TextView username;
        @BindView(R.id.fav_img_avatar) ImageView avatar;
        @BindView(R.id.fav_btn_delete_fav) Button btnDeleteFav;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void setItem(UserModel item) {
            username.setText(item.getLogin());
            Picasso.get()
                    .load(item.getAvatarUrl())
                    .resize(100, 100)
                    .centerCrop()
                    .into(avatar);

            btnDeleteFav.setOnClickListener(view -> {
                final AlertDialog.Builder alert = new AlertDialog.Builder(itemView.getContext());
                alert.setTitle(R.string.alert_delete);
                alert.setMessage(R.string.confirm_delete);
                alert.setCancelable(false);
                alert.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    UserDAO userDAO = Room.databaseBuilder(itemView.getContext(), AppDatabase.class, "user")
                            .allowMainThreadQueries()
                            .build()
                            .userDAO();

                    userDAO.deleteByUsername(item.getId());

                    listUser.remove(item);
                    notifyDataSetChanged();

                    Snackbar.make(view, R.string.delete_success, Snackbar.LENGTH_SHORT).show();
                });
                alert.setNegativeButton(R.string.no, ((dialogInterface, i) -> alert.setCancelable(true)));
                alert.show();
            });
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(UserModel data);
    }
}

package com.yudi.submission3bbfa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yudi.submission3bbfa.adapter.FavoriteAdapter;
import com.yudi.submission3bbfa.db.AppDatabase;
import com.yudi.submission3bbfa.db.UserDAO;
import com.yudi.submission3bbfa.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity {

	@BindView(R.id.fav_progressBar)
	ProgressBar progressBar;
	@BindView(R.id.fav_recyclerView)
	RecyclerView rvFavorite;
	@BindView(R.id.fav_txt_not_found)
	TextView txtNotFound;
	@BindView(R.id.fav_linearNotFound)
	LinearLayout linearNotFound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);

		ButterKnife.bind(this);

		if (getSupportActionBar() != null)
			getSupportActionBar().setTitle("Favorites");

		rvFavorite.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
		rvFavorite.setLayoutManager(new LinearLayoutManager(this));

		UserDAO userDAO = Room.databaseBuilder(this, AppDatabase.class, "user")
				.allowMainThreadQueries()
				.build()
				.userDAO();

		List<UserModel> listUser = userDAO.getAll();
		ArrayList<UserModel> arrayUser = new ArrayList<>(listUser);

		txtNotFound.setText(R.string.empty_favorite);

		showLoading(true);
		if (listUser.isEmpty()) {
			showLoading(false);
			linearNotFound.setVisibility(View.VISIBLE);
		}

		FavoriteAdapter adapter = new FavoriteAdapter(FavoriteActivity.this, arrayUser);
		rvFavorite.setAdapter(adapter);

		adapter.setData(arrayUser);

		showLoading(false);
	}

	private void showLoading(Boolean state) {
		if (state) progressBar.setVisibility(View.VISIBLE);
		else progressBar.setVisibility(View.GONE);
	}
}
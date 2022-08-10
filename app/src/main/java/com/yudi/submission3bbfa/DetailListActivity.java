package com.yudi.submission3bbfa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.yudi.submission3bbfa.adapter.FollowerAdapter;
import com.yudi.submission3bbfa.adapter.PageAdapter;
import com.yudi.submission3bbfa.adapter.UserAdapter;
import com.yudi.submission3bbfa.db.AppDatabase;
import com.yudi.submission3bbfa.db.UserDAO;
import com.yudi.submission3bbfa.model.DetailUserModel;
import com.yudi.submission3bbfa.model.FollowerUserModel;
import com.yudi.submission3bbfa.model.UserModel;
import com.yudi.submission3bbfa.retrofit.ApiClient;

import org.parceler.Parcels;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailListActivity extends AppCompatActivity {

	public static final String EXTRA_USER = "extra_user";
	DetailUserModel dataDetailUser;
	UserModel dataUser;
	FollowerUserModel dataFollower;
	TextView tvName, tvUsername, tvLocation;
	ImageView ivAvatar;

	private String uName;

	private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
	private boolean isAvatarShown = true;
	private int maxScrollSize;

	AppBarLayout appBarLayout;
	Toolbar toolbar;
	ProgressBar progressBar;
	@BindView(R.id.fab_favorite)
	FloatingActionButton fabFavorite;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_list);

		getSupportActionBar().setTitle(getString(R.string.detail_user));

		Bundle bundle = getIntent().getBundleExtra(UserAdapter.DATA_EXTRA);
		dataUser = Parcels.unwrap(bundle.getParcelable(UserAdapter.DATA_USER));

		ivAvatar = findViewById(R.id.iv_avatar_detail);
		tvUsername = findViewById(R.id.tv_username_detail);
		tvName = findViewById(R.id.tv_name_detail);
		tvLocation = findViewById(R.id.tv_location_detail);

		// Menampilkan progress Dialog
		final ProgressDialog progress = new ProgressDialog(DetailListActivity.this);
		progress.setMessage(getString(R.string.progress));
		progress.show();

		//Mengambil data dari Parcelable
		Glide.with(DetailListActivity.this)
				.load(dataUser.getAvatarUrl())
				.into(ivAvatar);
		tvUsername.setText(dataUser.getLogin());

		Call<DetailUserModel> request = ApiClient.getApiService().getDetailUser(dataUser.getLogin());
		request.enqueue(new Callback<DetailUserModel>() {
			@Override
			public void onResponse(Call<DetailUserModel> call, Response<DetailUserModel> response) {
				dataDetailUser = response.body();

				tvName.setText(dataDetailUser.getName());
				tvLocation.setText(dataDetailUser.getLocation());
				progress.dismiss();
			}

			@Override
			public void onFailure(Call<DetailUserModel> call, Throwable t) {

			}
		});

		//Tablayout dan ViewPager
		PageAdapter pageAdapter = new PageAdapter(this, getSupportFragmentManager());
		ViewPager viewPager = findViewById(R.id.view_pager);
		viewPager.setAdapter(pageAdapter);
		TabLayout tabLayout = findViewById(R.id.tab_layout);
		tabLayout.setupWithViewPager(viewPager);

		getSupportActionBar().setElevation(0);

		ButterKnife.bind(this);

		if (dataUser != null) {
			uName = dataUser.getLogin();
			tvUsername.setText(dataUser.getLogin());
		}

		Objects.requireNonNull(getSupportActionBar()).setTitle(uName);

		UserDAO userDAO = Room.databaseBuilder(this, AppDatabase.class, "user")
				.allowMainThreadQueries()
				.build()
				.userDAO();

		UserModel check = userDAO.getUserByUsername(uName);
		if (check != null) {
			fabFavorite.setVisibility(View.GONE);
		} else {
			fabFavorite.setOnClickListener(view -> {
				userDAO.insertAll(dataUser);

				Toast.makeText(this, R.string.fav_success_add, Toast.LENGTH_SHORT).show();

				fabFavorite.setVisibility(View.GONE);
			});
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
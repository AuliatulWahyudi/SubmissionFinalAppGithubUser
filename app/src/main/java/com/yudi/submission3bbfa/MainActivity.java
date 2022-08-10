package com.yudi.submission3bbfa;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yudi.submission3bbfa.adapter.UserAdapter;
import com.yudi.submission3bbfa.model.ResponseUser;
import com.yudi.submission3bbfa.model.UserModel;
import com.yudi.submission3bbfa.retrofit.ApiClient;
import com.yudi.submission3bbfa.theme.MainViewModel;
import com.yudi.submission3bbfa.theme.SettingPreferences;
import com.yudi.submission3bbfa.theme.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

	List<UserModel> dataGithub = new ArrayList<>();
	RecyclerView rvUser;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (getSupportActionBar() != null) {
			getSupportActionBar().setTitle(getString(R.string.search_user));
		}

		progressBar = findViewById(R.id.progressBar);

		//1. Mengenali Recyclerview
		rvUser = findViewById(R.id.rv_search_user);

		// Layout Manager
		rvUser.setLayoutManager(new LinearLayoutManager(MainActivity.this));

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		if (searchManager != null) {
			SearchView searchView = (SearchView) findViewById(R.id.sv_user);
			searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			searchView.setQueryHint(getResources().getString(R.string.username));
			searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				@Override
				public boolean onQueryTextSubmit(String s) {
					showProgress(true);
					if (s != null) {
						getDataOnline(s);
					} else {
						Toast.makeText(MainActivity.this, "Please insert username", Toast.LENGTH_SHORT).show();
					}
					return true;
				}

				@Override
				public boolean onQueryTextChange(String s) {
					return true;
				}
			});
		}
	}

	private void showProgress(Boolean state) {
		if (state) {
			progressBar.setVisibility(View.VISIBLE);
		} else {
			progressBar.setVisibility(View.GONE);
		}
	}

	private void getDataOnline(final String usernames) {
		Call<ResponseUser> request = ApiClient.getApiService().getSearchUser(usernames);
		request.enqueue(new Callback<ResponseUser>() {
			@Override
			public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
				if (response.isSuccessful()) {
					//Mengambil data dari internet masuk ke Data Github
					dataGithub = response.body().getItems();
					//Set Adapter ke Recycler View
					rvUser.setAdapter(new UserAdapter(MainActivity.this, dataGithub));
					showProgress(false);

				} else {
					Toast.makeText(MainActivity.this, "Request Not Success", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(Call<ResponseUser> call, Throwable t) {
				Toast.makeText(MainActivity.this, "Request Failure" + t.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_language)
		{
			Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
			startActivity(mIntent);
		}
		if (id == R.id.action_theme)
		{
			Intent intent = new Intent(this, ThemeActivity.class);
			startActivity(intent);
		}
		if (id == R.id.action_fav)
		{
			Intent intent = new Intent(this, FavoriteActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
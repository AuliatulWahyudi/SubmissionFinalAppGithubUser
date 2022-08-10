package com.yudi.submission3bbfa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;

import com.yudi.submission3bbfa.theme.MainViewModel;
import com.yudi.submission3bbfa.theme.SettingPreferences;
import com.yudi.submission3bbfa.theme.ViewModelFactory;

public class SplashScreenActivity extends AppCompatActivity {

	private int loadingSplash = 3000;
	private SettingPreferences settingPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this, "settings").build();
		settingPreferences = SettingPreferences.getInstance(dataStore);
		MainViewModel mainViewModel = new MainViewModel(settingPreferences);
		mainViewModel.getThemeSettings().observe(this, isActive ->
		{
			if (isActive)
			{
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
			}
			else
			{
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
			}
		});

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				Intent login=new Intent(SplashScreenActivity.this, MainActivity.class);
				startActivity(login);
				finish();

			}
		}, loadingSplash);
	}
}
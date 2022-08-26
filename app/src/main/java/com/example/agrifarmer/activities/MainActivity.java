package com.example.agrifarmer.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.agrifarmer.R;
import com.example.agrifarmer.fragments.AccountFragment;
import com.example.agrifarmer.fragments.HomeFragment;
import com.example.agrifarmer.fragments.WeatherFragment;
import com.example.agrifarmer.localDatabases.MyProfileDbHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    AccountFragment accountFragment = new AccountFragment();
    WeatherFragment weatherFragment = new WeatherFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.home_activity_bottom_navigation_view);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frame_container, homeFragment).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frame_container, homeFragment).commit();
                    return true;
                case R.id.nav_account:
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frame_container, accountFragment).commit();
                    return true;
                case R.id.nav_weather:
                    getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frame_container, weatherFragment).commit();
                    return true;
            }
            return false;
        });
    }
}
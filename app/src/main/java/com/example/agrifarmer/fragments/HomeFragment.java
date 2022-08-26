package com.example.agrifarmer.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.agrifarmer.R;
import com.example.agrifarmer.activities.CropHistoryActivity;
import com.example.agrifarmer.activities.PredictCropsActivity;
import com.example.agrifarmer.activities.registration.LoginActivity;
import com.example.agrifarmer.activities.registration.Register1Activity;
import com.example.agrifarmer.getterSetterClasses.Profile;
import com.example.agrifarmer.localDatabases.MyProfileDbHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeFragment extends Fragment {

    private String urlCurrentWeather = "https://weather-crop-api.herokuapp.com/currentWeather";
    private String urlPredictCrop = "https://weather-crop-api.herokuapp.com/predictCrop";

    private TextView userLocation, latLong, suggestCrops;
    private CardView suggestCropsCV, cropHistoryCV, weatherForecastingCV;

    private FirebaseAuth auth;
    private FirebaseUser user;

    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        MyProfileDbHandler profileDbHandler = new MyProfileDbHandler(getActivity());

        userLocation = root.findViewById(R.id.home_user_location);
        latLong = root.findViewById(R.id.home_lat_and_long);
        suggestCrops = root.findViewById(R.id.home_suggest_crop_btn);
        suggestCropsCV = root.findViewById(R.id.home_crop_suggest_card_view);
        cropHistoryCV = root.findViewById(R.id.home_crop_history_card_view);
        weatherForecastingCV = root.findViewById(R.id.home_weather_forecast_card_view);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            userLocation.setVisibility(View.INVISIBLE);
            latLong.setVisibility(View.INVISIBLE);
        }else {
            userLocation.setVisibility(View.VISIBLE);
            latLong.setVisibility(View.VISIBLE);

            Profile profile = profileDbHandler.getRegisteredUser();
            userLocation.setText(profile.getLocation());

            String latLongLocation = "";

            if (profile.getLocation().equalsIgnoreCase("Kolhapur")){
                latLongLocation = latLongLocation + "Lat : 16.7044, Long : 74.2414";
            }else if (profile.getLocation().equalsIgnoreCase("Sangli")){
                latLongLocation = latLongLocation + "Lat : 16.8508, Long : 74.5889";
            }else {
                latLongLocation = latLongLocation + "Lat : 19.995, Long : 73.7965";
            }
            latLong.setText(latLongLocation);
        }

        suggestCrops.setOnClickListener(view -> {
            if (user == null) {
                dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.login_register_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);
                dialog.show();

                Button login = dialog.findViewById(R.id.dialog_login_btn);
                Button signup = dialog.findViewById(R.id.dialog_signup_btn);
                CardView close = dialog.findViewById(R.id.dialog_close_btn);

                login.setOnClickListener(view1 -> {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(intent);
                });

                signup.setOnClickListener(view1 -> {
                    Intent intent = new Intent(getActivity(), Register1Activity.class);
                    getActivity().startActivity(intent);
                });

                close.setOnClickListener(view1 -> dialog.dismiss());
            }else {
                Intent intent = new Intent(getActivity(), PredictCropsActivity.class);
                startActivity(intent);
            }
        });

        cropHistoryCV.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CropHistoryActivity.class);
            startActivity(intent);
        });

        weatherForecastingCV.setOnClickListener(view -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.home_activity_frame_container, new WeatherFragment());
            transaction.commit();
            BottomNavigationView bnv = getActivity().findViewById(R.id.home_activity_bottom_navigation_view);
            bnv.setSelectedItemId(R.id.nav_weather);
        });

        return root;
    }
}
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
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.agrifarmer.R;
import com.example.agrifarmer.activities.registration.LoginActivity;
import com.example.agrifarmer.activities.registration.Register1Activity;
import com.example.agrifarmer.getterSetterClasses.Profile;
import com.example.agrifarmer.localDatabases.MyProfileDbHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WeatherFragment extends Fragment {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();

    private Dialog dialog;

    private String urlCurrentWeather = "https://agri-farmer-api.herokuapp.com/currentWeather";

    private LottieAnimationView animationView;
    private TextView dayDate, temp, addr, lat_long, desc, windspeed, precip, humidity, weatherAlerts;

    private String txtTemp, txtAddr, txtLat, txtLong, txtDesc, txtWindspeed, txtPrecip, txtHumidity, txtCondition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_weather, container, false);

        MyProfileDbHandler profileDbHandler = new MyProfileDbHandler(getActivity());

        animationView = root.findViewById(R.id.wc_weather_animation);
        dayDate = root.findViewById(R.id.wc_day_date);
        temp = root.findViewById(R.id.wc_temperature);
        addr = root.findViewById(R.id.wc_location);
        lat_long = root.findViewById(R.id.wc_lat_long);
        desc = root.findViewById(R.id.wc_description);
        windspeed = root.findViewById(R.id.wc_windspeed);
        precip = root.findViewById(R.id.wc_precip);
        humidity = root.findViewById(R.id.wc_humidity);
        weatherAlerts = root.findViewById(R.id.wc_weather_alerts);

        if (user == null) {
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.login_register_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);

            Button login = dialog.findViewById(R.id.dialog_login_btn);
            Button signup = dialog.findViewById(R.id.dialog_signup_btn);
            CardView close = dialog.findViewById(R.id.dialog_close_btn);

            login.setOnClickListener(view -> {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                getActivity().startActivity(intent);
            });

            signup.setOnClickListener(view -> {
                Intent intent = new Intent(getActivity(), Register1Activity.class);
                getActivity().startActivity(intent);
            });

            close.setOnClickListener(view -> {
                dialog.dismiss();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.home_activity_frame_container, new HomeFragment());
                transaction.commit();
                BottomNavigationView bnv = getActivity().findViewById(R.id.home_activity_bottom_navigation_view);
                bnv.setSelectedItemId(R.id.nav_home);
            });
        }else {
            if (profileDbHandler.userAlreadyExist()) {
                Profile profile = profileDbHandler.getRegisteredUser();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCurrentWeather,
                        response -> {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                txtAddr = jsonObject.getString("address");
                                txtCondition = jsonObject.getString("conditions");
                                txtDesc = jsonObject.getString("description");
                                txtHumidity = jsonObject.getString("humidity");
                                txtLat = jsonObject.getString("latitude");
                                txtLong = jsonObject.getString("longitude");
                                txtPrecip = jsonObject.getString("precip");
                                txtTemp = jsonObject.getString("temp");
                                txtWindspeed = jsonObject.getString("windspeed");

                                temp.setText(txtTemp);
                                addr.setText(txtAddr);
                                lat_long.setText("LAT : " + txtLat + ", LONG : " + txtLong);
                                desc.setText(txtDesc);
                                windspeed.setText(txtWindspeed);
                                precip.setText(txtPrecip);
                                humidity.setText(txtHumidity);

                                if (txtCondition.equalsIgnoreCase("sunny") || txtCondition.equalsIgnoreCase("clear")){
                                    animationView.setAnimation(R.raw.sunny_weather_animation);
                                }else if (txtCondition.equalsIgnoreCase("cloudy") || txtCondition.equalsIgnoreCase("partially cloudy") || txtCondition.equalsIgnoreCase("overcast")){
                                    animationView.setAnimation(R.raw.cloudy_weather_animation);
                                }else if (txtCondition.equalsIgnoreCase("windy")) {
                                    animationView.setAnimation(R.raw.windy_weather_animation);
                                }else if (txtCondition.equalsIgnoreCase("rainy") || txtCondition.equalsIgnoreCase("drizzle")) {
                                    animationView.setAnimation(R.raw.rainy_weather_animation);
                                }else if (txtCondition.equalsIgnoreCase("stormy")) {
                                    animationView.setAnimation(R.raw.stormy_weather_animation);
                                }
                                animationView.playAnimation();

                                Calendar calendar = Calendar.getInstance();
                                int day = calendar.get(Calendar.DAY_OF_WEEK);
                                int date = calendar.get(Calendar.DAY_OF_MONTH);
                                int month = calendar.get(Calendar.MONTH);

                                String[] days = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
                                String[] months = new String[] {"January", "February", "March", "April", "May", "June",
                                        "July", "August", "September", "October", "November", "December"};

                                dayDate.setText(days[day-1] + ", " + String.valueOf(date) + " " + months[month]);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        },
                        error -> {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("location", profile.getLocation().toLowerCase());
                        LocalTime time = LocalTime.now();
                        params.put("hour", String.valueOf(time.getHour()));
                        return params;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                queue.add(stringRequest);
            }else {
                Toast.makeText(getActivity(), "Error: No user data found!", Toast.LENGTH_SHORT).show();
            }
        }
        return root;
    }
}
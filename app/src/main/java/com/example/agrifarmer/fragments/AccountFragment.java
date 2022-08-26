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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.agrifarmer.R;
import com.example.agrifarmer.activities.CropHistoryActivity;
import com.example.agrifarmer.activities.registration.LoginActivity;
import com.example.agrifarmer.activities.registration.Register1Activity;
import com.example.agrifarmer.getterSetterClasses.Profile;
import com.example.agrifarmer.localDatabases.MyProfileDbHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class AccountFragment extends Fragment {

    private FirebaseAuth auth;
    private FirebaseUser user;

    private ImageView profile, edit;
    private TextView title, fullname, location, phone, email, loginBtn, signupBtn, updateBtn;
    private TextView weatherForecast, cropHistory, help, aboutUs, settings, logoutBtn;
    private EditText editFullName, editLocation, editPhone;
    private LinearLayout ll1, ll2, ll3, ll4, editll;
    private CardView updateBtnCV;

    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_account, container, false);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        MyProfileDbHandler profileDbHandler = new MyProfileDbHandler(getActivity());

        profile = root.findViewById(R.id.acc_profile);
        edit = root.findViewById(R.id.acc_edit);
        updateBtn = root.findViewById(R.id.acc_update_btn);
        updateBtnCV = root.findViewById(R.id.acc_update_btn_card_view);
        editFullName = root.findViewById(R.id.acc_edit_full_name);
        editLocation = root.findViewById(R.id.acc_edit_user_location);
        editPhone = root.findViewById(R.id.acc_edit_mobile_number);
        editll = root.findViewById(R.id.acc_edit_linear_layout);
        title = root.findViewById(R.id.acc_title);
        fullname = root.findViewById(R.id.acc_full_name);
        location = root.findViewById(R.id.acc_location);
        phone = root.findViewById(R.id.acc_phone);
        email = root.findViewById(R.id.acc_email);
        loginBtn = root.findViewById(R.id.acc_login_btn);
        signupBtn = root.findViewById(R.id.acc_signup_btn);
        weatherForecast = root.findViewById(R.id.acc_weather_tv);
        cropHistory = root.findViewById(R.id.acc_crop_history_tv);
        help = root.findViewById(R.id.acc_help_tv);
        aboutUs = root.findViewById(R.id.acc_about_us_tv);
        settings = root.findViewById(R.id.acc_settings_tv);
        logoutBtn = root.findViewById(R.id.acc_logout_btn);
        ll1 = root.findViewById(R.id.acc_ll_1);
        ll2 = root.findViewById(R.id.acc_ll_2);
        ll3 = root.findViewById(R.id.acc_ll_3);
        ll4 = root.findViewById(R.id.acc_ll_4);

        if (user == null) {
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            ll4.setVisibility(View.GONE);
        }else {
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
            ll3.setVisibility(View.VISIBLE);

            edit.setVisibility(View.VISIBLE);
            fullname.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);
            phone.setVisibility(View.VISIBLE);

            editll.setVisibility(View.GONE);
            updateBtnCV.setVisibility(View.GONE);

            Profile profile = profileDbHandler.getRegisteredUser();
            fullname.setText(profile.getFullname());
            location.setText(profile.getLocation());
            phone.setText(profile.getPhone());
            email.setText(profile.getEmail());
        }

        edit.setOnClickListener(view -> {
            edit.setVisibility(View.GONE);
            fullname.setVisibility(View.GONE);
            location.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);

            email.setGravity(Gravity.CENTER_HORIZONTAL);

            editll.setVisibility(View.VISIBLE);
            updateBtnCV.setVisibility(View.VISIBLE);

            Profile profile = profileDbHandler.getRegisteredUser();
            editFullName.setText(profile.getFullname());
            editPhone.setText(profile.getPhone());
            editLocation.setText(profile.getLocation());
        });

        updateBtn.setOnClickListener(view -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("email", email.getText().toString().trim());
            map.put("fullname", editFullName.getText().toString().trim());
            map.put("location", editLocation.getText().toString().trim());
            map.put("phone", editPhone.getText().toString().trim());

            FirebaseDatabase.getInstance().getReference().child("UserInfo").child(Objects.requireNonNull(auth.getUid())).updateChildren(map)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Profile updatedProfile = new Profile();
                            updatedProfile.setEmail(email.getText().toString().trim());
                            updatedProfile.setFullname(editFullName.getText().toString().trim());
                            updatedProfile.setLocation(editLocation.getText().toString().trim());
                            updatedProfile.setPhone(editPhone.getText().toString().trim());

                            profileDbHandler.updateUser(updatedProfile);

                            email.setGravity(Gravity.NO_GRAVITY);

                            Profile profile = profileDbHandler.getRegisteredUser();
                            fullname.setText(profile.getFullname());
                            location.setText(profile.getLocation());
                            phone.setText(profile.getPhone());

                            edit.setVisibility(View.VISIBLE);
                            fullname.setVisibility(View.VISIBLE);
                            location.setVisibility(View.VISIBLE);
                            phone.setVisibility(View.VISIBLE);

                            editll.setVisibility(View.GONE);
                            updateBtnCV.setVisibility(View.GONE);
                        }else {
                            Toast.makeText(getActivity(), "Failed to update profile!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(intent);
        });

        signupBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), Register1Activity.class);
            getActivity().startActivity(intent);
        });

        weatherForecast.setOnClickListener(view -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.home_activity_frame_container, new WeatherFragment());
            transaction.commit();
            BottomNavigationView bnv = getActivity().findViewById(R.id.home_activity_bottom_navigation_view);
            bnv.setSelectedItemId(R.id.nav_weather);
        });

        cropHistory.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CropHistoryActivity.class);
            startActivity(intent);
        });

        help.setOnClickListener(view -> {
            //Providing soon
        });

        aboutUs.setOnClickListener(view -> Toast.makeText(getActivity(), "This content will be provided soon!", Toast.LENGTH_SHORT).show());

        settings.setOnClickListener(view -> Toast.makeText(getActivity(), "This feature will be provided soon!", Toast.LENGTH_SHORT).show());

        logoutBtn.setOnClickListener(view -> {
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.message_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCancelable(false);

            dialog.show();

            LottieAnimationView animationView = dialog.findViewById(R.id.message_lottie_animation);
            TextView message = dialog.findViewById(R.id.message_textview);
            Button positiveBtn = dialog.findViewById(R.id.message_positive_btn);
            Button negativeBtn = dialog.findViewById(R.id.message_negative_btn);

            animationView.setAnimation(R.raw.are_you_sure_animation);
            animationView.loop(true);
            message.setText(R.string.confirmation);
            positiveBtn.setText(R.string.yes);
            negativeBtn.setText(R.string.no);

            positiveBtn.setOnClickListener(view1 -> {
                auth.signOut();
                profileDbHandler.deleteUser(profileDbHandler.getRegisteredUser().getEmail());
                negativeBtn.setVisibility(View.GONE);
                positiveBtn.setText(R.string.ok);
                animationView.setAnimation(R.raw.successfully_logout_animation);
                message.setText(R.string.success_logout);

                positiveBtn.setOnClickListener(view2 -> {
                    dialog.dismiss();
                    ll1.setVisibility(View.GONE);
                    ll4.setVisibility(View.GONE);
                    ll2.setVisibility(View.VISIBLE);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.home_activity_frame_container, new HomeFragment());
                    transaction.commit();
                    BottomNavigationView bnv = getActivity().findViewById(R.id.home_activity_bottom_navigation_view);
                    bnv.setSelectedItemId(R.id.nav_home);
                });
            });

            negativeBtn.setOnClickListener(view1 -> {
                dialog.dismiss();
            });
        });

        return root;
    }
}
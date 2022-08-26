package com.example.agrifarmer.activities.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.agrifarmer.R;
import com.example.agrifarmer.activities.MainActivity;
import com.example.agrifarmer.getterSetterClasses.Profile;
import com.example.agrifarmer.localDatabases.MyProfileDbHandler;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private ImageView backBtn, profileIcon;
    private TextView title, subtitle, forgotPassword, signup, login;
    private TextInputLayout email, password;
    private MaterialCheckBox rememberMe;
    private LinearLayout newUserSignup;

    private CountDownTimer backBtnTimer, signUpTimer;
    private Dialog dialog;
    private ProgressDialog progressDialog;

    private FirebaseAuth auth;

    MyProfileDbHandler profileDbHandler = new MyProfileDbHandler(LoginActivity.this);

    Animation animLeft1, animLeft2, animRight1, animRight2, animTop1, animTop2, animBottom1, animBottom2;
    Animation ranimLeft1, ranimLeft2, ranimRight1, ranimRight2, ranimTop1, ranimTop2, ranimBottom1, ranimBottom2;

    AnimationSet setAnimLeft = new AnimationSet(true);
    AnimationSet setAnimRight = new AnimationSet(true);
    AnimationSet setAnimTop = new AnimationSet(true);
    AnimationSet setAnimBottom = new AnimationSet(true);
    AnimationSet rsetAnimLeft = new AnimationSet(true);
    AnimationSet rsetAnimRight = new AnimationSet(true);
    AnimationSet rsetAnimTop = new AnimationSet(true);
    AnimationSet rsetAnimBottom = new AnimationSet(true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUIViews();
        setAndLoadAnimations();

        auth = FirebaseAuth.getInstance();

        signUpTimer = new CountDownTimer(1300, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                backBtn.setVisibility(View.INVISIBLE);
                profileIcon.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                subtitle.setVisibility(View.INVISIBLE);
                email.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                rememberMe.setVisibility(View.INVISIBLE);
                forgotPassword.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);
                newUserSignup.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(LoginActivity.this, Register1Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        };

        backBtnTimer = new CountDownTimer(1300, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                backBtn.setVisibility(View.INVISIBLE);
                profileIcon.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                subtitle.setVisibility(View.INVISIBLE);
                email.setVisibility(View.INVISIBLE);
                password.setVisibility(View.INVISIBLE);
                rememberMe.setVisibility(View.INVISIBLE);
                forgotPassword.setVisibility(View.INVISIBLE);
                login.setVisibility(View.INVISIBLE);
                newUserSignup.setVisibility(View.INVISIBLE);

                finish();
            }
        };

        backBtn.startAnimation(setAnimLeft);
        profileIcon.startAnimation(setAnimTop);
        title.startAnimation(setAnimLeft);
        subtitle.startAnimation(setAnimLeft);
        email.startAnimation(setAnimRight);
        password.startAnimation(setAnimLeft);
        rememberMe.startAnimation(setAnimLeft);
        forgotPassword.startAnimation(setAnimRight);
        login.startAnimation(setAnimBottom);
        newUserSignup.startAnimation(setAnimBottom);

        backBtn.setOnClickListener(view -> onBackPressed());

        signup.setOnClickListener(view -> {
            backBtn.startAnimation(rsetAnimLeft);
            profileIcon.startAnimation(rsetAnimTop);
            title.startAnimation(rsetAnimLeft);
            subtitle.startAnimation(rsetAnimLeft);
            email.startAnimation(rsetAnimRight);
            password.startAnimation(rsetAnimLeft);
            rememberMe.startAnimation(rsetAnimLeft);
            forgotPassword.startAnimation(rsetAnimRight);
            login.startAnimation(rsetAnimBottom);
            newUserSignup.startAnimation(rsetAnimBottom);

            signUpTimer.start();
        });

        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String txtEmail = email.getEditText().getText().toString().trim();
                if (txtEmail.equals(""))
                    email.setError("Empty Credential!");
                else if (!txtEmail.matches(emailPattern))
                    email.setError("Invalid Email!");
                else
                    email.setError(null);
            }
        });

        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*+=()])(?=\\S+$).{8,20}$";
                String txtPassword = password.getEditText().getText().toString().trim();
                if (txtPassword.equals(""))
                    password.setError("Empty Credential!");
                else if (!txtPassword.matches(passwordPattern))
                    // Add a dialog here to specify all conditions for password
                    password.setError("Doesn't meet all conditions!");
                else
                    password.setError(null);
            }
        });

        login.setOnClickListener(view -> {
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setCancelable(false);
            String txtEmail = email.getEditText().getText().toString().trim();
            String txtPassword = password.getEditText().getText().toString().trim();

            if (txtEmail.equals("") || txtPassword.equals("")) {
                if (txtEmail.equals("")) {
                    email.setError("Empty Credential!");
                }
                if (txtPassword.equals("")) {
                    password.setError("Empty Credential!");
                }
            }else {
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                dialog = new Dialog(LoginActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.message_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                LottieAnimationView animationView = dialog.findViewById(R.id.message_lottie_animation);
                TextView message = dialog.findViewById(R.id.message_textview);
                Button positiveBtn = dialog.findViewById(R.id.message_positive_btn);
                Button negativeBtn = dialog.findViewById(R.id.message_negative_btn);
                negativeBtn.setVisibility(View.GONE);

                if (email.getError() == null && password.getError() == null) {
                    auth.signInWithEmailAndPassword(txtEmail, txtPassword)
                            .addOnCompleteListener(this, task -> {
                                progressDialog.dismiss();

                                if (task.isSuccessful()) {
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("UserInfo").child(auth.getUid());
                                    reference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Profile profile = snapshot.getValue(Profile.class);
                                            new Handler().postDelayed(() -> profileDbHandler.addUser(profile), 2000);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    animationView.setAnimation(R.raw.success_animation);
                                    message.setText(R.string.user_successful_login);
                                    positiveBtn.setText(R.string.ok);
                                    positiveBtn.setOnClickListener(view1 -> {
                                        dialog.dismiss();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    });
                                }else {
                                    String error = Objects.requireNonNull(task.getException()).toString();
                                    String[] separated = error.split(":");

                                    animationView.setAnimation(R.raw.failed_animation);
                                    message.setText(separated[1]);
                                    positiveBtn.setText(R.string.try_again);
                                    positiveBtn.setOnClickListener(view1 -> dialog.dismiss());
                                }
                                dialog.show();
                            });
                }else {
                    progressDialog.dismiss();

                    animationView.setAnimation(R.raw.failed_animation);
                    message.setText(R.string.credential_fail);
                    positiveBtn.setText(R.string.try_again);
                    positiveBtn.setOnClickListener(view1 -> dialog.dismiss());
                }
            }
        });
    }

    private void setUIViews() {
        scrollView = findViewById(R.id.login_scrollview);
        backBtn = findViewById(R.id.login_back_btn);
        profileIcon = findViewById(R.id.login_profile_icon);
        title = findViewById(R.id.login_welcome_textview);
        subtitle = findViewById(R.id.login_subtitle_textview);
        forgotPassword = findViewById(R.id.login_forgot_password_textview);
        signup = findViewById(R.id.login_signup_btn);
        email = findViewById(R.id.login_email_textInputLayout);
        password = findViewById(R.id.login_password_textInputLayout);
        rememberMe = findViewById(R.id.login_remember_me_checkBox);
        login = findViewById(R.id.login_btn);
        newUserSignup = findViewById(R.id.login_new_user_signup_textview);
    }

    private void setAndLoadAnimations() {
        animLeft1 = AnimationUtils.loadAnimation(this, R.anim.anim_left_first);
        animLeft2 = AnimationUtils.loadAnimation(this, R.anim.anim_left_second);
        animRight1 = AnimationUtils.loadAnimation(this, R.anim.anim_right_first);
        animRight2 = AnimationUtils.loadAnimation(this, R.anim.anim_right_second);
        animTop1 = AnimationUtils.loadAnimation(this, R.anim.anim_top_first);
        animTop2 = AnimationUtils.loadAnimation(this, R.anim.anim_top_second);
        animBottom1 = AnimationUtils.loadAnimation(this, R.anim.anim_bottom_first);
        animBottom2 = AnimationUtils.loadAnimation(this, R.anim.anim_bottom_second);
        ranimLeft1 = AnimationUtils.loadAnimation(this, R.anim.ranim_left_first);
        ranimLeft2 = AnimationUtils.loadAnimation(this, R.anim.ranim_left_second);
        ranimRight1 = AnimationUtils.loadAnimation(this, R.anim.ranim_right_first);
        ranimRight2 = AnimationUtils.loadAnimation(this, R.anim.ranim_right_second);
        ranimTop1 = AnimationUtils.loadAnimation(this, R.anim.ranim_top_first);
        ranimTop2 = AnimationUtils.loadAnimation(this, R.anim.ranim_top_second);
        ranimBottom1 = AnimationUtils.loadAnimation(this, R.anim.ranim_bottom_first);
        ranimBottom2 = AnimationUtils.loadAnimation(this, R.anim.ranim_bottom_second);

        setAnimLeft.addAnimation(animLeft1);
        setAnimLeft.addAnimation(animLeft2);
        setAnimRight.addAnimation(animRight1);
        setAnimRight.addAnimation(animRight2);
        setAnimTop.addAnimation(animTop1);
        setAnimTop.addAnimation(animTop2);
        setAnimBottom.addAnimation(animBottom1);
        setAnimBottom.addAnimation(animBottom2);
        rsetAnimLeft.addAnimation(ranimLeft1);
        rsetAnimLeft.addAnimation(ranimLeft2);
        rsetAnimRight.addAnimation(ranimRight1);
        rsetAnimRight.addAnimation(ranimRight2);
        rsetAnimTop.addAnimation(ranimTop1);
        rsetAnimTop.addAnimation(ranimTop2);
        rsetAnimBottom.addAnimation(ranimBottom1);
        rsetAnimBottom.addAnimation(ranimBottom2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backBtn.startAnimation(rsetAnimLeft);
        profileIcon.startAnimation(rsetAnimTop);
        title.startAnimation(rsetAnimLeft);
        subtitle.startAnimation(rsetAnimLeft);
        email.startAnimation(rsetAnimRight);
        password.startAnimation(rsetAnimLeft);
        rememberMe.startAnimation(rsetAnimLeft);
        forgotPassword.startAnimation(rsetAnimRight);
        login.startAnimation(rsetAnimBottom);
        newUserSignup.startAnimation(rsetAnimBottom);

        backBtnTimer.start();
    }
}
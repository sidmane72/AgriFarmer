package com.example.agrifarmer.activities.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Register2Activity extends AppCompatActivity {

    private ScrollView scrollView;
    private ImageView backBtn;
    private TextView title, register;
    private TextInputLayout enterPass, confirmPass, referralCode;
    private MaterialCheckBox haveReferralCode;

    private CountDownTimer backBtnTimer;
    private ProgressDialog progressDialog;
    private Dialog dialog;

    private String txtFullName, txtEmail, txtPhone, txtLocation;

    private FirebaseAuth auth;

    private MyProfileDbHandler profileDbHandler = new MyProfileDbHandler(Register2Activity.this);

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
        setContentView(R.layout.activity_register2);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUIViews();
        setAndLoadAnimations();

        auth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        txtFullName = intent.getStringExtra("FullName");
        txtEmail = intent.getStringExtra("Email");
        txtPhone = intent.getStringExtra("Phone");
        txtLocation = intent.getStringExtra("Location");

        backBtnTimer = new CountDownTimer(1300, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                backBtn.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                enterPass.setVisibility(View.INVISIBLE);
                confirmPass.setVisibility(View.INVISIBLE);
                haveReferralCode.setVisibility(View.INVISIBLE);
                register.setVisibility(View.INVISIBLE);

                finish();
            }
        };

        backBtn.startAnimation(setAnimLeft);
        title.startAnimation(setAnimLeft);
        enterPass.startAnimation(setAnimRight);
        confirmPass.startAnimation(setAnimLeft);
        haveReferralCode.startAnimation(setAnimLeft);
        register.startAnimation(setAnimBottom);

        enterPass.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*+=()])(?=\\S+$).{8,20}$";
                String txtEnterPass = enterPass.getEditText().getText().toString().trim();
                if (txtEnterPass.equals(""))
                    enterPass.setError("Empty Credential!");
                else if (!txtEnterPass.matches(passwordPattern))
                    enterPass.setError("Doesn't meet all conditions!");
                else
                    enterPass.setError(null);
            }
        });

        confirmPass.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*+=()])(?=\\S+$).{8,20}$";
                String txtEnterP = enterPass.getEditText().getText().toString().trim();
                String txtConfirmP = confirmPass.getEditText().getText().toString().trim();
                if (!txtConfirmP.equals(txtEnterP))
                    confirmPass.setError("Password doesn't match!");
                else if (!txtConfirmP.matches(passwordPattern))
                    confirmPass.setError("Doesn't meet all conditions!");
                else
                    confirmPass.setError(null);
            }
        });

        backBtn.setOnClickListener(view -> onBackPressed());

        haveReferralCode.setOnClickListener(view -> {
            if (haveReferralCode.isChecked())
                referralCode.setVisibility(View.VISIBLE);
            else
                referralCode.setVisibility(View.INVISIBLE);
        });

        register.setOnClickListener(view -> {
            progressDialog = new ProgressDialog(Register2Activity.this);
            progressDialog.setCancelable(false);

            String enterP = enterPass.getEditText().getText().toString().trim();
            String confirmP = confirmPass.getEditText().getText().toString().trim();

            if (enterP.equals("") || confirmP.equals("")) {
                if (enterP.equals(""))
                    enterPass.setError("Empty Credential!");
                if (confirmP.equals(""))
                    confirmPass.setError("EmptyCredential!");
            }else{
                progressDialog.setMessage("Registering...");
                progressDialog.show();

                dialog = new Dialog(Register2Activity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.message_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                LottieAnimationView animationView = dialog.findViewById(R.id.message_lottie_animation);
                TextView message = dialog.findViewById(R.id.message_textview);
                Button positiveBtn = dialog.findViewById(R.id.message_positive_btn);
                Button negativeBtn = dialog.findViewById(R.id.message_negative_btn);
                negativeBtn.setVisibility(View.GONE);

                if (enterPass.getError() == null && confirmPass.getError() == null) {
                    auth.createUserWithEmailAndPassword(txtEmail, confirmP)
                            .addOnCompleteListener(this, task -> {
                                progressDialog.dismiss();

                                if (task.isSuccessful()) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("fullname", txtFullName);
                                    map.put("email", txtEmail);
                                    map.put("phone", txtPhone);
                                    map.put("location", txtLocation);
                                    Toast.makeText(this, auth.getUid(), Toast.LENGTH_SHORT).show();
                                    FirebaseDatabase.getInstance().getReference().child("UserInfo").child(auth.getUid()).updateChildren(map);

                                    Profile profile = new Profile();
                                    profile.setEmail(txtEmail);
                                    profile.setFullname(txtFullName);
                                    profile.setLocation(txtLocation);
                                    profile.setPhone(txtPhone);
                                    profileDbHandler.addUser(profile);

                                    animationView.setAnimation(R.raw.success_animation);
                                    message.setText(R.string.user_successful_login);
                                    positiveBtn.setText(R.string.ok);
                                    positiveBtn.setOnClickListener(view1 -> {
                                        dialog.dismiss();
                                        startActivity(new Intent(Register2Activity.this, MainActivity.class));
                                        finish();
                                    });
                                    dialog.show();
                                }else {
                                    String error = Objects.requireNonNull(task.getException()).toString();
                                    String[] separated = error.split(":");

                                    animationView.setAnimation(R.raw.failed_animation);
                                    message.setText(separated[1]);
                                    positiveBtn.setText(R.string.try_again);
                                    positiveBtn.setOnClickListener(view1 -> dialog.dismiss());
                                    dialog.show();
                                }
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
        scrollView = findViewById(R.id.register2_scrollview);
        backBtn = findViewById(R.id.register2_back_btn);
        title = findViewById(R.id.register2_create_password_textview);
        register = findViewById(R.id.register2_register_btn);
        enterPass = findViewById(R.id.register2_enter_password_textInputLayout);
        confirmPass = findViewById(R.id.register2_confirm_password_textInputLayout);
        referralCode = findViewById(R.id.register2_referral_code_textInputLayout);
        haveReferralCode = findViewById(R.id.register2_referral_code_checkbox);
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
}
package com.example.agrifarmer.activities.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agrifarmer.R;
import com.google.android.material.textfield.TextInputLayout;

public class Register1Activity extends AppCompatActivity {

    private ScrollView scrollView;
    private ImageView backBtn;
    private TextView title, next, login;
    private TextInputLayout fullname, email, phone, location;
    private AutoCompleteTextView autoCompleteLocations;
    private LinearLayout alreadyRegistered;

    private CountDownTimer backBtnTimer, loginBtnTimer;

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
        setContentView(R.layout.activity_register1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUIViews();
        setAndLoadAnimations();

        String[] locations = getResources().getStringArray(R.array.locations);
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, R.layout.dropdown_item, locations);
        autoCompleteLocations.setAdapter(locationAdapter);

        backBtnTimer = new CountDownTimer(1300, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                backBtn.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                fullname.setVisibility(View.INVISIBLE);
                email.setVisibility(View.INVISIBLE);
                phone.setVisibility(View.INVISIBLE);
                location.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);
                alreadyRegistered.setVisibility(View.INVISIBLE);

                finish();
            }
        };

        loginBtnTimer = new CountDownTimer(1300, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                backBtn.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                fullname.setVisibility(View.INVISIBLE);
                email.setVisibility(View.INVISIBLE);
                phone.setVisibility(View.INVISIBLE);
                location.setVisibility(View.INVISIBLE);
                next.setVisibility(View.INVISIBLE);
                alreadyRegistered.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(Register1Activity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        };

        backBtn.startAnimation(setAnimLeft);
        title.startAnimation(setAnimLeft);
        fullname.startAnimation(setAnimRight);
        email.startAnimation(setAnimLeft);
        phone.startAnimation(setAnimRight);
        location.startAnimation(setAnimLeft);
        next.startAnimation(setAnimRight);
        alreadyRegistered.startAnimation(setAnimBottom);

        backBtn.setOnClickListener(View -> onBackPressed());

        login.setOnClickListener(View -> {
            backBtn.startAnimation(rsetAnimLeft);
            title.startAnimation(rsetAnimLeft);
            fullname.startAnimation(rsetAnimRight);
            email.startAnimation(rsetAnimLeft);
            phone.startAnimation(rsetAnimRight);
            location.startAnimation(rsetAnimLeft);
            next.startAnimation(rsetAnimRight);
            alreadyRegistered.startAnimation(rsetAnimBottom);

            loginBtnTimer.start();
        });

        fullname.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String txtFullName = fullname.getEditText().getText().toString().trim();
                if (txtFullName.equals(""))
                    fullname.setError("Empty Credential!");
                else if (txtFullName.length() < 4)
                    fullname.setError("Credential too short!");
                else if (txtFullName.length() > 20)
                    fullname.setError("Credential too long!");
                else
                    fullname.setError(null);
            }
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

        phone.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String phonePattern = "\\d{10}";
                String txtPhone = phone.getEditText().getText().toString().trim();
                if (txtPhone.equals(""))
                    phone.setError("Empty Credential!");
                else if (!txtPhone.matches(phonePattern))
                    phone.setError("Invalid Phone Number!");
                else
                    phone.setError(null);
            }
        });

        autoCompleteLocations.setOnItemClickListener((adapterView, view, i, l) -> location.setError(null));

        next.setOnClickListener(view -> {
            String txtFullName = fullname.getEditText().getText().toString().trim();
            String txtEmail = email.getEditText().getText().toString().trim();
            String txtPhone = phone.getEditText().getText().toString().trim();
            String txtLocation = autoCompleteLocations.getText().toString().trim();

            if (txtFullName.equals("") || txtEmail.equals("") || txtPhone.equals("") || txtLocation.equals("")) {
                if (txtFullName.equals(""))
                    fullname.setError("Empty Credential!");
                if (txtEmail.equals(""))
                    email.setError("Empty Credential!");
                if (txtPhone.equals(""))
                    phone.setError("Empty Credential!");
                if (txtLocation.equals(""))
                    location.setError("Please Select Location!");
            }else {
                if (fullname.getError() == null && email.getError() == null && phone.getError() == null && location.getError() == null) {
                    CountDownTimer timer = new CountDownTimer(1300, 100) {
                        @Override
                        public void onTick(long l) {}
                        @Override
                        public void onFinish() {
                            backBtn.setVisibility(View.INVISIBLE);
                            title.setVisibility(View.INVISIBLE);
                            fullname.setVisibility(View.INVISIBLE);
                            email.setVisibility(View.INVISIBLE);
                            phone.setVisibility(View.INVISIBLE);
                            location.setVisibility(View.INVISIBLE);
                            next.setVisibility(View.INVISIBLE);
                            alreadyRegistered.setVisibility(View.INVISIBLE);

                            Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            intent.putExtra("FullName", txtFullName);
                            intent.putExtra("Email", txtEmail);
                            intent.putExtra("Phone", txtPhone);
                            intent.putExtra("Location", txtLocation);

                            startActivity(intent);
                        }
                    };

                    backBtn.startAnimation(rsetAnimLeft);
                    title.startAnimation(rsetAnimLeft);
                    fullname.startAnimation(rsetAnimRight);
                    email.startAnimation(rsetAnimLeft);
                    phone.startAnimation(rsetAnimRight);
                    location.startAnimation(rsetAnimLeft);
                    next.startAnimation(rsetAnimRight);
                    alreadyRegistered.startAnimation(rsetAnimBottom);

                    timer.start();
                }else {
                    //Add dialog here and after that remove below toast
                    Toast.makeText(this, "Failed to Register! Try Again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUIViews() {
        scrollView = findViewById(R.id.register1_scrollview);
        backBtn = findViewById(R.id.register1_back_btn);
        title = findViewById(R.id.register1_create_account_textview);
        next = findViewById(R.id.register1_next_btn);
        login = findViewById(R.id.register1_login_btn);
        fullname = findViewById(R.id.register1_fullname_textInputLayout);
        email = findViewById(R.id.register1_email_textInputLayout);
        phone = findViewById(R.id.register1_phone_textInputLayout);
        location = findViewById(R.id.register1_locations_textInputLayout);
        autoCompleteLocations = findViewById(R.id.register1_locations_autocomplete_textview);
        alreadyRegistered = findViewById(R.id.register1_already_registered_login_textview);
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
        title.startAnimation(rsetAnimLeft);
        fullname.startAnimation(rsetAnimRight);
        email.startAnimation(rsetAnimLeft);
        phone.startAnimation(rsetAnimRight);
        location.startAnimation(rsetAnimLeft);
        next.startAnimation(rsetAnimRight);
        alreadyRegistered.startAnimation(rsetAnimBottom);

        backBtnTimer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backBtn.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        fullname.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        phone.setVisibility(View.VISIBLE);
        location.setVisibility(View.VISIBLE);
        next.setVisibility(View.VISIBLE);
        alreadyRegistered.setVisibility(View.VISIBLE);
    }
}
package com.example.agrifarmer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.agrifarmer.R;

public class SplashActivity extends AppCompatActivity {

    private TextView title, subtitle;
    private View horizontalView;
    private LottieAnimationView icon;

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
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUIViews();
        setAndLoadAnimations();

        icon.startAnimation(setAnimRight);
        title.startAnimation(setAnimTop);
        subtitle.startAnimation(setAnimBottom);
        horizontalView.startAnimation(setAnimLeft);

        CountDownTimer timer1 = new CountDownTimer(1300, 500) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                icon.setVisibility(View.INVISIBLE);
                title.setVisibility(View.INVISIBLE);
                subtitle.setVisibility(View.INVISIBLE);
                horizontalView.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        };

        CountDownTimer timer2 = new CountDownTimer(icon.getDuration() + 1000, 500) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                timer1.start();
                icon.startAnimation(rsetAnimRight);
                title.startAnimation(rsetAnimTop);
                subtitle.startAnimation(rsetAnimBottom);
                horizontalView.startAnimation(rsetAnimLeft);
            }
        };

        CountDownTimer timer3 = new CountDownTimer(2000, 100) {
            @Override
            public void onTick(long l) {}
            @Override
            public void onFinish() {
                timer2.start();
                icon.playAnimation();
            }
        };

        timer3.start();
    }

    private void setUIViews() {
        title = findViewById(R.id.splash_title);
        subtitle = findViewById(R.id.splash_subtitle);
        horizontalView = findViewById(R.id.splash_horizontal_view);
        icon = findViewById(R.id.splash_icon);
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
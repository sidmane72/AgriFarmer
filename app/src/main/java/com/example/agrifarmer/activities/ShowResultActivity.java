package com.example.agrifarmer.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.agrifarmer.R;

import java.util.HashMap;

public class ShowResultActivity extends AppCompatActivity {

    private TextView location, duration, crop1, crop2, crop3;
    private ImageView icrop1, icrop2, icrop3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUIViews();

        Intent intent = getIntent();
        location.setText(intent.getStringExtra("location"));
        duration.setText("Duration: " + intent.getStringExtra("duration") + " months");

        String strCrop1 = intent.getStringExtra("crop1");
        String strCrop2 = intent.getStringExtra("crop2");
        String strCrop3 = intent.getStringExtra("crop3");

        crop1.setText(strCrop1);
        crop2.setText(strCrop2);
        crop3.setText(strCrop3);

        HashMap<String, Drawable> crops = new HashMap<>();
        crops.put("rice", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.rice));
        crops.put("bajra", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.bajra));
        crops.put("cotton", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.cotton));
        crops.put("gram", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.gram));
        crops.put("groundnut", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.groundnut));
        crops.put("jowar", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.jowar));
        crops.put("maize", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.maize));
        crops.put("ragi", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.ragi));
        crops.put("soyabean", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.soyabean));
        crops.put("sugarcane", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.sugarcane));
        crops.put("wheat", AppCompatResources.getDrawable(ShowResultActivity.this, R.drawable.wheat));

        icrop1.setImageDrawable(crops.get(strCrop1.toLowerCase()));
        icrop2.setImageDrawable(crops.get(strCrop2.toLowerCase()));
        icrop3.setImageDrawable(crops.get(strCrop3.toLowerCase()));

    }

    private void setUIViews() {
        location = findViewById(R.id.result_location);
        duration = findViewById(R.id.result_duration);
        crop1 = findViewById(R.id.result_crop1);
        crop2 = findViewById(R.id.result_crop2);
        crop3 = findViewById(R.id.result_crop3);
        icrop1 = findViewById(R.id.result_crop1_iv);
        icrop2 = findViewById(R.id.result_crop2_iv);
        icrop3 = findViewById(R.id.result_crop3_iv);
    }
}
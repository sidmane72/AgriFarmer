package com.example.agrifarmer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.agrifarmer.R;
import com.example.agrifarmer.getterSetterClasses.CropRecord;
import com.example.agrifarmer.localDatabases.CropRecordDbHandler;
import com.example.agrifarmer.relativeLayoutHandler.CropHistoryAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CropHistoryActivity extends AppCompatActivity {

    private LinearLayout noUser, yesUser;
    private RecyclerView recyclerRecords;
    private TextView loginText;

    private FirebaseAuth auth;
    private FirebaseUser user;

    ArrayList<CropRecord> records;

    CropRecordDbHandler handler = new CropRecordDbHandler(CropHistoryActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_history);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setUIViews();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        recyclerRecords.setNestedScrollingEnabled(false);
        records = handler.getAllRecords();

        if (user == null || records.size() == 0) {
            noUser.setVisibility(View.VISIBLE);
            yesUser.setVisibility(View.GONE);
            if (records.size() == 0)
                loginText.setVisibility(View.GONE);
            else
                loginText.setVisibility(View.VISIBLE);
        }else {
            noUser.setVisibility(View.GONE);
            yesUser.setVisibility(View.VISIBLE);

            LinearLayoutManager manager = new LinearLayoutManager(CropHistoryActivity.this, LinearLayoutManager.VERTICAL, false);
            recyclerRecords.setLayoutManager(manager);
            recyclerRecords.setItemAnimator(new DefaultItemAnimator());
            recyclerRecords.setAdapter(new CropHistoryAdapter(CropHistoryActivity.this, records, this));
        }
    }

    private void setUIViews() {
        noUser = findViewById(R.id.history_no_user_ll);
        yesUser = findViewById(R.id.history_yes_user_ll);
        recyclerRecords = findViewById(R.id.history_recycler_view);
        loginText = findViewById(R.id.history_login_text);
    }
}
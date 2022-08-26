package com.example.agrifarmer.relativeLayoutHandler;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agrifarmer.R;
import com.example.agrifarmer.activities.ShowResultActivity;
import com.example.agrifarmer.getterSetterClasses.CropRecord;

import java.util.ArrayList;
import java.util.HashMap;

public class CropHistoryAdapter extends RecyclerView.Adapter<CropHistoryAdapter.ViewHolder> {
    Context context;
    ArrayList<CropRecord> records;
    Activity activity;

    public CropHistoryAdapter(Context context, ArrayList<CropRecord> records, Activity activity) {
        this.context = context;
        this.records = records;
        this.activity = activity;
    }

    @NonNull
    @Override
    public CropHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crop_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropHistoryAdapter.ViewHolder holder, int position) {
        String[] dateTime = records.get(position).getDatetime().split(" ");
        String crop1 = records.get(position).getCrop1();
        String crop2 = records.get(position).getCrop2();
        String crop3 = records.get(position).getCrop3();
        holder.time.setText(dateTime[1]);
        holder.date.setText(dateTime[0]);
        holder.userLocation.setText(records.get(position).getLocation());
        holder.duration.setText("Duration: " + records.get(position).getDuration() + " months");
        holder.crop1.setText(crop1);
        holder.crop2.setText(crop2);
        holder.crop3.setText(crop3);

        HashMap<String, Drawable> crops = new HashMap<>();
        crops.put("rice", AppCompatResources.getDrawable(context, R.drawable.rice));
        crops.put("bajra", AppCompatResources.getDrawable(context, R.drawable.bajra));
        crops.put("cotton", AppCompatResources.getDrawable(context, R.drawable.cotton));
        crops.put("gram", AppCompatResources.getDrawable(context, R.drawable.gram));
        crops.put("groundnut", AppCompatResources.getDrawable(context, R.drawable.groundnut));
        crops.put("jowar", AppCompatResources.getDrawable(context, R.drawable.jowar));
        crops.put("maize", AppCompatResources.getDrawable(context, R.drawable.maize));
        crops.put("ragi", AppCompatResources.getDrawable(context, R.drawable.ragi));
        crops.put("soyabean", AppCompatResources.getDrawable(context, R.drawable.soyabean));
        crops.put("sugarcane", AppCompatResources.getDrawable(context, R.drawable.sugarcane));
        crops.put("wheat", AppCompatResources.getDrawable(context, R.drawable.wheat));

        holder.imgCrop1.setImageDrawable(crops.get(crop1.toLowerCase()));
        holder.imgCrop2.setImageDrawable(crops.get(crop2.toLowerCase()));
        holder.imgCrop3.setImageDrawable(crops.get(crop3.toLowerCase()));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView time, date, userLocation, duration, crop1, crop2, crop3;
        ImageView imgCrop1, imgCrop2, imgCrop3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.crop_record_time);
            date = itemView.findViewById(R.id.crop_record_date);
            userLocation = itemView.findViewById(R.id.crop_record_location);
            duration = itemView.findViewById(R.id.crop_record_duration);
            crop1 = itemView.findViewById(R.id.crop_record_crop_1_tv);
            crop2 = itemView.findViewById(R.id.crop_record_crop_2_tv);
            crop3 = itemView.findViewById(R.id.crop_record_crop_3_tv);
            imgCrop1 = itemView.findViewById(R.id.crop_record_crop_1_iv);
            imgCrop2 = itemView.findViewById(R.id.crop_record_crop_2_iv);
            imgCrop3 = itemView.findViewById(R.id.crop_record_crop_3_iv);
        }
    }
}

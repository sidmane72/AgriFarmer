package com.example.agrifarmer.localDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agrifarmer.getterSetterClasses.CropRecord;
import com.example.agrifarmer.params.CropRecordParams;

import java.util.ArrayList;

public class CropRecordDbHandler extends SQLiteOpenHelper {
    public CropRecordDbHandler(Context context) {
        super(context, CropRecordParams.DB_NAME, null, CropRecordParams.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE " + CropRecordParams.TABLE_NAME + " ("
                + CropRecordParams.KEY_DATE_TIME + " VARCHAR(20) PRIMARY KEY, "
                + CropRecordParams.KEY_LOCATION + " VARCHAR(10), "
                + CropRecordParams.KEY_DURATION + " VARCHAR(10), "
                + CropRecordParams.KEY_CROP1 + " VARCHAR(15), "
                + CropRecordParams.KEY_CROP2 + " VARCHAR(15), "
                + CropRecordParams.KEY_CROP3 + " VARCHAR(15))";

        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void addRecord(CropRecord cropRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CropRecordParams.KEY_DATE_TIME, cropRecord.getDatetime());
        values.put(CropRecordParams.KEY_LOCATION, cropRecord.getLocation());
        values.put(CropRecordParams.KEY_DURATION, cropRecord.getDuration());
        values.put(CropRecordParams.KEY_CROP1, cropRecord.getCrop1());
        values.put(CropRecordParams.KEY_CROP2, cropRecord.getCrop2());
        values.put(CropRecordParams.KEY_CROP3, cropRecord.getCrop3());

        db.insert(CropRecordParams.TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<CropRecord> getAllRecords() {
        ArrayList<CropRecord> recordList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + CropRecordParams.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                CropRecord record = new CropRecord();
                record.setDatetime(cursor.getString(0));
                record.setLocation(cursor.getString(1));
                record.setDuration(cursor.getString(2));
                record.setCrop1(cursor.getString(3));
                record.setCrop2(cursor.getString(4));
                record.setCrop3(cursor.getString(5));
                recordList.add(record);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recordList;
    }
}

package com.example.agrifarmer.localDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.agrifarmer.getterSetterClasses.Profile;
import com.example.agrifarmer.params.ProfileParams;

public class MyProfileDbHandler extends SQLiteOpenHelper {
    public MyProfileDbHandler(Context context) {
        super(context, ProfileParams.DB_NAME, null, ProfileParams.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE " + ProfileParams.TABLE_NAME + " ("
                + ProfileParams.KEY_EMAIL + " VARCHAR(50) PRIMARY KEY, "
                + ProfileParams.KEY_FULLNAME + " VARCHAR(50), "
                + ProfileParams.KEY_LOCATION + " VARCHAR(20), "
                + ProfileParams.KEY_PHONE + " VARCHAR(12))";

        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void addUser(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ProfileParams.KEY_EMAIL, profile.getEmail());
        values.put(ProfileParams.KEY_FULLNAME, profile.getFullname());
        values.put(ProfileParams.KEY_LOCATION, profile.getLocation());
        values.put(ProfileParams.KEY_PHONE, profile.getPhone());

        db.insert(ProfileParams.TABLE_NAME, null, values);
        db.close();
    }

    public Profile getRegisteredUser() {
        Profile profile = new Profile();
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + ProfileParams.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            profile.setEmail(cursor.getString(0));
            profile.setFullname(cursor.getString(1));
            profile.setLocation(cursor.getString(2));
            profile.setPhone(cursor.getString(3));
        }
        cursor.close();
        db.close();
        return profile;
    }

    public boolean userAlreadyExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + ProfileParams.TABLE_NAME;
        Cursor cursor = db.rawQuery(select, null);
        if (cursor.getCount() > 0) {
            return true;
        }
        cursor.close();
        db.close();
        return false;
    }

    public void deleteUser(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ProfileParams.TABLE_NAME, ProfileParams.KEY_EMAIL + "=?", new String[] {email});
        db.close();
    }

    public void updateUser(Profile profile) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ProfileParams.KEY_EMAIL, profile.getEmail());
        values.put(ProfileParams.KEY_FULLNAME, profile.getFullname());
        values.put(ProfileParams.KEY_LOCATION, profile.getLocation());
        values.put(ProfileParams.KEY_PHONE, profile.getPhone());

        db.update(ProfileParams.TABLE_NAME, values, ProfileParams.KEY_EMAIL + "=?", new String[]{profile.getEmail()});
        db.close();
    }
}

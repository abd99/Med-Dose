package com.abdsoft.med_dose.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.abdsoft.med_dose.ui.home.HomeItem;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Med-Dose";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "Medicines";
    public static final String KEY_ID = "ID";
    public static final String KEY_NAME = "Name";
    public static final String KEY_DAY = "Day";
    public static final String KEY_MONTH = "Month";
    public static final String KEY_YEAR= "Year";
    public static final String KEY_TIMES_PER_DAY= "TimesPerDay";
    public static final String KEY_TOTAL_DOSES= "TotalDoses";
    public static final String KEY_TIMINGS= "Timings";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s TEXT NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s INTEGER NOT NULL, " +
                "%s TEXT NOT NULL);", DB_TABLE, KEY_ID, KEY_NAME, KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_TIMES_PER_DAY, KEY_TOTAL_DOSES, KEY_TIMINGS);
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String queryUpgrade = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        sqLiteDatabase.execSQL(queryUpgrade);
        onCreate(sqLiteDatabase);
    }

    public void insertNewMedicine(String medicineName, int day, int month, int year, int noOfTimesPerDay, int totalDoses, String timings) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, medicineName);
        values.put(KEY_DAY , day);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_TIMES_PER_DAY, noOfTimesPerDay);
        values.put(KEY_TOTAL_DOSES, totalDoses);
        values.put(KEY_TIMINGS, timings);
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteMedicine(String medicineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, KEY_NAME + " = ?", new String[]{medicineName});
        db.close();
    }

    public List<HomeItem> getMedicineList() {
        List<HomeItem> medicineList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_TIMES_PER_DAY}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HomeItem homeItem = new HomeItem(cursor.getString(0)  , cursor.getString(1) + " times a day");
            medicineList.add(homeItem);
        }
        return medicineList;
       /* JSONObject json = new JSONObject(stringreadfromsqlite);
        ArrayList items = json.optJSONArray("uniqueArrays");*/
    }
}

package com.abdsoft.med_dose.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.abdsoft.med_dose.ui.dashboard.HistoryItem;
import com.abdsoft.med_dose.ui.home.HomeItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Med-Dose";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "Medicines";
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "Name";
    private static final String KEY_DAY = "Day";
    private static final String KEY_MONTH = "Month";
    private static final String KEY_YEAR= "Year";
    private static final String KEY_TIMES_PER_DAY= "TimesPerDay";
    private static final String KEY_TOTAL_DOSES= "TotalDoses";
    private static final String KEY_TIMINGS= "Timings";
    private static final String KEY_ALERT_TYPE = "AlertType";


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
                "%s TEXT NOT NULL, " +
                "%s TEXT NOT NULL);", DB_TABLE, KEY_ID, KEY_NAME, KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_TIMES_PER_DAY, KEY_TOTAL_DOSES, KEY_TIMINGS, KEY_ALERT_TYPE);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String queryUpgrade = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        sqLiteDatabase.execSQL(queryUpgrade);
        onCreate(sqLiteDatabase);
    }

    public void insertNewMedicine(String medicineName, int day, int month, int year, int noOfTimesPerDay, int totalDoses, String timings, String alertType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, medicineName);
        values.put(KEY_DAY , day);
        values.put(KEY_MONTH, month);
        values.put(KEY_YEAR, year);
        values.put(KEY_TIMES_PER_DAY, noOfTimesPerDay);
        values.put(KEY_TOTAL_DOSES, totalDoses);
        values.put(KEY_TIMINGS, timings);
        values.put(KEY_ALERT_TYPE, alertType);
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.i("Med-Dose DB Helper",  medicineName + day + month + year + noOfTimesPerDay + totalDoses + timings + alertType);
        db.close();
    }

    public void deleteMedicine(String medicineName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, KEY_NAME + " = ?", new String[]{medicineName});
        db.close();
    }

    public List<HomeItem> getMedicineList() {
        List<HomeItem> medicineList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_TIMES_PER_DAY}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            HomeItem homeItem = new HomeItem(cursor.getString(0)  , cursor.getString(1) + " times a day");
            medicineList.add(homeItem);
        }
        cursor.close();
        db.close();
        return medicineList;
       /* JSONObject json = new JSONObject(stringreadfromsqlite);
        ArrayList items = json.optJSONArray("uniqueArrays");*/
    }

    public int getId(String name) {
        int id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_TIMES_PER_DAY}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return id;
    }

    public List<HistoryItem> getMedicineHistory() {
        List<HistoryItem> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_NAME, KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_TIMES_PER_DAY, KEY_TOTAL_DOSES, KEY_TIMINGS}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(cursor.getInt(3), cursor.getInt(2), cursor.getInt(1));
            SimpleDateFormat format1 = new SimpleDateFormat("EEEE, MMMM d, yyyy");
            String date = format1.format(calendar.getTime());
            HistoryItem historyItem= new HistoryItem(cursor.getString(0)  , date, cursor.getInt(4), cursor.getInt(5), cursor.getString(6));
            historyList.add(historyItem);
        }
        cursor.close();
        db.close();
        return historyList;
    }

    public List<String> getTimings(String medicineName) {
        List<String> timingList = new ArrayList<>();
        StringBuffer timingsString = new StringBuffer();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_TIMINGS}, KEY_NAME + " = ?", new String[]{medicineName}, null, null, null);
        while (cursor.moveToNext()) {
            timingsString.append(cursor.getString(0));
            Log.i("Timings", timingsString.toString());
        }
        JSONObject json = null;
        try {
            json = new JSONObject(new String(timingsString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray items = json.optJSONArray("timingArrays");
        for (int i = 0; i < items.length(); i++) {
            try {
                timingList.add(items.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return timingList;
    }

    public int noOfDaysLeft(String medicineName, Calendar mNextAlarmDate) {
        int mPerDay = 0, mTotalDodes = 0;
        int day = 0, month = 0, year = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_TIMES_PER_DAY, KEY_TOTAL_DOSES}, KEY_NAME + " = ?", new String[]{medicineName}, null, null, null);
        while (cursor.moveToNext()) {
            day = cursor.getInt(0);
            month = cursor.getInt(1);
            year = cursor.getInt(2);
            mPerDay = cursor.getInt(3);
            mTotalDodes = cursor.getInt(4);
        }
        int totalDays = mTotalDodes/mPerDay;
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, day);
        startDate.set(Calendar.MONTH, month);
        startDate.set(Calendar.YEAR, year);
        long daysBetween = Math.round((float) (mNextAlarmDate.getTimeInMillis() - startDate.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        int daysLeft = (int) (totalDays - daysBetween);
        return daysLeft;
    }
}

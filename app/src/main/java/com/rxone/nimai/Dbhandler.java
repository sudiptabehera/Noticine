package com.rxone.nimai;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Dbhandler extends SQLiteOpenHelper {
    List<Reminder> reminderList = new ArrayList<>();
    Reminder rmd;
    public Dbhandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create = "CREATE TABLE reminder ( rem_id INTEGER PRIMARY KEY,user_id INTEGER,doc TEXT,med TEXT,end_date TEXT,start_date TEXT ,intervals TEXT,imgpath BLOB,qty TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "reminder");
        onCreate(db);
    }

    public void addReminder(Reminder rmd) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", rmd.getUser_id());
        values.put("rem_id", rmd.getRem_id());
        values.put("doc", rmd.getDoc());
        values.put("med", rmd.getMed());
        values.put("intervals", rmd.getIntrvl());
        values.put("start_date", rmd.getStart_date());
        values.put("end_date", rmd.getEnd_date());
        values.put("imgpath", rmd.getImgpath());
        values.put("qty", rmd.getQty());

        long k = db.insert("reminder", null, values);
        Log.d("mytag1", Long.toString(k));
        db.close();
    }



    public void getReminder(int remid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("reminder",new String[]{"user_id","rem_id","doc","med","start_date","end_date","intervals","imgpath","qty"},"rem_id=?",new String[]{String.valueOf(remid)},null,null,null);
        if(cursor != null && cursor.moveToFirst()){
            Log.d("mytag1", cursor.getString(0));
            Log.d("mytag1", cursor.getString(1));
            Log.d("mytag1", cursor.getString(2));
            Log.d("mytag1", cursor.getString(3));
            Log.d("mytag1", cursor.getString(4));
            Log.d("mytag1", cursor.getString(5));
            Log.d("mytag1", cursor.getString(6));
            Log.d("mytag1", cursor.getString(7));
        }
        else
        {
            Log.d("mytag","some error occured");
        }


    }

    public int getIdMax(){







        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(rem_id) AS max_id FROM reminder";
        Cursor cursor = db.rawQuery(query, null);

        int id = 0;
        if (cursor.moveToFirst())
        {
            do
            {
                id = cursor.getInt(0);
            } while(cursor.moveToNext());
        }

        return id +1;
    }

    public void deleteRem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from reminder where rem_id='"+id+"'");
    }


    public List<Reminder> listReminders() {
        String sql = "select * from reminder";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

                int user_id = cursor.getInt(1);
                int rem_id = cursor.getInt(0);
                String doc = cursor.getString(2);
                String med = cursor.getString(3);
                String start_date = cursor.getString(5);
                String end_date = cursor.getString(4);
                String intervals = cursor.getString(6);
                byte[] imgpath = cursor.getBlob(7);
                String qty = cursor.getString(8);

                reminderList.add(new Reminder(user_id,rem_id,doc,med,end_date,start_date,intervals,imgpath,qty));
            }


        cursor.close();
        return reminderList;
    }
    public Reminder getRemd(int rmdid) {
        String sql = "select * from reminder where rem_id="+rmdid;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            int user_id = cursor.getInt(1);
            int rem_id = cursor.getInt(0);
            String doc = cursor.getString(2);
            String med = cursor.getString(3);
            String start_date = cursor.getString(5);
            String end_date = cursor.getString(4);
            String intervals = cursor.getString(6);
            byte[] imgpath = cursor.getBlob(7);
            String qty = cursor.getString(8);
            rmd=new Reminder(user_id,rem_id,doc,med,end_date,start_date,intervals,imgpath,qty);
        }


        cursor.close();
        return rmd;
    }
}

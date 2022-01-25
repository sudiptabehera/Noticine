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

public class Dbhandlerdose extends SQLiteOpenHelper {
    List<Dosage> dosageList = new ArrayList<>();
    public Dbhandlerdose(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE dosage ( rem_id INTEGER,dos_id INTEGER,dosetime TEXT)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "dosage");
        onCreate(db);
    }


    public void addDose(Dosage dsg) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("dos_id", dsg.getDos_id());
        values.put("rem_id", dsg.getRem_id());
        values.put("dosetime", dsg.getDosetime());


        long k = db.insert("dosage", null, values);
        Log.d("mytag2", Long.toString(k));
        db.close();
    }

    public void getDosage(int remid){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("dosage",new String[]{"dos_id","rem_id","dosetime"},"rem_id=?",new String[]{String.valueOf(remid)},null,null,null);
        if(cursor != null && cursor.moveToFirst()){
            Log.d("mytag55", cursor.getString(0));
            Log.d("mytag55", cursor.getString(1));
            Log.d("mytag55", cursor.getString(2));

        }
        else
        {
            Log.d("mytag2","some error occured");
        }


    }
    public void deleteRem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from dosage where rem_id='"+id+"'");
    }
    public int getIdMax(){







        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT MAX(dos_id) AS max_id FROM dosage";
        Cursor cursor = db.rawQuery(query, null);

        int id = 0;
        if (cursor.moveToFirst())
        {
            do
            {
                id = cursor.getInt(0);
            } while(cursor.moveToNext());
        }

        return id ;
    }



    public List<Dosage> listDosage(int r) {
        String sql = "select * from dosage where rem_id="+r;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            int dos_id = cursor.getInt(0);
            int rem_id = cursor.getInt(1);
            String dosetime = cursor.getString(2);

            dosageList.add(new Dosage(rem_id,dos_id,dosetime));
        }


        cursor.close();
        return dosageList;
    }
}

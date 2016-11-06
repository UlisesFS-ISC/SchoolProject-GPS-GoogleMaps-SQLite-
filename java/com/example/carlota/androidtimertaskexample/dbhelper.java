package com.example.carlota.androidtimertaskexample;

/**
 * Created by Dante on 10/31/2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dante on 10/19/2015.
 */
public class dbhelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME="RunningApp.db";
    public static String TABLE_NAME="marks";
    public static String COL_1="ID";
    public static String COL_2="DATE";
    public static String COL_3="TIME_ELAPSED";
    public static String COL_4="LATITUDE";
    public static String COL_5="LONGITUDE";
    public static String COL_6="DISTANCE";


    public dbhelper(Context context) {
        super(context,DATABASE_NAME, null,1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE DATETIME DEFAULT CURRENT_TIMESTAMP ,TIME_ELAPSED TEXT,LATITUDE DOUBLE, LONGITUDE DOUBLE, DISTANCE DOUBLE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String time,Double latitude,Double longitude,Double distance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_3,time);
        contentValues.put(COL_4,latitude);
        contentValues.put(COL_5, longitude);
        contentValues.put(COL_6, distance);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("Delete  from " + TABLE_NAME + "where ID = " +id);
       // String delete = "DELETE FROM "+TABLE_NAME;
        //db.rawQuery(delete, null);
        db.delete(this.TABLE_NAME, null, null);
        return true;
    }
/*
    public boolean updateData(String id,String name,String age,String weight,String height) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,age);
        contentValues.put(COL_4, weight);
        contentValues.put(COL_5, height);
        long result = db.update(TABLE_NAME, contentValues, "ID = ?",new String[]{id});
        if(result == -1)
            return false;
        else
            return true;
    }
*/


    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}

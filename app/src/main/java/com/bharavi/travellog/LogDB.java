package com.bharavi.travellog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by bharavi on 28-12-2017.
 */

public class LogDB extends SQLiteOpenHelper{
    public LogDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE LOGDB (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,date TEXT,time TEXT,description TEXT)");
    }


    public boolean insertLog(String date,String time,String description)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("date",date);
        contentValues.put("time",time);
        contentValues.put("description",description);
        int id=-1;
        SQLiteDatabase db= getWritableDatabase();
        long rowID=db.insert("LOGDB",null,contentValues);
        Cursor cursor = db.rawQuery("SELECT * FROM LOGDB", null);
        if(cursor.moveToLast())
            id = cursor.getInt(cursor.getColumnIndex("id"));
        LogModel insertedLog = new LogModel(id, date,time, description);
        db.close();
        if (rowID > 0)
            return true;
        else
            return false;
    }

    public ArrayList<LogModel> getAllLogs() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM LOGDB", null);
        ArrayList<LogModel> logs = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                LogModel note = new LogModel(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("time")),
                        cursor.getString(cursor.getColumnIndex("description")));
                logs.add(note);
                cursor.moveToNext();//IMPORTANT for updating cursor
            }
        }
        cursor.close();
        db.close();
        return logs;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

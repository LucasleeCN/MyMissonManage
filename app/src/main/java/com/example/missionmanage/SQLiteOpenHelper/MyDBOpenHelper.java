package com.example.missionmanage.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyDBOpenHelper  extends SQLiteOpenHelper {
    private String CREATE_TABLE="create table MyMission("+"id integer primary key autoincrement, details text, priority integer,start_time text,end_time text,tag text,mark text)";
    public MyDBOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        Log.d("YAG","数据库创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table  if  exists MyMission");
        db.execSQL(CREATE_TABLE);
    }
}

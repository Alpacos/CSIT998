package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * 数据库
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "calculator.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userSql = "create table user(username varchar(20) primary key,password varchar(20))";
        db.execSQL(userSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

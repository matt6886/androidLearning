package com.example.chaper07_server.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db";
    public static final String TABLE_NAME = "user_info";
    private static final int DB_VERSION = 2;
    private static UserDbHelper mHelper = null;

    public UserDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static UserDbHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new UserDbHelper(context);
        }
        return mHelper;
    }


    // create database and create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name VARCHAR NOT NULL," +
                "age INTEGER NOT NULL," +
                "height LONG NOT NULL," +
                "weight FLOAT NOT NULL," +
                "married INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

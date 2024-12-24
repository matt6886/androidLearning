package com.example.chaper08.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.chaper08.bean.BillInfo;

import java.util.ArrayList;

public class BillDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "bill.db";
    private static final String TABLE_BILLS_INFO = "bill_info";
    private static final int DB_VERSION = 1;
    private static BillDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public BillDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static BillDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new BillDBHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create goods table
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_BILLS_INFO+
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "date VARCHAR NOT NULL," +
                "type INTEGER NOT NULL," +
                "amount DOUBLE NOT NULL," +
                "remark VARCHAR NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase openReadLink() {
        if (mRDB == null || !mRDB.isOpen()) {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    public SQLiteDatabase openWriteLink() {
        if (mWDB == null || !mWDB.isOpen()) {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    public void closeLink() {
        if (mRDB != null && mRDB.isOpen()) {
            mHelper.close();
        }
        if (mRDB != null && mWDB.isOpen()) {
            mHelper.close();
        }
    }

    public long save(BillInfo bill) {
        ContentValues values = new ContentValues();
        values.put("date", bill.date);
        values.put("type", bill.type);
        values.put("remark", bill.remark);
        values.put("amount", bill.amount);
        return mWDB.insert(TABLE_BILLS_INFO, null, values);
    }

    @SuppressLint("Range")
    public ArrayList<BillInfo> queryByMonth(String yearMonth) {
        ArrayList<BillInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM "+TABLE_BILLS_INFO+" WHERE date like '"+yearMonth+"%'";
        Log.d("Matt", sql);
        Cursor cursor = mWDB.rawQuery(sql, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                BillInfo bill = new BillInfo();
                bill.id = cursor.getInt(cursor.getColumnIndex("_id"));
                bill.date = cursor.getString(cursor.getColumnIndex("date"));
                bill.type = cursor.getInt(cursor.getColumnIndex("type"));
                bill.remark = cursor.getString(cursor.getColumnIndex("remark"));
                bill.amount = cursor.getDouble(cursor.getColumnIndex("amount"));
                list.add(bill);
            }
        }
        return list;
    }
}

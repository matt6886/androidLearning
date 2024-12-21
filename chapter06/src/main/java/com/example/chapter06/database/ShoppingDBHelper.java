package com.example.chapter06.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.chapter06.entity.CartInfo;
import com.example.chapter06.entity.GoodsInfo;
import com.example.chapter06.util.ToastUtil;

import java.util.ArrayList;

public class ShoppingDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shopping.db";
    private static final String TABLE_GOODS_INFO = "goods_info";
    private static final String TABLE_CART_INFO = "cart_info";
    private static final int DB_VERSION = 1;
    private static ShoppingDBHelper mHelper = null;
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    public ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static ShoppingDBHelper getInstance(Context context) {
        if (mHelper == null) {
            mHelper = new ShoppingDBHelper(context);
        }
        return mHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create goods table
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_GOODS_INFO+
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "name VARCHAR NOT NULL," +
                "description VARCHAR NOT NULL," +
                "price FLOAT NOT NULL," +
                "pic_path VARCHAR NOT NULL);";
        db.execSQL(sql);

        // create cart table
        sql = "CREATE TABLE IF NOT EXISTS "+TABLE_CART_INFO+
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "goods_id int NOT NULL," +
                "count INTEGER NOT NULL);";
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

    public void insertGoodsInfo(ArrayList<GoodsInfo> list) {
        try {
            mWDB.beginTransaction();
            for (GoodsInfo info: list) {
                ContentValues values = new ContentValues();
                values.put("name", info.name);
                values.put("description", info.description);
                values.put("price", info.price);
                values.put("pic_path", info.picPath);
                long result = mWDB.insert(TABLE_GOODS_INFO, null, values);
                Log.d("Matt", String.valueOf(result));
            }
            mWDB.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mWDB.endTransaction();
        }
    }

    public ArrayList<GoodsInfo> queryAllGoodsInfo() {
        ArrayList<GoodsInfo> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_GOODS_INFO;
        Cursor cursor = mWDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            GoodsInfo info = new GoodsInfo();
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
            list.add(info);
        }
        cursor.close();
        return list;
    }

    public void insertCartInfo(int goodsId) {
        CartInfo cartInfo = queryGoodsInfoByGoodsId(goodsId);
        ContentValues values = new ContentValues();
        values.put("goods_id", goodsId);
        if (cartInfo == null) {
            values.put("count", 1);
            mWDB.insert(TABLE_CART_INFO, null, values);
        } else {
            values.put("_id", cartInfo.id);
            values.put("count", cartInfo.count + 1);
            mWDB.update(TABLE_CART_INFO, values, "_id=?", new String[]{String.valueOf(cartInfo.id)});
        }
    }

    private CartInfo queryGoodsInfoByGoodsId(int goodsId) {
        Cursor cursor = mRDB.query(TABLE_CART_INFO, null, "goods_id=?", new String[]{String.valueOf(goodsId)}, null, null, null);
        CartInfo cartInfo = null;
        while (cursor.moveToNext()) {
            cartInfo = new CartInfo();
            cartInfo.id = cursor.getInt(0);
            cartInfo.goodsId = cursor.getInt(1);
            cartInfo.count = cursor.getInt(2);
        }
        return cartInfo;
    }

    public int countCartInfo() {
        int count = 0;
        String sql = "SELECT sum(count) from " + TABLE_CART_INFO;
        Cursor cursor = mRDB.rawQuery(sql, null);
        if (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        return count;
    }

    public ArrayList<CartInfo> queryAllCartInfo() {
        ArrayList<CartInfo> list = new ArrayList<>();
        Cursor cursor = mWDB.query(TABLE_CART_INFO, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            CartInfo cartInfo = new CartInfo();
            cartInfo.id = cursor.getInt(0);
            cartInfo.goodsId = cursor.getInt(1);
            cartInfo.count = cursor.getInt(2);
            list.add(cartInfo);
        }
        return list;
    }

    public GoodsInfo queryGoodsInfoById(int goodsId) {
        GoodsInfo info = null;
        Cursor cursor = mRDB.query(TABLE_GOODS_INFO, null, "_id=?", new String[]{String.valueOf(goodsId)}, null, null, null);
        while (cursor.moveToNext()) {
            info = new GoodsInfo();
            info.id = cursor.getInt(0);
            info.name = cursor.getString(1);
            info.description = cursor.getString(2);
            info.price = cursor.getFloat(3);
            info.picPath = cursor.getString(4);
        }
        return info;
    }

    public void deleteCartInfoByGoodsId(int goodsId) {
        mWDB.delete(TABLE_CART_INFO, "goods_id=?", new String[]{String.valueOf(goodsId)});
    }

    public void deleteAllCartInfo() {
        mWDB.delete(TABLE_CART_INFO, "1=1", null);
    }
}

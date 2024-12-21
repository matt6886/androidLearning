package com.example.chapter06;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.chapter06.database.BookDatabase;
import com.example.chapter06.database.ShoppingDBHelper;
import com.example.chapter06.entity.BookInfo;
import com.example.chapter06.entity.GoodsInfo;
import com.example.chapter06.util.FileUtil;
import com.example.chapter06.util.SharedUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyApplication extends Application {
    public HashMap<String, String> infoMap = new HashMap<>();
    private static MyApplication app;

    private BookDatabase bookDatabase;
    public int goodsCount;

    public static MyApplication getInstance() {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Log.d("Matt", "MyApplication:onCreate");
        bookDatabase = Room.databaseBuilder(this, BookDatabase.class, "book")
                .addMigrations()
                .allowMainThreadQueries()
                .build();

        initialGoodsInfo();
    }

    private void initialGoodsInfo() {
        boolean isFirst = SharedUtil.getInstance(this).readBoolean("first", true);
        String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar;
        if (isFirst) {
            // mock network downloading
            ArrayList<GoodsInfo> list = GoodsInfo.getDefaultList();
            for (GoodsInfo goodsInfo: list) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), goodsInfo.pic);
                String path = directory + goodsInfo.id + ".jpg";
                FileUtil.saveImage(path, bitmap);
                bitmap.recycle();
                goodsInfo.picPath = path;
            }
            // open database and write goods info
            ShoppingDBHelper dbHelper = ShoppingDBHelper.getInstance(this);
            dbHelper.openReadLink();
            dbHelper.openWriteLink();
            dbHelper.insertGoodsInfo(list);
            dbHelper.closeLink();
            // update the local status
            SharedUtil.getInstance(this).writeBoolean("first", false);
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d("Matt", "MyApplication:onTerminate");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("Matt", "MyApplication:onConfigurationChanged");
    }

    public BookDatabase getBookDatabase() {
        return bookDatabase;
    }
}

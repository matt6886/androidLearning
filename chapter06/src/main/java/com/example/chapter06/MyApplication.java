package com.example.chapter06;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.HashMap;

public class MyApplication extends Application {
    public HashMap<String, String> infoMap = new HashMap<>();
    private static MyApplication app;

    public static MyApplication getInstance() {
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Log.d("Matt", "MyApplication:onCreate");
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
}

package com.example.chapter06.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedUtil {
    private static SharedUtil sharedUtil;
    private SharedPreferences preferences;

    public static SharedUtil getInstance(Context context) {
        if (sharedUtil == null) {
            sharedUtil = new SharedUtil();
            sharedUtil.preferences = context.getSharedPreferences("shopping", Context.MODE_PRIVATE);
        }
        return sharedUtil;
    }

    public void writeBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedUtil.preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean readBoolean(String key, boolean defaultValue) {
        return preferences.getBoolean(key, defaultValue);
    }
}

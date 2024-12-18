package com.example.chapter05.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.input.InputManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class ViewUtils {
    public static void hideInputMethod(Activity act, View v) {
        // get system input manager
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        // close keyboard
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}

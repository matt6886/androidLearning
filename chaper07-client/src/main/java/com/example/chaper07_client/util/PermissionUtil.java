package com.example.chaper07_client.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    public static boolean checkPermission(Activity act, String[] permissions, int requestCode) {
        // Android begin to use dynamic permission management after android 6.0
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            int check = PackageManager.PERMISSION_GRANTED;
            for (String permission : permissions) {
                check = ContextCompat.checkSelfPermission(act, permission);
                if (check != PackageManager.PERMISSION_GRANTED) {
                    break;
                }
            }
            if (check != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act, permissions, requestCode);
                return false;
            }
        }
        return true;
    }

    public static boolean checkGranted(int[] grantResults) {
        if (grantResults != null) {
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    return  false;
                }
            }
            return true;
        }
        return false;
    }
}

package com.example.chaper07_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaper07_client.util.PermissionUtil;
import com.example.chaper07_client.util.ToastUtil;

public class PermissionHungryActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] PERMISSIONS = new String[] {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.READ_SMS,
            Manifest.permission.SEND_SMS
    };
    private static final int REQUEST_CODE_ALL = 1;
    private static final int REQUEST_CODE_CONTACTS = 2;
    private static final int REQUEST_CODE_SMS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permission_hungry);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        PermissionUtil.checkPermission(this, PERMISSIONS, REQUEST_CODE_ALL);

        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_msg).setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_ALL:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        switch (permissions[i]) {
                            case Manifest.permission.WRITE_CONTACTS:
                            case Manifest.permission.READ_CONTACTS:
                                ToastUtil.show(this, "Failed to get contact r/w permissions!");
                                break;
                            case Manifest.permission.SEND_SMS:
                            case Manifest.permission.READ_SMS:
                                ToastUtil.show(this, "Failed to get sms r/w permissions!");
                                break;
                        }
                    }
                }
                break;
            case REQUEST_CODE_CONTACTS:
                if (PermissionUtil.checkGranted(grantResults)) {
                    Log.d("Matt", "Successfully to get the contact permissions!");
                } else {
                    ToastUtil.show(this, "Failed to get contact r/w permissions!");
                    jumpToSettings();
                }
                break;
            case REQUEST_CODE_SMS:
                if (PermissionUtil.checkGranted(grantResults)) {
                    Log.d("Matt", "Successfully to get the sms permissions!");
                } else {
                    ToastUtil.show(this, "Failed to get sms r/w permissions!");
                    jumpToSettings();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_contact) {
            PermissionUtil.checkPermission(this, new String[]{PERMISSIONS[0], PERMISSIONS[1]}, REQUEST_CODE_CONTACTS);
        } else if (v.getId() == R.id.btn_msg) {
            PermissionUtil.checkPermission(this, new String[]{PERMISSIONS[2], PERMISSIONS[3]}, REQUEST_CODE_SMS);
        }
    }

    public void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
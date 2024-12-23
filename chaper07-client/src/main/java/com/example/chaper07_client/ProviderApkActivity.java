package com.example.chaper07_client;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaper07_client.util.PermissionUtil;
import com.example.chaper07_client.util.ToastUtil;

import java.io.File;

public class ProviderApkActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_provider_apk);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_install).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_install) {
            // android 11后要判断是否有 MANAGE_EXTERNAL_STORAGE授权
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Log.d("Matt", "android 11");
                checkAndInstall();
            } else {
                if (PermissionUtil.checkPermission(this, PERMISSIONS, PERMISSION_REQUEST_CODE)) {
                    installApk();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE &&
        PermissionUtil.checkGranted(grantResults)) {
            installApk();
        }
    }

    private void checkAndInstall() {
        // 检查是否有MANAGE_EXTERNAL_STORAGE授权授权，否则跳转设置界面
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            } else {
                installApk();
            }
        }
    }

    private void installApk() {
        String apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "chapter06-debug.apk";
        Log.d("Matt", "apkPath:" + apkPath);
        PackageManager pm = getPackageManager();
        PackageInfo pi = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (pi == null) {
            ToastUtil.show(this, "安装文件已经损坏！");
            return;
        }
        // 系统installer应用
        Uri uri = Uri.parse(apkPath);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(apkPath));
            Log.d("Matt", "new uri is:" + uri.toString());
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        // 设置uri的数据类型为apk文件
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        // 启动系统自带的安装程序
        startActivity(intent);
    }
}
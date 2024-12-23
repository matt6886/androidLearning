package com.example.chaper07_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// 动态申请权限的步骤
//1.检查app是否开启了指定的权限
//2.请求系统弹窗，以便用户是否开启权限
//3.判断用户的权限选择结果
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_content_write).setOnClickListener(this);
        findViewById(R.id.btn_lazy_permission).setOnClickListener(this);
        findViewById(R.id.btn_lazy_permission_hungry).setOnClickListener(this);
        findViewById(R.id.btn_contact).setOnClickListener(this);
        findViewById(R.id.btn_sms).setOnClickListener(this);
        findViewById(R.id.btn_send_sms).setOnClickListener(this);
        findViewById(R.id.btn_install_apk).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_content_write) {
            Intent intent = new Intent(this, ContentWriteActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_lazy_permission) {
            Intent intent = new Intent(this, PermissionLazyActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_lazy_permission_hungry) {
            Intent intent = new Intent(this, PermissionHungryActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_contact) {
            Intent intent = new Intent(this, ContactAddActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_sms) {
            Intent intent = new Intent(this, MonitorSmsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_send_sms) {
            Intent intent = new Intent(this, SendSMSActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_install_apk) {
            Intent intent = new Intent(this, ProviderApkActivity.class);
            startActivity(intent);
        }
    }
}
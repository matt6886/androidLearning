package com.example.chapter09;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BroadStaticActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String STATIC_ACTION = "com.example.chapter09.shock";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_broad_static);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_send_static).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_static) {
            //8.0之后删除了大部分静态注册，防止退出app仍在接受广播
            // 为了让应用能够接受静态广播，需要给静态注册广播注册包名
            String fullName = "com.example.chapter09.receiver.ShockReceiver";
            // 发送静态广播时，需要通过setComponent指定接收器的完整路径
            ComponentName componentName = new ComponentName(this, fullName);

            Intent intent = new Intent(STATIC_ACTION);
            intent.setComponent(componentName);
            sendBroadcast(intent);
        }
    }
}
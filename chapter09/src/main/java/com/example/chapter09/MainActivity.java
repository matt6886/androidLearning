package com.example.chapter09;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        findViewById(R.id.btn_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_order_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_static_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_system_minute_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_system_network_broadcast).setOnClickListener(this);
        findViewById(R.id.btn_timer).setOnClickListener(this);
        findViewById(R.id.btn_screen_change).setOnClickListener(this);
        findViewById(R.id.btn_return_desktop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_broadcast) {
            Intent intent = new Intent(this, BroadActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_order_broadcast) {
            Intent intent = new Intent(this, BroadOrderActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_static_broadcast) {
            Intent intent = new Intent(this, BroadStaticActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_system_minute_broadcast) {
            Intent intent = new Intent(this, BroadSystemActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_system_network_broadcast) {
            Intent intent = new Intent(this, BroadNetworkActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_timer) {
            Intent intent = new Intent(this, AlarmActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_screen_change) {
            Intent intent = new Intent(this, ChangeDirectionActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_return_desktop) {
            Intent intent = new Intent(this, ReturnDesktopActivity.class);
            startActivity(intent);
        }
    }
}
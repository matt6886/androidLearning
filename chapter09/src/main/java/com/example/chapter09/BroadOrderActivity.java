package com.example.chapter09;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import receiver.OrderAReceiver;
import receiver.OrderBReceiver;

public class BroadOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private OrderAReceiver aReceiver;
    private OrderBReceiver bReceiver;
    public static final String ORDER_ACTION = "com.example.chapter09.order";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_broad_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_send_order_standard).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ORDER_ACTION);
        sendOrderedBroadcast(intent, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 优先级规则
        // 优先级相同，谁先注册，谁先收到广播
        // 优先级高的先收到广播
        aReceiver = new OrderAReceiver();
        bReceiver = new OrderBReceiver();
        IntentFilter filter = new IntentFilter(ORDER_ACTION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            filter.setPriority(10);
            registerReceiver(aReceiver, filter, Context.RECEIVER_EXPORTED);
            filter.setPriority(20);
            registerReceiver(bReceiver, filter, Context.RECEIVER_EXPORTED);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(aReceiver);
        unregisterReceiver(bReceiver);
    }
}
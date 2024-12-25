package com.example.chapter09;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import receiver.AlarmReceiver;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private AlarmReceiver alarmReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_setting_alarm).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_setting_alarm) {
            alarmReceiver.sendAlarm();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        alarmReceiver = new AlarmReceiver(getApplicationContext());
        IntentFilter filter = new IntentFilter(AlarmReceiver.ALARM_ACTION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(alarmReceiver, filter, Context.RECEIVER_EXPORTED);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(alarmReceiver);
    }
}
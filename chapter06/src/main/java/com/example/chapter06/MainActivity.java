package com.example.chapter06;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Matt", "MainActivity:onCreate");
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btn_preference = findViewById(R.id.btn_preference);
        btn_preference.setOnClickListener(this);

        Button btn_db = findViewById(R.id.btn_db);
        btn_db.setOnClickListener(this);

        findViewById(R.id.btn_db_operate).setOnClickListener(this);

        findViewById(R.id.btn_external_save).setOnClickListener(this);
        findViewById(R.id.btn_save_img).setOnClickListener(this);
        findViewById(R.id.btn_save_to_app).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_preference) {
            Intent intent = new Intent(this, SharePreferenceActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_db) {
            Intent intent = new Intent(this, DatabaseActivituy.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_db_operate) {
            Intent intent = new Intent(this, DBOperationActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_external_save) {
            Intent intent = new Intent(this, FileWriteActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_save_img) {
            Intent intent = new Intent(this, ImageWriteActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_save_to_app) {
            Intent intent = new Intent(this, AppActivity.class);
            startActivity(intent);
        }
    }
}
package com.example.chapter06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class DatabaseActivituy extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_show_db_info;
    private String  dbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_database_activituy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btn_create_db = findViewById(R.id.btn_create_db);
        btn_create_db.setOnClickListener(this);

        Button btn_delete_db = findViewById(R.id.btn_delete_db);
        btn_delete_db.setOnClickListener(this);

        tv_show_db_info = findViewById(R.id.tv_show_db_info);

        // generate a database path
        dbName = getFilesDir() + "/test.db";
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_create_db) {
            // open database
            SQLiteDatabase db = openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
            String desc = String.format("open database %s", db != null ? "successfully" : "failed");
            tv_show_db_info.setText(desc);
        } else if (v.getId() == R.id.btn_delete_db) {
            boolean deleted = deleteDatabase(dbName);
            String desc = String.format("delete database %s", deleted ? "successfully" : "failed");
            tv_show_db_info.setText(desc);
        }
    }
}
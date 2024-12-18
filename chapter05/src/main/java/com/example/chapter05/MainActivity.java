package com.example.chapter05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button btn_draw = findViewById(R.id.btn_draw);
        btn_draw.setOnClickListener(this);

        Button btn_nine_draw = findViewById(R.id.btn_nine_draw);
        btn_nine_draw.setOnClickListener(this);

        Button btn_check = findViewById(R.id.btn_check);
        btn_check.setOnClickListener(this);

        Button btn_et = findViewById(R.id.btn_et);
        btn_et.setOnClickListener(this);

        Button btn_show_dialog = findViewById(R.id.btn_show_dialog);
        btn_show_dialog.setOnClickListener(this);

        Button btn_login_case = findViewById(R.id.btn_login_case);
        btn_login_case.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_draw) {
            Intent intent = new Intent(this, DrawableActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_nine_draw) {
            Intent intent = new Intent(this, NineDrawableActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_check) {
            Intent intent = new Intent(this, CheckActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_et) {
            Intent intent = new Intent(this, EditTextActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_show_dialog) {
            Intent intent = new Intent(this, DialogActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_login_case) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
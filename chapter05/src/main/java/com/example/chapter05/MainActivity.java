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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_draw) {
            Intent intent = new Intent(this, DrawableActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_nine_draw) {
            Intent intent = new Intent(this, NineDrawableActivity.class);
            startActivity(intent);
        }
    }
}
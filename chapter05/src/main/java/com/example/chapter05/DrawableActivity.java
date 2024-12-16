package com.example.chapter05;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DrawableActivity extends AppCompatActivity implements View.OnClickListener {

    private View view_drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drawable);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        view_drawer = findViewById(R.id.view_drawer);
        view_drawer.setBackgroundResource(R.drawable.shape_react);
        Button btn_rect = findViewById(R.id.btn_rect);
        btn_rect.setOnClickListener(this);
        Button btn_oval = findViewById(R.id.btn_oval);
        btn_oval.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_rect) {
            view_drawer.setBackgroundResource(R.drawable.shape_react);
        } else if (v.getId() == R.id.btn_oval) {
            view_drawer.setBackgroundResource(R.drawable.shape_oval);
        }
    }
}
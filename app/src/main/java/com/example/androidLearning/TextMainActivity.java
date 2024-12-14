package com.example.androidLearning;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidLearning.utils.Utils;

public class TextMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_text_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv1 = findViewById(R.id.tv_color);
        tv1.setTextColor(getResources().getColor(R.color.pink, null));
        tv1.setTextColor(ContextCompat.getColor(this, android.R.color.holo_blue_dark));

        TextView tv2 = findViewById(R.id.tv_dimension);
        ViewGroup.LayoutParams params = tv2.getLayoutParams();
        params.width = Utils.dip2px(this, 300);
        tv2.setLayoutParams(params);
    }
}
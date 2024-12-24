package com.example.chaper08;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.chaper08.adapter.LaunchImprovementAdapter;

public class LaunchImprovementActivity extends AppCompatActivity {

    private final int[] launchImageArray = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4 };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_launch_improvement);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ViewPager vp_launch = findViewById(R.id.vp_launch_improvement);
        LaunchImprovementAdapter adapter = new LaunchImprovementAdapter(getSupportFragmentManager(), launchImageArray);
        vp_launch.setAdapter(adapter);
    }
}
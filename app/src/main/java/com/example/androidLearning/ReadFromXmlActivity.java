package com.example.androidLearning;

import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReadFromXmlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read_from_xml);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv_read = findViewById(R.id.tv_read);
        String str = getString(R.string.tv_read);
        tv_read.setText(str);

        TextView tv_meta = findViewById(R.id.tv_meta);
        // get package manager
        PackageManager pm = getPackageManager();
        try {
            // get activity info
            ActivityInfo info = pm.getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);
            // get bundle info
            Bundle bundle = info.metaData;
            String weatherCondition = bundle.getString("weather");
            tv_meta.setText(weatherCondition);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
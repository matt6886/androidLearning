package com.example.chapter06;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.util.ToastUtil;

public class AppActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView et_name;
    private TextView et_age;
    private TextView et_height;
    private TextView et_weight;
    private CheckBox ck_married;
    private MyApplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);
        ck_married = findViewById(R.id.ck_married);

        findViewById(R.id.btn_save_to_app).setOnClickListener(this);

        app = MyApplication.getInstance();

        reload();
    }

    public void reload() {
        if (app.infoMap.get("name") == null) {
            return;
        }
        String name = app.infoMap.get("name");
        String age = app.infoMap.get("age");
        String height = app.infoMap.get("height");
        String weight = app.infoMap.get("weight");
        boolean married = app.infoMap.get("married").equals("yes");

        et_name.setText(name);
        et_age.setText(age);
        et_height.setText(height);
        et_weight.setText(weight);
        ck_married.setChecked(married);
    }

    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();

        app.infoMap.put("name", name);
        app.infoMap.put("age", age);
        app.infoMap.put("height", height);
        app.infoMap.put("weight", weight);
        app.infoMap.put("married", ck_married.isChecked() ? "yes" : "no");

        ToastUtil.show(this, "saved");
    }
}
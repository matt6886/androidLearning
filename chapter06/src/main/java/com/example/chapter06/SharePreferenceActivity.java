package com.example.chapter06;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SharePreferenceActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView et_name;
    private TextView et_age;
    private TextView et_height;
    private TextView et_weight;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_share_preference);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        et_height = findViewById(R.id.et_height);
        et_weight = findViewById(R.id.et_weight);

        Button btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);

        reloadPersonalInfo();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit) {
            String name = et_name.getText().toString();
            String age = et_age.getText().toString();
            String height = et_height.getText().toString();
            String weight = et_weight.getText().toString();

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name", name);
            editor.putString("age", age);
            editor.putString("height", height);
            editor.putString("weight", weight);

            editor.apply();

            updatePersonalInfo();
        }
    }

    public void updatePersonalInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information is Updated!");
        builder.setMessage("");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void reloadPersonalInfo() {
        String name = preferences.getString("name", null);
        if (name != null) {
            et_name.setText(name);
        }
        String age = preferences.getString("age", null);
        if (age != null) {
            et_age.setText(age);
        }
        String height = preferences.getString("height", null);
        if (height != null) {
            et_height.setText(height);
        }
        String weight = preferences.getString("weight", null);
        if (weight != null) {
            et_weight.setText(weight);
        }
    }
}
package com.example.chaper08;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEventSource;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaper08.util.ToastUtil;

public class SpinnerDropdownActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner sp_dropdown;
    private Spinner sp_dialog;
    private static String[] starArray = new String[] {"水星", "金星", "地球", "火星", "木星", "土星"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spinner_dropdown);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp_dropdown = findViewById(R.id.sp_dropdown);
        // 声明一个下拉列表的数据选择器
        ArrayAdapter<String> startAdapter = new ArrayAdapter<>(this, R.layout.item_select, starArray);
        sp_dropdown.setAdapter(startAdapter);
        //设置下拉列表跨框默认选择第一项
        sp_dropdown.setSelection(0);
        // 监听item选中事件
        sp_dropdown.setOnItemSelectedListener(this);

        sp_dialog = findViewById(R.id.sp_dialog);
        sp_dialog.setAdapter(startAdapter);
        sp_dialog.setSelection(0);
        sp_dialog.setPrompt("Please select your item:");
        sp_dialog.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.show(this, "You selected " + starArray[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
package com.example.androidLearning;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidLearning.utils.DateUtil;

public class ButtonStyleActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv;
    private Button test_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_button_style);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv = findViewById(R.id.tv);

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new MyOnClickListener(tv));

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                String time = String.format("%s clicked: %s", ((Button)view).getText(), DateUtil.getNowTime());
                tv.setText(time);
                return true;
            }
        });

        Button btn_enable = findViewById(R.id.btn_enable);
        btn_enable.setOnClickListener(this);

        Button btn_disable = findViewById(R.id.btn_disable);
        btn_disable.setOnClickListener(this);

        test_btn = findViewById(R.id.btn6);
        test_btn.setEnabled(false);
        test_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //String time = String.format("%s clicked: %s", ((Button)view).getText(), DateUtil.getNowTime());
        //tv.setText(time);
        int id = view.getId();
        if (id == R.id.btn_enable) {
            test_btn.setEnabled(true);
        } else if (id == R.id.btn_disable) {
            test_btn.setEnabled(false);
        } else {
            String time2 = String.format("%s clicked: %s", ((Button)view).getText(), DateUtil.getNowTime());
            tv.setText(time2);
        }
    }

    static class MyOnClickListener implements View.OnClickListener {
        private final TextView tv_result;
        public MyOnClickListener(TextView tv) {
            this.tv_result = tv;
        }
        @Override
        public void onClick(View view) {
            String time = String.format("%s clicked: %s", ((Button)view).getText(), DateUtil.getNowTime());
            tv_result.setText(time);
        }
    }

    public void doClick(View view) {
        String time = String.format("%s clicked: %s", ((Button)view).getText(), DateUtil.getNowTime());
        tv.setText(time);
    }
}
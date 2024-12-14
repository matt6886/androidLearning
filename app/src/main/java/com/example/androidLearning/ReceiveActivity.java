package com.example.androidLearning;

import android.app.Activity;
import android.content.Intent;
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
import com.example.androidLearning.utils.Utils;

public class ReceiveActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_receive);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv_receive = findViewById(R.id.tv_receive);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String request_time = bundle.getString("request_time");
        String request_content = bundle.getString("request_content");
        String receiveData = String.format("%s receive %s", request_time, request_content);
        tv_receive.setText(receiveData);

        Button btn_send_back = findViewById(R.id.btn_send_back);
        btn_send_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("response_time", DateUtil.getNowTime());
        bundle.putString("response_content", "response content");
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
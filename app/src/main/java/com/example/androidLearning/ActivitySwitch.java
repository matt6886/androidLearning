package com.example.androidLearning;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.androidLearning.utils.DateUtil;

public class ActivitySwitch extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "Matt";
    private ActivityResultLauncher<Intent> register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_switch);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton back = findViewById(R.id.btn_top_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btn = findViewById(R.id.btn_jump);
        btn.setOnClickListener(this);

        Button btn2 = findViewById(R.id.btn_jump_success);
        btn2.setOnClickListener(this);

        Button btn_call = findViewById(R.id.btn_call);
        btn_call.setOnClickListener(this);

        Button btn_message = findViewById(R.id.btn_message);
        btn_message.setOnClickListener(this);

        Button btn_pass = findViewById(R.id.btn_pass);
        btn_pass.setOnClickListener(this);

        TextView tv_response = findViewById(R.id.tv_response);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Intent intent = o.getData();
                if (intent != null && o.getResultCode() == Activity.RESULT_OK) {
                    Bundle bundle = intent.getExtras();
                    assert bundle != null;
                    String response_time = bundle.getString("response_time");
                    String response_content = bundle.getString("response_content");
                    String response = String.format("%s receive response: %s", response_time, response_content);
                    tv_response.setText(response);
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onClick(View view) {
        String phoneNum = "12345";
        if (view.getId() == R.id.btn_jump) {
            Intent intent = new Intent(this, ActivityStartMode.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn_jump_success) {
            Intent intent = new Intent(this, ActivityStartMode.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        Intent intent = new Intent();
        if (view.getId() == R.id.btn_call) {
            intent.setAction(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:" + phoneNum);
            intent.setData(uri);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn_message) {
            intent.setAction(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("smsto:" + phoneNum);
            intent.setData(uri);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn_pass) {
            Intent intent2 = new Intent(this, ReceiveActivity.class);
            TextView text = findViewById(R.id.tv_text);
            Bundle bundle = new Bundle();
            bundle.putString("request_time", DateUtil.getNowTime());
            bundle.putString("request_content", text.getText().toString());
            intent2.putExtras(bundle);
            register.launch(intent2);

        }

    }
}
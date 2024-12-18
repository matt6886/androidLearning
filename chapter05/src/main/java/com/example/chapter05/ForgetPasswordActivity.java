package com.example.chapter05;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_pwd_first;
    private EditText et_pwd_second;
    private EditText et_verify_code;
    private Button btn_get_verify_code;
    private Button btn_confirm;
    private String phone;
    String verifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_pwd_first = findViewById(R.id.et_pwd_first);
        et_pwd_second = findViewById(R.id.et_pwd_second);
        et_verify_code = findViewById(R.id.et_verify_code);

        btn_get_verify_code = findViewById(R.id.btn_get_verify_code);
        btn_get_verify_code.setOnClickListener(this);

        btn_confirm = findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);

        phone = getIntent().getStringExtra("phone");

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_get_verify_code) {
            if (phone.length() < 11) {
                Toast.makeText(this, "Phone is not correct", Toast.LENGTH_SHORT).show();
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Verify Code");
            verifyCode = String.format("%06d", new Random().nextInt(9999));
            String message = String.format("Phone:%s, Verify Code is: %s", phone, verifyCode);
            builder.setMessage(message);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else if (v.getId() == R.id.btn_confirm) {
            if (et_pwd_first.length() < 6) {
                Toast.makeText(this, "Length should be more than 6", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!et_pwd_first.getText().toString().equals(et_pwd_second.getText().toString())) {
                Toast.makeText(this, "Passwords is not equal!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!et_verify_code.getText().toString().equals(verifyCode)) {
                Toast.makeText(this, "Please input right verify code", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("password", et_pwd_first.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
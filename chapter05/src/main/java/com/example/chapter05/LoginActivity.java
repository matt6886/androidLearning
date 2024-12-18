package com.example.chapter05;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter05.utils.ViewUtils;

import java.util.Random;

public class LoginActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private TextView tv_password;
    private EditText et_input_password;
    private Button btn_forget_password;
    private CheckBox ck_password;
    private EditText et_input_phone;
    private RadioButton rb_password;
    private RadioButton rb_verify_code;
    private ActivityResultLauncher<Intent> register;
    private String mPassword = "111111";
    private String verifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RadioGroup rb = findViewById(R.id.rg_login);
        rb.setOnCheckedChangeListener(this);

        tv_password = findViewById(R.id.tv_password);
        et_input_password = findViewById(R.id.et_input_password);
        btn_forget_password = findViewById(R.id.btn_forget_password);
        ck_password = findViewById(R.id.ck_password);
        et_input_phone = findViewById(R.id.et_input_phone);

        et_input_password.addTextChangedListener(new HideTextWatcher(et_input_password, 6));
        et_input_phone.addTextChangedListener(new HideTextWatcher(et_input_phone, 11));

        btn_forget_password = findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
        rb_password = findViewById(R.id.rb_password);
        rb_verify_code = findViewById(R.id.rb_verify_code);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                Intent intent = o.getData();
                if (intent != null && o.getResultCode() == Activity.RESULT_OK) {
                    String new_password = intent.getStringExtra("password");
                    mPassword = new_password;
                }

            }
        });

        Button btn_login_page = findViewById(R.id.btn_login_page);
        btn_login_page.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_verify_code) {
            tv_password.setText("Verify Code");
            et_input_password.setHint("Verify Code");
            btn_forget_password.setText("send verify code");
            ck_password.setVisibility(View.GONE);
        } else {
            tv_password.setText("Password");
            et_input_password.setHint("password");
            btn_forget_password.setText("forget password");
            ck_password.setVisibility(TextView.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_forget_password) {
            if (et_input_phone.length() < 11) {
                Toast.makeText(this, "Plase inputy correct phone number", Toast.LENGTH_SHORT).show();
                return;
            }
            if (rb_password.isChecked()) {
                Intent intent = new Intent(this, ForgetPasswordActivity.class);
                intent.putExtra("phone", et_input_phone.getText().toString());
                register.launch(intent);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Verify Code");
                verifyCode = String.format("%06d", new Random().nextInt(9999));
                String message = String.format("phone number:%s, Verify Code:%s", et_input_phone.getText().toString(), verifyCode);
                builder.setMessage(message);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        } else if (v.getId() == R.id.btn_login_page) {
            if (rb_password.isChecked()) {
                if (!mPassword.equals(et_input_password.getText().toString())) {
                    Toast.makeText(this, "Plase input correct password", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginSuccessfully();
            } else if (rb_verify_code.isChecked()) {
                if (!verifyCode.equals(et_input_password.getText().toString())) {
                    Toast.makeText(this, "Plase input right verify code", Toast.LENGTH_SHORT).show();
                    return;
                }
                loginSuccessfully();
            }
        }
    }

    public void loginSuccessfully() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login Successfully!");
        builder.setMessage("Congratulations!");
        builder.setPositiveButton("back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class HideTextWatcher implements TextWatcher {
        private final EditText inputContent;
        private final int maxLength;
        public HideTextWatcher(EditText et, int maxLength) {
            this.inputContent = et;
            this.maxLength = maxLength;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (maxLength == s.length()) {
                ViewUtils.hideInputMethod(LoginActivity.this, inputContent);
            }
        }
    }
}
package com.example.chapter05;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter05.utils.ViewUtils;

import java.security.spec.ECField;

public class EditTextActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_text);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText phone = findViewById(R.id.et_phone);
        phone.setOnFocusChangeListener(this);
        phone.addTextChangedListener(new HideTextWatcher(phone, 11));

        EditText password = findViewById(R.id.et_password);
        password.setOnFocusChangeListener(this);
        password.addTextChangedListener(new HideTextWatcher(password, 6));

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            String phone = ((EditText)v).getText().toString();
            if (v.getId() == R.id.et_phone) {
                if (TextUtils.isEmpty(phone) || phone.length() < 11) {
                    Toast.makeText(this, "Please input correct phone", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    class HideTextWatcher implements TextWatcher {

        private final EditText et;
        private final int maxLength;
        public HideTextWatcher(EditText et, int maxLength) {
            this.et = et;
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
            String inputContent = s.toString();
            if (inputContent.length() == maxLength) {
                ViewUtils.hideInputMethod(EditTextActivity.this, et);
            }
        }
    }
}
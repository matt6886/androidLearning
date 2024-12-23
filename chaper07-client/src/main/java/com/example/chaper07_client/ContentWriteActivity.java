package com.example.chaper07_client;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaper07_client.entity.User;
import com.example.chaper07_client.util.ToastUtil;

import java.util.List;

public class ContentWriteActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView et_name;
    private TextView et_age;
    private TextView et_height;
    private TextView et_weight;
    private CheckBox ck_married;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_content_write);
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

        findViewById(R.id.btn_create).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_update).setOnClickListener(this);
        findViewById(R.id.btn_retrieve).setOnClickListener(this);
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View v) {
        String name = et_name.getText().toString();
        String age = et_age.getText().toString();
        String height = et_height.getText().toString();
        String weight = et_weight.getText().toString();

        if (v.getId() == R.id.btn_create) {
            ContentValues values = new ContentValues();
            values.put(UserInfoContent.USER_NAME, name);
            values.put(UserInfoContent.USER_AGE, Integer.parseInt(age));
            values.put(UserInfoContent.USER_HEIGHT, Integer.parseInt(height));
            values.put(UserInfoContent.USER_WEIGHT, Float.parseFloat(weight));
            values.put(UserInfoContent.USER_MARRIED, ck_married.isChecked());

            getContentResolver().insert(UserInfoContent.CONTENT_URI, values);
            ToastUtil.show(this, "Successfully saved!");

        } else if(v.getId() == R.id.btn_delete) {
            int count = getContentResolver().delete(UserInfoContent.CONTENT_URI, "name=?", new String[]{"Matt"});
//            Uri uri = ContentUris.withAppendedId(UserInfoContent.CONTENT_URI, 2);
//            int count = getContentResolver().delete(uri, null, null);
            if (count > 0) {
                ToastUtil.show(this, "Successfully deleted");
            }
        } else if(v.getId() == R.id.btn_update) {

        } else if(v.getId() == R.id.btn_retrieve) {
            Cursor cursor = getContentResolver().query(UserInfoContent.CONTENT_URI, null, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    User user = new User();
                    user.id = cursor.getInt(cursor.getColumnIndex(UserInfoContent._ID));
                    user.name = cursor.getString(cursor.getColumnIndex(UserInfoContent.USER_NAME));
                    user.age = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_AGE));
                    user.height = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_HEIGHT));
                    user.weight = cursor.getFloat(cursor.getColumnIndex(UserInfoContent.USER_WEIGHT));
                    user.married = cursor.getInt(cursor.getColumnIndex(UserInfoContent.USER_MARRIED)) == 1;
                    Log.d("Matt", user.toString());
                }
                cursor.close();
            }
        }
    }
}
package com.example.chapter06;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.util.FileUtil;
import com.example.chapter06.util.ToastUtil;

import java.io.File;

public class FileWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView et_name;
    private TextView et_age;
    private TextView et_height;
    private TextView et_weight;
    private CheckBox ck_married;
    private String path;
    private TextView tv_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_file_write);
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

        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);

        tv_txt = findViewById(R.id.tv_txt);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            String name = et_name.getText().toString();
            String age = et_age.getText().toString();
            String height = et_height.getText().toString();
            String weight = et_weight.getText().toString();

            StringBuffer sb = new StringBuffer();
            sb.append("name:").append(name);
            sb.append("\nage:").append(age);
            sb.append("\nheight:").append(height);
            sb.append("\nweight:").append(weight);
            sb.append("\nmarried:").append(ck_married.isChecked() ? "yes" : "no");

            String fileName = System.currentTimeMillis() + ".txt";
            String directory = null;
            // external private space
            // String directory = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString();
            // external public space
            // deprecated in Android 13
            // String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

            // internal private space
            directory = getFilesDir().toString();
            path = directory + File.separatorChar + fileName;
            Log.d("Matt", path);
            FileUtil.saveText(path, sb.toString());

            // save to public space(new api)
//            FileUtil.saveTextToFile(this, fileName, sb.toString());
            ToastUtil.show(this, "Successfully saved");
        } else if (v.getId() == R.id.btn_read) {
            tv_txt.setText(FileUtil.readText(path));
        }
    }
}
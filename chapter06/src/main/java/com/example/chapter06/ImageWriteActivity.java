package com.example.chapter06;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chapter06.util.FileUtil;
import com.example.chapter06.util.ToastUtil;

import java.io.File;

public class ImageWriteActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_img;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_write);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_save_img).setOnClickListener(this);
        findViewById(R.id.btn_show_img).setOnClickListener(this);

        iv_img = findViewById(R.id.iv_img);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save_img) {
            String fileName = System.currentTimeMillis() + ".jpeg";
            // get private downloading directory of current app
            path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + File.separatorChar + fileName;
            Log.d("Matt", path);
            // get bit image from the specific resource
            Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.img1);
            // save bit object into image
            FileUtil.saveImage(path, b1);
            ToastUtil.show(this, "Successfully saved!");
        } else if (v.getId() == R.id.btn_show_img) {
//            Bitmap b2 = FileUtil.openImage(path);
//            iv_img.setImageBitmap(b2);

//            Bitmap b2 = BitmapFactory.decodeFile(path);
//            iv_img.setImageBitmap(b2);

            iv_img.setImageURI(Uri.parse(path));
        }
    }
}
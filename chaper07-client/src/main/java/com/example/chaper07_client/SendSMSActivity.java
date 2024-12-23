package com.example.chaper07_client;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.chaper07_client.entity.ImageInfo;
import com.example.chaper07_client.util.FileUtil;
import com.example.chaper07_client.util.PermissionUtil;
import com.example.chaper07_client.util.ToastUtil;
import com.example.chaper07_client.util.Utils;

import java.io.File;
import java.util.ArrayList;

public class SendSMSActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_add;
    private ActivityResultLauncher<Intent> register;
    private EditText et_phone_number;
    private EditText et_title;
    private EditText et_content;
    private Uri picUri;
    private GridLayout gl_images;
    private ArrayList<ImageInfo> mImages = new ArrayList<>();
    private static final String[] PERMISSIONS = new String[] {
            Manifest.permission.READ_MEDIA_IMAGES
    };
    
    private static final int PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_smsactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        iv_add = findViewById(R.id.iv_add);
//        iv_add.setOnClickListener(this);

        findViewById(R.id.btn_send_sms).setOnClickListener(this);

        et_phone_number = findViewById(R.id.et_phone_number);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        gl_images = findViewById(R.id.gl_images);

        register = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == RESULT_OK) {
                    Intent intent = o.getData();
                    picUri = intent.getData();
                    if (picUri != null) {
                        iv_add.setImageURI(picUri);
                        Log.d("Matt", "picUri" + picUri.toString());
                    }
                }
            }
        });

        // 手动让MediaStore扫描入库
        MediaScannerConnection.scanFile(this, new String[] {Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()},
                null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                        Log.d("Matt", "Scanning finished:" + path);
                    }
                });
        if (PermissionUtil.checkPermission(this, PERMISSIONS, PERMISSION_REQUEST_CODE)) {
            loadImageList();
            showImageGrid();
        }

    }

    private void showImageGrid() {
        gl_images.removeAllViews();
        for (ImageInfo image : mImages) {
            ImageView iv_img = new ImageView(this);
            Bitmap bitmap = BitmapFactory.decodeFile(image.path);
            iv_img.setImageBitmap(bitmap);
            iv_img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            int px = Utils.dip2px(this, 110);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px, px);
            iv_img.setLayoutParams(params);
            int padding = Utils.dip2px(this, 5);
            iv_img.setPadding(padding, padding, padding, padding);
            iv_img.setOnClickListener(v ->  {
                sendSms(et_phone_number.getText().toString(),
                    et_title.getText().toString(),
                    et_content.getText().toString(),
                    image.path);
            });
            gl_images.addView(iv_img);
        }
    }

    @SuppressLint("Range")
    private void loadImageList() {
        String[] columns = new String[] {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.TITLE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA
        };

        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns,
                "_size < 1024000",
                null,
                "_size DESC"
        );

        if (cursor != null) {
            int count = 0;
            while (cursor.moveToNext() && count < 6) {
                ImageInfo image = new ImageInfo();
                image.id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                image.name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                image.size = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                image.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                // 如果图片合法则添加
                if (FileUtil.checkFileUri(this, image.path)) {
                    mImages.add(image);
                    count++;
                }
                Log.d("Matt", image.toString());
            }
        }


    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.iv_add) {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            register.launch(intent);
//        } else if (v.getId() == R.id.btn_send_sms) {
//            sendSms(et_phone_number.getText().toString(),
//                    et_title.getText().toString(),
//                    et_content.getText().toString());
//        }
    }

    private void sendSms(String phone, String title, String content, String path) {
        // 根据指定路径构建Uri
        Uri uri = Uri.parse(path);
        // 兼容7.0以上
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            // 通过FileProvider获得文件的Uri访问方式
            uri = FileProvider.getUriForFile(this, getString(R.string.file_provider), new File(path));
            Log.d("Matt", String.format("now uri:%s", uri.toString()));
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        // Intent允许接受者读取Intent携带的Uri
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("address", phone);
        intent.putExtra("subject", title);
        intent.putExtra("sms_body", content);
//        intent.putExtra(Intent.EXTRA_STREAM, picUri);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        startActivity(intent);
        ToastUtil.show(this, "Please select a App to send smg!");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            loadImageList();
            showImageGrid();
        }
    }
}
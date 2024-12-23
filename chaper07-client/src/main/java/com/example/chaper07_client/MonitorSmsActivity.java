package com.example.chaper07_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MonitorSmsActivity extends AppCompatActivity {

    private SmsGetObserver mObserver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_monitor_sms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Uri uri = Uri.parse("content://sms");
        // notifyForDescendant
        // false:表示精准匹配，只匹配uri，true表示可以匹配其派生的uri
        // 假设UriMatcher里面注册的Uri只有一个类型
        // 1.content://AUTHORITIES/table
        // 2.content://AUTHORITIES/table/#
        // 3.content://AUTHORITIES/subtable
        // 假设我们当前观察的Uri为content://AUTHORITIES/student
        // 如果发生数据变化的Uri为3
        // notifyForDescendant为false，则ContentObserver监听不到，notifyForDescendant为false为true，则可以监听到
        mObserver = new SmsGetObserver(this);
        getContentResolver().registerContentObserver(uri, true, mObserver);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mObserver);
    }

    private static class SmsGetObserver extends ContentObserver {
        private final Context context;

        public SmsGetObserver(Context context) {
            super(new Handler(Looper.getMainLooper()));
            this.context = context;
        }

        @SuppressLint("Range")
        @Override
        public void onChange(boolean selfChange, @Nullable Uri uri) {
            super.onChange(selfChange, uri);
            // onChange会调用多次
            // mUri===content://sms/raw/20
            // mUri===content://sms/inbox/20
            // 安卓7.0以上，标记为已读也会调用一次
            // mUri===content://sms
            // 收到一条短信Uri后面都会有一个确定的数字，对应数据库的id，比如上面的20
            if (uri == null) {
                return;
            }
            if (uri.toString().contains("sms/raw")
            || uri.toString().equals("sms")) {
                return;
            }
            Cursor cursor = context.getContentResolver().query(uri, new String[]{"address", "body", "date"}, null, null, "date DESC");
            if (cursor.moveToNext()) {
                String sender = cursor.getString(cursor.getColumnIndex("address"));
                String content = cursor.getString(cursor.getColumnIndex("body"));
                Log.d("Matt", String.format("sender:%s, content:%s", sender, content));
            }

            cursor.close();
        }
    }
}
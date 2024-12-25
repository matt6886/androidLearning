package com.example.chapter09;

import android.app.PictureInPictureParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Rational;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ReturnDesktopActivity extends AppCompatActivity {

    private ReturnDesktopReceiver returnDesktopReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_return_desktop);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        returnDesktopReceiver = new ReturnDesktopReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(returnDesktopReceiver, filter, Context.RECEIVER_EXPORTED);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(returnDesktopReceiver);
    }

    // 在进入画中画模式或退出画中画模式触发
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            Log.d("Matt", "进入了画中画模式");
        } else {
            Log.d("Matt", "退出了画中画模式");
        }
    }

    private class ReturnDesktopReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra("reason");
                if (!TextUtils.isEmpty(reason) &&
                        (reason.equals("homekey") || reason.equals("recentapps"))) {
                    //8.0之后才有画中画模式
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O && !isInPictureInPictureMode()) {
                        PictureInPictureParams.Builder builder = new PictureInPictureParams.Builder();
                        // 设置画中画宽高比，第一个为分子，第二个为分母
                        // 下面的宽高比为2
                        Rational rational = new Rational(10, 5);
                        builder.setAspectRatio(rational);
                        // 进入画中画模式
                        enterPictureInPictureMode(builder.build());
                    }
                }
            }
        }
    }
}
package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.chapter09.BroadOrderActivity;

public class OrderBReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null & intent.getAction().equals(BroadOrderActivity.ORDER_ACTION)) {
            Log.d("Matt", "接收器B收到广播！");
            // 中断广播，后面的接收器不再收到广播
            abortBroadcast();
        }
    }
}

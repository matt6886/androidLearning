package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.chapter09.BroadOrderActivity;

public class OrderAReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(BroadOrderActivity.ORDER_ACTION)) {
            Log.d("Matt", "接收器A收到广播！");
        }
    }
}

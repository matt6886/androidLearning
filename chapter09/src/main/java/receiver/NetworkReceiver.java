package receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            NetworkInfo networkInfo = intent.getParcelableExtra("networkInfo");
            String desc = String.format("收到一个网络变更通知：网络大类为%s,网络小类为%s,网络状态为%s",
                    networkInfo.getTypeName(),
                    networkInfo.getSubtypeName(),
                    networkInfo.getState().toString());
            Log.d("Matt", desc);
        }
    }
}

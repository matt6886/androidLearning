package receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String ALARM_ACTION = "com.example.chapter09.alarm";
    private final Context context;

    public AlarmReceiver(Context context) {
        super();
        this.context = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(ALARM_ACTION)) {
            Log.d("Matt", "收到闹钟广播");
            sendAlarm();
        }

    }

    public void sendAlarm() {
        Intent intent = new Intent(ALARM_ACTION);
        // 创建一个用于广播的延迟意图
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        // 从系统管理器中获取闹钟管理器
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 1000, pendingIntent);
        } else {
            // 6.0之前
            alarmManager.set(AlarmManager.RTC_WAKEUP, 1000, pendingIntent);
        }
    }
}

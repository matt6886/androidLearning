package com.example.chaper08.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public  static String getNowTime() {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return  fmt.format(new Date());
    }

    public static String  getDate(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String  getMonth(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(date);
    }

}

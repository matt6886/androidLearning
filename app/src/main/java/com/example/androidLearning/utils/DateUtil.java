package com.example.androidLearning.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public  static String getNowTime() {
        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
        return  fmt.format(new Date());
    }

}

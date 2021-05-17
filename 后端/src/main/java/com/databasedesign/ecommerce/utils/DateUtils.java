package com.databasedesign.ecommerce.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static String pattern = "yyyy-MM-dd HH:mm:ss";

    public static String getDateTime(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}

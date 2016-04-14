package com.example.pichery.weight.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pichery on 29/11/15.
 */
public class DateUtil {

    public static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat formatWithHour = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String formatDate(Date date){
        return format.format(date);
    }

    public static String formatDateWithHour(Date date){
        return formatWithHour.format(date);
    }

    public static String getToday(){
        return formatDate(new Date());
    }

    public static Date getDate(String date){
        try {
            return format.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

}

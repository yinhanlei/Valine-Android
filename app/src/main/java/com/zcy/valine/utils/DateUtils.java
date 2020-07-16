package com.zcy.valine.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yhl
 * on 2020/7/16
 */
public class DateUtils {

    public static String parseDate(String gmtDateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date gmtDate = sdf.parse(gmtDateString);
            Date localDate = convertGMT2Local(gmtDate);
            return sdf2.format(localDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date convertGMT2Local(Date gmtDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(gmtDate);
        int zoneOffset = c.get(Calendar.ZONE_OFFSET);
        int dstOffset = c.get(Calendar.DST_OFFSET);
        c.add(Calendar.MILLISECOND,zoneOffset+dstOffset);
        return c.getTime();
    }



}

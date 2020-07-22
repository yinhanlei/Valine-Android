package com.zcy.valine.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by Administrator
 * on 2020/7/22
 */
public class SDUtils {

    public static String getSDPath() {
        boolean isExistSDCard = isExistSDCard();
        Log.d("SDUtils", "isExistSDCard=======" + isExistSDCard);
        String path = "";
        if (isExistSDCard) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = "/data/data/com.zcy.valine/cache";
            Log.d("SDUtils", "path=======" + path);
        }
        Log.d("SDUtils", "path= " + path);
        return path;
    }

    public static boolean isExistSDCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getPath());
            return sd.canWrite();
        } else
            return false;
    }
}

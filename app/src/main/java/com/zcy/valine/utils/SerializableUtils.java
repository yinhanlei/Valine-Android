package com.zcy.valine.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by yhl 做序列化的处理
 * on 2019/12/19
 */
public class SerializableUtils {

    private static String TAG = "SerializableUtils";

    /**
     * 将object序列化文件
     *
     * @param object
     * @param filePath
     */
    public static void serializableObjectToFile(Object object, String filePath) {
        try {
            File fil = new File(filePath);
            if (!fil.exists())
                fil.createNewFile();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(object);
            oos.close();
            Log.d(TAG, "serializable object To file : " + filePath);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 从文件取出object
     *
     * @param filePath
     * @return
     */
    public static Object getDeserializeObject(String filePath) {
        try {
            File fil = new File(filePath);
            if (!fil.exists())
                return null;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            Object object = ois.readObject();
            Log.d(TAG, "get object deserialize file : " + filePath);
            return object;
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

}

package com.zcy.valine.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yinhanlei on 2020/7/5.
 * 配置文件
 */

public class MyConfig {

    //    public static boolean isLoginSuccess = false;//是否登录成功

    public static Map<String, Boolean> loginMap = new HashMap<>();//是否登录成功，每一个应用都要登录

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));


}

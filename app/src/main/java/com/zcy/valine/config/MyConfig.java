package com.zcy.valine.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yinhanlei on 2020/7/5.
 * 配置文件
 */
public class MyConfig {
    /**
     * 当前仅能存在一个应用登录
     */
    public static String currentAppId = "";//当前登录的应用id，有值表示在登录

    public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));


}

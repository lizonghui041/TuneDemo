package com.example.lizh.tunedemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by lizh on 2016/12/7.
 * 开机启动的服务
 */

public class BootStartService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //第一次创建服务时候启动onCreate,加载资源
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO 后台服务需要处理的逻辑，一直服务的话就用死循环
            }
        }).start();
        Log.d("BootStartService", "onCreate: 服务的确启动了。。。。。");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        Intent restartService = new Intent();
        restartService.setClass(this, BootStartService.class);
        this.startService(restartService);
    }
}

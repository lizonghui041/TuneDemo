package com.example.lizh.tunedemo.BroadcastReceive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.lizh.tunedemo.service.BootStartService;

/**
 * Created by lizh on 2016/12/7.
 * 监听开机启动的广播
 */

public class BootStartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent service = new Intent(context, BootStartService.class);
        context.startService(service);
        Log.d("BootStartReceiver", "onReceive: 开机自动服务启动。。。。。");
    }
}

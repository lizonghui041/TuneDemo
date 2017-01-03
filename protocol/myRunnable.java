package com.example.lizh.tunedemo.protocol;


import static com.example.lizh.tunedemo.EQActivity.flag;

/**
 * Created by lizh on 2016/12/7.
 * 根据通信协议向C层发送更新的数据
 * 这个类放到EQActivity作为内部类，放拿到当前滤波器参数
 */
public class myRunnable implements Runnable {
    @Override
    public void run() {
        SocketClient.sendInfo(Integer.toString(flag).getBytes());//发送当前是哪个滤波器号
    }
}

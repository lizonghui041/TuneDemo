package com.example.lizh.tunedemo.protocol;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by lizh on 2016/10/11.
 * 通信类
 */
public class SocketClient {
    static Socket sc;
    static DataInputStream is;
    static DataOutputStream os;
    /*服务器IP*/
    final static String HOST = "192.168.9.69";
    /*服务器端口*/
    final static int PORT = 10005;

    /*用于接收C服务器返回的字节数组*/
    static byte[] readLen = new byte[10];

    public static void init() {
        try {
            if (sc == null) {
                sc = new Socket(HOST, PORT);
            }
            if (is == null) {
                is = new DataInputStream(sc.getInputStream());
            }
            if (os == null) {
                os = new DataOutputStream(sc.getOutputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*-----外部调用时候sendInfo(Integer.toString(int info).getBytes())---------*/
    public static boolean sendInfo(byte[] info) {
        try {
            init();
            os.write(info);//发送字节流到服务端
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException npe) {
            Log.d("SocketClient", "sendInfo: 网络通信断开");
            try {
                sc = new Socket(HOST, PORT);
                is = new DataInputStream(sc.getInputStream());
                os = new DataOutputStream(sc.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public static boolean receiveInfoFromC() {
        try {
            is.read(readLen);//接收回来的字节流
            if ((char) readLen[0] == '1') {
                return true;
            } else return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

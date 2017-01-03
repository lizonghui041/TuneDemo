package com.example.lizh.tunedemo;

/**
 * Created by lizh on 2016/9/16.
 * JNI调用类
 */
public class JnigetFloatFromCpp {
    static {
        System.loadLibrary("TestJni");//只能有一个ndk包名，如果有两个，系统识别第二个
    }

    public native float[] changeFloatFromCpp(float[] javaFloat, int javaInt);

    public static native float SeekFcbyX(int x);

    public static native void calFilter(short type, int gain, int Hz, float Q);

    public static native double biQuadResponsel(int fc);

    public static native double biQuadResponse(int no);

    public static native float[] initValues(float[] m_RspSums);

    public static native int SeekXbyFc(float fc);

    public static native float[] calCurrFilterResponse(int flag,short type,int gain,int hz,float Q,float[] m_RspSums);

}

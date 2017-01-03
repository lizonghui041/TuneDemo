package com.example.lizh.tunedemo.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.example.lizh.tunedemo.JnigetFloatFromCpp;
import com.example.lizh.tunedemo.utils.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * Created by pan on 2016/8/24.
 * hard learning and conclude by usual,then make improvements
 * <p/>
 * 小球类
 */
public class MyImageView extends ImageView implements Serializable {
    private int bitmapX;
    private int bitmapY;
    private int Hz;//fc
    private float Q;//q
    private short type;
    private int gain;
    private double dbGain;

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public int getGain() {
        return gain;
    }

    public void setGain(int gain) {
        this.gain = gain;
    }

    public double getDbGain() {
        return dbGain;
    }

    public void setDbGain(double dbGain) {
        this.dbGain = dbGain;
    }


    public float getQ() {
        return Q;
    }

    public void setQ(float q) {
        Q = q;
    }


    public int getBitmapX() {
        return bitmapX;
    }

    public void setBitmapX(int bitmapX) {
        this.bitmapX = bitmapX;
    }

    public int getBitmapY() {
        return bitmapY;
    }

    public void setBitmapY(int bitmapY) {
        this.bitmapY = bitmapY;
    }

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
    }

    public int getHz() {
        return Hz;
    }

    public void setHz(int hz) {
        Hz = hz;
        this.setBitmapX(JnigetFloatFromCpp.SeekXbyFc(hz) + 100);
    }
}

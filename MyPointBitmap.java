package com.example.lizh.tunedemo;

import android.graphics.Bitmap;

/**
 * Created by pan on 2016/8/19.
 * hard learning and conclude by usual,then make improvements
 */
public class MyPointBitmap {
    private Bitmap bitmap;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    private int flag;

    public MyPointBitmap(Bitmap bitmap, int x, int y,int flag) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.flag = flag;
    }

    private int x;
    private int y;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

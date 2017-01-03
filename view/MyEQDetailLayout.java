package com.example.lizh.tunedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by lizh on 2016/9/5.
 *
 *显示当前点状态类
 */
public class MyEQDetailLayout extends LinearLayout {
    public int getLayout_x() {
        return layout_x;
    }

    public void setLayout_x(int layout_x) {
        this.layout_x = layout_x;
    }

    public int getLayout_y() {
        return layout_y;
    }

    public void setLayout_y(int layout_y) {
        this.layout_y = layout_y;
    }

    private int layout_x;
    private int layout_y;
    public MyEQDetailLayout(Context context) {
        this(context, null);
    }

    public MyEQDetailLayout(Context context, AttributeSet attrs) {
       this(context, attrs, 0);
    }

    public MyEQDetailLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

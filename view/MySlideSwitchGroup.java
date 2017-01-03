package com.example.lizh.tunedemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.lizh.tunedemo.EQActivity;
import com.example.lizh.tunedemo.VDActivity;

/**
 * Created by lizh on 2016/10/5.
 * 滚动条类
 */
public class MySlideSwitchGroup extends ViewGroup {
    private Context context;
    private int gap = 15;
    private int bg_width = 66;//美工给图的宽
    private int bg_height = 245;//美工给图的高

    public MySlideSwitchGroup(Context context) {
        this(context, null);
    }

    public MySlideSwitchGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySlideSwitchGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    /**
     * 计算所有ChildView的宽和高，然后根据childView的计算结果，设置自己的宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 获得ViewGroup上级容器为其推荐的宽高，以及计算模式
         */
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(sizeWidth, sizeHeight);
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        VDActivity.ll_reecho.layout(gap, gap, gap + bg_width, gap + bg_height);
        VDActivity.ll_delay_leftValue.layout(gap * 2 + bg_width, gap, gap * 2 + bg_width * 2, gap + bg_height);
        VDActivity.ll_delay_rightValue.layout(gap * 3 + bg_width * 2, gap, gap * 3 + bg_width * 3, gap + bg_height);

        VDActivity.iv_reecho.layout(gap+bg_width/2-10, VDActivity.reecho_top, gap+bg_width/2+10, VDActivity.reecho_top + 20);
        VDActivity.iv_delay_leftValue.layout(gap*2+bg_width+bg_width/2-10, VDActivity.delay_left_top, gap*2+bg_width+bg_width/2+10, VDActivity.delay_left_top + 20);
        VDActivity.iv_delay_rightValue.layout(gap*3+bg_width*2+bg_width/2-10, VDActivity.delay_right_top, gap*3+bg_width*2+bg_width/2+10, VDActivity.delay_right_top + 20);
    }
}

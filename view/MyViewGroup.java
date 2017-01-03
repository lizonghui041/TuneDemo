package com.example.lizh.tunedemo.view;

import android.content.Context;

import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.lizh.tunedemo.EQActivity;


/**
 * Created by pan on 2016/8/23.
 * hard learning and conclude by usual,then make improvements
 * 调音曲线图
 */
public class MyViewGroup extends ViewGroup {
    private Context context;

    public static int m_dltW = 100;//将整个横坐标轴分成10份，每份有多少个点
    public static int m_fltNums = 17;//整个曲线的滤波器总数
    public static boolean m_isSubWoof;
    public static int[] eqFcs15 = {25, 40, 63, 100, 160, 250, 400, 630, 1000, 1600, 2500, 4000, 6300, 10000, 16000};
    public static int[] eqFcs10 = {31, 63, 125, 250, 500, 1000, 2000, 4000, 8000, 16000};
    public static int[] eqFcs5 = {100, 315, 1000, 3150, 10000};
    public static int[] eqFcsSW5 = {31, 50, 80, 125, 200};
    public static float Q_15SEG = 2.15f;
    public static float Q_10SEG = 1.40f;
    public static float Q_5SEG = 0.80f;
    public static float Q_5SEGSW = 2.15f;

    //根据EQViwe来设定调音图的上下左右移动边界
    public static final int EQ_WIDTH_LEFT = 100;
    public static final int EQ_WIDTH_WIGHT = 1100;
    public static final int EQ_DB_TOP = 120;//移动频位图的最高处
    public static final int EQ_DB_BOTTOM = 280;
    public static float Q_MIN = (float) 0.05;
    public static float Q_MAX = (float) 32.00;

    public static final int IMAGINE_BIG = 10;//小球的半边长

    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
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
    public void onLayout(boolean changed, int l, int t, int r, int b) {

        //执行子View的绘制
        EQActivity.myEQView.layout(0, 0, 1200, 500);
        EQActivity.iv_hpf.layout(EQActivity.iv_hpf.getBitmapX() - IMAGINE_BIG, EQActivity.iv_hpf.getBitmapY() - IMAGINE_BIG, EQActivity.iv_hpf.getBitmapX() + IMAGINE_BIG, EQActivity.iv_hpf.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_lpf.layout(EQActivity.iv_lpf.getBitmapX() - IMAGINE_BIG, EQActivity.iv_lpf.getBitmapY() - IMAGINE_BIG, EQActivity.iv_lpf.getBitmapX() + IMAGINE_BIG, EQActivity.iv_lpf.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_1.layout(EQActivity.iv_point_1.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_1.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_1.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_1.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_2.layout(EQActivity.iv_point_2.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_2.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_2.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_2.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_3.layout(EQActivity.iv_point_3.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_3.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_3.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_3.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_4.layout(EQActivity.iv_point_4.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_4.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_4.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_4.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_5.layout(EQActivity.iv_point_5.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_5.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_5.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_5.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_6.layout(EQActivity.iv_point_6.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_6.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_6.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_6.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_7.layout(EQActivity.iv_point_7.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_7.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_7.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_7.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_8.layout(EQActivity.iv_point_8.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_8.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_8.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_8.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_9.layout(EQActivity.iv_point_9.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_9.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_9.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_9.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_10.layout(EQActivity.iv_point_10.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_10.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_10.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_10.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_11.layout(EQActivity.iv_point_11.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_11.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_11.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_11.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_12.layout(EQActivity.iv_point_12.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_12.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_12.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_12.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_13.layout(EQActivity.iv_point_13.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_13.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_13.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_13.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_14.layout(EQActivity.iv_point_14.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_14.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_14.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_14.getBitmapY() + IMAGINE_BIG);
        EQActivity.iv_point_15.layout(EQActivity.iv_point_15.getBitmapX() - IMAGINE_BIG, EQActivity.iv_point_15.getBitmapY() - IMAGINE_BIG, EQActivity.iv_point_15.getBitmapX() + IMAGINE_BIG, EQActivity.iv_point_15.getBitmapY() + IMAGINE_BIG);
        EQActivity.tv_EQ_detail.layout(EQActivity.tv_EQ_detail.getLayout_x(), EQActivity.tv_EQ_detail.getLayout_y(),
                EQActivity.tv_EQ_detail.getLayout_x() + EQActivity.EQ_detail_width, EQActivity.tv_EQ_detail.getLayout_y() + EQActivity.EQ_detail_height);
    }
}

package com.example.lizh.tunedemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.example.lizh.tunedemo.EQActivity;
import com.example.lizh.tunedemo.R;


/**
 * Created by pan on 2016/8/19.
 * hard learning and conclude by usual,then make improvements
 * <p/>
 * 坐标系底层图
 */
public class MyEQView extends View {
    private Context mContext;
    private Paint mPaint;
    private Canvas canvas;

    public static final int HEIGHT_gap = 16;
    public static final float FIRST__X = 100;//20Hz
    public static final float SECOND_X = 100 + 232;//100Hz
    public static final float THIRD_X = 100 + 564;//1000Hz
    public static final float FOURTH_X = 100 + 896;//10kHz
    public static final float FIFTH_X = 100 + 996;//20kHz
    public static final double[] YLines =
            {
                    0.585, 1.000, 1.322, 1.585, 1.807, 2.000, 2.170, 2.322,         //30-100Hz
                    3.322, 3.907, 4.322, 4.644, 4.907, 5.129, 5.322, 5.492, 5.644,  //200-1000Hz
                    6.644, 7.229, 7.644, 7.966, 8.229, 8.451, 8.644, 8.814, 8.966,  //2000-10000Hz
                    9.966                                                           //20000Hz.
            };

    public static final int FIRST__Y = 40;//坐标系距离顶部的dp
    public static final int SECOND_Y = 200;
    public static final int THIRD_Y = 360;
    private Paint paint;

    public MyEQView(Context context) {
        this(context, null);
    }

    public MyEQView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyEQView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        this.setFocusable(false);
    }

    private void initView() {

        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.lightgray));
    }


    @Override
    protected void onDraw(Canvas canvas) {

        this.canvas = canvas;

        drawPointLine(canvas);//画每个频率点的响应曲线
        drawTotalLine(canvas);//画总响应曲线

        drawAllYLine(canvas);
        drawAllXLine(canvas);
    }

    private void drawPointLine(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.darkgray));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        //必须用path才能填充
        Path path = new Path();

        path.moveTo(FIRST__X, SECOND_Y);
        for (int i = 0; i < 1000; i += 5) {
            if (EQActivity.currFilterResponse[i] > THIRD_Y)
                EQActivity.currFilterResponse[i] = THIRD_Y;//避免阴影超出坐标系
            path.lineTo(i + 100, EQActivity.currFilterResponse[i]);
        }
        path.lineTo(FIFTH_X, SECOND_Y);
        path.lineTo(FIRST__X, SECOND_Y);//形成封闭区域才会填充该区域，不然系统默认填充区域会出错
        canvas.drawPath(path, paint);
    }

    private void drawTotalLine(Canvas canvas) {

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        Path path = new Path();
        path.moveTo(100, EQActivity.m_RspSums[0] > THIRD_Y ? THIRD_Y : EQActivity.m_RspSums[0]);
        for (int i = 0; i < 1000; i += 5) {
            if (EQActivity.m_RspSums[i] > THIRD_Y) EQActivity.m_RspSums[i] = THIRD_Y;//避免阴影超出坐标系
            if (EQActivity.m_RspSums[i] < FIRST__Y) EQActivity.m_RspSums[i] = FIRST__Y;//避免阴影超出坐标系
            path.lineTo(i + 100, EQActivity.m_RspSums[i]);
        }
        canvas.drawPath(path, paint);
    }

    /**
     * 画所有横轴,横虚轴
     *
     * @param canvas
     */
    private void drawAllXLine(Canvas canvas) {

        canvas.drawLine(FIRST__X, FIRST__Y, FIFTH_X, FIRST__Y, mPaint);
        canvas.drawText("20db", 60, FIRST__Y, mPaint);
        canvas.drawLine(FIRST__X, SECOND_Y, FIFTH_X, SECOND_Y, mPaint);
        canvas.drawText("0db", 60, SECOND_Y, mPaint);
        canvas.drawLine(FIRST__X, THIRD_Y, FIFTH_X, THIRD_Y, mPaint);
        canvas.drawText("-20db", 60, THIRD_Y, mPaint);

        //画上半区横轴
        for (int i = 1; i < 10; i++) {
            canvas.drawLine(FIRST__X, FIRST__Y + i * HEIGHT_gap, FIFTH_X, FIRST__Y + i * HEIGHT_gap, paint);
        }
        //画下半区横轴
        for (int i = 1; i < 10; i++) {
            canvas.drawLine(FIRST__X, SECOND_Y + i * HEIGHT_gap, FIFTH_X, SECOND_Y + i * HEIGHT_gap, paint);
        }
    }

    /**
     * 画所有Y轴，纵虚轴
     *
     * @param canvas
     */
    private void drawAllYLine(Canvas canvas) {

        for (int i = 0; i < YLines.length; i++) {
            canvas.drawLine((float) YLines[i] * 100 + 100, FIRST__Y, (float) YLines[i] * 100 + 100, THIRD_Y, paint);
        }
        canvas.drawLine(FIRST__X, FIRST__Y, FIRST__X, THIRD_Y, mPaint);
        canvas.drawText("20Hz", FIRST__X, THIRD_Y + 20, mPaint);
        canvas.drawLine(SECOND_X, FIRST__Y, SECOND_X, THIRD_Y, mPaint);
        canvas.drawText("100Hz", SECOND_X, THIRD_Y + 20, mPaint);
        canvas.drawLine(THIRD_X, FIRST__Y, THIRD_X, THIRD_Y, mPaint);
        canvas.drawText("1kHz", THIRD_X, THIRD_Y + 20, mPaint);
        canvas.drawLine(FOURTH_X, FIRST__Y, FOURTH_X, THIRD_Y, mPaint);
        canvas.drawText("10kHz", FOURTH_X, THIRD_Y + 20, mPaint);
        canvas.drawLine(FIFTH_X, FIRST__Y, FIFTH_X, THIRD_Y, mPaint);
        canvas.drawText("20kHz", FIFTH_X, THIRD_Y + 20, mPaint);
    }
}

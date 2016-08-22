package com.example.lizh.tunedemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * Created by pan on 2016/8/19.
 * hard learning and conclude by usual,then make improvements
 */
public class MyEQView extends ViewGroup {
    private Context mContext;
    private Paint mPaint;
    private Paint mPaint2;
    private Resources res;
    private DisplayMetrics dm;
    private Bitmap point_1;
    private Bitmap point_2;
    private Bitmap point_3;
    private MyPointBitmap myPointBitmap_1;
    private MyPointBitmap myPointBitmap_2;
    private MyPointBitmap myPointBitmap_3;
    private ArrayList<MyPointBitmap> myPointBitmaps;

    public MyEQView(Context context) {
        this(context, null);
    }

    public MyEQView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyEQView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    private void initView() {
        this.res = mContext.getResources();
        this.mPaint = new Paint();
        mPaint2 = new Paint();

        point_1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.point_1), 80, 80, true);
        point_2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.point_2), 80, 80, true);
        point_3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.point_3), 80, 80, true);

        myPointBitmap_1 = new MyPointBitmap(point_1, 180, 250, 0);
        myPointBitmap_2 = new MyPointBitmap(point_2, 330, 250, 1);
        myPointBitmap_3 = new MyPointBitmap(point_3, 620, 250, 2);
        myPointBitmaps = new ArrayList<>();
        myPointBitmaps.add(myPointBitmap_1);
        myPointBitmaps.add(myPointBitmap_2);
        myPointBitmaps.add(myPointBitmap_3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#BFBFBF"));
        mPaint2.setColor(Color.BLACK);
        drawAllYLine(canvas);
        drawAllXLine(canvas);

        drawBalls(canvas);
    }

    private void drawBalls(Canvas canvas) {

        for (MyPointBitmap mpb : myPointBitmaps) {
            canvas.drawBitmap(mpb.getBitmap(), mpb.getX(), mpb.getY(), mPaint);
        }
    }

    private void drawAllXLine(Canvas canvas) {
        canvas.drawLine(100, 100, 1000, 100, mPaint);
        canvas.drawText("20db", 50, 100, mPaint);
        canvas.drawLine(100, 250, 1000, 250, mPaint);
        canvas.drawText("0db", 50, 250, mPaint);
        canvas.drawLine(100, 400, 1000, 400, mPaint);
        canvas.drawText("-20db", 50, 400, mPaint);
    }


    /**
     * 画所有Y轴，包括纵向表格
     *
     * @param canvas
     */
    private void drawAllYLine(Canvas canvas) {
        canvas.drawLine(100, 400, 100, 100, mPaint);
        canvas.drawText("20Hz", 100, 420, mPaint);
        canvas.drawLine(350, 400, 350, 100, mPaint);
        canvas.drawText("100Hz", 350, 420, mPaint);
        canvas.drawLine(700, 400, 700, 100, mPaint);
        canvas.drawText("1kHz", 700, 420, mPaint);
        canvas.drawLine(900, 400, 900, 100, mPaint);
        canvas.drawText("10kHz", 900, 420, mPaint);
        canvas.drawLine(1000, 400, 1000, 100, mPaint);
        canvas.drawText("20kHz", 1000, 420, mPaint);
    }
    /**
     * 根据手机分辨率从dp单位转为px像素
     * @param
     * @return
     */
    private int dip2px(float dpValue) {
        return (int) (dpValue * dm.density + .5f);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int flag = myPointBitmaps.size();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("MyEQView", "onTouch: 点击了屏幕" + "x=" + event.getX() + ",y=" + event.getY());
                for (int i = 0; i < myPointBitmaps.size(); i++) {
                    if (chooseWhichBitmap(event, myPointBitmaps.get(i))) {
                        flag = i;
                    }
                }
            case MotionEvent.ACTION_MOVE:
                switch (flag){
                    case 0:
                        myPointBitmap_1.setX((int) event.getX());
                        Log.d("MyEQView", "onTouchEvent:执行了 ");
                        Log.e("MyEQView", "onTouchEvent: event.getX()=" + event.getX() + "myPointBitmap_1.getX()=" + myPointBitmap_1.getX());
                        myPointBitmap_1.setY((int) event.getY());
                        break;
                    case 1:
                        myPointBitmap_2.setX((int) event.getX());
                        myPointBitmap_2.setY((int) event.getY());
                        break;
                    case 2:
                        myPointBitmap_3.setX((int) event.getX());
                        myPointBitmap_3.setY((int) event.getY());
                        break;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                flag = myPointBitmaps.size();
                break;
        }

        return true;
    }


    private boolean chooseWhichBitmap(MotionEvent event, MyPointBitmap myPointBitmap) {
        int x = myPointBitmap.getX();
        int y = myPointBitmap.getY();
        Log.d("MyEQView", "chooseWhichBitmap:x= " + myPointBitmap.getX() + ",y=" + myPointBitmap.getY());

        if (event.getX() < x || event.getX() > (x + myPointBitmap.getBitmap().getWidth()) || event.getY() < y || event.getY() > (y + myPointBitmap.getBitmap().getHeight())) {
            return false;
        } else {
            Log.d("MyEQView", "chooseWhichBitmap: 选中了" + myPointBitmap.getFlag());
            return true;
        }
    }

}

package com.example.lizh.tunedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pan on 2016/8/18.
 * hard learning and conclude by usual,then make improvements
 */
public class MyPointView extends View {
    private Context context;
    private Paint mPaint;
    private Bitmap bitmap;
    private int x;
    private int y;


    public MyPointView(Context context) {
        this(context, null);
    }

    public MyPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setStartPoint(Point startPoint) {
        this.y = startPoint.y;
        this.x = startPoint.x;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, this.x, this.y, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                this.x = (int) event.getX();
                this.y = (int) event.getY();
                invalidate();
            case MotionEvent.ACTION_UP:
        }
        return true;
    }
}

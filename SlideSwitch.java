package com.example.lizh.tunedemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pan on 2016/8/16.
 * hard learning and conclude by usual,then make improvements
 */
public class SlideSwitch extends View implements View.OnTouchListener {
    /**
     * 上下文
     */
    private Context context;

    /**
     * 当前位置
     */
    private int currentY;
    /**
     * 最大滑动距离
     */
    private int max_move;

    /**
     * 最小滑动距离
     */
    private int min_move;

    /**
     * 底层背景
     */
    private Bitmap bgBmp;
    /**
     * 上层按钮背景
     */
    private Bitmap btnBmp;

    /**
     * 画笔
     */
    private Paint paint;

    /**
     * 最大值
     */
    private int max;

    /**
     * 当前值
     */
    private int currentValue;

    /**
     * 手动设置初始值
     */
    public void setInitValue(int initValue) {
        btn_top = initValue;
        invalidate();//重绘
    }

    /**
     * 控件宽
     */
    private int mWidth;
    /**
     * 控件高
     */
    private int mHeight;
    /**
     * 控件上按钮的宽
     */
    private int mbtWidth;

    /**
     * 控件上按钮的高
     */
    private int mbtHeight;


    private int btn_top ;

    public SlideSwitch(Context context) {
        this(context,null);

    }

    public SlideSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        init();

        initAttrs(attrs);
    }

    /**
     * 测量控件大小
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mWidth, mHeight);
    }

    /**
     * 绘组件
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bgBmp, 0, 0, paint);
        canvas.drawBitmap(btnBmp, bgBmp.getWidth() / 2 - btnBmp.getWidth() / 2, btn_top, paint);
        currentValue = (bgBmp.getHeight() - btn_top - btnBmp.getHeight()) * max / (bgBmp.getHeight() - btnBmp.getHeight()) ;
        if(mListener != null){
            mListener.onChange(currentValue,btn_top);
        }
    }


    /**
     * 初始化
     */
    private void init() {
        // 画笔
        paint = new Paint();
        // 开启抗锯齿
        paint.setAntiAlias(true);
        setOnTouchListener(this);
    }

    /**
     * 获取xml配置文件中的属性值
     * @param attrs
     */
    public void initAttrs(AttributeSet attrs) {
        TypedArray te = getContext().obtainStyledAttributes(attrs,
                R.styleable.SlideSwitch);

        // 获取xml中图片的id
        int bgId = te.getResourceId(R.styleable.SlideSwitch_background, 0);
        int btnbgId = te.getResourceId(R.styleable.SlideSwitch_btn_background,0);
        // 根据id获取图片
        bgBmp = BitmapFactory.decodeResource(getResources(), bgId);
        btnBmp = BitmapFactory.decodeResource(getResources(), btnbgId);

        // 获取控件尺寸信息
        mWidth = (int) te.getDimension(R.styleable.SlideSwitch_mwidth, 0f);
        mHeight = (int) te.getDimension(R.styleable.SlideSwitch_mheight, 0f);
        mbtWidth = (int) te.getDimension(R.styleable.SlideSwitch_mbt_width, 0f);
        mbtHeight = (int) te.getDimension(R.styleable.SlideSwitch_mbt_height, 0f);
        max = te.getInteger(R.styleable.SlideSwitch_max,100);

        //如果mwidth属性未配置
        if (mWidth == 0f) {
            mWidth = bgBmp.getWidth();
        }

        //如果mheight属性未配置
        if (mHeight == 0f) {
            mHeight = bgBmp.getHeight();
        }

        //如果mbt_width属性未配置
        if (mbtWidth == 0f) {
            mbtWidth = btnBmp.getWidth();
        }
        //如果mbt_width属性未配置
        if (mbtHeight == 0f) {
            mbtHeight = btnBmp.getHeight();
        }

        // 按xml中设置的尺寸改变bitmap
        bgBmp = Bitmap.createScaledBitmap(bgBmp, mWidth, mHeight, true);
        btnBmp = Bitmap.createScaledBitmap(btnBmp, mbtWidth, mbtHeight, true);

        te.recycle();
    }

    /**
     * 滑动事件
     */
    public boolean onTouch(View view, MotionEvent event) {
        min_move = btnBmp.getHeight()/2;
        max_move = bgBmp.getHeight() - btnBmp.getHeight()/2;
        currentY = (int) event.getY();
        if (currentY <= min_move)
            currentY = min_move;
        if (currentY >= max_move)
            currentY = max_move;
        switch (event.getAction()) {
            // 按下
            case MotionEvent.ACTION_DOWN:
                Log.d("SlideSwitch", "onTouch: 点击了屏幕" + "x=" + event.getX() + ",y=" + event.getY());
                break;
            // 移动
            case MotionEvent.ACTION_MOVE:
                btn_top = currentY - btnBmp.getHeight() / 2;
                break;
            // 抬起
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    /*
     * 设置监听事件
     */
    private OnChangeListener mListener = null;

    public void setOnChangeListener(OnChangeListener listener) {
        mListener = listener;
    }

    /**
     * 监听时的接口
     *
     * @author longmap
     */
    public interface OnChangeListener {
         void onChange(int currentValue,int btn_top);
    }

}

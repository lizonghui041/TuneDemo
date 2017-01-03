package com.example.lizh.tunedemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lizh.tunedemo.view.MySlideSwitchGroup;

/**
 * Created by lizh on 2016/9/30. 测试一下从github下载项目
 * 滚动条
 */
public class VDActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView tv_reechoValue;
    private TextView tv_delay_leftValue;
    private TextView tv_delay_rightValue;
    public static int reecho_top;
    public static int delay_left_top;
    public static int delay_right_top;

    private static int value_reecho;
    private static int value_delay_left;
    private static int value_delay_right;

    public static ImageView iv_delay_leftValue;
    public static ImageView iv_delay_rightValue;
    public static ImageView iv_reecho;
    public static ImageView iv_bg_reecho;
    public static ImageView iv_bg_delay_leftValue;
    public static ImageView iv_bg_delay_rightValue;
    private int currentSS;
    private MySlideSwitchGroup mySlideSwitchGroup;
    public static LinearLayout ll_reecho;
    public static LinearLayout ll_delay_leftValue;
    public static LinearLayout ll_delay_rightValue;
    private Button vd_bt_back;

    private int max_reecho = 100;
    private int max_delay_left = 60;
    private int max_delay_right = 40;
    private int top = 60;
    private int button = 240;
    private int scroll_long = 180;
    private int totalFocusNum = 3;
    private TextView vd_tv_bgm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug_voice);
        sp = getApplicationContext().getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        initView();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentSS = 0;
        iv_reecho.requestFocus();
        tv_reechoValue.setText(value_reecho + "");
        tv_delay_leftValue.setText(value_delay_left + "");
        tv_delay_rightValue.setText(value_delay_right + "");
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return true;
    }

    private void reset() {
        value_reecho = 20;
        value_delay_left = 20;
        value_delay_right = 20;
        reecho_top = (max_reecho - value_reecho) * scroll_long / max_reecho + top;
        delay_left_top = (max_delay_left - value_delay_left) * scroll_long / max_delay_left + top;
        delay_right_top = (max_delay_right - value_delay_right) * scroll_long / max_delay_right + top;
    }

    private void initView() {
        mySlideSwitchGroup = (MySlideSwitchGroup) findViewById(R.id.MySlideSwitchGroup);
        tv_reechoValue = (TextView) findViewById(R.id.tv_reechoValue);
        tv_delay_leftValue = (TextView) findViewById(R.id.tv_delay_leftValue);
        tv_delay_rightValue = (TextView) findViewById(R.id.tv_delay_rightValue);
        vd_tv_bgm = (TextView) findViewById(R.id.VD_tv_BGM);

        ll_reecho = (LinearLayout) findViewById(R.id.ll_reecho);
        ll_delay_leftValue = (LinearLayout) findViewById(R.id.ll_delay_leftValue);
        ll_delay_rightValue = (LinearLayout) findViewById(R.id.ll_delay_rightValue);

        iv_delay_leftValue = (ImageView) findViewById(R.id.iv_delay_leftValue);
        iv_delay_rightValue = (ImageView) findViewById(R.id.iv_delay_rightValue);
        iv_reecho = (ImageView) findViewById(R.id.iv_reecho);
        iv_bg_reecho = (ImageView) findViewById(R.id.iv_bg_reecho);
        iv_bg_delay_leftValue = (ImageView) findViewById(R.id.iv_bg_delay_leftValue);
        iv_bg_delay_rightValue = (ImageView) findViewById(R.id.iv_bg_delay_rightValue);

        vd_bt_back = (Button) findViewById(R.id.vd_bt_back);
        vd_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        value_reecho = sp.getInt("reecho", 0);
        value_delay_left = sp.getInt("delay_left", 0);
        value_delay_right = sp.getInt("delay_right", 0);
        reecho_top = (max_reecho - value_reecho) * scroll_long / max_reecho + top;
        delay_left_top = (max_delay_left - value_delay_left) * scroll_long / max_delay_left + top;
        delay_right_top = (max_delay_right - value_delay_right) * scroll_long / max_delay_right + top;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (currentSS == 0) currentSS = totalFocusNum;
                else currentSS -= 1;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (currentSS == totalFocusNum) currentSS = 0;
                else currentSS += 1;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                switch (currentSS) {
                    case 0:
                        value_reecho += 1;
                        if (value_reecho >= max_reecho) value_reecho = max_reecho;
                        reecho_top = (max_reecho - value_reecho) * scroll_long / max_reecho + top;
                        reecho_top = checkTheTop(reecho_top);
                        tv_reechoValue.setText(value_reecho + "");
                        break;
                    case 1:
                        value_delay_left += 1;
                        if (value_delay_left >= max_delay_left) value_delay_left = max_delay_left;
                        delay_left_top = (max_delay_left - value_delay_left) * scroll_long / max_delay_left + top;
                        delay_left_top = checkTheTop(delay_left_top);
                        tv_delay_leftValue.setText(value_delay_left + "");
                        break;
                    case 2:
                        value_delay_right += 1;
                        if (value_delay_right >= max_delay_right)
                            value_delay_right = max_delay_right;
                        delay_right_top = (max_delay_right - value_delay_right) * scroll_long / max_delay_right + top;
                        delay_right_top = checkTheTop(delay_right_top);
                        tv_delay_rightValue.setText(value_delay_right + "");
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                switch (currentSS) {
                    case 0:
                        value_reecho -= 1;
                        if (value_reecho <= 0) value_reecho = 0;
                        reecho_top = (max_reecho - value_reecho) * scroll_long / max_reecho + top;
                        reecho_top = checkTheButton(reecho_top);
                        tv_reechoValue.setText(value_reecho + "");
                        break;
                    case 1:
                        value_delay_left -= 1;
                        if (value_delay_left <= 0) value_delay_left = 0;
                        delay_left_top = (max_delay_left - value_delay_left) * scroll_long / max_delay_left + top;
                        delay_left_top = checkTheButton(delay_left_top);
                        tv_delay_leftValue.setText(value_delay_left + "");
                        break;
                    case 2:
                        value_delay_right -= 1;
                        if (value_delay_right <= 0) value_delay_right = 0;
                        delay_right_top = (max_delay_right - value_delay_right) * scroll_long / max_delay_right + top;
                        delay_right_top = checkTheButton(delay_right_top);
                        tv_delay_rightValue.setText(value_delay_right + "");
                        break;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                editor.putInt("reecho", value_reecho);
                editor.putInt("delay_left", value_delay_left);
                editor.putInt("delay_right", value_delay_right);
                editor.commit();
                break;
        }
        refreshFocus(currentSS);
        mySlideSwitchGroup.requestLayout();
        mySlideSwitchGroup.invalidate();
        return true;
    }

    private void refreshFocus(int currentSS) {
        switch (currentSS) {
            case 0:
                iv_reecho.requestFocus();
                break;
            case 1:
                iv_delay_leftValue.requestFocus();
                break;
            case 2:
                iv_delay_rightValue.requestFocus();
                break;
            case 3:
                vd_tv_bgm.requestFocus();
                break;
        }
    }

    private int checkTheButton(int currButton) {
        if (currButton > button) currButton = button;
        return currButton;
    }

    private int checkTheTop(int currTop) {
        if (currTop < top) currTop = top;
        return currTop;
    }
}


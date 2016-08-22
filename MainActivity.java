package com.example.lizh.tunedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button showVoiceDebug;
    private Button bt_commit;
    private PopupWindow popupWindow;
    private TextView reechoValue;
    private SlideSwitch ss_reecho;
    private SharedPreferences sp;
    private int reecho_top;
    private int delay_left_top;
    private TextView delay_leftValue;
    private SlideSwitch ss_delay_left;
    private TextView delay_rightValue;
    private SlideSwitch ss_delay_right;
    private int delay_right_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getApplicationContext().getSharedPreferences("config", MODE_PRIVATE);
        showVoiceDebug = (Button) findViewById(R.id.showVoiceDebug);


    }

    public void showVoiceDebug(View view) {

        if (popupWindow == null) {
            View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.debug_voice, null);
            popupWindow = new PopupWindow(MainActivity.this);
            popupWindow.setContentView(inflate);
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.showAsDropDown(showVoiceDebug, 50, 50, Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

            reechoValue = (TextView) inflate.findViewById(R.id.reechoValue);
            ss_reecho = (SlideSwitch) inflate.findViewById(R.id.ss_reecho);
            ss_reecho.setInitValue(sp.getInt("reecho", 0));
            ss_reecho.setOnChangeListener(new SlideSwitch.OnChangeListener() {
                @Override
                public void onChange(int currentValue, int btn_top) {
                    reecho_top = btn_top;
                    reechoValue.setText(currentValue + "");

                }
            });

            delay_leftValue = (TextView) inflate.findViewById(R.id.delay_leftValue);
            ss_delay_left = (SlideSwitch)inflate.findViewById(R.id.ss_delay_left);
            ss_delay_left.setInitValue(sp.getInt("delay_left", 0));
            ss_delay_left.setOnChangeListener(new SlideSwitch.OnChangeListener() {
                @Override
                public void onChange(int currentValue, int btn_top) {
                    delay_left_top = btn_top;
                    delay_leftValue.setText(currentValue + "");

                }
            });

            delay_rightValue = (TextView) inflate.findViewById(R.id.delay_rightValue);
            ss_delay_right = (SlideSwitch) inflate.findViewById(R.id.ss_delay_right);
            ss_delay_right.setInitValue(sp.getInt("delay_right", 0));
            ss_delay_right.setOnChangeListener(new SlideSwitch.OnChangeListener() {
                @Override
                public void onChange(int currentValue, int btn_top) {
                    delay_right_top = btn_top;
                    delay_rightValue.setText(currentValue + "");
                }
            });

            bt_commit = (Button) inflate.findViewById(R.id.bt_commit);
            bt_commit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt("reecho", reecho_top);
                    editor.putInt("delay_left", delay_left_top);
                    editor.putInt("delay_right",delay_right_top);
                    editor.commit();

                }
            });

        }
    }

    public void showLine(View view) {
        Intent intent = new Intent(MainActivity.this,EQActivity.class);
        startActivity(intent);
    }
}

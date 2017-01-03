package com.example.lizh.tunedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity", "onCreate: MainActivity执行了");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getApplicationContext().getSharedPreferences("config", MODE_PRIVATE);
    }

    public void showVoiceDebug(View view) {
        Intent intent = new Intent(MainActivity.this, VDActivity.class);
        startActivity(intent);
    /*    if (popupWindow == null) {
            View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_debug_voice, null);
            popupWindow = new PopupWindow(MainActivity.this);
            popupWindow.setContentView(inflate);
            popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.showAsDropDown(showVoiceDebug, 50, 50, Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK);

        */
    }

    public void showLine(View view) {
        Intent intent = new Intent(MainActivity.this, EQActivity.class);
        startActivity(intent);

    }

    public void showC(View view) {
        JnigetFloatFromCpp fromCpp = new JnigetFloatFromCpp();
        float[] testArray = {2, 3, 4};
        float[] resultFromCpp = fromCpp.changeFloatFromCpp(testArray, 3);

        Log.d("showC", "showC: " + testArray[0] + "," + testArray[1] + "," + testArray[2] + ".");
        Log.d("showC", "showC: " + resultFromCpp[0] + "," + resultFromCpp[1] + "," + resultFromCpp[2] + ".");
    }
}

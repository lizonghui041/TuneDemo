package com.example.lizh.tunedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EQActivity extends AppCompatActivity {

    private MyPointView point_1;
    private MyPointView point_2;
    private MyPointView point_3;
    private Bitmap bitmap_1;
    private Bitmap bitmap_2;
    private Bitmap bitmap_3;
    private MyEQView myEQView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eq);

        initView();
        init();
    }

    private void init() {

    }

    private void initView() {
        myEQView = (MyEQView) findViewById(R.id.EQLines);
    }
}

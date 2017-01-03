package com.example.lizh.tunedemo;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lizh.tunedemo.protocol.myRunnable;
import com.example.lizh.tunedemo.view.MyEQDetailLayout;
import com.example.lizh.tunedemo.view.MyEQView;
import com.example.lizh.tunedemo.view.MyImageView;
import com.example.lizh.tunedemo.view.MyViewGroup;

import java.util.ArrayList;

/**
 * 控制调音图的逻辑活动类
 */
public class EQActivity extends AppCompatActivity {
    private MyViewGroup viewGroup;
    private SharedPreferences sp;

    public static MyEQView myEQView;
    public static MyEQDetailLayout tv_EQ_detail;
    public static MyImageView iv_point_1;
    public static MyImageView iv_point_2;
    public static MyImageView iv_point_3;
    public static MyImageView iv_point_4;
    public static MyImageView iv_point_5;
    public static MyImageView iv_point_6;
    public static MyImageView iv_point_7;
    public static MyImageView iv_point_8;
    public static MyImageView iv_point_9;
    public static MyImageView iv_point_10;
    public static MyImageView iv_point_11;
    public static MyImageView iv_point_12;
    public static MyImageView iv_point_13;
    public static MyImageView iv_point_14;
    public static MyImageView iv_point_15;
    public static MyImageView iv_lpf;
    public static MyImageView iv_hpf;
    public static int EQ_detail_width = 80;
    public static int EQ_detail_height = 110;
    public static final short LPF = 0;
    public static final short HPF = 1;
    public static final short LSH = 2;
    public static final short HSH = 3;
    public static final short PEQ = 4;
    public static final short PEQFIX = 5;
    public static final short GEQ = 6;
    public static final short BPF = 7;
    public static final short BPFGAIN = 8;
    public static final short BSF = 9;
    public static final short APF = 10;
    public static final int dB0 = 40;

    private TextView tv_eq_db;
    private TextView tv_eq_qValue;
    private TextView tv_eq_hz;
    private TextView tv_filterL_type;


    public static final int FLTNUMS = 16;
    public static final int SELECTED_STATE_1 = 1;//左右选择小球，上下调db值
    public static final int SELECTED_STATE_2 = 2;//左右调hz，上下调Q值
    public int MY_STATE;

    public static int flag;

    private Button eq_bt_back;
    private static float[][] m_Rsps = new float[MyViewGroup.m_fltNums][1000];//----------------得到单个滤波器响应的数组
    private float[] m_Fcs = new float[1000];
    private float[] m_sins = new float[1000];
    private float[] m_coss = new float[1000];
    private int FS = 48000;
    //    private double FS_R = 1 / 48;//--------java中两者相除就只算商。故这个就是0.0
    private double FS_R = 0.000020833333333;
    public static float[] currFilterResponse = new float[1000];
    public static float[] m_RspSums = new float[1000];
    public static float[] currFilterResponse_0 = new float[1000];
    public static float[] currFilterResponse_1 = new float[1000];
    public static float[] currFilterResponse_2 = new float[1000];
    public static float[] currFilterResponse_3 = new float[1000];
    public static float[] currFilterResponse_4 = new float[1000];
    public static float[] currFilterResponse_5 = new float[1000];
    public static float[] currFilterResponse_6 = new float[1000];
    public static float[] currFilterResponse_7 = new float[1000];
    public static float[] currFilterResponse_8 = new float[1000];
    public static float[] currFilterResponse_9 = new float[1000];
    public static float[] currFilterResponse_10 = new float[1000];
    public static float[] currFilterResponse_11 = new float[1000];
    public static float[] currFilterResponse_12 = new float[1000];
    public static float[] currFilterResponse_13 = new float[1000];
    public static float[] currFilterResponse_14 = new float[1000];
    public static float[] currFilterResponse_15 = new float[1000];
    public static float[] currFilterResponse_16 = new float[1000];
    private Button bt_reset;


    private int m_Points = 1000;
    private double b0, b1, b2, a1, a2;
    public static final double[] dbgains =
            {/*-10dB~10dB step 0.25dB*/
                    0.56234132519034907, 0.57049257970463196, 0.57876198834912063, 0.58715126379251659, 0.59566214352901048,
                    0.60429639023813286, 0.61305579214982064, 0.62194216341477615, 0.63095734448019325, 0.64010320247093089,
                    0.64938163157621132, 0.65879455344192384, 0.66834391756861455, 0.67803170171524585, 0.68785991230880761,
                    0.69783058485986638, 0.70794578438413791, 0.71820760583017051, 0.72861817451322775, 0.73917964655545965,
                    0.74989420933245587, 0.76076408192627010, 0.77179151558501236, 0.78297879418910232, 0.79432823472428149,
                    0.80584218776148187, 0.81752303794365000, 0.82937320447962848, 0.84139514164519502, 0.85359133929136588,
                    0.86596432336006535, 0.87851665640727172, 0.89125093813374556, 0.90416980592345042, 0.91727593538977958,
                    0.93057204092969903, 0.94406087628592339, 0.95774523511724119, 0.97162795157710613, 0.98571190090061622,
                    1.00000000000000000,
                    1.01449520806873620, 1.02920052719442820, 1.04411900298056430, 1.05925372517728890, 1.07460782832131740,
                    1.09018449238512760, 1.10598694343555960, 1.12201845430196330, 1.13828234525403200, 1.15478198468945830,
                    1.17152078983155980, 1.18850222743701850, 1.20572981451387440, 1.22320711904993160, 1.24093776075171960,
                    1.25892541179416730, 1.27717379758114300, 1.29568669751701940, 1.31446794578942220, 1.33352143216332400,
                    1.35285110278665030, 1.37246096100756200, 1.39235506820358410, 1.41253754462275440, 1.43301257023696270,
                    1.45378438560766180, 1.47485729276412460, 1.49623565609443340, 1.51792390324938410, 1.53992652605949210,
                    1.56224808146529040, 1.58489319246111360, 1.60786654905256080, 1.63117290922783840, 1.65481709994318150,
                    1.67880401812256030, 1.70313863167187710, 1.72782598050786350, 1.75287117760189300, 1.77827941003892280,
            };
    private ArrayList<MyImageView> myImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eq);
        sp = getApplication().getSharedPreferences("config", MODE_PRIVATE);
        myImageViews = new ArrayList<>();
        initView();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iv_point_1.requestFocus();//在即将出现界面时候让小球1获取焦点
        initdBSums(17);
    }

    private void initView() {
        //所有view都在这里找，在ViewGroup放位置，设文本等
        tv_EQ_detail = (MyEQDetailLayout) findViewById(R.id.tv_EQ_detail);
        viewGroup = (MyViewGroup) findViewById(R.id.EQGroup);
        myEQView = (MyEQView) findViewById(R.id.EQLines);
        eq_bt_back = (Button) findViewById(R.id.eq_bt_back);
        bt_reset = (Button) findViewById(R.id.bt_reset);
        iv_lpf = (MyImageView) findViewById(R.id.iv_lpf);
        iv_hpf = (MyImageView) findViewById(R.id.iv_hpf);
        iv_point_1 = (MyImageView) findViewById(R.id.iv_point_1);
        iv_point_2 = (MyImageView) findViewById(R.id.iv_point_2);
        iv_point_3 = (MyImageView) findViewById(R.id.iv_point_3);
        iv_point_4 = (MyImageView) findViewById(R.id.iv_point_4);
        iv_point_5 = (MyImageView) findViewById(R.id.iv_point_5);
        iv_point_6 = (MyImageView) findViewById(R.id.iv_point_6);
        iv_point_7 = (MyImageView) findViewById(R.id.iv_point_7);
        iv_point_8 = (MyImageView) findViewById(R.id.iv_point_8);
        iv_point_9 = (MyImageView) findViewById(R.id.iv_point_9);
        iv_point_10 = (MyImageView) findViewById(R.id.iv_point_10);
        iv_point_11 = (MyImageView) findViewById(R.id.iv_point_11);
        iv_point_12 = (MyImageView) findViewById(R.id.iv_point_12);
        iv_point_13 = (MyImageView) findViewById(R.id.iv_point_13);
        iv_point_14 = (MyImageView) findViewById(R.id.iv_point_14);
        iv_point_15 = (MyImageView) findViewById(R.id.iv_point_15);

        tv_eq_qValue = (TextView) tv_EQ_detail.findViewById(R.id.tv_EQ_QValue);
        tv_eq_hz = (TextView) tv_EQ_detail.findViewById(R.id.tv_EQ_Hz);
        tv_eq_db = (TextView) tv_EQ_detail.findViewById(R.id.tv_EQ_db);
        tv_filterL_type = (TextView) findViewById(R.id.tv_filterL_type);

        eq_bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
    }

    private void init() {


        myImageViews.add(0, iv_hpf);
        myImageViews.add(1, iv_point_1);
        myImageViews.add(2, iv_point_2);
        myImageViews.add(3, iv_point_3);
        myImageViews.add(4, iv_point_4);
        myImageViews.add(5, iv_point_5);
        myImageViews.add(6, iv_point_6);
        myImageViews.add(7, iv_point_7);
        myImageViews.add(8, iv_point_8);
        myImageViews.add(9, iv_point_9);
        myImageViews.add(10, iv_point_10);
        myImageViews.add(11, iv_point_11);
        myImageViews.add(12, iv_point_12);
        myImageViews.add(13, iv_point_13);
        myImageViews.add(14, iv_point_14);
        myImageViews.add(15, iv_point_15);
        myImageViews.add(16, iv_lpf);
        MY_STATE = 1;
        flag = 1;
        initValues();

        //通过控制属性X,Y，Q等控制ViewGroup的刷新
        iv_hpf.setBitmapY(sp.getInt("HPF_Y", MyEQView.SECOND_Y));
        iv_lpf.setBitmapY(sp.getInt("LPF_Y", MyEQView.SECOND_Y));
        iv_point_1.setBitmapY(sp.getInt("image1Y", MyEQView.SECOND_Y));
        iv_point_2.setBitmapY(sp.getInt("image2Y", MyEQView.SECOND_Y));
        iv_point_3.setBitmapY(sp.getInt("image3Y", MyEQView.SECOND_Y));
        iv_point_4.setBitmapY(sp.getInt("image4Y", MyEQView.SECOND_Y));
        iv_point_5.setBitmapY(sp.getInt("image5Y", MyEQView.SECOND_Y));
        iv_point_6.setBitmapY(sp.getInt("image6Y", MyEQView.SECOND_Y));
        iv_point_7.setBitmapY(sp.getInt("image7Y", MyEQView.SECOND_Y));
        iv_point_8.setBitmapY(sp.getInt("image8Y", MyEQView.SECOND_Y));
        iv_point_9.setBitmapY(sp.getInt("image9Y", MyEQView.SECOND_Y));
        iv_point_10.setBitmapY(sp.getInt("image10Y", MyEQView.SECOND_Y));
        iv_point_11.setBitmapY(sp.getInt("image11Y", MyEQView.SECOND_Y));
        iv_point_12.setBitmapY(sp.getInt("image12Y", MyEQView.SECOND_Y));
        iv_point_13.setBitmapY(sp.getInt("image13Y", MyEQView.SECOND_Y));
        iv_point_14.setBitmapY(sp.getInt("image14Y", MyEQView.SECOND_Y));
        iv_point_15.setBitmapY(sp.getInt("image15Y", MyEQView.SECOND_Y));


        iv_point_1.setQ(sp.getFloat("image1Q", MyViewGroup.Q_15SEG));
        iv_point_2.setQ(sp.getFloat("image2Q", MyViewGroup.Q_15SEG));
        iv_point_3.setQ(sp.getFloat("image3Q", MyViewGroup.Q_15SEG));
        iv_point_4.setQ(sp.getFloat("image4Q", MyViewGroup.Q_15SEG));
        iv_point_5.setQ(sp.getFloat("image5Q", MyViewGroup.Q_15SEG));
        iv_point_6.setQ(sp.getFloat("image6Q", MyViewGroup.Q_15SEG));
        iv_point_7.setQ(sp.getFloat("image7Q", MyViewGroup.Q_15SEG));
        iv_point_8.setQ(sp.getFloat("image8Q", MyViewGroup.Q_15SEG));
        iv_point_9.setQ(sp.getFloat("image9Q", MyViewGroup.Q_15SEG));
        iv_point_10.setQ(sp.getFloat("image10Q", MyViewGroup.Q_15SEG));
        iv_point_11.setQ(sp.getFloat("image11Q", MyViewGroup.Q_15SEG));
        iv_point_12.setQ(sp.getFloat("image12Q", MyViewGroup.Q_15SEG));
        iv_point_13.setQ(sp.getFloat("image13Q", MyViewGroup.Q_15SEG));
        iv_point_14.setQ(sp.getFloat("image14Q", MyViewGroup.Q_15SEG));
        iv_point_15.setQ(sp.getFloat("image15Q", MyViewGroup.Q_15SEG));

        iv_hpf.setType(HPF);
        iv_hpf.setQ(71);
        iv_hpf.setDbGain(0);
        iv_hpf.setGain(sp.getInt("HPF_Gain", 0));
        iv_hpf.setHz(sp.getInt("HPF_Hz", 50));//全部但不是超低音计算先
        iv_lpf.setType(LPF);
        iv_lpf.setQ(71);
        iv_lpf.setDbGain(0);
        iv_lpf.setGain(sp.getInt("LPF_Gain", 0));
        iv_lpf.setHz(sp.getInt("LPF_Hz", 14000));

        iv_point_1.setType(PEQFIX);
        iv_point_1.setDbGain(0.0);
        iv_point_1.setGain(sp.getInt("image1Gain", dB0));
        iv_point_1.setHz(sp.getInt("image1Hz", MyViewGroup.eqFcs15[1 - 1]));
        iv_point_2.setType(PEQFIX);
        iv_point_2.setDbGain(0.0);
        iv_point_2.setGain(sp.getInt("image2Gain", dB0));
        iv_point_2.setHz(sp.getInt("image2Hz", MyViewGroup.eqFcs15[2 - 1]));
        iv_point_3.setType(PEQFIX);
        iv_point_3.setDbGain(0.0);
        iv_point_3.setGain(sp.getInt("image3Gain", dB0));
        iv_point_3.setHz(sp.getInt("image3Hz", MyViewGroup.eqFcs15[3 - 1]));
        iv_point_4.setType(PEQFIX);
        iv_point_4.setDbGain(0.0);
        iv_point_4.setGain(sp.getInt("image4Gain", dB0));
        iv_point_4.setHz(sp.getInt("image4Hz", MyViewGroup.eqFcs15[4 - 1]));
        iv_point_5.setType(PEQFIX);
        iv_point_5.setDbGain(0.0);
        iv_point_5.setGain(sp.getInt("image5Gain", dB0));
        iv_point_5.setHz(sp.getInt("image5Hz", MyViewGroup.eqFcs15[5 - 1]));

        iv_point_6.setType(PEQFIX);
        iv_point_6.setDbGain(0.0);
        iv_point_6.setGain(sp.getInt("image6Gain", dB0));
        iv_point_6.setHz(sp.getInt("image6Hz", MyViewGroup.eqFcs15[6 - 1]));
        iv_point_7.setType(PEQFIX);
        iv_point_7.setDbGain(0.0);
        iv_point_7.setGain(sp.getInt("image7Gain", dB0));
        iv_point_7.setHz(sp.getInt("image7Hz", MyViewGroup.eqFcs15[7 - 1]));
        iv_point_8.setType(PEQFIX);
        iv_point_8.setDbGain(0.0);
        iv_point_8.setGain(sp.getInt("image8Gain", dB0));
        iv_point_8.setHz(sp.getInt("image8Hz", MyViewGroup.eqFcs15[8 - 1]));
        iv_point_9.setType(PEQFIX);
        iv_point_9.setDbGain(0.0);
        iv_point_9.setGain(sp.getInt("image9Gain", dB0));
        iv_point_9.setHz(sp.getInt("image9Hz", MyViewGroup.eqFcs15[9 - 1]));
        iv_point_10.setType(PEQFIX);
        iv_point_10.setDbGain(0.0);
        iv_point_10.setGain(sp.getInt("image10Gain", dB0));
        iv_point_10.setHz(sp.getInt("image10Hz", MyViewGroup.eqFcs15[10 - 1]));
        iv_point_11.setType(PEQFIX);
        iv_point_11.setDbGain(0.0);
        iv_point_11.setGain(sp.getInt("image11Gain", dB0));
        iv_point_11.setHz(sp.getInt("image11Hz", MyViewGroup.eqFcs15[11 - 1]));
        iv_point_12.setType(PEQFIX);
        iv_point_12.setDbGain(0.0);
        iv_point_12.setGain(sp.getInt("image12Gain", dB0));
        iv_point_12.setHz(sp.getInt("image12Hz", MyViewGroup.eqFcs15[12 - 1]));
        iv_point_13.setType(PEQFIX);
        iv_point_13.setDbGain(0.0);
        iv_point_13.setGain(sp.getInt("image13Gain", dB0));
        iv_point_13.setHz(sp.getInt("image13Hz", MyViewGroup.eqFcs15[13 - 1]));
        iv_point_14.setType(PEQFIX);
        iv_point_14.setDbGain(0.0);
        iv_point_14.setGain(sp.getInt("image14Gain", dB0));
        iv_point_14.setHz(sp.getInt("image14Hz", MyViewGroup.eqFcs15[14 - 1]));
        iv_point_15.setType(PEQFIX);
        iv_point_15.setDbGain(0.0);
        iv_point_15.setGain(sp.getInt("image15Gain", dB0));
        iv_point_15.setHz(sp.getInt("image15Hz", MyViewGroup.eqFcs15[15 - 1]));

        refreshEQDetailLayoutAndFocus(iv_point_1);//初始化显示第一个小球的情况
        setLPFOrderNumberByY(iv_lpf);//设置初始化的order值
        setHPFOrderNumberByY(iv_hpf);//设置初始化的order值


    }

    private void reset() {
        MY_STATE = 1;
        flag = 1;
        initValues();
        for (flag = 1; flag < 16; flag++) {
            myImageViews.get(flag).setQ(MyViewGroup.Q_15SEG);
        }

        iv_hpf.setType(HPF);
        iv_hpf.setQ(71);
        iv_hpf.setDbGain(0);
        iv_hpf.setGain(0);
        iv_hpf.setHz(50);//全部但不是超低音计算先
        iv_lpf.setType(LPF);
        iv_lpf.setQ(71);
        iv_lpf.setDbGain(0);
        iv_lpf.setGain(0);
        iv_lpf.setHz(14000);

        iv_point_1.setType(PEQFIX);
        iv_point_1.setDbGain(0.0);
        iv_point_1.setGain(dB0);
        iv_point_1.setHz(MyViewGroup.eqFcs15[1 - 1]);//内部包含了setBitmapX
        iv_point_2.setType(PEQFIX);
        iv_point_2.setDbGain(0.0);
        iv_point_2.setGain(dB0);
        iv_point_2.setHz(MyViewGroup.eqFcs15[2 - 1]);
        iv_point_3.setType(PEQFIX);
        iv_point_3.setDbGain(0.0);
        iv_point_3.setGain(dB0);
        iv_point_3.setHz(MyViewGroup.eqFcs15[3 - 1]);
        iv_point_4.setType(PEQFIX);
        iv_point_4.setDbGain(0.0);
        iv_point_4.setGain(dB0);
        iv_point_4.setHz(MyViewGroup.eqFcs15[4 - 1]);
        iv_point_5.setType(PEQFIX);
        iv_point_5.setDbGain(0.0);
        iv_point_5.setGain(dB0);
        iv_point_5.setHz(MyViewGroup.eqFcs15[5 - 1]);
        iv_point_6.setType(PEQFIX);
        iv_point_6.setDbGain(0.0);
        iv_point_6.setGain(dB0);
        iv_point_6.setHz(MyViewGroup.eqFcs15[6 - 1]);
        iv_point_7.setType(PEQFIX);
        iv_point_7.setDbGain(0.0);
        iv_point_7.setGain(dB0);
        iv_point_7.setHz(MyViewGroup.eqFcs15[7 - 1]);
        iv_point_8.setType(PEQFIX);
        iv_point_8.setDbGain(0.0);
        iv_point_8.setGain(dB0);
        iv_point_8.setHz(MyViewGroup.eqFcs15[8 - 1]);
        iv_point_9.setType(PEQFIX);
        iv_point_9.setDbGain(0.0);
        iv_point_9.setGain(dB0);
        iv_point_9.setHz(MyViewGroup.eqFcs15[9 - 1]);
        iv_point_10.setType(PEQFIX);
        iv_point_10.setDbGain(0.0);
        iv_point_10.setGain(dB0);
        iv_point_10.setHz(MyViewGroup.eqFcs15[10 - 1]);
        iv_point_11.setType(PEQFIX);
        iv_point_11.setDbGain(0.0);
        iv_point_11.setGain(dB0);
        iv_point_11.setHz(MyViewGroup.eqFcs15[11 - 1]);
        iv_point_12.setType(PEQFIX);
        iv_point_12.setDbGain(0.0);
        iv_point_12.setGain(dB0);
        iv_point_12.setHz(MyViewGroup.eqFcs15[12 - 1]);
        iv_point_13.setType(PEQFIX);
        iv_point_13.setDbGain(0.0);
        iv_point_13.setGain(dB0);
        iv_point_13.setHz(MyViewGroup.eqFcs15[13 - 1]);
        iv_point_14.setType(PEQFIX);
        iv_point_14.setDbGain(0.0);
        iv_point_14.setGain(dB0);
        iv_point_14.setHz(MyViewGroup.eqFcs15[14 - 1]);
        iv_point_15.setType(PEQFIX);
        iv_point_15.setDbGain(0.0);
        iv_point_15.setGain(dB0);
        iv_point_15.setHz(MyViewGroup.eqFcs15[15 - 1]);

        for (int i = 0; i < MyViewGroup.m_fltNums; i++) {
            myImageViews.get(i).setBitmapY(MyEQView.SECOND_Y);
        }

        refreshEQDetailLayoutAndFocus(iv_point_1);//初始化显示第一个小球的情况
        setLPFOrderNumberByY(iv_lpf);//设置初始化的order值
        setHPFOrderNumberByY(iv_hpf);//设置初始化的order值

        initdBSums(17);
        refreshViewGroup(viewGroup);
    }

    private void setHPFOrderNumberByY(MyImageView iv_hpf) {
        if (iv_hpf.getBitmapY() == 210) iv_hpf.setGain(1);
        if (iv_hpf.getBitmapY() == 220) iv_hpf.setGain(2);
    }

    private void setLPFOrderNumberByY(MyImageView iv_lpf) {
        if (iv_lpf.getBitmapY() == 210) iv_lpf.setGain(1);
        if (iv_lpf.getBitmapY() == 220) iv_lpf.setGain(2);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("onKeyDown", "onKeyDown: 点击按下");
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (MY_STATE == SELECTED_STATE_1) {
                    if (flag == 0) flag = FLTNUMS;//焦点给到最后一个
                    else --flag;
                    switch (flag) {
                        case 0:
                            calCurrFilterResponseAndseekYByGain(iv_hpf);
                            break;
                        case 1:
                            calCurrFilterResponseAndseekYByGain(iv_point_1);
                            break;
                        case 2:
                            calCurrFilterResponseAndseekYByGain(iv_point_2);
                            break;
                        case 3:
                            calCurrFilterResponseAndseekYByGain(iv_point_3);
                            break;
                        case 4:
                            calCurrFilterResponseAndseekYByGain(iv_point_4);
                            break;
                        case 5:
                            calCurrFilterResponseAndseekYByGain(iv_point_5);
                            break;
                        case 6:
                            calCurrFilterResponseAndseekYByGain(iv_point_6);
                            break;
                        case 7:
                            calCurrFilterResponseAndseekYByGain(iv_point_7);
                            break;
                        case 8:
                            calCurrFilterResponseAndseekYByGain(iv_point_8);
                            break;
                        case 9:
                            calCurrFilterResponseAndseekYByGain(iv_point_9);
                            break;
                        case 10:
                            calCurrFilterResponseAndseekYByGain(iv_point_10);
                            break;
                        case 11:
                            calCurrFilterResponseAndseekYByGain(iv_point_11);
                            break;
                        case 12:
                            calCurrFilterResponseAndseekYByGain(iv_point_12);
                            break;
                        case 13:
                            calCurrFilterResponseAndseekYByGain(iv_point_13);
                            break;
                        case 14:
                            calCurrFilterResponseAndseekYByGain(iv_point_14);
                            break;
                        case 15:
                            calCurrFilterResponseAndseekYByGain(iv_point_15);
                            break;
                        case FLTNUMS:
                            calCurrFilterResponseAndseekYByGain(iv_lpf);
                            break;
                    }
                } else {
                    //状态2
                    switch (flag) {
                        case 0:
                            onKeyDpad_Left(iv_hpf);
                            break;
                        case 1:
                            onKeyDpad_Left(iv_point_1);
                            break;
                        case 2:
                            onKeyDpad_Left(iv_point_2);
                            break;
                        case 3:
                            onKeyDpad_Left(iv_point_3);
                            break;
                        case 4:
                            onKeyDpad_Left(iv_point_4);
                            break;
                        case 5:
                            onKeyDpad_Left(iv_point_5);
                            break;
                        case 6:
                            onKeyDpad_Left(iv_point_6);
                            break;
                        case 7:
                            onKeyDpad_Left(iv_point_7);
                            break;
                        case 8:
                            onKeyDpad_Left(iv_point_8);
                            break;
                        case 9:
                            onKeyDpad_Left(iv_point_9);
                            break;
                        case 10:
                            onKeyDpad_Left(iv_point_10);
                            break;
                        case 11:
                            onKeyDpad_Left(iv_point_11);
                            break;
                        case 12:
                            onKeyDpad_Left(iv_point_12);
                            break;
                        case 13:
                            onKeyDpad_Left(iv_point_13);
                            break;
                        case 14:
                            onKeyDpad_Left(iv_point_14);
                            break;
                        case 15:
                            onKeyDpad_Left(iv_point_15);
                            break;
                        case FLTNUMS:
                            onKeyDpad_Left(iv_lpf);
                            break;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (MY_STATE == SELECTED_STATE_1) {
                    if (flag == FLTNUMS) flag = 0;//焦点返回第一个
                    else ++flag;
                    switch (flag) {
                        case 0:
                            calCurrFilterResponseAndseekYByGain(iv_hpf);
                            break;
                        case 1:
                            calCurrFilterResponseAndseekYByGain(iv_point_1);
                            break;
                        case 2:
                            calCurrFilterResponseAndseekYByGain(iv_point_2);
                            break;
                        case 3:
                            calCurrFilterResponseAndseekYByGain(iv_point_3);
                            break;
                        case 4:
                            calCurrFilterResponseAndseekYByGain(iv_point_4);
                            break;
                        case 5:
                            calCurrFilterResponseAndseekYByGain(iv_point_5);
                            break;
                        case 6:
                            calCurrFilterResponseAndseekYByGain(iv_point_6);
                            break;
                        case 7:
                            calCurrFilterResponseAndseekYByGain(iv_point_7);
                            break;
                        case 8:
                            calCurrFilterResponseAndseekYByGain(iv_point_8);
                            break;
                        case 9:
                            calCurrFilterResponseAndseekYByGain(iv_point_9);
                            break;
                        case 10:
                            calCurrFilterResponseAndseekYByGain(iv_point_10);
                            break;
                        case 11:
                            calCurrFilterResponseAndseekYByGain(iv_point_11);
                            break;
                        case 12:
                            calCurrFilterResponseAndseekYByGain(iv_point_12);
                            break;
                        case 13:
                            calCurrFilterResponseAndseekYByGain(iv_point_13);
                            break;
                        case 14:
                            calCurrFilterResponseAndseekYByGain(iv_point_14);
                            break;
                        case 15:
                            calCurrFilterResponseAndseekYByGain(iv_point_15);
                            break;
                        case FLTNUMS:
                            calCurrFilterResponseAndseekYByGain(iv_lpf);
                            break;
                    }
                } else {
                    //状态2
                    switch (flag) {
                        case 0:
                            onKeyDpad_Right(iv_hpf);
                            break;
                        case 1:
                            onKeyDpad_Right(iv_point_1);
                            break;
                        case 2:
                            onKeyDpad_Right(iv_point_2);
                            break;
                        case 3:
                            onKeyDpad_Right(iv_point_3);
                            break;
                        case 4:
                            onKeyDpad_Right(iv_point_4);
                            break;
                        case 5:
                            onKeyDpad_Right(iv_point_5);
                            break;
                        case 6:
                            onKeyDpad_Right(iv_point_6);
                            break;
                        case 7:
                            onKeyDpad_Right(iv_point_7);
                            break;
                        case 8:
                            onKeyDpad_Right(iv_point_8);
                            break;
                        case 9:
                            onKeyDpad_Right(iv_point_9);
                            break;
                        case 10:
                            onKeyDpad_Right(iv_point_10);
                            break;
                        case 11:
                            onKeyDpad_Right(iv_point_11);
                            break;
                        case 12:
                            onKeyDpad_Right(iv_point_12);
                            break;
                        case 13:
                            onKeyDpad_Right(iv_point_13);
                            break;
                        case 14:
                            onKeyDpad_Right(iv_point_14);
                            break;
                        case 15:
                            onKeyDpad_Right(iv_point_15);
                            break;
                        case FLTNUMS:
                            onKeyDpad_Right(iv_lpf);
                            break;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (MY_STATE == SELECTED_STATE_1) {
                    //状态1
                    switch (flag) {
                        case 0:
                            if (iv_hpf.getBitmapY() == 210) {
                                iv_hpf.setBitmapY(200);
                                iv_hpf.setGain(0);
                            } else if (iv_hpf.getBitmapY() == 220) {
                                iv_hpf.setBitmapY(210);
                                iv_hpf.setGain(1);
                            }
                            calCurrFilterResponseAndseekYByGain(iv_hpf);
                            break;
                        case 1:
                            onKeyDpad_Up(iv_point_1);
                            break;
                        case 2:
                            onKeyDpad_Up(iv_point_2);
                            break;
                        case 3:
                            onKeyDpad_Up(iv_point_3);
                            break;
                        case 4:
                            onKeyDpad_Up(iv_point_4);
                            break;
                        case 5:
                            onKeyDpad_Up(iv_point_5);
                            break;
                        case 6:
                            onKeyDpad_Up(iv_point_6);
                            break;
                        case 7:
                            onKeyDpad_Up(iv_point_7);
                            break;
                        case 8:
                            onKeyDpad_Up(iv_point_8);
                            break;
                        case 9:
                            onKeyDpad_Up(iv_point_9);
                            break;
                        case 10:
                            onKeyDpad_Up(iv_point_10);
                            break;
                        case 11:
                            onKeyDpad_Up(iv_point_11);
                            break;
                        case 12:
                            onKeyDpad_Up(iv_point_12);
                            break;
                        case 13:
                            onKeyDpad_Up(iv_point_13);
                            break;
                        case 14:
                            onKeyDpad_Up(iv_point_14);
                            break;
                        case 15:
                            onKeyDpad_Up(iv_point_15);
                            break;
                        case FLTNUMS:
                            if (iv_lpf.getBitmapY() == 210) {
                                iv_lpf.setBitmapY(MyEQView.SECOND_Y);
                                iv_lpf.setGain(0);
                            } else if (iv_lpf.getBitmapY() == 220) {
                                iv_lpf.setBitmapY(210);
                                iv_lpf.setGain(1);
                            }
                            calCurrFilterResponseAndseekYByGain(iv_lpf);
                            break;
                    }
                } else {
                    //状态2
                    switch (flag) {
                        case 1:
                            onKeyDpad_UpForQValue(iv_point_1);
                            break;
                        case 2:
                            onKeyDpad_UpForQValue(iv_point_2);
                            break;
                        case 3:
                            onKeyDpad_UpForQValue(iv_point_3);
                            break;
                        case 4:
                            onKeyDpad_UpForQValue(iv_point_4);
                            break;
                        case 5:
                            onKeyDpad_UpForQValue(iv_point_5);
                            break;
                        case 6:
                            onKeyDpad_UpForQValue(iv_point_6);
                            break;
                        case 7:
                            onKeyDpad_UpForQValue(iv_point_7);
                            break;
                        case 8:
                            onKeyDpad_UpForQValue(iv_point_8);
                            break;
                        case 9:
                            onKeyDpad_UpForQValue(iv_point_9);
                            break;
                        case 10:
                            onKeyDpad_UpForQValue(iv_point_10);
                            break;
                        case 11:
                            onKeyDpad_UpForQValue(iv_point_11);
                            break;
                        case 12:
                            onKeyDpad_UpForQValue(iv_point_12);
                            break;
                        case 13:
                            onKeyDpad_UpForQValue(iv_point_13);
                            break;
                        case 14:
                            onKeyDpad_UpForQValue(iv_point_14);
                            break;
                        case 15:
                            onKeyDpad_UpForQValue(iv_point_15);
                            break;
                        default:
                            break;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (MY_STATE == SELECTED_STATE_1) {
                    switch (flag) {
                        case 0:
                            if (iv_hpf.getBitmapY() == MyEQView.SECOND_Y) {
                                iv_hpf.setBitmapY(210);
                                iv_hpf.setGain(1);
                            } else if (iv_hpf.getBitmapY() == 210) {
                                iv_hpf.setBitmapY(220);
                                iv_hpf.setGain(2);
                            }
                            calCurrFilterResponseAndseekYByGain(iv_hpf);
                            break;
                        case 1:
                            onKeyDpad_Down(iv_point_1);
                            break;
                        case 2:
                            onKeyDpad_Down(iv_point_2);
                            break;
                        case 3:
                            onKeyDpad_Down(iv_point_3);
                            break;
                        case 4:
                            onKeyDpad_Down(iv_point_4);
                            break;
                        case 5:
                            onKeyDpad_Down(iv_point_5);
                            break;
                        case 6:
                            onKeyDpad_Down(iv_point_6);
                            break;
                        case 7:
                            onKeyDpad_Down(iv_point_7);
                            break;
                        case 8:
                            onKeyDpad_Down(iv_point_8);
                            break;
                        case 9:
                            onKeyDpad_Down(iv_point_9);
                            break;
                        case 10:
                            onKeyDpad_Down(iv_point_10);
                            break;
                        case 11:
                            onKeyDpad_Down(iv_point_11);
                            break;
                        case 12:
                            onKeyDpad_Down(iv_point_12);
                            break;
                        case 13:
                            onKeyDpad_Down(iv_point_13);
                            break;
                        case 14:
                            onKeyDpad_Down(iv_point_14);
                            break;
                        case 15:
                            onKeyDpad_Down(iv_point_15);
                            break;
                        case FLTNUMS:
                            if (iv_lpf.getBitmapY() == MyEQView.SECOND_Y) {
                                iv_lpf.setBitmapY(210);
                                iv_lpf.setGain(1);
                            } else if (iv_lpf.getBitmapY() == 210) {
                                iv_lpf.setBitmapY(220);
                                iv_lpf.setGain(2);
                            }
                            calCurrFilterResponseAndseekYByGain(iv_lpf);
                            break;
                    }
                } else {
                    switch (flag) {
                        case 1:
                            onKeyDpad_DownForQValue(iv_point_1);
                            break;
                        case 2:
                            onKeyDpad_DownForQValue(iv_point_2);
                            break;
                        case 3:
                            onKeyDpad_DownForQValue(iv_point_3);
                            break;
                        case 4:
                            onKeyDpad_DownForQValue(iv_point_4);
                            break;
                        case 5:
                            onKeyDpad_DownForQValue(iv_point_5);
                            break;
                        case 6:
                            onKeyDpad_DownForQValue(iv_point_6);
                            break;
                        case 7:
                            onKeyDpad_DownForQValue(iv_point_7);
                            break;
                        case 8:
                            onKeyDpad_DownForQValue(iv_point_8);
                            break;
                        case 9:
                            onKeyDpad_DownForQValue(iv_point_9);
                            break;
                        case 10:
                            onKeyDpad_DownForQValue(iv_point_10);
                            break;
                        case 11:
                            onKeyDpad_DownForQValue(iv_point_11);
                            break;
                        case 12:
                            onKeyDpad_DownForQValue(iv_point_12);
                            break;
                        case 13:
                            onKeyDpad_DownForQValue(iv_point_13);
                            break;
                        case 14:
                            onKeyDpad_DownForQValue(iv_point_14);
                            break;
                        case 15:
                            onKeyDpad_DownForQValue(iv_point_15);
                            break;
                        default:
                            break;
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                SharedPreferences.Editor editor = sp.edit();
                if (MY_STATE == SELECTED_STATE_1) {
                    //左右不变，上下变Y，Gain
                    editor.putInt("HPF_Y", iv_hpf.getBitmapY());
                    editor.putInt("HPF_Gain", iv_hpf.getGain());
                    editor.putInt("LPF_Y", iv_lpf.getBitmapY());
                    editor.putInt("LPF_Gain", iv_lpf.getGain());
                    editor.putInt("image1Y", iv_point_1.getBitmapY());
                    editor.putInt("image2Y", iv_point_2.getBitmapY());
                    editor.putInt("image3Y", iv_point_3.getBitmapY());
                    editor.putInt("image4Y", iv_point_4.getBitmapY());
                    editor.putInt("image5Y", iv_point_5.getBitmapY());
                    editor.putInt("image6Y", iv_point_6.getBitmapY());
                    editor.putInt("image7Y", iv_point_7.getBitmapY());
                    editor.putInt("image8Y", iv_point_8.getBitmapY());
                    editor.putInt("image9Y", iv_point_9.getBitmapY());
                    editor.putInt("image10Y", iv_point_10.getBitmapY());
                    editor.putInt("image11Y", iv_point_11.getBitmapY());
                    editor.putInt("image12Y", iv_point_12.getBitmapY());
                    editor.putInt("image13Y", iv_point_13.getBitmapY());
                    editor.putInt("image14Y", iv_point_14.getBitmapY());
                    editor.putInt("image15Y", iv_point_15.getBitmapY());
                    editor.putInt("image1Gain", iv_point_1.getGain());
                    editor.putInt("image2Gain", iv_point_2.getGain());
                    editor.putInt("image3Gain", iv_point_3.getGain());
                    editor.putInt("image4Gain", iv_point_4.getGain());
                    editor.putInt("image5Gain", iv_point_5.getGain());
                    editor.putInt("image6Gain", iv_point_6.getGain());
                    editor.putInt("image7Gain", iv_point_7.getGain());
                    editor.putInt("image8Gain", iv_point_8.getGain());
                    editor.putInt("image9Gain", iv_point_9.getGain());
                    editor.putInt("image10Gain", iv_point_10.getGain());
                    editor.putInt("image11Gain", iv_point_11.getGain());
                    editor.putInt("image12Gain", iv_point_12.getGain());
                    editor.putInt("image13Gain", iv_point_13.getGain());
                    editor.putInt("image14Gain", iv_point_14.getGain());
                    editor.putInt("image15Gain", iv_point_15.getGain());
                    editor.commit();
                    MY_STATE = SELECTED_STATE_2;
                } else {
                    //状态2 左右变Hz,X,上下变Q
                    editor.putInt("HPF_Hz", iv_hpf.getHz());
                    editor.putInt("LPF_Hz", iv_lpf.getHz());
                    editor.putInt("image1Hz", iv_point_1.getHz());
                    editor.putInt("image2Hz", iv_point_2.getHz());
                    editor.putInt("image3Hz", iv_point_3.getHz());
                    editor.putInt("image4Hz", iv_point_4.getHz());
                    editor.putInt("image5Hz", iv_point_5.getHz());
                    editor.putInt("image6Hz", iv_point_6.getHz());
                    editor.putInt("image7Hz", iv_point_7.getHz());
                    editor.putInt("image8Hz", iv_point_8.getHz());
                    editor.putInt("image9Hz", iv_point_9.getHz());
                    editor.putInt("image10Hz", iv_point_10.getHz());
                    editor.putInt("image11Hz", iv_point_11.getHz());
                    editor.putInt("image12Hz", iv_point_12.getHz());
                    editor.putInt("image13Hz", iv_point_13.getHz());
                    editor.putInt("image14Hz", iv_point_14.getHz());
                    editor.putInt("image15Hz", iv_point_15.getHz());
                    editor.putFloat("image1Q", iv_point_1.getQ());
                    editor.putFloat("image2Q", iv_point_2.getQ());
                    editor.putFloat("image3Q", iv_point_3.getQ());
                    editor.putFloat("image4Q", iv_point_4.getQ());
                    editor.putFloat("image5Q", iv_point_5.getQ());
                    editor.putFloat("image6Q", iv_point_6.getQ());
                    editor.putFloat("image7Q", iv_point_7.getQ());
                    editor.putFloat("image8Q", iv_point_8.getQ());
                    editor.putFloat("image9Q", iv_point_9.getQ());
                    editor.putFloat("image10Q", iv_point_10.getQ());
                    editor.putFloat("image11Q", iv_point_11.getQ());
                    editor.putFloat("image12Q", iv_point_12.getQ());
                    editor.putFloat("image13Q", iv_point_13.getQ());
                    editor.putFloat("image14Q", iv_point_14.getQ());
                    editor.putFloat("image15Q", iv_point_15.getQ());
                    editor.commit();
                    MY_STATE = SELECTED_STATE_1;
                }
                return true;//按确定键时候不用再刷新界面，会浪费内存。直接返回
        }
        caldBSums(17);
        setFocusByFlag(flag);
        refreshViewGroup(viewGroup);
        /**
         * TODU 先给用户显示，再通知C层要的东西。就是当用户停止变化的值被C层响应就可以了。但是数据还是要发送过去。
         */

        new Thread(new myRunnable()).start();//在子线程中发送数据到服务端 SocketClient.sendInfo(Integer.toString(flag).getBytes());//发送当前是哪个滤波器号

        return true;
    }

    //刷新界面
    private void refreshViewGroup(MyViewGroup myViewGroup) {
        myEQView.invalidate();
        myViewGroup.requestLayout();
        myViewGroup.invalidate();
    }

    /**
     * 点击向下时候，Q值减少
     *
     * @param myImageView
     */
    private void onKeyDpad_DownForQValue(MyImageView myImageView) {
        if (myImageView.getQ() <= MyViewGroup.Q_MIN + 0.01) {//加0.01是因为浮点不准确，最小为0.05000008
            return;
        } else {
            myImageView.setQ(myImageView.getQ() - getStepByQ(myImageView.getQ()));
        }
        calCurrFilterResponseAndseekYByGain(myImageView);
    }

    /**
     * 点击向上时候，Q值增加
     *
     * @param myImageView
     */
    private void onKeyDpad_UpForQValue(MyImageView myImageView) {
        if (myImageView.getQ() >= MyViewGroup.Q_MAX) {
            return;
        } else {
            myImageView.setQ(myImageView.getQ() + getStepByQ(myImageView.getQ()));
        }
        calCurrFilterResponseAndseekYByGain(myImageView);
    }

    private float getStepByQ(float q) {
        float step = 0;
        if (q < 1.00) step = 0.05f;
        else if (q < 10.00) step = 0.10f;
        else if (q < MyViewGroup.Q_MAX + 1) step = (float) 0.50;//加1是因为浮点型不准确。最大时候为32.0004
        return step;
    }

    /**
     * 点击向下时候下移一格，,但Y值是增加
     *
     * @param myImageView
     */
    private void onKeyDpad_Down(MyImageView myImageView) {
        if (myImageView.getBitmapY() >= MyViewGroup.EQ_DB_BOTTOM) {
            return;
        } else {
            myImageView.setBitmapY((myImageView.getBitmapY() + 2));
            if (myImageView.getGain() > 0) {
                myImageView.setGain(myImageView.getGain() - 1);
            }
            calCurrFilterResponseAndseekYByGain(myImageView);
        }
    }

    /**
     * 点击向上时候上移一格，,但Y值是减少
     *
     * @param myImageView
     */
    private void onKeyDpad_Up(MyImageView myImageView) {
        if (myImageView.getBitmapY() <= MyViewGroup.EQ_DB_TOP) {
            return;
        } else {
            myImageView.setBitmapY((myImageView.getBitmapY() - 2));//这里因为layout只能是int所以要自己计算好长度
            if (myImageView.getGain() < 80) {
                myImageView.setGain(myImageView.getGain() + 1);
            }
            calCurrFilterResponseAndseekYByGain(myImageView);
        }
    }

    /**
     * 点击右键时候右移一格
     *
     * @param myImageView
     */
    private void onKeyDpad_Right(MyImageView myImageView) {
        if (myImageView.getBitmapX() >= MyViewGroup.EQ_WIDTH_WIGHT) {
            return;
        } else {
            myImageView.setHz(addHz(myImageView.getHz()));
//            myImageView.setBitmapX(myImageView.getBitmapX() + 10);
        }
        calCurrFilterResponseAndseekYByGain(myImageView);
    }

    /**
     * 点击左键时候的左移一格
     *
     * @param myImageView
     */
    private void onKeyDpad_Left(MyImageView myImageView) {
        if (myImageView.getBitmapX() <= MyViewGroup.EQ_WIDTH_LEFT) {
            return;
        } else {
            myImageView.setHz(reduceHz(myImageView.getHz()));
//            myImageView.setBitmapX(myImageView.getBitmapX() - 10);
        }
        calCurrFilterResponseAndseekYByGain(myImageView);
    }

    private int addHz(int hz) {
        if (hz < 100) hz += 1;
        else if (hz < 1000) hz += 10;
        else if (hz < 10000) hz += 100;
        else if (hz < 20000) hz += 200;
        return hz;
    }

    private int reduceHz(int hz) {
        if (hz < 100) hz -= 1;
        else if (hz < 1000) hz -= 10;
        else if (hz < 10000) hz -= 100;
        else if (hz <= 20000) hz -= 200;
        return hz;
    }

    private void calCurrFilterResponseAndseekYByGain(MyImageView myImageView) {
        switch (flag) {
            case 0:
                currFilterResponse_0 = calCurrFilterResponse(myImageView);
                currFilterResponse_0 = seekYByGain(currFilterResponse_0);
                changeYSuitToScreen(currFilterResponse_0);
                setValueToCurrFilterResponse(currFilterResponse_0, currFilterResponse);
                break;
            case 1:
                currFilterResponse_1 = calCurrFilterResponse(myImageView);
                currFilterResponse_1 = seekYByGain(currFilterResponse_1);
                changeYSuitToScreen(currFilterResponse_1);
                setValueToCurrFilterResponse(currFilterResponse_1, currFilterResponse);
                break;
            case 2:
                currFilterResponse_2 = calCurrFilterResponse(myImageView);
                currFilterResponse_2 = seekYByGain(currFilterResponse_2);
                changeYSuitToScreen(currFilterResponse_2);
                setValueToCurrFilterResponse(currFilterResponse_2, currFilterResponse);
                break;
            case 3:
                currFilterResponse_3 = calCurrFilterResponse(myImageView);
                currFilterResponse_3 = seekYByGain(currFilterResponse_3);
                changeYSuitToScreen(currFilterResponse_3);
                setValueToCurrFilterResponse(currFilterResponse_3, currFilterResponse);
                break;
            case 4:
                currFilterResponse_4 = calCurrFilterResponse(myImageView);
                currFilterResponse_4 = seekYByGain(currFilterResponse_4);
                changeYSuitToScreen(currFilterResponse_4);
                setValueToCurrFilterResponse(currFilterResponse_4, currFilterResponse);
                break;
            case 5:
                currFilterResponse_5 = calCurrFilterResponse(myImageView);
                currFilterResponse_5 = seekYByGain(currFilterResponse_5);
                changeYSuitToScreen(currFilterResponse_5);
                setValueToCurrFilterResponse(currFilterResponse_5, currFilterResponse);
                break;
            case 6:
                currFilterResponse_6 = calCurrFilterResponse(myImageView);
                currFilterResponse_6 = seekYByGain(currFilterResponse_6);
                changeYSuitToScreen(currFilterResponse_6);
                setValueToCurrFilterResponse(currFilterResponse_6, currFilterResponse);
                break;
            case 7:
                currFilterResponse_7 = calCurrFilterResponse(myImageView);
                currFilterResponse_7 = seekYByGain(currFilterResponse_7);
                changeYSuitToScreen(currFilterResponse_7);
                setValueToCurrFilterResponse(currFilterResponse_7, currFilterResponse);
                break;
            case 8:
                currFilterResponse_8 = calCurrFilterResponse(myImageView);
                currFilterResponse_8 = seekYByGain(currFilterResponse_8);
                changeYSuitToScreen(currFilterResponse_8);
                setValueToCurrFilterResponse(currFilterResponse_8, currFilterResponse);
                break;
            case 9:
                currFilterResponse_9 = calCurrFilterResponse(myImageView);
                currFilterResponse_9 = seekYByGain(currFilterResponse_9);
                changeYSuitToScreen(currFilterResponse_9);
                setValueToCurrFilterResponse(currFilterResponse_9, currFilterResponse);
                break;
            case 10:
                currFilterResponse_10 = calCurrFilterResponse(myImageView);
                currFilterResponse_10 = seekYByGain(currFilterResponse_10);
                changeYSuitToScreen(currFilterResponse_10);
                setValueToCurrFilterResponse(currFilterResponse_10, currFilterResponse);
                break;
            case 11:
                currFilterResponse_11 = calCurrFilterResponse(myImageView);
                currFilterResponse_11 = seekYByGain(currFilterResponse_11);
                changeYSuitToScreen(currFilterResponse_11);
                setValueToCurrFilterResponse(currFilterResponse_11, currFilterResponse);
                break;
            case 12:
                currFilterResponse_12 = calCurrFilterResponse(myImageView);
                currFilterResponse_12 = seekYByGain(currFilterResponse_12);
                changeYSuitToScreen(currFilterResponse_12);
                setValueToCurrFilterResponse(currFilterResponse_12, currFilterResponse);
                break;
            case 13:
                currFilterResponse_13 = calCurrFilterResponse(myImageView);
                currFilterResponse_13 = seekYByGain(currFilterResponse_13);
                changeYSuitToScreen(currFilterResponse_13);
                setValueToCurrFilterResponse(currFilterResponse_13, currFilterResponse);
                break;
            case 14:
                currFilterResponse_14 = calCurrFilterResponse(myImageView);
                currFilterResponse_14 = seekYByGain(currFilterResponse_14);
                changeYSuitToScreen(currFilterResponse_14);
                setValueToCurrFilterResponse(currFilterResponse_14, currFilterResponse);
                break;
            case 15:
                currFilterResponse_15 = calCurrFilterResponse(myImageView);
                currFilterResponse_15 = seekYByGain(currFilterResponse_15);
                changeYSuitToScreen(currFilterResponse_15);
                setValueToCurrFilterResponse(currFilterResponse_15, currFilterResponse);
                break;
            case 16:
                currFilterResponse_16 = calCurrFilterResponse(myImageView);
                currFilterResponse_16 = seekYByGain(currFilterResponse_16);
                changeYSuitToScreen(currFilterResponse_16);
                setValueToCurrFilterResponse(currFilterResponse_16, currFilterResponse);
                break;
        }
    }

    private void setValueToCurrFilterResponse(float[] currFilterResponse_0, float[] currFilterResponse) {
        for (int i = 0; i < 1000; i++) {
            currFilterResponse[i] = currFilterResponse_0[i];
        }
    }

    private void changeYSuitToScreen(float[] currFilterResponse) {
        for (int i = 0; i < 1000; i++) {
            //这里的y为了配合坐标系的y
            float y = 2 * MyEQView.SECOND_Y - currFilterResponse[i] - 79.5f;//打印时候看到0的时候，Y为120。5，对应坐标系高度应为200.200-120.5=79.5
            //这里的y为了求和坐标一致性
            y = MyEQView.SECOND_Y + (y - MyEQView.SECOND_Y) * 2;//为了使得window下Y和android图像表现上的一致性
            currFilterResponse[i] = y;
        }
    }


    public void setFocusByFlag(int currentFlag) {
        switch (currentFlag) {
            case 0:
                refreshEQDetailLayoutAndFocus(iv_hpf);
                break;
            case 1:
                refreshEQDetailLayoutAndFocus(iv_point_1);
                break;
            case 2:
                refreshEQDetailLayoutAndFocus(iv_point_2);
                break;
            case 3:
                refreshEQDetailLayoutAndFocus(iv_point_3);
                break;
            case 4:
                refreshEQDetailLayoutAndFocus(iv_point_4);
                break;
            case 5:
                refreshEQDetailLayoutAndFocus(iv_point_5);
                break;
            case 6:
                refreshEQDetailLayoutAndFocus(iv_point_6);
                break;
            case 7:
                refreshEQDetailLayoutAndFocus(iv_point_7);
                break;
            case 8:
                refreshEQDetailLayoutAndFocus(iv_point_8);
                break;
            case 9:
                refreshEQDetailLayoutAndFocus(iv_point_9);
                break;
            case 10:
                refreshEQDetailLayoutAndFocus(iv_point_10);
                break;
            case 11:
                refreshEQDetailLayoutAndFocus(iv_point_11);
                break;
            case 12:
                refreshEQDetailLayoutAndFocus(iv_point_12);
                break;
            case 13:
                refreshEQDetailLayoutAndFocus(iv_point_13);
                break;
            case 14:
                refreshEQDetailLayoutAndFocus(iv_point_14);
                break;
            case 15:
                refreshEQDetailLayoutAndFocus(iv_point_15);
                break;
            case FLTNUMS:
                refreshEQDetailLayoutAndFocus(iv_lpf);
                break;
        }
    }


    public void refreshEQDetailLayoutAndFocus(MyImageView myImageView) {
        if (flag == 0) {
            if (myImageView.getBitmapY() == MyEQView.SECOND_Y) {
                tv_filterL_type.setText("HPF");
                tv_eq_qValue.setText("bypass");
                EQ_detail_height = 60;
            } else {
                tv_filterL_type.setText("HPF");
                tv_eq_qValue.setText(myImageView.getGain() + "order");
                tv_eq_hz.setText(myImageView.getHz() + "Hz");
                EQ_detail_height = 80;
            }
        } else if (flag == FLTNUMS) {
            if (myImageView.getBitmapY() == MyEQView.SECOND_Y) {
                tv_filterL_type.setText("LPF");
                tv_eq_qValue.setText("bypass");
                EQ_detail_height = 60;
            } else {
                tv_filterL_type.setText("LPF");
                tv_eq_qValue.setText(myImageView.getGain() + "order");
                tv_eq_hz.setText(myImageView.getHz() + "Hz");
                EQ_detail_height = 80;
            }
        } else {
            tv_filterL_type.setText("PEQ");
            tv_eq_hz.setText(myImageView.getHz() + "Hz");
            tv_eq_db.setText(String.format("%1.2f", -(myImageView.getBitmapY() - MyEQView.SECOND_Y) / 8f) + "db");
            tv_eq_qValue.setText("Q：" + String.format("%1.2f", myImageView.getQ()));
            EQ_detail_height = 110;
        }

        tv_EQ_detail.setLayout_x(myImageView.getBitmapX() + 25);
        tv_EQ_detail.setLayout_y(myImageView.getBitmapY() + 25);

        myImageView.requestFocus();
    }

    private float[] caldBSums(int m_fltNums) {
        for (int i = 0; i < 1000; i++) {
            m_RspSums[i] = currFilterResponse_0[i] + currFilterResponse_1[i] + currFilterResponse_2[i]
                    + currFilterResponse_3[i] + currFilterResponse_4[i] + currFilterResponse_5[i] + currFilterResponse_6[i]
                    + currFilterResponse_7[i] + currFilterResponse_8[i] + currFilterResponse_9[i] + currFilterResponse_10[i]
                    + currFilterResponse_11[i] + currFilterResponse_12[i] + currFilterResponse_13[i] + currFilterResponse_14[i]
                    + currFilterResponse_15[i] + currFilterResponse_16[i] - 3200;//减去3200是为了得到现实在坐标系的值
        }
        return m_RspSums;
    }

    private float[] initdBSums(int m_fltNums) {
        for (flag = 0; flag < 17; flag++) {
            calCurrFilterResponseAndseekYByGain(myImageViews.get(flag));
        }
        flag = 1;
        calCurrFilterResponseAndseekYByGain(iv_point_1);
        caldBSums(17);
        return m_RspSums;
    }

    private float[] calCurrFilterResponse(MyImageView myImageView) {//用到的参数，flag，Q，Type，gain,hz
        int N, i;
        double fc0, fc, w, dB;
        if (myImageView.getType() == HPF || myImageView.getType() == LPF) {
            if (myImageView.getGain() == 0) {
                myImageView.setDbGain(0.0);
                for (i = 0; i < m_Points; i++) {
                    m_Rsps[flag][i] = (float) 0.0;//bypass=0db
                }
            } else {
                myImageView.setDbGain(-3.0);//-3db,阶数
                N = myImageView.getGain();
                fc0 = myImageView.getHz();
                for (i = 0; i < m_Points; i++) {
                    fc = m_Fcs[i];
                    if (myImageView.getType() == LPF) {
                        w = fc / fc0;
                    } else {
                        w = fc0 / fc;
                    }
                    dB = (1 + Math.pow(w, 2 * N));
                    dB = -10 * Math.log10(dB);
                    m_Rsps[flag][i] = (float) dB;
                }
            }
        } else {
            calFilter(myImageView);
            for (i = 0; i < m_Points; i++) {
                m_Rsps[flag][i] = (float) biQuadResponse(i);
            }
            myImageView.setDbGain(biQuadResponsel(myImageView.getHz()));
        }
        return m_Rsps[flag];//----------------得到单个滤波器响应的数组
    }

    private double biQuadResponsel(int fc) {
        double cs, sn, sn2, cs2, y, sqrtA, sqrtB, w;
        double AA0, AA1, BB0, BB1, AA02, AA12, BB02, BB12;
        w = Math.PI * 2 * fc * FS_R;
        sn = Math.sin(w);
        cs = Math.cos(w);
        sn2 = sn * sn;
        cs2 = cs * cs;
        AA0 = b0 * cs2 - b0 * sn2 + b1 * cs + b2;
        AA02 = AA0 * AA0;
        AA1 = 2 * b0 * cs * sn + b1 * sn;
        AA12 = AA1 * AA1;
        BB0 = cs2 - sn2 - a1 * cs - a2;
        BB02 = BB0 * BB0;
        BB1 = 2 * cs * sn - a1 * sn;
        BB12 = BB1 * BB1;
        sqrtA = Math.sqrt(AA02 + AA12);
        sqrtB = Math.sqrt(BB02 + BB12);
        y = 8.6858896384 * Math.log(sqrtA / sqrtB);//20*lg(sqrt1/sqrt2);
        return y;
    }

    //根据初始化好的数组，得到
    private double biQuadResponse(int no) {
        double cs, sn, sn2, cs2, y, sqrtA, sqrtB;
        double AA0, AA1, BB0, BB1, AA02, AA12, BB02, BB12;
        sn = m_sins[no];
        cs = m_coss[no];

        sn2 = sn * sn;
        cs2 = cs * cs;
        AA0 = b0 * cs2 - b0 * sn2 + b1 * cs + b2;
        AA02 = AA0 * AA0;
        AA1 = 2 * b0 * cs * sn + b1 * sn;
        AA12 = AA1 * AA1;
        BB0 = cs2 - sn2 - a1 * cs - a2;
        BB02 = BB0 * BB0;
        BB1 = 2 * cs * sn - a1 * sn;
        BB12 = BB1 * BB1;
        sqrtA = Math.pow(AA02 + AA12, 0.5);
        sqrtB = Math.pow(BB02 + BB12, 0.5);
        y = 8.6858896384 * Math.log(sqrtA / sqrtB);//20*lg(sqrt1/sqrt2);
        return y;

    }

    private void calFilter(MyImageView myImageView) {
        double A, w, sn, cs, alpha, beta, a0r = 0.0, slope = 1.0, q = myImageView.getQ();
        if ((myImageView.getType() >= LPF && myImageView.getType() <= HPF && myImageView.getGain() == 0)
                || (myImageView.getType() >= LSH && myImageView.getType() <= PEQFIX && myImageView.getGain() == dB0)) {
            b0 = 1.0;
            b1 = 0.0;
            b2 = 0.0;
            a1 = 0.0;
            a2 = 0.0;
            return;
        }
        A = dbgains[myImageView.getGain()];//pow(10.0,(dBgain*0.025));//(for peaking and shelving EQ filters only)
        w = Math.PI * 2 * myImageView.getHz() * FS_R;
        cs = Math.cos(w);
        sn = Math.sin(w);
        switch (myImageView.getType()) {
            case LPF:
                alpha = sn / (2.0 * q);//Q
                a0r = 1.0 / (1.0 + alpha);
                b1 = 1.0 - cs;
                b0 = 0.5 * b1; //0.5*(1 - cs);
                b2 = b0;     //0.5*(1 - cs);
                a1 = -2.0 * cs;
                a2 = 1.0 - alpha;
                break;
            case HPF:        //H(s) = s^2 / (s^2 + s/Q + 1)
                alpha = sn / (2.0 * q);//Q
                a0r = 1.0 / (1.0 + alpha);
                b0 = 0.5 * (1.0 + cs);
                b1 = -(1.0 + cs);
                b2 = b0;//0.5*(1 + cs);
                a1 = -2.0 * cs;
                a2 = 1.0 - alpha;
                break;
            case BPFGAIN://        H(s) = s / (s^2 + s/Q + 1)  (constant skirt gain, peak gain = Q)
                alpha = sn / (2.0 * q);//Q
                a0r = 1.0 / (1.0 + alpha);
                b0 = 0.5 * sn;//  =   Q*alpha
                b1 = 0;
                b2 = -sn / 2.0;//  =  -Q*alpha
                a1 = -2.0 * cs;
                a2 = 1.0 - alpha;
                break;
            case BPF://        H(s) = (s/Q) / (s^2 + s/Q + 1)      (constant 0 dB peak gain)
                alpha = sn / (2.0 * q);//Q
                a0r = 1.0 / (1.0 + alpha);
                b0 = alpha;
                b1 = 0;
                b2 = -alpha;
                a1 = -2.0 * cs;
                a2 = 1.0 - alpha;
                break;
            case BSF://      H(s) = (s^2 + 1) / (s^2 + s/Q + 1)
                alpha = sn / (2.0 * q);//Q
                a0r = 1.0 / (1.0 + alpha);
                b0 = 1.0;
                b1 = -2.0 * cs;
                b2 = 1.0;
                a1 = -2.0 * cs;
                a2 = 1.0 - alpha;
                break;
            case APF:        //H(s) = (s^2 - s/Q + 1) / (s^2 + s/Q + 1)
                alpha = sn / (2.0 * q);//Q
                a0r = 1.0 / (1.0 + alpha);
                b0 = 1.0 - alpha;
                b1 = -2.0 * cs;
                b2 = 1.0 + alpha;
                a1 = b1;//-2*cs;
                a2 = b0;//1 - alpha;
                break;
            case PEQ:
            case PEQFIX:
            case GEQ:
                //  H(s) = (s^2 + s*(A/Q) + 1) / (s^2 + s/(A*Q) + 1)
                //alpha = sn*sinh(LN2DIV2*q*w0/sn);//bw
                alpha = sn / (2.0 * q);//Q
                a0r = 1.0 / (1.0 + alpha / A);
                b0 = 1.0 + alpha * A;
                b1 = -2.0 * cs;
                b2 = 1.0 - alpha * A;
                a1 = -2.0 * cs;
                a2 = 1.0 - (alpha / A);
                break;
            case LSH: //H(s) = A * (s^2 + (sqrt(A)/Q)*s + A)/(A*s^2 + (sqrt(A)/Q)*s + 1)
                //tmp=(A+1.0/A)*(1.0/slope-1.0)+2.0;
                //q=1.0/pow(tmp,0.5);
                alpha = sn / (2.0 * q);
                beta = 2.0 * Math.pow(A, 0.5) * alpha;
                a0r = 1.0 / ((A + 1.0) + (A - 1.0) * cs + beta);
                b0 = A * ((A + 1.0) - (A - 1.0) * cs + beta);
                b1 = 2.0 * A * ((A - 1.0) - (A + 1.0) * cs);
                b2 = A * ((A + 1.0) - (A - 1.0) * cs - beta);
                a1 = -2.0 * ((A - 1.0) + (A + 1.0) * cs);
                a2 = (A + 1.0) + (A - 1.0) * cs - beta;
                break;
            case HSH:// H(s) = A * (A*s^2 + (sqrt(A)/Q)*s + 1)/(s^2 + (sqrt(A)/Q)*s + A)
                //tmp=(A+1.0/A)*(1.0/slope-1.0)+2.0;
                //q=1.0/pow(tmp,0.5);
                alpha = sn / (2.0 * q);
                beta = 2.0 * Math.pow(A, 0.5) * alpha;

                a0r = 1.0 / ((A + 1.0) - (A - 1.0) * cs + beta);
                b0 = A * ((A + 1.0) + (A - 1.0) * cs + beta);
                b1 = -2.0 * A * ((A - 1.0) + (A + 1.0) * cs);
                b2 = A * ((A + 1.0) + (A - 1.0) * cs - beta);
                a1 = 2.0 * ((A - 1.0) - (A + 1.0) * cs);
                a2 = (A + 1.0) - (A - 1.0) * cs - beta;
                break;
        }
        b0 *= a0r;
        b1 *= a0r;
        b2 *= a0r;
        a1 *= (-1 * a0r);
        a2 *= (-1 * a0r);
    }


    private float[] seekYByGain(float[] oldArray) {
        int m_dltH = 2;
        for (int i = 0; i < 1000; i++) {
            oldArray[i] = 0.5f + (oldArray[i] * 2 + 60) * m_dltH;
        }
        return oldArray;
    }

    /**
     * C部分-初始化sins，coss值
     */
    private void initValues() {
        float fc;
        double w;
        int x;
        for (int i = 0; i < m_Points; i++) {
            x = i + 100;//---对应屏幕的坐标系，第一个点对应100
            fc = JnigetFloatFromCpp.SeekFcbyX(x);
            m_Fcs[i] = fc;
            w = (2 * Math.PI * fc * FS_R);
            m_sins[i] = (float) Math.sin(w);//初始化数据表
            m_coss[i] = (float) Math.cos(w);
            m_RspSums[i] = 0.0f;//初始化总响应
        }
    }
}

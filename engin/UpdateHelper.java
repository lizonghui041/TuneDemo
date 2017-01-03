package com.example.lizh.tunedemo.engin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


import com.example.lizh.tunedemo.R;
import com.example.lizh.tunedemo.bean.UpdateBean;
import com.example.lizh.tunedemo.utils.JSONParseUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lizh on 2016/12/13.
 * 自动更新帮助类
 */

public class UpdateHelper {
    private static UpdateHelper updateHelper;
    private Activity context;

    private final int UPDATE = 1;//更新
    private final int CONNECT_ERROE = -3;//网络连接错误
    private final int DATA_ERROR = -1;//服务器数据错误
    private final int DOWNLOADERROR = -2;//下载失败

    private UpdateBean bean;
    private ProgressDialog pd;
    private Message message = new Message();
    private HttpHandler<File> download;

    private UpdateHelper(Activity context) {
        this.context = context;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    updateTipDialog();
                    break;
                case CONNECT_ERROE:
                    Toast.makeText(context, "服务器连接不上", Toast.LENGTH_SHORT).show();
                    break;
                case DATA_ERROR:
                    Toast.makeText(context, "服务器数据出错", Toast.LENGTH_SHORT).show();
                    break;
                case DOWNLOADERROR:
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    public void updateConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                connect();
            }
        }).start();
    }

    private void connect() {
        String downloadurl = context.getResources().getString(R.string.downloadurl);
        try {
            URL url = new URL(downloadurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");//设置请求方法
            con.setConnectTimeout(3000);//设置连接超时
            con.setReadTimeout(5000);//链接上了，响应超时

            if (con.getResponseCode() == 200) {
                InputStream in = con.getInputStream();
                bean = JSONParseUtil.getUpDateInfo(in);
                if (bean != null) {
                    if (bean.getVersion().equals(ViewHelper.getVersion(context))) {
                        Toast.makeText(context, "当前为最新版本", Toast.LENGTH_SHORT).show();
                        // TODO: 2016/12/13 返回当前活动
                    }
                } else {
                    message.what = UPDATE;
                    handler.sendMessage(message);
                }

            } else {
                //连接服务器失败
                message.what = DATA_ERROR;
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            //服务器连不上
            message.what = CONNECT_ERROE;
            handler.sendMessage(message);
            e.printStackTrace();
        }
    }

    public static UpdateHelper getInstance(Activity context) {
        if (updateHelper == null) {
            updateHelper = new UpdateHelper(context);
        }
        return updateHelper;
    }

    public void destroy() {
        //静态单例退出后，要赋空值
        updateHelper = null;
    }

    //提示用户升级
    private void updateTipDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("升级提醒");
        builder.setMessage(bean.getDes());
        builder.setNegativeButton("升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateAPK();
            }
        });
        builder.show();
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "更新已取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    private void updateAPK() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                download();
            }
        }).start();
    }

    private void download() {
        //判断时候有SD卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //读写SD卡，要有权限
            String target = Environment.getExternalStorageDirectory() + "/update.apk";
            HttpUtils utils = new HttpUtils();
            //参数，URL，下载文件的地址，回调接口.回调会在主线程进行
            download = utils.download(bean.getDownloadurl(), target, new RequestCallBack<File>() {
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                }

                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                    //成功后安装
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                    //解决用户在安装过程中点取消就会卡机的bug
                    context.startActivityForResult(intent, 0);
                    //然后在调用UpdateHelper的活动中复写onActivityResult方法
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "您没有安装SD卡", Toast.LENGTH_SHORT).show();
            // TODO: 2016/12/13  这里是不是应该直接不用判断，就安装在内部存储中·？
        }
    }
}

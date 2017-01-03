package com.example.lizh.tunedemo.engin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by lizh on 2016/12/13.
 */

public class ViewHelper {
    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        PackageManager pm = context.getPackageManager();//拿到包管理器
        try {
//          PackageInfo info = pm.getPackageInfo("com.lizonghui.safety", 0);
            //封装了所有的清单中数据
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

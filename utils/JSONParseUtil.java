package com.example.lizh.tunedemo.utils;

import com.example.lizh.tunedemo.bean.UpdateBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lizh on 2016/12/13.
 */

public class JSONParseUtil {
    private static UpdateBean bean;
    private static StringBuilder response;

    /**
     * 将数据流转换为String对象，再JSON解析
     * @param inputStream
     * @return
     */
    public static UpdateBean getUpDateInfo(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null){
                response.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            //这里也应该有message返回去，所以这个类最好卸载LoginHelper中，不然就卡在这里了！！！
        }
        return parseJSONObject(response.toString());
    }

    /**
     * 将String对象解析成JSON数据，再把这些字段放到我们的数据对象中，以备使用
     * @param jsonData
     * @return
     */
    private static UpdateBean parseJSONObject(String jsonData) {
        try {
            JSONObject object = new JSONObject(jsonData);
            String version = object.getString("version");
            String downloadurl = object.getString("downloadurl");
            String desc = object.getString("desc");
            bean = new UpdateBean();
            bean.setVersion(version);
            bean.setDes(desc);
            bean.setDownloadurl(downloadurl);
        } catch (JSONException e) {
            e.printStackTrace();
            // 这里也应该有message返回去，不然就卡在这里了，所以这个类最好卸载LoginHelper中
        }
        return bean;
    }
}

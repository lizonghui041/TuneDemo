package com.example.lizh.tunedemo.bean;

/**
 * Created by lizh on 2016/12/13.
 */

public class UpdateBean {
    private String version;

    private String downloadurl;

    private String des;

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDes() {
        return des;
    }

    public void testGit() {
        System.out.print("测试git");
    }

    public void setDes(String des) {
        this.des = des;
    }
}

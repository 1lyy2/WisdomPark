package com.zykj.carfigure.entity;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/7/3116:47
 * desc   : 首页轮播图
 * version: 1.0
 */
public class Banner {
    //图片url
    private String url;
    //图片的标题
    private String title;

    public Banner() {
    }

    public Banner(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

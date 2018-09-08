package com.zykj.carfigure.entity;

import java.util.List;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/7/3116:47
 * desc   : 首页轮播图
 * version: 1.0
 */
public class Banner {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":1,"image_url":"39.107.66.96/zy/image/test_img01.png","title_name":"轮播图广告测试图片","title_status":0,"title_type":0,"create_times":"2018-08-20T03:46:45.000+0000","update_times":"2018-08-20T03:46:45.000+0000","emarks":null},{"id":2,"image_url":"39.107.66.96/zy/image/test_img01.png","title_name":"首页广广告图测试","title_status":0,"title_type":0,"create_times":"2018-08-20T03:49:01.000+0000","update_times":"2018-08-20T03:49:04.000+0000","emarks":null}]
     * code : 200
     */

    private int status;
    private String message;
    private int code;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * image_url : 39.107.66.96/zy/image/test_img01.png
         * title_name : 轮播图广告测试图片
         * title_status : 0
         * title_type : 0
         * create_times : 2018-08-20T03:46:45.000+0000
         * update_times : 2018-08-20T03:46:45.000+0000
         * emarks : null
         */

        private int id;
        private String image_url;
        private String title_name;
        private int title_status;
        private int title_type;
        private String create_times;
        private String update_times;
        private Object emarks;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getTitle_name() {
            return title_name;
        }

        public void setTitle_name(String title_name) {
            this.title_name = title_name;
        }

        public int getTitle_status() {
            return title_status;
        }

        public void setTitle_status(int title_status) {
            this.title_status = title_status;
        }

        public int getTitle_type() {
            return title_type;
        }

        public void setTitle_type(int title_type) {
            this.title_type = title_type;
        }

        public String getCreate_times() {
            return create_times;
        }

        public void setCreate_times(String create_times) {
            this.create_times = create_times;
        }

        public String getUpdate_times() {
            return update_times;
        }

        public void setUpdate_times(String update_times) {
            this.update_times = update_times;
        }

        public Object getEmarks() {
            return emarks;
        }

        public void setEmarks(Object emarks) {
            this.emarks = emarks;
        }
    }
}

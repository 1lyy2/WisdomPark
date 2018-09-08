package com.zykj.carfigure.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2314:35
 * desc   : 首页功能列表
 * version: 1.0
 */
public class Function implements Serializable{

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":2,"nv_image_url":"39.107.66.96/zy/image/test_img01.png","nv_title_name":"我要停车","nv_title_status":0,"nv_title_type":1,"create_times":"2018-08-23T03:12:19.000+0000","update_times":"2018-08-23T03:12:19.000+0000","emarks":null},{"id":3,"nv_image_url":"39.107.66.96/zy/image/test_img01.png","nv_title_name":"反向寻车","nv_title_status":0,"nv_title_type":2,"create_times":"2018-08-23T03:12:42.000+0000","update_times":"2018-08-23T03:13:29.000+0000","emarks":null},{"id":4,"nv_image_url":"39.107.66.96/zy/image/test_img01.png","nv_title_name":"预定车位","nv_title_status":0,"nv_title_type":3,"create_times":"2018-08-23T03:12:42.000+0000","update_times":"2018-08-23T03:13:29.000+0000","emarks":null},{"id":5,"nv_image_url":"39.107.66.96/zy/image/test_img01.png","nv_title_name":"代缴车费","nv_title_status":0,"nv_title_type":4,"create_times":"2018-08-23T03:12:42.000+0000","update_times":"2018-08-23T03:13:29.000+0000","emarks":null},{"id":6,"nv_image_url":"39.107.66.96/zy/image/test_img01.png","nv_title_name":"快速充值","nv_title_status":0,"nv_title_type":5,"create_times":"2018-08-23T03:12:42.000+0000","update_times":"2018-08-23T03:13:29.000+0000","emarks":null},{"id":7,"nv_image_url":"39.107.66.96/zy/image/test_img01.png","nv_title_name":"我的行程","nv_title_status":0,"nv_title_type":6,"create_times":"2018-08-23T03:12:42.000+0000","update_times":"2018-08-23T03:13:29.000+0000","emarks":null},{"id":8,"nv_image_url":"39.107.66.96/zy/image/test_img01.png","nv_title_name":"我的账单","nv_title_status":0,"nv_title_type":7,"create_times":"2018-08-23T03:12:42.000+0000","update_times":"2018-08-23T03:13:29.000+0000","emarks":null},{"id":9,"nv_image_url":"39.107.66.96/zy/image/test_img01.png","nv_title_name":"停车资讯","nv_title_status":0,"nv_title_type":8,"create_times":"2018-08-23T03:12:42.000+0000","update_times":"2018-08-23T03:13:29.000+0000","emarks":null}]
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
         * id : 2
         * nv_image_url : 39.107.66.96/zy/image/test_img01.png
         * nv_title_name : 我要停车
         * nv_title_status : 0
         * nv_title_type : 1
         * create_times : 2018-08-23T03:12:19.000+0000
         * update_times : 2018-08-23T03:12:19.000+0000
         * emarks : null
         */

        private int id;
        private String nv_image_url;
        private String nv_title_name;
        private int nv_title_status;
        private int nv_title_type;
        private String create_times;
        private String update_times;
        private Object emarks;
        private int imageResources;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNv_image_url() {
            return nv_image_url;
        }

        public void setNv_image_url(String nv_image_url) {
            this.nv_image_url = nv_image_url;
        }

        public String getNv_title_name() {
            return nv_title_name;
        }

        public void setNv_title_name(String nv_title_name) {
            this.nv_title_name = nv_title_name;
        }

        public int getNv_title_status() {
            return nv_title_status;
        }

        public void setNv_title_status(int nv_title_status) {
            this.nv_title_status = nv_title_status;
        }

        public int getNv_title_type() {
            return nv_title_type;
        }

        public void setNv_title_type(int nv_title_type) {
            this.nv_title_type = nv_title_type;
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

        public int getImageResources() {
            return imageResources;
        }

        public void setImageResources(int imageResources) {
            this.imageResources = imageResources;
        }
    }
}

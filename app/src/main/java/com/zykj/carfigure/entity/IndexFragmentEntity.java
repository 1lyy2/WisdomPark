package com.zykj.carfigure.entity;

import com.amap.api.maps.model.LatLng;

import java.util.List;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/218:01
 * desc   :
 * version: 1.0
 */
public class IndexFragmentEntity {

    public static class IndexBanner {
        private List<Banner.DataBean> list;

        public IndexBanner(List<Banner.DataBean> list) {
            this.list = list;
        }

        public IndexBanner() {
        }

        public List<Banner.DataBean> getList() {
            return list;
        }

        public void setList(List<Banner.DataBean> list) {
            this.list = list;
        }

    }


    //宫格列表
    public static class Content {
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
    }

    //附近优选列表
    public static class Near {
        //图片资源url
        private String url;
        //停车场名字
        private String parkName;
        //描述
        private String description;
        //距离
        private double distance;
        //首付标准
        private String charge;
        //经纬度
        private LatLng  latLng;

        public LatLng getLatLng() {
            return latLng;
        }

        public void setLatLng(LatLng latLng) {
            this.latLng = latLng;
        }

        public Near(String url, String parkName, String description, double distance, String charge) {
            this.url = url;
            this.parkName = parkName;
            this.description = description;
            this.distance = distance;
            this.charge = charge;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getParkName() {
            return parkName;
        }

        public void setParkName(String parkName) {
            this.parkName = parkName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getCharge() {
            return charge;
        }

        public void setCharge(String charge) {
            this.charge = charge;
        }
    }


}

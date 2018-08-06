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
        private List<Banner> list;

        public IndexBanner(List<Banner> list) {
            this.list = list;
        }

        public IndexBanner() {
        }

        public List<Banner> getList() {
            return list;
        }

        public void setList(List<Banner> list) {
            this.list = list;
        }

        public static class Banner {
            //图片url
            private String url;
            //图片的标题
            private String title;

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
    }


    //宫格列表
    public static class Content {
        public String getmTitle() {
            return mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        private String mTitle;

        public Content(String mTitle) {
            this.mTitle = mTitle;
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

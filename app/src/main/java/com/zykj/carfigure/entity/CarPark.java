package com.zykj.carfigure.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/3014:48
 * desc   : 停车位
 * version: 1.0
 */
public class CarPark implements Serializable {


    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":1,"streetId":1,"carnumber":8,"cartype":"1","longitude":78.82,"latitude":23.62,"create_time":"2018-08-23T04:56:00.000+0000","update_time":"2018-08-23T04:56:00.000+0000","deviceId":"01","state":0,"emptypark":0,"licensePlateNumber":"桂.A66666","enterDate":"2018-08-23T04:00:00.000+0000","outDate":"2018-08-23T04:56:00.000+0000","charge":3,"streetName":"五合大道"},{"id":5,"streetId":1,"carnumber":5,"cartype":"1","longitude":111.65,"latitude":40.82,"create_time":"2018-08-23T04:56:00.000+0000","update_time":"2018-08-23T04:56:00.000+0000","deviceId":"05","state":0,"emptypark":0,"licensePlateNumber":"桂.A8888","enterDate":"2018-08-24T03:00:00.000+0000","outDate":"2018-08-23T04:56:00.000+0000","charge":3,"streetName":"五合大道"}]
     * code : 200
     */

    private int status;
    private String message;
    private int code;
    private List<CarParkDetail> data;

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

    public List<CarParkDetail> getData() {
        return data;
    }

    public void setData(List<CarParkDetail> data) {
        this.data = data;
    }

    public static class CarParkDetail implements Serializable {
        /**
         * id : 1
         * streetId : 1
         * carnumber : 8
         * cartype : 1
         * longitude : 78.82
         * latitude : 23.62
         * create_time : 2018-08-23T04:56:00.000+0000
         * update_time : 2018-08-23T04:56:00.000+0000
         * deviceId : 01
         * state : 0
         * emptypark : 0
         * licensePlateNumber : 桂.A66666
         * enterDate : 2018-08-23T04:00:00.000+0000
         * outDate : 2018-08-23T04:56:00.000+0000
         * charge : 3
         * streetName : 五合大道
         * "carparkinterval": 0,
         * "user_id": 0
         */

        private int id;
        private int streetId;
        private String carnumber;
        private String cartype;
        private double longitude;
        private double latitude;
        private String create_time;
        private String update_time;
        private String deviceId;
        private int state;
        private int emptypark;
        private String licensePlateNumber;
        private String enterDate;
        private String outDate;
        private double price;
        private String streetName;
        private double carparkinterval;
        private int user_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStreetId() {
            return streetId;
        }

        public void setStreetId(int streetId) {
            this.streetId = streetId;
        }


        public String getCartype() {
            return cartype;
        }

        public void setCartype(String cartype) {
            this.cartype = cartype;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getEmptypark() {
            return emptypark;
        }

        public void setEmptypark(int emptypark) {
            this.emptypark = emptypark;
        }

        public String getLicensePlateNumber() {
            return licensePlateNumber;
        }

        public void setLicensePlateNumber(String licensePlateNumber) {
            this.licensePlateNumber = licensePlateNumber;
        }

        public String getEnterDate() {
            return enterDate;
        }

        public void setEnterDate(String enterDate) {
            this.enterDate = enterDate;
        }

        public String getOutDate() {
            return outDate;
        }

        public void setOutDate(String outDate) {
            this.outDate = outDate;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public String getCarnumber() {
            return carnumber;
        }

        public void setCarnumber(String carnumber) {
            this.carnumber = carnumber;
        }

        public double getCarparkinterval() {
            return carparkinterval;
        }

        public void setCarparkinterval(double carparkinterval) {
            this.carparkinterval = carparkinterval;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}

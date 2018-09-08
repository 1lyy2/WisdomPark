package com.zykj.carfigure.entity;

import java.io.Serializable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9 /411:39
 * desc   : 当前位置最近的停车位
 * version: 1.0
 */
public class NearCarPark implements Serializable {

    /**
     * status : 200
     * message : SUCCESS
     * data : {"id":3,"streetId":1,"carnumber":"007","cartype":"1","longitude":108.509625,"latitude":22.798163,"create_time":"2018-08-23T04:56:00.000+0000","update_time":"2018-08-23T04:56:00.000+0000","deviceId":"03","state":1,"emptypark":0,"licensePlateNumber":"","enterDate":"2018-08-24T03:00:00.000+0000","outDate":"2018-08-23T04:56:00.000+0000","price":3,"streetName":"五合大道","carparkinterval":0.043465611190785305,"user_id":0}
     * code : 200
     */

    private int status;
    private String message;
    private NearCarParkBean data;
    private int code;

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

    public NearCarParkBean getData() {
        return data;
    }

    public void setData(NearCarParkBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class NearCarParkBean {
        /**
         * id : 3
         * streetId : 1
         * carnumber : 007
         * cartype : 1
         * longitude : 108.509625
         * latitude : 22.798163
         * create_time : 2018-08-23T04:56:00.000+0000
         * update_time : 2018-08-23T04:56:00.000+0000
         * deviceId : 03
         * state : 1
         * emptypark : 0
         * licensePlateNumber :
         * enterDate : 2018-08-24T03:00:00.000+0000
         * outDate : 2018-08-23T04:56:00.000+0000
         * price : 3
         * streetName : 五合大道
         * carparkinterval : 0.043465611190785305
         * user_id : 0
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
        private int price;
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

        public String getCarnumber() {
            return carnumber;
        }

        public void setCarnumber(String carnumber) {
            this.carnumber = carnumber;
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

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
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

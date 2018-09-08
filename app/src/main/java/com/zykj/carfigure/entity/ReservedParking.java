package com.zykj.carfigure.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/9/514:02
 * desc   : 预约实体类
 * version: 1.0
 */
public class ReservedParking implements Serializable {

    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":16,"license_number":"桂A88888","street_id":"3","user_id":38,"order_time":"2018-09-06 10:39:00.0","username":"胡歌","user_phone":"18275794549","order_statu":1,"order_carnumber":"011"}]
     * code : 200
     */

    private int status;
    private String message;
    private int code;
    private List<OrderParking> data;

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

    public List<OrderParking> getData() {
        return data;
    }

    public void setData(List<OrderParking> data) {
        this.data = data;
    }

    public static class OrderParking {
        /**
         * id : 16
         * license_number : 桂A88888
         * street_id : 3
         * user_id : 38
         * order_time : 2018-09-06 10:39:00.0
         * username : 胡歌
         * user_phone : 18275794549
         * order_statu : 1
         * order_carnumber : 011
         */

        private int id;
        private String license_number;
        private String street_id;
        private int user_id;
        private String order_time;
        private String username;
        private String user_phone;
        private int order_statu;
        private String order_carnumber;
        private String street_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLicense_number() {
            return license_number;
        }

        public void setLicense_number(String license_number) {
            this.license_number = license_number;
        }

        public String getStreet_id() {
            return street_id;
        }

        public void setStreet_id(String street_id) {
            this.street_id = street_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public int getOrder_statu() {
            return order_statu;
        }

        public void setOrder_statu(int order_statu) {
            this.order_statu = order_statu;
        }

        public String getOrder_carnumber() {
            return order_carnumber;
        }

        public void setOrder_carnumber(String order_carnumber) {
            this.order_carnumber = order_carnumber;
        }

        public String getStreetName() {
            return street_name;
        }

        public void setStreetName(String streetName) {
            this.street_name = streetName;
        }
    }
}

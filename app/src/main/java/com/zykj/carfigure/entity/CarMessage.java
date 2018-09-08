package com.zykj.carfigure.entity;

import java.io.Serializable;
import java.util.List;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2317:22
 * desc   : 车辆信息
 * version: 1.0
 */
public class CarMessage implements Serializable {


    /**
     * status : 200
     * message : SUCCESS
     * data : [{"id":29,"user_id":38,"car_license_plate_type":null,"car_license_plate":"桂A88888","car_brand":null,"car_model":null,"car_to_configure":null,"car_color":null,"car_type":0,"car_power_tpye":1,"car_buy_time":null,"car_kilometre_numbers":0,"create_times":"2018-08-28T02:50:49.000+0000","update_times":"2018-08-28T02:50:49.000+0000","emarks":null},{"id":30,"user_id":38,"car_license_plate_type":null,"car_license_plate":"桂A55555","car_brand":null,"car_model":null,"car_to_configure":null,"car_color":null,"car_type":0,"car_power_tpye":1,"car_buy_time":null,"car_kilometre_numbers":0,"create_times":"2018-08-28T02:59:48.000+0000","update_times":"2018-08-28T02:59:48.000+0000","emarks":null}]
     * code : 200
     */

    private int status;
    private String message;
    private int code;
    private List<Car> data;

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

    public List<Car> getData() {
        return data;
    }

    public void setData(List<Car> data) {
        this.data = data;
    }

    public static class Car implements Serializable {
        /**
         * id : 29
         * user_id : 38
         * car_license_plate_type : null
         * car_license_plate : 桂A88888
         * car_brand : null
         * car_model : null
         * car_to_configure : null
         * car_color : null
         * car_type : 0
         * car_power_tpye : 1
         * car_buy_time : null
         * car_kilometre_numbers : 0
         * create_times : 2018-08-28T02:50:49.000+0000
         * update_times : 2018-08-28T02:50:49.000+0000
         * emarks : null
         */

        private int id;
        private int user_id;
        private String car_license_plate_type;
        private String car_license_plate;
        private String car_brand;
        private String car_model;
        private String car_to_configure;
        private String car_color;
        private int car_type;
        private int car_power_tpye;
        private String car_buy_time;
        private int car_kilometre_numbers;
        private String create_times;
        private String update_times;
        private String emarks;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getCar_license_plate_type() {
            return car_license_plate_type;
        }

        public void setCar_license_plate_type(String car_license_plate_type) {
            this.car_license_plate_type = car_license_plate_type;
        }

        public String getCar_license_plate() {
            return car_license_plate;
        }

        public void setCar_license_plate(String car_license_plate) {
            this.car_license_plate = car_license_plate;
        }

        public String getCar_brand() {
            return car_brand;
        }

        public void setCar_brand(String car_brand) {
            this.car_brand = car_brand;
        }

        public String getCar_model() {
            return car_model;
        }

        public void setCar_model(String car_model) {
            this.car_model = car_model;
        }

        public String getCar_to_configure() {
            return car_to_configure;
        }

        public void setCar_to_configure(String car_to_configure) {
            this.car_to_configure = car_to_configure;
        }

        public String getCar_color() {
            return car_color;
        }

        public void setCar_color(String car_color) {
            this.car_color = car_color;
        }

        public int getCar_type() {
            return car_type;
        }

        public void setCar_type(int car_type) {
            this.car_type = car_type;
        }

        public int getCar_power_tpye() {
            return car_power_tpye;
        }

        public void setCar_power_tpye(int car_power_tpye) {
            this.car_power_tpye = car_power_tpye;
        }

        public String getCar_buy_time() {
            return car_buy_time;
        }

        public void setCar_buy_time(String car_buy_time) {
            this.car_buy_time = car_buy_time;
        }

        public int getCar_kilometre_numbers() {
            return car_kilometre_numbers;
        }

        public void setCar_kilometre_numbers(int car_kilometre_numbers) {
            this.car_kilometre_numbers = car_kilometre_numbers;
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

        public String getEmarks() {
            return emarks;
        }

        public void setEmarks(String emarks) {
            this.emarks = emarks;
        }
    }
}

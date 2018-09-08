package com.zykj.carfigure.entity;
import java.io.Serializable;
//用户
public class User implements Serializable {
    /**
     * code : 200
     * message : SUCCESS
     * data : {"id":24,"username":"18775354463","password":"7c4a8d09ca3762af61e59520943dc26494f8941b"}
     */

    private int status;
    private String message;
    private User.DataBean data;

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

    public User.DataBean getData() {
        return data;
    }

    public void setData(User.DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 24
         * username : 18775354463
         * password : 7c4a8d09ca3762af61e59520943dc26494f8941b
         */

        private int id;
        private String username;
        private String password;
        private String car_id ;
        private String  address ;
        private String  id_care ;
        private String image ;
        private String name ;
        private String  phone ;
        private String power ;
        private String  sex ;
        private String  state ;
        private String SESSION_ID;//	登录session	后续请求带上此标记
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCar_id() {
            return car_id;
        }

        public void setCar_id(String car_id) {
            this.car_id = car_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getId_care() {
            return id_care;
        }

        public void setId_care(String id_care) {
            this.id_care = id_care;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPower() {
            return power;
        }

        public void setPower(String power) {
            this.power = power;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSESSION_ID() {
            return SESSION_ID;
        }

        public void setSESSION_ID(String SESSION_ID) {
            this.SESSION_ID = SESSION_ID;
        }
    }
}


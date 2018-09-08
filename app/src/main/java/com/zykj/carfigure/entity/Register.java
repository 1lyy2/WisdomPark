package com.zykj.carfigure.entity;

import java.io.Serializable;

/**
 * author : lyy
 * e-mail : luyuyi@foxmail.com
 * date   : 2018/8/2216:48
 * desc   :
 * version: 1.0
 */
public class Register implements Serializable{


    /**
     * status : 200
     * message : SUCCESS
     * data : {"id":24,"username":"18775354463","password":"7c4a8d09ca3762af61e59520943dc26494f8941b"}
     */

    private int status;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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
    }
}

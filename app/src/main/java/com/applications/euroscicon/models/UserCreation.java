package com.applications.euroscicon.models;

import java.util.List;

public class UserCreation {

    /**
     * app_user_id : [{"app_user_id":"14","name":"suresh11","email":"suresh132@gmail.com","password":"test11","source":"android","created_date":"2020-05-15 11:53:47"}]
     * status : true
     * message : User created sucessfully
     */

    private boolean status;
    private String message;
    private List<AppUserIdBean> app_user_id;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AppUserIdBean> getApp_user_id() {
        return app_user_id;
    }

    public void setApp_user_id(List<AppUserIdBean> app_user_id) {
        this.app_user_id = app_user_id;
    }

    public static class AppUserIdBean {
        /**
         * app_user_id : 14
         * name : suresh11
         * email : suresh132@gmail.com
         * password : test11
         * source : android
         * created_date : 2020-05-15 11:53:47
         */

        private String app_user_id;
        private String name;
        private String email;
        private String password;
        private String source;
        private String created_date;

        public String getApp_user_id() {
            return app_user_id;
        }

        public void setApp_user_id(String app_user_id) {
            this.app_user_id = app_user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }
}

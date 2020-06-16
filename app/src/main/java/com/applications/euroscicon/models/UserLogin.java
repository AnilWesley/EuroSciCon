package com.applications.euroscicon.models;

import java.util.List;

public class UserLogin {


    /**
     * status : true
     * app_user : [{"app_user_id":"11","name":"suresh11","email":"suresh11@gmail.com"}]
     */

    private boolean status;
    private List<AppUserBean> app_user;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<AppUserBean> getApp_user() {
        return app_user;
    }

    public void setApp_user(List<AppUserBean> app_user) {
        this.app_user = app_user;
    }

    public static class AppUserBean {
        /**
         * app_user_id : 11
         * name : suresh11
         * email : suresh11@gmail.com
         */

        private String app_user_id;
        private String name;
        private String email;

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
    }
}

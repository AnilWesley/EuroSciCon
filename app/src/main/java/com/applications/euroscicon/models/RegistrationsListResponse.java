package com.applications.euroscicon.models;

import java.util.List;

public class RegistrationsListResponse {


    /**
     * status : true
     * result : [{"title":"9th International Conference on  Clinical and Medical Case Reports","conf_type":"conference","city":"Amsterdam","country":"Netherlands","order_no":"312226","name":null,"email":"","currency":"eur","price":"110"},{"title":"International Conference on Physical Education, Sports Medicine and Doping Studies","conf_type":"conference","city":"Osaka","country":"Japan","order_no":"312227","name":null,"email":"lina1986@live.be","currency":"eur","price":"740"}]
     */

    private boolean status;
    private List<ResultBean> result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * title : 9th International Conference on  Clinical and Medical Case Reports
         * conf_type : conference
         * city : Amsterdam
         * country : Netherlands
         * order_no : 312226
         * name : null
         * email :
         * currency : eur
         * price : 110
         */

        private String title;
        private String conf_type;
        private String city;
        private String country;
        private String order_no;
        private Object name;
        private String email;
        private String currency;
        private String price;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getConf_type() {
            return conf_type;
        }

        public void setConf_type(String conf_type) {
            this.conf_type = conf_type;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}

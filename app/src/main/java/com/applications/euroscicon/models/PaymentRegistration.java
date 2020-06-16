package com.applications.euroscicon.models;

import java.util.List;

public class PaymentRegistration {


    /**
     * status : true
     * registration_details : [{"name":"ANIL","conf_id":"6036","reg_id":"388051","country":"india","email":"anil@gmail.com","phone":"123466","amount":"799","currency":"gbp","paystatus":"pending","productid":"71972","product_type":"final"}]
     */

    private boolean status;
    private List<RegistrationDetailsBean> registration_details;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<RegistrationDetailsBean> getRegistration_details() {
        return registration_details;
    }

    public void setRegistration_details(List<RegistrationDetailsBean> registration_details) {
        this.registration_details = registration_details;
    }

    public static class RegistrationDetailsBean {
        /**
         * name : ANIL
         * conf_id : 6036
         * reg_id : 388051
         * country : india
         * email : anil@gmail.com
         * phone : 123466
         * amount : 799
         * currency : gbp
         * paystatus : pending
         * productid : 71972
         * product_type : final
         */

        private String name;
        private String conf_id;
        private String reg_id;
        private String country;
        private String email;
        private String phone;
        private String amount;
        private String currency;
        private String paystatus;
        private String productid;
        private String product_type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getConf_id() {
            return conf_id;
        }

        public void setConf_id(String conf_id) {
            this.conf_id = conf_id;
        }

        public String getReg_id() {
            return reg_id;
        }

        public void setReg_id(String reg_id) {
            this.reg_id = reg_id;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public String getProductid() {
            return productid;
        }

        public void setProductid(String productid) {
            this.productid = productid;
        }

        public String getProduct_type() {
            return product_type;
        }

        public void setProduct_type(String product_type) {
            this.product_type = product_type;
        }
    }
}

package com.applications.euroscicon.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConferenceProducts {


    private List<RegistrationProductsBean> registration_products;

    public List<RegistrationProductsBean> getRegistration_products() {
        return registration_products;
    }

    public void setRegistration_products(List<RegistrationProductsBean> registration_products) {
        this.registration_products = registration_products;
    }

    public static class RegistrationProductsBean {
        /**
         * regproducts_id : 83756
         * productname : Young Research Forum
         * price1 : 399
         * price2 : 499
         * price3 : 599
         * price4 : 380
         * price5 : 470
         * price6 : 560
         * price7 : 340
         * price8 : 420
         * price9 : 500
         * price10 : 0
         * price11 : 0
         * price12 : 0
         * status : Active
         * position : 0
         * confid : 6289
         * createdat : 2019-12-24 04:17:51
         * modifiedat : 2019-12-24 04:17:51
         * type : yrf
         * early : February 29, 2020
         * normal : March 28, 2020
         * final : April 17, 2020
         */

        private String regproducts_id;
        private String productname;
        private String price1;
        private String price2;
        private String price3;
        private String price4;
        private String price5;
        private String price6;
        private String price7;
        private String price8;
        private String price9;
        private String price10;
        private String price11;
        private String price12;
        private String status;
        private String position;
        private String confid;
        private String createdat;
        private String modifiedat;
        private String type;
        private String early;
        private String normal;
        @SerializedName("final")
        private String finalX;

        public String getRegproducts_id() {
            return regproducts_id;
        }

        public void setRegproducts_id(String regproducts_id) {
            this.regproducts_id = regproducts_id;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getPrice1() {
            return price1;
        }

        public void setPrice1(String price1) {
            this.price1 = price1;
        }

        public String getPrice2() {
            return price2;
        }

        public void setPrice2(String price2) {
            this.price2 = price2;
        }

        public String getPrice3() {
            return price3;
        }

        public void setPrice3(String price3) {
            this.price3 = price3;
        }

        public String getPrice4() {
            return price4;
        }

        public void setPrice4(String price4) {
            this.price4 = price4;
        }

        public String getPrice5() {
            return price5;
        }

        public void setPrice5(String price5) {
            this.price5 = price5;
        }

        public String getPrice6() {
            return price6;
        }

        public void setPrice6(String price6) {
            this.price6 = price6;
        }

        public String getPrice7() {
            return price7;
        }

        public void setPrice7(String price7) {
            this.price7 = price7;
        }

        public String getPrice8() {
            return price8;
        }

        public void setPrice8(String price8) {
            this.price8 = price8;
        }

        public String getPrice9() {
            return price9;
        }

        public void setPrice9(String price9) {
            this.price9 = price9;
        }

        public String getPrice10() {
            return price10;
        }

        public void setPrice10(String price10) {
            this.price10 = price10;
        }

        public String getPrice11() {
            return price11;
        }

        public void setPrice11(String price11) {
            this.price11 = price11;
        }

        public String getPrice12() {
            return price12;
        }

        public void setPrice12(String price12) {
            this.price12 = price12;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getConfid() {
            return confid;
        }

        public void setConfid(String confid) {
            this.confid = confid;
        }

        public String getCreatedat() {
            return createdat;
        }

        public void setCreatedat(String createdat) {
            this.createdat = createdat;
        }

        public String getModifiedat() {
            return modifiedat;
        }

        public void setModifiedat(String modifiedat) {
            this.modifiedat = modifiedat;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getEarly() {
            return early;
        }

        public void setEarly(String early) {
            this.early = early;
        }

        public String getNormal() {
            return normal;
        }

        public void setNormal(String normal) {
            this.normal = normal;
        }

        public String getFinalX() {
            return finalX;
        }

        public void setFinalX(String finalX) {
            this.finalX = finalX;
        }
    }
}

package com.applications.euroscicon.models;

import java.util.List;

public class AbsrtactResponse {


    /**
     * status : true
     * result : [{"title":"22<sup>nd<\/sup> World Congress on Toxicology and Pharmacology","conf_type":"webinar","city":"Kyoto","country":"India","name":"Dr Narahari","email":"naraharipatel1954@gmail.com","phone":"+91-7889135117","category":"Oral","track_name":"Forensic Toxicology","address":"#518/1 Pink Flat, Housefed Complex, Pakhowal Road \r\nLudhiana,Punjab INDIA\r\nPin Code 141013","date_of_submission":"2020-06-13","file":"http://omicsgroup.com/registration/abstract-download.php?filename=toxicology-congress-2020-192065-Abstract.docx"},{"title":"28<sup>th<\/sup> International Diabetes and Healthcare Conference","conf_type":"webinar","city":"Helsinki","country":"India","name":"Mr ABHISHEK","email":"abhishri2002@yahoo.com","phone":"8718035040","category":"Oral","track_name":"Diabetes: Types and Complications","address":"wright town jabalpur  madhya pradesh  india","date_of_submission":"2020-06-13","file":"http://omicsgroup.com/registration/abstract-download.php?filename=diabetic-2020-192066-Abstract.docx"}]
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
         * title : 22<sup>nd</sup> World Congress on Toxicology and Pharmacology
         * conf_type : webinar
         * city : Kyoto
         * country : India
         * name : Dr Narahari
         * email : naraharipatel1954@gmail.com
         * phone : +91-7889135117
         * category : Oral
         * track_name : Forensic Toxicology
         * address : #518/1 Pink Flat, Housefed Complex, Pakhowal Road
         Ludhiana,Punjab INDIA
         Pin Code 141013
         * date_of_submission : 2020-06-13
         * file : http://omicsgroup.com/registration/abstract-download.php?filename=toxicology-congress-2020-192065-Abstract.docx
         */

        private String title;
        private String conf_type;
        private String city;
        private String country;
        private String name;
        private String email;
        private String phone;
        private String category;
        private String track_name;
        private String address;
        private String date_of_submission;
        private String file;

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

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTrack_name() {
            return track_name;
        }

        public void setTrack_name(String track_name) {
            this.track_name = track_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDate_of_submission() {
            return date_of_submission;
        }

        public void setDate_of_submission(String date_of_submission) {
            this.date_of_submission = date_of_submission;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }
    }
}

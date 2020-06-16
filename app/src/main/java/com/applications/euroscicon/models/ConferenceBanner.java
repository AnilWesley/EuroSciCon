package com.applications.euroscicon.models;

import java.util.List;

public class ConferenceBanner {


    /**
     * status : true
     * conference : [{"id":"6192","conf_type":"conference","short_name":"Pediatric Dental Care 2020","title":"6<sup>th<\/sup> Annual Congress on Pediatric Dentistry & Orthodontics","city":"Auckland","country":"Newzealand","subject":"Pediatrics","start_date":"2020-02-10","end_date":"2020-02-11","url":"pediatric.dentistryconferences.com","icon_url":null,"slider_url":"https://d2cax41o7ahm5l.cloudfront.net/cs/upload-images/pediatricdentalcare2020-420.jpg"}]
     */

    private boolean status;
    private List<ConferenceBean> conference;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ConferenceBean> getConference() {
        return conference;
    }

    public void setConference(List<ConferenceBean> conference) {
        this.conference = conference;
    }

    public static class ConferenceBean {
        /**
         * id : 6192
         * conf_type : conference
         * short_name : Pediatric Dental Care 2020
         * title : 6<sup>th</sup> Annual Congress on Pediatric Dentistry & Orthodontics
         * city : Auckland
         * country : Newzealand
         * subject : Pediatrics
         * start_date : 2020-02-10
         * end_date : 2020-02-11
         * url : pediatric.dentistryconferences.com
         * icon_url : null
         * slider_url : https://d2cax41o7ahm5l.cloudfront.net/cs/upload-images/pediatricdentalcare2020-420.jpg
         */

        private String id;
        private String conf_type;
        private String short_name;
        private String title;
        private String city;
        private String country;
        private String subject;
        private String start_date;
        private String end_date;
        private String url;
        private Object icon_url;
        private String slider_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getConf_type() {
            return conf_type;
        }

        public void setConf_type(String conf_type) {
            this.conf_type = conf_type;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(Object icon_url) {
            this.icon_url = icon_url;
        }

        public String getSlider_url() {
            return slider_url;
        }

        public void setSlider_url(String slider_url) {
            this.slider_url = slider_url;
        }
    }
}

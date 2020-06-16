package com.applications.euroscicon.models;

import java.util.List;

public class FilterData {


    /**
     * status : true
     * months : [{"month":"May 2020"},{"month":"June 2020"},{"month":"July 2020"},{"month":"August 2020"},{"month":"September 2020"},{"month":"October 2020"},{"month":"November 2020"},{"month":"December 2020"},{"month":"February 2021"},{"month":"March 2021"},{"month":"April 2021"},{"month":"May 2021"},{"month":"June 2021"},{"month":"September 2021"}]
     * subjects : [{"subject":"Agri, Food & Aqua"},{"subject":"Alternative Healthcare"},{"subject":"Animal Science and Veterinary"},{"subject":"Biochemistry"},{"subject":"Business & Management"},{"subject":"Cardiology"},{"subject":"Chemical Engineering"},{"subject":"Chemistry"},{"subject":"Dentistry"},{"subject":"Dermatology"},{"subject":"Diabetes & Endocrinology"},{"subject":"EEE & Engineering"},{"subject":"Environmental Sciences"},{"subject":"Gastroenterology"},{"subject":"Genetics & Molecular Biology"},{"subject":"Geology & Earth science"},{"subject":"Healthcare Management"},{"subject":"Hematology"},{"subject":"Immunology"},{"subject":"Infectious Diseases"},{"subject":"Materials Science"},{"subject":"Medical"},{"subject":"Medical Ethics & Health Policies"},{"subject":"Microbiology"},{"subject":"Nanotechnology"},{"subject":"Nephrology"},{"subject":"Neuroscience"},{"subject":"Nursing"},{"subject":"Nutrition"},{"subject":"Obesity"},{"subject":"Oncology & Cancer"},{"subject":"Ophthalmology"},{"subject":"Palliativecare"},{"subject":"Pathology"},{"subject":"Pediatrics"},{"subject":"Petroleum"},{"subject":"Pharma Marketing & Industry"},{"subject":"Pharmaceutical Sciences"},{"subject":"Physical Therapy Rehabilitation"},{"subject":"Physics"},{"subject":"Psychiatry"},{"subject":"Pulmonology"},{"subject":"Radiology"},{"subject":"Reproductive Medicine & Women Healthcare"},{"subject":"Surgery"},{"subject":"Toxicology"},{"subject":"Vaccines"}]
     * country_cities : [{"country_id":"1","country_name":"UK","cities":[{"city_id":"488","city_name":"Birmingham"},{"city_id":"673","city_name":"Edinburgh"},{"city_id":"472","city_name":"London"},{"city_id":"508","city_name":"Manchester"}]},{"country_id":"8","country_name":"Australia","cities":[{"city_id":"11","city_name":"Brisbane"},{"city_id":"14","city_name":"Canberra"},{"city_id":"10","city_name":"Melbourne"},{"city_id":"12","city_name":"Perth"},{"city_id":"9","city_name":"Sydney"}]},{"country_id":"9","country_name":"Austria","cities":[{"city_id":"24","city_name":"Vienna"}]},{"country_id":"14","country_name":"Belgium","cities":[{"city_id":"36","city_name":"Brussels"}]},{"country_id":"18","country_name":"Canada","cities":[{"city_id":"3399","city_name":"Montreal"},{"city_id":"740","city_name":"Ottawa"},{"city_id":"738","city_name":"Toronto"},{"city_id":"3401","city_name":"Vancouver"}]},{"country_id":"20","country_name":"China","cities":[{"city_id":"79","city_name":"Beijing"},{"city_id":"80","city_name":"Guangzhou"},{"city_id":"2366","city_name":"Guangzhou"},{"city_id":"296","city_name":"Macau"},{"city_id":"78","city_name":"Shanghai"},{"city_id":"2513","city_name":"Taipei"}]},{"country_id":"22","country_name":"Croatia","cities":[{"city_id":"105","city_name":"Dubrovnik"},{"city_id":"104","city_name":"Zagreb"}]},{"country_id":"24","country_name":"Czech Republic","cities":[{"city_id":"108","city_name":"Prague"}]},{"country_id":"25","country_name":"Denmark","cities":[{"city_id":"110","city_name":"Copenhagen"}]},{"country_id":"30","country_name":"Finland","cities":[{"city_id":"117","city_name":"Helsinki"}]},{"country_id":"31","country_name":"France","cities":[{"city_id":"124","city_name":"Nice"},{"city_id":"122","city_name":"Paris"}]},{"country_id":"33","country_name":"Germany","cities":[{"city_id":"139","city_name":"Berlin"},{"city_id":"144","city_name":"Cologne"},{"city_id":"142","city_name":"Frankfurt"},{"city_id":"140","city_name":"Munich"},{"city_id":"141","city_name":"Munich"}]},{"country_id":"35","country_name":"Greece","cities":[{"city_id":"178","city_name":"Athens"}]},{"country_id":"36","country_name":"Hong Kong","cities":[{"city_id":"2348","city_name":"Hong Kong"}]},{"country_id":"37","country_name":"Hungary","cities":[{"city_id":"183","city_name":"Budapest"}]},{"country_id":"41","country_name":"Indonesia","cities":[{"city_id":"237","city_name":"Bali"}]},{"country_id":"44","country_name":"Ireland","cities":[{"city_id":"248","city_name":"Dublin"}]},{"country_id":"46","country_name":"Italy","cities":[{"city_id":"256","city_name":"Florence"},{"city_id":"255","city_name":"Milan"},{"city_id":"259","city_name":"Naples"},{"city_id":"254","city_name":"Rome"}]},{"country_id":"48","country_name":"Japan","cities":[{"city_id":"279","city_name":"Kyoto"},{"city_id":"278","city_name":"Osaka"},{"city_id":"277","city_name":"Tokyo"},{"city_id":"283","city_name":"Yokohama"}]},{"country_id":"75","country_name":"Netherlands","cities":[{"city_id":"1313","city_name":"Amersfoort"},{"city_id":"308","city_name":"Amsterdam"},{"city_id":"311","city_name":"Rotterdam"}]},{"country_id":"82","country_name":"Philippines","cities":[{"city_id":"332","city_name":"Manila"}]},{"country_id":"84","country_name":"Portugal","cities":[{"city_id":"344","city_name":"Lisbon"}]},{"country_id":"94","country_name":"South Africa","cities":[{"city_id":"376","city_name":"Cape Town"},{"city_id":"375","city_name":"Johannesburg"}]},{"country_id":"95","country_name":"South Korea","cities":[{"city_id":"1997","city_name":"Bucharest"},{"city_id":"2313","city_name":"Incheon"},{"city_id":"383","city_name":"Seoul"}]},{"country_id":"96","country_name":"Spain","cities":[{"city_id":"1282","city_name":"Alicante"},{"city_id":"388","city_name":"Barcelona"},{"city_id":"389","city_name":"Madrid"},{"city_id":"390","city_name":"Valencia"}]},{"country_id":"98","country_name":"Sweden","cities":[{"city_id":"400","city_name":"Stockholm"}]},{"country_id":"99","country_name":"Switzerland","cities":[{"city_id":"406","city_name":"Geneva"},{"city_id":"405","city_name":"Zurich"}]},{"country_id":"102","country_name":"Thailand","cities":[{"city_id":"417","city_name":"Bangkok"}]},{"country_id":"104","country_name":"Turkey","cities":[{"city_id":"423","city_name":"Istanbul"}]},{"country_id":"105","country_name":"UAE","cities":[{"city_id":"430","city_name":"Abu Dhabi"},{"city_id":"429","city_name":"Dubai"}]},{"country_id":"110","country_name":"Vietnam","cities":[{"city_id":"439","city_name":"Hanoi"}]},{"country_id":"112","country_name":"USA","cities":[{"city_id":"1228","city_name":"Athens"},{"city_id":"448","city_name":"Atlanta"},{"city_id":"463","city_name":"Baltimore"},{"city_id":"772","city_name":"Birmingham"},{"city_id":"447","city_name":"Boston"},{"city_id":"489","city_name":"Charlotte"},{"city_id":"3400","city_name":"Charlottesville"},{"city_id":"442","city_name":"Chicago"},{"city_id":"476","city_name":"Columbus"},{"city_id":"965","city_name":"Copenhagen"},{"city_id":"457","city_name":"Dallas"},{"city_id":"1231","city_name":"Florence"},{"city_id":"446","city_name":"Florida"},{"city_id":"453","city_name":"Houston"},{"city_id":"451","city_name":"Las Vegas"},{"city_id":"531","city_name":"Lexington"},{"city_id":"449","city_name":"Los Angeles"},{"city_id":"518","city_name":"Melbourne"},{"city_id":"580","city_name":"Naples"},{"city_id":"441","city_name":"New York"},{"city_id":"721","city_name":"North Carolina"},{"city_id":"516","city_name":"Omaha"},{"city_id":"454","city_name":"Orlando"},{"city_id":"450","city_name":"Philadelphia"},{"city_id":"469","city_name":"Phoenix"},{"city_id":"461","city_name":"Portland"},{"city_id":"460","city_name":"San Antonio"},{"city_id":"455","city_name":"San Diego"},{"city_id":"444","city_name":"San Francisco"},{"city_id":"520","city_name":"Santa Clara"},{"city_id":"513","city_name":"Savannah"},{"city_id":"462","city_name":"Seattle"}]},{"country_id":"113","country_name":"Newzealand","cities":[{"city_id":"654","city_name":"Auckland"},{"city_id":"655","city_name":"Wellington"}]},{"country_id":"114","country_name":"Singapore","cities":[{"city_id":"730","city_name":"Singapore"}]},{"country_id":"115","country_name":"Malaysia","cities":[]},{"country_id":"178","country_name":"Scotland","cities":[{"city_id":"748","city_name":"Edinburgh"},{"city_id":"758","city_name":"Perth"}]}]
     */

    private boolean status;
    private List<MonthsBean> months;
    private List<SubjectsBean> subjects;
    private List<CountryCitiesBean> country_cities;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<MonthsBean> getMonths() {
        return months;
    }

    public void setMonths(List<MonthsBean> months) {
        this.months = months;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public List<CountryCitiesBean> getCountry_cities() {
        return country_cities;
    }

    public void setCountry_cities(List<CountryCitiesBean> country_cities) {
        this.country_cities = country_cities;
    }

    public static class MonthsBean {
        /**
         * month : May 2020
         */

        private String month;

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }

    public static class SubjectsBean {
        /**
         * subject : Agri, Food & Aqua
         */

        private String subject;

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
    }

    public static class CountryCitiesBean {
        /**
         * country_id : 1
         * country_name : UK
         * cities : [{"city_id":"488","city_name":"Birmingham"},{"city_id":"673","city_name":"Edinburgh"},{"city_id":"472","city_name":"London"},{"city_id":"508","city_name":"Manchester"}]
         */

        private String country_id;
        private String country_name;
        private List<CitiesBean> cities;

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getCountry_name() {
            return country_name;
        }

        public void setCountry_name(String country_name) {
            this.country_name = country_name;
        }

        public List<CitiesBean> getCities() {
            return cities;
        }

        public void setCities(List<CitiesBean> cities) {
            this.cities = cities;
        }

        public static class CitiesBean {
            /**
             * city_id : 488
             * city_name : Birmingham
             */

            private String city_id;
            private String city_name;

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }
        }
    }
}

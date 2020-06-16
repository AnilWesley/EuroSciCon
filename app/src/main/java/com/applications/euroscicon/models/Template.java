package com.applications.euroscicon.models;

import java.util.List;

public class Template {

    /**
     * status : true
     * result : [{"abstract_template":"https://computergraphics-animation.conferenceseries.com/abstract-template.doc"}]
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
         * abstract_template : https://computergraphics-animation.conferenceseries.com/abstract-template.doc
         */

        private String abstract_template;

        public String getAbstract_template() {
            return abstract_template;
        }

        public void setAbstract_template(String abstract_template) {
            this.abstract_template = abstract_template;
        }
    }
}

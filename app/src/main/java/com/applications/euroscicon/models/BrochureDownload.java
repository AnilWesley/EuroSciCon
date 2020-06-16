package com.applications.euroscicon.models;

import java.util.List;

public class BrochureDownload {


    /**
     * file : [{"brochure_file":"https://d2cax41o7ahm5l.cloudfront.net/cs/pdfs/computer-graphics-animation-2020-50903-brochure78669.pdf"}]
     * status : true
     */

    private boolean status;
    private List<FileBean> file;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<FileBean> getFile() {
        return file;
    }

    public void setFile(List<FileBean> file) {
        this.file = file;
    }

    public static class FileBean {
        /**
         * brochure_file : https://d2cax41o7ahm5l.cloudfront.net/cs/pdfs/computer-graphics-animation-2020-50903-brochure78669.pdf
         */

        private String brochure_file;

        public String getBrochure_file() {
            return brochure_file;
        }

        public void setBrochure_file(String brochure_file) {
            this.brochure_file = brochure_file;
        }
    }
}

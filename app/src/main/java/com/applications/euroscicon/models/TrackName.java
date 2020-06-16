package com.applications.euroscicon.models;

import java.util.List;

public class TrackName {

    /**
     * status : true
     * tracks : [{"TrackName":"Dementia"},{"TrackName":"Alzheimerâ\u20ac™s Disease"},{"TrackName":"Dementia and Ageing"},{"TrackName":"Vascular Dementia"},{"TrackName":"Geriatrics Dementia and Cognitive Disorders"},{"TrackName":"Animal Models in Dementia"},{"TrackName":"Amyloid Protein in Alzheimerâ\u20ac™s and Dementia"},{"TrackName":"Dementia Care Management"},{"TrackName":"Dementia Nursing"},{"TrackName":"Recent Advancement in Treating Dementia"}]
     */

    private boolean status;
    private List<TracksBean> tracks;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<TracksBean> getTracks() {
        return tracks;
    }

    public void setTracks(List<TracksBean> tracks) {
        this.tracks = tracks;
    }

    public static class TracksBean {
        /**
         * TrackName : Dementia
         */

        private String TrackName;

        public String getTrackName() {
            return TrackName;
        }

        public void setTrackName(String TrackName) {
            this.TrackName = TrackName;
        }
    }
}

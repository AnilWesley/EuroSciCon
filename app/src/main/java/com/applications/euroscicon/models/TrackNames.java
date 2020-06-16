package com.applications.euroscicon.models;

import java.util.List;

public class TrackNames {

    private String countryId;
    private String countryName;
    private String description;


    private List<SubTrackNames> subCity;

    public TrackNames() {
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<SubTrackNames> getSubCity() {
        return subCity;
    }

    public void setSubCity(List<SubTrackNames> subCity) {
        this.subCity = subCity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.applications.euroscicon.models;

import java.util.List;

public class CountryItem {

    private String countryId;
    private String countryName;
    private String isChecked = "NO";
    private List<CityItem> subCity;

    public CountryItem() {
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

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public List<CityItem> getSubCity() {
        return subCity;
    }

    public void setSubCity(List<CityItem> subCity) {
        this.subCity = subCity;
    }
}

package com.softcodeinfotech.dreamhousing.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocationModel {
   /* @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("pin")
    @Expose
    private Integer pin;
    */


    String locality;
    Integer pin;

    public LocationModel(String locality, Integer pin) {
        this.locality = locality;
        this.pin = pin;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }
}

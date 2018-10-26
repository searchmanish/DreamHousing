package com.softcodeinfotech.dreamhousing.search;

public class Location {
    String location;
    String pin;

    public Location(String location, String pin) {
        this.location = location;
        this.pin = pin;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

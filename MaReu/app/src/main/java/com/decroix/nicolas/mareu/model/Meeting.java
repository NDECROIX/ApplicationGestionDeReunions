package com.decroix.nicolas.mareu.model;

import java.util.List;

public class Meeting {

    private String name;
    private String location;
    private String date;
    private String timeStart;
    private String timeEnd;
    private List<String> email;

    public Meeting(String name, String location, String date, String timeStart, String timeEnd, List<String> email) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.email = email;
    }

    // GETTER

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public List<String> getEmail() {
        return email;
    }

    // SETTER


    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }
}

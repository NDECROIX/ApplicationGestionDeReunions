package com.decroix.nicolas.mareu.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    /**
     * Subject
     */
    private String subject;

    /**
     * Meeting room
     */
    private String location;

    /**
     * Date and time
     */
    private Date dateTime;

    /** Participants' email address */
    private String email;

    /**
     * Constructor
     *
     * @param subject
     * @param location
     * @param dateTime
     * @param email
     */
    public Meeting(String subject, String location, Date dateTime, String email) {
        this.subject = subject;
        this.location = location;
        this.dateTime = dateTime;
        this.email = email;
    }

    // GETTER

    public String getSubject() {
        return subject;
    }

    public String getLocation() {
        return location;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getEmail() {
        return email;
    }

    // SETTER

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String meetingDateToString() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        return subject + " - " + dateFormat.format(dateTime) + " - " + location;
    }
}

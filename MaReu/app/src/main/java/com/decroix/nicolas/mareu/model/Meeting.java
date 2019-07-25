package com.decroix.nicolas.mareu.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Model object representing a Meeting
 */
public class Meeting implements Serializable {

    /**
     * Meeting subject
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

    /**
     * Participants' emails address
     */
    private String emails;

    /**
     * Constructor
     *
     * @param subject  Meeting subject
     * @param location Meeting room
     * @param dateTime Date and time
     * @param email    Participants' emails address
     */
    public Meeting(String subject, String location, Date dateTime, String email) {
        this.subject = subject;
        this.location = location;
        this.dateTime = dateTime;
        this.emails = email;
    }

    // GETTER

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // SETTER

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String email) {
        this.emails = email;
    }

    /**
     * Concatenate the subject, date and meeting room as the title.
     *
     * @return returns the result of the concatenation
     */
    public String meetingTitleToString() {
        DateFormat dateFormat = new SimpleDateFormat("dd MMM HH:mm", Locale.FRANCE);
        return subject + " - " + dateFormat.format(dateTime) + " - " + location;
    }

    public String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return dateFormat.format(dateTime);
    }

    public String getTimeString() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        return timeFormat.format(dateTime);
    }
}

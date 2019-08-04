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
    private String meetingRoom;

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
     * @param meetingRoom Meeting room
     * @param dateTime Date and time
     * @param email    Participants' emails address
     */
    public Meeting(String subject, String meetingRoom, Date dateTime, String email) {
        this.subject = subject;
        this.meetingRoom = meetingRoom;
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

    public String getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(String meetingRoom) {
        this.meetingRoom = meetingRoom;
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
        return subject + " - " + dateFormat.format(dateTime) + " - " + meetingRoom;
    }

    public String getDateString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        return dateFormat.format(dateTime);
    }

    public String getTimeString() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        return timeFormat.format(dateTime);
    }
    
    public boolean isContentIdentical(Meeting meeting){
        if(meeting == this) return true;
        boolean equalsSubject = (this.subject != null && this.subject.equals(meeting.getSubject())) ||
                (this.subject == null && meeting.subject == null);
        boolean equalsMeetingRoom = (this.meetingRoom != null && this.meetingRoom.equals(meeting.getMeetingRoom())) ||
                (this.meetingRoom == null && meeting.getMeetingRoom() == null);
        boolean equalsDateTime = this.dateTime != null && this.dateTime.equals(meeting.getDateTime())||
                (this.dateTime == null && meeting.getDateTime() == null);
        boolean equalsMails = this.emails != null && this.emails.equals(meeting.getEmails())||
                (this.emails == null && meeting.getEmails() == null);
        return equalsSubject && equalsMeetingRoom && equalsDateTime && equalsMails;
    }
}

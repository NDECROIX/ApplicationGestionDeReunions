package com.decroix.nicolas.mareu.events;

import com.decroix.nicolas.mareu.model.Meeting;

/**
 * Event fired when a user save a Meeting
 */
public class SaveMeetingEvent {

    /**
     * Meeting to save
     */
    public Meeting meeting;

    /**
     * Constructor.
     * @param meeting Meeting to be saved
     */
    public SaveMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}

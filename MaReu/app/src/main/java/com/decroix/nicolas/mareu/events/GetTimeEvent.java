package com.decroix.nicolas.mareu.events;

import java.util.Calendar;

/**
 * Event fired when a user select a Time
 */
public class GetTimeEvent {

    /**
     * Selected time
     */
    public Calendar time;

    /**
     * Constructor.
     * @param time Time to be deleted
     */
    public GetTimeEvent(Calendar time) {
        this.time = time;
    }
}

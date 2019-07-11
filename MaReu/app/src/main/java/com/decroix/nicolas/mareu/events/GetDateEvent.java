package com.decroix.nicolas.mareu.events;

import java.util.GregorianCalendar;

/**
 * Event fired when a user select a Date
 */
public class GetDateEvent {

    /**
     * Selected date
     */
    public GregorianCalendar date;

    /**
     * Constructor.
     * @param date Date to be deleted
     */
    public GetDateEvent(GregorianCalendar date) {
        this.date = date;
    }
}

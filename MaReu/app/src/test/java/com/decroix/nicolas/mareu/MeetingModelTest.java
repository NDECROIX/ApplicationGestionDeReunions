package com.decroix.nicolas.mareu;

import com.decroix.nicolas.mareu.model.Meeting;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static junit.framework.TestCase.assertEquals;

/**
 * unit test on Meeting Model
 */
public class MeetingModelTest {

    private Meeting meeting;

    @Before
    public void createMeeting() {
        String subject = "Subject";
        String location = "Location";
        String mails = "lamzone@lamzone.com, lamzone@lamzone.com";
        Date date = new GregorianCalendar(2019, 6, 13, 12, 8).getTime();
        meeting = new Meeting(subject, location, date, mails);
    }

    @Test
    public void getMeetingDateToStringWithSuccess() {
        String title = "Subject - 13 juil. 12:08 - Location";
        assertEquals(title, meeting.meetingTitleToString());
    }

    @Test
    public void getDateStringWithSuccess() {
        String dateString = "13/07/2019";
        assertEquals(dateString, meeting.getDateString());
    }

    @Test
    public void getTimeStringWithSuccess() {
        String timeString = "12:08";
        assertEquals(timeString, meeting.getTimeString());
    }
}
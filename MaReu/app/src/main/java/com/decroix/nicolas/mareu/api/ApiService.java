package com.decroix.nicolas.mareu.api;

import com.decroix.nicolas.mareu.model.Meeting;

import java.util.Date;
import java.util.List;

public interface ApiService {
    List<Meeting> getMeetings();

    void addMeeting(Meeting meeting);

    void deleteMeeting(Meeting meeting);

    void sortByDate();

    void sortByMeetingRoom();

    boolean availableMeetingRoom(Date date, String meetingRoom);
}

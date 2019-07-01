package com.decroix.nicolas.mareu.service;

import com.decroix.nicolas.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();

    void deleteMeeting(Meeting meeting);

}

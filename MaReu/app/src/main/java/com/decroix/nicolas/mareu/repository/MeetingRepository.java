package com.decroix.nicolas.mareu.repository;

import com.decroix.nicolas.mareu.api.ApiService;
import com.decroix.nicolas.mareu.model.Meeting;

import java.util.Date;
import java.util.List;

public class MeetingRepository {

    private final ApiService apiService;

    public MeetingRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public List<Meeting> getMeetings() {
        return apiService.getMeetings();
    }

    public void addMeeting(Meeting meeting) {
        apiService.addMeeting(meeting);
    }

    public void deleteMeeting(Meeting meeting) {
        apiService.deleteMeeting(meeting);
    }

    public void updateMeeting(Meeting meeting, Meeting update){
        apiService.updateMeeting(meeting, update);
    }

    public void sortByDate() {
        apiService.sortByDate();
    }

    public void sortByMeetingRoom() {
        apiService.sortByMeetingRoom();
    }

    public boolean availableMeetingRoom(Date date, String meetingRoom, Meeting toUpdate) {
        return apiService.availableMeetingRoom(date, meetingRoom, toUpdate);
    }
}

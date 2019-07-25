package com.decroix.nicolas.mareu;

import android.app.Application;

import com.decroix.nicolas.mareu.di.DependencyInjector;
import com.decroix.nicolas.mareu.repository.MeetingRepository;

public class MaReuApplication extends Application {

    private MeetingRepository meetingRepository;

    // GET MEETING REPOSITORY

    public MeetingRepository getMeetingRepository() {
        if (meetingRepository == null)
            meetingRepository = DependencyInjector.createMeetingRepository();
        return meetingRepository;
    }

    public void resetMeetingRepository() {
        meetingRepository = null;
    }
}

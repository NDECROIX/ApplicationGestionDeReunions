package com.decroix.nicolas.mareu.dependencyInjector;

import com.decroix.nicolas.mareu.service.DummyMeetingApiService;
import com.decroix.nicolas.mareu.service.MeetingApiService;

public class DependencyInjector {

    private static MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService() { return service;}

    public static MeetingApiService getNewInstanceApiService() { return new DummyMeetingApiService();}

}

package com.decroix.nicolas.mareu.di;

import com.decroix.nicolas.mareu.api.MeetingApiService;
import com.decroix.nicolas.mareu.repository.MeetingRepository;

public class DependencyInjector {
    public static MeetingRepository createMeetingRepository() {
        return new MeetingRepository(new MeetingApiService());
    }
}

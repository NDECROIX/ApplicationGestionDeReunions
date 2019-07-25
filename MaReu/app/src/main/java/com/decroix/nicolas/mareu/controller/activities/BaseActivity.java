package com.decroix.nicolas.mareu.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import com.decroix.nicolas.mareu.MaReuApplication;
import com.decroix.nicolas.mareu.repository.MeetingRepository;

public abstract class BaseActivity extends AppCompatActivity {
    public MeetingRepository getMeetingRepository() {
        return ((MaReuApplication) getApplication()).getMeetingRepository();
    }

    public void resetMeetingRepository() {
        ((MaReuApplication) getApplication()).resetMeetingRepository();
    }
}

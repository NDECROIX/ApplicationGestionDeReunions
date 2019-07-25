package com.decroix.nicolas.mareu.controller.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.controller.fragments.ListMeetingsFragment;

/**
 * Main activity of the application
 */
public class MeetingActivity extends BaseActivity {

    public ListMeetingsFragment listMeetingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        resetMeetingRepository();
        startFragment();
    }

    private void startFragment() {
        listMeetingFragment = new ListMeetingsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_list_meeting_placeholder, listMeetingFragment);
        transaction.commit();
    }
}
